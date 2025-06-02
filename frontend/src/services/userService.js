import api from "./api";

export async function getUserByToken(token) {
  return await api.get(`/api/v1/user`, {
    headers: {
      Authorization: `Bearer ${token}`,
      "Content-Type": "application/json",
    },
  });
}
