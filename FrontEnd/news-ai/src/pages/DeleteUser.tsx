import  { useState } from "react";
import { useAuth } from "../context/AuthContext";

import { Button } from "@/components/ui/button";
import DeleteUserDialog from "../componnent/DeleteUserDialog";

const DeleteUser = () => {

  const { deleteUser } = useAuth();
 
  const [isOpen, setIsOpen] = useState(false);

 

  const handleDelete = async () => {
      console.log("Delete button clicked!");
    try {
      await deleteUser();
      console.log("Login successful! Redirecting...");
    } catch (err) {
      console.error(err);
      
    }
  };

  return (
    <div className=" items-center"
      style={{
        
        marginTop: "20px",
        
        
        }} >
      <Button variant="destructive" onClick={() => setIsOpen(true)}>
        Delete Account
      </Button>

      <DeleteUserDialog 
        open={isOpen} 
        onClose={() => setIsOpen(false)} 
        onConfirm={handleDelete} 
      />
    </div>
  );
};


 

export default DeleteUser;