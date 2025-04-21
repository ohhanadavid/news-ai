
import React, { useState } from "react";
import config from "../config";
import { useNavigate } from "react-router-dom";
import { useAuth } from "../context/AuthContext"; 
import { MdCancel, MdClose } from "react-icons/md";
import { useCategory } from "../context/CategoryContext";

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
import { Input } from "@/components/ui/input";

interface AddCategoryProps {
  isOpen: boolean;
  onClose: () => void;
}

const AddCategory: React.FC<AddCategoryProps> = ({ isOpen, onClose }) => {
  const [selectedCategory, setSelectedCategory] = useState("");
  const [preference, setPreference] = useState("");
  const navigate = useNavigate();
  const { handleRefreshToken } = useAuth();
  const [error, setError] = useState<string | null>(null);
  const { categories, refreshCategories } = useCategory();
  const [isSubmitting, setIsSubmitting] = useState(false);
  

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();

    if (!selectedCategory) {
      setError("Please select a category");
      return;
    }

    const token = localStorage.getItem("token");
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
    setIsSubmitting(false);
    if (res.status === 401) {
      await handleRefreshToken();
      return;
    }
    if (res.status === 409) {
      setError("Category already exists");
      return;
    }
    if (res.status === 400) {
      setError("Something went wrong, please try again later");
      return;
    }
    if (!res.ok) {
      setError("Failed to save category");
      return;
    }

    await refreshCategories();
    
    // Reset form and close dialog
    setSelectedCategory("");
    setPreference("");
    setError(null);
    onClose();
  };

  const handleExit = () => {
    onClose();
    navigate("/dashboard");
  };

  return (
    <Dialog open={isOpen} onOpenChange={onClose}>
      <DialogContent className="sm:max-w-md">
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
            <Select value={selectedCategory} onValueChange={setSelectedCategory}>
              <SelectTrigger>
                <SelectValue placeholder="Select a category" />
              </SelectTrigger>
              <SelectContent>
                {categories.map((category, index) => (
                  <SelectItem key={index} value={category}>
                    {category}
                  </SelectItem>
                ))}
              </SelectContent>
            </Select>
          </div>
          
          <div className="space-y-2">
            <label htmlFor="preference" className="text-sm font-medium">
              Preference
            </label>
            <Input
              id="preference"
              type="text"
              value={preference}
              onChange={(e) => setPreference(e.target.value)}
              placeholder="Enter your preference"
            />
          </div>
          
          {error && <p className="text-red-500 text-sm">{error}</p>}
          
          <DialogFooter className="flex justify-end space-x-2">
            <DialogClose asChild>
              <Button type="button" variant="outline">
                Cancel
              </Button>
            </DialogClose>
            <Button 
              type="submit" 
              disabled={!selectedCategory}
            >
              Add Category
            </Button>
          </DialogFooter>
        </form>
        
        
        
      </DialogContent>
    </Dialog>
  );
};

export default AddCategory;