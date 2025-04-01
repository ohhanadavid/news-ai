import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import UpdateUser from "../componnent/UpdateUser";
import ChangePassword from "../componnent/ChancePassword";

const Update = () => {
  const navigate = useNavigate();
  const [activeSection, setActiveSection] = useState<"updateUser" | "changePassword" | null>(null);

  const toggleSection = (section: "updateUser" | "changePassword") => {
    setActiveSection((prevSection) => (prevSection === section ? null : section));
  };

  return (
    <div className="flex flex-col items-center justify-center min-h-screen bg-gray-100">
      <div className="bg-white p-6 rounded-lg shadow-lg w-96">
        {/* Update User Section */}
        <h2
          className={`text-2xl font-bold mb-4 cursor-pointer ${
            activeSection === "updateUser" ? "text-blue-500" : "text-gray-700"
          }`}
          onClick={() => toggleSection("updateUser")}
        >
          Update User
        </h2>
        {activeSection === "updateUser" && <UpdateUser />}

        {/* Change Password Section */}
        <h2
          className={`text-2xl font-bold mb-4 mt-6 cursor-pointer ${
            activeSection === "changePassword" ? "text-blue-500" : "text-gray-700"
          }`}
          onClick={() => toggleSection("changePassword")}
        >
          Change Password
        </h2>
        {activeSection === "changePassword" && <ChangePassword />}

        {/* Back to Dashboard Button */}
        <div
        style={{
          display: "flex",
          
          alignItems: "center",
          marginTop: "20px",
        }}>
        <button
          onClick={() => navigate("/dashboard")}
          className="w-full mt-4 bg-gray-500 text-white p-2 rounded hover:bg-gray-600"
        >
          Back to Dashboard
        </button>
        </div>
      </div>
    </div>
  );
};

export default Update;

