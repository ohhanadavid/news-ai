import { Dialog, DialogHeader, DialogTitle, DialogFooter, DialogTrigger, DialogContentWithoutClosing } from "@/components/ui/dialog";
import { Button } from "@/components/ui/button";
import { useAuth } from "@/context/AuthContext";
import { useEffect, useState } from "react";


const DeleteUserDialog = () => {
 
   const { deleteUser } = useAuth();
   const [isOpen, setIsOpen] = useState(false);
  

   useEffect(() => {
    console.log("DeleteUserDialog isOpen:", isOpen);
  }, [isOpen]);
  
 
   const handleDelete = async () => {
       console.log("Delete button clicked!");
     try {
       await deleteUser();
       console.log("Login successful! Redirecting...");
        setIsOpen(false); // Close the dialog after deletion
     } catch (err) {
       console.error(err);
       
     }
   };
 
  return (

        <Dialog open={isOpen} onOpenChange={setIsOpen} modal={true}>
          <DialogTrigger asChild  >
            <label  tabIndex={0} className=" focus:bg-accent focus:text-accent-foreground data-[variant=destructive]:text-destructive data-[variant=destructive]:focus:bg-destructive/10 dark:data-[variant=destructive]:focus:bg-destructive/20 data-[variant=destructive]:focus:text-destructive data-[variant=destructive]:*:[svg]:!text-destructive [&_svg:not([class*='text-'])]:text-muted-foreground relative flex cursor-default items-center gap-2 rounded-sm px-2 py-1.5 text-sm outline-hidden select-none data-[disabled]:pointer-events-none data-[disabled]:opacity-50 data-[inset]:pl-8 [&_svg]:pointer-events-none [&_svg]:shrink-0 [&_svg:not([class*='size-'])]:size-4">Delete Account</label> 
          </DialogTrigger>
      <DialogContentWithoutClosing
        className="w-1/4"
        aria-describedby={undefined}
        
        
      >
        <DialogHeader>
          <DialogTitle>Delete Account</DialogTitle>
        </DialogHeader>
        <p>Are you sure you want to delete your account?</p>
        <DialogFooter>
          <Button variant="outline" onClick={() => setIsOpen(false)}>
            Cancel
          </Button>
          <Button variant="destructive" onClick={handleDelete}>
            Delete
          </Button>
        </DialogFooter>
      </DialogContentWithoutClosing>
    </Dialog>


  );
};

 export default DeleteUserDialog;
