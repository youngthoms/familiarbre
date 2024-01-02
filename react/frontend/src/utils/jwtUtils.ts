export const getUserIdFromToken = () => {
    const jwtDecode = require('jwt-decode');
    const token = localStorage.getItem('jwtToken'); // Assurez-vous que c'est le bon nom de clé
    if(token) {
        const decodedToken = jwtDecode(token);
        return decodedToken.userId; // ou decodedToken.sub, etc., selon la structure de votre token
    }
    return null;
};
