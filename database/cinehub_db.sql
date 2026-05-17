-- 1. Tạo Database và sử dụng nó
CREATE DATABASE IF NOT EXISTS cinehub_db;
USE cinehub_db;

-- 2. Bảng Users (Người dùng)
CREATE TABLE users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    email VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    avatar_url VARCHAR(500),
    role ENUM('USER', 'ADMIN') DEFAULT 'USER',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- 3. Bảng Movies (Phim)
CREATE TABLE movies (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    description TEXT,
    poster_url VARCHAR(500),
    backdrop_url VARCHAR(500),
    release_date DATE,
    duration INT, -- Độ dài phim tính bằng phút
    rating DECIMAL(3,1) DEFAULT 0.0,
    is_trending BOOLEAN DEFAULT FALSE,
    is_top_rated BOOLEAN DEFAULT FALSE,
    movie_type ENUM('movie', 'series', 'anime') DEFAULT 'movie',
    status ENUM('released', 'upcoming', 'ongoing') DEFAULT 'released',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- 4. Bảng Genres (Thể loại)
CREATE TABLE genres (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50) NOT NULL UNIQUE,
    slug VARCHAR(50) NOT NULL UNIQUE
);

-- 5. Bảng Movie_Genres (Bảng trung gian n-n giữa Phim và Thể loại)
CREATE TABLE movie_genres (
    movie_id BIGINT,
    genre_id INT,
    PRIMARY KEY (movie_id, genre_id),
    FOREIGN KEY (movie_id) REFERENCES movies(id) ON DELETE CASCADE,
    FOREIGN KEY (genre_id) REFERENCES genres(id) ON DELETE CASCADE
);

-- 6. Bảng Episodes (Tập phim - dùng cho series hoặc anime)
CREATE TABLE episodes (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    movie_id BIGINT,
    title VARCHAR(255),
    episode_number INT NOT NULL,
    video_url VARCHAR(500) NOT NULL,
    duration INT,
    thumbnail_url VARCHAR(500),
    FOREIGN KEY (movie_id) REFERENCES movies(id) ON DELETE CASCADE
);

-- 7. Bảng Reviews (Đánh giá & Bình luận của người dùng)
CREATE TABLE reviews (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT,
    movie_id BIGINT,
    content TEXT,
    rating INT CHECK (rating >= 1 AND rating <= 10),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    FOREIGN KEY (movie_id) REFERENCES movies(id) ON DELETE CASCADE
);

-- 8. Bảng Favorites (Danh sách phim yêu thích)
CREATE TABLE favorites (
    user_id BIGINT,
    movie_id BIGINT,
    added_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (user_id, movie_id),
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    FOREIGN KEY (movie_id) REFERENCES movies(id) ON DELETE CASCADE
);

-- 9. Bảng Watch_History (Lịch sử xem phim để xem tiếp đoạn đang dừng)
CREATE TABLE watch_history (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT,
    movie_id BIGINT,
    episode_id BIGINT NULL,
    last_position INT DEFAULT 0, -- Lưu thời gian đang xem dở (giây)
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    FOREIGN KEY (movie_id) REFERENCES movies(id) ON DELETE CASCADE,
    FOREIGN KEY (episode_id) REFERENCES episodes(id) ON DELETE CASCADE
);

-- ==========================================
-- INSERT DỮ LIỆU MẪU (MOCK DATA) ĐỂ TEST FE
-- ==========================================

-- Thêm người dùng mẫu (mật khẩu đang để thô, sau này vào Java sẽ mã hóa)
INSERT INTO users (username, email, password, role)
VALUES ('admin', 'admin@cinehub.com', '123456', 'ADMIN'),
       ('quanzn', 'quan@cinehub.com', '123456', 'USER');

-- Thêm thể loại
INSERT INTO genres (name, slug)
VALUES ('Hành động', 'hanh-dong'),
       ('Viễn tưởng', 'vien-tuong'),
       ('Hoạt hình', 'hoat-hinh');

-- Thêm phim
INSERT INTO movies (title, description, poster_url, rating, is_trending, movie_type)
VALUES ('Inception', 'Kẻ đánh cắp giấc mơ', 'https://image.tmdb.org/t/p/w500/8Z8dptEQ9VKZDi1Xv5sT1hC2uQZ.jpg', 8.8, TRUE, 'movie'),
       ('Jujutsu Kaisen', 'Chú Thuật Hồi Chiến', 'https://image.tmdb.org/t/p/w500/1vKdE0FEfE52L2O5X8XvSvyxXq6.jpg', 9.0, TRUE, 'anime');

-- Gán thể loại cho phim
INSERT INTO movie_genres (movie_id, genre_id)
VALUES (1, 1), (1, 2), -- Inception: Hành động, Viễn tưởng
       (2, 1), (2, 3); -- JJK: Hành động, Hoạt hình

-- Thêm tập phim cho Jujutsu Kaisen
INSERT INTO episodes (movie_id, title, episode_number, video_url)
VALUES (2, 'Ryomen Sukuna', 1, 'https://example.com/jjk-ep1.mp4'),
       (2, 'Vì tôi ở đây', 2, '