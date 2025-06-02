import { useEffect, useState } from "react";
import RideSolicitation from "../../../components/RideSolicitation";
import {
  useGetPendingSolicitations,
  useCancelSolicitation,
} from "../../../services/rideSolicitationService";
import { useAuth } from "../../../context/AuthContext";

//TODO - receber rides por requisição GET ao invés de props
export const PassengerRideRequests = () => {
  const { userToken } = useAuth();
  const [isLoading, setIsLoading] = useState(false);

  const [solictitations, setSolicitations] = useState([]);
  const updateSolicitations = async () => {
    try {
      setIsLoading(true);
      const solicitations = await useGetPendingSolicitations(userToken);
      console.log("Updated Rides:", solicitations);
      setSolicitations(solicitations);
    } catch (error) {
      console.error("Error attempting to update solicitations:", error);
    } finally {
      setIsLoading(false);
    }
  };

  useEffect(() => {
    updateSolicitations();
  }, []);

  const handleCancelSolicitation = async (solicitationId) => {
    try {
      const response = await useCancelSolicitation(userToken, solicitationId);
      console.log("Solicitation canceled successfully:", response);
      updateSolicitations();
    } catch (error) {
      console.error("Error attempting to cancel solicitation:", error);
    }
  };

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
        Solicitações de Carona
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
        {solictitations.map((solicitation) => (
          <RideSolicitation
            stats={solicitation}
            key={solicitation.rideSolicitationId}
            isLoading={isLoading}
            cancelSolicitationHandler={() =>
              handleCancelSolicitation(solicitation.rideSolicitationId)
            }
          />
        ))}
      </div>
    </div>
  );
};

export default PassengerRideRequests;
