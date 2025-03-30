import React, { useState, useEffect } from "react";
import config from "../config";
import { useNavigate, Link } from "react-router-dom";
import { useAuth } from "../context/AuthContext"; 

const AddLanguage = () => {
  const [languages, setLanguages] = useState<string[]>([]);
  const [selectedLanguage, setSelectedLanguage] = useState("");
  const  navigate = useNavigate();
  const { handleRefreshToken } = useAuth();
  const [error, setError] = useState<string | null>(null);
  
  useEffect(() => {
    // Fetch the list of languages from the backend
    const token = localStorage.getItem("token");
    fetch(`${config.baseURL}/getLanguages`,{
      method:"GET",
      headers:{
        "Content-Type": "application/json",
        Authorization: `Bearer ${token}`,
      },
    })
      .then(response => response.json())
      .then(data => {
        setLanguages(data);
      })
      .catch(error => {
        console.error("There was an error fetching the languages!", error);
      });
  }, []);

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    console.log("Selected Language:", selectedLanguage);
    const token = localStorage.getItem("token");
    const res= await fetch(`${config.baseURL}/saveLanguage`,{
      method:"POST",
      headers:{
        "Content-Type": "application/json",
        Authorization: `Bearer ${token}`,
      },
      body:  JSON.stringify({ language: selectedLanguage }),
    })
    if (res.status === 401) {
      await handleRefreshToken();
      return;
    }
    if (res.status === 409) {
      setError("Language already exists");
      return;
    }
    if (res.status === 400) {
      setError("you have alredy maximum number of languages");
      return;
    }
    if (!res.ok) throw new Error("Failed to fetch user");
    
  

    console.log("Language added:", selectedLanguage);
    navigate("/dashboard"); 
  };

  return (
    <div>
      <h1>Add Language</h1>
      <form onSubmit={handleSubmit}>
        <label>
          Language:
          <select
            value={selectedLanguage}
            onChange={(e) => setSelectedLanguage(e.target.value)}
          >
            <option value="">Select a language</option>
            {languages.map((language, index) => (
              <option key={index} value={language}>
                {language}
              </option>
            ))}
          </select>
        </label>
        {error && <p className="text-red-500">{error}</p>}
        <br />
        <button type="submit">Add Language</button>
        
        <div style={{ marginTop: "10px" }}>
        <button type="button" onClick={() => navigate("/dashboard")}>
          Cancel
        </button>
      </div>
      </form>
    </div>
  );
};

export default AddLanguage;