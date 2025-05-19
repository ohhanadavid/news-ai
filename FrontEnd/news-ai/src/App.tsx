import { ReactNode } from "react";
import { BrowserRouter as Router, Routes, Route, Navigate } from "react-router-dom";
import Login from "./pages/Login";
import SignUp from "./pages/SignUp";
import Dashboard from "./pages/Dashboard";
import { AuthProvider, useAuth } from "./context/AuthContext";
import { LanguagesProvider } from "./context/LanguagesContext";
import { CategoryProvider } from "./context/CategoryContext";
import { NewsProvider } from "./context/NewsViewContext";
import { Toaster } from "sonner";
import "./App.css";

const PrivateApp = () => (
  <NewsProvider>
    <LanguagesProvider>
      <CategoryProvider>
        <Toaster />
        <Routes>
          <Route path="/dashboard" element={<Dashboard />} />
          <Route path="*" element={<Navigate to="/dashboard" replace />} />
        </Routes>
      </CategoryProvider>
    </LanguagesProvider>
  </NewsProvider>
);

function App() {
  return (
    <Router>
      <AuthProvider>
        <Routes>
          <Route path="/login" element={<Login />} />
          <Route path="/signup" element={<SignUp />} />
          <Route
            path="*"
            element={
              <RequireAuth>
                <PrivateApp />
              </RequireAuth>
            }
          />
        </Routes>
      </AuthProvider>
    </Router>
  );
}


function RequireAuth({ children }: { children: ReactNode }) {
  const { user,tokenStr } = useAuth();
  const token = localStorage.getItem(tokenStr);
  if (user === undefined) return <p>Loading...</p>;
  if (!user &&!token) return <Navigate to="/login" replace />;
  return <>{children}</>;
}

export default App;

