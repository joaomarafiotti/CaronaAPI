import api from "./api";

export async function registerRide(ride, token) {
  return api.post("/api/v1/ride", ride, {
    headers: {
      Authorization: `Bearer ${token}`,
      "Content-Type": "application/json",
    },
  });
}

export async function getDriverRides(token) {
  return api.get("/api/v1/rides", {
    headers: {
      Authorization: `Bearer ${token}`,
      "Content-Type": "application/json",
    },
  });
}

export async function getRideById(rideId, token) {
  return api.get(`/api/v1/ride/${rideId}`, {
    headers: {
      Authorization: `Bearer ${token}`,
      "Content-Type": "application/json",
    },
  });
}

export async function useGetAvailableRides(token) {
  const response = await api.get("/api/v1/ride", {
    headers: {
      Authorization: `Bearer ${token}`,
      "Content-Type": "application/json",
    },
  });
  return response.data;
}

export async function useGetRides(token) {
  const response = await api.get("/api/v1/ride/passenger", {
    headers: {
      Authorization: `Bearer ${token}`,
      "Content-Type": "application/json",
    },
  });
  return response.data;
}
