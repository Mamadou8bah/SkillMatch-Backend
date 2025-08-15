-- Flyway baseline migration: initial schema
-- Note: id types follow the entities (AUTO -> bigserial, IDENTITY -> bigserial)

create table users (
    id bigserial primary key,
    full_name varchar(100) not null,
    profile_picture varchar(255),
    email varchar(255) not null unique,
    location varchar(255) not null,
    password varchar(255) not null,
    role varchar(50),
    is_active boolean,
    created_at timestamp not null,
    account_verified boolean,
    login_disabled boolean
);

create table candidates (
    id bigserial primary key,
    user_id bigint references users(id) on delete cascade,
    bio varchar(200),
    profession varchar(200),
    cv_url varchar(255)
);

create table employer (
    id bigserial primary key,
    company_name varchar(255) not null,
    website varchar(255),
    industry varchar(255) not null,
    location varchar(255) not null,
    description text not null,
    picture varchar(255) not null,
    user_id bigint references users(id) on delete cascade
);

create table job_post (
    id bigserial primary key,
    title varchar(255) not null,
    description text not null,
    employer_id bigint references employer(id) on delete cascade,
    location_type varchar(50) not null,
    salary double precision not null,
    posted_at timestamp not null
);

create table skill (
    id bigserial primary key,
    title varchar(255) not null,
    candidate_id bigint references candidates(id) on delete cascade,
    job_post_id bigint references job_post(id) on delete cascade
);

create table experience (
    id bigserial primary key,
    company_name varchar(255) not null,
    job_title varchar(255) not null,
    description text,
    start_date date not null,
    end_date date,
    candidate_id bigint references candidates(id) on delete cascade
);

create table education (
    id bigserial primary key,
    institution_name varchar(255),
    degree varchar(255),
    year_started int,
    year_completed int,
    education_type varchar(50),
    candidate_id bigint references candidates(id) on delete cascade
);

create table applications (
    id bigserial primary key,
    candidate_id bigint not null references candidates(id) on delete cascade,
    job_post_id bigint not null references job_post(id) on delete cascade,
    status varchar(255) not null,
    created_at timestamp not null,
    updated_at timestamp
);

create table token (
    id bigserial primary key,
    token varchar(255) not null,
    revoked boolean,
    expired boolean,
    user_id bigint references users(id) on delete cascade
);

create table secureTokens (
    id bigserial primary key,
    token varchar(255) unique,
    expired_at timestamp not null,
    user_id bigint references users(id) on delete cascade
);

-- Indexes
create index idx_candidates_user on candidates(user_id);
create index idx_employer_user on employer(user_id);
create index idx_job_post_employer on job_post(employer_id);
create index idx_skill_candidate on skill(candidate_id);
create index idx_skill_job on skill(job_post_id);
create index idx_experience_candidate on experience(candidate_id);
create index idx_education_candidate on education(candidate_id);
create index idx_applications_candidate on applications(candidate_id);
create index idx_applications_job on applications(job_post_id);
create index idx_token_user on token(user_id);
create index idx_secure_tokens_user on secureTokens(user_id);
