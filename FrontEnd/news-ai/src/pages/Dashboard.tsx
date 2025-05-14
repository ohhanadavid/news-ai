


import React, { useState, useEffect } from "react";
import { Link, useLocation, useNavigate } from "react-router-dom";
import { useAuth } from "../context/AuthContext";
import MyLanguage from "../componnent/MyLanguage";
import MyCategoryies from "../componnent/MyCategory";
import AddLanguage from "./AddLanguage";
import AddCategory from "./AddCategory";
import { Button } from "@/components/ui/button";
import NewsSubscription from "./NewsSubscription";
import { Card, CardContent, CardFooter, CardHeader, CardTitle } from "@/components/ui/card";
import { Tabs, TabsContent, TabsList, TabsTrigger } from "@/components/ui/tabs";
import Home from "@/componnent/Home";
import Menu from "@/componnent/Menu";
import DeleteUserDialog from "@/componnent/DeleteUserDialog";
import UpdateUser from "@/componnent/UpdateUser";
import ChancePassword from "@/componnent/ChancePassword";
import { IoHome, IoHomeOutline } from "react-icons/io5";
import { TbMessageLanguage } from "react-icons/tb";
import { BiCategoryAlt } from "react-icons/bi";


const Dashboard = () => {
  const { user, loading, handleRefreshToken } = useAuth();
  const navigate = useNavigate();
  const location = useLocation();
  const token = location.state?.token;
  const [isDeleteOpen, setDeleteIsOpen] = useState(false);
  const [activeTab, setActiveTab] = useState("home");

  useEffect(() => {
    const token = localStorage.getItem("token");
    if (!user && !token) {
      navigate("/login");
    }
    const previousBackground = document.body.style.backgroundColor;
    document.body.style.backgroundImage = "url('/Images/background.webp')";
    document.body.style.backgroundSize = "cover";
    return () => {
      document.body.style.backgroundColor = previousBackground;
    };
  }, [user, navigate]);

  if (loading) {
    return <div>טוען...</div>;
  }

  return (
    <div id="firstdiv" style={{ display: "flex", padding: "20px" }}>
      <div
        id="secondtdiv"
        style={{
          borderRadius: "20px",
          padding: "20px",
          marginRight: "20px",
        }}
      >
        <div
          id="thirdtdiv"
          className="content-box"
          style={{
            backgroundColor: "rgba(240, 240, 240, 0.8)",
            padding: "20px",
            flexGrow: 1,
            overflowY: "auto",
            borderRadius: "20px",
          }}
        >
          <div
            id="fourdiv"
            className="flex flex-col items-center bg-gray-100 rounded-lg shadow-lg p-6"
          >
            <Tabs value={activeTab} onValueChange={setActiveTab} className="w-[400px]">
              <Menu 
                setActiveTab={setActiveTab}
              />
              <TabsContent value="UpdateUser">
                <UpdateUser/>
              </TabsContent>
              <TabsContent value="ChangePassword">
                <ChancePassword/>
              </TabsContent>
              <TabsContent value="home">
                <Home />
                
              </TabsContent>
              <TabsContent value="language">
                <Card>
                  <CardHeader>
                    <CardTitle>Languages</CardTitle>
                  </CardHeader>
                  <CardContent className="space-y-2">
                    <div style={{ marginTop: "20px" }}>
                      <MyLanguage />
                    </div>
                  </CardContent>
                  <CardFooter className="flex justify-center items-center">
                    <AddLanguage token={token} />
                  </CardFooter>
                </Card>
              </TabsContent>
              <TabsContent value="perference">
                <Card>
                  <CardHeader>
                    <CardTitle>Preferences</CardTitle>
                  </CardHeader>
                  <CardContent className="space-y-2 align-center">
                    <MyCategoryies />
                  </CardContent>
                  <CardFooter className="flex justify-center items-center">
                    <AddCategory />
                  </CardFooter>
                </Card>
              </TabsContent>
              <TabsList className="grid w-full grid-cols-3 gap-x-1">
                <TabsTrigger value="language"><TbMessageLanguage /></TabsTrigger>
                <TabsTrigger value="home"><IoHomeOutline /></TabsTrigger>
                <TabsTrigger value="perference"><BiCategoryAlt /></TabsTrigger>
              </TabsList>
              <NewsSubscription />
            </Tabs>
          
          </div>
        </div>
      </div>
    </div>
  );
};

export default Dashboard;