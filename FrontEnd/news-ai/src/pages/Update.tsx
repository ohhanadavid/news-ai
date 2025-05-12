import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import UpdateUser from "../componnent/UpdateUser";
import ChangePassword from "../componnent/ChancePassword";

const Update = () => {
  const navigate = useNavigate();
  const [activeSection, setActiveSection] = useState<"updateUser" | "changePassword" | null>(null);

  const toggleSection = (section: "updateUser" | "changePassword") => {
    setActiveSection((prevSection) => (prevSection === section ? null : section));
  };

  useEffect(() => {
    const previousBackground = document.body.style.backgroundColor;
    document.body.style.backgroundImage = "url('/Images/update.webp')";
    document.body.style.backgroundSize = "cover";

    return () => {
      document.body.style.backgroundColor = previousBackground;
    };
  }, []);

  return (
    
      <div className="bg-white shadow-lg rounded-lg p-8 w-full max-w-lg">
        <h1 className="text-3xl font-bold text-center text-gray-800 mb-6">Update Profile</h1>

        <div className="space-y-6">
          <div>
            <h2
              className={`text-xl font-semibold cursor-pointer transition-colors duration-300 ${
                activeSection === "updateUser" ? "text-blue-600" : "text-gray-700"
              }`}
              onClick={() => toggleSection("updateUser")}
            >
              Update User
            </h2>
            {activeSection === "updateUser" && <UpdateUser />}
          </div>

          <div>
            <h2
              className={`text-xl font-semibold cursor-pointer transition-colors duration-300 ${
                activeSection === "changePassword" ? "text-blue-600" : "text-gray-700"
              }`}
              onClick={() => toggleSection("changePassword")}
            >
              Change Password
            </h2>
            {activeSection === "changePassword" && <ChangePassword />}
          </div>
        </div>

        <button
          onClick={() => navigate("/dashboard")}
          className="mt-8 w-full bg-blue-500 text-white py-2 rounded-lg hover:bg-blue-600 transition-colors duration-300"
        >
          Back to Dashboard
        </button>
      </div>
    
  );
};

export default Update;

