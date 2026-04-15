-- Profile update requests table
CREATE TABLE profile_update_requests
(
    id               UUID PRIMARY KEY NOT NULL,
    section_type     VARCHAR(50)      NOT NULL,
    status           VARCHAR(20)      NOT NULL DEFAULT 'PENDING',
    target_entity_id UUID,
    requested_at     TIMESTAMP        NOT NULL,
    processed_at     TIMESTAMP,
    processed_by     UUID,
    user_id          UUID             NOT NULL,

    CONSTRAINT chk_target_entity
        CHECK (
            (section_type = 'PERSONAL_INFORMATION' AND target_entity_id IS NULL)
                OR
            (section_type != 'PERSONAL_INFORMATION' AND target_entity_id IS NOT NULL)
            ),

    CONSTRAINT chk_status
        CHECK (status IN ('PENDING', 'APPROVED', 'REJECTED'))
);

CREATE INDEX idx_profile_update_requests_user_id ON profile_update_requests (user_id);
CREATE INDEX idx_profile_update_requests_status ON profile_update_requests (status);
CREATE INDEX idx_profile_update_requests_section ON profile_update_requests (section_type);
CREATE INDEX idx_profile_update_requests_requested ON profile_update_requests (requested_at);

