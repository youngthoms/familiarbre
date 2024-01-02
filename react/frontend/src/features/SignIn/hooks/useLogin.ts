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
            const { token } = await response.json();  // Extract the token from the response
            console.log("Token received: ", token);

            // Decode the token
            const decoded: DecodedToken = jwtDecode(token);
            console.log("User ID from token: ", decoded.userId);

            // Store the token in localStorage
            localStorage.setItem('jwtToken', token);
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
