import {Link, useLocation} from "react-router-dom";
import FamilyMembersTable from "../features/TreeList/components/FamilyMembersTable";

const HomeIndex = () => {
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
            <FamilyMembersTable/>
        </div>
    )
}

export default HomeIndex;