
-- A PARTIR DE AQUI SE EMPIEZAN A CREAR LAS TABLAS
-- Tabla para los roles
CREATE TABLE roles(
                      id SERIAL PRIMARY KEY,
                      name VARCHAR(50) NOT NULL
);
-- Tabla para los usuarios, aqui se guardan todos y cada
-- uno de los usuarios que se registren
CREATE TABLE users (
                       id SERIAL PRIMARY KEY,
                       name VARCHAR(100) NOT NULL,
                       email VARCHAR(100) NOT NULL UNIQUE,
                       password VARCHAR(255) NOT NULL,
                       phone_number VARCHAR(20) NOT NULL,
                       birth_date DATE NOT NULL,
                       profile_img TEXT,
                       personal_description TEXT,
                       created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                       updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                       role_id BIGINT,

                       CONSTRAINT fk_user_role FOREIGN KEY (role_id)
                           REFERENCES roles(id)
                           ON DELETE SET NULL
                           ON UPDATE CASCADE
);

-- Tabla para los alojamientos
CREATE TABLE accommodations(

                               id SERIAL PRIMARY KEY,
                               name VARCHAR(100) NOT NULL,
                               description TEXT,
                               price DECIMAL(10,2) NOT NULL CHECK (price >= 0),
                               max_capacity INT NOT NULL CHECK (max_capacity > 0),
                               is_available BOOLEAN DEFAULT TRUE,
                               owner_id INT REFERENCES users(id) ON DELETE CASCADE,
                               created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                               updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP

);

-- Tabla para las reservas
CREATE TABLE bookings (

                          id SERIAL PRIMARY KEY,
                          accommodation_id INT REFERENCES accommodations(id) ON DELETE CASCADE,
                          user_id INT REFERENCES users(id) ON DELETE CASCADE,
                          date_check_in DATE NOT NULL,
                          date_check_out DATE NOT NULL,
                          CHECK (date_check_out > date_check_in),
                          guests_number INT NOT NULL CHECK (guests_number>0),
                          status VARCHAR(20) NOT NULL DEFAULT 'PENDING',
                          created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                          updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP

);

-- En esta tabla se guardan todas las reservas
CREATE TABLE reviews(

                        id SERIAL PRIMARY KEY,
                        accommodation_id INT REFERENCES accommodations(id) ON DELETE CASCADE,
                        user_id INT REFERENCES users(id) ON DELETE CASCADE,
                        rate INT NOT NULL CHECK (rate BETWEEN 1 AND 5),
                        comment TEXT NOT NULL,
                        created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                        updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP

);


-- Tabla intermedia que conecta a los roles con el usuario al que pertenecen
--CREATE TABLE user_roles(

--	user_id INT REFERENCES users(id) ON DELETE CASCADE,
--	role_id INT REFERENCES roles(id) ON DELETE CASCADE,
--	PRIMARY KEY (user_id, role_id)

--);

-- Tabla para los privilegios de cada rol
CREATE TABLE privileges(

                           id SERIAL PRIMARY KEY,
                           name VARCHAR(50) NOT NULL

);

-- Tabla que conecta los privilegios con cada rol al que pertenecen
CREATE TABLE role_privileges(

                                role_id INT REFERENCES roles(id) ON DELETE CASCADE,
                                privilege_id INT REFERENCES privileges(id) ON DELETE CASCADE,
                                PRIMARY KEY (privilege_id, role_id)

);

-- Tabla para los servicios de un alojamiento
CREATE TABLE services(

                         id SERIAL PRIMARY KEY,
                         name VARCHAR(50) NOT NULL

);

-- Tabla intermedia que conecta a los servicios con el alojamiento que los ofrece
CREATE TABLE accommodation_services(

                                       service_id INT REFERENCES services(id) ON DELETE CASCADE,
                                       accommodation_id INT REFERENCES accommodations(id) ON DELETE CASCADE,
                                       PRIMARY KEY (accommodation_id, service_id)

);

-- Tabla para las ubicaciones, decidi hacerl a aparte para un mejor control a la hora de filtrar
CREATE TABLE locations (

                           id SERIAL PRIMARY KEY,
                           city VARCHAR(100),
                           department VARCHAR(100),
                           latitude DECIMAL(9,6),
                           longitude DECIMAL(9,6),
                           address VARCHAR(255),
                           accommodation_id INT REFERENCES accommodations(id) ON DELETE CASCADE

);

-- Tabla unicamente para las imagenes de los alojamientos, se guarda la url
CREATE TABLE accommodation_images(

                                     id SERIAL PRIMARY KEY,
                                     accommodation_id INT REFERENCES accommodations(id) ON DELETE CASCADE,
                                     image_url TEXT

);

-- A PARTIR DE AQUI:****
-- por favor no tocar el orden ya que luego
-- seteamos la tabla role_privileges segun
-- el orden de insersion de esta tabla
--**

-- aqui se inserta los roles que se usaran
INSERT INTO roles (name) VALUES ('USER'); --1
INSERT INTO roles (name) VALUES ('HOST'); --2

-- aqui se crean los diferentes privilegios
INSERT INTO privileges (name) VALUES
                                  ('CREATE_BOOKING'), 		--1
                                  ('CREATE_REVIEW'), 			--2
                                  ('CREATE_ACCOMMODATION'), 	--3
                                  ('VIEW_BOOKINGS'), 			--4
                                  ('VIEW_REVIEWS'), 			--5
                                  ('VIEW_STATISTICS'), 		--6
                                  ('VIEW_ACCOMMODATIONS'), 	--7
                                  ('EDIT_ACCOMMODATION'), 	--8
                                  ('EDIT_USER'),				--9
                                  ('DELETE_ACCOMMODATION'), 	--10
                                  ('CANCEL_BOOKING'), 		--11
                                  ('MANAGE_OWN_PROFILE'); 	--12

-- aqui asignamos los privilegios a los roles que
-- corresponde

-- PARA EL ROL USER
INSERT INTO role_privileges (role_id, privilege_id) VALUES
                                                        (1, 1), -- CREATE_BOOKING
                                                        (1, 2), -- CREATE_REVIEW
                                                        (1, 4), -- VIEW_BOOKINGS
                                                        (1, 5), -- VIEW_REVIEWS
                                                        (1, 9), -- EDIT_USER
                                                        (1, 11), -- CANCEL_BOOKING
                                                        (1, 12); -- MANAGE_OWN_PROFILE


-- PARA EL ROL HOST
INSERT INTO role_privileges (role_id, privilege_id) VALUES
                                                        (2, 3), -- CREATE_ACCOMMODATION
                                                        (2, 4), -- 	VIEW_BOOKINGS
                                                        (2, 5), -- VIEW_REVIEWS
                                                        (2, 6),  -- VIEW_STATISTICS
                                                        (2, 7),  -- VIEW_ACCOMMODATIONS
                                                        (2, 8), -- EDIT_ACCOMMODATION
                                                        (2, 10), -- DELETE_ACCOMMODATION
                                                        (2, 11), -- CANCEL_BOOKING
                                                        (2, 12); -- MANAGE_OWN_PROFILE

-- aqui insertamos los services por defecto que los accommodations pueden tener
INSERT INTO services (name) VALUES
                                ('Wi-Fi gratuito'),
                                ('Aire acondicionado'),
                                ('Televisión por cable'),
                                ('Desayuno incluido'),
                                ('Piscina'),
                                ('Gimnasio'),
                                ('Estacionamiento'),
                                ('Mascotas permitidas'),
                                ('Servicio de limpieza diaria'),
                                ('Recepción 24 horas');


