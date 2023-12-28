import { useRef, useEffect } from 'react';
import FamilyTree from "@balkangraph/familytree.js";

export default function Chart({ nodes }) {
    const divRef = useRef(null);
    const familyRef = useRef(null);

    useEffect(() => {
        const transformedNodes = nodes.map(member => ({
            id: member.id,
            pids: member.spouse ? [member.spouse] : [],
            mid: member.parents && member.parents[0],
            fid: member.parents && member.parents[1],
            name: member.firstname + ' ' + (member.lastname || ''),
            img: member.img
        }));

        familyRef.current = new FamilyTree(divRef.current, {
            nodes: transformedNodes,
            nodeBinding: {
                field_0: 'name',
                img_0: 'img'
            }
        });

        return () => {
            if (familyRef.current) {
                familyRef.current.destroy();
            }
        };
    }, [nodes]);

    return <div ref={divRef}></div>;
}
//onupdatenode