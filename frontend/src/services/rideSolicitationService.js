import api from "./api";

export async function useGetPassengerPendingSolicitations(token) {
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

export async function useGetDriverPendingSolicitations(token) {
  const response = await api.get("/api/v1/ride-solicitations/driver/pending", {
    headers: {
      Authorization: `Bearer ${token}`,
      "Content-Type": "application/json",
    },
  });
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

export async function useAcceptSolicitation(token, rideSolicitationId) {
  const response = await api.post(
    `/api/v1/ride-solicitations/${rideSolicitationId}/accept`,
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

export async function useRejectSolicitation(token, rideSolicitationId) {
  const response = await api.post(
    `/api/v1/ride-solicitations/${rideSolicitationId}/reject`,
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
