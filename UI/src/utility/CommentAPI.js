const api = "http://localhost:8080";
let token = sessionStorage.token;

if (!token) token = sessionStorage.token = Math.random().toString(36).substr(-8);

const headers = {
  Accept: "application/json",
  Authorization: `Bearer ${token}`,
};

export const get = (commentId) =>
  fetch(`${api}/comment/find/${commentId}`, { headers })
    .then((res) => res.json())

export const getAll = () =>
  fetch(`${api}/comment/find/all`, {headers})
    .then((res) => res.json())

export const update = (comment) =>
fetch(`${api}/comment/${comment.id}`, {
      method: "PUT",
      headers: {
        ...headers,
        "Content-Type": "application/json",
      },
      body: JSON.stringify({ comment }),
    })
    .then((res) => res.json());

export const addComment = async (commentData) => {
  try {
    const response = await fetch(`${api}/comment/add`, {
      method: 'POST',
      headers: {
        ...headers,
        'Content-Type': 'application/json',
      },
      body: JSON.stringify({
        ...commentData,
        visibleComment: commentData.visibleComment, 
      }),
    });

    return response;
  } catch (error) {
    throw new Error('Failed to add Comment:', error);
  }
};

