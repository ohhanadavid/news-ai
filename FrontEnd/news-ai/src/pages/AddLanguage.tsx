import React, { useState, useEffect } from "react";
import config from "../config";
import { useAuth } from "../context/AuthContext"; 
import { useLanguages } from "../context/LanguagesContext";

import {
  Dialog,
  DialogContent,
  DialogContentWithoutClosing,
  DialogDescription,
  DialogFooter,
  DialogHeader,
  DialogTitle,
} from "@/components/ui/dialog";

import { Button } from "@/components/ui/button";

import {toast} from "sonner";
import LanguageList from "./LanguageList";
import { DialogClose } from "@radix-ui/react-dialog";




interface AddLanguageProps {
  isOpen: boolean;
  onClose: () => void;
  token: string | null; // Add token prop
}

const AddLanguage: React.FC<AddLanguageProps> = ({ isOpen, onClose, token: propToken }) => {
  const token = propToken || localStorage.getItem("token") || null; 
  const [languages, setLanguages] = useState<string[]>([]);
  const [maxLanguages, setMaxLanguages] = useState<number>(0);
  const { MyLanguages, refreshLanguages } = useLanguages();
  const { handleRefreshToken } = useAuth();
  const [selectedLanguage, setSelectedLanguage] = useState("");
  const [error, setError] = useState<string | null>(null);
  const [languageListOpent, setlanguageListOpent] = React.useState(false);

  useEffect(() => {
    if (!token) {
      console.error("Token is not available in AddLanguage component");
      return; // חכה עד שהטוקן יהיה זמין
    }

    
    getLanguages(token, setLanguages);
    getMaxLanguages(token, setMaxLanguages);
  }, [token]);

  useEffect(() => {
    console.log(isOpen);
  }, [isOpen]);

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    if (!selectedLanguage) {
      setError("Please select a language");
      return;
    }
    // const token = localStorage.getItem("token");
    const res = await fetch(`${config.baseURL}/saveLanguage`, {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
        Authorization: `Bearer ${token}`,
      },
      body: JSON.stringify({ language: selectedLanguage }),
    });

    if (res.status === 401) {
      await handleRefreshToken();
      return;
    }
    if (res.status === 409) {
      errorMessage("Language already exists");
      
      return;
    }
    if (res.status === 400) {
      errorMessage("You have already reached the maximum number of languages");
      return;
    }
    if (!res.ok) {
      errorMessage("Failed to add language");
      return;
    }

    await refreshLanguages();

    setError(null);
    toast("language added successfully",{
          description:`language ${selectedLanguage} added successfully`,
          position: "top-right",
          duration: 3000,
          closeButton: true,
         
  });
    setSelectedLanguage("");

    onClose();
    
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
  
};


const handleDialogClick = (e: React.MouseEvent<HTMLDivElement>) => {
  console.log("Dialog clicked");
  if (e.currentTarget === e.target) {
    e.stopPropagation();
  }
};

  return (
    <Dialog open={isOpen} onOpenChange={onClose}  >
      <DialogContentWithoutClosing className="w-fit max-w-full  " onClick={handleDialogClick}>
        <DialogHeader  >
          <DialogTitle>Add Language</DialogTitle>
          <DialogDescription 
          style={{ color: maxLanguages > MyLanguages.length ? "green" : "red" }}>
            The maximum languages allowed is {maxLanguages}. You have {MyLanguages.length}.
          </DialogDescription>
         
        </DialogHeader>
        
        <form onSubmit={handleSubmit} className="space-y-4" onClick={(e)=>e.stopPropagation()}>
          <div className="space-y-2">
            <label htmlFor="language" className="text-sm font-medium">
              Language
            </label>

      
            <LanguageList
              languages={languages}
              language={selectedLanguage}
              setLanguage={setSelectedLanguage}
              setOpen={setlanguageListOpent}
              open={languageListOpent} 
              />
         
          </div>
          
          {error && <p className="text-red-500 text-sm">{error}</p>}
          
          <DialogFooter className="flex justify-end space-x-2">
          <DialogClose asChild className="bg-red-500 ring-offset-background focus:ring-ring  data-[state=open]:text-muted-foreground   rounded-xs opacity-70 transition-opacity hover:opacity-500 focus:ring-2 focus:ring-offset-2 focus:outline-hidden disabled:pointer-events-none [&_svg]:pointer-events-none [&_svg]:shrink-0 [&_svg:not([class*='size-'])]:size-4">
              <Button type="button" variant="outline">
                Cancel
              </Button>
            </DialogClose>
            <Button 
              type="submit" 
              disabled={!selectedLanguage || MyLanguages.length >= maxLanguages}
            >
              Add Language
            </Button>
           
          </DialogFooter>
        </form>
      </DialogContentWithoutClosing>
    </Dialog>
  );
};

// Helper functions
function getLanguages(token: string | null, setLanguages: React.Dispatch<React.SetStateAction<string[]>>) {
  console.log("getLanguages call");
  if (!token) {
    console.error("No token provided for getLanguages");
    return;
  }

  console.log("Fetching languages with token:", token ? "Token exists" : "No token");
  
  fetch(`${config.baseURL}/getLanguages`, {
    method: "GET",
    headers: {
      "Content-Type": "application/json",
      "Authorization": `Bearer ${token}`,
    },
  })
    .then(async response => {
      console.log("Languages response status:", response.status);
      console.log("Languages response headers:", Object.fromEntries(response.headers.entries()));
      
      if (!response.ok) {
        const errorText = await response.text();
        console.error("Server error response:", errorText.substring(0, 150));
        throw new Error(`Error ${response.status}: ${response.statusText}`);
      }
      
      
      const text = await response.text();
      console.log("Raw response:", text.substring(0, 100));
      
      
      if (!text) throw new Error("Empty response");
      try {
        const data = JSON.parse(text);
        console.log("Languages parsed:", data);
        setLanguages(data);
      } catch (parseError) {
        console.error("JSON parse error:", parseError);
        throw parseError;
      }
    })
    .catch(error => console.error("Error fetching languages:", error));
}

function getMaxLanguages(token: string | null, setMaxLanguages: React.Dispatch<React.SetStateAction<number>>) {
  console.log("maxLaguages call");
  if (!token) {
    console.error("No token provided for getMaxLanguages");
    return;
  }

  console.log("Fetching max languages with token:", token ? "Token exists" : "No token");
  
  fetch(`${config.baseURL}/maximumLanguage`, {
    method: "GET",
    headers: {
      "Content-Type": "application/json",
      "Authorization": `Bearer ${token}`,
    },
  })
    .then(async response => {
      console.log("Max languages response status:", response.status);
      
      if (!response.ok) {
        const errorText = await response.text();
        console.error("Server error response:", errorText.substring(0, 150));
        throw new Error(`Error ${response.status}: ${response.statusText}`);
      }
      
      
      const text = await response.text();
      console.log("Raw response:", text.substring(0, 100));
      
     
      
      if (!text) throw new Error("Empty response");
      try {
        const data = JSON.parse(text);
        console.log("Max languages parsed:", data);
        setMaxLanguages(data);
      } catch (parseError) {
        console.error("JSON parse error:", parseError);
        throw parseError;
      }
    })
    .catch(error => console.error("Error fetching max languages:", error));
}


export default AddLanguage;

