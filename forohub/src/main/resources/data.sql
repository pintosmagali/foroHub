INSERT INTO users(email, password_hash, role) VALUES
 ('demo@alura.com', '$2a$10$2Jj9x2q4n0c3v3d8T0p8x.Dh1v8m4G7m3v2p9QyWm2s1n0vY8rIh6', 'USER');
-- password: demo123

INSERT INTO courses(name) VALUES ('Java y Spring'), ('SQL y JPA'), ('Estructuras de Datos');

INSERT INTO topics(title, message, created_at, status, author_id, course_id) VALUES
 ('¿Cómo configuro Spring Security?', 'No me autentica el login', CURRENT_TIMESTAMP, 'OPEN', 1, 1);
