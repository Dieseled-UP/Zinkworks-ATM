# ATM Restful App
This is a Spring Boot project simulating a simple ATM

### About API
The ATM provides two services.
* Withdraw service - To withdraw money from the ATM
* Check balance service - To check current balance of user

### Running project the project
* Clone the project by running command
```
$ git clone https://github.com/Dieseled-UP/Zinkworks-ATM
$ cd Zinkworks-ATM
```

* Then prepare project dependencies with
```
$ mvn clean install
```

* To run the project can be either done using Spring Boot or using Docker
```
$ mvn spring-boot:run
```
```
$ docker build --tag=atm:latest .
$ docker run -p 8080:8080 atm:latest
```

The project should start on port 8080

### How to test the API

You can use Postman or Curl to test the endpoints
There are two accounts already setup and one ATM

* Account Details
```
Account 1: acccountNumber - 123456789, pin - 1234, balance - 800, overdraft - 200
Account 2: accountNumber - 987654321, pin - 4321, balance - 1230, overdraft - 150
```
The ATM is set with a default balance of 1500 made up of the following:
<ul>
<li>10 x 50</li>
<li>30 x 20</li>
<li>30 x 10</li>
<li>20 x 5</li>
</ul>

The first endpoint can be used to check the users balance with the parameters passed the users account number and the users PIN
```
http://localhost:8080/api/v1/account/balance/{accountNumber}/{pinNumber}
```
<br/>The response should look like
```
{
    "payload": {
        "remainingBalance": 755.00,
        "remainingOverdraft": 200.00,
        "withdraw": 45
    },
    "success": true
}
```
The second endpoint can be used to withdraw money with the parameters passed the users account number, PIN, and amount to withdraw
```
http://localhost:8080/api/v1/account/balance/{accountNumber}/{pinNumber}/{amount}
```
<br/>The response should look like
```
{
"payload": 500.00,
"success": true
}
```

#### Possible API response

```
{
    "payload": "Sorry your account has insufficient funds",
    "success": false
}
```

```
{
    "payload": "Sorry this ATM cannot complete this transaction due to insufficient funds",
    "success": false
}
```

```
{
    "payload": "Invalid pin number",
    "success": false
}
```

```
{
    "payload": "Sorry withdrawal requests must be in multiples of 5",
    "success": false
}
```

#### Notes
* Application does not implement security
* Application does not use encryption
* Application does not have a front-end
