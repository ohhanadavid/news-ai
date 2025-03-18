import React from "react";
import { Link } from "react-router-dom";

const Dashboard = () => {
  return (
    <div>
      <h1>Dashboard</h1>
      <p>Welcome to the Dashboard!</p>
      <div>
        <h2>Quick Links</h2>
        <ul>
          <li><Link to="/add-language">Add Language</Link></li>
          <li><Link to="/add-category">Add Category</Link></li>
          <li><Link to="/news-subscription">Manage News Subscriptions</Link></li>
        </ul>
      </div>
      <div>
        <h2>User Information</h2>
        <p>Here you can display user-specific information or recent activity.</p>
      </div>
      <div>
        <h2>Statistics</h2>
        <p>Show relevant statistics or reports here.</p>
      </div>
      <div>
        <h2>Notifications</h2>
        <p>Display any notifications or alerts for the user.</p>
      </div>
    </div>
  );
};

export default Dashboard;