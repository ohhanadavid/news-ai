import { useEffect, useState } from "react";
import { useCategory } from "../context/CategoryContext";
import { FaChevronDown, FaChevronUp } from "react-icons/fa";
import { MdCategory, MdCheckCircle, MdDelete, MdEdit } from "react-icons/md";
import config from "../config";
import { toast } from "sonner";


const MyCategories = () => {
  const { myCategories, refreshCategories } = useCategory();
  const [visibleCategories, setVisibleCategories] = useState<Record<string, boolean>>({});
  const [selectedCategories, setSelectedCategories] = useState<Record<string, boolean>>({});
  const [selectedPreferenc, setSelectedPreferenc] = useState<Record<string, boolean>>({});

  useEffect(() => {
    if (!myCategories || myCategories.size === 0) {
      console.log("myCategories is null");
      refreshCategories();
    } else {
      console.log("myCategories is not null");
    }
  }, [myCategories, refreshCategories]);

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
      await refreshCategories();
    } catch (error) {
      console.error("Error deleting category:", error);
      errorMessage("Failed to delete category");
    }
  };

  
  const handleDeletePrefernce = async (selectedCategory: string,preference:string) => {
    try {
      const token = localStorage.getItem("token");
      const res = await fetch(`${config.baseURL}/deletePreference`, {
        method: "DELETE",
        headers: {
          "Content-Type": "application/json",
          Authorization: `Bearer ${token}`,
        },
        body: JSON.stringify({
          category: {
            category: selectedCategory,
            preference
             }
            }),
      });

      if (!res.ok) {
        throw new Error("Failed to delete category");
      }
      sucsessMessage(`Category ${selectedCategory} deleted successfully!`);
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
  };

  const toggleCategorySelection = (category: string) => {
    setSelectedCategories((prev) => ({
      ...prev,
      [category]: !prev[category],
    }));
  };

  const togglePrefenrcecSelection = (preference: string) => {
    console.log("preference", preference);
    setSelectedPreferenc((prev) => ({
      ...prev,
      [preference]: !prev[preference],
    }));
  };

  return (
    <div style={{ padding: "20px", backgroundColor: "#f9f9f9", borderRadius: "10px", boxShadow: "0 4px 6px rgba(0, 0, 0, 0.1)" }}>
      {myCategories.size > 0 ? (
        <ul style={{ display: "flex", flexDirection: "column", gap: "15px" }}>
          {Array.from(myCategories.entries()).map(([category, preferences], index) => (
            <li key={index} style={{ display: "block", marginBottom: "15px" }}>
              <div style={{ display: "flex", alignItems: "center", justifyContent: "space-between" }}>
                <div style={{ display: "flex", alignItems: "center" }}>
                  <input
                    type="checkbox"
                    checked={!!selectedCategories[category]}
                    onChange={() => toggleCategorySelection(category)}
                    style={{ marginRight: "10px" }}
                  />
                 
                  <strong style={{ fontSize: "18px", color: "#333" }}>{category}</strong>
                </div>
                <div>
                  
                  
                      {/* <button

                        style={{
                          background: "#4CAF50",
                          border: "none",
                          color: "white",
                          fontSize: "16px",
                          cursor: "pointer",
                          padding: "5px 10px",
                          borderRadius: "5px",
                        }}
                        aria-label="edit category"
                      >
                        <MdEdit />
                      </button> */}
                    
                  
                  <button
                    style={{
                      background: "#4A90E2",
                      border: "none",
                      color: "white",
                      fontSize: "16px",
                      cursor: "pointer",
                      padding: "5px 10px",
                      borderRadius: "5px",
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
                  {selectedCategories[category] && (
                    <>
                  <button
                        onClick={() => handleDeleteCategory(category)}
                        style={{
                          background: "#ff4d4f",
                          border: "none",
                          color: "white",
                          fontSize: "16px",
                          cursor: "pointer",
                          padding: "5px 10px",
                          borderRadius: "5px",
                          marginRight: "10px",
                          marginLeft: "10px",
                        }}
                        aria-label="delete category"
                      >
                        <MdDelete />
                      </button>
                      </>
                  )}
                </div>
              </div>
              <div style={{ display: visibleCategories[category] ? "block" : "none", marginLeft: "20px", marginTop: "10px" }}>
                <ul style={{ display: "flex", flexDirection: "column", gap: "10px" }}>
                  {preferences.map((preference, subIndex) => (
                    <li key={subIndex} style={{ display: "flex", alignItems: "center", gap: "10px", color: "#555" }}>
                      <input
                        type="checkbox"
                        style={{ marginRight: "10px" }}
                        checked={!!selectedPreferenc[preference]}
                        onChange={() => togglePrefenrcecSelection(preference)}
                      />
                      <MdCheckCircle style={{ color: "#4CAF50" }} />
                      {preference}
                      {selectedPreferenc[preference] && (
                    <>
                      <button
                        onClick={() => handleDeletePrefernce(category,preference)}
                        style={{
                          background: "#ff4d4f",
                          border: "none",
                          color: "white",
                          fontSize: "16px",
                          cursor: "pointer",
                          padding: "5px 10px",
                          borderRadius: "5px",
                          marginRight: "10px",
                        }}
                        aria-label="delete category"
                      >
                        <MdDelete />
                      </button>
                      {/* <button
                        style={{

                          background: "#4CAF50",
                          border: "none",
                          color: "white",
                          fontSize: "16px",
                          cursor: "pointer",
                          padding: "5px 10px",
                          borderRadius: "5px",
                        }}
                        aria-label="edit category"
                      >
                        <MdEdit />
                      </button> */}
                    </>
                  )}
                    </li>
                  ))}
                </ul>
              </div>
            </li>
          ))}
        </ul>
      ) : (
        <p style={{ textAlign: "center", color: "#999" }}>No categories found.</p>
      )}
    </div>
  );
};

export default MyCategories;