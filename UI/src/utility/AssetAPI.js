const api = "http://localhost:8080";
let token = sessionStorage.token;

if (!token) token = sessionStorage.token = Math.random().toString(36).substr(-8);

const headers = {
  Accept: "application/json",
  Authorization: `Bearer ${token}`,
};

export const get = (assetId) =>
  fetch(`${api}/asset/find/${assetId}`, { headers }).then((res) => res.json());

export const getAll = () =>
  fetch(`${api}/asset/find/all`, { headers }).then((res) => res.json());
  
export const getExists = (title, type) =>
  fetch(`${api}/asset/getAssetExists/${title}/${type}`, { headers }).then((res) => res.json()).then(data => data);

export const update = (asset) =>
  fetch(`${api}/asset/${asset.id}`, {
    method: "PUT",
    headers: {
      ...headers,
      "Content-Type": "application/json",
    },
    body: JSON.stringify({ asset }),
  }).then((res) => res.json());

export const deleteById = async (id) => {
  try {
    const response = await fetch(`${api}/asset/delete/${id}`, {
      method: "DELETE",
      headers: {
        ...headers,
        "Content-Type": "application/json",
      },
    });

    return response;
  } catch (error) {
    throw new Error(`Failed to delete asset with ID ${id}: ${error.message}`);
  }
};

export const addAsset = async (assetData) => {
  try {
    const response = await fetch(`${api}/asset/add`, {
      method: 'POST',
      headers: {
        ...headers,
        'Content-Type': 'application/json',
      },
      body: JSON.stringify(assetData),
    });

    return response;
  } catch (error) {
    throw new Error('Failed to add asset:', error);
  }
};
