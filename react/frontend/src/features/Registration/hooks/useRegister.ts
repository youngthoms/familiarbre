import { useState } from 'react';

interface SignUpFormData {
    username: string;
    email: string;
    password: string;
}

const useRegister = () => {
    const [isLoading, setIsLoading] = useState<boolean>(false);
    const [error, setError] = useState<string | null>(null);

    const signUp = async (formData: SignUpFormData) => {
        setIsLoading(true);
        try {
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
            // Tu peux traiter la réponse ici si nécessaire
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
