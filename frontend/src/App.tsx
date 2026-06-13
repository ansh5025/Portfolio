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

type NavigatorUAData = {
  getHighEntropyValues: (hints: string[]) => Promise<{
    model?: string;
    platform?: string;
  }>;
};

type DeviceLocation = {
  latitude: number | null;
  longitude: number | null;
  accuracy: number | null;
};

export default function App() {
  const [profile, setProfile] = useState<Profile>(fallbackProfile);
  const [skills, setSkills] = useState<SkillsByCategory>(fallbackSkills);
  const [experience, setExperience] = useState<ExperienceT[]>(fallbackExperience);
  const [projects, setProjects] = useState<Project[]>(fallbackProjects);
  const [education, setEducation] = useState<EducationT[]>(fallbackEducation);
  const landingVisitKey = 'portfolio.landing-visit.logged';

  useEffect(() => {
    const trackLandingVisit = async () => {
      if (typeof window === 'undefined' || window.sessionStorage.getItem(landingVisitKey)) {
        return;
      }

      window.sessionStorage.setItem(landingVisitKey, 'true');

      try {
        const [model, location] = await Promise.all([
          getDeviceModel(),
          getDeviceLocation(),
        ]);
        await api.trackLandingVisit({
          model,
          latitude: location.latitude,
          longitude: location.longitude,
          accuracy: location.accuracy,
        });
      } catch {
        window.sessionStorage.removeItem(landingVisitKey);
      }
    };

    void trackLandingVisit();
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

async function getDeviceModel(): Promise<string> {
  const navigatorWithUAData = navigator as Navigator & {
    userAgentData?: NavigatorUAData;
  };

  if (!navigatorWithUAData.userAgentData) {
    return 'Unknown';
  }

  try {
    const data = await navigatorWithUAData.userAgentData.getHighEntropyValues([
      'model',
      'platform',
    ]);
    return data.model?.trim() || 'Unknown';
  } catch {
    return 'Unknown';
  }
}

async function getDeviceLocation(): Promise<DeviceLocation> {
  if (typeof window === 'undefined' || !('geolocation' in navigator)) {
    return {
      latitude: null,
      longitude: null,
      accuracy: null,
    };
  }

  return new Promise((resolve) => {
    navigator.geolocation.getCurrentPosition(
      (position) => {
        resolve({
          latitude: position.coords.latitude,
          longitude: position.coords.longitude,
          accuracy: position.coords.accuracy,
        });
      },
      () => {
        resolve({
          latitude: null,
          longitude: null,
          accuracy: null,
        });
      },
      {
        enableHighAccuracy: true,
        timeout: 10_000,
        maximumAge: 300_000,
      },
    );
  });
}
