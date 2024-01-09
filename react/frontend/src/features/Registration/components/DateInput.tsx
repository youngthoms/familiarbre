import { useState } from 'react';
import { Input } from 'antd';

const DateInput = () => {
    const [value, setValue] = useState('');

    const handleChange = (e: { target: { value: string; }; }) => {
        let inputValue = e.target.value.replace(/[^\d]/g, ''); // Enlevez tout caractère non-numérique
        // Format: DD/MM/YYYY
        if (inputValue.length > 4) {
            inputValue = inputValue.slice(0, 2) + '/' + inputValue.slice(2, 4) + '/' + inputValue.slice(4);
        } else if (inputValue.length > 2) {
            inputValue = inputValue.slice(0, 2) + '/' + inputValue.slice(2);
        }
        setValue(inputValue);
    };

    return <Input value={value} onChange={handleChange} maxLength={10} />;
};

export default DateInput;
