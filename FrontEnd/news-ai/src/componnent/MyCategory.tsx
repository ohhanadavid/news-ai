

import  { useEffect, useState } from "react";
import { useCategory } from "../context/CategoryContext";
import { FaChevronDown, FaChevronUp } from "react-icons/fa";
import { MdCategory, MdCheckCircle, MdDelete } from "react-icons/md";
import config from "../config";
import { toast } from "sonner";
import { set } from "react-hook-form";

const MyCategories = () => {
  const { myCategories, refreshCategories } = useCategory(); // Access categories from context
  const [visibleCategories, setVisibleCategories] = useState<Record<string, boolean>>({});


  useEffect(() => {
    if (!myCategories || myCategories.size === 0) {
      console.log("myCategories is null");
      refreshCategories();
    } else 
      console.log("myCategories is not null");
    
  }, [myCategories, refreshCategories]); // Check if myCategories is null or empty

  

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
      sucsessMessage(`Category ${category} deleted successfully!`);
      // Refresh categories after deletion
      await refreshCategories();
    } catch (error) {
      console.error("Error deleting category:", error);
      errorMessage("Failed to delete category");
    }
  };

  
  const errorMessage = async (error: string | null) => {
    toast.error("ERROR", {
      description: error,
      position: "top-right",
      duration: 5000,
    });
  };
  const sucsessMessage = async (success: string | null) => {
    toast.success("SUCCESS", {
      description: success,
      position: "top-right",
      duration: 5000,
    });
  }
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
                  onClick={() => {
                    setVisibleCategories((prev) => ({
                      ...prev,
                      [category]: !prev[category],
                    }));
                  }}
                >
                  {visibleCategories[category] ? <FaChevronUp /> : <FaChevronDown />}
                </button>
              </div>
             
                <div style={{display:visibleCategories[category]?"block":"none", marginLeft: "20px" }}>
                  <ul style={{ display: "flex", flexDirection: "column", gap: "5px" }}>
                    {preferences.map((preference, subIndex) => (
                      <li key={subIndex} style={{ display: "flex", alignItems: "center", gap: "10px" }}>
                        <MdCheckCircle />
                        {preference}
                      </li>
                    ))}
                  </ul>
                </div>
              
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