import { useState } from "react";
import Ride from "../../../components/Ride";

//TODO - receber rides por requisição GET ao invés de props
export const AvailableRides = () => {
  const [rides, setRides] = useState([
    {
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
    },
    {
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
    },
    {
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
    },
    {
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
    },
    {
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
    },
    {
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
    },
    {
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
    },
    {
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
    },
  ]);
  return (
    <div
      style={{
        display: "flex",
        flexDirection: "column",
        justifyContent: "space-between",
        gap: "50px",
        width: "100%",
        height: "100%",
      }}
    >
      <h2
        className="page-title"
        style={{ width: "fit-content", alignSelf: "center", marginTop: "20px" }}
      >
        Caronas Disponíveis
      </h2>
      <div
        style={{
          display: "flex",
          flexWrap: "wrap",
          gap: "20px",
          width: "100%",
          justifyContent: "center",
        }}
      >
        {rides.map((ride) => (
          <Ride stats={ride} />
        ))}
      </div>
    </div>
  );
};

export default AvailableRides;
