import React, { useState } from "react";
import { Link } from "react-router-dom";
import { useAuth } from "../context/AuthContext";
import MyLanguage from "../componnent/MyLanguage";
import MyCategoryies from "../componnent/MyCategory";
import { HiOutlinePencilSquare } from "react-icons/hi2";
import { IoMdAddCircleOutline } from "react-icons/io";
import { MdDelete } from "react-icons/md";
import { Delete } from "lucide-react";
import DeleteUser from "./DeleteUser";
import AddLanguage from "./AddLanguage";
import AddCategory from "./AddCategory";
import { Button } from "@/components/ui/button";

const Dashboard = () => {
  const { user } = useAuth();
  const [isDialogOpen, setIsDialogOpen] = React.useState(false);
  const [isCategoryDialogOpen, setIsCategoryDialogOpen] = useState(false);

  return (
    <div style={{ padding: "20px" }}>
      <div>
      <h1>NewsAI</h1>
      <p>Welcome to the NewsAi APP!</p>
      </div>
     
      <div  style={{
    display: "flex",
    alignItems: "center",
    justifyContent: "center",
    padding: 0,
    margin: 0,
    height: "auto",
    marginTop: "10px", // שליטה על הגובה
  }}  >
        <h2 style={{
      margin: 0,
      padding: 0,
      fontSize: "20px",
      lineHeight: "1",
    }}>
          <b>User Information </b>
        </h2>
        <Link to="/update" >
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
              <HiOutlinePencilSquare style={{ fontSize: "20px", verticalAlign: "middle" }}/>
            </button>
          </Link>
        </div>
        <div style={{ marginTop: "10px" }}>
        <p>
          <b>name:</b> {user.name}
        </p>
        <p>
          <b>email:</b> {user.email}
        </p>
        <p>
          <b>phone:</b> {user.phone}
        </p>
        </div>
      <div style={{ marginTop: "20px" }}>
        <h2>Quick Links</h2>
        <ul>
          <li>
            <Link to="/news-subscription">Manage News Subscriptions</Link>
          </li>
        </ul>
      </div>

      <div style={{ marginTop: "20px" }}>
        <h2>
          My language{" "}
          <Button onClick={() => setIsDialogOpen(true)} className="bg-blue-600 hover:bg-blue-700"
             style={{
              
              border: "none",
              fontSize: "24px",
              cursor: "poinr",
              
            }}
            aria-label="Add Language">
          <IoMdAddCircleOutline />
          </Button>
          <AddLanguage 
            isOpen={isDialogOpen} 
            onClose={() => setIsDialogOpen(false)} 
          />
        </h2>
        <MyLanguage/>
      </div>
    
      <div style={{ marginTop: "20px" }}>
        <h2>
          My categories{" "}
         
           <Button 
        onClick={() => setIsCategoryDialogOpen(true)} 
        className="bg-blue-600 hover:bg-blue-700 mr-2"
      >
        <IoMdAddCircleOutline />
      </Button>
      
      {/* Category dialog component */}
      <AddCategory 
        isOpen={isCategoryDialogOpen} 
        onClose={() => setIsCategoryDialogOpen(false)} 
      />
        </h2>
        <MyCategoryies />
      </div>
    
      <div>
        <h2>Delete Account</h2>
        <DeleteUser />
      </div>
    </div>
  );
};

export default Dashboard;