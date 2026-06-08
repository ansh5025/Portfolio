import type {
  Education,
  Experience,
  Profile,
  Project,
  SkillsByCategory,
} from '../types';

export const fallbackProfile: Profile = {
  id: 0,
  name: 'Ansh Rai',
  title: 'Java Backend Software Engineer',
  tagline: 'Building reliable, scalable backend systems with Java & Spring Boot',
  summary:
    'Software engineer with a strong foundation in Java, OOP, and SDLC principles. ' +
    'Hands-on with backend development, REST APIs, SQL, and Java-based automation frameworks. ' +
    'Currently a System Engineer Trainee at Infosys, focused on Java engineering and Agile delivery. ' +
    'Passionate about backend architecture, distributed systems, clean code, and continuous learning.',
  email: 'anshrai922@gmail.com',
  phone: '',
  location: 'Greater Noida, Uttar Pradesh, India',
  linkedinUrl: 'https://www.linkedin.com/in/ansh-rai-832117218/',
  githubUrl: 'https://github.com/ansh5025',
  leetcodeUrl: 'https://leetcode.com/ansh5025',
  codolioUrl: 'https://codolio.com/profile/RetWCBRJ',
  resumeUrl: '/resume.pdf',
  avatarUrl: '',
};

export const fallbackSkills: SkillsByCategory = {
  Languages: ['Java', 'Python', 'SQL', 'JavaScript', 'Bash'],
  Backend: [
    'Spring Boot',
    'Spring MVC',
    'REST APIs',
    'JPA / Hibernate',
    'Maven',
    'Microservices (basics)',
  ],
  Databases: ['MySQL', 'H2', 'Firebase'],
  Testing: ['JUnit', 'TestNG', 'Selenium', 'Postman'],
  Tools: ['Git', 'GitHub', 'IntelliJ IDEA', 'VS Code', 'Jira'],
  Frontend: ['HTML', 'CSS', 'React (basics)'],
  Concepts: [
    'OOP',
    'Data Structures & Algorithms',
    'SDLC / Agile',
    'System Design (basics)',
  ],
};

export const fallbackExperience: Experience[] = [
  {
    id: 1,
    company: 'Infosys',
    role: 'System Engineer Trainee',
    location: 'Mysore, India',
    period: '2025 \u2013 Present',
    current: true,
    displayOrder: 1,
    bullets: [
      'Training in Java backend engineering and automation under Agile SDLC.',
      'Building and maintaining Java-based automation frameworks using Selenium, TestNG, and JUnit.',
      'Designing and validating REST API workflows using Postman and JSON request/response payloads.',
      'Applying OOP principles, exception handling, and modular design across Java projects.',
      'Writing SQL queries for data setup, validation, and database verification.',
      'Collaborating in Agile ceremonies \u2014 sprint planning, daily stand-ups, and retrospectives.',
    ],
  },
];

export const fallbackProjects: Project[] = [
  {
    id: 1,
    name: 'Fitness Tracker Web App',
    description:
      'Full-stack fitness tracker with authentication, BMI calculation, and workout/diet modules backed by Firebase.',
    githubUrl: 'https://github.com/ansh5025',
    liveUrl: '',
    displayOrder: 1,
    techStack: ['HTML', 'CSS', 'JavaScript', 'Firebase'],
    bullets: [
      'Designed modular components for authentication, BMI calculation, diet, and workout tracking.',
      'Implemented client-side validation and integrated Firebase Auth + Firestore for persistence.',
      'Built session management flow with secure data storage for user activity tracking.',
      'Verified end-to-end workflows including auth, DB writes, and session restoration.',
    ],
  },
  {
    id: 2,
    name: 'Image Steganography Web App',
    description:
      'Web app that embeds and extracts hidden text inside image files using LSB-based steganography.',
    githubUrl: 'https://github.com/ansh5025',
    liveUrl: '',
    displayOrder: 2,
    techStack: ['HTML', 'CSS', 'JavaScript', 'Firebase'],
    bullets: [
      'Implemented encryption and decryption flows for embedding messages inside image files.',
      'Built file upload pipeline with validation for file type, size, and message length.',
      'Integrated Firebase storage for processed media artifacts and retrieval flows.',
      'Added robust error handling and negative-case coverage across the workflow.',
    ],
  },
  {
    id: 3,
    name: 'Botnet Detection System',
    description:
      'Machine-learning pipeline that classifies network traffic as benign or malicious from packet metadata.',
    githubUrl: 'https://github.com/ansh5025',
    liveUrl: '',
    displayOrder: 3,
    techStack: ['Python', 'Scikit-learn', 'Pandas', 'NumPy'],
    bullets: [
      'Performed feature engineering on raw network packet metadata.',
      'Trained and evaluated classification models for detecting botnet traffic.',
      'Compared model accuracy across normal vs. malicious traffic samples.',
      'Validated the end-to-end detection workflow with multiple datasets.',
    ],
  },
];

export const fallbackEducation: Education[] = [
  {
    id: 1,
    institution: 'Galgotias University',
    degree: 'B.Tech \u2013 Computer Science and Engineering',
    location: 'Greater Noida, Uttar Pradesh',
    period: '2021 \u2013 2025',
    score: 'CGPA: 8.01 / 10',
    displayOrder: 1,
  },
];
