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

export async function useCreateSolicitation(token, rideId) {
  const response = await api.post(
    `/api/v1/ride-solicitations?rideId=${rideId}`,
    {},
    {
      headers: {
        Authorization: `Bearer ${token}`,
        "Content-Type": "application/json",
      },
    }
  );
  return response.data;
}

export async function useCancelSolicitation(token, solicitationId) {
  console.log(solicitationId);
  const response = await api.put(
    `/api/v1/ride-solicitations/${solicitationId}/cancel`,
    {},
    {
      headers: {
        Authorization: `Bearer ${token}`,
        "Content-Type": "application/json",
      },
    }
  );
  return response.data;
}
