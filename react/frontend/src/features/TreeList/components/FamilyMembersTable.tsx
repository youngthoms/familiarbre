import { useState, useEffect } from 'react';
import { Table } from 'antd';

function FamilyMembers() {
    const [familyMembers, setFamilyMembers] = useState([]);

    useEffect(() => {
        fetch(`${import.meta.env.VITE_BASE_URL}/api/family-members/all`)
            .then(response => response.json())
            .then(data => setFamilyMembers(data.familyMembers))
            .catch(error => console.error('Error fetching data: ', error));
    }, []);

    const columns = [
        {
            title: 'Full name',
            dataIndex: 'fullName',
            key: 'fullName',
        },
        {
            title: 'Gender',
            dataIndex: 'gender',
            key: 'gender',
        },
        {
            title: 'Status',
            dataIndex: 'status',
            key: 'status',
        },
    ];

    return (
        <div>
            <h1>Family Members</h1>
            <Table dataSource={familyMembers} columns={columns} rowKey="id" />
        </div>
    );
}

export default FamilyMembers;
