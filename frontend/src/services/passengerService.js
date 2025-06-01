import api from "./api";
import { useAuth } from "../context/AuthContext";

export async function getPassengerById(passengerId) {
  const { userToken } = useAuth();

  return api.get(`/api/v1/passenger/${passengerId}`, {
    headers: {
      Authorization: `Bearer ${userToken}`,
      "Content-Type": "application/json",
    },
  });
}
