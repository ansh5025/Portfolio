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
                .tagline("Building reliable, scalable backend systems with Java & Spring Boot")
                .summary("Software engineer with a strong foundation in Java, OOP, and SDLC principles. "
                        + "Hands-on with backend development, REST APIs, SQL, and Java-based automation frameworks. "
                        + "Currently a System Engineer at Infosys, focused on Java engineering and Agile delivery. "
                        + "Passionate about backend architecture, distributed systems, clean code, and continuous learning.")
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
                        "Training in Java backend engineering and automation under Agile SDLC.",
                        "Building and maintaining Java-based automation frameworks using Selenium, TestNG, and JUnit.",
                        "Designing and validating REST API workflows using Postman and JSON request/response payloads.",
                        "Applying OOP principles, exception handling, and modular design across Java projects.",
                        "Writing SQL queries for data setup, validation, and database verification.",
                        "Collaborating in Agile ceremonies \u2014 sprint planning, daily stand-ups, and retrospectives."
                ))
                .build();
        experienceRepository.save(infosys);
    }

    private void seedProjects() {
        Project fitness = Project.builder()
                .name("Fitness Tracker Web App")
                .description("Full-stack fitness tracker with authentication, BMI calculation, and workout/diet modules backed by Firebase.")
                .githubUrl("https://github.com/ansh5025")
                .liveUrl("")
                .displayOrder(1)
                .techStack(List.of("HTML", "CSS", "JavaScript", "Firebase"))
                .bullets(List.of(
                        "Designed modular components for authentication, BMI calculation, diet, and workout tracking.",
                        "Implemented client-side validation and integrated Firebase Auth + Firestore for persistence.",
                        "Built session management flow with secure data storage for user activity tracking.",
                        "Verified end-to-end workflows including auth, DB writes, and session restoration."
                ))
                .build();

        Project stego = Project.builder()
                .name("Image Steganography Web App")
                .description("Web app that embeds and extracts hidden text inside image files using LSB-based steganography.")
                .githubUrl("https://github.com/ansh5025")
                .liveUrl("")
                .displayOrder(2)
                .techStack(List.of("HTML", "CSS", "JavaScript", "Firebase"))
                .bullets(List.of(
                        "Implemented encryption and decryption flows for embedding messages inside image files.",
                        "Built file upload pipeline with validation for file type, size, and message length.",
                        "Integrated Firebase storage for processed media artifacts and retrieval flows.",
                        "Added robust error handling and negative-case coverage across the workflow."
                ))
                .build();

        Project botnet = Project.builder()
                .name("Botnet Detection System")
                .description("Machine-learning pipeline that classifies network traffic as benign or malicious from packet metadata.")
                .githubUrl("https://github.com/ansh5025")
                .liveUrl("")
                .displayOrder(3)
                .techStack(List.of("Python", "Scikit-learn", "Pandas", "NumPy"))
                .bullets(List.of(
                        "Performed feature engineering on raw network packet metadata.",
                        "Trained and evaluated classification models for detecting botnet traffic.",
                        "Compared model accuracy across normal vs. malicious traffic samples.",
                        "Validated the end-to-end detection workflow with multiple datasets."
                ))
                .build();

        projectRepository.saveAll(List.of(fitness, stego, botnet));
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
