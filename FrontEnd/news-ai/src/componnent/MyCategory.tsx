import React, { useState, useEffect } from "react";
import config from "../config";
import { useAuth } from "../context/AuthContext";

const MyCategoryies = () => {
  const [categories, setCategories] = useState<Map<string, string[]>>(new Map());
  const [error, setError] = useState<string | null>(null);
  const [visibleCategories, setVisibleCategories] = useState<Set<string>>(new Set());
  const [selectedItems, setSelectedItems] = useState<Set<string>>(new Set()); // Track selected items
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

  const toggleItemSelection = (item: string, category?: string) => {
    setSelectedItems((prev) => {
      const updated = new Set(prev);
      if (updated.has(item)) {
        updated.delete(item); // Unselect the item
      } else {
        updated.add(item); // Select the item
      }
      return updated;
    });
  };

  const handleDelete = async () => {
    console.log("Selected items to delete:", );
    // const token = localStorage.getItem("token");
    // const itemsToDelete = Array.from(selectedItems);

    // try {
    //   const res = await fetch(`${config.baseURL}/deleteCategories`, {
    //     method: "POST",
    //     headers: {
    //       "Content-Type": "application/json",
    //       Authorization: `Bearer ${token}`,
    //     },
    //     body: JSON.stringify({ items: itemsToDelete }),
    //   });

    //   if (!res.ok) {
    //     throw new Error("Failed to delete selected items");
    //   }

    //   // Remove deleted items from the state
    //   setCategories((prev) => {
    //     const updated = new Map(prev);
    //     itemsToDelete.forEach((item) => {
    //       updated.forEach((values, key) => {
    //         if (key === item) {
    //           updated.delete(key); // Remove category
    //         } else {
    //           updated.set(key, values.filter((value) => value !== item)); // Remove item
    //         }
    //       });
    //     });
    //     return updated;
    //   });

    //   setSelectedItems(new Set()); // Clear selected items
    // } catch (error) {
    //   console.error("Error deleting items:", error);
    //   setError("Failed to delete selected items.");
    // }
  };

  return (
    <div>
      {error ? (
        <p>{error}</p>
      ) : categories.size > 0 ? (
        <div>
          <ul>
            {Array.from(categories.entries()).map(([category, items], index) => (
              <li key={index}>
                <strong>
                  <input
                    type="checkbox"
                    checked={selectedItems.has(category)}
                    onChange={() => toggleItemSelection(category)}
                  />
                  {category}
                </strong>
                <button
                  style={{ marginLeft: "10px" }}
                  onClick={() => toggleCategoryVisibility(category)}
                >
                  {visibleCategories.has(category) ? "Hide Items" : "Show Items"}
                </button>
                {visibleCategories.has(category) && (
                  <ul>
                    {items.map((item, subIndex) => (
                      <li key={subIndex}>
                        <input
                          type="checkbox"
                          checked={selectedItems.has(item)}
                          onChange={() => toggleItemSelection(item, category)} // Pass both item and category
                        />
                        {item}
                      </li>
                    ))}
                  </ul>
                )}
              </li>
            ))}
          </ul>
          <button onClick={handleDelete} style={{ marginTop: "20px" }}>
            Delete Selected
          </button>
        </div>
      ) : (
        <p>No categories found.</p>
      )}
    </div>
  );
};

export default MyCategoryies;