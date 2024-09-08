const api = "http://localhost:8080";

export const register = async (userData) => {
    try {
      const response = await fetch(`${api}/register`, {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify(userData),
      });
  
      return response.json();
    } catch (error) {
      throw new Error('Failed to create user:', error.message);
    }
};