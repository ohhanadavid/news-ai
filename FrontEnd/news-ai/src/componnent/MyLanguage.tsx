import React, { useState, useEffect } from "react";
import config from "../config";
import { useAuth } from "../context/AuthContext"; 

const MyLanguage = () => {
  const [languages, setLanguages] = useState<string[]>([]);
  const [error, setError] = useState<string | null>(null);
   const { handleRefreshToken } = useAuth();

  useEffect(() => {
    const token = localStorage.getItem("token");

    if (!token) {
      setError("Token not found, please log in again.");
      return;
    }

    fetch(`${config.baseURL}/getMyLanguages`, {
      method: "GET",
      headers: {
        "Content-Type": "application/json",
        Authorization: `Bearer ${token}`,
      },
    })
      .then(response => {
        if (response.status === 401) {
          handleRefreshToken();  // אם הסטטוס הוא 401, נבצע רענון טוקן
          
        }
        if (!response.ok) {
          throw new Error(`Failed to fetch languages, status: ${response.status}`);
        }
        
        // בדוק אם יש גוף תגובה
        if (response.status === 204) {
          return null;  // במקרה שאין תוכן
        }

        return response.json();  // אחרת, קריאה ל-JSON
      })
      .then(data => {
        if (data) {
          setLanguages(data);  // אם יש נתונים, הצג אותם
        } else {
          setError("No languages found.");
        }
      })
      .catch(error => {
        setError("There was an error fetching the languages: " + error.message);
        console.error("There was an error fetching the languages!", error);
      });
  }, []);

  return (
    <div>
      {error ? (
        <p>{error}</p>
      ) : languages.length > 0 ? (
        <ul>
          {languages.map((language, index) => (
            <li key={index}>{language}</li>
          ))}
        </ul>
      ) : (
        <p>No languages selected yet.</p>
      )}
    </div>
  );
};

export default MyLanguage;
