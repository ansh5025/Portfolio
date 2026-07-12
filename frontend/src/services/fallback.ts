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
  title: 'Java Backend Engineer',
  tagline: 'I work on Java and Spring Boot backends. Currently a System Engineer at Infosys.',
  summary:
    'I’m a software engineer who works mostly with Java and Spring Boot. ' +
    'I finished my B.Tech in Computer Science in 2025 and joined Infosys as a System Engineer, ' +
    'where I work on Java and Spring Boot backends, REST APIs, and SQL. ' +
    'Most of what I know outside of work came from building side projects and practicing DSA on LeetCode. ' +
    'Right now I’m focused on getting better at backend development and the basics of system design.',
  email: 'anshrai922@gmail.com',
  phone: '',
  location: 'Pune, Maharashtra, India',
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
    role: 'System Engineer',
    location: 'Pune, India',
    period: '2025 \u2013 Present',
    current: true,
    displayOrder: 1,
    bullets: [
      'Work on Java backend development with Spring Boot on the team\u2019s projects.',
      'Build REST APIs with Spring MVC and test them in Postman against the expected request and response payloads.',
      'Use Spring Data JPA and Hibernate to map entities and query the database.',
      'Write SQL to set up data and check results, and cover the code with JUnit tests.',
      'Work in an Agile team \u2014 sprint planning, daily stand-ups, and retrospectives.',
    ],
  },
];

export const fallbackProjects: Project[] = [
  {
    id: 4,
    name: 'E-Commerce Platform',
    description:
      'An online store with product browsing, a cart, and an admin panel behind it. Started as an older-style Spring Boot app and rebuilt into a proper backend and frontend, then deployed live on my own domain.',
    githubUrl: 'https://github.com/ansh5025/ecommerce-project',
    liveUrl: 'https://ecommerce.anshrai.in',
    displayOrder: 1,
    techStack: ['Java', 'Spring Boot', 'Spring Security', 'Hibernate', 'MySQL', 'React', 'Tailwind CSS', 'Azure'],
    bullets: [
      'Rebuilt the old app as a REST API with its own React frontend, with separate logins for admins and customers.',
      'Added product, category, and cart management, plus an admin panel to manage all of it.',
      'Deployed everything to Azure with its own database, a custom domain, and HTTPS.',
      'Set up GitHub Actions so pushing to main builds and deploys both sides automatically.',
      'Found and fixed a tricky login bug that only showed up once the frontend and backend were split onto separate domains.',
    ],
  },
  {
    id: 1,
    name: 'Fitness Tracker Web App',
    description:
      'A web app for logging workouts and diet and tracking basic fitness stats. Users sign in, record activity, and calculate their BMI, with everything saved to Firebase.',
    githubUrl: 'https://github.com/ansh5025',
    liveUrl: '',
    displayOrder: 2,
    techStack: ['HTML', 'CSS', 'JavaScript', 'Firebase'],
    bullets: [
      'Set up login with Firebase Authentication and stored user data in Firestore.',
      'Added BMI calculation and separate sections for logging diet and workouts.',
      'Validated form input on the client side and kept users signed in across sessions.',
      'Tested the main flows end to end — signing in, saving data, and restoring a session.',
    ],
  },
  {
    id: 2,
    name: 'Image Steganography Web App',
    description:
      'A web app that hides text inside an image and reads it back out, using LSB (least significant bit) steganography.',
    githubUrl: 'https://github.com/ansh5025',
    liveUrl: '',
    displayOrder: 3,
    techStack: ['HTML', 'CSS', 'JavaScript', 'Firebase'],
    bullets: [
      'Wrote the encode/decode logic — hiding message bits in the least significant bits of the image pixels and pulling them back out.',
      'Checked each upload for file type, size, and message length before processing.',
      'Stored the processed images in Firebase.',
      'Handled invalid input and error cases across the flow.',
    ],
  },
  {
    id: 3,
    name: 'Botnet Detection System',
    description:
      'A machine learning project that labels network traffic as normal or malicious based on packet metadata.',
    githubUrl: 'https://github.com/ansh5025',
    liveUrl: '',
    displayOrder: 4,
    techStack: ['Python', 'Scikit-learn', 'Pandas', 'NumPy'],
    bullets: [
      'Cleaned and prepared features from raw packet data using Pandas and NumPy.',
      'Trained and compared a few classification models with scikit-learn.',
      'Measured accuracy on normal vs. malicious traffic.',
      'Ran the pipeline on more than one dataset to check the results held up.',
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
