import { useState, useEffect } from "react";
import Ride from "../../../components/Ride";
import { useGetRides } from "../../../services/rideService";
import { useAuth } from "../../../context/AuthContext";

//TODO - receber rides por requisição GET ao invés de props
export const PassengerRides = () => {
  const { userToken } = useAuth();
  const [rides, setRides] = useState([]);

  useEffect(() => {
    const updateRides = async () => {
      try {
        const rides = await useGetRides(userToken);
        console.log("Updated Rides:", rides);
        setRides(rides);
      } catch (error) {
        console.error("Error attempting to update rides:", error);
      }
    };

    updateRides();
  }, []);

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
        Minhas Caronas
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
          <Ride
            stats={ride}
            isAvailable={ride.status === "AVAILABLE"}
            key={ride.uuid}
            isDone={ride.status === "FINISHED" || ride.status === "CANCELLED"}
          />
        ))}
      </div>
    </div>
  );
};

export default PassengerRides;
