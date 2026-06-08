import { FaGraduationCap } from 'react-icons/fa';
import type { Education } from '../types';

interface Props {
  educations: Education[];
}

export default function EducationSection({ educations }: Props) {
  return (
    <section id="education" className="section">
      <div className="container">
        <div className="section__head">
          <span className="section__eyebrow">// education</span>
          <h2 className="section__title">Education</h2>
        </div>

        <div className="education__grid">
          {educations.map((e) => (
            <article className="edu" key={e.id}>
              <div className="edu__icon"><FaGraduationCap /></div>
              <div>
                <h3 className="edu__degree">{e.degree}</h3>
                <div className="edu__inst">{e.institution}</div>
                <div className="edu__meta">{e.period} · {e.location}</div>
              </div>
              {e.score && <div className="edu__score">{e.score}</div>}
            </article>
          ))}
        </div>
      </div>
    </section>
  );
}
