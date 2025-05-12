// import React, { useState, useEffect } from "react";
// import { Link, useLocation, useNavigate } from "react-router-dom";
// import { useAuth } from "../context/AuthContext";
// import MyLanguage from "../componnent/MyLanguage";
// import MyCategoryies from "../componnent/MyCategory";
// import { HiOutlinePencilSquare } from "react-icons/hi2";
// import { IoMdAddCircleOutline } from "react-icons/io";

// import DeleteUser from "./DeleteUser";
// import AddLanguage from "./AddLanguage";
// import AddCategory from "./AddCategory";
// import { Button } from "@/components/ui/button";
// import NewsSubscription from "./NewsSubscription";
// import { FaChevronDown, FaChevronUp } from "react-icons/fa";
// import { RiMailSendLine } from "react-icons/ri";


// const Dashboard2 = () => {
//   const { user, loading } = useAuth();
//   const navigate = useNavigate();
//   const [isDialogOpen, setIsDialogOpen] = React.useState(false);
//   const [isDialogNewsSubscribeOpen, setIsDialogNewsSubscribeOpen] = React.useState(false);
//   const [isCategoryDialogOpen, setIsCategoryDialogOpen] = useState(false);
//   const [isMyLanguageVisible, setIsMyLanguageVisible] = useState(false); // State to toggle MyLanguage visibility
//   const [isMyCategoryVisible, setIsMyCategoryVisible] = useState(false);

//   const location = useLocation();
  
//   // גש לטוקן מה-state
//   const token = location.state?.token;

  
//   useEffect(() => {
//     const token = localStorage.getItem("token");
//     if (!user && !token) {
//       navigate("/login"); // נווט לדף ההתחברות אם המשתמש לא מחובר
//     }
//     const previousBackground = document.body.style.backgroundColor;
//     console.log("log in previousBackground:", previousBackground);
//     // שינוי הרקע
//     document.body.style.backgroundImage = "url('/Images/background.webp')";
//     document.body.style.backgroundSize = "cover";
//      return () => {
//         document.body.style.backgroundColor = previousBackground;
//       }
//   }, [user, navigate]);

//   if (loading) {
//     return <div>טוען...</div>;  
//   }

//   return (
  
//     <div style={{ display: "flex", padding: "20px" }}>
//     {/* Sidebar קבוע בצד שמאל */}
//     <div
//       style={{
        
       
//         borderRadius: "20px",
//         padding: "20px",
//         marginRight: "20px",
//       }}
//     >
//     <div className="content-box" 
//     style={{
//       backgroundColor: "rgba(240, 240, 240, 0.8)",
       
//         padding: "20px" ,
//         flexGrow: 1,
//         overflowY: "auto",
//         borderRadius: "20px"
//         }}>
//       <div className="flex flex-col items-center bg-gray-100 rounded-lg shadow-lg p-6">
//       <h1 className="font-algerian text-4xl text-blue-600">News-AI</h1>
//       <p className="font-pattaya text-xl text-gray-700">Welcome to the NewsAi APP!</p>
//       <div className="mt-4 w-full">
//       <div style={{ display: "flex", alignItems: "center" }}>
//       <Link to="/update">
//           <button
//             style={{
//               background: "none",
//               border: "none",
//               padding: 0,
//               margin: 0,
//               fontSize: "20px",
//               cursor: "pointer",
//               height: "100%", // מתאים לגובה של ה-div
//               display: "flex",
//               alignItems: "center",
//               marginLeft: "10px",
//             }}
//             aria-label="Update User"
//           >
//             <HiOutlinePencilSquare
//               style={{ fontSize: "20px", verticalAlign: "middle" }}
//             />
//           </button>
//         </Link>
//         <h2 className="text-2xl font-bold text-gray-800">User Information</h2>
//         </div>
//         <p className="text-gray-600"><b>Name:</b> {user.name || "No name"}</p>
//         <p className="text-gray-600"><b>Email:</b> {user.email || "No email"}</p>
//         <p className="text-gray-600"><b>Phone:</b> {user.phone || "No phone"}</p>
//       </div>
//       <div className="mt-6 w-full">
//         <h2 className="text-2xl font-bold text-gray-800">Give me my news!!</h2>
//         <Button
//           onClick={() => setIsDialogNewsSubscribeOpen(true)}
//           className="w-full bg-blue-600 text-white py-2 rounded-md hover:bg-blue-700 mt-2"
//         >
//           Send me News
//         </Button>
//         <NewsSubscription
        
//         />
//       </div>
//       <div style={{ marginTop: "20px" }}>
//         <h2 style={{fontWeight: "bold", fontSize:"20px",}}>
//           My language{" "}

//           <Button
//             onClick={() => setIsMyLanguageVisible(!isMyLanguageVisible)}
//             className="bg-gray-600 hover:bg-gray-700 ml-2"
//             aria-label="Toggle My Language"
//           >
//             {isMyLanguageVisible ? <FaChevronUp /> : <FaChevronDown />}
//           </Button>

//         </h2>
//         <div style={{ display: isMyLanguageVisible ? "block" : "none" }}>
//          <MyLanguage />
//             <Button
//                 onClick={() => setIsDialogOpen(true)}
//                 className="bg-blue-600 hover:bg-blue-700"
//                 style={{
//                     border: "none",

//                     cursor: "pointer",
//                 }}
//                 aria-label="Add Language"
//             >
//                 <IoMdAddCircleOutline /> Add new language
//             </Button>
//             <AddLanguage
             
//                 token={token} // Pass the token to AddLanguage
//             />
//          </div>
//       </div>

//       <div style={{ marginTop: "20px" }}>
//         <h2 style={{fontWeight: "bold",fontSize:"20px"}}>
//           My categories{" "}

//           <Button
//             onClick={() => setIsMyCategoryVisible(!isMyCategoryVisible)}
//             className="bg-gray-600 hover:bg-gray-700 ml-2"
//             aria-label="Toggle My Categories"
//           >
//             {isMyCategoryVisible ?<FaChevronUp /> : <FaChevronDown />}
//           </Button>
//           {/* Category dialog component */}

//         </h2>
//        <div style={{ display: isMyCategoryVisible ? "block" : "none" }}>
//        <MyCategoryies />
//            <Button
//                onClick={() => setIsCategoryDialogOpen(true)}
//                className="bg-blue-600 hover:bg-blue-700 mr-2"
//            >
//                <IoMdAddCircleOutline /> Add new preference
//            </Button>
//            <AddCategory
            
//            />
//        </div>
//       </div>

//       <div style={{ marginTop: "20px" }}>
//         <h2 style={{fontWeight: "bold",fontSize:"20px"}}>Delete Account</h2>
//         <div className="items-center">
//         <DeleteUser />
//         </div>
//         </div>
//         </div>
//       </div>
//     </div>
//     </div>
          
    
//   );
// };

// export default Dashboard2;