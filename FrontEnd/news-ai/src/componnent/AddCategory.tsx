
import React, { useState } from "react";
import config from "../config";

import { useAuth } from "../context/AuthContext"; 

import { useCategory } from "../context/CategoryContext";

import {
  Dialog,

  DialogDescription,
  DialogFooter,
  DialogHeader,
  DialogTitle,
  DialogClose,
  DialogContentWithoutClosing,
  DialogTrigger,
} from "@/components/ui/dialog";

import { Button } from "@/components/ui/button";
import { Input } from "@/components/ui/input";
import { toast } from "sonner";
import CategoriesList from "./CategoryList";
import { IoMdAddCircleOutline } from "react-icons/io"



const AddCategory = () => {

  const [selectedCategory, setSelectedCategory] = useState("");
  const [preference, setPreference] = useState("");
  const [open, setOpen] = useState(false);
  const { handleRefreshToken,tokenStr } = useAuth();
  const [error, setError] = useState<string | null>(null);
  const { categories, refreshCategories } = useCategory();
   const [categoiesListOpent, setCategoiesListOpent] = React.useState(false);
  
  

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();

    if (!selectedCategory) {
      setError("Please select a category");
      return;
    }

    const token = localStorage.getItem(tokenStr);
    const res = await fetch(`${config.baseURL}/saveCategory`, {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
        Authorization: `Bearer ${token}`,
      },
      body: JSON.stringify({
        category: {
          preference,
          category: selectedCategory,
        },
      }),
    });
    
    if (res.status === 401) {
      await handleRefreshToken();
      return;
    }
    if (res.status === 409) {
      errorMessage("Category already exists");
      return;
    }
    if (res.status === 400) {
      errorMessage("Something went wrong, please try again later");
      return;
    }
    if (!res.ok) {
      errorMessage("Failed to save category");
      return;
    }

    await refreshCategories();
    
    sucsessMessage("Category added successfully");
    setSelectedCategory("");
    setPreference("");
    setError(null);
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
    
  return (
    <Dialog  open={open} onOpenChange={setOpen}>
      <DialogTrigger asChild className="px-4 py-2 bg-blue-500 text-white font-semibold rounded-lg shadow-lg hover:shadow-xl hover:bg-blue-600 transition-all duration-300">
        <Button variant="outline" className="w-fit max-w-full">
           <IoMdAddCircleOutline /> Add new preference
        </Button>
      </DialogTrigger>
      <DialogContentWithoutClosing className="w-fit max-w-full">
        <DialogHeader className="relative">
          <DialogTitle>Add Category</DialogTitle>
          <DialogDescription>
            Add a new category with your preference
          </DialogDescription>
          
         
        </DialogHeader>
        
        <form onSubmit={handleSubmit} className="space-y-4">
          <div className="space-y-2">
            <label htmlFor="category" className="text-sm font-medium">
              Category
            </label>
        
            <CategoriesList
              categories={categories}
              category={selectedCategory}
              setCategory={setSelectedCategory}
              open={categoiesListOpent}
              setOpen={setCategoiesListOpent}
            />
          </div>
          
          <div className="space-y-2  inline-flex items-center">
            <label htmlFor="preference" className="text-sm font-medium">
              Preference
            </label>
            <Input
              id="preference"
              value={preference}
              onChange={(e) => setPreference(e.target.value)}
              placeholder="Enter your preference"
              className="w-fit max-w-full"
              onInput={(e) => {
                e.currentTarget.style.height = "auto";
                e.currentTarget.style.height = `${e.currentTarget.scrollHeight}px`;
              }}
              style={{
                marginLeft: "15px",
                
              }}
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
              disabled={!selectedCategory || (preference.trim() === "")}
            >
              Add Category
            </Button>
          </DialogFooter>
        </form>
        
        
        
      </DialogContentWithoutClosing>
    </Dialog>
  );
};

export default AddCategory;