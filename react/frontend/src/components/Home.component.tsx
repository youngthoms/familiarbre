import './Home.css';
import NavigationLinks from "./NavigationLinks.component.tsx";

const Home = () => {
    return (
        <div className="home-container">
            <img src="src/assets/logo.png" alt="logo" className="logo-form-image"/>
            <NavigationLinks/>
        </div>
    );
};

export default Home;