import React, { useState, useEffect } from "react";
import config from "../config";
import { useNavigate } from "react-router-dom";
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
import { MdCancel, MdClose } from "react-icons/md";



interface AddLanguageProps {
  isOpen: boolean;
  onClose: () => void;
}

const AddLanguage: React.FC<AddLanguageProps> = ({ isOpen, onClose }) => {
  const [languages, setLanguages] = useState<string[]>([]);
  const [maxLanguages, setMaxLanguages] = useState<number>(0);
  const { MyLanguages, refreshLanguages } = useLanguages();
  const navigate = useNavigate();
  const { handleRefreshToken } = useAuth();
  const [selectedLanguage, setSelectedLanguage] = useState("");
  const [error, setError] = useState<string | null>(null);

  useEffect(() => {
    const token = localStorage.getItem("token");
    getLanguages(token, setLanguages);
    getMaxLanguages(token, setMaxLanguages);
  }, []);

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    if (!selectedLanguage) {
      setError("Please select a language");
      return;
    }
    const token = localStorage.getItem("token");
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
    // navigate("/dashboard");
  };
  // const handleExit = () => {
  //   onClose();
  //   navigate("/dashboard");
  // };

  return (
    <Dialog open={isOpen} onOpenChange={onClose}>
      <DialogContent className="sm:max-w-md">
        <DialogHeader>
          <DialogTitle>Add Language</DialogTitle>
          <DialogDescription>
            The maximum languages allowed is {maxLanguages}. You have {MyLanguages.length}.
          </DialogDescription>
          {/* <div className="absolute top-0 right-0">
            <button
              type="button"
              onClick={handleExit}
              className="rounded-full p-2 text-red-500 hover:text-red-700 hover:bg-gray-100 transition-colors"
              aria-label="Exit"
            >
              <MdClose size={24} />
            </button>
          </div> */}
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
  fetch(`${config.baseURL}/getLanguages`, {
    method: "GET",
    headers: {
      "Content-Type": "application/json",
      Authorization: `Bearer ${token}`,
    },
  })
    .then(response => response.json())
    .then(data => setLanguages(data))
    .catch(error => console.error("Error fetching languages:", error));
}

function getMaxLanguages(token: string | null, setMaxLanguages: React.Dispatch<React.SetStateAction<number>>) {
  fetch(`${config.baseURL}/maximumLanguage`, {
    method: "GET",
    headers: {
      "Content-Type": "application/json",
      Authorization: `Bearer ${token}`,
    },
  })
    .then(response => response.json())
    .then(data => setMaxLanguages(data))
    .catch(error => console.error("Error fetching max languages:", error));
}

export default AddLanguage;

