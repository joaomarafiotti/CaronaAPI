import { Routes, Route, Navigate } from "react-router-dom";
import LoginPage from "./pages/LoginPage";
import RegisterPassengerPage from "./pages/RegisterPassengerPage";
import RegisterDriverPage from "./pages/RegisterDriverPage";
import "./App.css";
import { AuthProvider } from "./context/AuthContext";
import DashBoardLayout from "./pages/dashboard/layout/DashBoardLayout";
import DriverDashBoard from "./pages/dashboard/driver/DriverDashBoard";
import PassengerDashBoard from "./pages/dashboard/passenger/PassengerDashBoard";
import RegisterCarPage from "./pages/RegisterCarPage";
import AvailableRides from "./pages/dashboard/passenger/AvailableRides";

function App() {
  return (
    <AuthProvider>
      <Routes>
        <Route path="/dashboard" element={<DashBoardLayout />}>
          <Route index element={<DriverDashBoard />} />
          <Route path="driver" element={<DriverDashBoard />} />
          <Route path="passenger" element={<PassengerDashBoard />} />
          <Route path="passenger/avalable-rides" element={<AvailableRides />} />
          <Route path="passenger/rides" element={<PassengerDashBoard />} />
          <Route
            path="passenger/ride-requests"
            element={<PassengerDashBoard />}
          />
          <Route path="driver/cars/register" element={<RegisterCarPage />} />
        </Route>
        <Route path="/login" element={<LoginPage />} />
        <Route path="/register-passenger" element={<RegisterPassengerPage />} />
        <Route path="/register-driver" element={<RegisterDriverPage />} />
        <Route path="*" element={<Navigate to="/login" replace={true} />} />
      </Routes>
    </AuthProvider>
  );
}

export default App;
