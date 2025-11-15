-- A PARTIR DE AQUI SE EMPIEZAN A CREAR LAS TABLAS
-- Tabla para los roles
CREATE TABLE IF NOT EXISTS roles(
                                    id SERIAL PRIMARY KEY,
                                    name VARCHAR(50) NOT NULL
    );

-- Tabla para los usuarios, aqui se guardan todos y cada
-- uno de los usuarios que se registren
CREATE TABLE IF NOT EXISTS users (
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
CREATE TABLE IF NOT EXISTS accommodations(
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
CREATE TABLE IF NOT EXISTS bookings (
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
CREATE TABLE IF NOT EXISTS reviews(
                                      id SERIAL PRIMARY KEY,
                                      accommodation_id INT REFERENCES accommodations(id) ON DELETE CASCADE,
    user_id INT REFERENCES users(id) ON DELETE CASCADE,
    rate INT NOT NULL CHECK (rate BETWEEN 1 AND 5),
    comment TEXT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
    );

-- Tabla para los privilegios de cada rol
CREATE TABLE IF NOT EXISTS privileges(
                                         id SERIAL PRIMARY KEY,
                                         name VARCHAR(50) NOT NULL
    );

-- Tabla que conecta los privilegios con cada rol al que pertenecen
CREATE TABLE IF NOT EXISTS role_privileges(
                                              role_id INT REFERENCES roles(id) ON DELETE CASCADE,
    privilege_id INT REFERENCES privileges(id) ON DELETE CASCADE,
    PRIMARY KEY (privilege_id, role_id)
    );

-- Tabla para los servicios de un alojamiento
CREATE TABLE IF NOT EXISTS services(
                                       id SERIAL PRIMARY KEY,
                                       name VARCHAR(50) NOT NULL
    );

-- Tabla intermedia que conecta a los servicios con el alojamiento que los ofrece
CREATE TABLE IF NOT EXISTS accommodation_services(
                                                     service_id INT REFERENCES services(id) ON DELETE CASCADE,
    accommodation_id INT REFERENCES accommodations(id) ON DELETE CASCADE,
    PRIMARY KEY (accommodation_id, service_id)
    );

-- Tabla para las ubicaciones, decidi hacerl a aparte para un mejor control a la hora de filtrar
CREATE TABLE IF NOT EXISTS locations (
                                         id SERIAL PRIMARY KEY,
                                         city VARCHAR(100),
    department VARCHAR(100),
    latitude DECIMAL(9,6),
    longitude DECIMAL(9,6),
    address VARCHAR(255),
    accommodation_id INT REFERENCES accommodations(id) ON DELETE CASCADE
    );

-- Tabla unicamente para las imagenes de los alojamientos, se guarda la url
CREATE TABLE IF NOT EXISTS accommodation_images(
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
INSERT INTO roles (id, name) VALUES
                                 (1, 'USER'),
                                 (2, 'HOST')
    ON CONFLICT (id) DO NOTHING;

-- aqui se crean los diferentes privilegios
INSERT INTO privileges (id, name) VALUES
                                      (1, 'CREATE_BOOKING'),
                                      (2, 'CREATE_REVIEW'),
                                      (3, 'CREATE_ACCOMMODATION'),
                                      (4, 'VIEW_BOOKINGS'),
                                      (5, 'VIEW_REVIEWS'),
                                      (6, 'VIEW_STATISTICS'),
                                      (7, 'VIEW_ACCOMMODATIONS'),
                                      (8, 'EDIT_ACCOMMODATION'),
                                      (9, 'EDIT_USER'),
                                      (10, 'DELETE_ACCOMMODATION'),
                                      (11, 'CANCEL_BOOKING'),
                                      (12, 'MANAGE_OWN_PROFILE')
    ON CONFLICT (id) DO NOTHING;

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
                                                        (1, 12) -- MANAGE_OWN_PROFILE
    ON CONFLICT DO NOTHING;

-- PARA EL ROL HOST
INSERT INTO role_privileges (role_id, privilege_id) VALUES
                                                        (2, 3), -- CREATE_ACCOMMODATION
                                                        (2, 4), -- VIEW_BOOKINGS
                                                        (2, 5), -- VIEW_REVIEWS
                                                        (2, 6), -- VIEW_STATISTICS
                                                        (2, 7), -- VIEW_ACCOMMODATIONS
                                                        (2, 8), -- EDIT_ACCOMMODATION
                                                        (2, 10), -- DELETE_ACCOMMODATION
                                                        (2, 11), -- CANCEL_BOOKING
                                                        (2, 12) -- MANAGE_OWN_PROFILE
    ON CONFLICT DO NOTHING;

-- aqui insertamos los services por defecto que los accommodations pueden tener
INSERT INTO services (id, name) VALUES
                                    (1, 'Wi-Fi gratuito'),
                                    (2, 'Aire acondicionado'),
                                    (3, 'Televisión por cable'),
                                    (4, 'Desayuno incluido'),
                                    (5, 'Piscina'),
                                    (6, 'Gimnasio'),
                                    (7, 'Estacionamiento'),
                                    (8, 'Mascotas permitidas'),
                                    (9, 'Servicio de limpieza diaria'),
                                    (10, 'Recepción 24 horas')
    ON CONFLICT (id) DO NOTHING;