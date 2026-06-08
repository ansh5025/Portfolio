import { FaGithub, FaLinkedin } from 'react-icons/fa';
import { SiLeetcode } from 'react-icons/si';
import type { Profile } from '../types';

interface Props {
  profile: Profile;
}

export default function Footer({ profile }: Props) {
  const year = new Date().getFullYear();
  return (
    <footer className="footer">
      <div className="container footer__inner">
        <span>© {year} {profile.name}. Built with React + Spring Boot.</span>
        <div className="footer__links">
          {profile.githubUrl && (
            <a href={profile.githubUrl} target="_blank" rel="noreferrer" aria-label="GitHub"><FaGithub /></a>
          )}
          {profile.linkedinUrl && (
            <a href={profile.linkedinUrl} target="_blank" rel="noreferrer" aria-label="LinkedIn"><FaLinkedin /></a>
          )}
          {profile.leetcodeUrl && (
            <a href={profile.leetcodeUrl} target="_blank" rel="noreferrer" aria-label="LeetCode"><SiLeetcode /></a>
          )}
        </div>
      </div>
    </footer>
  );
}
