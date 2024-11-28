# Software-Engineering-Tron-Project

A Tron-style game made in Java using client-server communication to allow two players to compete against each other within a Java GUI environment.

## Table of Contents
- [Introduction](#introduction)
- [Features](#features)
- [Installation](#installation)
- [Usage](#usage)
- [License](#license)

## Introduction
This project is a Tron-style game developed as part of a software engineering course. The game is built in Java and features client-server communication to support multiplayer gameplay.

## Features
- Tron-style gameplay
- Java GUI for user interaction
- Client-server architecture
- Multiplayer support for two players

## Installation
1. Clone the repository:
    ```bash
    git clone https://github.com/ATCummings5102/Software-Engineering-Tron-Project.git
    ```
2. Navigate to the project directory:
    ```bash
    cd Software-Engineering-Tron-Project
    ```
3. Compile the project:
    ```bash
    javac -d bin src/**/*.java
    ```
4. Run the server:
    ```bash
    java -cp bin com.example.tron.server.Server
    ```
5. Run the client (in a separate terminal or machine):
    ```bash
    java -cp bin com.example.tron.client.Client
    ```

## Usage
- Start the server and wait for clients to connect.
- Run the client application and connect to the server using the server's IP address.
- Enjoy the game!


## License
This project is licensed under the MIT License. See the [LICENSE](LICENSE) file for details.
