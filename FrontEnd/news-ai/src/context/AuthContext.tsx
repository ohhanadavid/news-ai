import React, { createContext, useContext, useState, useEffect, ReactNode, FormEvent } from "react";
import { useNavigate, Navigate } from "react-router-dom";
import config from "../config"; // Adjust the path if necessary

interface AuthContextType {
  user: any;
  login: (userIdentifier: string, password: string) => Promise<void>;
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
      // Fetch user data or set user state
      fetchUser();
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
        await handleRefreshToken();
        return;
      }
      if (!res.ok) throw new Error("Failed to fetch user");
      const data = await res.json();
      setUser(data);
    } catch {
      logout();
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
    console.log(data);
    setUser(data.user);
    setToken(data.token);
    setRefreshToken(data.refreshToken);
    localStorage.setItem("token", data.token);
    localStorage.setItem("refreshToken", data.refreshToken);
    navigate("/dashboard");
  };

  const handleRefreshToken = async () => {
    if (!refreshToken) return logout();
    try {
      const res = await fetch(`${config.baseURL}/refreshToken`, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ refreshToken }),
      });
      if (!res.ok) throw new Error("Refresh token failed");
      const data = await res.json();
      setToken(data.token);
      localStorage.setItem("token", data.token);
    } catch {
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


  return (
    <AuthContext.Provider value={{ user, login, register, logout ,handleRefreshToken,updateUser}}>
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

const PrivateRoute = ({ children }: { children: ReactNode }) => {
  const { user } = useAuth();

  return user ? children : <Navigate to="/login" replace />;
};


