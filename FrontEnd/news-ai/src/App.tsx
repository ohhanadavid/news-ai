import  { ReactNode } from "react";
import { BrowserRouter as Router, Routes, Route, Navigate } from "react-router-dom";
import Login from "./pages/Login";
import SignUp from "./pages/SignUp";
import Dashboard from "./pages/Dashboard";
import { AuthProvider, useAuth } from "./context/AuthContext";
import Update from "./pages/Update";
import { LanguagesProvider } from "./context/LanguagesContext";
import { CategoryProvider } from "./context/CategoryContext";

import "./App.css";
import {Toaster} from "sonner";

const PrivateRoute = ({ children }: { children: ReactNode }) => {
  console.log("PrivateRoute rendered"); 
  const { user } = useAuth();
  console.log("user in PrivateRoute:", user); 
  if (user === undefined) {
    return <p>Loading...</p>; 
  }
  const token= localStorage.getItem("token");
  if (!token && !user) {
    console.log("Token not found, redirecting to login"); 
    return <Navigate to="/login" replace />;
  }
  else{
    console.log("Token found, user is authenticated"); 
     
    return children; 
  }
  console.log("token in PrivateRoute:", token?true:false);
  console.log(user) 

  // return user ? children : <Navigate to="/login" replace />;
};

function App() {
  return (
    <Router>
    <AuthProvider>

    <LanguagesProvider>
      <CategoryProvider>
        <Toaster></Toaster>
        <Routes>
          <Route path="/login" element={<Login />} />
          <Route path="/signup" element={<SignUp />} />
          <Route path="/dashboard" element={<PrivateRoute><Dashboard /></PrivateRoute>} />

          <Route path="/update" element={<PrivateRoute><Update /></PrivateRoute>} />
          
          <Route path="*" element={<Navigate to="/login" replace />} />
        </Routes>
      </CategoryProvider>
    </LanguagesProvider>

    </AuthProvider>
    </Router>
  );
}

export default App;

