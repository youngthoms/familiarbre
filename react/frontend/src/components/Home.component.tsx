import './Home.css';
import HomeIndex from "./HomeIndex.component";
// @ts-ignore
import logo from '../assets/logo.png'; // Assurez-vous que le chemin est correct

const Home = () => {
    return (
        <div className="home-container">
            <img src={logo} alt="logo" className="logo-form-image"/>
            <HomeIndex/>
        </div>
    );
};

export default Home;