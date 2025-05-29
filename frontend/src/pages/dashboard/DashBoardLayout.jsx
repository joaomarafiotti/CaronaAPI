import React from "react";
import { Outlet } from "react-router-dom";

const DashBoardLayout = () => {
  return (
    <div style={{ display: "flex", minHeight: "100vh" }}>
      {/* Sidebar */}
      <aside style={{ width: "220px", background: "#222", color: "#fff", padding: "1rem" }}>
        <h2>Dashboard</h2>
        {/* Add navigation links here if needed */}
      </aside>
      {/* Main Content */}
      <main style={{ flex: 1, background: "#f5f5f5", padding: "2rem" }}>
        <Outlet />
      </main>
    </div>
  );
};

export default DashBoardLayout;