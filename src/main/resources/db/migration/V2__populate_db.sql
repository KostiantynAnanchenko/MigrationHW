INSERT INTO worker (NAME, BIRTHDAY, LEVEL, SALARY)
VALUES 
    ('John Doe', '1990-05-15', 'Trainee', 800),
    ('Jane Smith', '1985-10-20', 'Junior', 1200),
    ('Michael Johnson', '1982-03-08', 'Middle', 2500),
    ('Emily Williams', '1978-12-30', 'Senior', 5000),
    ('David Brown', '1992-07-25', 'Trainee', 900),
    ('Jessica Taylor', '1989-04-18', 'Junior', 1300),
    ('Daniel Miller', '1983-11-10', 'Middle', 2700),
    ('Emma Martinez', '1975-09-05', 'Senior', 5500),
    ('Christopher Garcia', '1995-02-28', 'Trainee', 950),
    ('Sarah Lopez', '1987-06-12', 'Junior', 1400);


INSERT INTO client (NAME)
VALUES 
    ('Client A'),
    ('Client B'),
    ('Client C'),
    ('Client D'),
    ('Client E');


INSERT INTO project (CLIENT_ID, START_DATE, FINISH_DATE)
SELECT 
    (SELECT ID FROM client ORDER BY random() LIMIT 1),
    CURRENT_DATE - INTERVAL '1 month' * (random() * 100),
    CURRENT_DATE + INTERVAL '1 month' * (random() * 100)
FROM generate_series(1, 10);


INSERT INTO project_worker (PROJECT_ID, WORKER_ID)
SELECT 
    p.ID,
    w.ID
FROM project p
CROSS JOIN LATERAL (
    SELECT ID FROM worker ORDER BY random() LIMIT random() * 5 + 1
) w;