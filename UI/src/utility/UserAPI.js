const api = "http://localhost:8080";
let token = sessionStorage.token;

if (!token) token = sessionStorage.token = Math.random().toString(36).substr(-8);

const headers = {
  Accept: "application/json",
  Authorization: `Bearer ${token}`,
};

export const get = (username) =>
  fetch(`${api}/user/${username}`, { headers }).then((res) => res.json());

export const getAll = () =>
  fetch(`${api}/user/find/all`, { headers }).then((res) => res.json());

export const update = (user) =>
  fetch(`${api}/user/${user.id}`, {
    method: "PUT",
    headers: {
      ...headers,
      "Content-Type": "application/json",
    },
    body: JSON.stringify({ user }),
  }).then((res) => res.json());

export const deleteById = async (id) => {
  try {
    const response = await fetch(`${api}/user/delete/${id}`, {
      method: "DELETE",
      headers: {
        ...headers,
        "Content-Type": "application/json",
      },
    });

    return response;
  } catch (error) {
    throw new Error(`Failed to delete user with ID ${id}: ${error.message}`);
  }
};

export const updateRole = async (userData) => {
  try {
    const response = await fetch(`${api}/user/${userData.updatingUsername}/role`, {
      method: 'PUT',
      headers: {
        ...headers,
        'Content-Type': 'application/json',
      },
      body: JSON.stringify({
        "role" : userData.updatingRole
      }),
    });

    return response;
  } catch (error) {
    throw new Error('Failed to update role:', error);
  }
};