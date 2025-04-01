import React, { useState, useEffect } from "react";
import config from "../config";
import { useNavigate, Link } from "react-router-dom";
import { useAuth } from "../context/AuthContext"; 
import { MdCancel } from "react-icons/md";
import { useCategory } from "../context/CategoryContext";

const AddCategory = () => {
  
  const [selectedCategory, setSelectedCategory] = useState("");
  const [preference, setPreference] = useState("");
  const navigate = useNavigate();
  const { handleRefreshToken } = useAuth();
  const [error, setError] = useState<string | null>(null);
  const { categories } = useCategory();


  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();

    const token = localStorage.getItem("token");
    const res = await fetch(`${config.baseURL}/saveCategory`, {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
        Authorization: `Bearer ${token}`,
      },
      body: JSON.stringify({
        category: {
          preference,
          category: selectedCategory,
        },
      }),
    });

    if (res.status === 401) {
      await handleRefreshToken();
      return;
    }
    if (res.status === 409) {
      setError("Category already exists");
      return;
    }
    if (res.status === 400) {
      setError("Somthing went wrong, please try again later");
      return;
    }
    if (!res.ok) throw new Error("Failed to save category");

    navigate("/dashboard");
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
        {error && <p className="text-red-500">{error}</p>}
        <br />
        <button type="submit">Add Category</button>
        <div style={{ marginTop: "10px" }}>
        <button type="button" onClick={() => navigate("/dashboard")}
        style={{
                background: "none",
                border: "none",
                fontSize: "24px",
                cursor: "pointer",
                color: "red",
              }}
              aria-label="Cancel"
            >
        <MdCancel />
        </button>
      </div>
      </form>
    </div>
  );
};

export default AddCategory;