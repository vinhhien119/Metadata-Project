const api = "http://localhost:8080";

export const login = async (userData) => {
    try {
        const response = await fetch(`${api}/login`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(userData),
        });

        if (response.ok) {
            const data = await response.json();
            if (data.token) {
                sessionStorage.setItem('token', data.token);
                sessionStorage.setItem('role', data.role);
                sessionStorage.setItem('username', data.username)
            }
            return data;
        } else {
            throw new Error('Failed to login: Invalid credentials');
        }
    } catch (error) {
        throw new Error('Failed to login:', error.message);
    }
};