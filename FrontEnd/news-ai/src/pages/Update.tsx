import  { useEffect, useState } from "react";
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
      // שמירה על הרקע הקודם
      const previousBackground = document.body.style.backgroundColor;
  
      // שינוי הרקע
      document.body.style.backgroundImage = "url('/Images/update.webp')";
      document.body.style.backgroundSize = "cover";
  
      // ניקוי – החזרת הרקע כשהקומפוננטה יוצאת
      return () => {
        document.body.style.backgroundColor = previousBackground;
      };
    }, []);

  return (
   
   
  
   
    <div className="flex flex-col items-center justify-center flex-1 p-8">
      <div className="bg-white/90 p-6 rounded-2xl shadow-lg w-full max-w-md flex flex-col items-center">
        {/* Update User */}
        <h2
          className={`text-2xl font-bold mb-4 cursor-pointer ${
            activeSection === "updateUser" ? "text-blue-500" : "text-gray-700"
          }`}
          onClick={() => toggleSection("updateUser")}
        >
          Update User
        </h2>
        {activeSection === "updateUser" && <UpdateUser />}
  
        {/* Change Password */}
        <h2
          className={`text-2xl font-bold mb-4 mt-6 cursor-pointer ${
            activeSection === "changePassword" ? "text-blue-500" : "text-gray-700"
          }`}
          onClick={() => toggleSection("changePassword")}
        >
          Change Password
        </h2>
        {activeSection === "changePassword" && <ChangePassword />}
  
        {/* Back Button */}
        <button
          onClick={() => navigate("/dashboard")}
          className="w-full mt-6 bg-gray-500 text-white py-2 rounded hover:bg-gray-600"
        >
          Back to Dashboard
        </button>
        {/* <button
        onClick={() => navigate("/dashboard")}
        className="bg-black text-blue-500 px-4 py-2 rounded-md hover:bg-gray-800"
      >
        Dashboard
      </button> */}
    </div>
      </div>
    
  
  
  //   <div className="flex min-h-screen">
  // {/* Sidebar קבוע בצד שמאל */}
  //  <div className="bg-white p-4 w-64 shadow-md"
  //   style={{
  //     backgroundColor: "rgba(255, 255, 255, 0.95)",
  //     height: "100vh",
  //     borderTopRightRadius: "20px",
  //     borderBottomRightRadius: "20px",
  //   }}
  // >
  //   <h2 className="text-xl font-bold mb-4">Sidebar</h2>
  //   {/* תוכל להוסיף כאן ניווט, קישורים וכו' */}
  //   <button
  //     className="text-blue-500 underline"
  //     onClick={() => navigate("/dashboard")}
  //   >
  //     Dashboard
  //   </button>
  // </div>
    // <div className="flex flex-col items-center justify-center min-h-screen">
    //   <div className="content-box bg-white p-6 rounded-lg shadow-lg w-96"
    //   style={{
    //     backgroundColor: "rgba(240, 240, 240, 0.9)",
    //     padding: "20px",
    //     borderRadius: "20px",
    //     marginTop: "20px",
    //     display: "flex",
    //     flexDirection: "column",
    //     alignItems: "center",
    //     overflowY:"auto",
    //     flexGrow: 1,
    //   }}
    //   >
    //     {/* Update User Section */}
    //     <h2
    //       className={`text-2xl font-bold mb-4 cursor-pointer ${
    //         activeSection === "updateUser" ? "text-blue-500" : "text-gray-700"
    //       }`}
    //       onClick={() => toggleSection("updateUser")}
    //     >
    //       Update User
    //     </h2>
    //     {activeSection === "updateUser" && <UpdateUser />}

    //     {/* Change Password Section */}
    //     <h2
    //       className={`text-2xl font-bold mb-4 mt-6 cursor-pointer ${
    //         activeSection === "changePassword" ? "text-blue-500" : "text-gray-700"
    //       }`}
    //       onClick={() => toggleSection("changePassword")}
    //     >
    //       Change Password
    //     </h2>
    //     {activeSection === "changePassword" && <ChangePassword />}

    //     {/* Back to Dashboard Button */}
    //     <div
    //     style={{
    //       display: "flex",
          
    //       alignItems: "center",
    //       marginTop: "20px",
    //     }}>
    //     <button
    //       onClick={() => navigate("/dashboard")}
    //       className="w-full mt-4 bg-gray-500 text-white p-2 rounded hover:bg-gray-600"
    //     >
    //       Back to Dashboard
    //     </button>
    //     </div>
    //   </div>
    // </div>
    // </div>
    
  );
};

export default Update;

