DROP TABLE IF EXISTS Leaderboard;
DROP TABLE IF EXISTS PlayerStats;
DROP TABLE IF EXISTS Games;
DROP TABLE IF EXISTS Players;

CREATE TABLE Players (
                         username VARCHAR(30),
                         password VARBINARY(16) NOT NULL,
                         join_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP, -- For debugging purposes for now.
                         player_id INT NOT NULL AUTO_INCREMENT,
                         PRIMARY KEY(player_id, username)
);


CREATE TABLE Games (
                       game_id INT AUTO_INCREMENT PRIMARY KEY,

                       start_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP, -- For debugging purposes for now.
                       end_time TIMESTAMP NULL -- For debugging purposes for now.
);

-- Decided to seperate it from Players so that future updates to the table arent tied to the player account (just a seperation of concerns and easier to query IMO).
CREATE TABLE PlayerStats (
                             stats_id INT AUTO_INCREMENT PRIMARY KEY,
                             game_id INT NOT NULL,
                             player_id INT NOT NULL,
                             curr_score INT DEFAULT 0,
                             total_wins INT DEFAULT 0
);


CREATE TABLE Leaderboard (
                             leaderboard_id INT AUTO_INCREMENT PRIMARY KEY,
                             player_id INT NOT NULL,
                             high_score INT NOT NULL,
                             last_updated TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP -- Expierimental but could be nice to show when someone wins a game.
);

ALTER TABLE PlayerStats ADD CONSTRAINT fk_playerstats_game FOREIGN KEY (game_id) REFERENCES Games(game_id);

ALTER TABLE PlayerStats ADD CONSTRAINT fk_playerstats_player FOREIGN KEY (player_id) REFERENCES Players(player_id);
