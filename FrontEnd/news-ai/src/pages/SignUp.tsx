import React, { useState, FormEvent, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import { useAuth } from "../context/AuthContext";
import { Input } from "@/components/ui/input";
import { Label } from "@/components/ui/label";
import { Button } from "@/components/ui/button";
import { Card, CardContent, CardHeader, CardTitle } from "@/components/ui/card";

const SignUp: React.FC = () => {
  const [userName, setUserName] = useState<string>("");
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

  useEffect(() => {
      // שמירה על הרקע הקודם
      const previousBackground = document.body.style.backgroundColor;
  
      // שינוי הרקע
      document.body.style.backgroundImage = "url(/SignIn.png)";
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
        userName,
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
    
    <div className="flex justify-center items-center min-h-screen bg-gray-100 " 
    style={{
      borderRadius: "20px",
      backgroundColor: "rgba(240, 240, 240, 0.6)",
    }}>
      <Card className="w-full max-w-md" style={{ borderRadius: "20px", backgroundColor: "rgba(240, 240, 240, 0.8)" }}>
        <CardHeader>
          <CardTitle className="text-center">Sign Up</CardTitle>
        </CardHeader>
        <CardContent>
          {error && <p className="text-red-500 text-center">{error}</p>}
          {success && <p className="text-green-500 text-center">{success}</p>}
          <form onSubmit={handleSubmit}>
            <div className="mb-4">
              <Label htmlFor="username">Username</Label>
              <Input
                id="username"
                type="text"
                value={userName}
                onChange={(e) => setUserName(e.target.value)}
                placeholder="Enter your username"
                required
              />
            </div>
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
              <Input
                id="password"
                type="password"
                value={password}
                onChange={(e) => setPassword(e.target.value)}
                placeholder="Enter your password"
                required
              />
            </div>
            <div className="mb-4">
              <Label htmlFor="confirmPassword">Confirm Password</Label>
              <Input
                id="confirmPassword"
                type="password"
                value={confirmPassword}
                onChange={(e) => setConfirmPassword(e.target.value)}
                placeholder="Confirm your password"
                required
              />
            </div>
            <Button type="submit" className="w-full">
              Sign Up
            </Button>
          </form>
        </CardContent>
      </Card>
    </div>
    
  );
};

export default SignUp;





