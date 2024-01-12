import * as React from 'react';
import {useEffect, useState} from 'react';
import {Layout} from 'antd';
import {
    ContentContainer,
    StyledContent, StyledDiv,
    StyledHeader, StyledLayout,
    StyledSider
} from "./PersonalTree.style";
import MyTree from "../../MyTree/components/mytree.component.jsx";
import {TreeNode} from "../../../types/TreeNode";
import TreeVisibilityOptions from "./TreeVisibility.component";
import {NavigationMenu} from "../../../components/NavigationMenu.component";

export const PersonalTree: React.FC = (): React.ReactElement => {
    const [treeData, setTreeData] = useState<TreeNode[]>([]);
    const [loadingTree, setLoadingTree] = useState(true);
    const userId = localStorage.getItem('userId');
    const loadUserTree = async () => {
        const token = localStorage.getItem('jwtToken');

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
                <div className="demo-logo-vertical" />
                <NavigationMenu/>
            </StyledSider>
            <StyledLayout className="site-layout">
                <StyledContent>
                    <ContentContainer>
                        <TreeVisibilityOptions userId={userId}/>
                        {!loadingTree && treeData.length > 0 && (
                            <StyledDiv>
                                <MyTree nodes={treeData}/>
                            </StyledDiv>
                        )}
                    </ContentContainer>
                </StyledContent>
            </StyledLayout>
        </Layout>
    );
};

export default PersonalTree;
