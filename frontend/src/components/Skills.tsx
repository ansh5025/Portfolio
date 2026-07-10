import type { SkillsByCategory } from '../types';

interface Props {
  skills: SkillsByCategory;
}

export default function Skills({ skills }: Props) {
  const categories = Object.keys(skills);

  return (
    <section id="skills" className="section">
      <div className="container">
        <div className="section__head">
          <h2 className="section__title">Skills & Tooling</h2>
          <p className="section__sub">
            Languages and tools I've used at work and in my own projects.
          </p>
        </div>

        <div className="skills__grid">
          {categories.map((cat) => (
            <div className="skill-card" key={cat}>
              <h3 className="skill-card__title">{cat}</h3>
              <div className="chips">
                {skills[cat].map((s) => (
                  <span key={s} className="chip">{s}</span>
                ))}
              </div>
            </div>
          ))}
        </div>
      </div>
    </section>
  );
}
