import React, { useState } from "react";
import config from "../config";
import { useCategory } from "../context/CategoryContext";
import { useNavigate } from "react-router-dom";
import { useAuth } from "../context/AuthContext";
import { Dialog, DialogContent, DialogHeader, DialogTitle } from "@/components/ui/dialog";
import { Button } from "@/components/ui/button";

interface NewsSubscriptionProps {
  isOpen: boolean;
  onClose: () => void;
}


const NewsSubscription: React.FC <NewsSubscriptionProps>= ({ isOpen, onClose }) => {
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
        getLatestNews(token, deliveryMethod.join(","), articleCount, handleRefreshToken);
        break;
      case "getLatestListNewsByCategories":
        getLatestNewsByMyCategories(token, deliveryMethod.join(","), articleCount, handleRefreshToken);
        break;
      case "getLatestNewsByCategory":
        getLatestNewsByCategory(token, deliveryMethod.join(","), selectedCategory, articleCount, handleRefreshToken);
        break;
      default:
        break;
    }

    navigate("/dashboard"); // Redirect to the dashboard after submission
  };

  const handleDeliveryMethodChange = (method: string) => {
    setDeliveryMethod((prevMethods) =>
      prevMethods.includes(method)
        ? prevMethods.filter((m) => m !== method)
        : [...prevMethods, method]
    );
  };

  return (
    <Dialog open={isOpen} onOpenChange={onClose}>
    
      <DialogContent className="max-w-[50%]">
        <DialogHeader>
          <DialogTitle className="font-algerian text-3xl !font-['algerian']">News Subscription</DialogTitle>
        </DialogHeader>
        <form onSubmit={handleSubmit}>
          <label className="font-pattaya text-3xl !font-['pattaya']">
            Option:
            
          </label>
          <select
              value={selectedOption}
              onChange={(e) => setSelectedOption(e.target.value)} 
              style={{ marginLeft: "15px", fontSize: "20px" }}
            >
              <option value="getLatestNews">Get Latest News</option>
              <option value="getLatestListNewsByCategories">Get Latest List News By Categories</option>
              <option value="getLatestNewsByCategory">Get Latest News By Category</option>
            </select>
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
          <label className="font-pattaya text-3xl !font-['pattaya']">
            Delivery Methods:
            
          </label>
          <div>
              <label >
                <input
                  type="checkbox"
                  value="email"
                  checked={deliveryMethod.includes("email")}
                  onChange={() => handleDeliveryMethodChange("email")}
                  style={{ marginRight: "5px" }}
                />
                Email
              </label>
              <label style={{ marginLeft: "15px" }}>
                <input
                  type="checkbox"
                  value="sms"
                  checked={deliveryMethod.includes("sms")}
                  onChange={() => handleDeliveryMethodChange("sms")}
                  style={{ marginRight: "5px" }}
                />
                SMS
              </label>
              <label style={{ marginLeft: "15px" }}>
                <input
                  type="checkbox"
                  value="whatsapp"
                  checked={deliveryMethod.includes("whatsapp")}
                  onChange={() => handleDeliveryMethodChange("whatsapp")}
                  style={{ marginRight: "5px" }}
                />
                WhatsApp
              </label>
            </div>
          <br />
          <label>
            Number of Articles (max 10):
            
          </label>
          <input
          style={{ border: "1px solid #ccc", borderRadius: "4px", padding: "5px", marginLeft: "15px" ,alignItems: "center"}}
              type="number"
              value={articleCount}
              onChange={(e) =>
                setArticleCount(Math.min(10, Math.max(1, Number(e.target.value))))
              }
              min="1"
              max="10"
            />
          <br />
          <Button type="submit" className="bg-green-600 hover:bg-green-700" style={{ marginTop: "15px" }}>
            Subscribe
          </Button>
        </form>
      </DialogContent>
    </Dialog>
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
  if (deliveryMethod) {
    url += `&sendOption=${encodeURIComponent(deliveryMethod)}`;
  }
  if (selectedCategory) {
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
