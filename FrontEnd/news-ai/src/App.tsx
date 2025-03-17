import React, { ReactNode } from "react";
import { BrowserRouter as Router, Routes, Route, Navigate } from "react-router-dom";
import Login from "./pages/Login";
import SignUp from "./pages/SignUp";
import Dashboard from "./pages/Dashboard";
import AddLanguage from "./pages/AddLanguage";
import AddCategory from "./pages/AddCategory";
import NewsSubscription from "./pages/NewsSubscription";
import { AuthProvider, useAuth } from "./context/AuthContext";
//import "./styles/globals.css";

const PrivateRoute = ({ children }: { children: ReactNode }) => {
  const { user } = useAuth();
  return user ? children : <Navigate to="/login" replace />;
};

function App() {
  return (
    <Router>
      <AuthProvider>
        <Routes>
          <Route path="/login" element={<Login />} />
          <Route path="/signup" element={<SignUp />} />
          <Route path="/dashboard" element={<PrivateRoute><Dashboard /></PrivateRoute>} />
          <Route path="/add-language" element={<PrivateRoute><AddLanguage /></PrivateRoute>} />
          <Route path="/add-category" element={<PrivateRoute><AddCategory /></PrivateRoute>} />
          <Route path="/news-subscription" element={<PrivateRoute><NewsSubscription /></PrivateRoute>} />
          <Route path="*" element={<Navigate to="/login" replace />} />
        </Routes>
      </AuthProvider>
    </Router>
  );
}

export default App;










