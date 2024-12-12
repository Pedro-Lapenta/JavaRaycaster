package projetos;

import org.lwjgl.opengl.GL11;

public class Player {

    private static final float PI = 3.1415926535f;
    private static final float P2 = PI / 2;
    private float px, py; // Player position
    private float pdx, pdy; // Player delta movement
    private float pa; // Player angle

    private float moveSpeed = 1f;
    private float turnSpeed = 0.002f; // Adjusted for smoother turning

    public Player(float px, float py, float pa) {
        this.px = px;
        this.py = py;
        this.pa = pa;
        this.pdx = (float) Math.cos(pa) * 5;
        this.pdy = (float) Math.sin(pa) * 5;
    }

    public void moveForward() {
        px += Math.cos(pa) * moveSpeed;
        py += Math.sin(pa) * moveSpeed;
    }

    public void moveBackward() {
        px -= Math.cos(pa) * moveSpeed;
        py -= Math.sin(pa) * moveSpeed;
    }

    public void strafeLeft() {
        px += Math.cos(pa - P2) * moveSpeed;
        py += Math.sin(pa - P2) * moveSpeed;
    }

    public void strafeRight() {
        px += Math.cos(pa + P2) * moveSpeed;
        py += Math.sin(pa + P2) * moveSpeed;
    }

    public void lookAround(double mouseX) {
        pa += mouseX * turnSpeed;
        pdx = (float) Math.cos(pa) * 5;
        pdy = (float) Math.sin(pa) * 5;
    }

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

    public void drawPlayerOnMap(int mapScale) {
        GL11.glColor3f(1, 1, 0);
        GL11.glPointSize(8);
        GL11.glBegin(GL11.GL_POINTS);
        GL11.glVertex2i((int) (px / mapScale), (int) (py / mapScale));
        GL11.glEnd();

        GL11.glLineWidth(3);
        GL11.glBegin(GL11.GL_LINES);
        GL11.glVertex2i((int) (px / mapScale), (int) (py / mapScale));
        GL11.glVertex2i((int) ((px + pdx * 5) / mapScale), (int) ((py + pdy * 5) / mapScale));
        GL11.glEnd();
    }

    public float getPx() {
        return px;
    }

    public float getPy() {
        return py;
    }

    public float getPa() {
        return pa;
    }
}