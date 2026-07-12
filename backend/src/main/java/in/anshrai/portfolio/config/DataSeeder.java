package in.anshrai.portfolio.config;

import in.anshrai.portfolio.model.*;
import in.anshrai.portfolio.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DataSeeder implements CommandLineRunner {

    private final ProfileRepository profileRepository;
    private final SkillRepository skillRepository;
    private final ExperienceRepository experienceRepository;
    private final ProjectRepository projectRepository;
    private final EducationRepository educationRepository;

    public DataSeeder(ProfileRepository profileRepository,
                      SkillRepository skillRepository,
                      ExperienceRepository experienceRepository,
                      ProjectRepository projectRepository,
                      EducationRepository educationRepository) {
        this.profileRepository = profileRepository;
        this.skillRepository = skillRepository;
        this.experienceRepository = experienceRepository;
        this.projectRepository = projectRepository;
        this.educationRepository = educationRepository;
    }

    @Override
    public void run(String... args) {
        if (profileRepository.count() == 0) {
            seedProfile();
        }
        if (skillRepository.count() == 0) {
            seedSkills();
        }
        if (experienceRepository.count() == 0) {
            seedExperience();
        }
        if (projectRepository.count() == 0) {
            seedProjects();
        }
        if (educationRepository.count() == 0) {
            seedEducation();
        }
    }

    private void seedProfile() {
        profileRepository.save(Profile.builder()
                .name("Ansh Rai")
                .title("Java Backend Engineer")
                .tagline("I work on Java and Spring Boot backends. Currently a System Engineer at Infosys.")
                .summary("I'm a software engineer who works mostly with Java and Spring Boot. "
                        + "I finished my B.Tech in Computer Science in 2025 and joined Infosys as a System Engineer, "
                        + "where I work on Java and Spring Boot backends, REST APIs, and SQL. "
                        + "Most of what I know outside of work came from building side projects and practicing DSA on LeetCode. "
                        + "Right now I'm focused on getting better at backend development and the basics of system design.")
                .email("anshrai922@gmail.com")
                .phone("")
                .location("Pune, Maharashtra, India")
                .linkedinUrl("https://www.linkedin.com/in/ansh-rai-832117218/")
                .githubUrl("https://github.com/ansh5025")
                .leetcodeUrl("https://leetcode.com/ansh5025")
                .codolioUrl("https://codolio.com/profile/RetWCBRJ")
                .resumeUrl("/resume.pdf")
                .avatarUrl("")
                .build());
    }

    private void seedSkills() {
        List<Skill> skills = List.of(
                Skill.builder().category("Languages").name("Java").displayOrder(1).build(),
                Skill.builder().category("Languages").name("Python").displayOrder(2).build(),
                Skill.builder().category("Languages").name("SQL").displayOrder(3).build(),
                Skill.builder().category("Languages").name("JavaScript").displayOrder(4).build(),
                Skill.builder().category("Languages").name("Bash").displayOrder(5).build(),

                Skill.builder().category("Backend").name("Spring Boot").displayOrder(1).build(),
                Skill.builder().category("Backend").name("Spring MVC").displayOrder(2).build(),
                Skill.builder().category("Backend").name("REST APIs").displayOrder(3).build(),
                Skill.builder().category("Backend").name("JPA / Hibernate").displayOrder(4).build(),
                Skill.builder().category("Backend").name("Maven").displayOrder(5).build(),
                Skill.builder().category("Backend").name("Microservices (basics)").displayOrder(6).build(),

                Skill.builder().category("Databases").name("MySQL").displayOrder(1).build(),
                Skill.builder().category("Databases").name("H2").displayOrder(2).build(),
                Skill.builder().category("Databases").name("Firebase").displayOrder(3).build(),

                Skill.builder().category("Testing").name("JUnit").displayOrder(1).build(),
                Skill.builder().category("Testing").name("TestNG").displayOrder(2).build(),
                Skill.builder().category("Testing").name("Selenium").displayOrder(3).build(),
                Skill.builder().category("Testing").name("Postman").displayOrder(4).build(),

                Skill.builder().category("Tools").name("Git").displayOrder(1).build(),
                Skill.builder().category("Tools").name("GitHub").displayOrder(2).build(),
                Skill.builder().category("Tools").name("IntelliJ IDEA").displayOrder(3).build(),
                Skill.builder().category("Tools").name("VS Code").displayOrder(4).build(),
                Skill.builder().category("Tools").name("Jira").displayOrder(5).build(),

                Skill.builder().category("Frontend").name("HTML").displayOrder(1).build(),
                Skill.builder().category("Frontend").name("CSS").displayOrder(2).build(),
                Skill.builder().category("Frontend").name("React (basics)").displayOrder(3).build(),

                Skill.builder().category("Concepts").name("OOP").displayOrder(1).build(),
                Skill.builder().category("Concepts").name("Data Structures & Algorithms").displayOrder(2).build(),
                Skill.builder().category("Concepts").name("SDLC / Agile").displayOrder(3).build(),
                Skill.builder().category("Concepts").name("System Design (basics)").displayOrder(4).build()
        );
        skillRepository.saveAll(skills);
    }

    private void seedExperience() {
        Experience infosys = Experience.builder()
                .company("Infosys")
                .role("System Engineer")
                .location("Pune, India")
                .period("2025 \u2013 Present")
                .current(true)
                .displayOrder(1)
                .bullets(List.of(
                        "Work on Java backend development with Spring Boot on the team's projects.",
                        "Build REST APIs with Spring MVC and test them in Postman against the expected request and response payloads.",
                        "Use Spring Data JPA and Hibernate to map entities and query the database.",
                        "Write SQL to set up data and check results, and cover the code with JUnit tests.",
                        "Work in an Agile team \u2014 sprint planning, daily stand-ups, and retrospectives."
                ))
                .build();
        experienceRepository.save(infosys);
    }

    private void seedProjects() {
        Project ecommerce = Project.builder()
                .name("E-Commerce Platform")
                .description("An online store with product browsing, a cart, and an admin panel behind it. Started as an older-style Spring Boot app and rebuilt into a proper backend and frontend, then deployed live on my own domain.")
                .githubUrl("https://github.com/ansh5025/ecommerce-project")
                .liveUrl("https://ecommerce.anshrai.in")
                .displayOrder(1)
                .techStack(List.of("Java", "Spring Boot", "Spring Security", "Hibernate", "MySQL", "React", "Tailwind CSS", "Azure"))
                .bullets(List.of(
                        "Rebuilt the old app as a REST API with its own React frontend, with separate logins for admins and customers.",
                        "Added product, category, and cart management, plus an admin panel to manage all of it.",
                        "Deployed everything to Azure with its own database, a custom domain, and HTTPS.",
                        "Set up GitHub Actions so pushing to main builds and deploys both sides automatically.",
                        "Found and fixed a tricky login bug that only showed up once the frontend and backend were split onto separate domains."
                ))
                .build();

        Project fitness = Project.builder()
                .name("Fitness Tracker Web App")
                .description("A web app for logging workouts and diet and tracking basic fitness stats. Users sign in, record activity, and calculate their BMI, with everything saved to Firebase.")
                .githubUrl("https://github.com/ansh5025")
                .liveUrl("")
                .displayOrder(2)
                .techStack(List.of("HTML", "CSS", "JavaScript", "Firebase"))
                .bullets(List.of(
                        "Set up login with Firebase Authentication and stored user data in Firestore.",
                        "Added BMI calculation and separate sections for logging diet and workouts.",
                        "Validated form input on the client side and kept users signed in across sessions.",
                        "Tested the main flows end to end — signing in, saving data, and restoring a session."
                ))
                .build();

        Project stego = Project.builder()
                .name("Image Steganography Web App")
                .description("A web app that hides text inside an image and reads it back out, using LSB (least significant bit) steganography.")
                .githubUrl("https://github.com/ansh5025")
                .liveUrl("")
                .displayOrder(3)
                .techStack(List.of("HTML", "CSS", "JavaScript", "Firebase"))
                .bullets(List.of(
                        "Wrote the encode/decode logic — hiding message bits in the least significant bits of the image pixels and pulling them back out.",
                        "Checked each upload for file type, size, and message length before processing.",
                        "Stored the processed images in Firebase.",
                        "Handled invalid input and error cases across the flow."
                ))
                .build();

        Project botnet = Project.builder()
                .name("Botnet Detection System")
                .description("A machine learning project that labels network traffic as normal or malicious based on packet metadata.")
                .githubUrl("https://github.com/ansh5025")
                .liveUrl("")
                .displayOrder(4)
                .techStack(List.of("Python", "Scikit-learn", "Pandas", "NumPy"))
                .bullets(List.of(
                        "Cleaned and prepared features from raw packet data using Pandas and NumPy.",
                        "Trained and compared a few classification models with scikit-learn.",
                        "Measured accuracy on normal vs. malicious traffic.",
                        "Ran the pipeline on more than one dataset to check the results held up."
                ))
                .build();

        projectRepository.saveAll(List.of(ecommerce, fitness, stego, botnet));
    }

    private void seedEducation() {
        Education galgotias = Education.builder()
                .institution("Galgotias University")
                .degree("B.Tech \u2013 Computer Science and Engineering")
                .location("Greater Noida, Uttar Pradesh")
                .period("2021 \u2013 2025")
                .score("CGPA: 8.01 / 10")
                .displayOrder(1)
                .build();
        educationRepository.save(galgotias);
    }
}
