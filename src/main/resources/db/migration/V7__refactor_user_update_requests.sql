CREATE TABLE request_field_changes (
                                       id UUID PRIMARY KEY NOT NULL,
                                       request_id UUID NOT NULL,

                                       field_name VARCHAR(50) NOT NULL,

                                       old_value TEXT,
                                       new_value TEXT,

                                       CONSTRAINT fk_request
                                           FOREIGN KEY (request_id)
                                               REFERENCES user_update_requests(id)
                                               ON DELETE CASCADE
);
CREATE INDEX idx_request_field_changes_request_id
    ON request_field_changes(request_id);
ALTER TABLE user_update_requests
DROP COLUMN old_first_name,
DROP COLUMN old_last_name,
DROP COLUMN old_cin,
DROP COLUMN old_email,
DROP COLUMN old_phone,
DROP COLUMN old_birth_date,
DROP COLUMN old_address,
DROP COLUMN old_profile_image_url,
DROP COLUMN new_first_name,
DROP COLUMN new_last_name,
DROP COLUMN new_cin,
DROP COLUMN new_email,
DROP COLUMN new_phone,
DROP COLUMN new_birth_date,
DROP COLUMN new_address,
DROP COLUMN new_profile_image_url;

ALTER TABLE user_update_requests
DROP CONSTRAINT chk_status;

ALTER TABLE user_update_requests
    ADD CONSTRAINT chk_status
        CHECK (status IN ('PENDING', 'APPROVED', 'REJECTED'));
