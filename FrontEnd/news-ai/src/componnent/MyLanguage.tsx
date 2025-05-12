// import { useEffect, useState } from "react";
// import config from "../config";
// import { useAuth } from "../context/AuthContext";
// import { useLanguages } from "../context/LanguagesContext"; // ✅ ייבוא ה-Context
// import { TbMessageLanguage } from "react-icons/tb";
// import { MdDelete } from "react-icons/md";
// import { toast } from "sonner";

// const MyLanguage = () => {
//   const { MyLanguages: languages, setMyLanguages } = useLanguages(); // ✅ שימוש ב-Context
//   const [error, setError] = useState<string | null>(null);
//   const { handleRefreshToken } = useAuth();
//   const {refreshLanguages} = useLanguages(); 

//  useEffect(() => {
//     if(!languages || languages.length === 0) {
//       console.log("languages is null");
//       refreshLanguages(); 
//     }
//     else
 
//       console.log("languages is not null");

//  }, []); // Check if languages is null or empty

//   const handleDeleteLanguage = async (language: string) => {
//     const token = localStorage.getItem("token");

//     try {
//       const res = await fetch(`${config.baseURL}/deleteLanguage`, {
//         body: JSON.stringify({ language }),
//         method: "DELETE",
//         headers: {
//           "Content-Type": "application/json",
//           Authorization: `Bearer ${token}`,
//         },
//       });

//       if (!res.ok) {
//         if (res.status === 401) {
//           handleRefreshToken();
//           return null;
//         }
//         errorMessage("Failed to delete selected items");
//         throw new Error("Failed to delete selected items");
//       }

//       setMyLanguages((prev) => prev.filter((item) => item !== language)); // ✅ עדכון גלובלי

//       setError(null);
//     } catch (error) {
//       setError("Failed to delete selected items.");
//     }
//     sucsessMessage(`Language ${language} deleted successfully!`);
//   };
//   const errorMessage= async(error: string | null)=> {
//     toast.error("ERROR", {
//       description: error,
//       position: "top-right",
//       duration: 5000,
//       closeButton: true,
//       style: {
//         color: "red",
        
//     }
//     });
//   }
//   const sucsessMessage= async(success: string | null)=> {
//     toast.success("SUCCESS", {
//       description: success,
//       position: "top-right",
//       duration: 5000,
//       closeButton: true,
//       style: {
//         color: "green",
        
//     }
//     });
//   }

//   return (
//     <div >
//       {error && <p className="text-red-500">{error}</p>}
     
//       {languages.length > 0 ? (
//         <ul style={{ listStyleType: "none", padding: "0px", margin: "0" }}>
//           {languages.map((language, index) => (
            
//             <li key={index} style={{ display: "flex", alignItems: "center",marginRight: "0px",marginTop: "0px" }}>
//               <TbMessageLanguage style={{ marginRight: "10px" }} />
//               {language}
//               <button
//                 onClick={() => handleDeleteLanguage(language)}
//                 style={{
//                   background: "none",
//                   border: "none",
//                   fontSize: "24px",
//                   cursor: "pointer",
//                   marginLeft: "30px",
//                   //marginTop: "20px",
//                 }}
//                 aria-label="delete language"
//                 className="delete-button"
//               >
//                 <MdDelete />
//               </button>
//             </li>
//           ))}
//         </ul>
//       ) : (
//         !error && <p>No languages selected yet.</p>
//       )}
//     </div>
//   );
// };

// export default MyLanguage;


import { useEffect, useState } from "react";
import config from "../config";
import { useAuth } from "../context/AuthContext";
import { useLanguages } from "../context/LanguagesContext"; // ✅ ייבוא ה-Context
import { TbMessageLanguage } from "react-icons/tb";
import { MdDelete } from "react-icons/md";
import { toast } from "sonner";

const MyLanguage = () => {
  const { MyLanguages: languages, setMyLanguages } = useLanguages(); // ✅ שימוש ב-Context
  const [error, setError] = useState<string | null>(null);
  const { handleRefreshToken } = useAuth();
  const {refreshLanguages} = useLanguages(); 
  const [selectedItems, setSelectedItems] = useState<Record<string, boolean>>({});

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
        errorMessage("Failed to delete selected items");
        throw new Error("Failed to delete selected items");
      }

      setMyLanguages((prev) => prev.filter((item) => item !== language)); // ✅ עדכון גלובלי

      setError(null);
    } catch (error) {
      setError("Failed to delete selected items.");
    }
    sucsessMessage(`Language ${language} deleted successfully!`);
  };
 


  const errorMessage= async(error: string | null)=> {
    toast.error("ERROR", {
      description: error,
      position: "top-right",
      duration: 5000,
      closeButton: true,
      style: {
        color: "red",
        
    }
    });
  }
  
  const sucsessMessage= async(success: string | null)=> {
    toast.success("SUCCESS", {
      description: success,
      position: "top-right",
      duration: 5000,
      closeButton: true,
      style: {
        color: "green",
        
    }
    });
  }


  const toggleItemSelection = (item: string) => {
    setSelectedItems((prev) => ({
      ...prev,
      [item]: !prev[item],
    }));
  };

  return (
    <div style={{ padding: "20px", backgroundColor: "#f9f9f9", borderRadius: "10px", boxShadow: "0 4px 6px rgba(0, 0, 0, 0.1)" }}> 
      {error && <p style={{ color: "#ff4d4f", textAlign: "center" }}>{error}</p>} 
      {languages.length > 0 ? (
        <ul style={{ listStyleType: "none", padding: "0px", margin: "0", display: "flex", flexDirection: "column", gap: "15px" }}> 
          {languages.map((language, index) => (
            <li key={index} style={{ display: "flex", alignItems: "center", justifyContent: "space-between", padding: "10px", backgroundColor: "#fff", borderRadius: "5px", boxShadow: "0 2px 4px rgba(0, 0, 0, 0.1)" }}> 
              <div style={{ display: "flex", alignItems: "center" }}>
                <input
                  type="checkbox"
                  checked={!!selectedItems[language]}
                  onChange={() => toggleItemSelection(language)}
                  style={{ marginRight: "10px" }}
                />
                <TbMessageLanguage style={{ marginRight: "10px", color: "#4A90E2" }} /> 
                <span style={{ fontSize: "16px", color: "#333" }}>{language}</span> 
              </div>
              {selectedItems[language] && ( // הצגת כפתורים רק אם checkbox מסומן
                <div>
                  <button
                    onClick={() => handleDeleteLanguage(language)}
                    style={{
                      background: "#ff4d4f", // צבע רקע אדום לכפתור מחיקה
                      border: "none",
                      color: "white",
                      fontSize: "16px",
                      cursor: "pointer",
                      padding: "5px 10px",
                      borderRadius: "5px",
                      marginLeft: "10px",
                    }}
                    aria-label="delete language"
                    className="delete-button"
                  >
                    <MdDelete />
                  </button>
                
                </div>
              )}
            </li>
          ))}
        </ul>
      ) : (
        !error && <p style={{ textAlign: "center", color: "#999" }}>No languages selected yet.</p> // שינוי צבע ומיקום הטקסט
      )}
    </div>
  );
};

export default MyLanguage;