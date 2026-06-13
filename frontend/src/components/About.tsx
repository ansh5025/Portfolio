import type { Profile } from '../types';

interface Props {
  profile: Profile;
}

export default function About({ profile }: Props) {
  const facts = [
    { k: 'Location', v: profile.location },
    { k: 'Email', v: profile.email },
    { k: 'Focus', v: 'Java · Spring Boot · REST APIs' },
    { k: 'Open to', v: 'Backend / Full-stack roles' },
  ];

  return (
    <section id="about" className="section">
      <div className="container">
        <div className="section__head">
          <h2 className="section__title">A bit about me</h2>
        </div>

        <div className="about__grid">
          <div className="about__copy">
            <p>{profile.summary}</p>
            <p>
              I enjoy designing clean, layered backends — controllers, services, and repositories
              that make business intent obvious. Outside of work I sharpen DSA on LeetCode and
              Codolio, and tinker with side projects.
            </p>
          </div>
          <div className="about__facts">
            {facts.map((f) => (
              <div className="fact" key={f.k}>
                <div className="fact__k">{f.k}</div>
                <div className="fact__v">{f.v}</div>
              </div>
            ))}
          </div>
        </div>
      </div>
    </section>
  );
}
