import { useState, useEffect } from 'react';
import {Input, AutoComplete, Layout} from 'antd';
import MyTree from "../../MyTree/components/mytree.component.jsx";
import {
    ContentContainer,
    StyledContent,
    StyledHeader,
    StyledLayout,
    StyledSider
} from "../../PersonalTree/components/PersonalTree.style";
import {NavigationMenu} from "../../../components/NavigationMenu.component";

const SearchFamilyTree = () => {
    const [familyMembers, setFamilyMembers] = useState([]);
    const [selectedValue, setSelectedValue] = useState(""); // Etat pour la valeur sélectionnée (affichée)
    const [loading, setLoading] = useState(false);
    const [loadingTree, setLoadingTree] = useState(false);
    const [treeData, setTreeData] = useState([]);

    useEffect(() => {
        fetchFamilyMembers();
    }, []);

    const fetchFamilyMembers = async () => {
        setLoading(true);
        try {
            const response = await fetch(`${import.meta.env.VITE_BASE_URL}/api/family-members/all`);
            if (!response.ok) {
                throw new Error(`HTTP error! status: ${response.status}`);
            }
            const data = await response.json();
            setFamilyMembers(data.familyMembers);
        } catch (error) {
            console.error("Fetching error: ", error);
        } finally {
            setLoading(false);
        }
    };

    const loadUserTree = async (userId) => {
        setLoadingTree(true);
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
    };

    const onSelect = (value, option) => {
        setSelectedValue(option.label);
        loadUserTree(value);
    };

    return (
    <Layout hasSider>
        <StyledSider>
            <div className="demo-logo-vertical" />
            <NavigationMenu/>
        </StyledSider>
        <StyledLayout className="site-layout">
            <StyledHeader>
                <AutoComplete
                    style={{ width: 500 }}
                    options={familyMembers.map(member => ({ value: member.id.toString(), label: member.fullName }))}
                    value={selectedValue} // Utiliser l'état selectedValue comme valeur du champ
                    onSelect={onSelect}
                    onChange={setSelectedValue}
                    placeholder="Search family tree..."
                    notFoundContent={loading ? "Loading..." : null}
                />
            </StyledHeader>
            <StyledContent>
                {!loadingTree && treeData.length > 0 && (
                    <ContentContainer>

                            <div style={{height: "100%"}}>
                                <MyTree nodes={treeData}/>
                            </div>
                    </ContentContainer>
                )}
            </StyledContent>
        </StyledLayout>
    </Layout>
    );
};

export default SearchFamilyTree;
