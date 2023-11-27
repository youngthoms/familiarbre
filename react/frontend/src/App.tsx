import { BrowserRouter as Router, Routes, Route, Link, useLocation } from 'react-router-dom';
import './App.css';
import RegisterForm from "./features/Registration/components/RegisterForm.tsx";
import PersonalTreeComponent from "./features/PersonalTree/components/PersonalTree.component.tsx";
import LoginForm from "./features/SignIn/components/LoginForm.tsx";

const NavigationLinks = () => {
    const location = useLocation();

    if (location.pathname !== '/') {
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
                    <Route path="/login" element={<LoginForm />} />
                    <Route path="/personaltree" element={<PersonalTreeComponent />} />
                    {/* Ajoute ici la route vers ton composant de connexion */}
                </Routes>
            </div>
        </Router>
    );
}

export default App;
