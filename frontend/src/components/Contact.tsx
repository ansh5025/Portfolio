import { useState } from 'react';
import { FaEnvelope, FaMapMarkerAlt, FaPaperPlane, FaGithub, FaLinkedin } from 'react-icons/fa';
import { api } from '../services/api';
import type { ContactRequest, Profile } from '../types';

interface Props {
  profile: Profile;
}

type Status =
  | { kind: 'idle' }
  | { kind: 'sending' }
  | { kind: 'ok'; msg: string }
  | { kind: 'err'; msg: string };

const EMPTY: ContactRequest = { name: '', email: '', subject: '', message: '' };

export default function Contact({ profile }: Props) {
  const [form, setForm] = useState<ContactRequest>(EMPTY);
  const [status, setStatus] = useState<Status>({ kind: 'idle' });

  const update = (k: keyof ContactRequest) =>
    (e: React.ChangeEvent<HTMLInputElement | HTMLTextAreaElement>) =>
      setForm((f) => ({ ...f, [k]: e.target.value }));

  const onSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    setStatus({ kind: 'sending' });
    try {
      await api.submitContact(form);
      setStatus({ kind: 'ok', msg: "Thanks! I'll get back to you soon." });
      setForm(EMPTY);
    } catch (err) {
      const msg = err instanceof Error ? err.message : 'Something went wrong.';
      setStatus({ kind: 'err', msg });
    }
  };

  return (
    <section id="contact" className="section">
      <div className="container">
        <div className="section__head">
          <h2 className="section__title">Let's build something</h2>
          <p className="section__sub">
            Got a backend role, a project, or just want to chat? Drop a message — I'll reply.
          </p>
        </div>

        <div className="contact__grid">
          <aside className="contact__info">
            <h3>Reach me</h3>
            <p>I'm open to backend / full-stack roles, freelance work, and interesting collabs.</p>

            <div className="contact__item">
              <span className="ico"><FaEnvelope /></span>
              <a href={`mailto:${profile.email}`}>{profile.email}</a>
            </div>
            <div className="contact__item">
              <span className="ico"><FaMapMarkerAlt /></span>
              <span>{profile.location}</span>
            </div>
            {profile.githubUrl && (
              <div className="contact__item">
                <span className="ico"><FaGithub /></span>
                <a href={profile.githubUrl} target="_blank" rel="noreferrer">github.com/ansh5025</a>
              </div>
            )}
            {profile.linkedinUrl && (
              <div className="contact__item">
                <span className="ico"><FaLinkedin /></span>
                <a href={profile.linkedinUrl} target="_blank" rel="noreferrer">linkedin.com/in/ansh-rai</a>
              </div>
            )}
          </aside>

          <form className="contact__form" onSubmit={onSubmit} noValidate>
            <div className="form-row">
              <div className="field">
                <label htmlFor="name">Name</label>
                <input
                  id="name"
                  className="input"
                  required
                  maxLength={120}
                  value={form.name}
                  onChange={update('name')}
                />
              </div>
              <div className="field">
                <label htmlFor="email">Email</label>
                <input
                  id="email"
                  className="input"
                  type="email"
                  required
                  maxLength={200}
                  value={form.email}
                  onChange={update('email')}
                />
              </div>
            </div>

            <div className="field">
              <label htmlFor="subject">Subject</label>
              <input
                id="subject"
                className="input"
                maxLength={200}
                value={form.subject}
                onChange={update('subject')}
              />
            </div>

            <div className="field">
              <label htmlFor="message">Message</label>
              <textarea
                id="message"
                className="textarea"
                required
                maxLength={4000}
                value={form.message}
                onChange={update('message')}
              />
            </div>

            {status.kind === 'ok' && (
              <div className="form-msg form-msg--ok">{status.msg}</div>
            )}
            {status.kind === 'err' && (
              <div className="form-msg form-msg--err">{status.msg}</div>
            )}

            <button
              type="submit"
              className="btn btn--primary"
              disabled={status.kind === 'sending'}
              style={{ alignSelf: 'flex-start' }}
            >
              <FaPaperPlane />
              {status.kind === 'sending' ? 'Sending…' : 'Send message'}
            </button>
          </form>
        </div>
      </div>
    </section>
  );
}
