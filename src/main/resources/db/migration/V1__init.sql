-- ENUM type for status
CREATE TYPE status AS ENUM ('ACTIVE', 'INACTIVE');

-- Department table
CREATE TABLE departments
(
    id         BIGSERIAL PRIMARY KEY,
    name       VARCHAR(50)                           NOT NULL,
    status     status                                NOT NULL,
    created_at TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP NOT NULL,
    updated_at TIMESTAMPTZ
);

-- User table
CREATE TABLE users
(
    id            BIGSERIAL PRIMARY KEY,
    first_name    VARCHAR(50)                           NOT NULL,
    last_name     VARCHAR(50)                           NOT NULL,
    email         VARCHAR(50) UNIQUE                    NOT NULL,
    password      VARCHAR(255)                          NOT NULL,
    phone_number  VARCHAR(15)                           NOT NULL,
    CIN           VARCHAR(10) UNIQUE                    NOT NULL,
    status        status                                NOT NULL,
    address       TEXT,
    picture_path  TEXT,
    department_id BIGINT                                NOT NULL,
    created_at    TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP NOT NULL,
    updated_at    TIMESTAMPTZ,
    CONSTRAINT fk_department
        FOREIGN KEY (department_id)
            REFERENCES departments (id)
            ON DELETE CASCADE
            ON UPDATE CASCADE
);

-- Promotion table
CREATE TABLE promotions
(
    id              BIGSERIAL PRIMARY KEY,
    user_id         BIGINT                                NOT NULL,
    promotion_date  DATE                                  NOT NULL,
    previous_salary NUMERIC(12, 2)                        NOT NULL,
    new_salary      NUMERIC(12, 2)                        NOT NULL,
    previous_post   VARCHAR(50)                           NOT NULL,
    new_post        VARCHAR(50)                           NOT NULL,
    created_at      TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP NOT NULL,
    updated_at      TIMESTAMPTZ,
    CONSTRAINT fk_promotion_user
        FOREIGN KEY (user_id)
            REFERENCES users (id)
            ON DELETE CASCADE
            ON UPDATE CASCADE
);

-- Post table
CREATE TABLE posts
(
    id           BIGSERIAL PRIMARY KEY,
    promotion_id BIGINT                                NOT NULL,
    name         VARCHAR(50)                           NOT NULL,
    level        INT,
    base_salary  NUMERIC(12, 2)                        NOT NULL,
    created_at   TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP NOT NULL,
    updated_at   TIMESTAMPTZ,
    CONSTRAINT fk_post_promotions FOREIGN KEY (promotion_id)
        REFERENCES promotions (id)
        ON DELETE RESTRICT
        ON UPDATE CASCADE
);

-- Bank information table
CREATE TABLE bank_informations
(
    id             BIGSERIAL PRIMARY KEY,
    user_id        BIGINT                                NOT NULL UNIQUE,
    name           VARCHAR(50)                           NOT NULL,
    account_number VARCHAR(100)                          NOT NULL,
    code_iban      VARCHAR(100)                          NOT NULL,
    agency         VARCHAR(50)                           NOT NULL,
    created_at     TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP NOT NULL,
    updated_at     TIMESTAMPTZ,
    CONSTRAINT fk_bank_information_user
        FOREIGN KEY (user_id)
            REFERENCES users (id)
            ON DELETE CASCADE
            ON UPDATE CASCADE
);

-- Education table
CREATE TABLE educations
(
    id               BIGSERIAL PRIMARY KEY,
    user_id          BIGINT                                NOT NULL,
    university       VARCHAR(50)                           NOT NULL,
    field_of_study   VARCHAR(50)                           NOT NULL,
    degree           VARCHAR(50)                           NOT NULL,
    start_final_year VARCHAR(50)                           NOT NULL,
    created_at       TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP NOT NULL,
    updated_at       TIMESTAMPTZ,
    CONSTRAINT fk_education_user
        FOREIGN KEY (user_id)
            REFERENCES users (id)
            ON DELETE CASCADE
            ON UPDATE CASCADE
);

-- Resignation table
CREATE TABLE resignations
(
    id               BIGSERIAL PRIMARY KEY,
    user_id          BIGINT                                NOT NULL UNIQUE,
    reason           TEXT                                  NOT NULL,
    notice_date      DATE                                  NOT NULL,
    resignation_date DATE                                  NOT NULL,
    created_at       TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP NOT NULL,
    updated_at       TIMESTAMPTZ,
    CONSTRAINT fk_resignation_user
        FOREIGN KEY (user_id)
            REFERENCES users (id)
            ON DELETE CASCADE
            ON UPDATE CASCADE
);

-- Payroll details table
CREATE TABLE payroll_details
(
    id             BIGSERIAL PRIMARY KEY,
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
    id                   BIGSERIAL PRIMARY KEY,
    user_id              BIGINT                                NOT NULL,
    detail_id            BIGINT                                NOT NULL,
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
            ON DELETE CASCADE
            ON UPDATE CASCADE,
    CONSTRAINT fk_payroll_detail
        FOREIGN KEY (detail_id)
            REFERENCES payroll_details (id)
            ON DELETE CASCADE
            ON UPDATE CASCADE
);