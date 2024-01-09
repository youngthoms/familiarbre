import {Col, Row, Select, Typography} from 'antd';
const { Text } = Typography;


function TreeVisibilityOptions() {
    const token = localStorage.getItem('jwtToken');
    const handleChange = async(value: any) => {
        if (token) {
            try {
                // @ts-ignore
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
                console.log(`its good privacy ${value}`)
            } catch (error) {
                console.error('Failed to fetch tree:', error);
                // Traitez l'erreur comme vous le souhaitez
            } finally {
            }
        }    };


    return (
        <div>
            <Row align="middle" gutter={[8, 8]} justify="center">
                <Col span={12} style={{ display: 'flex', justifyContent: 'center', alignItems: 'center', gap: '10px' }}>
                    <Text style={{ fontSize: '15px', textAlign: 'center' }}>Veuillez sélectionner une visibilité pour votre arbre :</Text>
                    <Select defaultValue="public" style={{ width: 180 }} onChange={handleChange}>
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
