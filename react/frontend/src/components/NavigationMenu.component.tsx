import { SearchOutlined, UserOutlined, RadarChartOutlined } from '@ant-design/icons';
import { Layout, Menu } from 'antd';
import {StyledMenu} from "./NavigationMenu.style";
import {useNavigate} from "react-router-dom";
import {useGetUserName} from "../hooks/useGetUserName";

const { Header, Content, Footer, Sider } = Layout;

const items = [
    {
        key: 'search',
        icon: <SearchOutlined />,
        label: 'Recherche d\'arbre',
    },
    {
        key: 'tree',
        icon: <RadarChartOutlined />,
        label: 'Mon arbre',
    },
];

export const NavigationMenu = () => {
    const userName = useGetUserName();
    const navigate = useNavigate(); // Ajout de useNavigate
    console.log(userName)

    const handleMenuClick = ({key}) => {
        switch (key) {
            case 'search':
                navigate('/search-tree');
                break;
            case 'tree':
                navigate('/personaltree');
                break;
        }
    }
    return (
        <Layout hasSider>
            <Sider
                style={{
                    overflow: 'auto',
                    height: '100vh',
                    position: 'fixed',
                    left: 0,
                    top: 0,
                    bottom: 0,
                    backgroundColor: '#1A757E', // Couleur de fond personnalisée pour le Sider
                }}
            >
                <div className="demo-logo-vertical"/>
                <div style={{padding: '16px', textAlign: 'center', color: 'white', backgroundColor: '#1A757E', fontSize:'22px'}}>
                    FamiliArbre
                </div>
                <div style={{ padding: '16px', display: 'flex', alignItems: 'center', color: 'white' }}>
                    <UserOutlined style={{ marginRight: '8px' }} />
                    {userName || 'Utilisateur'}
                </div>
                <StyledMenu
                    mode="inline"
                    defaultSelectedKeys={['1']}
                    items={items}
                    onClick={handleMenuClick}/>
            </Sider>
        </Layout>
    );
};