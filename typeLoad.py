import requests
import json

# Function to authenticate the user and obtain an authentication token
def authenticate_user(username, password):
    login_url = 'http://localhost:8080/login'
    login_data = {'username': username, 'password': password}
    response = requests.post(login_url, json=login_data)
    if response.status_code == 200:
        return response.json().get('token')  
    else:
        print(f"Failed to authenticate user: {response.status_code}")
        return None

base_url = 'http://localhost:8080'
add_type_endpoint = '/type/add'

username = input("Enter your username: ")
password = input("Enter your password: ")

token = authenticate_user(username, password)
if not token:
    exit()

types = [
    {
        'typeName': 'Document',
        'customAttribute1': 'Originator',
        'customAttribute2': 'Security Rating',
        'customAttribute3': 'Location',
        'customAttribute4': 'Project Name'
    },
    {
        'typeName': 'Project Report',
        'customAttribute1': 'Project Name',
        'customAttribute2': 'Budget',
        'customAttribute3': 'Project Manager',
        'customAttribute4': 'Milestone'
    },
    {
        'typeName': 'Source Code',
        'customAttribute1': 'Version',
        'customAttribute2': 'GitHub',
        'customAttribute3': 'Issues'
    }
]

for type_data in types:
    url = base_url + add_type_endpoint
    headers = {
        'Content-Type': 'application/json',
        'Authorization': f'Bearer {token}' 
    }
    response = requests.post(url, json=type_data, headers=headers)

    if response.status_code == 200:
        print(f"Type added successfully: {type_data['typeName']}")
    else:
        print(f"Failed to add type: {type_data['typeName']}. Status code: {response.status_code}")
