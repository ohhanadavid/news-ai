import React, { useState, useEffect } from "react";
import { Link, useLocation, useNavigate } from "react-router-dom";
import { useAuth } from "../context/AuthContext";
import MyLanguage from "../componnent/MyLanguage";
import MyCategoryies from "../componnent/MyCategory";
import { HiOutlinePencilSquare } from "react-icons/hi2";
import { IoMdAddCircleOutline } from "react-icons/io";

import DeleteUser from "./DeleteUser";
import AddLanguage from "./AddLanguage";
import AddCategory from "./AddCategory";
import { Button } from "@/components/ui/button";
import NewsSubscription from "./NewsSubscription";
import { FaChevronDown, FaChevronUp } from "react-icons/fa";
import { RiMailSendLine } from "react-icons/ri";


const Dashboard = () => {
  const { user, loading } = useAuth();
  const navigate = useNavigate();
  const [isDialogOpen, setIsDialogOpen] = React.useState(false);
  const [isDialogNewsSubscribeOpen, setIsDialogNewsSubscribeOpen] = React.useState(false);
  const [isCategoryDialogOpen, setIsCategoryDialogOpen] = useState(false);
  const [isMyLanguageVisible, setIsMyLanguageVisible] = useState(false); // State to toggle MyLanguage visibility
  const [isMyCategoryVisible, setIsMyCategoryVisible] = useState(false);

  const location = useLocation();
  
  // גש לטוקן מה-state
  const token = location.state?.token;

  // useEffect(() => {
  //   const fetchDashboardData = async () => {
  //     // const token = localStorage.getItem("token");
  //   //   try {
  //   //     const response = await fetch(`${config.baseURL}/dashboard`, {
  //   //       headers: {
  //   //         Authorization: `Bearer ${token}`,
  //   //       },
  //   //     });
        
  //   //     if (response.ok) {
  //   //        await response.json();
  //   //     }
  //   //   } catch (error) {
  //   //     console.error("Error fetching dashboard data:", error);
  //   //   }
  //   // };

  //   if (user) {
  //     const previousBackground = document.body.style.backgroundColor;
  //     console.log("log in previousBackground:", previousBackground);
  //     // שינוי הרקע
  //     document.body.style.backgroundImage = "url('/Images/login.webp')";
  //     document.body.style.backgroundSize = "cover";
  //     fetchDashboardData();
  //     return () => {
  //       document.body.style.backgroundColor = previousBackground;
  //     }
  //   }
  // }, [user]);

  // if (loading) {
  //   return <div>טוען...</div>;
  // }
  
  useEffect(() => {
    const token = localStorage.getItem("token");
    if (!user && !token) {
      navigate("/login"); // נווט לדף ההתחברות אם המשתמש לא מחובר
    }
    const previousBackground = document.body.style.backgroundColor;
    console.log("log in previousBackground:", previousBackground);
    // שינוי הרקע
    document.body.style.backgroundImage = "url('/Images/login.webp')";
    document.body.style.backgroundSize = "cover";
     return () => {
        document.body.style.backgroundColor = previousBackground;
      }
  }, [user, navigate]);

  if (loading) {
    return <div>טוען...</div>;  
  }

  return (
  
    <div style={{ display: "flex", padding: "20px" }}>
    {/* Sidebar קבוע בצד שמאל */}
    <div
      style={{
        
       
        borderRadius: "20px",
        padding: "20px",
        marginRight: "20px",
      }}
    >
    <div className="content-box" 
    style={{
      backgroundColor: "rgba(240, 240, 240, 0.8)",
       height: "100vh",
        padding: "20px" ,
        flexGrow: 1,
        overflowY: "auto",
        borderRadius: "20px"
        }}>
      <div>
      <h1 className="font-algerian text-3xl !font-['algerian']">News-AI</h1>
        <p className="font-pattaya text-3xl !font-['pattaya']">Welcome to the NewsAi APP!</p>
      </div>

      <div
        style={{
          display: "flex",
          alignItems: "center",
          justifyContent: "center",
          padding: 0,
          margin: 0,
          height: "auto",
          marginTop: "10px", // שליטה על הגובה
        }}
      >
        <h2
          style={{
            margin: 0,
            padding: 0,
            fontSize: "20px",
            lineHeight: "1",
            fontWeight: "bold",
          }}
        >
         User Information
        </h2>
        <Link to="/update">
          <button
            style={{
              background: "none",
              border: "none",
              padding: 0,
              margin: 0,
              fontSize: "20px",
              cursor: "pointer",
              height: "100%", // מתאים לגובה של ה-div
              display: "flex",
              alignItems: "center",
              marginLeft: "10px",
            }}
            aria-label="Update User"
          >
            <HiOutlinePencilSquare
              style={{ fontSize: "20px", verticalAlign: "middle" }}
            />
          </button>
        </Link>
      </div>
     
      <div style={{ marginTop: "10px" }}>
        <p>
          <b>name:</b> {user.name? user.name : "No name"}
        </p>
        <p>
          <b>email:</b> {user.email ? user.email : "No email"}
        </p>
        <p>
          <b>phone:</b> {user.phone ? user.phone : "No phone"}
        </p>
      </div>
      
      <div style={{ marginTop: "20px" }}>
        <h2 style={{fontWeight: "bold" , fontSize:"20px",}}>Give me my news!!</h2>

        <Button
            onClick={() => setIsDialogNewsSubscribeOpen(true)}
            className="bg-blue-600 hover:bg-blue-700"
            style={{
              border: "none",
              fontSize: "24px",
              cursor: "pointer",
              marginTop: "10px",
              
            }}
            aria-label="news subscription"
          >
            <RiMailSendLine />
          </Button>
          <NewsSubscription 
          isOpen={isDialogNewsSubscribeOpen}
          onClose={() => setIsDialogNewsSubscribeOpen(false)} 
          />
      </div>

      <div style={{ marginTop: "20px" }}>
        <h2 style={{fontWeight: "bold", fontSize:"20px",}}>
          My language{" "}
          <Button
            onClick={() => setIsDialogOpen(true)}
            className="bg-blue-600 hover:bg-blue-700"
            style={{
              border: "none",
              fontSize: "24px",
              cursor: "pointer",
            }}
            aria-label="Add Language"
          >
            <IoMdAddCircleOutline />
          </Button>
          <Button
            onClick={() => setIsMyLanguageVisible(!isMyLanguageVisible)}
            className="bg-gray-600 hover:bg-gray-700 ml-2"
            aria-label="Toggle My Language"
          >
            {isMyLanguageVisible ? <FaChevronUp /> : <FaChevronDown />}
          </Button>
          <AddLanguage
            isOpen={isDialogOpen}
            onClose={() => setIsDialogOpen(false)}
            token={token} // Pass the token to AddLanguage
          />
        </h2>
        <div style={{ display: isMyLanguageVisible ? "block" : "none" }}>
         <MyLanguage />
         </div>
      </div>

      <div style={{ marginTop: "20px" }}>
        <h2 style={{fontWeight: "bold",fontSize:"20px"}}>
          My categories{" "}
          <Button
            onClick={() => setIsCategoryDialogOpen(true)}
            className="bg-blue-600 hover:bg-blue-700 mr-2"
          >
            <IoMdAddCircleOutline />
          </Button>
          <Button
            onClick={() => setIsMyCategoryVisible(!isMyCategoryVisible)}
            className="bg-gray-600 hover:bg-gray-700 ml-2"
            aria-label="Toggle My Categories"
          >
            {isMyCategoryVisible ?<FaChevronUp /> : <FaChevronDown />}
          </Button>
          {/* Category dialog component */}
          <AddCategory
            isOpen={isCategoryDialogOpen}
            onClose={() => setIsCategoryDialogOpen(false)}
          />
        </h2>
       <div style={{ display: isMyCategoryVisible ? "block" : "none" }}>
       <MyCategoryies />
       </div>
      </div>

      <div style={{ marginTop: "20px" }}>
        <h2 style={{fontWeight: "bold",fontSize:"20px"}}>Delete Account</h2>
        <div className="items-center">
        <DeleteUser />
        </div>
        </div>
        </div>
      </div>
    </div>
          
    
  );
};

export default Dashboard;