-- Professional experiences table
CREATE TABLE professional_experiences
(
    id               UUID PRIMARY KEY,
    employment_type  VARCHAR(50)                           NOT NULL, -- CDI, CDD, Stage, Freelance...
    experience_level VARCHAR(50)                           NOT NULL, -- JUNIOR, MID, SENIOR, LEAD
    company_name     VARCHAR(50)                           NOT NULL,
    company_location VARCHAR(50)                           NOT NULL,
    job_title        VARCHAR(50)                           NOT NULL,
    start_date       DATE                                  NOT NULL,
    end_date         DATE,
    is_current       BOOLEAN     DEFAULT FALSE             NOT NULL,
    description      TEXT,
    created_at       TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP NOT NULL,
    updated_at       TIMESTAMPTZ,
    user_id          UUID                                  NOT NULL,

    CONSTRAINT fk_professional_experience_user
        FOREIGN KEY (user_id)
            REFERENCES users (id)
            ON DELETE RESTRICT
            ON UPDATE RESTRICT
);