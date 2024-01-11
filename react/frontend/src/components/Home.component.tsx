import HomeIndex from "./HomeIndex.component";
// @ts-ignore
import logo from '../assets/logo.png';
import {StyledHomeContainer, StyledImg} from "./Home.style"; // Assurez-vous que le chemin est correct

const Home = () => {
    return (
        <StyledHomeContainer>
            <StyledImg src={logo} alt="logo" className="logo-form-image"/>
            <HomeIndex/>
        </StyledHomeContainer>
    );
};

export default Home;