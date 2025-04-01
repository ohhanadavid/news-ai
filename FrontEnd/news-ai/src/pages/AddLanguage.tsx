// import React, { useState, useEffect } from "react";
// import config from "../config";
// import { useNavigate } from "react-router-dom";
// import { useAuth } from "../context/AuthContext"; 
// import { MdCancel } from "react-icons/md";
// import { useLanguages } from "../context/LanguagesContext";

// import { zodResolver } from "@hookform/resolvers/zod";
// import { useForm } from "react-hook-form";
// import { z } from "zod";
 
// import { Button } from "@/components/ui/button";
// import {
//   Form,
//   FormControl,
//   FormField,
//   FormItem,
//   FormLabel,
//   FormMessage,
// } from "@/components/ui/form";
// import { Select, SelectContent, SelectItem, SelectTrigger, SelectValue } from "@/components/ui/select";

// const formSchema = z.object({
//   language: z.string().min(1, "Please select a language"),
// });

// const AddLanguage = () => {
//   const [languages, setLanguages] = useState<string[]>([]);
//   const [maxLanguages, setMaxLanguages] = useState<number>(0);
//   const { MyLanguages } = useLanguages();
//   const navigate = useNavigate();
//   const { handleRefreshToken } = useAuth();
//   const [error, setError] = useState<string | null>(null);

//   const form = useForm({
//     resolver: zodResolver(formSchema),
//     defaultValues: { language: "" },
//   });

//   useEffect(() => {
//     const token = localStorage.getItem("token");
//     getLanguages(token, setLanguages);
//     getMaxLanguages(token, setMaxLanguages);
//   }, []);

//   const onSubmit = async (values: { language: string }) => {
//     const token = localStorage.getItem("token");
//     const res = await fetch(`${config.baseURL}/saveLanguage`, {
//       method: "POST",
//       headers: {
//         "Content-Type": "application/json",
//         Authorization: `Bearer ${token}`,
//       },
//       body: JSON.stringify({ language: values.language }),
//     });

//     if (res.status === 401) {
//       await handleRefreshToken();
//       return;
//     }
//     if (res.status === 409) {
//       setError("Language already exists");
//       return;
//     }
//     if (res.status === 400) {
//       setError("You have already reached the maximum number of languages");
//       return;
//     }
//     if (!res.ok) throw new Error("Failed to fetch user");

//     navigate("/dashboard");
//   };

//   return (
//     <div className="max-w-md mx-auto p-4">
//       <h1 className="text-xl font-bold mb-4">Add Language</h1>
//       <h2 className="mb-4">The maximum languages allowed is {maxLanguages}. You have {MyLanguages.length}.</h2>
//       <Form {...form}>
//         <form onSubmit={form.handleSubmit(onSubmit)} className="space-y-4">
//           <FormField
//             control={form.control}
//             name="language"
//             render={({ field }) => (
//               <FormItem>
//                 <FormLabel>Language</FormLabel>
//                 <FormControl>
//                   <Select onValueChange={field.onChange} defaultValue={field.value}>
//                     <SelectTrigger>
//                       <SelectValue placeholder="Select a language" />
//                     </SelectTrigger>
//                     <SelectContent>
//                       {languages.map((language, index) => (
//                         <SelectItem key={index} value={language}>
//                           {language}
//                         </SelectItem>
//                       ))}
//                     </SelectContent>
//                   </Select>
//                 </FormControl>
//                 <FormMessage />
//               </FormItem>
//             )}
//           />
//           {error && <p className="text-red-500">{error}</p>}
//           <Button type="submit">Add Language</Button>
//         </form>
//       </Form>
//       <button
//         type="button"
//         onClick={() => navigate("/dashboard")}
//         className="mt-4 text-red-500 text-lg"
//         aria-label="Cancel"
//       >
//         <MdCancel />
//       </button>
//     </div>
//   );
// };

// export default AddLanguage;

// function getLanguages(token: string | null, setLanguages: React.Dispatch<React.SetStateAction<string[]>>) {
//   fetch(`${config.baseURL}/getLanguages`, {
//     method: "GET",
//     headers: {
//       "Content-Type": "application/json",
//       Authorization: `Bearer ${token}`,
//     },
//   })
//     .then(response => response.json())
//     .then(data => setLanguages(data))
//     .catch(error => console.error("Error fetching languages:", error));
// }

// function getMaxLanguages(token: string | null, setMaxLanguages: React.Dispatch<React.SetStateAction<number>>) {
//   fetch(`${config.baseURL}/maximumLanguage`, {
//     method: "GET",
//     headers: {
//       "Content-Type": "application/json",
//       Authorization: `Bearer ${token}`,
//     },
//   })
//     .then(response => response.json())
//     .then(data => setMaxLanguages(data))
//     .catch(error => console.error("Error fetching max languages:", error));
// }



import React, { useState, useEffect } from "react";
import config from "../config";
import { useNavigate, Link } from "react-router-dom";
import { useAuth } from "../context/AuthContext"; 
import { MdCancel } from "react-icons/md";
import { useLanguages } from "../context/LanguagesContext";
import { useForm } from "react-hook-form"



const AddLanguage = () => {
  const [languages, setLanguages] = useState<string[]>([]);
  const [MaxLanguages, setMaxLanguages] = useState<number>(0);
  const { MyLanguages } = useLanguages();
  const [selectedLanguage, setSelectedLanguage] = useState("");
  const  navigate = useNavigate();
  const { handleRefreshToken } = useAuth();
  const [error, setError] = useState<string | null>(null);
  const form = useForm()
  
  useEffect(() => {
    // Fetch the list of languages from the backend
    const token = localStorage.getItem("token");
    getLanguages(token, setLanguages);
    getMaxLanguages(token, setMaxLanguages);
    
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
      
      <form onSubmit={handleSubmit}>
      <h1>Add Language</h1>
      <h2>The Maximum languages is {MaxLanguages} you have {MyLanguages.length}</h2>
      
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
        <button type="button" onClick={() => navigate("/dashboard")}
        style={{
                background: "none",
                border: "none",
                fontSize: "24px",
                cursor: "pointer",
                color: "red",
              }}
              aria-label="Cancel"
            >
              <MdCancel />
        </button>
      </div>
      </form>
      
    </div>
  );
};

export default AddLanguage;

function getLanguages(token: string | null, setLanguages: React.Dispatch<React.SetStateAction<string[]>>) {
  fetch(`${config.baseURL}/getLanguages`, {
    method: "GET",
    headers: {
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
  }
function getMaxLanguages(token: string | null, setLanguages: React.Dispatch<React.SetStateAction<number>>) {
      fetch(`${config.baseURL}/maximumLanguage`, {
        method: "GET",
        headers: {
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
      }

