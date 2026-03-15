CREATE TABLE user_update_requests
(
    id           UUID PRIMARY KEY,
    status       VARCHAR(20)        DEFAULT 'PENDING' NOT NULL,
    requested_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    processed_at TIMESTAMP NULL,
    user_id      UUID      NOT NULL,
    processed_by UUID NULL,

    CONSTRAINT fk_user
        FOREIGN KEY (user_id)
            REFERENCES users (id)
            ON DELETE RESTRICT,

    CONSTRAINT fk_processed_by
        FOREIGN KEY (processed_by)
            REFERENCES users (id),

    CONSTRAINT chk_status
        CHECK (status IN ('PENDING', 'ACCEPTED', 'REJECTED'))
);

CREATE INDEX idx_user_update_requests_status
    ON user_update_requests (status);

CREATE INDEX idx_user_update_requests_user_id
    ON user_update_requests (user_id);

CREATE INDEX idx_user_update_requests_requested_at
    ON user_update_requests (requested_at);