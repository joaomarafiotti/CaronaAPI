import { useState, useEffect } from "react";
import Ride from "../../../components/Ride";
import { useGetAvailableRides } from "../../../services/rideService";
import { useAuth } from "../../../context/AuthContext";
import { useCreateSolicitation } from "../../../services/rideSolicitationService";

//TODO - receber rides por requisição GET ao invés de props
export const AvailableRides = () => {
  const { userToken } = useAuth();
  const [rides, setRides] = useState([]);
  const [isLoading, setIsLoading] = useState(false);

  const updateRides = async () => {
    try {
      const rides = await useGetAvailableRides(userToken);
      setRides(rides);
    } catch (error) {
      console.error("Error attempting to update rides:", error);
    }
  };

  const handleSolicitation = async (rideId) => {
    try {
      setIsLoading(true);
      await useCreateSolicitation(userToken, rideId);
    } catch (error) {
      console.error("Erro ao solicitar carona:", error);
    } finally {
      setIsLoading(false);
      updateRides();
    }
  };

  useEffect(() => {
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
          <Ride
            stats={ride}
            isAvailable={ride.status === "WAITING"}
            key={ride.uuid}
            isDone={ride.status === "FINISHED" || ride.status === "CANCELLED"}
            solicitationHandler={() => handleSolicitation(ride.uuid)}
            isLoading={isLoading}
          />
        ))}
      </div>
    </div>
  );
};

export default AvailableRides;
