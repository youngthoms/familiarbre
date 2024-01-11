import * as React from 'react';
import {useEffect, useState} from 'react';
import {Layout} from 'antd';
import MyTree from "../../PersonalTree/mytree.jsx";
import {TreeNode} from "../../../types/TreeNode";
import {useParams} from "react-router-dom";
import {
    ContentContainer,
    StyledContent,
    StyledHeader,
    StyledLayout, StyledSider,
} from "../../PersonalTree/components/PersonalTree.style";
import {NavigationMenu} from "../../../components/NavigationMenu.component";


export const PublicTree: React.FC = (): JSX.Element => {
    const [treeData, setTreeData] = useState<TreeNode[]>([]);
    const [loadingTree, setLoadingTree] = useState(true);
    const {id} = useParams<{ id: string }>();

    const loadUserTree = async () => {
        const userId = id;

        if (userId) {
            try {
                const response = await fetch(`${import.meta.env.VITE_BASE_URL}/api/family-members/tree/${userId}`, {
                    method: 'GET',
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
                <StyledHeader/>
                <StyledContent>
                    <ContentContainer>
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

export default PublicTree;
