import React, { useState, FormEvent } from "react";
import { useNavigate } from "react-router-dom";
import { useAuth } from "../context/AuthContext";
import { toast } from "sonner";

const ChancePassword: React.FC = () => {
  const [oldPassword, setOldPassword] = useState<string>("");
  const [newPassword, setNewPassword] = useState<string>("");
  const [confirmPassword, setConfirmPassword] = useState<string>("");
  const [error, setError] = useState<string | null>(null);
  const [success, setSuccess] = useState<string | null>(null);
  const navigate = useNavigate();
  const { changePassword } = useAuth();

  const handleSubmit = async (e: FormEvent) => {
    e.preventDefault();
    setError(null);
    setSuccess(null);

    // Validate passwords
    if (newPassword !== confirmPassword) {
      setError("Passwords do not match");
      return;
    }

   

    try {
      
      await changePassword( oldPassword,newPassword);
      setSuccess("password change successfuly! Redirecting...");
      sucsessMessage("password change successfuly! Redirecting...");
      navigate("/dashboard"); // Redirect to dashboard immediately
    } catch (err: any) {
      // Handle specific error messages if available
      errorMessage(err.message || "Failed to change password. Please try again.");
    }
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
    <div className="w-fit max-w-full "  style={{
      backgroundColor: "rgba(240, 240, 240, 0.7)",
      
      
      
      display: "flex",
      flexDirection: "column",
      
    }}>
      <div className="bg-white p-6 rounded-lg shadow-lg w-96"  style={{
      backgroundColor: "rgba(240, 240, 240, 0.7)",
      
      
      
      display: "flex",
      flexDirection: "column",
      
    }}>
        
        {error && <p className="text-red-500">{error}</p>}
        {success && <p className="text-green-500">{success}</p>}
        <form onSubmit={handleSubmit}>
        <div className="mb-4">
            <label className="block text-gray-700">Password</label>
            <input
              type="password"
              value={oldPassword}
              onChange={(e) => setOldPassword(e.target.value)}
              className="w-full p-2 border border-gray-300 rounded mt-1"
              required
            />
          </div>
     
          <div className="mb-4">
            <label className="block text-gray-700">Password</label>
            <input
              type="password"
              value={newPassword}
              onChange={(e) => setNewPassword(e.target.value)}
              className="w-full p-2 border border-gray-300 rounded mt-1"
              required
            />
          </div>
          <div className="mb-4">
            <label className="block text-gray-700">Confirm Password</label>
            <input
              type="password"
              value={confirmPassword}
              onChange={(e) => setConfirmPassword(e.target.value)}
              className="w-full p-2 border border-gray-300 rounded mt-1"
              required
            />
          </div>
          <button type="submit" className="w-full bg-green-500 text-white p-2 rounded hover:bg-green-600">
            change password
          </button>
        </form>
      </div>
    </div>
  );
};

export default ChancePassword;





