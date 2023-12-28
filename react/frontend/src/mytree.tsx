import React, { Component } from 'react';
import FamilyTree from "@balkangraph/familytree.js";

export default class Chart extends Component {
    divRef = React.createRef();

    constructor(props) {
        super(props);
        this.divRef = React.createRef();
    }

    shouldComponentUpdate() {
        return false;
    }

    componentDidMount() {
        // Transformation des données JSON en format compatible avec FamilyTreeJS
        const transformedNodes = this.props.nodes.map(member => ({
            id: member.id,
            pids: member.spouse ? [member.spouse] : [], // Partenaire
            mid: member.parents && member.parents[0], // Mère
            fid: member.parents && member.parents[1], // Père
            name: member.firstname + ' ' + (member.lastname || ''), // Nom complet
            img: member.img // Image (si disponible)
        }));

        this.family = new FamilyTree(this.divRef.current, {
            nodes: transformedNodes,
            nodeBinding: {
                field_0: 'name',
                img_0: 'img'
            }
        });
    }

    render() {
        return (
            <div ref={this.divRef}></div>
        );
    }
}
