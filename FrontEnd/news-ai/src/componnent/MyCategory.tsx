import React, { useState, useEffect } from "react";
import config from "../config";
import { useAuth } from "../context/AuthContext";
import { FaChevronDown, FaChevronUp } from "react-icons/fa";
import { MdCategory, MdCheckCircle, MdDelete } from "react-icons/md";

const MyCategoryies = () => {
  const [categories, setCategories] = useState<Map<string, string[]>>(new Map());
  const [error, setError] = useState<string | null>(null);
  const [visibleCategories, setVisibleCategories] = useState<Set<string>>(new Set());
  const { handleRefreshToken } = useAuth();

  useEffect(() => {
    const token = localStorage.getItem("token");

    if (!token) {
      setError("Token not found, please log in again.");
      return;
    }

    fetch(`${config.baseURL}/myCategories`, {
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
          throw new Error(`Failed to fetch categories, status: ${response.status}`);
        }
        if (response.status === 204) {
          return null; // No content
        }
        return response.json();
      })
      .then((data) => {
        if (data) {
          setCategories(new Map(Object.entries(data))); // Convert object to Map
          setError(null); // Clear error if the fetch is successful
        } else {
          setError("No categories found.");
        }
      })
      .catch((error) => {
        setError("There was an error fetching the categories: " + error.message);
        console.error("There was an error fetching the categories!", error);
      });
  }, []);

  const toggleCategoryVisibility = (category: string) => {
    setVisibleCategories((prev) => {
      const updated = new Set(prev);
      if (updated.has(category)) {
        updated.delete(category); // Hide the category
      } else {
        updated.add(category); // Show the category
      }
      return updated;
    });
  };

  const handleDeleteCategory = async (category: string) => {
    console.log("Selected items to delete:", category);
    const token = localStorage.getItem("token");

    try {
      const res = await fetch(
        `${config.baseURL}/deleteCategory?category=${encodeURIComponent(category)}&preference=${encodeURIComponent(category)}`,
        {
          method: "DELETE",
          headers: {
            "Content-Type": "application/json",
            Authorization: `Bearer ${token}`,
          },
        }
      );

      if (!res.ok) {
        throw new Error("Failed to delete selected items");
      }
      setCategories((prev) => {
        const updated = new Map(prev);
        updated.delete(category);
        return updated;
      });

      setError(null); // Clear error if the delete is successful
    } catch (error) {
      console.error("Error deleting items:", error);
      setError("Failed to delete selected items.");
    }
  };

  const handleDeletePrefference = async (category: string, preference: string) => {
    console.log("Selected items to delete:", category, preference);
    const token = localStorage.getItem("token");

    try {
      const res = await fetch(`${config.baseURL}/deletePreference`, {
        method: "DELETE",
        headers: {
          "Content-Type": "application/json",
          Authorization: `Bearer ${token}`,
        },
        body: JSON.stringify({
          category: {
            preference,
            category,
          },
        }),
      });

      if (!res.ok) {
        throw new Error("Failed to delete selected items");
      }

      // Remove deleted items from the state
      setCategories((prev) => {
        const updated = new Map(prev);
        updated.forEach((values, key) => {
          if (key === category) {
            updated.set(key, values.filter((value) => value !== preference)); // Remove preference
          }
        });
        return updated;
      });

      setError(null); // Clear error if the delete is successful
    } catch (error) {
      console.error("Error deleting items:", error);
      setError("Failed to delete selected items.");
    }
  };

  return (
    <div>
      {error && <p className="text-red-500">{error}</p>}
      {categories.size > 0 ? (
        <div>
          <ul>
            {Array.from(categories.entries()).map(([category, items], index) => (
              <li key={index} style={{  alignItems: "center" }}>
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
                  className="delete-button"
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
                  className="toggle-button"
                  onClick={() => toggleCategoryVisibility(category)}
                >
                  {visibleCategories.has(category) ? <FaChevronUp /> : <FaChevronDown />}
                </button>
                {visibleCategories.has(category) && (
                  <ul style={{ marginLeft: "0px", marginBottom: "10px" }}>
                    {items.map((item, subIndex) => (
                      <li key={subIndex} style={{ display: "block", marginTop: "15px" }}>
                        <MdCheckCircle style={{ marginRight: "10px" }} />
                        {item}
                        <button
                          onClick={() => handleDeletePrefference(category, item)}
                          style={{
                            background: "none",
                            border: "none",
                            fontSize: "24px",
                            cursor: "pointer",
                            marginLeft: "10px",
                          }}
                          aria-label="delete preference"
                          className="delete-button"
                        >
                          <MdDelete />
                        </button>
                      </li>
                    ))}
                  </ul>
                )}
              </li>
            ))}
          </ul>
        </div>
      ) : (
        <p>No categories found.</p>
      )}
    </div>
  );
};

export default MyCategoryies;