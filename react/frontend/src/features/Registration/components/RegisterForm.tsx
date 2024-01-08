import { Button, DatePicker, Form, Input, Select } from 'antd';
import useRegister from "../hooks/useRegister.ts";
import './RegisterForm.css'

const dateFormat = 'DD/MM/YYYY';

const RegisterForm = () => {
    const [form] = Form.useForm();
    const { signUp, isLoading, error } = useRegister();

    const onFinish = async (values: any) => {
        console.log('Received values of form:', values);
        const formattedValues = {
            ...values,
            birthDay: values.birthDay ? values.birthDay.format(dateFormat) : null
        };
        await signUp(formattedValues);
        console.log('its good')
    };

    return (
        <div className="register-form-container">
            <h1>Register</h1>
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
                    <Input/>
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
                    name="firstName"
                    label="First name"
                    rules={[
                        {
                            type: 'string',
                            message: '',
                        },
                        {
                            required: true,
                            message: 'Please input your first name',
                        },
                    ]}
                >
                    <Input/>
                </Form.Item>

                <Form.Item
                    name="lastName"
                    label="Last name"
                    rules={[
                        {
                            type: 'string',
                            message: '',
                        },
                        {
                            required: true,
                            message: 'Please input your last name',
                        },
                    ]}
                >
                    <Input/>
                </Form.Item>

                <Form.Item
                    name="birthDay"
                    label="Birthday"
                    rules={[
                        {
                            type: 'date',
                            message: '',
                        },
                        {
                            required: true,
                            message: 'Please input your birthday',
                        },
                    ]}
                >
                    <DatePicker format={dateFormat}/>
                </Form.Item>

                <Form.Item
                    name="gender"
                    label="Gender"
                    rules={[
                        {
                            message: '',
                        },
                        {
                            required: true,
                            message: 'Please input your gender',
                        },
                    ]}
                >
                    <Select placeholder="Select your gender">
                        <Select.Option value="male">Male</Select.Option>
                        <Select.Option value="female">Female</Select.Option>
                    </Select>
                </Form.Item>

                <Form.Item
                    name="socialSecurityNumber"
                    label="Social security number"
                    rules={[
                        {
                            type: 'string',
                            message: '',
                        },
                        {
                            required: true,
                            message: 'Please input your Social security number',
                        },
                    ]}
                >
                    <Input/>
                </Form.Item>

                <Form.Item>
                    <Button type="primary" htmlType="submit" loading={isLoading}>
                        Register
                    </Button>
                </Form.Item>
                {error && <p style={{color: 'red'}}>{error}</p>}
            </Form>
        </div>
    );
};

export default RegisterForm;