CREATE TABLE holidays
(
    id       BIGSERIAL PRIMARY KEY,
    label_fr TEXT    NOT NULL,
    label_ar TEXT    NOT NULL,
    date     DATE    NOT NULL,
    duration INTEGER     DEFAULT 1,
    is_fixed BOOLEAN NOT NULL,
    status   VARCHAR(20) DEFAULT 'CONFIRMED'
);