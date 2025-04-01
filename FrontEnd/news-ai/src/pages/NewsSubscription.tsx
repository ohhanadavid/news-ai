import React, { useState, useEffect } from "react";
import config from "../config";
import { useCategory } from "../context/CategoryContext";
import { useNavigate, Link } from "react-router-dom";
import { useAuth } from "../context/AuthContext"; 

const NewsSubscription = () => {
  const { categories } = useCategory();
  const [selectedOption, setSelectedOption] = useState("getLatestNews");
  const [selectedCategory, setSelectedCategory] = useState("");
  const [deliveryMethod, setDeliveryMethod] = useState<string[]>([]);
  const [articleCount, setArticleCount] = useState(1);
   const { handleRefreshToken } = useAuth();
   const navigate = useNavigate();


  const handleSubmit = (e: React.FormEvent) => {
    e.preventDefault();
    console.log("Option selected:", selectedOption);
    console.log("Category selected:", selectedCategory);
    console.log("Delivery methods:", deliveryMethod);
    console.log("Article count:", articleCount);
    const token = localStorage.getItem("token");
    if (!token) return;

    // Call the appropriate function based on the selected option
    switch (selectedOption) {   
      case "getLatestNews":
        getLatestNews(token, deliveryMethod.join(','), articleCount, handleRefreshToken);
        break;
      case "getLatestListNewsByCategories":
        getLatestNewsByMyCategories(token, deliveryMethod.join(','), articleCount, handleRefreshToken);
        break;
      case "getLatestNewsByCategory":
        getLatestNewsByCategory(token, deliveryMethod.join(','), selectedCategory, articleCount, handleRefreshToken);
        break;
      default:
        break;
    }

   
    navigate("/dashboard"); // Redirect to the dashboard after submission
  };

  const handleDeliveryMethodChange = (method: string) => {
    setDeliveryMethod(prevMethods =>
      prevMethods.includes(method)
        ? prevMethods.filter(m => m !== method)
        : [...prevMethods, method]
    );
  };

  return (
    <div>
      <h1>News Subscription</h1>
      <form onSubmit={handleSubmit}>
        <label>
          Option:
          <select
            value={selectedOption}
            onChange={(e) => setSelectedOption(e.target.value)}
          >
            <option value="getLatestNews">Get Latest News</option>
            <option value="getLatestListNewsByCategories">Get Latest List News By Categories</option>
            <option value="getLatestNewsByCategory">Get Latest News By Category</option>
          </select>
        </label>
        <br />
        {selectedOption === "getLatestNewsByCategory" && (
          <label>
            Category:
            <select
              value={selectedCategory}
              onChange={(e) => setSelectedCategory(e.target.value)}
            >
              <option value="">Select a category</option>
              {categories.map((category, index) => (
                <option key={index} value={category}>
                  {category}
                </option>
              ))}
            </select>
          </label>
        )}
        <br />
        <label>
          Delivery Methods:
          <div>
            <label>
              <input
                type="checkbox"
                value="email"
                checked={deliveryMethod.includes("email")}
                onChange={() => handleDeliveryMethodChange("email")}
              />
              Email
            </label>
            <label>
              <input
                type="checkbox"
                value="sms"
                checked={deliveryMethod.includes("sms")}
                onChange={() => handleDeliveryMethodChange("sms")}
              />
              SMS
            </label>
            <label>
              <input
                type="checkbox"
                value="whatsapp"
                checked={deliveryMethod.includes("whatsapp")}
                onChange={() => handleDeliveryMethodChange("whatsapp")}
              />
              WhatsApp
            </label>
          </div>
        </label>
        <br />
        <label>
          Number of Articles (max 10):
          <input
            type="number"
            value={articleCount}
            onChange={(e) => setArticleCount(Math.min(10, Math.max(1, Number(e.target.value))))}
            min="1"
            max="10"
          />
        </label>
        <br />
        <button type="submit">Subscribe</button>
      </form>
    </div>
  );
};

export default NewsSubscription;

function getLatestNews(
  token: string,
  deliveryMethod: string,
  articleCount: number,
  handleRefreshToken: () => Promise<void>,
  
) {
  // Construct the base URL
  let url = `${config.baseURL}/getLatestNews?numberOfArticles=${articleCount}`;

  // Add category to the query parameters if the selected option requires it
  if (deliveryMethod) {
    url += `&sendOption=${encodeURIComponent(deliveryMethod)}`;
  }
 
  fetch(url, {
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
        throw new Error("Failed to fetch news");
      }
      return response.text(); // Use .text() to log the raw response
    })
    
    .catch((error) => console.error("Error fetching news:", error));
}

function getLatestNewsByMyCategories(
  token: string,
  deliveryMethod: string,
  articleCount: number,
  handleRefreshToken: () => Promise<void>,
  
) {
  // Construct the base URL
  let url = `${config.baseURL}/getLatestListNewsByCategories?numberOfArticles=${articleCount}`;

  // Add category to the query parameters if the selected option requires it
  if (deliveryMethod ) {
    url += `&sendOption=${encodeURIComponent(deliveryMethod)}`;
  }

  fetch(url, {
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
        throw new Error("Failed to fetch news");
      }
      return response.text(); // Use .text() to log the raw response
    })
    
    .catch((error) => console.error("Error fetching news:", error));
}

function getLatestNewsByCategory(
  token: string,
  deliveryMethod: string,
  selectedCategory: string,
  articleCount: number,
  handleRefreshToken: () => Promise<void>,
) {
  // Construct the base URL
  let url = `${config.baseURL}/getLatestNewsByCategory?numberOfArticles=${articleCount}`;

  // Add category to the query parameters if the selected option requires it
  if (deliveryMethod ) {
    url += `&sendOption=${encodeURIComponent(deliveryMethod)}`;
  }
  if (selectedCategory ) {
    url += `&category=${encodeURIComponent(selectedCategory)}`;
  }

  fetch(url, {
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
        throw new Error("Failed to fetch news");
      }
      return response.text(); // Use .text() to log the raw response
    })
   
    .catch((error) => console.error("Error fetching news:", error));
}
