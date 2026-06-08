import type { Experience } from '../types';

interface Props {
  experiences: Experience[];
}

export default function ExperienceSection({ experiences }: Props) {
  return (
    <section id="experience" className="section">
      <div className="container">
        <div className="section__head">
          <span className="section__eyebrow">// experience</span>
          <h2 className="section__title">Where I've worked</h2>
        </div>

        <div className="timeline">
          {experiences.map((exp) => (
            <article className="exp" key={exp.id}>
              <header className="exp__head">
                <h3 className="exp__role">
                  {exp.role} <span className="exp__company">· {exp.company}</span>
                  {exp.current && <span className="badge-current">Current</span>}
                </h3>
                <span className="exp__meta">{exp.period} · {exp.location}</span>
              </header>
              <ul className="exp__bullets">
                {exp.bullets.map((b, i) => (
                  <li key={i}>{b}</li>
                ))}
              </ul>
            </article>
          ))}
        </div>
      </div>
    </section>
  );
}
