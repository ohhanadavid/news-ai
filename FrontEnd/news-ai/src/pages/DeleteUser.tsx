import React, { useState, FormEvent } from "react";
import { useAuth } from "../context/AuthContext";
import { useNavigate, Link } from "react-router-dom";
import { MdCancel, MdCheckCircleOutline, MdOutlineWarningAmber } from "react-icons/md";

const DeleteUser = () => {
  const [error, setError] = useState<string | null>(null);
  const { deleteUser } = useAuth();
  const navigate = useNavigate();

  const handleDelete = async (e: FormEvent) => {
    e.preventDefault();
    try {
      await deleteUser();
      console.log("Login successful! Redirecting...");
    } catch (err) {
      console.error(err);
      setError("Failed to login. Please check your credentials.");
    }
  };

  return (
    <div className="flex justify-center items-center h-screen bg-gray-100">
      <div className="bg-white p-6 rounded-lg shadow-lg w-96">
        <h2
          className="text-2xl font-bold mb-4"
          style={{
            display: "flex",
            alignItems: "center",
            justifyContent: "center",
            color: "red",
            fontSize: "72px",
          }}
        >
          <MdOutlineWarningAmber />
        </h2>
        {error && <p className="text-red-500">{error}</p>}
        <form onSubmit={handleDelete}>
          <div className="mb-4" style={{ display: "flex", alignItems: "center", justifyContent: "center" }}>
            <h2 style={{ marginRight: "10px" }}>
              <MdOutlineWarningAmber color="red" />
            </h2>
            <h2 style={{ margin: "0 10px" }}>Are you sure you want to delete your account?</h2>
            <h2 style={{ marginLeft: "10px" }}>
              <MdOutlineWarningAmber color="red" />
            </h2>
          </div>
          <div style={{ display: "flex", justifyContent: "center", gap: "20px" }}>
            <button
              type="submit"
              className="w-full bg-blue-500 text-white p-2 rounded hover:bg-blue-600"
              style={{
                background: "none",
                border: "none",
                fontSize: "24px",
                cursor: "pointer",
                color: "green",
              }}
              aria-label="Submit"
            >
              <MdCheckCircleOutline />
            </button>
            <button
              type="button"
              onClick={() => navigate("/dashboard")}
              style={{
                background: "none",
                border: "none",
                fontSize: "24px",
                cursor: "pointer",
                color: "red",
              }}
              aria-label="Cancel"
            >
              <MdCancel />
            </button>
          </div>
        </form>
      </div>
    </div>
  );
};

export default DeleteUser;