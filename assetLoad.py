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
add_asset_endpoint = '/asset/add'

username = input("Enter your username: ")
password = input("Enter your password: ")

token = authenticate_user(username, password)
if not token:
    exit()

assets = [
    {
        'type': 'Document',
        'title': 'Customer Requirements',
        'link': 'http://example.com/asset1',
        'author': 'Tony Giles',
        'association1': 1,
        'association2': 2,
        'association3': 3,
        'association4': 4,
        'associationRelation1': 'Is Documented In',
        'associationRelation2': 'Is Part Of',
        'associationRelation3': 'Is Preceded By',
        'associationRelation4': 'Is Parent Of',
        'customAttribute1': 'Jim Prince',
        'customAttribute2': 'Official Sensitive',
        'customAttribute3': 'Local',
        'customAttribute4': 'Prometheus'
    },
    {
        'type': 'Project Report',
        'title': 'Monthly Project Update',
        'link': 'www.prince2.com',
        'author': 'Tony Giles',
        'association1': 1,
        'association2': 2,
        'association3': 3,
        'association4': 4,
        'associationRelation1': 'Is Documented In',
        'associationRelation2': 'Is Part Of',
        'associationRelation3': 'Is Preceded By',
        'associationRelation4': 'Is Parent Of',
        'customAttribute1': 'Artemis',
        'customAttribute2': 'Q1',
        'customAttribute3': 'Alan Cooper',
        'customAttribute4': 'Delivery'
    },
    {
        'type': 'Document',
        'title': 'Initial Specification',
        'link': 'http://example.com/asset3',
        'author': 'Tony Giles',
        'association1': 1,
        'association2': 2,
        'association3': 3,
        'association4': 4,
        'associationRelation1': 'Is Documented In',
        'associationRelation2': 'Is Part Of',
        'associationRelation3': 'Is Preceded By',
        'associationRelation4': 'Is Parent Of',
        'customAttribute1': 'Tom Clancy',
        'customAttribute2': 'Unclassified',
        'customAttribute3': 'Cloud',
        'customAttribute4': 'Flame'
    }
]

for asset_data in assets:
    url = base_url + add_asset_endpoint
    headers = {
        'Content-Type': 'application/json',
        'Authorization': f'Bearer {token}' 
    }
    response = requests.post(url, json=asset_data, headers=headers)

    if response.status_code == 200:
        print(f"Asset added successfully: {asset_data['title']}")
    else:
        print(f"Failed to add type: {asset_data['title']}. Status code: {response.status_code}")
