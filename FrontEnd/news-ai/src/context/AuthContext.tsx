import React, { createContext, useContext, useState, useEffect, ReactNode, useCallback, useRef } from "react";
import { useLocation, useNavigate } from "react-router-dom";
import config from "../config"; 


interface AuthContextType {
  user: any;
  loading: boolean;
  tokenStr: string;
  login: (userIdentifier: string, password: string) => Promise<void>;
  deleteUser: () => Promise<void>;
  changePassword: (oldPassword: string, newPassword: string) => Promise<void>;
  register: ({
    userName,
    firstName,
    lastName,
    email,
    phone,
    password,
  }: {
    userName: string;
    firstName: string;
    lastName: string;
    email: string;
    phone: string;
    password: string;
  }) => Promise<void>;
  logout: () => void;
  handleRefreshToken: () => Promise<boolean>;
  updateUser:({
    firstName,
    lastName,
    email,
    phone,
  }: {
    firstName: string;
    lastName: string;
    email: string;
    phone: string;
  }) => Promise<void>;
}

const AuthContext = createContext<AuthContextType | undefined>(undefined);

interface AuthProviderProps {
  children: ReactNode;
}

export const AuthProvider: React.FC<AuthProviderProps> = ({ children }) => {
  
  const tokenStr=`newsAi_token`;
  const refreshTokenStr=`newsAi_refreshToken`;
  const [user, setUser] = useState<any>(null);
  const [token, setToken] = useState<string | null>(localStorage.getItem(tokenStr));
  const [refreshToken, setRefreshToken] = useState<string | null>(localStorage.getItem(refreshTokenStr));
  const navigate = useNavigate();
  const [loading, setLoading] = useState<boolean>(true);
  const location = useLocation();
  let refersing =false;
  

  const logout = useCallback(() => {
    localStorage.removeItem(tokenStr);
    localStorage.removeItem(refreshTokenStr);
    
    setToken(null);
    setRefreshToken(null);
    setUser(null);
    navigate('/login');
  }, [navigate]);

  const handleRefreshToken = useCallback(async (): Promise<boolean> => {
    if (refersing) return false;
    refersing = true;
    
    console.log("Refreshing token...");
    const currentRefreshToken = refreshToken || localStorage.getItem(refreshTokenStr);


    if (!token) {
      console.log("No token found, cannot refresh");
      refersing = false;
      return false;
    }
    else{
      console.log("Token found, proceeding to refresh...");
      const localStorageToken = localStorage.getItem(tokenStr);
      if (localStorageToken !== token) {
        console.log("Token in local storage is different from current token, updating...");
        setToken(localStorageToken);
      }
    }

    if (!currentRefreshToken) {
      console.log("No refresh token found");
      refersing = false;
      return false;
    }

    try {
      console.log("I have a refresh token and token, trying to refresh...");
      const res = await fetch(`${config.baseURL}/refreshToken`, {
        method: "POST",
        headers: {
          Authorization: `Bearer ${token}`,
          "Content-Type": "application/json",
        },
        body: JSON.stringify({ refreshToken: currentRefreshToken }),
      });

      if (!res.ok) {
        console.log(`Refresh token request failed with status: ${res.status}`);
        refersing = false;
        logout();
        return false;
      }

      const data = await res.json();
      console.log(`Token refresh response: token? ${data.token ? true : false}`);

      localStorage.setItem(tokenStr, data.token);
      setToken(data.token);
      

      if (data.refreshToken) {
        setRefreshToken(data.refreshToken);
        localStorage.setItem(refreshTokenStr, data.refreshToken);
      }

      refersing = false;
      return true;
    } catch (error) {
      console.error(`Token refresh failed: status code `, error);
            return false;
    }
  }, [token, refreshToken]);

  const fetchUser = useCallback(async (shouldRetryOnRefresh = true) => {
    if (!token) return false;

    try {
      console.log("Fetching user data with token...");

      const res = await fetch(`${config.baseURL}/getUser`, {
        headers: { Authorization: `Bearer ${token}` },
      });

      if (res.status === 401 && shouldRetryOnRefresh) {
        console.log("401 received during fetchUser, trying to refresh token");
        const refreshSuccessful = await handleRefreshToken();
        if (refreshSuccessful) {
          // Try once more with the new token
          return fetchUser(false);
        }
        throw new Error("Authentication failed");
      }

      if (!res.ok) {
        console.log("Failed to fetch user, status:", res.status);
        throw new Error(`Failed to fetch user - Status: ${res.status}`);
      }

      const data = await res.json();
      setUser(data);
      return true;
    } catch (error) {
      console.error("Error fetching user:", error);
      return false;
    }
  }, [token, handleRefreshToken]);


  const prevTokenRef = useRef<string | null>(null);

  useEffect(() => {
    const prevToken = prevTokenRef.current;

    // רק אם קודם לא היה טוקן ועכשיו יש – כלומר התחברות
    if (!prevToken && token) {
      const checkAuthentication = async () => {
        setLoading(true);
        try {
          const userFetched = await fetchUser();
          if (
            !userFetched &&
            location.pathname !== "/login" &&
            location.pathname !== "/signup"
          ) {
            logout();
          }
        } catch (error) {
          console.error("User fetch error:", error);
        } finally {
          setLoading(false);
        }
      };

      checkAuthentication();
    }

    prevTokenRef.current = token;
  }, [token]);



  const login = async (userIdentifier: string, password: string) => {
    setLoading(true);
    try {
      const res = await fetch(`${config.baseURL}/authenticate`, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ userIdentifier: userIdentifier, password }),
      });
      
      if (!res.ok) throw new Error("Login failed");
      
      const data = await res.json();
      console.log(`Login response: token received: ${!!data.token}`);
      setToken(data.token);
      setRefreshToken(data.refreshToken);
      navigate("/dashboard");
      setUser(data);
      
     
      localStorage.setItem(tokenStr, data.token);
      localStorage.setItem(refreshTokenStr, data.refreshToken);
      
      console.log("Navigating to dashboard...");
      
    } catch (error) {
      console.error("Login error:", error);
      throw error;
    } finally {
      setLoading(false);
    }
  };
  
  const register = async ({    userName,    firstName,    lastName,    email,    phone,    password,  }: {
    userName: string;
    firstName: string;
    lastName: string;
    email: string;
    phone: string;
    password: string;
  }) => {
    const res = await fetch(`${config.baseURL}/saveUser`, {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify({ userName, firstName, lastName, email, phone, password }),
    });

    if (!res.ok) 
      throw new Error("Registration failed");
    const data = await res.json();
    setToken(data.token);
    setRefreshToken(data.refreshToken);
    localStorage.setItem(tokenStr, data.token);
    localStorage.setItem(refreshTokenStr, data.refreshToken);
    setUser(data.user);
    navigate("/dashboard"); 
  };

  const updateUser = async ({  firstName,    lastName,    email,    phone,    }: {
    firstName: string;
    lastName: string;
    email: string;
    phone: string;
  }) => {
    const res = await fetch(`${config.baseURL}/updateUser`, {
      method: "PUT",
      headers: { 
        Authorization: `Bearer ${token}`,
        "Content-Type": "application/json" },
      body: JSON.stringify({  firstName, lastName, email, phone }),
    });

    if (!res.ok) 
      throw new Error("Registration failed");

    localStorage.removeItem(tokenStr);
    localStorage.removeItem(refreshTokenStr);
 
    localStorage.setItem(tokenStr, token ?? "");
    localStorage.setItem(refreshTokenStr, refreshToken ?? "");

    await fetchUser();
      

  };
 

  
  const deleteUser = async () => {
    const res = await fetch(`${config.baseURL}/deleteUser`, {
      method: "DELETE",
      headers: { Authorization: `Bearer ${token}` },
    });

    if (!res.ok) throw new Error("Failed to delete user");
    logout();
  };

  const changePassword = async (oldPassword: string, newPassword: string) => {
    const res = await fetch(`${config.baseURL}/changePassword`, {
      method: "PUT",
      headers: { Authorization: `Bearer ${token}`, "Content-Type": "application/json" },
      body: JSON.stringify({ oldPassword, newPassword }),
    });

    if (!res.ok) throw new Error("Failed to change password");
    console.log("Password changed successfully");
  };

interface JwtPayload {
  exp: number;
  [key: string]: any;
}



// פונקציה לפענוח הטוקן ללא צורך בספריות חיצוניות
const parseJwt = (token: string): JwtPayload | null => {
  try {
    const base64Url = token.split('.')[1];
    const base64 = base64Url.replace(/-/g, '+').replace(/_/g, '/');
    const jsonPayload = decodeURIComponent(
      atob(base64).split('').map(c => {
        return '%' + ('00' + c.charCodeAt(0).toString(16)).slice(-2);
      }).join('')
    );
    return JSON.parse(jsonPayload);
  } catch (error) {
    console.error("Error parsing JWT:", error);
    return null;
  }
};

const isTokenExpiringSoon = (token: string | null): boolean => {
  if (!token) return false;
  
  const decoded = parseJwt(token);
  if (!decoded || !decoded.exp) return false;
  
  // המרה למילישניות ובדיקה אם נותרה פחות מדקה
  const expirationTime = decoded.exp * 1000;
  const currentTime = Date.now();
  const timeRemaining = expirationTime - currentTime;
  
  // מחזיר אמת אם נותר פחות מדקה (60000 מילישניות)
  return timeRemaining > 0 && timeRemaining < 60000*3;
};


let tokenCheckInterval: NodeJS.Timeout | undefined;

const startTokenExpirationChecker = (): void => {
  if (tokenCheckInterval) {
    clearInterval(tokenCheckInterval);
  }
  
  tokenCheckInterval = setInterval(() => {
    const currentToken = token || localStorage.getItem(tokenStr);
    
    if (isTokenExpiringSoon(currentToken)) {
      console.log("Token is about to expire in less than a minute, refreshing...");
      if(!refersing)
        handleRefreshToken();
    }
  }, 30000);
};

useEffect(() => {
  const currentToken = localStorage.getItem(tokenStr);
  if (currentToken) {
    startTokenExpirationChecker();
  }

  return () => {
    if (tokenCheckInterval) {
      clearInterval(tokenCheckInterval);
    }
  };
}, []);

  return (
    <AuthContext.Provider value={{ tokenStr,user,loading, login, register, logout ,handleRefreshToken,updateUser,deleteUser,changePassword}}>
      {children}
    </AuthContext.Provider>
  );
};

export const useAuth = (): AuthContextType => {
  const context = useContext(AuthContext);
  if (context === undefined) {
    throw new Error("useAuth must be used within an AuthProvider");
  }
  return context;
};






