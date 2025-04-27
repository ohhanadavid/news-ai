import React, { useState, useEffect } from "react";
import config from "../config";
import { useAuth } from "../context/AuthContext"; 
import { useLanguages } from "../context/LanguagesContext";

import {
  Dialog,
  DialogContent,
  DialogDescription,
  DialogFooter,
  DialogHeader,
  DialogTitle,
  DialogClose,
} from "@/components/ui/dialog";
import {
  Select,
  SelectContent,
  SelectItem,
  SelectTrigger,
  SelectValue,
} from "@/components/ui/select";
import { Button } from "@/components/ui/button";
import { MdCancel } from "react-icons/md";



interface AddLanguageProps {
  isOpen: boolean;
  onClose: () => void;
  token: string | null; // Add token prop
}

const AddLanguage: React.FC<AddLanguageProps> = ({ isOpen, onClose ,token}) => {
  const [languages, setLanguages] = useState<string[]>([]);
  const [maxLanguages, setMaxLanguages] = useState<number>(0);
  const { MyLanguages, refreshLanguages } = useLanguages();
  
  const { handleRefreshToken } = useAuth();
  const [selectedLanguage, setSelectedLanguage] = useState("");
  const [error, setError] = useState<string | null>(null);

  useEffect(() => {
    if (!token) return; // Wait until the token is available

    console.log("Token:", token);
    getLanguages(token, setLanguages);
    getMaxLanguages(token, setMaxLanguages);
  }, [token]); // Add token as a dependency

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
      setError("Language already exists");
      return;
    }
    if (res.status === 400) {
      setError("You have already reached the maximum number of languages");
      return;
    }
    if (!res.ok) {
      setError("Failed to add language");
      return;
    }

    await refreshLanguages();
    setSelectedLanguage("");
    setError(null);

    onClose();
    
  };


  return (
    <Dialog open={isOpen} onOpenChange={onClose}>
      <DialogContent className="w-1/2">
        <DialogHeader>
          <DialogTitle>Add Language</DialogTitle>
          <DialogDescription>
            The maximum languages allowed is {maxLanguages}. You have {MyLanguages.length}.
          </DialogDescription>
         
        </DialogHeader>
        
        <form onSubmit={handleSubmit} className="space-y-4">
          <div className="space-y-2">
            <label htmlFor="language" className="text-sm font-medium">
              Language
            </label>
            <Select value={selectedLanguage} onValueChange={setSelectedLanguage}>
              <SelectTrigger>
                <SelectValue placeholder="Select a language" />
              </SelectTrigger>
              <SelectContent>
                {languages.map((language, index) => (
                  <SelectItem key={index} value={language}>
                    {language}
                  </SelectItem>
                ))}
              </SelectContent>
            </Select>
          </div>
          
          {error && <p className="text-red-500 text-sm">{error}</p>}
          
          <DialogFooter className="flex justify-end space-x-2">
            <DialogClose asChild>
              <Button type="button" variant="outline"
              style={{
                                background: "none",
                                border: "none",
                                fontSize: "35px",
                                cursor: "pointer",
                                color: "red",
                              }}
                              aria-label="Cancel"
                            >
              
                <MdCancel />
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
      </DialogContent>
    </Dialog>
  );
};

// Helper functions
function getLanguages(token: string | null, setLanguages: React.Dispatch<React.SetStateAction<string[]>>) {
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

