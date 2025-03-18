import React, { useState, useEffect } from "react";
import config from "../config";

const AddLanguage = () => {
  const [languages, setLanguages] = useState<string[]>([]);
  const [selectedLanguage, setSelectedLanguage] = useState("");

  useEffect(() => {
    // Fetch the list of languages from the backend
    fetch(`${config.baseURL}/getLanguages`)
      .then(response => response.json())
      .then(data => {
        setLanguages(data);
      })
      .catch(error => {
        console.error("There was an error fetching the languages!", error);
      });
  }, []);

  const handleSubmit = (e: React.FormEvent) => {
    e.preventDefault();
    // Handle form submission logic here
    console.log("Language added:", selectedLanguage);
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
        <button type="submit">Add Language</button>
      </form>
    </div>
  );
};

export default AddLanguage;