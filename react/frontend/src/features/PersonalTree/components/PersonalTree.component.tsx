import React, {useRef, useState} from 'react';
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
import FamilyTree from "@balkangraph/familytree.js";


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
    const [showTree, setShowTree] = useState(false);
    const treeDivRef = useRef(null);

    const createTree = () => {
        setShowTree(true);
        setTimeout(() => {
            if (treeDivRef.current) {
                new FamilyTree(treeDivRef.current, {
                    nodes: [], // Commence avec un tableau vide
                    nodeBinding: {
                        field_0: 'name',
                        img_0: 'img'
                    }
                });
            }
        }, 0);
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
                        {showTree && <div ref={treeDivRef} style={{ height: '500px', width: '100%' }}></div>}
                    </ContentContainer>
                </StyledContent>
            </StyledLayout>
        </Layout>
    );
};

export default PersonalTree;
