-- Tạo bảng city
CREATE TABLE city
(
    id   SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL
);

-- Tạo bảng measurement
CREATE TABLE measurement
(
    id               SERIAL PRIMARY KEY,
    temperature      DOUBLE PRECISION NOT NULL,
    measurement_time DATE             NOT NULL,
    delete_flg       BOOLEAN          NOT NULL,
    city_id          BIGINT,
    FOREIGN KEY (city_id) REFERENCES city (id)
);
