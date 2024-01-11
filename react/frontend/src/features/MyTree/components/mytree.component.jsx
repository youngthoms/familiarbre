import React, {useEffect, useRef} from 'react';
import FamilyTree from '@balkangraph/familytree.js';
import {v4 as uuidv4} from 'uuid';

function MyTree({ nodes }) {
    const treeRef = useRef(null);
    const familyRef = useRef(null);

    function convertToLong(id) {
        const idString = typeof id === 'string' ? id : String(id);
        if (idString.length > 4) {
            return parseInt(idString.substring(0, 8), 16);
        }
        return id;
    }



    useEffect(() => {
        console.log("mon arbre dans MyTree" + JSON.stringify(nodes))
        const tree = new FamilyTree(treeRef.current, {
            mouseScrool: FamilyTree.action.none,
            nodeTreeMenu: true,
            nodeBinding: {
                field_0: 'name',
                field_1: 'birthDay',
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
                    { type: 'textbox', label: 'Full name', binding: 'name' },
                    { type: 'textbox', label: 'Security Social Number', binding: 'socialSecurityNumber' },
                    { type: 'date', label: 'Birth Date', binding: 'birthDay' },
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
        tree.generateId = () => uuidv4();

        tree.onUpdateNode(async (args) => {
            const formatNodeData = (nodeData) => {
                const idString = typeof nodeData.id === 'string' ? nodeData.id : String(nodeData.id);
                return {
                    id: convertToLong(nodeData.id),
                    pids: nodeData.pids ? nodeData.pids.map(pid => convertToLong(pid)) : null, // Convertir chaque élément de pids
                    mid: nodeData.mid ? convertToLong(nodeData.mid) : null,
                    fid: nodeData.fid ? convertToLong(nodeData.fid) : null,
                    name: nodeData.name || null,
                    socialSecurityNumber: nodeData.socialSecurityNumber || 'null',
                    gender: nodeData.gender,
                };
            };
            if (args.updateNodesData) {
                for (const nodeData of args.updateNodesData) {
                    const formattedNode = formatNodeData(nodeData);
                    const url = `${import.meta.env.VITE_BASE_URL}/api/family-members/update/`;
                    try {
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

        tree.onUpdateNode(async (args) => {

            if(args.addNodesData){
                await new Promise(r => setTimeout(r, 1000));
                for(const nodeData of args.addNodesData){
                    if(nodeData.fid != null && nodeData.mid != null){
                        const momId = convertToLong(nodeData.mid);
                        const dadId = convertToLong(nodeData.fid);
                        const childId = convertToLong(nodeData.id);
                        const url = `${import.meta.env.VITE_BASE_URL}/api/family-members/child/add`;
                        try {
                            const response = await fetch(url, {
                                method: 'POST',
                                headers: {
                                    'Content-Type': 'application/json',
                                    'Authorization': `Bearer ${localStorage.getItem('jwtToken')}`,
                                },
                                body: JSON.stringify({
                                    momId: momId,
                                    dadId: dadId,
                                    childid: childId,
                                    gender: nodeData.gender,
                                }),
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
            }
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