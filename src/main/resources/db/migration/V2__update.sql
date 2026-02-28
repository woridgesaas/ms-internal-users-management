-- 3️⃣ Ajouter colonne role
ALTER TABLE users
    ADD COLUMN role VARCHAR(50) NOT NULL ;

-- 4️⃣ Ajouter colonne post_id
ALTER TABLE users
    ADD COLUMN post_id UUID;

-- 5️⃣ Ajouter contrainte FK
ALTER TABLE users
    ADD CONSTRAINT fk_user_post
        FOREIGN KEY (post_id)
            REFERENCES posts(id)
            ON DELETE SET NULL
            ON UPDATE CASCADE;

-- Professional Experiences table
CREATE TABLE professional_experiences
(
    id              UUID PRIMARY KEY,
    user_id         UUID                                NOT NULL,
    company_name    VARCHAR(100)                        NOT NULL,
    job_title       VARCHAR(100)                        NOT NULL,
    employment_type VARCHAR(50), -- CDI, CDD, Stage, Freelance...
    location        VARCHAR(100),
    start_date      DATE                                NOT NULL,
    end_date        DATE,
    is_current      BOOLEAN DEFAULT FALSE               NOT NULL,
    description     TEXT,
    logo_path       TEXT,
    created_at      TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP NOT NULL,
    updated_at      TIMESTAMPTZ,

    CONSTRAINT fk_experience_user
        FOREIGN KEY (user_id)
            REFERENCES users (id)
            ON DELETE CASCADE
            ON UPDATE CASCADE
);