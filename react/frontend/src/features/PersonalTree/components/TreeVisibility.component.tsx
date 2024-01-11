import React, { useState, useEffect } from 'react';
import { Col, Row, Select, Typography } from 'antd';
const { Text } = Typography;


function TreeVisibilityOptions({ userId }) {
    const [currentPrivacy, setCurrentPrivacy] = useState('public'); // default value
    const token = localStorage.getItem('jwtToken');

    useEffect(() => {
        const fetchCurrentPrivacy = async (value: any) => {
            if (token) {
                try {
                    const response = await fetch(`${import.meta.env.VITE_BASE_URL}/api/family-members/privacy/${value}`, {
                        method: 'GET',
                        headers: {
                            'Content-Type': 'application/json',
                            'Authorization': `Bearer ${token}`,
                        },
                    });

                    if (!response.ok) {
                        throw new Error('Failed to fetch current privacy setting');
                    }

                    const data = await response.json();
                    setCurrentPrivacy(data.status);
                } catch (error) {
                    console.error('Error fetching current privacy setting:', error);
                }
            }
        };

        fetchCurrentPrivacy(userId);
    }, [token, userId]);

    const handleChange = async (value) => {
        if (token) {
            try {
                const response = await fetch(`${import.meta.env.VITE_BASE_URL}/api/family-members/privacy/${token}/${value}`, {
                    method: 'GET',
                    headers: {
                        'Content-Type': 'application/json',
                        'Authorization': `Bearer ${token}`,
                    },
                });

                if (!response.ok) {
                    throw new Error('Failed to send visibility');
                }
                console.log(`Privacy set to ${value}`);
                setCurrentPrivacy(value); // Update the current privacy state
            } catch (error) {
                console.error('Failed to set tree privacy:', error);
            }
        }
    };

    return (
        <div>
            <Row align="middle" gutter={[8, 8]} justify="center">
                <Col span={12} style={{ display: 'flex', justifyContent: 'center', alignItems: 'center', gap: '10px' }}>
                    <Text style={{ fontSize: '15px', textAlign: 'center' }}>Veuillez sélectionner une visibilité pour votre arbre :</Text>
                    <Select value={currentPrivacy} style={{ width: 180 }} onChange={handleChange}>
                        <Select.Option value="public">Public</Select.Option>
                        <Select.Option value="private">Privé</Select.Option>
                        <Select.Option value="protected">Protégé</Select.Option>
                    </Select>
                </Col>
            </Row>
        </div>
    );
}

export default TreeVisibilityOptions;
