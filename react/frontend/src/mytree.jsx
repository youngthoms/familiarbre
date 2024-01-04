import React, {useRef, useEffect} from 'react';
import FamilyTree from '@balkangraph/familytree.js';

function MyTree({ nodes }) {
    const treeRef = useRef(null);
    const familyRef = useRef(null);


    useEffect(() => {
        const tree = new FamilyTree(treeRef.current, {
            nodeTreeMenu: true,
            nodeBinding: {
                field_0: 'name',
                field_1: 'securityNumber',
                img_0: 'img'
            },
            nodes: nodes
        });
        tree.onUpdateNode(async (args) => {
            // Gérer l'ajout de nouveaux nœuds
            if (args.addNodesData) {
                for (const newNode of args.addNodesData) {
                    if (newNode.mid || newNode.fid) { // Si un enfant a été ajouté
                        await sendRelationUpdate('child', newNode.mid || newNode.fid, newNode.id);
                    } else if (newNode.pids && newNode.pids.length > 0) { // Si un partenaire a été ajouté
                        for (const partnerId of newNode.pids) {
                            await sendRelationUpdate('partner', newNode.id, partnerId);
                        }
                    }
                }
            }

            // Gérer la suppression de nœuds
            if (args.removeNodesData) {
                for (const removedNode of args.removeNodesData) {
                    await sendRelationUpdate('remove', removedNode.id);
                }
            }
        });

// Fonction pour envoyer la mise à jour de la relation ou la suppression au back-end
        async function sendRelationUpdate(relationType, sourceId, targetId = null) {
            let url;
            let postData;

            if (relationType === 'remove') {
                url = `${import.meta.env.VITE_BASE_URL}/api/family-members/remove`;
                postData = { id: sourceId };
            } else {
                url = `${import.meta.env.VITE_BASE_URL}/api/family-members/${relationType}/add`;
                postData = {
                    parentId: sourceId, // Pour 'child', ceci est l'ID du parent
                    childId: targetId,  // Pour 'child', ceci est l'ID de l'enfant
                };
            }

            try {
                const response = await fetch(url, {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/json',
                        'Authorization': `Bearer ${localStorage.getItem('jwtToken')}`,
                    },
                    body: JSON.stringify(postData),
                });

                const responseData = await response.json();
                if (!response.ok) {
                    throw new Error(responseData.message || `Failed to ${relationType === 'remove' ? 'remove' : 'add'} relation`);
                }

                console.log(`${relationType} relation update successfully:`, responseData);
            } catch (error) {
                console.error(`Failed to send ${relationType} relation update to the server:`, error);
            }
        }


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
