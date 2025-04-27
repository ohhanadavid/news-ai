import React, { useState, FormEvent, useEffect } from "react";
import {  Link } from "react-router-dom";
import { useAuth } from "../context/AuthContext";

const Login: React.FC = () => {
  const [userIdentifier, setUserIdentifier] = useState<string>("");
  const [password, setPassword] = useState<string>("");
  const [error, setError] = useState<string | null>(null);
 
  const { login } = useAuth();

  useEffect(() => {
    // שמירה על הרקע הקודם
    
    console.log("login useEffect called");
    const previousBackground = document.body.style.backgroundColor;
    console.log("log in previousBackground:", previousBackground);
    // שינוי הרקע
    document.body.style.backgroundImage = "url('/Images/login.webp')";
    document.body.style.backgroundSize = "cover";
    console.log("log in document.body.style.backgroundImage:", document.body.style.backgroundImage);

    // ניקוי – החזרת הרקע כשהקומפוננטה יוצאת
    return () => {
      document.body.style.backgroundColor = previousBackground;
    };
  }, []);

  const handleSubmit = async (e: FormEvent) => {
    e.preventDefault();
    try {
      await login(userIdentifier, password);
      console.log("Login successful! Redirecting...");
     
    } catch (err) {
      console.error(err);
      setError("Failed to login. Please check your credentials.");
    }
    console.log("UserIdentifier:", userIdentifier);
    console.log("Password:", password);
  };

  return (
   
     <div className="flex justify-center items-center min-h-screen"
    //  style=
    //  {{backgroundColor: "rgba(240, 240, 240, 0.8)",
    //    padding: "20px" ,borderRadius: "20px"}}
     >
   <div className="bg-white p-8 rounded-xl shadow-xl w-full max-w-md"
      style=
    {{backgroundColor: "rgba(240, 240, 240, 0.9)",
      padding: "20px" ,borderRadius: "20px"}}>
        <h2 className="text-2xl font-bold mb-4">Login</h2>
        {error && <p className="text-red-500">{error}</p>}
        <form onSubmit={handleSubmit}>
          <div className="mb-4">
            <label className="block text-gray-700">Email or user name</label>
            <input
              type="string"
              value={userIdentifier}
              onChange={(e) => setUserIdentifier(e.target.value)}
              className="w-full p-2 border border-gray-300 rounded mt-1"
              required
            />
          </div>
          <div className="mb-4">
            <label className="block text-gray-700">Password</label>
            <input
              type="password"
              value={password}
              onChange={(e) => setPassword(e.target.value)}
              className="w-full p-2 border border-gray-300 rounded mt-1"
              required
            />
          </div>
          <button type="submit" className="w-full bg-blue-500 text-white p-2 rounded hover:bg-blue-600">
            Login
          </button>
        </form>
        <p className="mt-4">
          Don't have an account? <Link to="/signup" className="text-blue-500">Sign Up</Link>
        </p>
      </div>
    </div>
    
  );
};

export default Login;