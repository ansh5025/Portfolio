import { useEffect, useState } from 'react';
import Navbar from './components/Navbar';
import Hero from './components/Hero';
import About from './components/About';
import Skills from './components/Skills';
import Experience from './components/Experience';
import Projects from './components/Projects';
import Education from './components/Education';
import Contact from './components/Contact';
import Footer from './components/Footer';
import { api } from './services/api';
import {
  fallbackEducation,
  fallbackExperience,
  fallbackProfile,
  fallbackProjects,
  fallbackSkills,
} from './services/fallback';
import type {
  Education as EducationT,
  Experience as ExperienceT,
  Profile,
  Project,
  SkillsByCategory,
} from './types';

export default function App() {
  const [profile, setProfile] = useState<Profile>(fallbackProfile);
  const [skills, setSkills] = useState<SkillsByCategory>(fallbackSkills);
  const [experience, setExperience] = useState<ExperienceT[]>(fallbackExperience);
  const [projects, setProjects] = useState<Project[]>(fallbackProjects);
  const [education, setEducation] = useState<EducationT[]>(fallbackEducation);
  const landingVisitKey = 'portfolio.landing-visit.logged';

  useEffect(() => {
    if (typeof window !== 'undefined' && !window.sessionStorage.getItem(landingVisitKey)) {
      window.sessionStorage.setItem(landingVisitKey, 'true');
      void api.trackLandingVisit().catch(() => {
        window.sessionStorage.removeItem(landingVisitKey);
      });
    }
  }, [landingVisitKey]);

  useEffect(() => {
    let cancelled = false;
    (async () => {
      const safeSet = <T,>(setter: (v: T) => void, value: T) => {
        if (!cancelled) setter(value);
      };
      await Promise.allSettled([
        api.getProfile().then((d) => safeSet(setProfile, d)),
        api.getSkills().then((d) => safeSet(setSkills, d)),
        api.getExperience().then((d) => safeSet(setExperience, d)),
        api.getProjects().then((d) => safeSet(setProjects, d)),
        api.getEducation().then((d) => safeSet(setEducation, d)),
      ]);
    })();
    return () => {
      cancelled = true;
    };
  }, []);

  return (
    <div className="app">
      <Navbar profile={profile} />
      <main>
        <Hero profile={profile} />
        <About profile={profile} />
        <Skills skills={skills} />
        <Experience experiences={experience} />
        <Projects projects={projects} />
        <Education educations={education} />
        <Contact profile={profile} />
      </main>
      <Footer profile={profile} />
    </div>
  );
}
