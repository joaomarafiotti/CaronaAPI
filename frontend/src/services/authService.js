import api from "./api";

export const parseJwt = (token) => {
  if (!token) return null;
  try {
    const base64Url = token.split(".")[1];
    const base64 = base64Url.replace(/-/g, "+").replace(/_/g, "/");
    return JSON.parse(window.atob(base64));
  } catch (e) {
    console.error("Invalid JWT token:", e);
    return null;
  }
};

export const getUserRole = (token) => {
  const decodedToken = parseJwt(token);
  return decodedToken ? decodedToken.role : null;
};

export const loginUser = async (formData) => {
  const { data } = await api.post("/api/v1/authenticate", formData);
  return data;
};

export const registerUser = async (formData) => {
  await api.post("/api/v1/register", formData);
};

export const isValidToken = async (token) => {
  await api
    .post("/api/v1/validate-token", null, {
      headers: {
        Authorization: `Bearer ${token}`,
      },
    })
    .catch((error) => {
      if (
        error.response &&
        (error.response.status === 401 || error.response.status === 404)
      ) {
        throw new Error("Token is invalid or expired");
      } else {
        throw new Error("An error occurred while validating the token");
      }
    });
};
