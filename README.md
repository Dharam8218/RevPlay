# 🎵 RevPlay – Music Streaming Web Application

![Java](https://img.shields.io/badge/Backend-Java-orange)
![Spring Boot](https://img.shields.io/badge/SpringBoot-4.0.2-brightgreen)
![Angular](https://img.shields.io/badge/Frontend-Angular-red)
![JWT](https://img.shields.io/badge/Auth-JWT-blue)
![License](https://img.shields.io/badge/Status-Active-success)

RevPlay is a full-stack monolithic music streaming web application that allows users to explore, stream, and manage music content including songs, artists, albums, playlists, and podcasts. The platform supports role-based access for Listeners and Artists and includes a built-in web music player.

--

## 🚀 Application Overview

RevPlay provides:

- 🎧 Music streaming with integrated player  
- 🔎 Advanced search & genre-based browsing  
- ❤️ Favorites & playlist management  
- 👤 User & Artist profiles  
- 📊 Real-time user statistics  
- 🔐 Secure authentication with JWT  
- 🎭 Role-based authorization  

The application is designed with scalable architecture and can be extended into microservices in future phases.

---

## 🏗️ Tech Stack

### 🔹 Backend
- Java  
- Spring Boot  
- Spring Security  
- JWT Authentication  
- Spring Data JPA (Hibernate)  
- REST APIs  
- MySQL / Oracle (Configurable)  
- Maven  

### 🔹 Frontend
- Angular  
- TypeScript  
- Tailwind CSS  
- RxJS  
- Angular Routing  
- HTTP Interceptor (JWT Token Handling)  

---

## 👥 User Roles

### 🎧 Listener (User)
- Register & Login  
- Browse songs, albums, playlists  
- Search by keyword or genre  
- Create & manage playlists  
- Mark songs as favorite  
- View listening history  
- View profile statistics  
- Play songs using web player  

### 🎤 Artist
- Create artist profile  
- Upload songs  
- Manage music content  
- View engagement statistics  

---

## 🔐 Authentication & Security

- JWT-based authentication  
- Role-based authorization (ROLE_USER, ROLE_ARTIST, ROLE_ADMIN)  
- Password encryption  
- Angular HTTP Interceptor for token injection  
- Protected routes  

---

## 📂 Project Structure

### Backend (Spring Boot)

```
com.revature.revplay
│
├── controller
├── service
├── repository
├── model
├── dto
├── config
└── security
```

### Frontend (Angular)

```
src/app
│
├── components
│   ├── navbar
│   ├── sidebar
│   ├── player
│   ├── playlist
│   └── genre
│
├── services
├── interceptors
├── models
└── pages
```

---

## ⚙️ Setup Instructions

### 🔹 Backend Setup

1. Clone the repository


git clone https://github.com/Dharam8218/RevPlay.git


2. Configure `application.properties`


spring.datasource.url=jdbc:mysql://localhost:3306/revplay
spring.datasource.username=your_username
spring.datasource.password=your_password

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true

jwt.secret=your_secret_key
jwt.expiration=86400000


3. Run the application


mvn spring-boot:run


Backend runs on:


http://localhost:8080


---

### 🔹 Frontend Setup

1. Navigate to frontend folder


cd revplay-frontend


2. Install dependencies


npm install


3. Run Angular application


ng serve


Frontend runs on:


http://localhost:4200


---

## 🔄 API Features

- User Registration & Login  
- Song CRUD operations  
- Playlist management  
- Genre filtering  
- Artist management  
- Favorites handling  
- Secure endpoints with JWT  

---

## 🎵 Core Features Implemented

✔ User Authentication  
✔ Role-Based Authorization  
✔ Browse Songs by Genre  
✔ Search Functionality  
✔ Create & Manage Playlists  
✔ Favorites System  
✔ Music Player Component  
✔ HTTP Interceptor  
✔ Responsive UI  

---

## 📊 Future Enhancements

- Microservices Architecture  
- Payment Integration (Premium Plans)  
- Real-time Streaming Optimization  
- Social Features (Follow Artists)  
- Recommendation System  
- Cloud Deployment (AWS/Azure)  

---

## 🧠 Learning Outcomes

- Full-stack development  
- Secure REST API design  
- JWT Authentication & Authorization  
- Angular State Management  
- Component-based UI architecture  
- Database design with JPA  

---

## 👨‍💻 Author

**Dharamveer Singh**  
Java Full Stack Developer  

---
