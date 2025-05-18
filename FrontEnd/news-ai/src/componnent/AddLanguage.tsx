import React, { useState} from "react";
import config from "../config";
import { useAuth } from "../context/AuthContext"; 
import { useLanguages } from "../context/LanguagesContext";

import {
  Dialog,

  DialogContentWithoutClosing,
  DialogDescription,
  DialogFooter,
  DialogHeader,
  DialogTitle,
} from "@/components/ui/dialog";

import { Button } from "@/components/ui/button";

import {toast} from "sonner";
import LanguageList from "./LanguageList";
import { DialogClose, DialogTrigger } from "@radix-ui/react-dialog";
import { IoMdAddCircleOutline } from "react-icons/io";




interface AddLanguageProps {

  token: string | null; // Add token prop
}

const AddLanguage: React.FC<AddLanguageProps> = ({  token: propToken }) => {
  const token = propToken || localStorage.getItem("token") || null; 
  const { MyLanguages, refreshLanguages ,languages,maxLanguages} = useLanguages();
  const { handleRefreshToken } = useAuth();
  const [selectedLanguage, setSelectedLanguage] = useState("");
  const [error, setError] = useState<string | null>(null);
  const [languageListOpent, setlanguageListOpent] = React.useState(false);
  const [open, setOpen] = useState(false);



 

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
    setOpen(false);

   
    
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
    <Dialog  open={open} onOpenChange={setOpen}>
      <DialogTrigger asChild className="px-4 py-2 bg-blue-500 text-white font-semibold rounded-lg shadow-lg hover:shadow-xl hover:bg-blue-600 transition-all duration-300">
        <Button variant="outline"><IoMdAddCircleOutline /> Add new language</Button>
      </DialogTrigger>
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
          <DialogClose asChild className="bg-red-400 hover:bg-red-500 inline-flex items-center justify-center gap-2 whitespace-nowrap rounded-md text-sm font-medium transition-all disabled:pointer-events-none disabled:opacity-50 [&_svg]:pointer-events-none [&_svg:not([class*='size-'])]:size-4 shrink-0 [&_svg]:shrink-0 outline-none focus-visible:border-ring focus-visible:ring-ring/50 focus-visible:ring-[3px] aria-invalid:ring-destructive/20 dark:aria-invalid:ring-destructive/40 aria-invalid:border-destructive w-full">
              <Button type="button" variant="outline">
                Cancel
              </Button>
            </DialogClose>
            <Button  className="bg-[#739dcc] text-white py-2 rounded-md hover:bg-[#2a3a5c] hover:text-white"
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


export default AddLanguage;

