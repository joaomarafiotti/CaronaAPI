import { Routes, Route, Navigate, Router } from "react-router-dom";
import LoginPage from "./pages/LoginPage";
import RegisterPassengerPage from "./pages/RegisterPassengerPage";
import RegisterDriverPage from "./pages/RegisterDriverPage";
import "./App.css";
import { AuthProvider } from "./context/AuthContext";
import RegisterCarPage from "./pages/RegisterCarPage";
import AvailableRides from "./pages/dashboard/passenger/AvailableRides";
import ViewCarPage from "./pages/ViewCarPage";
import PassengerRides from "./pages/dashboard/passenger/PassengerRides";
import PassengerRideRequests from "./pages/dashboard/passenger/PassengerRideRequests";
import ProtectedPassengerRoute from "./components/ProtectedPassengerRoute";
import ProtectedDriverRoute from "./components/ProtectedDriverRoute";
import RegisterRidePage from "./pages/RegisterRidePage";
import DriverDashBoardLayout from "./pages/dashboard/layout/DriverDashBoardLayout";
import PassengerDashBoardLayout from "./pages/dashboard/layout/PassengerDashBoardLayout";
import ViewRidesPage from "./pages/ViewRidesPage";
import UserProfile from "./pages/dashboard/passenger/UserProfile";
import ViewSolicitationNotifications from "./pages/dashboard/driver/ViewSolicitationNotifications";

function App() {
  return (
    <AuthProvider>
      <Routes>
        {/* Driver Routes */}
        <Route path="/dashboard/driver" element={<DriverDashBoardLayout />}>
          <Route
            path="solicitations/pending"
            element={
              <ProtectedDriverRoute>
                <ViewSolicitationNotifications />
              </ProtectedDriverRoute>
            }
          />
          <Route
            path=""
            element={<Navigate to="/dashboard/driver/profile" replace={true} />}
          />
          <Route
            path="cars/register"
            element={
              <ProtectedDriverRoute>
                <RegisterCarPage />
              </ProtectedDriverRoute>
            }
          />
          <Route
            path="cars/view"
            element={
              <ProtectedDriverRoute>
                <ViewCarPage />
              </ProtectedDriverRoute>
            }
          />
          <Route
            path="rides/register"
            element={
              <ProtectedDriverRoute>
                <RegisterRidePage />
              </ProtectedDriverRoute>
            }
          />
          <Route
            path="rides/view"
            element={
              <ProtectedDriverRoute>
                <ViewRidesPage />
              </ProtectedDriverRoute>
            }
          />
          <Route
            path="profile"
            element={
              <ProtectedDriverRoute>
                <UserProfile />
              </ProtectedDriverRoute>
            }
          />
        </Route>

        {/* Passenger Routes */}
        <Route
          path="/dashboard/passenger"
          element={<PassengerDashBoardLayout />}
        >
          <Route
            path=""
            element={
              <Navigate to="/dashboard/passenger/profile" replace={true} />
            }
          />
          <Route
            path="avalable-rides"
            element={
              <ProtectedPassengerRoute>
                <AvailableRides />
              </ProtectedPassengerRoute>
            }
          />
          <Route
            path="profile"
            element={
              <ProtectedPassengerRoute>
                <UserProfile />
              </ProtectedPassengerRoute>
            }
          />
          <Route
            path="rides"
            element={
              <ProtectedPassengerRoute>
                <PassengerRides />
              </ProtectedPassengerRoute>
            }
          />
          <Route
            path="ride-requests"
            element={
              <ProtectedPassengerRoute>
                <PassengerRideRequests />
              </ProtectedPassengerRoute>
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
