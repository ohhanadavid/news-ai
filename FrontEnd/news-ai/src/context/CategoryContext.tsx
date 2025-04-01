import React, { createContext, useContext, useState, useEffect } from "react";
import config from "../config";
import { useAuth } from "../context/AuthContext";

interface CategoryContextType {
  categories: string[];
  setCategories: React.Dispatch<React.SetStateAction<string[]>>;
}

const CategoryContext = createContext<CategoryContextType | undefined>(undefined);

export const CategoryProvider: React.FC<{ children: React.ReactNode }> = ({ children }) => {
  const [categories, setCategory] = useState<string[]>([]);
  const { handleRefreshToken } = useAuth();

  useEffect(() => {
    const token = localStorage.getItem("token");
    if (!token) return;

    fetch(`${config.baseURL}/getCategories`, {
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
          throw new Error("Failed to fetch Category");
        }
        return response.json();
      })
      .then((data) => {
        if (data) setCategory(data);
      })
      .catch((error) => console.error("Error fetching Category:", error));
  }, []);

  return (
    <CategoryContext.Provider value={{ categories: categories, setCategories: setCategory }}>
      {children}
    </CategoryContext.Provider>
  );
};

export const useCategory = () => {
  const context = useContext(CategoryContext);
  if (!context) {
    throw new Error("useCategory must be used within a CategoryProvider");
  }
  return context;
};
