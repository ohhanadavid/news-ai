import React from "react";
import { Link } from "react-router-dom";
import { useAuth } from "../context/AuthContext";
import MyLanguage from "../componnent/MyLanguage";
import MyCategoryies from "../componnent/MyCategory";

const Dashboard = () => {
  const { user } = useAuth();
  return (
    <div>
      <h1>NewsAI</h1>
      <p>Welcome to the NewsAi APP!</p>
      <div>
        <h2>User Information <Link to="/updateUser">Update User</Link></h2>
        <p><b>name:</b> {user.name}</p>
        <p><b>email:</b> {user.email}</p>
        <p><b>phone:</b> {user.phone}</p>
      </div>
      <div>
        <h2>Quick Links</h2>
        <ul>
          <li><Link to="/add-language">Add Language</Link></li>
          <li><Link to="/add-category">Add Category</Link></li>
          <li><Link to="/news-subscription">Manage News Subscriptions</Link></li>
        </ul>
      </div>
      
      <div>
        <h2>My language</h2>
        <MyLanguage />
      </div>
      <div>
        <h2>My categories</h2>
        <MyCategoryies/>
      </div>
    </div>
  );
};

export default Dashboard;