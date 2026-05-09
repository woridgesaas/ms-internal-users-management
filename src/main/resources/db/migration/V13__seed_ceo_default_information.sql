-- Default department for CEO onboarding
INSERT INTO departments (id, name, status)
VALUES (gen_random_uuid(), 'Direction générale', 'ACTIVE');

-- Default post for CEO onboarding
INSERT INTO posts (id, name, status, base_salary)
VALUES (gen_random_uuid(), 'Directeur général', 'ACTIVE', 0.00);