# Technical Test API

## Table of Contents
- [About](#about)
- [Getting Started](#getting_started)
- [Installing](#installing)
- [Usage](#usage)

<!-- GETTING STARTED -->

## About
Offer technical test

## Getting Started
1. POST ( /users/register ) : Manages the creation of a new user
```sh
    200 ok -> user created successfully
    400 bad request -> user is not valid
    409 conflict -> user already exists
```
2. GET ( /users/username ) : Manages the search of an existing user
```sh
    200 ok -> user found and returned
    404 not found -> user does not exist
```

## Installing
1. Clone the repo
```sh
    git clone git@github.com:boubacardiallo07/technical-test-api.git
```
2. Install mvn
```sh
    mvn clean install
```

## Usage
#### lunch the app
    mvn spring-boot:run
### export Postman collection
    in the file : TechnicalTestAPI.postman_collection.json
    or
    link : https://www.getpostman.com/collections/f0eed7e875cd6962d73e
#### lunch only the tests
    mvn test

#### clean the project
    mvn clean
