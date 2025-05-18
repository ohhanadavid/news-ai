import React, { createContext, useContext, useState, useEffect } from "react";
import config from "@/config";
import { useAuth } from "./AuthContext";

interface NewsView {
  title: string;
  url: string;
}

interface NewsContextType {
  newsView: NewsView[];
  fetchNews: () => Promise<void>;
}

const NewsContext = createContext<NewsContextType | undefined>(undefined);

export const NewsProvider: React.FC<{ children: React.ReactNode }> = ({ children }) => {
  const [newsView, setNewsView] = useState<NewsView[]>([]);
  const { tokenStr } = useAuth();

  const fetchNews = async () => {
    try {
      const token = localStorage.getItem(tokenStr);
      if (!token){
        console.log("Token not found fetchNews");
        return
      };

      const res = await fetch(`${config.baseURL}/getNewsView`, {
        method: "GET",
        headers: {
          "Content-Type": "application/json",
          Authorization: `Bearer ${token}`,
        },
      });

      if (!res.ok) {
        console.log(`Error with status ${res.status}`);
        throw new Error("Failed to fetch news for view");
      }
      console.log("fetching newsView from server");
      const newsViewRes = await res.json();
      console.log("newsView from server", newsViewRes);
      setNewsView(newsViewRes);
    } catch (error) {
      console.error("Error fetching news:", error);
    }
  };

  useEffect(() => {
    console.log("useEffect in NewsProvider");
    console.log("newsView", newsView?true:false);
    console.log("newsView length", newsView.length);
    const fetchData = async () => {
      if (newsView.length === 0) {
        console.log("fetching newsView");
        await fetchNews();
        console.log("resive newsView");
      }
    };
     fetchData();
  }, []);

  return (
    <NewsContext.Provider value={{ newsView, fetchNews }}>
      {children}
    </NewsContext.Provider>
  );
};

export const useNews = (): NewsContextType => {
  const context = useContext(NewsContext);
  if (!context) {
    throw new Error("useNews must be used within a NewsProvider");
  }
  return context;
};