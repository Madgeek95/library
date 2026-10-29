INSERT INTO rooms (room_number, capacity) VALUES
('101-A', 30),
('202-B', 25),
('303-C', 20);

INSERT INTO students (first_name, last_name, grade, room_id) VALUES
('Исмаил', 'Ибрагимов', 10, 1),
('Алишер', 'Навои', 11, 1),
('Сардор', 'Каримов', 9, 2);

INSERT INTO books (title, subject, student_id, issued_at) VALUES
('Алгебра и начала анализа 10-11', 'Математика', 1, CURRENT_TIMESTAMP),
('Физика: Механика и динамика', 'Физика', 1, CURRENT_TIMESTAMP),
('История Узбекистана', 'История', 2, CURRENT_TIMESTAMP),
('Органическая химия', 'Химия', NULL, NULL);
