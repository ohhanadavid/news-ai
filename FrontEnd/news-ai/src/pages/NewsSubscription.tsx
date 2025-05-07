import React, { useState } from "react";
import config from "../config";
import { useCategory } from "../context/CategoryContext";

import { useAuth } from "../context/AuthContext";
import { Dialog, DialogContent, DialogHeader, DialogTitle } from "@/components/ui/dialog";
import { Button } from "@/components/ui/button";
import { toast } from "sonner";

interface NewsSubscriptionProps {
  isOpen: boolean;
  onClose: () => void;
}


const NewsSubscription: React.FC <NewsSubscriptionProps>= ({ isOpen, onClose }) => {
  const { categories } = useCategory();
  const [selectedOption, setSelectedOption] = useState("getLatestNews");
  const [selectedCategory, setSelectedCategory] = useState("");
  const [deliveryMethod, setDeliveryMethod] = useState<string>("");
  const [articleCount, setArticleCount] = useState(1);
  const { handleRefreshToken } = useAuth();
  

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
        getLatestNews(token, deliveryMethod, articleCount, handleRefreshToken);
        break;
      case "getLatestListNewsByCategories":
        getLatestNewsByMyCategories(token, deliveryMethod, articleCount, handleRefreshToken);
        break;
      case "getLatestNewsByCategory":
        getLatestNewsByCategory(token, deliveryMethod, selectedCategory, articleCount, handleRefreshToken);
        break;
      default:
        break;
    }
    massageSucces(deliveryMethod);
    onClose(); 
    
  };

  return (
    <Dialog open={isOpen} onOpenChange={onClose}>
    
      <DialogContent className="w-fit max-w-full">
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
            <label>
              <input
                type="radio"
                name="deliveryMethod"
                value="email"
                checked={deliveryMethod === "email"}
                onChange={() => setDeliveryMethod("email")}
                style={{ marginRight: "5px" }}
              />
              Email
            </label>
            <label style={{ marginLeft: "15px" }}>
              <input
                type="radio"
                name="deliveryMethod"
                value="sms"
                checked={deliveryMethod === "sms"}
                onChange={() => setDeliveryMethod("sms")}
                style={{ marginRight: "5px" }}
              />
              SMS
            </label>
            <label style={{ marginLeft: "15px" }}>
              <input
                type="radio"
                name="deliveryMethod"
                value="whatsapp"
                checked={deliveryMethod === "whatsapp"}
                onChange={() => setDeliveryMethod("whatsapp")}
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
  handleRefreshToken: () => Promise<boolean>,
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
    .catch((error) => {console.error("Error fetching news:", error);
  massageError(error.message);}); 
}

function getLatestNewsByMyCategories(
  token: string,
  deliveryMethod: string,
  articleCount: number,
  handleRefreshToken: () => Promise<boolean>,
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
    .catch((error) => {console.error("Error fetching news:", error);
      massageError(error.message);}); 
}

function getLatestNewsByCategory(
  token: string,
  deliveryMethod: string,
  selectedCategory: string,
  articleCount: number,
  handleRefreshToken: () => Promise<boolean>,
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
    .catch((error) => {console.error("Error fetching news:", error);
      massageError(error.message);}); 
}

async function  massageError(error:string) {
  toast.error("ERROR", {
    description: error,
    duration: 5000,

  });
}

async function  massageSucces(optionDelivery:string) {
  toast.success("News send!!", {
    description: `News send to ${optionDelivery}`,
    duration: 5000,

  });
}
