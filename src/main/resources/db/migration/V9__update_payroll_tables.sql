-- 2. Row of jasper
ALTER TABLE payrolls DROP CONSTRAINT IF EXISTS fk_payroll_detail;
DROP TABLE IF EXISTS payroll_details CASCADE;

CREATE TABLE payroll_details (
                                 id           UUID PRIMARY KEY,
                                 payroll_id   UUID NOT NULL,
                                 label        VARCHAR(100) NOT NULL,
                                 type         VARCHAR(20) NOT NULL,
                                 base_value DOUBLE PRECISION,
                                 rate       DOUBLE PRECISION,
                                 amount     DOUBLE PRECISION NOT NULL,
                                 CONSTRAINT fk_detail_payroll FOREIGN KEY (payroll_id) REFERENCES payrolls (id) ON DELETE CASCADE
);
--1 parent ligne

DROP TABLE IF EXISTS payrolls CASCADE;

CREATE TABLE payrolls (
                          id                   UUID PRIMARY KEY,
                          user_id              UUID NOT NULL,
                          period_date          DATE NOT NULL,
                          base_salary_at_date  DOUBLE PRECISION NOT NULL,
                          total_overtime_hours DOUBLE PRECISION DEFAULT 0,
                          total_absence_days   DOUBLE PRECISION DEFAULT 0,
                          gross_salary         DOUBLE PRECISION NOT NULL,
                          net_salary           DOUBLE PRECISION NOT NULL,
                          is_validated         BOOLEAN DEFAULT FALSE, --by RH to check if ther's any errors in chifres
                          generated_at         TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP,
                          CONSTRAINT fk_payroll_user FOREIGN KEY (user_id) REFERENCES users (id)
);

