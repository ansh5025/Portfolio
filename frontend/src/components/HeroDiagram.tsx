interface Node {
  id: string;
  cx: number;
  cy: number;
  label: string;
  tag: string;
  dot: string;
}

const NODES: Node[] = [
  { id: 'client', cx: 220, cy: 50, label: 'Client', tag: 'GET /api', dot: 'var(--accent-2)' },
  { id: 'api', cx: 220, cy: 170, label: 'REST API', tag: 'Spring MVC', dot: 'var(--accent)' },
  { id: 'svc', cx: 220, cy: 290, label: 'Service Layer', tag: '@Service', dot: 'var(--accent-3)' },
  { id: 'db', cx: 110, cy: 412, label: 'Database', tag: 'JPA · MySQL', dot: 'var(--success)' },
  { id: 'cache', cx: 330, cy: 412, label: 'Cache', tag: 'in-memory', dot: 'var(--accent-2)' },
];

const LINKS = [
  { id: 'l1', d: 'M220,78 L220,142' },
  { id: 'l2', d: 'M220,198 L220,262' },
  { id: 'l3', d: 'M220,318 C220,365 110,362 110,384' },
  { id: 'l4', d: 'M220,318 C220,365 330,362 330,384' },
];

const W = 150;
const H = 56;

/**
 * A live "backend system" diagram — client → REST API → service → data stores,
 * with data flowing along the connections. Pure SVG + CSS/SMIL, no JS libraries.
 */
export default function HeroDiagram() {
  const reduce =
    typeof window !== 'undefined' &&
    window.matchMedia?.('(prefers-reduced-motion: reduce)').matches;

  return (
    <div className="hero__diagram">
      <svg viewBox="0 0 440 470" className="hero__svg" role="img" aria-label="Backend system architecture: client to REST API to service layer to database and cache">
        {/* Connections */}
        {LINKS.map((l) => (
          <g key={l.id}>
            <path id={l.id} d={l.d} className="hero__link" fill="none" />
            <path d={l.d} className="hero__link-flow" fill="none" />
            {!reduce && (
              <circle r="3.5" className="hero__packet">
                <animateMotion dur="2.4s" repeatCount="indefinite" keyPoints="0;1" keyTimes="0;1" calcMode="linear">
                  <mpath href={`#${l.id}`} />
                </animateMotion>
              </circle>
            )}
          </g>
        ))}

        {/* Nodes */}
        {NODES.map((n) => {
          const x = n.cx - W / 2;
          const y = n.cy - H / 2;
          return (
            <g key={n.id} className="hero__node-g">
              <rect x={x} y={y} width={W} height={H} rx="14" className="hero__node" />
              <circle cx={x + 20} cy={n.cy - 7} r="4" fill={n.dot} />
              {!reduce && (
                <circle cx={x + 20} cy={n.cy - 7} r="4" fill="none" stroke={n.dot} opacity="0.5">
                  <animate attributeName="r" values="4;9" dur="2.2s" repeatCount="indefinite" />
                  <animate attributeName="opacity" values="0.5;0" dur="2.2s" repeatCount="indefinite" />
                </circle>
              )}
              <text x={x + 34} y={n.cy - 2} className="hero__node-label">{n.label}</text>
              <text x={x + 20} y={n.cy + 16} className="hero__node-tag">{n.tag}</text>
            </g>
          );
        })}
      </svg>
    </div>
  );
}
