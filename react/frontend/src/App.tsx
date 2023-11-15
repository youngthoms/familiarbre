import { BrowserRouter as Router, Routes, Route, Link, useLocation } from 'react-router-dom';
import './App.css';
import RegisterForm from "./features/Registration/components/RegisterForm.tsx";

const NavigationLinks = () => {
    const location = useLocation();

    // Affiche les liens uniquement si l'utilisateur n'est pas sur les pages d'inscription ou de connexion
    if (location.pathname === '/register' || location.pathname === '/login') {
        return null;
    }

    return (
        <nav>
            <Link to="/register">S'inscrire</Link>
            <Link to="/login">Se connecter</Link>
        </nav>
    );
};

function App() {
    return (
        <Router>
            <div>
                <NavigationLinks />
                <Routes>
                    <Route path="/register" element={<RegisterForm />} />
                    {/* Ajoute ici la route vers ton composant de connexion */}
                </Routes>
            </div>
        </Router>
    );
}

export default App;
