# Ansh Rai Portfolio

A full-stack portfolio website built to showcase my experience, projects, technical skills, and professional journey as a Java Backend Engineer.

Live Website: https://anshrai.in

## Tech Stack

### Frontend

* React
* TypeScript
* Vite
* CSS

### Backend

* Java 21
* Spring Boot 3
* Spring Data JPA
* REST APIs
* H2 Database

## Features

* Responsive modern UI
* Dynamic portfolio content served through REST APIs
* Skills, experience, projects, and education sections
* Contact form integration
* Backend-driven architecture instead of hardcoded frontend content
* CORS-enabled API for frontend-backend communication

## Project Architecture

The application follows a full-stack architecture:

* React frontend consumes REST APIs from Spring Boot backend
* Spring Boot exposes portfolio data through structured endpoints
* JPA manages persistence and entity mapping
* Data is seeded automatically during application startup

## API Endpoints

* GET `/api/profile`
* GET `/api/skills`
* GET `/api/experience`
* GET `/api/projects`
* GET `/api/education`
* POST `/api/contact`

## Key Learnings

While building this project, I gained practical experience with:

* Spring Boot application development
* REST API design
* JPA and database integration
* Frontend-backend communication
* Deployment using Vercel and Railway
* CORS configuration and production environment setup

## Future Improvements

* MySQL/PostgreSQL integration
* Email notifications from contact form
* Admin dashboard for content management
* Docker containerization
* CI/CD pipeline integration

## Author

Ansh Rai

* Portfolio: https://anshrai.in
* GitHub: https://github.com/ansh5025
* LinkedIn: https://www.linkedin.com/in/ansh-rai/
* Email: [anshrai922@gmail.com](mailto:anshrai922@gmail.com)
