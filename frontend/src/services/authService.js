export const parseJwt = (token) => {
    if (!token) return null;
    try {
        const base64Url = token.split('.')[1];
        const base64 = base64Url.replace(/-/g, '+').replace(/_/g, '/');
        return JSON.parse(window.atob(base64));
    } catch (e) {
        console.error('Invalid JWT token:', e);
        return null;
    }
}

export const getUserRole = (token) => {
    const decodedToken = parseJwt(token);
    return decodedToken ? decodedToken.role : null;
};

