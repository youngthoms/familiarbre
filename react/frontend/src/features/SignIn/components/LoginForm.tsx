import { Button, Form, Input } from 'antd';
import useLogin from "../hooks/useLogin";
import '../styles/LoginForm.css';

const LoginForm = () => {
    const [form] = Form.useForm();
    const { signIn, isLoading, error } = useLogin(); // Utilisez le hook de connexion ici

    const onFinish = async (values: any) => {
        console.log('Received values of form:', values);
        await signIn(values); // Appel de la fonction de connexion au lieu de signUp
        console.log('Login successful');
    };

    return (
        <div className="login-form-container">
            <h1>Login</h1>
            <Form form={form} name="login" onFinish={onFinish} scrollToFirstError>
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
                    <Input.Password autoComplete="off" />
                </Form.Item>

                <Form.Item>
                    <Button type="primary" htmlType="submit" loading={isLoading}>
                        Login
                    </Button>
                </Form.Item>
                {error && <p style={{ color: 'red' }}>{error}</p>}
            </Form>
        </div>
    );
};

export default LoginForm;
