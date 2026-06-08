# Ansh Rai — Portfolio (`anshrai.in`)

A full-stack portfolio website for **Ansh Rai**, focused on a **Java Backend Software Engineer** profile.

- **Frontend:** React 18 + TypeScript + Vite (modern dark theme, responsive)
- **Backend:** Spring Boot 3 + Java 21 + Spring Data JPA + H2 (in-memory)
- **API style:** REST (JSON), CORS-ready for `localhost` and `https://anshrai.in`

---

## 📁 Project Structure

```
PORTFOLIO/
├── backend/                                  # Spring Boot REST API
│   ├── pom.xml
│   └── src/
│       ├── main/
│       │   ├── java/in/anshrai/portfolio/
│       │   │   ├── PortfolioApplication.java
│       │   │   ├── config/
│       │   │   │   ├── DataSeeder.java       # Seeds H2 with portfolio data
│       │   │   │   └── WebConfig.java        # CORS configuration
│       │   │   ├── controller/               # REST endpoints
│       │   │   │   ├── ProfileController.java
│       │   │   │   ├── SkillController.java
│       │   │   │   ├── ExperienceController.java
│       │   │   │   ├── ProjectController.java
│       │   │   │   ├── EducationController.java
│       │   │   │   ├── ContactController.java
│       │   │   │   └── GlobalExceptionHandler.java
│       │   │   ├── service/                  # Business logic
│       │   │   ├── repository/               # JPA repositories
│       │   │   ├── model/                    # JPA entities
│       │   │   └── dto/                      # Request/response DTOs
│       │   └── resources/
│       │       └── application.properties
│       └── test/java/in/anshrai/portfolio/
│           └── PortfolioApplicationTests.java
│
├── frontend/                                 # React + Vite app
│   ├── index.html
│   ├── package.json
│   ├── tsconfig.json
│   ├── vite.config.ts
│   ├── public/favicon.svg
│   └── src/
│       ├── main.tsx
│       ├── App.tsx
│       ├── vite-env.d.ts
│       ├── components/                       # UI sections
│       │   ├── Navbar.tsx
│       │   ├── Hero.tsx
│       │   ├── About.tsx
│       │   ├── Skills.tsx
│       │   ├── Experience.tsx
│       │   ├── Projects.tsx
│       │   ├── Education.tsx
│       │   ├── Contact.tsx
│       │   └── Footer.tsx
│       ├── services/
│       │   ├── api.ts                        # Axios client
│       │   └── fallback.ts                   # Offline/dev fallback data
│       ├── types/
│       │   └── index.ts                      # Shared TS types
│       └── styles/
│           └── global.css                    # Theme + all section styles
│
└── PORTFOLIO.code-workspace
```

---

## ✅ Prerequisites

| Tool | Version |
|------|---------|
| **JDK** | 21+ |
| **Maven** | 3.9+ (or use the Maven Wrapper) |
| **Node.js** | 18+ (LTS recommended) |
| **npm** | 9+ |

---

## 🚀 Run locally

Open **two terminals** — one for the backend, one for the frontend.

### 1. Backend (Spring Boot)

```powershell
cd backend
mvn spring-boot:run
```

Backend will start on **http://localhost:8080**.

Useful URLs:
- API base — http://localhost:8080/api
- H2 console — http://localhost:8080/h2 (JDBC URL: `jdbc:h2:mem:portfoliodb`, user `sa`, blank password)

### 2. Frontend (React + Vite)

```powershell
cd frontend
npm install
npm run dev
```

Frontend will start on **http://localhost:5173** and proxy `/api/*` to the backend (configured in [vite.config.ts](frontend/vite.config.ts)).

Open http://localhost:5173 in your browser.

---

## 🌐 REST API

All responses are wrapped in `{ success, message, data }`.

| Method | Endpoint           | Description                  |
|--------|--------------------|------------------------------|
| GET    | `/api/profile`     | Basic profile + socials      |
| GET    | `/api/skills`      | Skills grouped by category   |
| GET    | `/api/experience`  | Work experience list         |
| GET    | `/api/projects`    | Projects list                |
| GET    | `/api/education`   | Education list               |
| POST   | `/api/contact`     | Submit contact form          |

`POST /api/contact` body:
```json
{
  "name": "Jane Doe",
  "email": "jane@example.com",
  "subject": "Job opportunity",
  "message": "Hi Ansh, we have a backend role..."
}
```

---

## 🛠 Build for production

### Backend
```powershell
cd backend
mvn clean package
java -jar target/portfolio-0.0.1-SNAPSHOT.jar
```

### Frontend
```powershell
cd frontend
npm run build
npm run preview
```
Static build output lands in `frontend/dist/`. You can serve it via any static host (Vercel, Netlify, Nginx) and point its API calls to your deployed backend via the `VITE_API_BASE_URL` env var at build time:

```powershell
$env:VITE_API_BASE_URL="https://api.anshrai.in/api"; npm run build
```

---

## ✏️ Updating your content

Portfolio data lives in **[backend/src/main/java/in/anshrai/portfolio/config/DataSeeder.java](backend/src/main/java/in/anshrai/portfolio/config/DataSeeder.java)** — change the strings there, restart the backend, and the site will reflect your edits.

To swap H2 for **MySQL** (recommended for production):

1. Replace the H2 dependency in [backend/pom.xml](backend/pom.xml) with:
   ```xml
   <dependency>
       <groupId>com.mysql</groupId>
       <artifactId>mysql-connector-j</artifactId>
       <scope>runtime</scope>
   </dependency>
   ```
2. Update [application.properties](backend/src/main/resources/application.properties):
   ```properties
   spring.datasource.url=jdbc:mysql://localhost:3306/portfolio
   spring.datasource.username=root
   spring.datasource.password=YOUR_PASSWORD
   spring.jpa.hibernate.ddl-auto=update
   ```

---

## 🎨 Customization

- **Theme colors** — edit CSS variables at the top of [frontend/src/styles/global.css](frontend/src/styles/global.css)
- **Sections** — toggle/reorder in [frontend/src/App.tsx](frontend/src/App.tsx)
- **Resume** — drop a `resume.pdf` into [frontend/public/](frontend/public/) and the Hero button will serve it at `/resume.pdf`

---

## 📦 Deployment ideas

| Layer    | Suggestion                                              |
|----------|---------------------------------------------------------|
| Frontend | Vercel / Netlify / GitHub Pages (point to `dist/`)      |
| Backend  | Render / Railway / Fly.io / AWS Elastic Beanstalk       |
| Domain   | Point `anshrai.in` → frontend, `api.anshrai.in` → backend |

---

Made with care by **Ansh Rai** · [anshrai922@gmail.com](mailto:anshrai922@gmail.com)
