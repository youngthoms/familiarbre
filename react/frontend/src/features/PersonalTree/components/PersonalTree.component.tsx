import React, {useState} from 'react';
import {
    AppstoreOutlined,
    BarChartOutlined,
    CloudOutlined,
    ShopOutlined,
    TeamOutlined,
    UploadOutlined,
    UserOutlined,
    VideoCameraOutlined,
} from '@ant-design/icons';
import {Menu, Button, Layout} from 'antd';
import type { MenuProps } from 'antd';
import {
    ContentContainer,
    StyledContent,
    StyledHeader, StyledLayout,
    StyledSider
} from "./PersonalTree.style.tsx";
import MyTree from "../../../mytree.jsx";

const items: MenuProps['items'] = [
    UserOutlined,
    VideoCameraOutlined,
    UploadOutlined,
    BarChartOutlined,
    CloudOutlined,
    AppstoreOutlined,
    TeamOutlined,
    ShopOutlined,
].map((icon, index) => ({
    key: String(index + 1),
    icon: React.createElement(icon),
    label: `nav ${index + 1}`,
}));

export const PersonalTree: React.FC = (): JSX.Element => {
    // const [nodes, setNodes] = useState<TreeNode[]>([]);
    const [showTree, setShowTree] = useState(false);
    // const treeDivRef = useRef(null);
    const nodes = [
        { id: 1, pids: [2], name: "Amber McKenzie", gender: "female" },
        { id: 2, pids: [1], name: "Ava Field", gender: "male" },
        { id: 3, mid: 1, fid: 2, name: "Peter Stevens", gender: "male" }
    ];
    const createTree = () => {
        //setNodes([{ id: "1", name: "Racine" }]);  // Ajoutez un nœud racine à l'arbre
        setShowTree(true);
    };
    return (
        <Layout hasSider>
            <StyledSider>
                <div className="demo-logo-vertical" />
                <Menu theme="dark" mode="inline" defaultSelectedKeys={['4']} items={items} />
            </StyledSider>
            <StyledLayout className="site-layout">
                <StyledHeader />
                <StyledContent>
                    <ContentContainer>
                        <Button type="primary" onClick={createTree}>Créer un arbre</Button>
                        {showTree && <div style={{ height: "100%" }}>
                            <MyTree nodes={nodes} />
                        </div>}
                    </ContentContainer>
                </StyledContent>
            </StyledLayout>
        </Layout>
    );
};

export default PersonalTree;
