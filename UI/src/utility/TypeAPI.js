const api = "http://localhost:8080";
let token = sessionStorage.token;

if (!token) token = sessionStorage.token = Math.random().toString(36).substr(-8);

const headers = {
  Accept: "application/json",
  Authorization: `Bearer ${token}`,
};

export const get = (typeId) =>
  fetch(`${api}/type/find/${typeId}`, { headers }).then((res) => res.json());

export const getAll = () =>
  fetch(`${api}/type/find/all`, { headers }).then((res) => res.json());

export const getTypeExists = (typeName) =>
  fetch(`${api}/type/getTypeExists/${typeName}`, { headers }).then((res) => res.json()).then(data => data);

export const update = (type) =>
  fetch(`${api}/type/${type.id}`, {
    method: "PUT",
    headers: {
      ...headers,
      "Content-Type": "application/json",
    },
    body: JSON.stringify({ type }),
  }).then((res) => res.json());

export const deleteById = async (id) => {
  try {
    const response = await fetch(`${api}/type/delete/${id}`, {
      method: "DELETE",
      headers: {
        ...headers,
        "Content-Type": "application/json",
      },
    });

    return response;
  } catch (error) {
    throw new Error(`Failed to delete type with ID ${id}: ${error.message}`);
  }
};

export const addType = async (typeData) => {
  try {
    const response = await fetch(`${api}/type/add`, {
      method: 'POST',
      headers: {
        ...headers,
        'Content-Type': 'application/json',
      },
      body: JSON.stringify(typeData),
    });

    return response;
  } catch (error) {
    throw new Error('Failed to add Type:', error);
  }
};
