CREATE TABLE request_field_changes
(
    id         UUID PRIMARY KEY NOT NULL,
    field_name VARCHAR(50)      NOT NULL,
    old_value  TEXT,
    new_value  TEXT,
    request_id UUID             NOT NULL,

    CONSTRAINT fk_request
        FOREIGN KEY (request_id)
            REFERENCES user_update_requests (id)
            ON DELETE CASCADE
);

CREATE INDEX idx_request_field_changes_request_id
    ON request_field_changes (request_id);