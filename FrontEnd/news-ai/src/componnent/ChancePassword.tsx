import React, { useState, FormEvent } from "react";

import { useAuth } from "../context/AuthContext";
import { toast } from "sonner";

const ChancePassword: React.FC = () => {
  const [oldPassword, setOldPassword] = useState<string>("");
  const [newPassword, setNewPassword] = useState<string>("");
  const [confirmPassword, setConfirmPassword] = useState<string>("");
  const [error, setError] = useState<string | null>(null);
  const [success, setSuccess] = useState<string | null>(null);

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
      await changePassword(oldPassword, newPassword);
      setSuccess("Password changed successfully! Redirecting...");
      successMessage("Password changed successfully! Redirecting...");
      
    } catch (err: any) {
      // Handle specific error messages if available
      errorMessage(err.message || "Failed to change password. Please try again.");
    }
  };

  const errorMessage = async (error: string | null) => {
    toast.error("ERROR", {
      description: error,
      position: "top-right",
      duration: 5000,
      closeButton: true,
      style: {
        color: "red",
      },
    });
  };

  const successMessage = async (success: string | null) => {
    toast.success("SUCCESS", {
      description: success,
      position: "top-right",
      duration: 5000,
      closeButton: true,
      style: {
        color: "green",
      },
    });
  };

  return (
    
      <div
        className="bg-white p-8 rounded-lg shadow-lg w-96"
        style={{
          backgroundColor: "rgba(255, 255, 255, 0.9)",
        }}
      >
        {error && <p className="text-red-500 font-semibold mb-4">{error}</p>}
        {success && <p className="text-green-500 font-semibold mb-4">{success}</p>}
        <form onSubmit={handleSubmit} className="space-y-4">
          <div>
            <label className="block text-gray-700 font-medium">Old Password</label>
            <input
              type="password"
              value={oldPassword}
              onChange={(e) => setOldPassword(e.target.value)}
              className="w-full p-3 border border-gray-300 rounded focus:outline-none focus:ring-2 focus:ring-blue-500"
              required
            />
          </div>
          <div>
            <label className="block text-gray-700 font-medium">New Password</label>
            <input
              type="password"
              value={newPassword}
              onChange={(e) => setNewPassword(e.target.value)}
              className="w-full p-3 border border-gray-300 rounded focus:outline-none focus:ring-2 focus:ring-blue-500"
              required
            />
          </div>
          <div>
            <label className="block text-gray-700 font-medium">Confirm Password</label>
            <input
              type="password"
              value={confirmPassword}
              onChange={(e) => setConfirmPassword(e.target.value)}
              className="w-full p-3 border border-gray-300 rounded focus:outline-none focus:ring-2 focus:ring-blue-500"
              required
            />
          </div>
          <button
            type="submit"
            className="w-full bg-[#739dcc] text-white p-3 rounded hover:bg-[#2a3a5c] transition"
          >
            Change Password
          </button>
        </form>
      </div>
    
  );
};

export default ChancePassword;





