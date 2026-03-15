-- Department table
CREATE TABLE departments
(
    id         UUID PRIMARY KEY,
    name       VARCHAR(50)                           NOT NULL,
    status     VARCHAR(50)                           NOT NULL,
    created_at TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP NOT NULL,
    updated_at TIMESTAMPTZ
);

-- Post table
CREATE TABLE posts
(
    id          UUID PRIMARY KEY,
    name        VARCHAR(50) UNIQUE                    NOT NULL,
    status      VARCHAR(20)                           NOT NULL,
    base_salary NUMERIC(12, 2)                        NOT NULL,
    created_at  TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP NOT NULL,
    updated_at  TIMESTAMPTZ
);

-- User table
CREATE TABLE users
(
    id            UUID PRIMARY KEY,
    first_name    VARCHAR(50)                           NOT NULL,
    last_name     VARCHAR(50)                           NOT NULL,
    email         VARCHAR(50) UNIQUE                    NOT NULL,
    password      VARCHAR(255)                          NOT NULL,
    phone_number  VARCHAR(15)                           NOT NULL,
    CIN           VARCHAR(10) UNIQUE                    NOT NULL,
    status        VARCHAR(50)                           NOT NULL,
    address       TEXT,
    picture_path  TEXT,
    role          VARCHAR(50)                           NOT NULL,
    birthdate     DATE,
    department_id UUID                                  NOT NULL,
    post_id       UUID                                  NOT NULL,
    created_at    TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP NOT NULL,
    updated_at    TIMESTAMPTZ,
    CONSTRAINT fk_department
        FOREIGN KEY (department_id)
            REFERENCES departments (id)
            ON DELETE RESTRICT
            ON UPDATE RESTRICT,

    CONSTRAINT fk_user_post
        FOREIGN KEY (post_id)
            REFERENCES posts (id)
            ON DELETE RESTRICT
            ON UPDATE RESTRICT
);

-- Promotion table
CREATE TABLE promotions
(
    id                 UUID PRIMARY KEY,
    user_id            UUID                                  NOT NULL,
    previous_post_id   UUID                                  NOT NULL,
    previous_post_name VARCHAR(50)                           NOT NULL,
    new_post_id        UUID                                  NOT NULL,
    new_post_name      VARCHAR(50)                           NOT NULL,
    promotion_date     DATE                                  NOT NULL,
    previous_salary    NUMERIC(12, 2)                        NOT NULL,
    new_salary         NUMERIC(12, 2)                        NOT NULL,
    created_at         TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP NOT NULL,
    updated_at         TIMESTAMPTZ,

    CONSTRAINT check_promotions_salary
        CHECK (new_salary >= previous_salary),

    CONSTRAINT fk_promotions_user_id
        FOREIGN KEY (user_id)
            REFERENCES users (id)
            ON DELETE RESTRICT
            ON UPDATE RESTRICT,

    CONSTRAINT fk_promotions_previous_post_id
        FOREIGN KEY (previous_post_id)
            REFERENCES posts (id)
            ON DELETE RESTRICT
            ON UPDATE RESTRICT,

    CONSTRAINT fk_promotions_new_post_id
        FOREIGN KEY (new_post_id)
            REFERENCES posts (id)
            ON DELETE RESTRICT
            ON UPDATE RESTRICT
);

-- Bank information table
CREATE TABLE bank_informations
(
    id             UUID PRIMARY KEY,
    user_id        UUID                                  NOT NULL UNIQUE,
    name           VARCHAR(50)                           NOT NULL,
    account_number VARCHAR(100)                          NOT NULL,
    code_iban      VARCHAR(100)                          NOT NULL,
    agency         VARCHAR(50)                           NOT NULL,
    created_at     TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP NOT NULL,
    updated_at     TIMESTAMPTZ,
    CONSTRAINT fk_bank_information_user
        FOREIGN KEY (user_id)
            REFERENCES users (id)
            ON DELETE RESTRICT
            ON UPDATE RESTRICT
);

-- Education table
CREATE TABLE educations
(
    id               UUID PRIMARY KEY,
    user_id          UUID                                  NOT NULL,
    university       VARCHAR(50)                           NOT NULL,
    field_of_study   VARCHAR(50)                           NOT NULL,
    degree           VARCHAR(50)                           NOT NULL,
    start_final_year VARCHAR(50)                           NOT NULL,
    created_at       TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP NOT NULL,
    updated_at       TIMESTAMPTZ,
    CONSTRAINT fk_education_user
        FOREIGN KEY (user_id)
            REFERENCES users (id)
            ON DELETE RESTRICT
            ON UPDATE RESTRICT
);

-- Resignation table
CREATE TABLE resignations
(
    id               UUID PRIMARY KEY,
    user_id          UUID                                  NOT NULL UNIQUE,
    reason           TEXT                                  NOT NULL,
    notice_date      DATE                                  NOT NULL,
    resignation_date DATE                                  NOT NULL,
    created_at       TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP NOT NULL,
    updated_at       TIMESTAMPTZ,
    CONSTRAINT fk_resignation_user
        FOREIGN KEY (user_id)
            REFERENCES users (id)
            ON DELETE RESTRICT
            ON UPDATE RESTRICT
);

-- Payroll details table
CREATE TABLE payroll_details
(
    id             UUID PRIMARY KEY,
    date           DATE                                  NOT NULL,
    type           VARCHAR(50)                           NOT NULL,
    source_service VARCHAR(50)                           NOT NULL,
    value          NUMERIC(12, 2)                        NOT NULL,
    amount         NUMERIC(12, 2)                        NOT NULL,
    final_salary   NUMERIC(12, 2)                        NOT NULL,
    generated_at   TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP NOT NULL,
    updated_at     TIMESTAMPTZ
);

-- Payroll table
CREATE TABLE payrolls
(
    id                   UUID PRIMARY KEY,
    user_id              UUID                                  NOT NULL,
    detail_id            UUID                                  NOT NULL,
    date                 DATE                                  NOT NULL,
    total_overtime_hours NUMERIC(6, 2)                         NOT NULL,
    total_absence_hours  NUMERIC(6, 2)                         NOT NULL,
    bonus_amount         NUMERIC(12, 2)                        NOT NULL,
    deduction_amount     NUMERIC(12, 2)                        NOT NULL,
    final_salary         NUMERIC(12, 2)                        NOT NULL,
    generated_at         TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP NOT NULL,
    updated_at           TIMESTAMPTZ,
    CONSTRAINT fk_payroll_user
        FOREIGN KEY (user_id)
            REFERENCES users (id)
            ON DELETE RESTRICT
            ON UPDATE RESTRICT,
    CONSTRAINT fk_payroll_detail
        FOREIGN KEY (detail_id)
            REFERENCES payroll_details (id)
            ON DELETE RESTRICT
            ON UPDATE RESTRICT
);