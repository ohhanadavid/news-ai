import React, { createContext, useContext, useState, useEffect, ReactNode, useCallback } from "react";
import { useLocation, useNavigate } from "react-router-dom";
import config from "../config"; // Adjust the path if necessary

interface AuthContextType {
  user: any;
  loading: boolean;
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
  const [user, setUser] = useState<any>(null);
  const [token, setToken] = useState<string | null>(localStorage.getItem("token"));
  const [refreshToken, setRefreshToken] = useState<string | null>(localStorage.getItem("refreshToken"));
  const navigate = useNavigate();
  const [loading, setLoading] = useState<boolean>(true);
  const location = useLocation();

  // useEffect(() => {
  //   const token = localStorage.getItem("token");
  //   if (token) {
  //     setToken(token); // ודא שהטוקן נטען ל-state
  //     fetchUser(); // טען את פרטי המשתמש
  //   } else {
  //     navigate("/login"); // אם אין טוקן, נווט לדף ההתחברות
  //   }
  // }, []);
  // useEffect(() => {
  //   const checkAuth = async () => {
  //     setLoading(true);
      
  //     try {
  //       const storedToken = localStorage.getItem("token");
        
  //       if (!storedToken) {
  //         console.log("No token found in localStorage");
  //         if (location.pathname !== '/login' && location.pathname !== '/signup') {
  //           navigate("/login");
  //         }
  //         setLoading(false);
  //         return;
  //       }
        
  //       setToken(storedToken);
  //       const userFetched = await fetchUser();
        
  //       if (!userFetched) {
  //         console.log("Failed to fetch user, trying token refresh");
  //         const refreshSuccessful = await handleRefreshToken();
          
  //         if (!refreshSuccessful && location.pathname !== '/login' && location.pathname !== '/signup') {
  //           console.log("Refresh failed, redirecting to login");
  //           logout();
  //         }
  //       }
  //     } catch (error) {
  //       console.error("Auth check error:", error);
  //     } finally {
  //       setLoading(false);
  //     }
  //   };

  //   checkAuth();
  // }, []);


  // useEffect(() => {
  //   if (token) {
  //     fetchUser();
  //   }
  // }, [token]);

  // useEffect(() => {
  //   const checkAuth = async () => {
  //     setLoading(true);
      
  //     try {
  //       const storedToken = localStorage.getItem("token");
        
  //       if (!storedToken) {
  //         console.log("No token found in localStorage");
  //         if (location.pathname !== '/login' && location.pathname !== '/signup') {
  //           navigate("/login");
  //         }
  //         return;
  //       }
        
  //       // setToken(storedToken);
  //     } catch (error) {
  //       console.error("Auth check error:", error);
  //     } finally {
  //       setLoading(false);
  //     }
  //   };
  
  //   checkAuth();
  // }, []);
  
  // useEffect(() => {
  //   const getUserData = async () => {
  //     if (!token) return;
      
  //     setLoading(true);
  //     try {
  //       const userFetched = await fetchUser(); 
        
  //       if (!userFetched) {
  //         console.log("Failed to fetch user, trying token refresh");
  //         const refreshSuccessful = await handleRefreshToken();
          
  //         if (!refreshSuccessful && location.pathname !== '/login' && location.pathname !== '/signup') {
  //           console.log("Refresh failed, redirecting to login");
  //           logout();
  //         }
  //       }
  //     } catch (error) {
  //       console.error("User fetch error:", error);
  //     } finally {
  //       setLoading(false);
  //     }
  //   };
    
  //   getUserData();
  // }, [token]);
  // // const fetchUser = async () => {
  // //   try {
  // //     const res = await fetch(`${config.baseURL}/getUser`, {
  // //       headers: { Authorization: `Bearer ${token}` },
  // //     });
  // //     if (res.status === 401) {
  // //       await handleRefreshToken(); // רענן את הטוקן אם מתקבלת שגיאה 401
  // //       return;
  // //     }
  // //     if (!res.ok) throw new Error("Failed to fetch user");
  // //     const data = await res.json();
  // //     setUser(data);
  // //   } catch {
  // //     logout(); // אם לא ניתן לרענן את הטוקן, בצע התנתקות
  // //   }
  // // };


  // const fetchUser = async (shouldRetryOnRefresh = true) => {
    
  //   try {
  //     console.log("Fetching user data with token:", token?.substring(0, 15) + "...");
      
  //     const res = await fetch(`${config.baseURL}/getUser`, {
  //       headers: { Authorization: `Bearer ${token}` },
  //     });
      
  //     if (res.status === 401 && shouldRetryOnRefresh) {
  //       console.log("401 received during fetchUser, trying to refresh token");
  //       const refreshSuccessful = await handleRefreshToken();
  //       if (refreshSuccessful) {
  //         // במקום לקרוא לפונקציה עצמה, נקרא לגרסה חדשה שלה עם דגל שמונע ניסיון נוסף
  //         return fetchUser(false); // ניסיון אחד בלבד אחרי רענון
  //       }
  //       throw new Error("Authentication failed");
  //     }
      
  //     if (!res.ok) {
  //       console
        
  //       throw new Error(`Failed to fetch user - Status: ${res.status}`)};
      
  //     const data = await res.json();
  //     setUser(data);
  //     return true;
  //   } catch (error) {
  //     console.error("Error fetching user:", error);
  //     return false;
  //   }
  // };
  // const fetchUser = async () => {
  //   try {
  //     console.log("Fetching user data with token:", token?.substring(0, 15) + "...");
      
  //     const res = await fetch(`${config.baseURL}/getUser`, {
  //       headers: { Authorization: `Bearer ${token}` },
  //     });
      
  //     if (res.status === 401) {
  //       console.log("401 received during fetchUser, trying to refresh token");
  //       // נסה לרענן את הטוקן ואז נסה שוב לטעון משתמש
  //       const refreshSuccessful = await handleRefreshToken();
  //       if (refreshSuccessful) {
  //         return fetchUser(); // נסה שוב לאחר רענון
  //       }
  //       throw new Error("Authentication failed");
  //     }
      
  //     if (!res.ok) throw new Error("Failed to fetch user");
      
  //     const data = await res.json();
  //     setUser(data);
  //     return true;
  //   } catch (error) {
  //     console.error("Error fetching user:", error);
  //     // אל תתנתק אוטומטית, רק תחזיר שגיאה
  //     return false;
  //   }
  // };
  const logout = useCallback(() => {
    localStorage.removeItem('token');
    localStorage.removeItem("refreshToken");
    setToken(null);
    setRefreshToken(null);
    setUser(null);
    navigate('/login');
  }, [navigate]);

  const handleRefreshToken = useCallback(async (): Promise<boolean> => {
    console.log("Refreshing token...");
    const currentRefreshToken = refreshToken || localStorage.getItem("refreshToken");
    
    if (!currentRefreshToken) {
      console.log("No refresh token found");
      return false;
    }
    
    try {
      const res = await fetch(`${config.baseURL}/refreshToken`, {
        
        method: "POST",
        headers: { 
          Authorization: `Bearer ${token}`,
          "Content-Type": "application/json" 
        },
        body: JSON.stringify({ refreshToken: currentRefreshToken }),
      });

      if (!res.ok) {
        console.log(`Refresh token request failed with status: ${res.status}`);
        return false;
      }
      
      const data = await res.json();
      console.log(`Token refresh response: token? ${data.token ? true : false}`);
      
      setToken(data.token);
      localStorage.setItem("token", data.token);
      
      if (data.refreshToken) {
        setRefreshToken(data.refreshToken);
        localStorage.setItem("refreshToken", data.refreshToken);
      }
      
      return true;
    } catch (error) {
      console.error(`Token refresh failed: status code `, error);
      return false;
    }
  }, [token, refreshToken]);

  const fetchUser = useCallback(async (shouldRetryOnRefresh = true) => {
    if (!token) return false;
    
    try {
      console.log("Fetching user data with token:");
      
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

  // Handle initial authentication check
  useEffect(() => {
    const checkAuthentication = async () => {
      if (!token) return;
      
      setLoading(true);
      try {
        const userFetched = await fetchUser();
        
        if (!userFetched && 
            location.pathname !== '/login' && 
            location.pathname !== '/signup') {
          console.log("Failed to fetch user, redirecting to login");
          logout();
        }
      } catch (error) {
        console.error("User fetch error:", error);
      } finally {
        setLoading(false);
      }
    };
    
    checkAuthentication();
  }, [token, fetchUser, logout, location.pathname]);


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
      
      setUser(data);
      setToken(data.token);
      setRefreshToken(data.refreshToken);
      
      localStorage.setItem("token", data.token);
      localStorage.setItem("refreshToken", data.refreshToken);
      
      console.log("Navigating to dashboard...");
      navigate("/dashboard");
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
  return timeRemaining > 0 && timeRemaining < 60000*3;
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
    <AuthContext.Provider value={{ user,loading, login, register, logout ,handleRefreshToken,updateUser,deleteUser,changePassword}}>
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






