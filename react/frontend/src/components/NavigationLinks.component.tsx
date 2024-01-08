import {Link, useLocation} from "react-router-dom";

const NavigationLinks = () => {
    const location = useLocation();

    if (location.pathname !== '/') {
        return null;
    }

    return (
        <div>
            <h1>Welcome to FamiliArbre</h1>
            <nav className="navigation">
                <Link to="/register" className="nav-link">Register</Link>
                <Link to="/login" className="nav-link">Login</Link>
            </nav>
        </div>
    )
}

export default NavigationLinks;