import React, { createContext, useContext, useState, useEffect } from "react";
import config from "../config";
import { useAuth } from "../context/AuthContext";

interface LanguagesContextType {
  MyLanguages: string[];
  setMyLanguages: React.Dispatch<React.SetStateAction<string[]>>;
}

const LanguagesContext = createContext<LanguagesContextType | undefined>(undefined);

export const LanguagesProvider: React.FC<{ children: React.ReactNode }> = ({ children }) => {
  const [myLanguages, setMyLanguages] = useState<string[]>([]);
  const { handleRefreshToken } = useAuth();

  useEffect(() => {
    const token = localStorage.getItem("token");
    if (!token) return;

    fetch(`${config.baseURL}/getMyLanguages`, {
      method: "GET",
      headers: {
        "Content-Type": "application/json",
        Authorization: `Bearer ${token}`,
      },
    })
      .then((response) => {
        if (response.status === 401) {
          handleRefreshToken();
          return null;
        }
        if (!response.ok) {
          throw new Error("Failed to fetch languages");
        }
        return response.json();
      })
      .then((data) => {
        if (data) setMyLanguages(data);
      })
      .catch((error) => console.error("Error fetching languages:", error));
  }, []);

  return (
    <LanguagesContext.Provider value={{ MyLanguages: myLanguages, setMyLanguages: setMyLanguages }}>
      {children}
    </LanguagesContext.Provider>
  );
};

export const useLanguages = () => {
  const context = useContext(LanguagesContext);
  if (!context) {
    throw new Error("useLanguages must be used within a LanguagesProvider");
  }
  return context;
};
