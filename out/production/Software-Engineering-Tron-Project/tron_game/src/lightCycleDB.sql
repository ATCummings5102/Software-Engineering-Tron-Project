DROP TABLE IF EXISTS Games;
DROP TABLE IF EXISTS Players;

-- Players table
CREATE TABLE Players (
                         username VARCHAR(30) PRIMARY KEY,
                         password VARBINARY(16) NOT NULL,
                         total_wins INT DEFAULT 0,
                         join_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Games table
CREATE TABLE Games (
                       game_id INT AUTO_INCREMENT PRIMARY KEY,
                       start_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                       end_time TIMESTAMP NULL
);

/*
-- Test player data
INSERT INTO Players (username, password, total_wins, join_date)
VALUES
    ('player1', AES_ENCRYPT('password123', 'key'), 5, '2024-11-01 10:00:00'),
    ('player2', AES_ENCRYPT('securePass!@#', 'key'), 3, '2024-11-05 15:30:00'),
    ('player3', AES_ENCRYPT('mypassword', 'key'), 8, '2024-10-20 09:45:00'),
    ('player4', AES_ENCRYPT('admin2024', 'key'), 12, '2024-11-10 13:20:00');
*/