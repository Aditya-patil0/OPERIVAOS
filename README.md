<div align="center">

<img src="https://capsule-render.vercel.app/api?type=soft&amp;color=0:0F172A,50:1E3A8A,100:3457D5&amp;height=200&amp;section=header&amp;text=OPERIVA%20OS&amp;fontSize=56&amp;fontColor=ffffff&amp;fontAlignY=42&amp;desc=Enterprise%20Operations%20and%20Process%20Platform&amp;descAlignY=62&amp;descSize=18&amp;descColor=D6E0FF" width="100%"/>

<img src="https://readme-typing-svg.demolab.com?font=Fira+Code&amp;weight=600&amp;size=20&amp;duration=3000&amp;pause=1000&amp;color=3457D5&amp;center=true&amp;vCenter=true&amp;width=650&amp;lines=Project+lifecycle+management;Multi-level+approval+chains;Project-scoped+RBAC+by+design;Permission-scoped+AI+assistant" alt="Typing SVG" />

<br/><br/>

<table>
<tr>
<td align="center" width="110">
  <img src="https://img.icons8.com/3d-fluency/94/angular.png" width="60"/><br/>
  <sub><b>Angular</b></sub>
</td>
<td align="center" width="110">
  <img src="https://img.icons8.com/3d-fluency/94/spring-logo.png" width="60"/><br/>
  <sub><b>Spring Boot</b></sub>
</td>
<td align="center" width="110">
  <img src="https://img.icons8.com/3d-fluency/94/java-coffee-cup-logo.png" width="60"/><br/>
  <sub><b>Java 21</b></sub>
</td>
<td align="center" width="110">
  <img src="https://img.icons8.com/3d-fluency/94/kafka.png" width="60"/><br/>
  <sub><b>Kafka</b></sub>
</td>
<td align="center" width="110">
  <img src="https://img.icons8.com/3d-fluency/94/postgreesql.png" width="60"/><br/>
  <sub><b>PostgreSQL</b></sub>
</td>
<td align="center" width="110">
  <img src="https://img.icons8.com/3d-fluency/94/redis.png" width="60"/><br/>
  <sub><b>Redis</b></sub>
</td>
<td align="center" width="110">
  <img src="https://img.icons8.com/3d-fluency/94/docker.png" width="60"/><br/>
  <sub><b>Docker</b></sub>
</td>
</tr>
</table>

<br/>

<table>
<tr>
<td align="center" width="230">
  <img src="https://img.icons8.com/3d-fluency/60/module.png" width="42"/><br/>
  <b>Modular Monolith</b><br/><sub>Phased, containerized build</sub>
</td>
<td align="center" width="230">
  <img src="https://img.icons8.com/3d-fluency/60/lock-2.png" width="42"/><br/>
  <b>Project-Scoped RBAC</b><br/><sub>(user, project, role) triples</sub>
</td>
<td align="center" width="230">
  <img src="https://img.icons8.com/3d-fluency/60/artificial-intelligence.png" width="42"/><br/>
  <b>Permission-Scoped RAG</b><br/><sub>Filtered before the model sees it</sub>
</td>
</tr>
<tr>
<td align="center" width="230">
  <img src="https://img.icons8.com/3d-fluency/60/synchronize.png" width="42"/><br/>
  <b>Append-Only History</b><br/><sub>Nothing is ever overwritten</sub>
</td>
<td align="center" width="230">
  <img src="https://img.icons8.com/3d-fluency/60/settings.png" width="42"/><br/>
  <b>Generic Approval Engine</b><br/><sub>New types = configuration</sub>
</td>
<td align="center" width="230">
  <img src="https://img.icons8.com/3d-fluency/60/alarm-clock.png" width="42"/><br/>
  <b>SLA Auto-Escalation</b><br/><sub>Nothing gets silently ignored</sub>
</td>
</tr>
</table>

<br/>

<p>
  <img src="https://img.shields.io/badge/status-in%20development-orange?style=flat-square"/>
  <img src="https://img.shields.io/badge/architecture-modular%20monolith-3457D5?style=flat-square"/>
  <img src="https://img.shields.io/badge/license-MIT-2E8B57?style=flat-square"/>
  <img src="https://img.shields.io/badge/MVP-10%20week%20roadmap-10192E?style=flat-square"/>
</p>

**[Overview](#-overview)** ·
**[Architecture](#-architecture)** ·
**[AI Assistant](#-ai-assistant-architecture)** ·
**[Tech Stack](#-tech-stack)** ·
**[Roadmap](#-development-roadmap)** ·
**[Run Locally](#-running-locally)**

</div>

---

## 🚀 Overview

[#-overview](#-overview)

**OPERIVA OS** replaces scattered email chains, verbal approvals, and disconnected spreadsheets with one auditable digital record of a project's entire lifecycle — intake, requirement capture, budgeting, multi-level approval, execution, and closure.

The engine is **domain-agnostic**: the same workflow core that runs a corporate project pipeline can run a law firm's matter-approval process or a government permit workflow — only the labels change.

**Built for:** 🏢 Corporates · ⚖️ Law firms · 🏛️ Government bodies · 🤝 NGOs & SMBs

---

## ✨ Features

[#-features](#-features)

- Auto-coded project containers — no manual numbering
- CEO/Board feasibility gate with auto-generated client confirmation
- Chronological, permanent meeting/progress log per project
- Requirements & budget as **versioned, append-only** revision chains — never overwritten
- Generic, configuration-driven **Approval Chain engine** (Manager → Finance → CEO/Board)
- Three decision outcomes per level: approve, reject, request clarification (with loop-back)
- **SLA-based auto-escalation** — no silently-ignored approvals
- Strict **project-scoped RBAC** — a Manager sees only their assigned projects
- Cross-department request routing (budget / asset / HR tickets)
- Embedded, **role-aware AI assistant** scoped to exactly what the asker is permitted to see
- Full **append-only audit trail** on every state transition
- Event-driven core (Kafka) — async fan-out to notifications, audit log, and AI ingestion

---

## 🏗 Architecture

[#-architecture](#-architecture)

```mermaid
flowchart TB
    UI["🖥️ Angular SPA<br/>role-based dashboards + chat widget"]
    GW["🔐 API Gateway · Auth · RBAC Resolver<br/>(user, project, role) permission context"]

    subgraph Services["Layer 3 — Application Services (Spring Boot)"]
        direction LR
        LC["Project Lifecycle Svc"]
        WF["Workflow / Approval Engine"]
        RB["Requirements & Budget Svc"]
        MT["Meeting / Progress Log Svc"]
        TK["Task Svc"]
        NT["Notification Svc"]
        AL["Audit Log Svc"]
    end

    BUS{{"🔁 Event Bus — Kafka<br/>project.state.changed · request.raised<br/>approval.granted/rejected · budget.revised · sla.breached"}}

    AI["🧠 AI Assistant Service<br/>Permission-Scoped RAG"]

    subgraph Data["Layer 6 — Data Layer"]
        direction LR
        PG[("PostgreSQL<br/>system of record")]
        RD[("Redis<br/>permission / session cache")]
        VD[("Vector DB<br/>embeddings")]
    end

    UI -->|REST| GW
    GW --> LC & WF & RB & MT & TK
    LC & WF & RB & MT & TK -->|produce| BUS
    BUS -->|consume| NT
    BUS -->|consume| AL
    BUS -->|consume| AI
    AI <-->|filtered retrieval| VD
    LC & WF & RB & MT & TK <--> PG
    GW <--> RD
    AL --> PG

    style UI fill:#3457D5,color:#fff
    style GW fill:#10192E,color:#fff
    style BUS fill:#231F20,color:#fff
    style AI fill:#6DB33F,color:#fff
```

> Synchronous calls (REST) flow top-to-bottom through the Gateway and Services. Asynchronous propagation (Kafka) fans out from the Event Bus to Notifications, Audit Log, and the AI ingestion pipeline.

### Full end-to-end workflow — intake to closure

```mermaid
flowchart TD
    A(["👤 Client Inquiry"]) --> B["Create Project Container<br/>auto-code · state = INQUIRY"]
    B --> C{"CEO Reviews<br/>Slot / Feasibility"}
    C -->|Not feasible| D["Notify Client: Not Feasible"]
    C -->|Feasible| E["Auto-generate & Send Confirmation"]
    E --> F["Meetings Logged<br/>(Progress Section)"]
    F -.->|repeats per meeting| F
    F --> G["Requirements & Budget Captured<br/>versioned · appended"]
    G --> H{"Approval Chain:<br/>CEO / Board"}
    H -->|Rejected| I(["Project Closed"])
    H -->|Clarification requested| G
    H -->|No decision in SLA| H
    H -->|Approved| J["Manager Workspace Opens<br/>Assigns Tasks"]
    J --> K["Employees Execute Tasks"]
    K --> L["Budget / Asset / HR<br/>Ticket Routed"]
    L -.->|fulfilled / response| K
    K --> M["Submit Proposal / Deliverable"]
    M --> N{"Manager Reviews<br/>Proposal"}
    N -->|Rework / clarification| M
    N -->|Approved| O["Final Budget Sanction<br/>(if required)"]
    O --> P(["Project State:<br/>IN_PROGRESS → REVIEW → COMPLETED"])

    style A fill:#3457D5,color:#fff
    style D fill:#C0392B,color:#fff
    style I fill:#C0392B,color:#fff
    style E fill:#2E8B57,color:#fff
    style J fill:#2E8B57,color:#fff
    style O fill:#2E8B57,color:#fff
    style P fill:#10192E,color:#fff
```

> Every step across the whole flow publishes to Kafka and is written to `AuditLog` with actor + timestamp — queryable by the role-scoped AI Assistant, subject to the same RBAC boundary as the UI. `ON_HOLD` is reachable from any state.

---

## ⚙ Components

[#-components](#-components)

| Service | Responsibility |
|---|---|
| **Project Lifecycle Svc** | Owns the `Project` entity and its state machine; auto-generated project codes; CEO feasibility review |
| **Workflow / Approval Engine** | A **generic** `ApprovalChain` + `ApprovalStep` model — configurable levels, per-level approver role, per-chain SLA. Reused as-is for budget, asset, and HR requests |
| **Requirements & Budget Svc** | Append-only revision chains (self-referencing `supersedes_id`) with a materialized "current" pointer — full history, no overwrites |
| **Meeting / Progress Log Svc** | Chronological, searchable, permanent log of every meeting tied to a project |
| **AI Assistant Svc** | Permission-scoped RAG — retrieval happens inside the user's permission boundary before the model ever sees the data |
| **Notification / Audit Log Svc** | Consumes Kafka events to dispatch notifications and write an immutable audit record of every transition |

---

## 🧠 AI Assistant Architecture

[#-ai-assistant-architecture](#-ai-assistant-architecture)

```mermaid
flowchart LR
    Q(["💬 User Query<br/>'Status of Project X?'"]) --> P["Permission Context<br/>(user_id, project_ids, role)<br/>from RBAC resolver"]
    P --> V["Filtered Vector Search<br/>query embedding +<br/>metadata filter: project_id IN allowed_ids"]
    V --> G["AI Model Generation<br/>only permitted chunks<br/>reach the prompt"]
    G --> R(["✅ Scoped Answer"])

    style Q fill:#3457D5,color:#fff
    style V fill:#6DB33F,color:#fff
    style R fill:#10192E,color:#fff
```

**Guardrails**

1. The retrieval filter is enforced at the **vector-store query level**, never via prompt instructions.
2. The AI model is never given a tool that can bypass the filter (e.g. raw SQL access).
3. Every AI answer logs which chunks were retrieved, for audit purposes.

**Ingestion pipeline** runs continuously via Kafka consumers — meeting notes, requirement revisions, task updates, and approval decisions are chunked and embedded as they're created, each tagged with `project_id`, `allowed_roles`, `entity_type`, `created_at`.

| Component | Free / Open-Source | Paid Alternative |
|---|---|---|
| Orchestration | LangChain / LlamaIndex | Managed RAG-as-a-service |
| Embeddings | Sentence-Transformers, BGE (local) | OpenAI / Cohere embedding APIs |
| Vector DB | FAISS, Chroma, Qdrant (self-hosted) | Pinecone, Weaviate Cloud |
| Generation model | Llama 3 / Mistral / Phi-3 via Ollama | Claude API / GPT API |
| Reranking (optional) | Open-source cross-encoders | Cohere Rerank API |

> Recommended path: validate the RBAC-scoped retrieval architecture against the **free, self-hosted stack** first (LangChain + Sentence-Transformers + Chroma + Ollama); swap in a hosted API only once answer quality needs to improve for production.

---

## 🔁 Budget Request — Sequence Diagram

[#-budget-request--sequence-diagram](#-budget-request--sequence-diagram)

```mermaid
sequenceDiagram
    actor Employee
    participant WF as Workflow Engine
    participant Manager
    participant Finance as Finance/Sales
    actor CEO

    Employee->>WF: raise BudgetRequest
    WF->>Manager: create ApprovalStep L1 (notify)
    Manager-->>WF: decision: approve
    WF->>Finance: create ApprovalStep L2 (notify)
    Finance-->>WF: decision: approve, budget available
    WF->>CEO: create ApprovalStep L3 — final sanction (notify)
    CEO-->>WF: decision: approved
    WF->>Employee: publish budget.revised event → notify

    Note over WF,CEO: In parallel via Kafka - AuditLog records every decision, Notification dispatches email/push, SLA worker auto-escalates on timeout
```

---

## 🔐 Role-Based Access Model

[#-role-based-access-model](#-role-based-access-model)

| Role | Visibility Scope | Cannot See |
|---|---|---|
| 👑 **CEO / Board** | All projects, budgets, meetings, approvals, org-wide analytics | — |
| 🧑‍💼 **Manager** | Only assigned projects — requirements, tasks, progress | Other managers' projects, board notes, org-wide budgets |
| 🧑‍💻 **Employee** | Only their own assigned tasks + relevant requirements | Project budget, other employees' tasks, meeting/approval history |
| 💰 **Finance / Sales / HR** | Only the specific request ticket routed to them | Full project context, other departments' tickets |

Enforced at two points: **(1)** the API Gateway resolves and attaches the permission context to every request, and **(2)** the AI Assistant's retrieval layer applies the same filter before returning any content to the LLM.

---

## 🧬 Core Schema & State Machines

[#-core-schema--state-machines](#-core-schema--state-machines)

```mermaid
erDiagram
    USER ||--o{ PROJECTROLE : has
    PROJECT ||--o{ PROJECTROLE : "scoped by"
    PROJECT ||--o{ MEETING : logs
    PROJECT ||--o{ REQREVISION : "revision chain"
    PROJECT ||--o{ BUDGETREVISION : "revision chain"
    PROJECT ||--o{ TASK : contains
    PROJECT ||--o{ APPROVALCHAIN : triggers
    APPROVALCHAIN ||--o{ APPROVALSTEP : "ordered levels"
    PROJECT ||--o{ REQUESTTICKET : routes
    REQUESTTICKET }o--|| APPROVALCHAIN : uses
    USER ||--o{ NOTIFICATION : receives
    PROJECT ||--o{ AUDITLOG : "logs to"

    USER {
        uuid id PK
        string name
        string email
        string designation
    }
    PROJECT {
        uuid id PK
        string code
        string title
        enum state
        uuid manager_id FK
    }
    APPROVALCHAIN {
        uuid id PK
        enum request_type
        enum status
        int sla_hours
    }
    REQREVISION {
        uuid id PK
        int version_no
        uuid supersedes_id FK
    }
    BUDGETREVISION {
        uuid id PK
        decimal amount
        uuid supersedes_id FK
    }
```

**Project lifecycle**

```mermaid
stateDiagram-v2
    [*] --> INQUIRY
    INQUIRY --> DISCUSSION
    DISCUSSION --> REQUIREMENTS_DEFINED
    REQUIREMENTS_DEFINED --> BUDGET_PROPOSED
    BUDGET_PROPOSED --> UNDER_APPROVAL
    UNDER_APPROVAL --> APPROVED
    UNDER_APPROVAL --> REJECTED
    APPROVED --> IN_PROGRESS
    IN_PROGRESS --> REVIEW
    REVIEW --> COMPLETED
    COMPLETED --> [*]
    REJECTED --> [*]

    state "Any State" as ANY
    ANY --> ON_HOLD
    ON_HOLD --> ANY
```

**Generic approval chain** (reused for Budget / Asset / Clarification requests)

```mermaid
stateDiagram-v2
    [*] --> DRAFT
    DRAFT --> PENDING_LEVEL_1
    PENDING_LEVEL_1 --> PENDING_LEVEL_2N : approve
    PENDING_LEVEL_2N --> APPROVED : approve
    PENDING_LEVEL_1 --> REJECTED : reject
    PENDING_LEVEL_2N --> REJECTED : reject
    PENDING_LEVEL_1 --> CLARIFICATION_REQUESTED : clarify
    PENDING_LEVEL_2N --> CLARIFICATION_REQUESTED : clarify
    CLARIFICATION_REQUESTED --> PENDING_LEVEL_1 : loops back
    PENDING_LEVEL_1 --> PENDING_LEVEL_1 : SLA timeout → escalate
    APPROVED --> [*]
    REJECTED --> [*]
```

- `ReqRevision` and `BudgetRevision` are **append-only, self-referencing** (`supersedes_id`) — history is never lost.
- `ApprovalChain` + `ApprovalStep` is the **one generic engine** reused by `RequestTicket` for every request type.

---

## 🛠 Tech Stack

[#-tech-stack](#-tech-stack)

<div align="center">
<table>
<tr>
<td align="center" width="90"><img src="https://img.icons8.com/3d-fluency/60/angular.png" width="38"/></td>
<td align="center" width="90"><img src="https://img.icons8.com/3d-fluency/60/spring-logo.png" width="38"/></td>
<td align="center" width="90"><img src="https://img.icons8.com/3d-fluency/60/java-coffee-cup-logo.png" width="38"/></td>
<td align="center" width="90"><img src="https://img.icons8.com/3d-fluency/60/kafka.png" width="38"/></td>
<td align="center" width="90"><img src="https://img.icons8.com/3d-fluency/60/postgreesql.png" width="38"/></td>
<td align="center" width="90"><img src="https://img.icons8.com/3d-fluency/60/redis.png" width="38"/></td>
<td align="center" width="90"><img src="https://img.icons8.com/3d-fluency/60/docker.png" width="38"/></td>
<td align="center" width="90"><img src="https://img.icons8.com/3d-fluency/60/kubernetes.png" width="38"/></td>
</tr>
</table>
</div>
<br/>

| Layer | Technology | Notes |
|---|---|---|
| Frontend framework | Angular 17+ (standalone components) | No NgModules needed |
| Frontend language | TypeScript 5.x | Strict mode enabled |
| Frontend styling | SCSS + CSS custom-property design tokens | See [Design System](#-frontend-design-system) |
| Backend framework | Spring Boot 3.x | Requires Java 17+ |
| Backend language | Java 21 (LTS) | |
| API style | REST (OpenAPI 3) | Sync service-to-service & client calls |
| Event streaming | Apache Kafka 3.x | State-change propagation, async fan-out |
| Relational database | PostgreSQL 16 | System of record, append-only revisions |
| Cache | Redis 7.x | Permission-context & session caching |
| Vector database | Chroma / Qdrant (free) or Pinecone (paid) | For the AI assistant |
| AI orchestration | LangChain | Retrieval, prompt assembly, chat memory |
| AI model | Claude / GPT API, or Llama 3 / Mistral via Ollama | Self-hosted-first path |
| Containerization | Docker | One image per service |
| Orchestration (prod) | Kubernetes (optional at scale) | Not required for MVP |
| CI/CD | GitHub Actions | Test → build → containerize → deploy |
| Cloud hosting | Azure App Service / AKS, or AWS/GCP equivalent | Vendor-agnostic |

---

## 🗂 Project Structure

[#-project-structure](#-project-structure)

```
operiva-os
│
├── frontend                     # Angular SPA
│   ├── app-shell                # sidebar, topbar, route guards
│   ├── dashboard
│   ├── project-list
│   ├── project-detail           # Overview / Progress / Req+Budget / Tasks / Approvals
│   └── chat-widget               # AI assistant UI
│
├── services
│   ├── lifecycle-svc             # Project entity + state machine
│   ├── workflow-svc               # Generic ApprovalChain engine
│   ├── rbac-svc                   # Auth + (user, project, role) resolver
│   ├── requirements-budget-svc
│   ├── progress-svc               # Meeting log
│   ├── task-svc
│   ├── notification-svc
│   ├── audit-log-svc
│   └── ai-assistant-svc           # Python + LangChain, permission-scoped RAG
│
├── infra
│   ├── docker-compose.yml         # full local stack
│   └── github-actions/            # CI/CD pipeline definitions
│
└── docs                           # ER diagram, state machines, RBAC model
```

---

## 🎨 Frontend Design System

[#-frontend-design-system](#-frontend-design-system)

- **Palette:** deep navy ink `#10192E` (sidebar/header), soft canvas `#F5F7FB` (background), royal-blue `#3457D5` (primary actions)
- **State colors:** blue (early stage) · amber (under approval) · green (approved/complete) · red (rejected) · purple (on-hold/clarification)
- **Typography:** Fraunces (serif) for titles/wordmark, Inter (sans) for functional UI
- **Signature element — the Lifecycle Rail:** a segmented progress bar showing a project's exact state-machine position, used on dashboard cards, table rows, and the detail header
- **Responsive:** sidebar → slide-in drawer below 1024px; tables → stacked cards below 860px; stat grids reflow 4 → 1 column
- **Accessibility:** visible keyboard focus rings, `prefers-reduced-motion` respected

| Screen | What it shows |
|---|---|
| Login | Minimal, enterprise-styled sign-in |
| Dashboard | Role-aware stat cards, "projects in motion" feed, activity panel |
| Project List | Filterable by lifecycle state; table (desktop) / cards (mobile) |
| Project Detail | Overview · Progress · Requirements & Budget (RBAC-restricted) · Tasks · Approvals |

---

## 🐳 Running Locally

[#-running-locally](#-running-locally)

```bash
git clone https://github.com/<your-org>/operiva-os.git
cd operiva-os
docker compose up
```

`docker-compose.yml` brings up every backend service plus **PostgreSQL, Redis, Kafka, and the vector database** in one command:

```mermaid
flowchart LR
    FE["frontend<br/>nginx + angular"]
    LC["lifecycle-svc"]
    WF["workflow-svc"]
    RB["rbac-svc"]
    AI["ai-assistant<br/>python + langchain"]
    PG[("postgres")]
    RD[("redis")]
    KF{{"kafka + zookeeper"}}
    VD[("vector-db")]

    FE --> LC & WF & RB
    LC & WF & RB --> PG
    LC & WF & RB --> RD
    LC & WF & RB --> KF
    KF --> AI
    AI --> VD
```

Each backend Dockerfile is **multi-stage**: a build stage with the full SDK, and a slim `eclipse-temurin:21-jre-alpine` runtime — no build tools, no source code, minimal attack surface in the final image.

---

## 📊 Observability

[#-observability](#-observability)

Every `AuditLog` write and every AI retrieval is queryable alongside infrastructure metrics from day one.

```mermaid
flowchart LR
    A["OPERIVA services"] --> B["Centralized Logging<br/>+ AuditLog stream"]
    B --> C["Metrics<br/>Azure Monitor / Prometheus"]
    C --> D["Dashboards<br/>Grafana"]

    style A fill:#3457D5,color:#fff
    style D fill:#10192E,color:#fff
```

- **Secrets:** managed secret store (Azure Key Vault or equivalent) — never committed to source control, never baked into images.
- **Database:** managed PostgreSQL with automated backups and point-in-time recovery.
- Architecture is intentionally **cloud-vendor-agnostic** — every component has a direct equivalent on AWS or GCP.

---

## 🔁 CI/CD Pipeline

[#-cicd-pipeline](#-cicd-pipeline)

```mermaid
flowchart LR
    A["Push to main / PR"] --> B["Lint + Unit Tests"]
    B --> C["Build<br/>Maven / ng build"]
    C --> D["Docker Build<br/>tag: git SHA"]
    D --> E["Push to Registry"]
    E --> F["Deploy: staging"]
    F -->|smoke tests pass| G["Deploy: production"]

    style A fill:#3457D5,color:#fff
    style G fill:#2E8B57,color:#fff
```

- A failing test blocks the merge.
- Images are tagged with the **Git commit SHA**, never overwriting `latest`.
- Production deploys require staging smoke tests to pass first; DB migrations run as a separate, reviewed step — never bundled silently into deploy.

---

## 🗺 Development Roadmap

[#-development-roadmap](#-development-roadmap)

10 weeks to a launch-ready MVP with a small, focused team (2–4 engineers).

```mermaid
gantt
    title OPERIVA OS — 10 Week MVP Roadmap
    dateFormat  YYYY-MM-DD
    axisFormat  W%W
    section Backend
    P0 Discovery & Design        :p0, 2026-01-05, 7d
    P1 Core Lifecycle Engine     :p1, after p0, 14d
    P2 RBAC & Auth Layer         :p2, after p1, 7d
    P3 Workflow / Approval Engine:p3, after p2, 14d
    P4 Events & Notifications    :p4, after p3, 7d
    P5 AI Assistant (RAG)        :p5, after p4, 7d
    section Frontend
    P6 Frontend Build            :p6, 2026-01-19, 49d
    section Release
    P7 Docker / CI/CD            :p7, after p5, 7d
    P8 Testing & Launch          :p8, after p7, 7d
```

| Phase | Name | Duration | Primary Deliverable |
|---|---|---|---|
| 0 | Discovery & System Design | Week 1 | Finalized schema, API contracts, RBAC model |
| 1 | Core Lifecycle Engine | Week 2–3 | Project entity, state machine, meetings, versioned requirements/budget |
| 2 | RBAC & Auth Layer | Week 4 | JWT auth, project-scoped permission resolver |
| 3 | Workflow / Approval Engine | Week 5–6 | Generic ApprovalChain engine |
| 4 | Event Layer & Notifications | Week 6–7 | Kafka topics, SLA-escalation worker, dispatch |
| 5 | AI Assistant (RAG) | Week 7–8 | Role-scoped retrieval pipeline, chat UI |
| 6 | Frontend Build | Week 3–9 (parallel) | All role-based screens, fully responsive |
| 7 | Docker, CI/CD & Deployment | Week 9 | Containerized services, automated pipeline |
| 8 | Testing, Hardening & Launch | Week 10 | End-to-end pass, production launch |

---

## ⚠️ Risks & Mitigations

[#️-risks--mitigations](#️-risks--mitigations)

| Risk | Mitigation |
|---|---|
| RBAC modeled as simple global roles instead of project-scoped | Freeze the `(user, project, role)` model in Phase 0 before dependent code exists |
| Budget/requirements stored as mutable fields | Append-only revision tables from day one, never retrofitted |
| AI retrieval not scoped at the vector-store level | Enforce the permission filter in the vector query itself, never via prompts alone |
| All modules attempted simultaneously | Strict phase boundaries with defined exit criteria |
| Notification spam from naive SLA reminders | Escalate to the next level on timeout rather than re-pinging the same approver |
| Premature microservice split | Modular monolith first; extract only services whose load genuinely diverges |

---

## 💡 Design Principles

[#-design-principles](#-design-principles)

- Single source of truth for the entire project lifecycle
- Full, append-only audit trail — strong fit for regulated sectors
- One generic workflow engine reused across every approval type
- Project-scoped RBAC by design, not by convention
- Permission-scoped AI retrieval, never prompt-only guardrails
- Domain-agnostic core — usable well beyond one industry
- Ship in bounded phases — a working system exists early

---

## 📌 Applicable Domains

[#-applicable-domains](#-applicable-domains)

<div align="center">
<table>
<tr>
<td align="center" width="140"><img src="https://img.icons8.com/3d-fluency/60/city-buildings.png" width="44"/><br/><sub><b>Corporates</b></sub></td>
<td align="center" width="140"><img src="https://img.icons8.com/3d-fluency/60/courthouse.png" width="44"/><br/><sub><b>Government</b></sub></td>
<td align="center" width="140"><img src="https://img.icons8.com/3d-fluency/60/scales.png" width="44"/><br/><sub><b>Law Firms</b></sub></td>
<td align="center" width="140"><img src="https://img.icons8.com/3d-fluency/60/helping-hand.png" width="44"/><br/><sub><b>NGOs</b></sub></td>
<td align="center" width="140"><img src="https://img.icons8.com/3d-fluency/60/shop.png" width="44"/><br/><sub><b>SMBs</b></sub></td>
</tr>
</table>
</div>

---

## 📄 License

[#-license](#-license)

MIT — see [`LICENSE`](./LICENSE) for details.

<div align="center">
<img src="https://capsule-render.vercel.app/api?type=waving&amp;color=gradient&amp;customColorList=2,6,12,20&amp;height=100&amp;section=footer" width="100%"/>
</div>
