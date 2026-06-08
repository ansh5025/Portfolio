export interface Profile {
  id: number;
  name: string;
  title: string;
  tagline: string;
  summary: string;
  email: string;
  phone: string;
  location: string;
  linkedinUrl: string;
  githubUrl: string;
  leetcodeUrl: string;
  gfgUrl: string;
  resumeUrl: string;
  avatarUrl: string;
}

export interface Experience {
  id: number;
  company: string;
  role: string;
  location: string;
  period: string;
  current: boolean;
  displayOrder: number;
  bullets: string[];
}

export interface Project {
  id: number;
  name: string;
  description: string;
  githubUrl: string;
  liveUrl: string;
  displayOrder: number;
  techStack: string[];
  bullets: string[];
}

export interface Education {
  id: number;
  institution: string;
  degree: string;
  location: string;
  period: string;
  score: string;
  displayOrder: number;
}

export type SkillsByCategory = Record<string, string[]>;

export interface ApiResponse<T> {
  success: boolean;
  message: string;
  data: T;
}

export interface ContactRequest {
  name: string;
  email: string;
  subject: string;
  message: string;
}
