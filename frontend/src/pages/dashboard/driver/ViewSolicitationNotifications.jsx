import RideSolicitationNotification from "../../../components/RideSolicitationNotification";
import { useState, useEffect } from "react";
import { Spinner } from "@chakra-ui/react";
import { useAuth } from "../../../context/AuthContext";
import {
  useGetDriverPendingSolicitations,
  useAcceptSolicitation,
  useRejectSolicitation,
} from "../../../services/rideSolicitationService";

const ViewSolicitationNotifications = () => {
  const { userToken } = useAuth();
  const [isLoading, setIsLoading] = useState(false);
  const [solicitations, setSolicitations] = useState([]);

  const updateSolicitations = async () => {
    try {
      setIsLoading(true);
      const solicitations = await useGetDriverPendingSolicitations(userToken);
      setSolicitations(solicitations);
    } catch (error) {
      console.error("Error attempting to update rides:", error);
    } finally {
      setIsLoading(false);
    }
  };

  const handleDecline = async (rideSolicitationId) => {
    try {
      await useRejectSolicitation(userToken, rideSolicitationId);
      updateSolicitations();
    } catch (error) {
      console.error("Error attempting to decline solicitation:", error);
    }
  };

  const handleApprove = async (rideSolicitationId) => {
    try {
      setIsLoading(true);
      await useAcceptSolicitation(userToken, rideSolicitationId);
      updateSolicitations();
    } catch (error) {
      console.error("Error attempting to approve solicitation:", error);
    } finally {
      setIsLoading(false);
    }
  };

  useEffect(() => {
    updateSolicitations();
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
        Notificações de Solicitações
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
        {solicitations.map((s) => (
          <RideSolicitationNotification
            solicitation={s}
            handleDecline={() => handleDecline(s.rideSolicitationId)}
            handleApprove={() => handleApprove(s.rideSolicitationId)}
            isLoading={isLoading}
            key={s.rideSolicitationId}
          />
        ))}
      </div>
    </div>
  );
};

export default ViewSolicitationNotifications;
