import Ride from "../../../components/Ride";
import { useState } from "react";
import { Button } from "@chakra-ui/react";
const PassengerDashBoard = () => {
  const [cardButton, setCardButton] = useState(
    <Button variant="outline">Solicitar</Button>
  );
  const [stats, setStats] = useState({
    driverName: "John Doe",
    car: {
      brand: "Toyota",
      model: "Corolla",
      color: "Silver",
      seats: 5,
      licensePlate: "ABC1D23",
    },
    rideDate: "2023-10-01",
    rideTime: "10:00 AM",
    pickupLocation: "123 Main St",
    dropoffLocation: "456 Elm St",
    availableSeats: 3,
  });
  return (
    <>
      <p style={{ fontSize: "24px", fontWeight: "bold" }}>
        Welcome to the Driver Dashboard! Here you can manage your rides, view
        your earnings, and update your profile.
      </p>
      <Ride stats={stats} cardButton={cardButton} />
    </>
  );
};

export default PassengerDashBoard;
