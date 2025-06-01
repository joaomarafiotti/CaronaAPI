import api from "./api";

export async function useGetAvailableRides(token) {
  const response = await api.get("/api/v1/ride", {
    headers: {
      Authorization: `Bearer ${token}`,
      "Content-Type": "application/json",
    },
  });
  return response.data;
}