

import  { useEffect, useState } from "react";
import { useCategory } from "../context/CategoryContext";
import { FaChevronDown, FaChevronUp } from "react-icons/fa";
import { MdCategory, MdCheckCircle, MdDelete } from "react-icons/md";
import config from "../config";

const MyCategories = () => {
  const { myCategories, refreshCategories } = useCategory(); // Access categories from context
  const [visibleCategories, setVisibleCategories] = useState<Set<string>>(new Set());


  useEffect(() => {
    if (!myCategories) {
      console.log("myCategories is null");
      refreshCategories();
    } else 
      console.log("myCategories is not null");
    
  }, [myCategories, refreshCategories]); // Check if myCategories is null or empty

  // Toggle visibility of a category
  const toggleCategoryVisibility = (category: string) => {
    setVisibleCategories((prev) => {
      const updated = new Set(prev);
      if (updated.has(category)) {
        updated.delete(category);
      } else {
        updated.add(category);
      }
      return updated;
    });
  };

  // Handle deleting a category
  const handleDeleteCategory = async (category: string) => {
    try {
      const token = localStorage.getItem("token");
      const res = await fetch(`${config.baseURL}/deleteCategory?category=${encodeURIComponent(category)}`, {
        method: "DELETE",
        headers: {
          "Content-Type": "application/json",
          Authorization: `Bearer ${token}`,
        },
      });

      if (!res.ok) {
        throw new Error("Failed to delete category");
      }

      // Refresh categories after deletion
      await refreshCategories();
    } catch (error) {
      console.error("Error deleting category:", error);
    }
  };

  return (
    <div>
      {myCategories.size > 0 ? ( // Check if there are categories
        <ul style={{ display: "flex", flexDirection: "column", gap: "10px" }}>
          {Array.from(myCategories.entries()).map(([category, preferences], index) => (
            <li key={index} style={{ display: "block", marginBottom: "10px" }}>
              <div style={{ display: "flex", alignItems: "center" }}>
                <MdCategory style={{ marginRight: "10px" }} />
                <strong>{category}</strong>
                <button
                  onClick={() => handleDeleteCategory(category)}
                  style={{
                    background: "none",
                    border: "none",
                    fontSize: "24px",
                    cursor: "pointer",
                    marginLeft: "10px",
                  }}
                  aria-label="delete category"
                >
                  <MdDelete />
                </button>
                <button
                  style={{
                    background: "none",
                    border: "none",
                    fontSize: "24px",
                    cursor: "pointer",
                    marginLeft: "10px",
                  }}
                  aria-label="Toggle visibility"
                  onClick={() => toggleCategoryVisibility(category)}
                >
                  {visibleCategories.has(category) ? <FaChevronUp /> : <FaChevronDown />}
                </button>
              </div>
              {visibleCategories.has(category) && (
                <div style={{ marginLeft: "20px" }}>
                  <ul style={{ display: "flex", flexDirection: "column", gap: "5px" }}>
                    {preferences.map((preference, subIndex) => (
                      <li key={subIndex} style={{ display: "flex", alignItems: "center", gap: "10px" }}>
                        <MdCheckCircle />
                        {preference}
                      </li>
                    ))}
                  </ul>
                </div>
              )}
            </li>
          ))}
        </ul>
      ) : (
        <p>No categories found.</p>
      )}
    </div>
  );
};

export default MyCategories;