import { Button, Form, Input } from 'antd';
import useRegister from "../hooks/useRegister";

const RegisterForm = () => {
    const [form] = Form.useForm();
    const { signUp, isLoading, error } = useRegister();


    const onFinish = async (values: any) => {
        console.log('Received values of form:', values);
        await signUp(values);
        console.log('its good')
    };

    return (
        <Form form={form} name="register" onFinish={onFinish} scrollToFirstError>
            <Form.Item
                name="email"
                label="Email"
                rules={[
                    {
                        type: 'email',
                        message: 'The input is not valid E-mail!',
                    },
                    {
                        required: true,
                        message: 'Please input your E-mail!',
                    },
                ]}
            >
                <Input />
            </Form.Item>

            <Form.Item
                name="password"
                label="Password"
                rules={[
                    {
                        required: true,
                        message: 'Please input your password!',
                    },
                ]}
                hasFeedback
            >
                <Input
                       type="password"
                       autoComplete="off"
                />
            </Form.Item>
            <Form.Item
                name="secunumber"
                label="Social Security Number"
                rules={[
                    {
                        required: true,
                        message: 'Please input your social security number!',
                    },
                ]}
                hasFeedback
            >
                <Input
                    type="number"
                    autoComplete="off"
                />
            </Form.Item>

            <Form.Item>
                <Button type="primary" htmlType="submit" loading={isLoading}>
                    Register
                </Button>
            </Form.Item>
            {error && <p style={{ color: 'red' }}>{error}</p>}
        </Form>
    );
};

export default RegisterForm;