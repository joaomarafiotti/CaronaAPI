import api from "./api";

export async function useGetPendingSolicitations(token) {
  const response = await api.get(
    "/api/v1/ride-solicitations/passenger/pending",
    {
      headers: {
        Authorization: `Bearer ${token}`,
        "Content-Type": "application/json",
      },
    }
  );
  return response.data;
}
