CREATE OR REPLACE FUNCTION create_salary_after_user()
RETURNS TRIGGER AS $$
BEGIN
    INSERT INTO salary_history
    (id, user_id, salary, post_name, valid_from, valid_to)
    VALUES
    (
        gen_random_uuid(),
        NEW.id,
        (SELECT base_salary FROM posts WHERE id = NEW.post_id),
        (SELECT name FROM posts WHERE id = NEW.post_id),
        CURRENT_DATE,
        NULL
    );

    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trigger_create_salary
AFTER INSERT ON users
FOR EACH ROW
EXECUTE FUNCTION create_salary_after_user();
SELECT previous_salary FROM promotions;
