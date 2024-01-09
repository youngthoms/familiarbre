import './Home.css';
import NavigationLinks from "./NavigationLinks.component";
import FamilyMembersTable from "../features/TreeList/components/FamilyMembersTable";

const Home = () => {
    return (
        <div className="home-container">
            <img src="src/assets/logo.png" alt="logo" className="logo-form-image"/>
            <NavigationLinks/>

            <FamilyMembersTable/>
        </div>
    );
};

export default Home;