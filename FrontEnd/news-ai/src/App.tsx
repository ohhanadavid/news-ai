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

const PrivateRoute = ({ children }: { children: ReactNode }) => {
  console.log("PrivateRoute rendered"); // ✅ הוספנו לוג
  const { user } = useAuth();
  console.log("user in PrivateRoute:", user); // ✅ הוספנו לוג
  if (user === undefined) {
    return <p>Loading...</p>; // מוודא שהמשתמש נטען לפני מעבר
  }

  return user ? children : <Navigate to="/login" replace />;
};

function App() {
  return (
    <Router>
    <AuthProvider> {/* ✅ הזזנו את זה מחוץ ל-Router */}
    <LanguagesProvider> {/* ✅ ספק השפות */}
      <CategoryProvider>
        <Routes>
          <Route path="/login" element={<Login />} />
          <Route path="/signup" element={<SignUp />} />
          <Route path="/dashboard" element={<PrivateRoute><Dashboard /></PrivateRoute>} />

          <Route path="/update" element={<PrivateRoute><Update /></PrivateRoute>} />
          
          <Route path="*" element={<Navigate to="/login" replace />} />
        </Routes>
      </CategoryProvider> {/* ✅ ספק הקטגוריות */}
    </LanguagesProvider> {/* ✅ ספק השפות */}
    </AuthProvider>
    </Router>
  );
}

export default App;

