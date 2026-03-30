

ALTER TABLE payrolls
    ADD COLUMN status VARCHAR(20) NOT NULL DEFAULT 'EN_ATTENTE';


UPDATE payrolls
SET status = CASE
                 WHEN is_validated = TRUE THEN 'VALIDEE'
                 ELSE 'EN_ATTENTE'
    END;


ALTER TABLE payrolls
DROP COLUMN is_validated;