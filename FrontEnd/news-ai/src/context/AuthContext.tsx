import React, { createContext, useContext, useState, useEffect, ReactNode } from "react";
import { useNavigate } from "react-router-dom";
import config from "../config"; // Adjust the path if necessary

interface AuthContextType {
  user: any;
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
  handleRefreshToken: () => Promise<void>;
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
  const [user, setUser] = useState<any>(null);
  const [token, setToken] = useState<string | null>(localStorage.getItem("token"));
  const [refreshToken, setRefreshToken] = useState<string | null>(localStorage.getItem("refreshToken"));
  const navigate = useNavigate();

  useEffect(() => {
    const token = localStorage.getItem("token");
    if (token) {
      setToken(token); // ודא שהטוקן נטען ל-state
      fetchUser(); // טען את פרטי המשתמש
    } else {
      navigate("/login"); // אם אין טוקן, נווט לדף ההתחברות
    }
  }, []);

  useEffect(() => {
    if (token) {
      fetchUser();
    }
  }, [token]);

  const fetchUser = async () => {
    try {
      const res = await fetch(`${config.baseURL}/getUser`, {
        headers: { Authorization: `Bearer ${token}` },
      });
      if (res.status === 401) {
        await handleRefreshToken(); // רענן את הטוקן אם מתקבלת שגיאה 401
        return;
      }
      if (!res.ok) throw new Error("Failed to fetch user");
      const data = await res.json();
      setUser(data);
    } catch {
      logout(); // אם לא ניתן לרענן את הטוקן, בצע התנתקות
    }
  };

  const login = async (userIdentifier: string, password: string) => {
    const res = await fetch(`${config.baseURL}/authenticate`, {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify({ userIdentifier: userIdentifier, password }),
    });
    if (!res.ok) throw new Error("Login failed");
    
    const data = await res.json();
    console.log(`Login response: token? ${data.token?true:false} refresh token? ${data.refreshToken?true:false}`); // Log the response for debugging
    setUser(data);
    setToken(data.token);
    setRefreshToken(data.refreshToken);
    localStorage.setItem("token", data.token);
    localStorage.setItem("refreshToken", data.refreshToken);
    console.log(` token? ${localStorage.getItem("token")?true:false}`); // Log the response for debugging
    console.log("nevigating to dashboard...");
    navigate("/dashboard" ,{state:{token:data.token}});
  };

  const handleRefreshToken = async () => {
    console.log("Refreshing token...");
    if (!refreshToken){
      console.log("No refresh token found, logging out...");
      setRefreshToken(localStorage.getItem("refreshToken"));
      if (!refreshToken) 
        console.log("No refresh token found, logging out...");
        return logout();
    } 
    try {
      const res = await fetch(`${config.baseURL}/refreshToken`, {
        method: "POST",
        headers: { 
          Authorization: `Bearer ${token}`,
          "Content-Type": "application/json" },
        body: JSON.stringify({ refreshToken }),
      });

      if (!res.ok){
        console.log("Refresh token failed, logging out...");
         throw new Error("Refresh token failed");

      }
      const data = await res.json();
      console.log(`Login response: token? ${data.token?true:false} refresh token? ${data.refreshToken?true:false}`);
      setToken(data.token);
      localStorage.setItem("token", data.token);
      if (data.refreshToken) {
        setRefreshToken(data.refreshToken); 
        localStorage.setItem("refreshToken", data.refreshToken);
      }
      localStorage.setItem("token", data.token);
    } catch (error) {
      console.error("Token refresh failed:", error);
      logout();
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
      localStorage.setItem("token", data.token);
      localStorage.setItem("refreshToken", data.refreshToken);
      setUser(data.user);

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
    await fetchUser();
      

  };

  const logout = () => {
    setUser(null);
    setToken(null);
    setRefreshToken(null);
    localStorage.removeItem("token");
    localStorage.removeItem("refreshToken");
    navigate("/login");
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
// טיפוסים
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

// פונקציה לבדיקה האם הטוקן עומד לפוג בקרוב
const isTokenExpiringSoon = (token: string | null): boolean => {
  if (!token) return false;
  
  const decoded = parseJwt(token);
  if (!decoded || !decoded.exp) return false;
  
  // המרה למילישניות ובדיקה אם נותרה פחות מדקה
  const expirationTime = decoded.exp * 1000;
  const currentTime = Date.now();
  const timeRemaining = expirationTime - currentTime;
  
  // מחזיר אמת אם נותר פחות מדקה (60000 מילישניות)
  return timeRemaining > 0 && timeRemaining < 60000*1;
};


// פונקציה לבדיקה תקופתית של תוקף הטוקן
let tokenCheckInterval: NodeJS.Timeout | undefined;

const startTokenExpirationChecker = (): void => {
  // ניקוי האינטרוול הקודם אם קיים
  if (tokenCheckInterval) {
    clearInterval(tokenCheckInterval);
  }
  
  // בדיקה כל 30 שניות אם הטוקן עומד לפוג
  tokenCheckInterval = setInterval(() => {
    const currentToken = localStorage.getItem("token");
    
    if (isTokenExpiringSoon(currentToken)) {
      console.log("Token is about to expire in less than a minute, refreshing...");
      handleRefreshToken();
    }
  }, 30000); // בדיקה כל 30 שניות
};

// התחלת המנגנון כשהקומפוננטה נטענת
useEffect(() => {
  const currentToken = localStorage.getItem("token");
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
    <AuthContext.Provider value={{ user, login, register, logout ,handleRefreshToken,updateUser,deleteUser,changePassword}}>
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






