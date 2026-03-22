CREATE TABLE social_info (
                             id UUID PRIMARY KEY,
                             user_id UUID NOT NULL UNIQUE,
                             cnss_number VARCHAR(20),
                             cimr_number VARCHAR(20),
                             family_deductions INT DEFAULT 0, --nbrchilds
                             CONSTRAINT fk_social_user FOREIGN KEY (user_id) REFERENCES users(id)
);