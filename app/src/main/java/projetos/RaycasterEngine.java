package projetos;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;

public class RaycasterEngine {

    private long window;
    private Player player;
    private Map map;
    private Raycaster raycaster;

    public static void main(String[] args) {
        new RaycasterEngine().run();
    }

    /**
     * Initializes GLFW, creates the window, and starts the game loop.
     */
    public void run() {
        if (!GLFW.glfwInit()) {
            throw new IllegalStateException("Unable to initialize GLFW");
        }

        GLFW.glfwWindowHint(GLFW.GLFW_DECORATED, GLFW.GLFW_FALSE); // Remove window decorations
        GLFWVidMode vidMode = GLFW.glfwGetVideoMode(GLFW.glfwGetPrimaryMonitor());
        window = GLFW.glfwCreateWindow(vidMode.width(), vidMode.height(), "Raycaster Engine", GLFW.glfwGetPrimaryMonitor(), 0);
        if (window == 0) {
            throw new RuntimeException("Failed to create the GLFW window");
        }

        GLFW.glfwMakeContextCurrent(window);
        GL.createCapabilities();

        // Hide the cursor and capture it
        GLFW.glfwSetInputMode(window, GLFW.GLFW_CURSOR, GLFW.GLFW_CURSOR_DISABLED);

        init();

        while (!GLFW.glfwWindowShouldClose(window)) {
            update();
            render();
            GLFW.glfwSwapBuffers(window);
            GLFW.glfwPollEvents();
        }

        GLFW.glfwDestroyWindow(window);
        GLFW.glfwTerminate();
    }

    /**
     * Initializes OpenGL settings and game objects.
     */
    private void init() {
        GL11.glClearColor(0.3f, 0.3f, 0.3f, 0);
        GLFWVidMode videoMode = GLFW.glfwGetVideoMode(GLFW.glfwGetPrimaryMonitor());
        GL11.glViewport(0, 0, videoMode.width(), videoMode.height());
        GL11.glOrtho(0, videoMode.width(), videoMode.height(), 0, -1, 1);

        map = new Map(); // Initialize the map before using it
        player = new Player(3 * map.getMapS(), 3 * map.getMapS(), 0, map); // Pass the map to the player
        raycaster = new Raycaster(map, player);
    }

    /**
     * Updates game logic, including handling input.
     */
    private void update() {
        handleInput();
        // Other update logic
    }

    /**
     * Renders the game objects.
     */
    private void render() {
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
        raycaster.drawRays2D();
    }

    /**
     * Handles keyboard and mouse input.
     */
    private void handleInput() {
        // Keyboard input for movement
        if (GLFW.glfwGetKey(window, GLFW.GLFW_KEY_W) == GLFW.GLFW_PRESS) {
            player.moveForward();
        }
        if (GLFW.glfwGetKey(window, GLFW.GLFW_KEY_S) == GLFW.GLFW_PRESS) {
            player.moveBackward();
        }
        if (GLFW.glfwGetKey(window, GLFW.GLFW_KEY_A) == GLFW.GLFW_PRESS) {
            player.strafeLeft();
        }
        if (GLFW.glfwGetKey(window, GLFW.GLFW_KEY_D) == GLFW.GLFW_PRESS) {
            player.strafeRight();
        }

        // Quit the game when ESC is pressed
        if (GLFW.glfwGetKey(window, GLFW.GLFW_KEY_ESCAPE) == GLFW.GLFW_PRESS) {
            GLFW.glfwSetWindowShouldClose(window, true);
        }

        // Mouse input for looking around
        double[] mouseX = new double[1];
        double[] mouseY = new double[1];
        GLFW.glfwGetCursorPos(window, mouseX, mouseY);
        GLFWVidMode videoMode = GLFW.glfwGetVideoMode(GLFW.glfwGetPrimaryMonitor());
        double centerX = videoMode.width() / 2.0;
        double centerY = videoMode.height() / 2.0;
        player.lookAround(mouseX[0] - centerX, mouseY[0] - centerY);
        GLFW.glfwSetCursorPos(window, centerX, centerY); // Reset the cursor to the center of the window
    }
}