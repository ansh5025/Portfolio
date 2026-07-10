import { FaGithub, FaExternalLinkAlt } from 'react-icons/fa';
import type { Project } from '../types';

interface Props {
  projects: Project[];
}

export default function Projects({ projects }: Props) {
  return (
    <section id="projects" className="section">
      <div className="container">
        <div className="section__head">
          <h2 className="section__title">Projects</h2>
          <p className="section__sub">
            A few things I've built — a couple of web apps and a machine learning project.
          </p>
        </div>

        <div className="projects__grid">
          {projects.map((p) => (
            <article className="project" key={p.id}>
              <header className="project__head">
                <h3 className="project__name">{p.name}</h3>
                <div className="project__links">
                  {p.githubUrl && (
                    <a className="project__link" href={p.githubUrl} target="_blank" rel="noreferrer" aria-label="GitHub repo">
                      <FaGithub />
                    </a>
                  )}
                  {p.liveUrl && (
                    <a className="project__link" href={p.liveUrl} target="_blank" rel="noreferrer" aria-label="Live site">
                      <FaExternalLinkAlt />
                    </a>
                  )}
                </div>
              </header>
              <p className="project__desc">{p.description}</p>
              <ul className="project__bullets">
                {p.bullets.map((b, i) => (
                  <li key={i}>{b}</li>
                ))}
              </ul>
              <div className="project__tech chips">
                {p.techStack.map((t) => (
                  <span key={t} className="chip">{t}</span>
                ))}
              </div>
            </article>
          ))}
        </div>
      </div>
    </section>
  );
}
