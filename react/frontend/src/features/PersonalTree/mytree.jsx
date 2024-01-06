import React, { useRef, useEffect } from 'react';
import FamilyTree from '@balkangraph/familytree.js';

function MyTree({ nodes }) {
    const treeRef = useRef(null);
    const familyRef = useRef(null);


    useEffect(() => {
        const tree = new FamilyTree(treeRef.current, {
            nodeTreeMenu: true,
            nodeBinding: {
                field_0: 'name',
                field_1: 'securitySocialNumber',
            },
            nodes: nodes,
            editForm: {
                titleBinding: "name",
                photoBinding: "photo",
                generateElementsFromFields: false,
                addMore: 'Add more elements',
                addMoreBtn: 'Add element',
                addMoreFieldName: 'Element name',
                elements: [
                    { type: 'textbox', label: 'Security Social Number', binding: 'securitySocialNumber' },
                ],
                buttons: {
                    edit: {
                        icon: FamilyTree.icon.edit(24, 24, '#fff'),
                        text: 'Edit',
                        hideIfEditMode: true,
                        hideIfDetailsMode: false
                    },
                    share: {
                        icon: FamilyTree.icon.share(24, 24, '#fff'),
                        text: 'Share'
                    },
                    pdf: {
                        icon: FamilyTree.icon.pdf(24, 24, '#fff'),
                        text: 'Save as PDF'
                    },
                    remove: null
                }
            }
        });
        tree.onUpdateNode(async (args) => {
            const formatNodeData = (nodeData) => {
                return {
                    id: nodeData.id,
                    pids: nodeData.pids || null,
                    mid: nodeData.mid || null,
                    fid: nodeData.fid || null,
                    name: nodeData.name || null,
                    securitySocialNumber: nodeData.securitySocialNumber || '1234567890123'
                };
            };

            if(args.updateNodesData){
                for (const nodeData of args.updateNodesData) {
                    const formattedNode = formatNodeData(nodeData);
                    // console.log("avant le stringify" + nodeData);
                    const url = `${import.meta.env.VITE_BASE_URL}/api/family-members/update/`;
                    try {
                        console.log("J'envoie ceci au back: " + JSON.stringify(formattedNode));
                        console.log("le token que j'envoie :" + localStorage.getItem('jwtToken'))
                        const response = await fetch(url, {
                            method: 'POST',
                            headers: {
                                'Content-Type': 'application/json',
                                'Authorization': `Bearer ${localStorage.getItem('jwtToken')}`,
                            },
                            body: JSON.stringify(formattedNode),
                        });

                        if (response.ok && response.headers.get('Content-Type')?.includes('application/json')) {
                            const responseData = await response.json();
                            console.log("Mise à jour de la relation réussie :", responseData);
                        } else {
                            const textResponse = await response.text();
                            throw new Error(textResponse || 'Failed to update');
                        }

                    } catch (error) {
                        console.error(`Failed to send relation update to the server:`, error);
                    }
                }
            }
        });

        tree.onUpdateNode((args) => {
            console.log(args.addNodesData);
            console.log(args.updateNodesData);
        });

        return () => {
            if (familyRef.current) {
                familyRef.current.destroy();
            }
        };
    }, [nodes]); // Depend on nodes so it updates when nodes change

    return <div ref={treeRef} style={{ width: '100%', height: '500px' }} />;
}

export default MyTree;