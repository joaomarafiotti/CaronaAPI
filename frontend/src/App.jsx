import { Routes, Route, Navigate, Router } from "react-router-dom";
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
import ViewCarPage from "./pages/ViewCarPage";
import PassengerRides from "./pages/dashboard/passenger/PassengerRides";
import PassengerRideRequests from "./pages/dashboard/passenger/PassengerRideRequests";
import ProtectedPassengerRoute from "./components/ProtectedPassengerRoute";
import ProtectedDriverRoute from "./components/ProtectedDriverRoute";
import PassengerProfile from "./pages/dashboard/passenger/PassengerProfile";
import DriverProfile from "./pages/dashboard/driver/DriverProfile";
import RegisterRidePage from "./pages/RegisterRidePage";

function App() {
  return (
    <AuthProvider>
      <Routes>
        <Route path="/dashboard" element={<DashBoardLayout />}>
          <Route
            path="driver"
            element={
              <ProtectedDriverRoute>
                <DriverDashBoard />
              </ProtectedDriverRoute>
            }
          />
          <Route
            path="passenger"
            element={
              <ProtectedPassengerRoute>
                <PassengerDashBoard />
              </ProtectedPassengerRoute>
            }
          />
          <Route
            path="passenger/avalable-rides"
            element={
              <ProtectedPassengerRoute>
                <AvailableRides />
              </ProtectedPassengerRoute>
            }
          />
          <Route
            path="passenger/profile"
            element={
              <ProtectedPassengerRoute>
                <PassengerProfile />
              </ProtectedPassengerRoute>
            }
          />
          <Route
            path="passenger/rides"
            element={
              <ProtectedPassengerRoute>
                <PassengerRides />
              </ProtectedPassengerRoute>
            }
          />
          <Route
            path="passenger/ride-requests"
            element={
              <ProtectedPassengerRoute>
                <PassengerRideRequests />
              </ProtectedPassengerRoute>
            }
          />
          <Route
            path="driver/profile"
            element={
              <ProtectedDriverRoute>
                <DriverProfile />
              </ProtectedDriverRoute>
            }
          />
          <Route
            path="driver/cars/register"
            element={
              <ProtectedDriverRoute>
                <RegisterCarPage />
              </ProtectedDriverRoute>
            }
          />
          <Route
            path="driver/cars/view"
            element={
              <ProtectedDriverRoute>
                <ViewCarPage />
              </ProtectedDriverRoute>
            }
          />
          <Route
            path="driver/rides/register"
            element={
              <ProtectedDriverRoute>
                <RegisterRidePage />
              </ProtectedDriverRoute>
            }
          />
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
