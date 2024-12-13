package projetos;

import org.lwjgl.opengl.GL11;

public class Player {

    public static final float PI = 3.1415926535f; // Make PI public
    private static final float P2 = PI / 2;
    private float px, py; // Player position
    private float pdx, pdy; // Player delta movement
    private float pa; // Player angle
    private float pitch = 0; // Player vertical angle

    private float moveSpeed = 1f;
    private float turnSpeed = 0.0005f; // Lowered for less sensitivity

    private Map map;

    public Player(float px, float py, float pa, Map map) {
        this.px = px;
        this.py = py;
        this.pa = pa;
        this.map = map;
        this.pdx = (float) Math.cos(pa) * 5;
        this.pdy = (float) Math.sin(pa) * 5;
    }

    /**
     * Moves the player forward.
     */
    public void moveForward() {
        float newPosX = px + (float) Math.cos(pa) * moveSpeed;
        float newPosY = py + (float) Math.sin(pa) * moveSpeed;
        if (!map.isWall(newPosX, py)) {
            px = newPosX;
        }
        if (!map.isWall(px, newPosY)) {
            py = newPosY;
        }
    }

    /**
     * Moves the player backward.
     */
    public void moveBackward() {
        float newPosX = px - (float) Math.cos(pa) * moveSpeed;
        float newPosY = py - (float) Math.sin(pa) * moveSpeed;
        if (!map.isWall(newPosX, py)) {
            px = newPosX;
        }
        if (!map.isWall(px, newPosY)) {
            py = newPosY;
        }
    }

    /**
     * Strafes the player to the left.
     */
    public void strafeLeft() {
        float newPosX = px + (float) Math.cos(pa - P2) * moveSpeed;
        float newPosY = py + (float) Math.sin(pa - P2) * moveSpeed;
        if (!map.isWall(newPosX, py)) {
            px = newPosX;
        }
        if (!map.isWall(px, newPosY)) {
            py = newPosY;
        }
    }

    /**
     * Strafes the player to the right.
     */
    public void strafeRight() {
        float newPosX = px + (float) Math.cos(pa + P2) * moveSpeed;
        float newPosY = py + (float) Math.sin(pa + P2) * moveSpeed;
        if (!map.isWall(newPosX, py)) {
            px = newPosX;
        }
        if (!map.isWall(px, newPosY)) {
            py = newPosY;
        }
    }

    /**
     * Updates the player's angle based on mouse movement.
     * @param mouseX The x-coordinate of the mouse.
     * @param mouseY The y-coordinate of the mouse.
     */
    public void lookAround(double mouseX, double mouseY) {
        pa += mouseX * turnSpeed;
        pitch -= mouseY * turnSpeed; // Invert mouseY for natural look up/down
        pitch = Math.max(-PI / 2, Math.min(PI / 2, pitch)); // Clamp pitch to avoid flipping
        pdx = (float) Math.cos(pa) * 5;
        pdy = (float) Math.sin(pa) * 5;
    }

    /**
     * Draws the player on the screen.
     */
    public void drawPlayer() {
        GL11.glColor3f(1, 1, 0);
        GL11.glPointSize(8);
        GL11.glBegin(GL11.GL_POINTS);
        GL11.glVertex2i((int) px, (int) py);
        GL11.glEnd();

        GL11.glLineWidth(3);
        GL11.glBegin(GL11.GL_LINES);
        GL11.glVertex2i((int) px, (int) py);
        GL11.glVertex2i((int) (px + pdx * 5), (int) (py + pdy * 5));
        GL11.glEnd();
    }

    // Getters for player position and angle
    public float getPx() {
        return px;
    }

    public float getPy() {
        return py;
    }

    public float getPa() {
        return pa;
    }

    // Getter for player pitch
    public float getPitch() {
        return pitch;
    }
}