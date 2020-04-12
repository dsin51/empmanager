# empmanager

To create a Person - 

curl --location --request POST 'http://localhost:8080/person' \
--header 'Content-Type: application/json' \
--data-raw '{
    "name": "Peter",
    "age": 40,
    "email": "peter@test.com",
    "dob": "1980-02-19",
    "salary": 33000.0,
    "currency": "INR"
}'
