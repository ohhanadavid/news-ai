import React from "react";
import { Link } from "react-router-dom";
import { useAuth } from "../context/AuthContext";
import MyLanguage from "../componnent/MyLanguage";
import MyCategoryies from "../componnent/MyCategory";
import { HiOutlinePencilSquare } from "react-icons/hi2";
import { IoMdAddCircleOutline } from "react-icons/io";
import { MdDelete } from "react-icons/md";

const Dashboard = () => {
  const { user } = useAuth();
  return (
    <div>
      <h1>NewsAI</h1>
      <p>Welcome to the NewsAi APP!</p>
      <div>
        <h2>
          User Information 
          <Link to="/update" style={{ textDecoration: "none" }}>
          <button
              style={{
                background: "none",
                border: "none",
                fontSize: "24px",
                cursor: "pointer",
              }}
              aria-label="Update User"
            >
              <HiOutlinePencilSquare />
            </button>
          </Link>
        </h2>
        <p>
          <b>name:</b> {user.name}
        </p>
        <p>
          <b>email:</b> {user.email}
        </p>
        <p>
          <b>phone:</b> {user.phone}
        </p>
      </div>
      <div>
        <h2>Quick Links</h2>
        <ul>
          <li>
            <Link to="/news-subscription">Manage News Subscriptions</Link>
          </li>
        </ul>
      </div>

      <div>
        <h2>
          My language{" "}
          <Link to="/add-language" style={{ textDecoration: "none" }}>
            <button
              style={{
                background: "none",
                border: "none",
                fontSize: "24px",
                cursor: "pointer",
              }}
              aria-label="Add Language"
            >
              <IoMdAddCircleOutline />
            </button>
          </Link>
        </h2>
        <MyLanguage />
      </div>
      <div>
        <h2>
          My categories{" "}
          <Link to="/add-category" style={{ textDecoration: "none" }}>
          <button
              style={{
                background: "none",
                border: "none",
                fontSize: "24px",
                cursor: "pointer",
              }}
              aria-label="Add Category"
            >
              <IoMdAddCircleOutline />
            </button>
          </Link>
        </h2>
        <MyCategoryies />
      </div>
      <div>
        <h2>Delete Account</h2>
        <Link to="/deleteUser" style={{ textDecoration: "none" }}>
          <button
            style={{
              background: "none",
              border: "none",
              fontSize: "24px",
              cursor: "pointer",
            }}
            aria-label="Delete User"
          >
            <MdDelete />
          </button>
        </Link>
    </div>
    </div>
  );
};

export default Dashboard;