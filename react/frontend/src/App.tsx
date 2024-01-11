import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import './App.css';
import RegisterForm from "./features/Registration/components/RegisterForm.tsx";
import PersonalTreeComponent from "./features/PersonalTree/components/PersonalTree.component.tsx";
import LoginForm from "./features/SignIn/components/LoginForm.tsx";
import Home from "./components/Home.component.tsx";
import PublicTreeComponent from "./features/PublicTree/components/PublicTree.component.tsx";
import FamilyMembersTable from "./features/TreeList/components/FamilyMembersTable.tsx";


function App() {
    return (

        <Router>
            <link rel="preconnect" href="https://fonts.googleapis.com"/>
            <link rel="preconnect" href="https://fonts.gstatic.com"/>
            <link href="https://fonts.googleapis.com/css2?family=Nunito:wght@200&display=swap" rel="stylesheet"/>
            <div>
                <Home/>
                <Routes>
                    <Route path="/register" element={<RegisterForm/>}/>
                    <Route path="/login" element={<LoginForm/>}/>
                    <Route path="/personaltree" element={<PersonalTreeComponent />}/> {/* Keep the existing route without id */}
                    <Route path="/publictree/:id" element={<PublicTreeComponent/>}/>
                    <Route path="/search-tree" element={<FamilyMembersTable/>}/>
                    {/* Ajoute ici la route vers ton composant de connexion */}
                </Routes>
            </div>
        </Router>
    );
}

export default App;
