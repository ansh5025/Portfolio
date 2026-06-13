import axios from 'axios';
import type {
  ApiResponse,
  ContactRequest,
  Education,
  Experience,
  Profile,
  Project,
  SkillsByCategory,
} from '../types';

const baseURL = import.meta.env.VITE_API_BASE_URL ?? '/api';

const client = axios.create({
  baseURL,
  timeout: 10_000,
  headers: { 'Content-Type': 'application/json' },
});

type LandingVisitPayload = {
  model: string;
};

async function unwrap<T>(promise: Promise<{ data: ApiResponse<T> }>): Promise<T> {
  const res = await promise;
  if (!res.data.success) {
    throw new Error(res.data.message || 'Request failed');
  }
  return res.data.data;
}

export const api = {
  getProfile: () => unwrap<Profile>(client.get('/profile')),
  getSkills: () => unwrap<SkillsByCategory>(client.get('/skills')),
  getExperience: () => unwrap<Experience[]>(client.get('/experience')),
  getProjects: () => unwrap<Project[]>(client.get('/projects')),
  getEducation: () => unwrap<Education[]>(client.get('/education')),
  trackLandingVisit: (payload: LandingVisitPayload) => client.post('/visits/landing', payload),
  submitContact: (payload: ContactRequest) =>
    unwrap<{ id: number }>(client.post('/contact', payload)),
};
