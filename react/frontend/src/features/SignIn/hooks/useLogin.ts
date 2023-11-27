import { useState } from 'react';

interface SignInFormData {
    email: string;
    password: string;
}

const useLogin = () => {
    const [isLoading, setIsLoading] = useState<boolean>(false);
    const [error, setError] = useState<string | null>(null);

    const signIn = async (formData: SignInFormData) => {
        setIsLoading(true);
        try {
            const response = await fetch(`${import.meta.env.VITE_BASE_URL}/api/users`, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify(formData),
            });
            if (!response.ok) {
                throw new Error('Failed to login');
            }
            // Gestion de la réponse de succès ici
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
