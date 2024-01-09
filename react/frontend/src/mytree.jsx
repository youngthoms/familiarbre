import React, {useRef, useEffect} from 'react';
import FamilyTree from '@balkangraph/familytree.js';

function MyTree({ nodes }) {
    const treeRef = useRef(null);
    const familyRef = useRef(null); // Assurez-vous de définir familyRef ici


    useEffect(() => {
        const tree = new FamilyTree(treeRef.current, {
            nodeTreeMenu: true,
            nodeBinding: {
                field_0: 'name',
                img_0: 'img'
            },
            nodes: nodes
        });
        // console.log(nodes);
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
