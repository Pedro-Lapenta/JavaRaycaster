package projetos;

import org.lwjgl.glfw.GLFW;
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

    public void run() {
        if (!GLFW.glfwInit()) {
            throw new IllegalStateException("Unable to initialize GLFW");
        }

        window = GLFW.glfwCreateWindow(1024, 512, "Raycaster Engine", 0, 0);
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

    private void init() {
        GL11.glClearColor(0.3f, 0.3f, 0.3f, 0);
        GL11.glOrtho(0, 1024, 512, 0, -1, 1);

        player = new Player(300, 300, 0);
        map = new Map();
        raycaster = new Raycaster(map, player);
    }

    private void update() {
        handleInput();
        // Other update logic
    }

    private void render() {
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
        map.drawMap2D(player);
        raycaster.drawRays2D();
        player.drawPlayer();
    }

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

        // Mouse input for looking around
        double[] mouseX = new double[1];
        double[] mouseY = new double[1];
        GLFW.glfwGetCursorPos(window, mouseX, mouseY);
        player.lookAround(mouseX[0] - 512);
        GLFW.glfwSetCursorPos(window, 512, 256); // Reset the cursor to the center of the window
    }
}