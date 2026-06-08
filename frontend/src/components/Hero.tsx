import { FaGithub, FaLinkedin, FaCode, FaFileDownload } from 'react-icons/fa';
import { SiLeetcode } from 'react-icons/si';
import type { Profile } from '../types';

interface Props {
  profile: Profile;
}

export default function Hero({ profile }: Props) {
  const firstName = profile.name.split(' ')[0];
  return (
    <section id="top" className="hero">
      <div className="container hero__grid">
        <div>
          <span className="hero__eyebrow">
            <span className="dot" />
            Available for backend engineering roles
          </span>
          <h1 className="hero__title">
            Hi, I'm <span className="grad">{firstName}</span>.<br />
            {profile.title}.
          </h1>
          <p className="hero__subtitle">{profile.tagline}</p>

          <div className="hero__cta">
            <a className="btn btn--primary" href="#contact">
              <FaCode /> Let's work together
            </a>
            <a className="btn btn--ghost" href={profile.resumeUrl} target="_blank" rel="noreferrer">
              <FaFileDownload /> Download Resume
            </a>
          </div>

          <div className="hero__socials">
            {profile.githubUrl && (
              <a className="icon-btn" href={profile.githubUrl} target="_blank" rel="noreferrer" aria-label="GitHub">
                <FaGithub />
              </a>
            )}
            {profile.linkedinUrl && (
              <a className="icon-btn" href={profile.linkedinUrl} target="_blank" rel="noreferrer" aria-label="LinkedIn">
                <FaLinkedin />
              </a>
            )}
            {profile.leetcodeUrl && (
              <a className="icon-btn" href={profile.leetcodeUrl} target="_blank" rel="noreferrer" aria-label="LeetCode">
                <SiLeetcode />
              </a>
            )}
            {profile.codolioUrl && (
              <a className="icon-btn" href={profile.codolioUrl} target="_blank" rel="noreferrer" aria-label="Codolio">
                <img alt="Codolio" width="24" height="24" src="/codolio_assets/codolio.svg" />
              </a>
            )}
          </div>
        </div>
      </div>
    </section>
  );
}
