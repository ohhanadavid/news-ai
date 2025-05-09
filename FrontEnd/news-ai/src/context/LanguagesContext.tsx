import React, { createContext, useContext, useState, useEffect } from "react";
import config from "../config";


interface LanguagesContextType {
  MyLanguages: string[];
  setMyLanguages: React.Dispatch<React.SetStateAction<string[]>>;
  refreshLanguages: () => Promise<void>;
}

const LanguagesContext = createContext<LanguagesContextType>({
  MyLanguages: [],
  setMyLanguages: () => {}, // Default no-op function
  refreshLanguages: async () => {},
});

export const LanguagesProvider: React.FC<{ children: React.ReactNode }> = ({ children }) => {
  const [myLanguages, setMyLanguages] = useState<string[]>([]);
  


  const refreshLanguages = async () => {
    try {
      const token = localStorage.getItem("token");
      if (!token) {
        console.error("No token found");
        return;}
      console.log("geting languages");
      const response = await fetch(`${config.baseURL}/getMyLanguages`, {
        method: "GET",
        headers: {
          "Content-Type": "application/json",
          Authorization: `Bearer ${token}`,
        },
      });

      if (!response.ok) {
        throw new Error("Failed to fetch languages");
      }

      const data = await response.json();
      setMyLanguages(data);
    } catch (error) {
      console.error("Error refreshing languages:", error);
    }
  };

  useEffect(() => {
    console.log("LanguagesProvider mounted");
    refreshLanguages();
  }, []);

  return (
    <LanguagesContext.Provider value={{ MyLanguages: myLanguages, setMyLanguages: setMyLanguages, refreshLanguages }}>
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
