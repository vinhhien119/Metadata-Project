import requests
import json

# Basic python script to populate the Database
base_url = 'http://localhost:8080'

add_user_endpoint = '/register'

users = [
    {
        "firstName": "Admin",
        "lastName": "Adminson",
        "username": "admin_acct",
        "password": "pass",
        "role": "ADMIN"
    },
    {
        "firstName": "User",
        "lastName": "Userson",
        "username": "user_acct",
        "password": "pass",
        "role": "USER"
    },
    {
        "firstName": "Reader",
        "lastName": "Readerson",
        "username": "reader_acct",
        "password": "pass",
        "role": "READER"
    }
]

# Loop through the list of users and make POST requests to add them to the database
for user in users:
    url = base_url + add_user_endpoint
    headers = {'Content-Type': 'application/json'}
    response = requests.post(url, json=user, headers=headers)

    # Check the response status code
    if response.status_code == 200:
        print(f"User added successfully: {user['username']}")
    else:
        print(f"Failed to add user: {user['username']}. Status code: {response.status_code}")
