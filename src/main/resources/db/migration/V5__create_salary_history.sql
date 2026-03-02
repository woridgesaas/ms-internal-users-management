CREATE TABLE salary_history
(
    id UUID PRIMARY KEY,
    user_id UUID NOT NULL,
    salary NUMERIC(12,2) NOT NULL,
    post_name VARCHAR(100) NOT NULL,
    valid_from DATE NOT NULL,
    valid_to DATE,
    created_at TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP NOT NULL,
    updated_at TIMESTAMPTZ,

    CONSTRAINT fk_salary_history_user
        FOREIGN KEY (user_id)
            REFERENCES users(id)
            ON DELETE CASCADE
            ON UPDATE CASCADE
);