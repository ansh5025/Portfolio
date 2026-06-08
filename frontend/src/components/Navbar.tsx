import { useState } from 'react';
import type { Profile } from '../types';

const NAV_LINKS = [
  { href: '#about', label: 'About' },
  { href: '#skills', label: 'Skills' },
  { href: '#experience', label: 'Experience' },
  { href: '#projects', label: 'Projects' },
  { href: '#education', label: 'Education' },
];

interface Props {
  profile: Profile;
}

export default function Navbar({ profile }: Props) {
  const [open, setOpen] = useState(false);
  const close = () => setOpen(false);

  return (
    <header className="navbar">
      <div className="container navbar__inner">
        <a href="#top" className="navbar__brand" onClick={close}>
          <span className="navbar__logo">AR</span>
          <span>{profile.name.replace(' ', '.')}</span>
        </a>

        <nav className={`navbar__links ${open ? 'open' : ''}`} aria-label="Primary">
          {NAV_LINKS.map((l) => (
            <a key={l.href} href={l.href} className="navbar__link" onClick={close}>
              {l.label}
            </a>
          ))}
          <a href="#contact" className="navbar__cta" onClick={close}>
            Contact
          </a>
        </nav>

        <button
          className="navbar__toggle"
          aria-label="Toggle navigation"
          aria-expanded={open}
          onClick={() => setOpen((v) => !v)}
        >
          <svg width="22" height="22" viewBox="0 0 24 24" fill="none" stroke="currentColor"
               strokeWidth="2" strokeLinecap="round">
            {open ? (
              <>
                <line x1="18" y1="6" x2="6" y2="18" />
                <line x1="6" y1="6" x2="18" y2="18" />
              </>
            ) : (
              <>
                <line x1="4" y1="7" x2="20" y2="7" />
                <line x1="4" y1="12" x2="20" y2="12" />
                <line x1="4" y1="17" x2="20" y2="17" />
              </>
            )}
          </svg>
        </button>
      </div>
    </header>
  );
}
