import React, { useState, FormEvent } from "react";
import { useNavigate } from "react-router-dom";
import { useAuth } from "../context/AuthContext";
import { toast } from "sonner";

const UpdateUser: React.FC = () => {
  const [firstName, setFirstName] = useState<string>("");
  const [lastName, setLastName] = useState<string>("");
  const [email, setEmail] = useState<string>("");
  const [phone, setPhone] = useState<string>("");
  const [success, setSuccess] = useState<string | null>(null);
  const navigate = useNavigate();
  const { updateUser } = useAuth();

  const handleSubmit = async (e: FormEvent) => {
    e.preventDefault();
    try {
      await updateUser({
        firstName,lastName,email,phone
      });
      setSuccess("User updated successfully!");
      sucsessMessage("User updated successfully!");
      console.log("Login successful! Redirecting...");
     
    } catch (err) {
      console.error(err);
      errorMessage("Failed to login. Please check your credentials.");
    }
    navigate("/dashboard");
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
    <div className=" w-fit max-w-full "
    style={{
      backgroundColor: "rgba(240, 240, 240, 0.7)",
      display: "flex",
      flexDirection: "column",
      
    }}>
      <div className="bg-white p-6 rounded-lg shadow-lg w-96" style={{
      backgroundColor: "rgba(240, 240, 240, 0.5)",
      display: "flex",
      flexDirection: "column",
      
    }}>
       
        
        {success && <p className="text-green-500">{success}</p>}
        <form onSubmit={handleSubmit}>
        
          <div className="mb-4">
            <label className="block text-gray-700">First Name</label>
            <input
              type="text"
              value={firstName}
              onChange={(e) => setFirstName(e.target.value)}
              className="w-full p-2 border border-gray-300 rounded mt-1"
              
            />
          </div>
          <div className="mb-4">
            <label className="block text-gray-700">Last Name</label>
            <input
              type="text"
              value={lastName}
              onChange={(e) => setLastName(e.target.value)}
              className="w-full p-2 border border-gray-300 rounded mt-1"
              
            />
          </div>
          <div className="mb-4">
            <label className="block text-gray-700">Email</label>
            <input
              type="email"
              value={email}
              onChange={(e) => setEmail(e.target.value)}
              className="w-full p-2 border border-gray-300 rounded mt-1"
              
            />
          </div>
          <div className="mb-4">
            <label className="block text-gray-700">Phone</label>
            <input
              type="text"
              value={phone}
              onChange={(e) => setPhone(e.target.value)}
              className="w-full p-2 border border-gray-300 rounded mt-1"
              
            />
          </div>
        
          <button type="submit" className="w-full bg-green-500 text-white p-2 rounded hover:bg-green-600">
            Update
          </button>
      
        </form>
      </div>
    </div>
  );
};

export default UpdateUser;





