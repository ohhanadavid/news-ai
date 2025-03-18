import React, { useState, useEffect } from "react";
import config from "../config";

const NewsSubscription = () => {
  const [categories, setCategories] = useState<string[]>([]);
  const [selectedOption, setSelectedOption] = useState("getLatestNews");
  const [selectedCategory, setSelectedCategory] = useState("");
  const [deliveryMethod, setDeliveryMethod] = useState<string[]>([]);
  const [articleCount, setArticleCount] = useState(1);

  useEffect(() => {
    // Fetch the list of categories from the backend
    fetch(`${config.baseURL}/getCategories`)
      .then(response => response.json())
      .then(data => {
        setCategories(data);
      })
      .catch(error => {
        console.error("There was an error fetching the categories!", error);
      });
  }, []);

  const handleSubmit = (e: React.FormEvent) => {
    e.preventDefault();
    // Handle form submission logic here
    console.log("Option selected:", selectedOption);
    console.log("Category selected:", selectedCategory);
    console.log("Delivery methods:", deliveryMethod);
    console.log("Article count:", articleCount);
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