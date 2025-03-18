import React, { useState, useEffect } from "react";
import config from "../config";

const AddCategory = () => {
  const [categories, setCategories] = useState<string[]>([]);
  const [selectedCategory, setSelectedCategory] = useState("");
  const [preference, setPreference] = useState("");

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
    console.log("Category added:", selectedCategory);
    console.log("Preference:", preference);
  };

  return (
    <div>
      <h1>Add Category</h1>
      <form onSubmit={handleSubmit}>
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
        <br />
        <label>
          Preference:
          <input
            type="text"
            value={preference}
            onChange={(e) => setPreference(e.target.value)}
          />
        </label>
        <br />
        <button type="submit">Add Category</button>
      </form>
    </div>
  );
};

export default AddCategory;