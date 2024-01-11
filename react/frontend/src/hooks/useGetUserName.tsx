import { useState, useEffect } from 'react';

export const useGetUserName = () => {
    const [userName, setUserName] = useState('');

    useEffect(() => {
        const name = localStorage.getItem('firstName');
        const surname = localStorage.getItem('LastName');
        if (name && surname) {
            setUserName(`${name} ${surname}`);
        }
    }, []);

    return userName;
};
