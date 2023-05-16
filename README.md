# strong.team-newsapp
Spring Boot is an application for storing, processing and displaying news

## Description
This project is a News App that allows users to browse and read news from various sources. It provides a simple and intuitive user interface that allows users to search for news based on different criteria such as topic, source, and date.
The News App is built using `Java` and the `Spring Framework`. It uses a `PostgreSQL/H2 memory`(for your chose) database to store the news topics and related information. `Spring Security 5` to implement security issues, `JWT` to authentcate via token.


## Running
To build and run the project, follow these steps:

* Clone the repository: `https://github.com/Bakbergenchick/strong.team-newsapp.git`
* Navigate to the project directory: cd newsapp
* Add database "newsdb" to postgres or at using h2 memory doens't necessary to do
* If you want to run at command line:
  * Build the project: mvn clean install
  * Run the project: mvn spring-boot:run
* Otherwise you can do it manually using IDE run button


## SignUp
First we need to sign up to make requests to secured endpoints: `POST localhost:8080/api/user/register`
```
{
    "firstname": "Bakbergen",
    "surname": "Atymtay",
    "email": "atymtaev@mail.ru",
    "password": "123",
    "roles": ["admin", "mod", "user"]
}

```
<img width="626" alt="image" src="https://github.com/Bakbergenchick/strong.team-newsapp/assets/79043496/293b9f5b-a70d-4039-970b-c2319607718d">

## SignIn and get JWT Token
After registration, we need to login to authenticate existing user: `POST localhost:8080/api/user/login`
```
{
    "username": "atymtaev@mail.ru",
    "password": "123"
}
```
<img width="634" alt="image" src="https://github.com/Bakbergenchick/strong.team-newsapp/assets/79043496/4517f350-1aea-4eba-9087-77507e9ae870">

## Make Request to Secure API
To check endpoint, we use JWT token: `localhost:8080/api/user`

```Jwt-Header {jwtToken}```

<img width="635" alt="image" src="https://github.com/Bakbergenchick/strong.team-newsapp/assets/79043496/019942df-b95d-41bb-8459-1c6cba2bbcb5">

## Main Entities endpoint test
After launch application going initial inserts:

<img width="576" alt="image" src="https://github.com/Bakbergenchick/strong.team-newsapp/assets/79043496/922d85a4-c94a-466a-81d2-87e4c5b06f05">


### News entity testing
By taken JWT token, we can have access to resources with depending permission:

#### Add news
`POST localhost:8080/api/user`
```
{
    "title": "Breaking news",
    "content": "Lorem ipsum dolor sit amet, consectetur adipiscing elit.",
    "publishedAt": "2023-05-12",
    "sourceId": 2,
    "topics": ["business", "policy", "tech"]
}

```
<img width="632" alt="image" src="https://github.com/Bakbergenchick/strong.team-newsapp/assets/79043496/6bec4688-8583-47a1-8d36-5d4d0054912e">


#### Get news(with pagination)
By default page number is 0, size is 3: `GET localhost:8080/api/user`

<img width="638" alt="image" src="https://github.com/Bakbergenchick/strong.team-newsapp/assets/79043496/a6f1eb34-7435-4710-8de7-45cef6c6c243">


#### Get by source id(with pagination)
`GET localhost:8080/api/user/bySource/{sourceId}`

<img width="641" alt="image" src="https://github.com/Bakbergenchick/strong.team-newsapp/assets/79043496/62f651fc-0ef5-44ee-a96f-fdad6e3c2370">

#### Update news by Id
`PUT localhost:8080/api/user/{newsId}`

<img width="621" alt="image" src="https://github.com/Bakbergenchick/strong.team-newsapp/assets/79043496/e4d1aeed-52ec-40e5-81cd-c81a8cc12243">

Like these examples work other entities and endpoints

## Creating of Scheduling Task(Part 3)
I've implemented a scheduled statistical task in your Spring Boot application. Which is create a daily task that generates a report file containing the number of news for each news source.
This task build by using `@Scheduled` annotation and `Spring's task executor` is properly configured for multithreading.

### Spring schedule taks:
<img width="444" alt="image" src="https://github.com/Bakbergenchick/strong.team-newsapp/assets/79043496/5f03493a-f346-4897-a8cc-91c4722ecdef">

### Spring multithreading settings:
<img width="573" alt="image" src="https://github.com/Bakbergenchick/strong.team-newsapp/assets/79043496/2858e597-a594-4701-87e9-7a6d8152b7e8">


