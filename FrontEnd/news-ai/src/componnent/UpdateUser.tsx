import React, { useState, FormEvent } from "react";
import { useNavigate } from "react-router-dom";
import { useAuth } from "../context/AuthContext";
import { toast } from "sonner";

const UpdateUser: React.FC = () => {
  const { user } = useAuth();
  const [firstName, setFirstName] = useState<string>("");
  const [lastName, setLastName] = useState<string>("");
  const [email, setEmail] = useState<string>("");
  const [phone, setPhone] = useState<string>("");
  const [success, setSuccess] = useState<string | null>(null);
  const navigate = useNavigate();
  const { updateUser } = useAuth();
  const userName = user.name.split(" ");

  const handleSubmit = async (e: FormEvent) => {
    e.preventDefault();
    try {
      await updateUser({
        firstName, lastName, email, phone
      });
      setSuccess("User updated successfully!");
      successMessage("User updated successfully!");
      console.log("Update successful! Redirecting...");
    } catch (err) {
      console.error(err);
      errorMessage("Failed to update user. Please try again.");
    }
    navigate("/dashboard");
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
        {success && <p className="text-green-500 font-semibold mb-4">{success}</p>}
        <form onSubmit={handleSubmit} className="space-y-4">
          <div>
            <label className="block text-gray-700 font-medium">First Name</label>
            <input
              type="text"
              value={firstName || userName[1]}
              onChange={(e) => setFirstName(e.target.value)}
              className="w-full p-3 border border-gray-300 rounded focus:outline-none focus:ring-2 focus:ring-blue-500"
            />
          </div>
          <div>
            <label className="block text-gray-700 font-medium">Last Name</label>
            <input
              type="text"
              value={lastName || userName[0]}
              onChange={(e) => setLastName(e.target.value)}
              className="w-full p-3 border border-gray-300 rounded focus:outline-none focus:ring-2 focus:ring-blue-500"
            />
          </div>
          <div>
            <label className="block text-gray-700 font-medium">Email</label>
            <input
              type="email"
              value={email || user.email}
              onChange={(e) => setEmail(e.target.value)}
              className="w-full p-3 border border-gray-300 rounded focus:outline-none focus:ring-2 focus:ring-blue-500"
            />
          </div>
          <div>
            <label className="block text-gray-700 font-medium">Phone</label>
            <input
              type="text"
              value={phone || user.phone}
              onChange={(e) => setPhone(e.target.value)}
              className="w-full p-3 border border-gray-300 rounded focus:outline-none focus:ring-2 focus:ring-blue-500"
            />
          </div>
          <button
            type="submit"
            className="w-full bg-blue-500 text-white p-3 rounded hover:bg-blue-600 transition"
          >
            Update
          </button>
        </form>
      </div>
    
  );
};

export default UpdateUser;





