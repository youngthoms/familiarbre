import { useState } from 'react';
import {useNavigate} from "react-router-dom";

interface SignInFormData {
    email: string;
    password: string;
}

const useLogin = () => {
    const [isLoading, setIsLoading] = useState<boolean>(false);
    const [error, setError] = useState<string | null>(null);
    const navigate = useNavigate(); // Ajout de useNavigate

    const signIn = async (formData: SignInFormData) => {
        setIsLoading(true);
        try {
            //@ts-ignored
            const response = await fetch(`${import.meta.env.VITE_BASE_URL}/api/auth/authenticate`, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify(formData),
            });
            if (!response.ok) {
                throw new Error('Failed to login');
            }
            const data = await response.json();
            console.log(data);
            localStorage.setItem('jwtToken', data.token);
            localStorage.setItem('userId', data.userId);
            navigate('/personaltree');
            console.log("tudo bem");
        } catch (err) {
            // @ts-ignore
            setError(err.message);
        } finally {
            setIsLoading(false);
        }
    };

    return { signIn, isLoading, error };
};

export default useLogin;
