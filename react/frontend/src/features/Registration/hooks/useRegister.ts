import { useState } from 'react';
import {useNavigate} from "react-router-dom";

interface SignUpFormData {
    email: string;
    password: string;
    firstName: string;
    lastName: string;
    birthDay: Date;
    gender: string;
    socialSecurityNumber: string;
}

const useRegister = () => {
    const [isLoading, setIsLoading] = useState<boolean>(false);
    const [error, setError] = useState<string | null>(null);
    const navigate = useNavigate(); // Ajout de useNavigate

    const signUp = async (formData: SignUpFormData) => {
        setIsLoading(true);
        try {
            //@ts-ignored
            const response = await fetch(`${import.meta.env.VITE_BASE_URL}/api/auth/register`, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify(formData),
            });
            if (!response.ok) {
                throw new Error('Failed to sign up');
            }
            const data = await response.json();
            localStorage.setItem('jwtToken', data.token);
            localStorage.setItem('userId', data.userId);
            localStorage.setItem('name', data.name);
            navigate('/personaltree');
        } catch (err) {
            // @ts-ignore
            setError(err.message);
        } finally {
            setIsLoading(false);
        }
    };

    return { signUp, isLoading, error };
};

export default useRegister;
