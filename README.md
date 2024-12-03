# Tron Game Multiplayer Project

Welcome to the **Tron Game Multiplayer Project**, a modern adaptation of the classic Tron game with a multiplayer focus. The game allows players to compete in a grid arena, leaving trails behind them as they move, with the goal of avoiding collisions and outlasting their opponents.

---

## Features

- **Multiplayer Gameplay**: Supports multiple players with real-time interactions.
- **Dynamic Arena**: Tracks player movements, trails, and collisions in real-time.
- **Scoreboard**: Displays player scores and updates dynamically as the game progresses.
- **Chat Integration**: Players can communicate via an in-game chat system.
- **Server-Client Architecture**: Powered by a robust backend for handling game state and client interactions.
- **User Authentication**: Secure login and account creation with validation.

---

## Class Structure

The project is implemented in Java and follows an object-oriented design pattern with the following main classes:

- **Arena**: Manages the game arena and player trails.
- **Player**: Represents individual players and their states.
- **GameController**: Orchestrates game logic, including movements, collisions, and scoring.
- **TronServer**: Backend server that handles player connections and game data.
- **Database**: Manages user authentication and leaderboard data.
- **ClientGUI**: Provides a user-friendly interface for players.

For a detailed overview, see the [UML Diagram](docs/UML.md).

---

## Installation

### Prerequisites

Ensure you have the following installed:

- **Java Development Kit (JDK)** 17 or later
- **Maven** (optional for dependency management)
- **MySQL** or any compatible relational database

### Steps

1. **Clone the Repository**:
   ```bash
   git clone https://github.com/yourusername/tron-multiplayer.git
   cd tron-multiplayer
2. **Run SQL File:**

- Navigate to the scripts directory in the project folder.
- Locate the database.sql file.
- Open your MySQL client (e.g., MySQL Workbench or command line).
- Execute the following commands to create and populate the database:
-- sql
-- Copy code
-- SOURCE /path/to/scripts/database.sql;
-- Replace /path/to/scripts/ with the path to your database.sql file.

3. **Run BAT Files:**

- Locate the .bat files for running the server and client:
-- TronGameServer.bat
-- TronGameClient.bat
- Double-click each .bat file to launch the server and client.
- When prompted, ensure you click "Allow Access" to grant secure access through your system's firewall for both the server and client.
4. **Usage:**
- Start the Server: Run run-server.bat to initialize the server and wait for the server to display "Server is running...".
- Connect Clients: Use run-client.bat to launch the client application. Players can log in or create accounts to join the game.
5. **Gameplay:**
- Move around the grid using arrow keys.
- Avoid collisions with trails (yours and others).
- Accumulate points by outlasting your opponents.
6. **Troubleshooting:**
- Firewall Issues: If you experience connection issues, check your system firewall settings and allow access to the Java runtime for both server and client applications.
7. **Database Connection:** 
-Ensure the database server is running and the credentials in db.properties are correctly configured.
