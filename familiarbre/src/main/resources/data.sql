DELETE FROM users;
DELETE FROM family_members;
SET datestyle = 'European';

INSERT INTO users (id, email, social_security_number, password) VALUES
    (1, 'bernard@pm.me', '0123456789', 'password1'),
    (2, 'alice@example.com', '9876543210', 'password2'),
    (3, 'john@gmail.com', '1234567890', 'password3'),
    (4, 'emma@yahoo.com', '5678901234', 'password4'),
    (5, 'charlie@hotmail.com', '2345678901', 'password5'),
    (6, 'sophie@outlook.com', '7890123456', 'password6'),
    (7, 'david@gmail.com', '4567890123', 'password7'),
    (8, 'olivia@yahoo.com', '8901234567', 'password8'),
    (9, 'james@hotmail.com', '3456789012', 'password9'),
    (10, 'lily@outlook.com', '9012345678', 'password10');

INSERT INTO family_members (id, birth_day, death_day, first_name, name) VALUES
    (1, '15-05-1990', NULL, 'Bernard', 'Doe'),
    (2, '02-12-1985', '20-07-2022', 'Alice', 'Smith'),
    (3, '08-09-1970', NULL, 'John', 'Johnson'),
    (4, '25-03-1988', '10-11-2021', 'Emma', 'Miller'),
    (5, '18-11-1975', NULL, 'Charlie', 'Brown'),
    (6, '30-06-1982', '05-04-2020', 'Sophie', 'Taylor'),
    (7, '14-02-1995', NULL, 'David', 'Williams'),
    (8, '12-07-1980', '15-01-2023', 'Olivia', 'Clark'),
    (9, '03-04-1978', NULL, 'James', 'Davis'),
    (10, '22-10-1992', NULL, 'Lily', 'Moore');
