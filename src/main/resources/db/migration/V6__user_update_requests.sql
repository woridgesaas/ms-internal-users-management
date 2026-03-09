CREATE TABLE user_update_requests (
                                      id UUID PRIMARY KEY,

                                      user_id UUID NOT NULL,

    -- OLD VALUES (Snapshot)
                                      old_first_name VARCHAR(50),
                                      old_last_name VARCHAR(50),
                                      old_cin VARCHAR(10),
                                      old_email VARCHAR(50),
                                      old_phone VARCHAR(15),
                                      old_birth_date DATE,
                                      old_address TEXT,
                                      old_profile_image_url TEXT,

    -- NEW VALUES (Requested)
                                      new_first_name VARCHAR(50),
                                      new_last_name VARCHAR(50),
                                      new_cin VARCHAR(10),
                                      new_email VARCHAR(50),
                                      new_phone VARCHAR(15),
                                      new_birth_date DATE,
                                      new_address TEXT,
                                      new_profile_image_url TEXT,

                                      status VARCHAR(20) NOT NULL DEFAULT 'PENDING',

                                      requested_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                                      processed_at TIMESTAMP NULL,

                                      processed_by UUID NULL,

                                      CONSTRAINT fk_user
                                          FOREIGN KEY (user_id)
                                              REFERENCES users(id)
                                              ON DELETE CASCADE,

                                      CONSTRAINT fk_processed_by
                                          FOREIGN KEY (processed_by)
                                              REFERENCES users(id),

                                      CONSTRAINT chk_status
                                          CHECK (status IN ('PENDING', 'ACCEPTED', 'REJECTED'))
);

CREATE INDEX idx_user_update_requests_status
    ON user_update_requests(status);

CREATE INDEX idx_user_update_requests_user_id
    ON user_update_requests(user_id);

CREATE INDEX idx_user_update_requests_requested_at
    ON user_update_requests(requested_at);


ALTER TABLE users
    ADD COLUMN birthdate DATE;