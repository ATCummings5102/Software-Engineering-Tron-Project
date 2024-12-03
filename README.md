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
