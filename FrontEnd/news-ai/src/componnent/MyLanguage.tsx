import { useEffect, useState } from "react";
import config from "../config";
import { useAuth } from "../context/AuthContext";
import { useLanguages } from "../context/LanguagesContext"; // ✅ ייבוא ה-Context
import { TbMessageLanguage } from "react-icons/tb";
import { MdDelete } from "react-icons/md";

const MyLanguage = () => {
  const { MyLanguages: languages, setMyLanguages } = useLanguages(); // ✅ שימוש ב-Context
  const [error, setError] = useState<string | null>(null);
  const { handleRefreshToken } = useAuth();
  const {refreshLanguages} = useLanguages(); 

 useEffect(() => {
    if(!languages || languages.length === 0) {
      console.log("languages is null");
      refreshLanguages(); 
    }
    else
 
      console.log("languages is not null");

 }, []); // Check if languages is null or empty

  const handleDeleteLanguage = async (language: string) => {
    const token = localStorage.getItem("token");

    try {
      const res = await fetch(`${config.baseURL}/deleteLanguage`, {
        body: JSON.stringify({ language }),
        method: "DELETE",
        headers: {
          "Content-Type": "application/json",
          Authorization: `Bearer ${token}`,
        },
      });

      if (!res.ok) {
        if (res.status === 401) {
          handleRefreshToken();
          return null;
        }
        throw new Error("Failed to delete selected items");
      }

      setMyLanguages((prev) => prev.filter((item) => item !== language)); // ✅ עדכון גלובלי

      setError(null);
    } catch (error) {
      setError("Failed to delete selected items.");
    }
  };

  return (
    <div >
      {error && <p className="text-red-500">{error}</p>}
     
      {languages.length > 0 ? (
        <ul style={{ listStyleType: "none", padding: "0px", margin: "0" }}>
          {languages.map((language, index) => (
            
            <li key={index} style={{ display: "flex", alignItems: "center",marginRight: "0px",marginTop: "0px" }}>
              <TbMessageLanguage style={{ marginRight: "10px" }} />
              {language}
              <button
                onClick={() => handleDeleteLanguage(language)}
                style={{
                  background: "none",
                  border: "none",
                  fontSize: "24px",
                  cursor: "pointer",
                  marginLeft: "30px",
                  //marginTop: "20px",
                }}
                aria-label="delete language"
                className="delete-button"
              >
                <MdDelete />
              </button>
            </li>
          ))}
        </ul>
      ) : (
        !error && <p>No languages selected yet.</p>
      )}
    </div>
  );
};

export default MyLanguage;


