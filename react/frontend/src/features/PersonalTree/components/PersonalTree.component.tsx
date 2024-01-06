import * as React from 'react';
import {useEffect, useState} from 'react';
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
import {Menu, Layout} from 'antd';
import type {MenuProps} from 'antd';
import {
    ContentContainer,
    StyledContent,
    StyledHeader, StyledLayout,
    StyledSider
} from "./PersonalTree.style";
import MyTree from "../mytree.jsx";
import {TreeNode} from "../../../types/TreeNode";
import TreeVisibilityOptions from "./TreeVisibility.component";


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

export const PersonalTree: React.FC = (): React.ReactElement => {
    const [treeData, setTreeData] = useState<TreeNode[]>([]);
    const [loadingTree, setLoadingTree] = useState(true);

    const loadUserTree = async () => {
        const token = localStorage.getItem('jwtToken');
        const userId = localStorage.getItem('userId');

        if (token && userId) {
            try {
                const queryParams = token ? `?token=${encodeURIComponent(token)}` : '';
                const response = await fetch(`${import.meta.env.VITE_BASE_URL}/api/family-members/tree/${userId}${queryParams}`, {
                    method: 'GET',
                    headers: {
                        'Content-Type': 'application/json',
                        ...(token && {'Authorization': `Bearer ${token}`}),
                    },
                });
                if (!response.ok) {
                    throw new Error('Failed to load tree');
                }
                const tree = await response.json();
                setTreeData(tree);
            } catch (error) {
                console.error('Failed to fetch tree:', error);
            } finally {
                setLoadingTree(false);
            }
        }
    };

    useEffect(() => {
        loadUserTree();
    }, []);

    return (
        <Layout hasSider>
            <StyledSider>
                <div className="demo-logo-vertical"/>
                <Menu theme="dark" mode="inline" defaultSelectedKeys={['4']} items={items}/>
            </StyledSider>
            <StyledLayout className="site-layout">
                <StyledHeader/>
                <StyledContent>
                    <ContentContainer>
                        <TreeVisibilityOptions/>
                        {!loadingTree && treeData.length > 0 && (
                            <div style={{height: "100%"}}>
                                <MyTree nodes={treeData}/>
                            </div>
                        )}
                    </ContentContainer>
                </StyledContent>
            </StyledLayout>
        </Layout>
    );
};

export default PersonalTree;
