DELETE FROM is_maried;
DELETE FROM family_members;
DELETE FROM users;

SET datestyle = 'European';

INSERT INTO users (id, email, password,role) VALUES
    (1, 'bernard@pm.me', 'password1','USER'),
    (2, 'alice@example.com', 'password2','USER'),
    (3, 'john@gmail.com', 'password3','USER'),
    (4, 'emma@yahoo.com', 'password4','USER'),
    (5, 'charlie@hotmail.com', 'password5','USER'),
    (6, 'sophie@outlook.com', 'password6','USER'),
    (7, 'david@gmail.com', 'password7','USER'),
    (8, 'olivia@yahoo.com', 'password8','USER'),
    (9, 'james@hotmail.com', 'password9','USER'),
    (10, 'lily@outlook.com', 'password10','USER');

INSERT INTO family_members (id, birth_day, death_day, first_name, last_name, social_security_number) VALUES
    (1, '15-05-1990', NULL, 'Bernard', 'Doe', '9012345678'),
    (2, '02-12-1985', '20-07-2022', 'Alice', 'Smith', '0123456789'),
    (3, '08-09-1970', NULL, 'John', 'Johnson', '9876543210'),
    (4, '25-03-1988', '10-11-2021', 'Emma', 'Miller', '1234567890'),
    (5, '18-11-1975', NULL, 'Charlie', 'Brown', '5678901234'),
    (6, '30-06-1982', '05-04-2020', 'Sophie', 'Taylor', '2345678901'),
    (7, '14-02-1995', NULL, 'David', 'Williams', '7890123456'),
    (8, '12-07-1980', '15-01-2023', 'Olivia', 'Clark', '4567890123'),
    (9, '03-04-1978', NULL, 'James', 'Davis', '8901234567'),
    (10, '22-10-1992', NULL, 'Lily', 'Moore', '3456789012');

INSERT INTO family_members (id, birth_day, death_day, first_name, last_name, social_security_number, mom_id, dad_id,status) VALUES
                 (11, '15-05-1990', NULL, 'Michael', 'Johnson', '9012345678', 12, 13,'PROTECTED'),
                 (12, '02-12-1985', '20-07-2022', 'Sarah', 'Anderson', '0123456789', NULL, NULL,'PROTECTED'),
                 (13, '08-09-1970', NULL, 'Robert', 'Smith', '9876543210', NULL, NULL,'PROTECTED');






INSERT INTO is_maried(id,man_id,women_id) VALUES
    (1,9,10);