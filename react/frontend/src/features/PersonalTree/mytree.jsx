import React, {useEffect, useRef} from 'react';
import FamilyTree from '@balkangraph/familytree.js';
import {v4 as uuidv4} from 'uuid';

function MyTree({ nodes }) {
    const treeRef = useRef(null);
    const familyRef = useRef(null);

    function convertToLong(id) {
        const idString = typeof id === 'string' ? id : String(id);
        return parseInt(idString.substring(0, 8), 16);
    }


    useEffect(() => {
        console.log("mon arbre dans MyTree" + JSON.stringify(nodes))
        const tree = new FamilyTree(treeRef.current, {
            nodeTreeMenu: true,
            nodeBinding: {
                field_0: 'name',
                field_1: 'socialSecurityNumber',
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
                    { type: 'textbox', label: 'Security Social Number', binding: 'socialSecurityNumber' },
                    { type: 'textbox', label: 'Security Social Number', binding: 'socialSecurityNumber' },
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
                console.log(typeof nodeData.id)
                const idString = typeof nodeData.id === 'string' ? nodeData.id : String(nodeData.id);
                console.log(typeof idString)
                console.log("mon parseint", parseInt(idString.substring(0,8), 16) )
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

            // if(args.addNodesData){
            //     for(const nodeData of args.addNodesData){
            //         if(nodeData.fid != null || nodeData.mid != null){
            //             const url = `${import.meta.env.VITE_BASE_URL}/api/family-members/child/add`;
            //             try {
            //                 console.log("J'envoie ceci au back pour le fils: " + JSON.stringify(formattedNode));
            //                 const response = await fetch(url, {
            //                     method: 'POST',
            //                     headers: {
            //                         'Content-Type': 'application/json',
            //                         'Authorization': `Bearer ${localStorage.getItem('jwtToken')}`,
            //                     },
            //                     body: JSON.stringify(formattedNode),
            //                 });
            //
            //                 if (response.ok && response.headers.get('Content-Type')?.includes('application/json')) {
            //                     const responseData = await response.json();
            //                     console.log("Mise à jour de la relation réussie :", responseData);
            //                 } else {
            //                     const textResponse = await response.text();
            //                     throw new Error(textResponse || 'Failed to update');
            //                 }
            //
            //             } catch (error) {
            //                 console.error(`Failed to send relation update to the server:`, error);
            //             }
            //         }                    }
            //     }
            //     console.log("j'ajoute ce node" + JSON.stringify(args.addNodesData))

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