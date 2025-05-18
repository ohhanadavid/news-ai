import React, { useState, FormEvent, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import { useAuth } from "../context/AuthContext";
import { Input } from "@/components/ui/input";
import { Label } from "@/components/ui/label";
import { Button } from "@/components/ui/button";
import { Card, CardContent, CardHeader, CardTitle } from "@/components/ui/card";
import { IoEyeOffOutline, IoEyeOutline } from "react-icons/io5";

const SignUp: React.FC = () => {
  //const [userName, setUserName] = useState<string>("");
  const [firstName, setFirstName] = useState<string>("");
  const [lastName, setLastName] = useState<string>("");
  const [email, setEmail] = useState<string>("");
  const [phone, setPhone] = useState<string>("");
  const [password, setPassword] = useState<string>("");
  const [confirmPassword, setConfirmPassword] = useState<string>("");
  const [error, setError] = useState<string | null>(null);
  const [success, setSuccess] = useState<string | null>(null);
  const navigate = useNavigate();
  const { register } = useAuth();
  const [showPassword, setShowPassword] = useState(false);
  const [showConfirmPassword, setShowConfirmPassword] = useState(false);

  useEffect(() => {
      // שמירה על הרקע הקודם
      const previousBackground = document.body.style.backgroundColor;
  
      // שינוי הרקע
      document.body.style.backgroundImage = "url('/Images/SignIn.webp')";
      document.body.style.backgroundSize = "cover";
  
      // ניקוי – החזרת הרקע כשהקומפוננטה יוצאת
      return () => {
        document.body.style.backgroundColor = previousBackground;
      };
    }, []);

  const handleSubmit = async (e: FormEvent) => {
    e.preventDefault();
    setError(null);
    setSuccess(null);

    // Validate passwords
    if (password !== confirmPassword) {
      setError("Passwords do not match");
      return;
    }

    // Validate email format
    const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    if (!emailRegex.test(email)) {
      setError("Invalid email format");
      return;
    }

    // Validate phone number (basic validation)
    if (phone.length < 10 || isNaN(Number(phone))) {
      setError("Invalid phone number");
      return;
    }

    try {
      // Call the register function with the required fields
      await register({
        userName: email, 
        firstName,
        lastName,
        email,
        phone,
        password,
      });
      setSuccess("Registration successful! Redirecting...");
      navigate("/dashboard"); // Redirect to dashboard immediately
    } catch (err: any) {
      // Handle specific error messages if available
      setError(err.message || "Failed to register. Please try again.");
    }
  };

  return (
    
 
      <Card className="w-full max-w-md bg-gray-300" style={{ borderRadius: "20px"}}>
        <CardHeader>
          <CardTitle className="text-center">Sign Up</CardTitle>
        </CardHeader>
        <CardContent>
          {error && <p className="text-red-500 text-center">{error}</p>}
          {success && <p className="text-green-500 text-center">{success}</p>}
          <form onSubmit={handleSubmit}>
            {/* <div className="mb-4">
              <Label htmlFor="username">Username</Label>
              <Input
                id="username"
                type="text"
                value={userName}
                onChange={(e) => setUserName(e.target.value)}
                placeholder="Enter your username"
                required
              />
            </div> */}
            <div className="mb-4">
              <Label htmlFor="firstName">First Name</Label>
              <Input
                id="firstName"
                type="text"
                value={firstName}
                onChange={(e) => setFirstName(e.target.value)}
                placeholder="Enter your first name"
                required
              />
            </div>
            <div className="mb-4">
              <Label htmlFor="lastName">Last Name</Label>
              <Input
                id="lastName"
                type="text"
                value={lastName}
                onChange={(e) => setLastName(e.target.value)}
                placeholder="Enter your last name"
                required
              />
            </div>
            <div className="mb-4">
              <Label htmlFor="email">Email</Label>
              <Input
                id="email"
                type="email"
                value={email}
                onChange={(e) => setEmail(e.target.value)}
                placeholder="Enter your email"
                required
              />
            </div>
            <div className="mb-4">
              <Label htmlFor="phone">Phone</Label>
              <Input
                id="phone"
                type="text"
                value={phone}
                onChange={(e) => setPhone(e.target.value)}
                placeholder="Enter your phone number"
                required
              />
            </div>
            <div className="mb-4">
              <Label htmlFor="password">Password</Label>
              <div className="relative">
              <Input
                id="password"
                type={showPassword ? "text" : "password"}
                value={password}
                onChange={(e) => setPassword(e.target.value)}
                placeholder="Enter your password"
                required
              />
               <button
                type="button"
                className="absolute -right-3 top-1/2 -translate-y-1/2  bg-transparent border-none cursor-pointer focus:outline-none"
                onClick={() => setShowPassword((prev) => !prev)}
                tabIndex={-1}
              >
              {showPassword ? <IoEyeOffOutline /> : <IoEyeOutline />}
              </button>
              </div>
            </div>
            <div className="mb-4">
              <Label htmlFor="confirmPassword">Confirm Password</Label>
              <div className="relative">
              <Input
                id="confirmPassword"
                type={showConfirmPassword ? "text" : "password"}
                value={confirmPassword}
                onChange={(e) => setConfirmPassword(e.target.value)}
                placeholder="Confirm your password"
                required
              />
              <button
                type="button"
                className="absolute -right-3 top-1/2 -translate-y-1/2  bg-transparent border-none cursor-pointer focus:outline-none"
                onClick={() => setShowConfirmPassword((prev) => !prev)}
                tabIndex={-1}
              >
                {showConfirmPassword ? <IoEyeOffOutline /> : <IoEyeOutline />}
              </button>
              </div>
            </div>
            <Button type="submit" className="w-full bg-[#739dcc] text-white p-2 rounded hover:bg-[#2a3a5c]">
              Sign Up
            </Button>
          </form>
        </CardContent>
      </Card>
   
    
  );
};

export default SignUp;





