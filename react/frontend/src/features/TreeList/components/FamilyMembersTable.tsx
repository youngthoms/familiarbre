import { useState, useEffect } from 'react';
import { Table } from 'antd';

type FamilyMember = {
    id: number;
    fullName: string;
    gender: string;
    status: string;
};


const FamilyMembersTable: React.FC = () => {
    const [familyMembers, setFamilyMembers] = useState<FamilyMember[]>([]);
    const [loading, setLoading] = useState<boolean>(false);

    useEffect(() => {
        const fetchFamilyMembers = async () => {
            setLoading(true);
            try {
                const response = await fetch(`${import.meta.env.VITE_BASE_URL}/api/family-members/all`);
                if (response.status === 302) {
                    // Handle redirection here if necessary
                    return;
                }
                if (!response.ok) {
                    throw new Error(`HTTP error! status: ${response.status}`);
                }
                const data = await response.json();
                setFamilyMembers(data.familyMembers); // Assuming the data is in the format you provided
            } catch (error) {
                console.error("Fetching error: ", error);
            } finally {
                setLoading(false);
            }
        };

        fetchFamilyMembers();
    }, []);

    // Define the columns for the Ant Design table
    const columns = [
        {
            title: 'Full Name',
            dataIndex: 'fullName',
            key: 'fullName',
            render: (text: string, record: FamilyMember) => (
                <a href={`/personaltree/${record.id}`} target="_blank" rel="noopener noreferrer">
                    {record.fullName}
                </a>
            ),
        },
        // ... other columns ...
    ];

    return (
        <div style={{ padding: '20px' }}>
            <Table<FamilyMember>
                dataSource={familyMembers}
                columns={columns}
                pagination={false}
                loading={loading}
                rowKey="id"
            />
        </div>
    );
};

export default FamilyMembersTable;
