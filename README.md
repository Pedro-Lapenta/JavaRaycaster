# Java Raycaster

This project is a simple raycasting engine implemented in Java using LWJGL (Lightweight Java Game Library). It demonstrates basic 3D rendering techniques using raycasting, a method used in early 3D games like Wolfenstein 3D.

## Features

- Basic 3D rendering using raycasting
- Player movement and collision detection
- Mouse look for horizontal and vertical view control
- Adjustable shading for walls based on distance

## Prerequisites

- Java 22 or higher
- Gradle
- LWJGL 3.2.3

## Setup

1. Clone the repository:
    ```sh
    git clone https://github.com/Pedro-Lapenta/Java_Raycaster.git
    cd Java_Raycaster
    ```

2. Build the project using Gradle:
    ```sh
    ./gradlew build
    ```

3. Run the application:
    ```sh
    ./gradlew run
    ```

## Controls

- `W` - Move forward
- `S` - Move backward
- `A` - Strafe left
- `D` - Strafe right
- `Mouse` - Look around
- `ESC` - Quit the game

## Project Structure

- `src/main/java/projetos/` - Contains the main Java source files
  - `RaycasterEngine.java` - Main class to initialize and run the game
  - `Player.java` - Handles player movement and input
  - `Map.java` - Represents the game map and handles collision detection
  - `Raycaster.java` - Handles the raycasting logic and rendering
- `src/test/java/projetos/` - Contains the test files
  - `RaycasterEngineTest.java` - Unit tests for the project

## Dependencies

- LWJGL (Lightweight Java Game Library) for OpenGL rendering
- JUnit Jupiter for testing

## License

This project is licensed under the MIT License. See the [LICENSE](LICENSE) file for details.

## Acknowledgements

- LWJGL for providing the Java bindings for OpenGL
- The creators of early 3D games like Wolfenstein 3D for inspiring this project