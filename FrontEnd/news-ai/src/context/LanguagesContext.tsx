import React, { createContext, useContext, useState, useEffect } from "react";
import config from "../config";


interface LanguagesContextType {
  MyLanguages: string[];
  setMyLanguages: React.Dispatch<React.SetStateAction<string[]>>;
  refreshLanguages: () => Promise<void>;
  languages: string[];
  maxLanguages: number;
}

const LanguagesContext = createContext<LanguagesContextType>({
  MyLanguages: [],
  setMyLanguages: () => {}, // Default no-op function
  refreshLanguages: async () => {},
  languages: [],
  maxLanguages: 0,
});

export const LanguagesProvider: React.FC<{ children: React.ReactNode }> = ({ children }) => {
  const [myLanguages, setMyLanguages] = useState<string[]>([]);
    const [languages, setLanguages] = useState<string[]>([]);
    const [maxLanguages, setMaxLanguages] = useState<number>(0);
  


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

  const getLanguages = async () =>{
    const token = localStorage.getItem("token");
    if (!token) {
      console.error("No token provided for getLanguages");
      return;
    }
  
    console.log("Fetching languages with token:", token ? "Token exists" : "No token");
    
    fetch(`${config.baseURL}/getLanguages`, {
      method: "GET",
      headers: {
        "Content-Type": "application/json",
        "Authorization": `Bearer ${token}`,
      },
    })
      .then(async response => {
        console.log("Languages response status:", response.status);
        console.log("Languages response headers:", Object.fromEntries(response.headers.entries()));
        
        if (!response.ok) {
          const errorText = await response.text();
          console.error("Server error response:", errorText.substring(0, 150));
          throw new Error(`Error ${response.status}: ${response.statusText}`);
        }
        
        
        const text = await response.text();
        console.log("Raw response:", text.substring(0, 100));
        
        
        if (!text) throw new Error("Empty response");
        try {
          const data = JSON.parse(text);
          console.log("Languages parsed:", data);
          setLanguages(data);
        } catch (parseError) {
          console.error("JSON parse error:", parseError);
          throw parseError;
        }
      })
      .catch(error => console.error("Error fetching languages:", error));
  }
  
  const getMaxLanguages= async () => {
    const token = localStorage.getItem("token");
    console.log("maxLaguages call");
    if (!token) {
      console.error("No token provided for getMaxLanguages");
      return;
    }
  
    console.log("Fetching max languages with token:", token ? "Token exists" : "No token");
    
    fetch(`${config.baseURL}/maximumLanguage`, {
      method: "GET",
      headers: {
        "Content-Type": "application/json",
        "Authorization": `Bearer ${token}`,
      },
    })
      .then(async response => {
        console.log("Max languages response status:", response.status);
        
        if (!response.ok) {
          const errorText = await response.text();
          console.error("Server error response:", errorText.substring(0, 150));
          throw new Error(`Error ${response.status}: ${response.statusText}`);
        }
        
        
        const text = await response.text();
        console.log("Raw response:", text.substring(0, 100));
        
       
        
        if (!text) throw new Error("Empty response");
        try {
          const data = JSON.parse(text);
          console.log("Max languages parsed:", data);
          setMaxLanguages(data);
        } catch (parseError) {
          console.error("JSON parse error:", parseError);
          throw parseError;
        }
      })
      .catch(error => console.error("Error fetching max languages:", error));
  }
  

  useEffect(() => {
    console.log("LanguagesProvider mounted");
    refreshLanguages();
    getLanguages();
    getMaxLanguages();  
  }, []);

  return (
    <LanguagesContext.Provider value={{ MyLanguages: myLanguages, setMyLanguages: setMyLanguages, refreshLanguages,languages,maxLanguages }}>
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
