import React, { createContext, useContext, useState, useEffect } from "react";
import config from "../config";
import { useAuth } from "./AuthContext";


type CategoryType = Map<string, string[]>; // Updated to use Map for categories

interface CategoryContextType {
  categories: string[]; // Available categories
  myCategories: CategoryType; // User's categories as a Map
  setCategories: React.Dispatch<React.SetStateAction<string[]>>;
  refreshCategories: () => Promise<void>;
}

const CategoryContext = createContext<CategoryContextType>({
  myCategories: new Map(), // Default value as an empty Map
  categories: [],
  setCategories: () => {}, // Provide a default no-op function
  refreshCategories: async () => {},
});

export const CategoryProvider: React.FC<{ children: React.ReactNode }> = ({ children }) => {
  const [categories, setCategories] = useState<string[]>([]);
  const [myCategories, setMyCategories] = useState<CategoryType>(new Map());
  const { tokenStr } = useAuth();
 

  const refreshCategories = async () => {
    try {
      console.log(tokenStr);
      const token = localStorage.getItem(tokenStr);
      if (!token) return;

      // Fetch user categories
      const userCategoriesResponse = await fetch(`${config.baseURL}/myCategories`, {
        method: "GET",
        headers: {
          "Content-Type": "application/json",
          Authorization: `Bearer ${token}`,
        },
      });

      if (!userCategoriesResponse.ok) {
        throw new Error("Failed to fetch user categories");
      }

      const userCategoriesData = await userCategoriesResponse.json();

      // Convert the response to a Map
      const userCategoriesMap = new Map<string, string[]>(
        Object.entries(userCategoriesData)
      );
      setMyCategories(userCategoriesMap);

      // Fetch available categories
      const availableCategoriesResponse = await fetch(`${config.baseURL}/getCategories`, {
        method: "GET",
        headers: {
          "Content-Type": "application/json",
          Authorization: `Bearer ${token}`,
        },
      });

      if (!availableCategoriesResponse.ok) {
        throw new Error("Failed to fetch available categories");
      }

      const availableCategoriesData = await availableCategoriesResponse.json();
      setCategories(availableCategoriesData);
    } catch (error) {
      console.error("Error refreshing categories:", error);
    }
  };

  useEffect(() => {
    console.log("Refreshing categories on mount...");
    refreshCategories();
  }, []);

  return (
    <CategoryContext.Provider
      value={{ myCategories, categories, setCategories, refreshCategories }}
    >
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
