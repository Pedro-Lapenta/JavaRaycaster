package projetos;

import org.lwjgl.opengl.GL11;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWVidMode;

public class Raycaster {

    private static final float PI = 3.1415926535f;
    private static final float P2 = PI / 2;
    private static final float P3 = 3 * PI / 2;
    private static final float DR = 0.0174533f; // 1 degree in radians

    private Map map;
    private Player player;

    public Raycaster(Map map, Player player) {
        this.map = map;
        this.player = player;
    }

    /**
     * Draws the rays for the 2D raycasting visualization.
     */
    public void drawRays2D() {
        float rayAngle = player.getPa() - DR * 30;
        if (rayAngle < 0) rayAngle += 2 * PI;
        if (rayAngle > 2 * PI) rayAngle -= 2 * PI;

        GL11.glLineWidth(12.0f); // Set the line width to 12.0

        GLFWVidMode vidMode = GLFW.glfwGetVideoMode(GLFW.glfwGetPrimaryMonitor());
        int screenWidth = vidMode.width();
        int screenHeight = vidMode.height();

        for (int ray = 0; ray < screenWidth; ray++) {
            float distanceToWall = castRay(rayAngle);

            float correctedAngle = player.getPa() - rayAngle;
            if (correctedAngle < 0) correctedAngle += 2 * PI;
            if (correctedAngle > 2 * PI) correctedAngle -= 2 * PI;
            distanceToWall *= Math.cos(correctedAngle);

            float lineHeight = (map.getMapS() * screenHeight) / distanceToWall;
            if (lineHeight > screenHeight) lineHeight = screenHeight;
            float lineOffset = screenHeight / 2 - lineHeight / 2;

            float shade = 1.0f / (1.0f + distanceToWall * 0.01f); // Adjusted shade calculation for lighter scene
            GL11.glColor3f(shade, 0, 0); // Apply shade to color

            GL11.glBegin(GL11.GL_LINES);
            GL11.glVertex2i(ray, (int) lineOffset);
            GL11.glVertex2i(ray, (int) (lineOffset + lineHeight));
            GL11.glEnd();

            rayAngle += DR / (screenWidth / 60.0f);
            if (rayAngle < 0) rayAngle += 2 * PI;
            if (rayAngle > 2 * PI) rayAngle -= 2 * PI;
        }
    }

    /**
     * Casts a ray and returns the distance to the nearest wall.
     * @param rayAngle The angle of the ray.
     * @return The distance to the nearest wall.
     */
    private float castRay(float rayAngle) {
        float distanceToWall = 1000000;

        // Horizontal check
        float horizontalX = 0, horizontalY = 0;
        float aTan = -1 / (float) Math.tan(rayAngle);
        int depthOfField = 0;

        if (rayAngle > PI) {
            horizontalY = (((int) player.getPy() >> 6) << 6) - 0.0001f;
            horizontalX = (player.getPy() - horizontalY) * aTan + player.getPx();
            float yOffset = -64, xOffset = -yOffset * aTan;
            while (depthOfField < 8) {
                int mapX = (int) (horizontalX) >> 6;
                int mapY = (int) (horizontalY) >> 6;
                if (map.inBounds(mapX, mapY) && map.getMap()[mapY * map.getMapX() + mapX] == 1) {
                    distanceToWall = dist(player.getPx(), player.getPy(), horizontalX, horizontalY);
                    break;
                }
                horizontalX += xOffset;
                horizontalY += yOffset;
                depthOfField++;
            }
        } else if (rayAngle < PI) {
            horizontalY = (((int) player.getPy() >> 6) << 6) + 64;
            horizontalX = (player.getPy() - horizontalY) * aTan + player.getPx();
            float yOffset = 64, xOffset = -yOffset * aTan;
            while (depthOfField < 8) {
                int mapX = (int) (horizontalX) >> 6;
                int mapY = (int) (horizontalY) >> 6;
                if (map.inBounds(mapX, mapY) && map.getMap()[mapY * map.getMapX() + mapX] == 1) {
                    distanceToWall = dist(player.getPx(), player.getPy(), horizontalX, horizontalY);
                    break;
                }
                horizontalX += xOffset;
                horizontalY += yOffset;
                depthOfField++;
            }
        }

        // Vertical check
        float verticalX = 0, verticalY = 0;
        float nTan = - (float) Math.tan(rayAngle);
        depthOfField = 0;

        if (rayAngle > P2 && rayAngle < P3) {
            verticalX = (((int) player.getPx() >> 6) << 6) - 0.0001f;
            verticalY = (player.getPx() - verticalX) * nTan + player.getPy();
            float xOffset = -64, yOffset = -xOffset * nTan;
            while (depthOfField < 8) {
                int mapX = (int) (verticalX) >> 6;
                int mapY = (int) (verticalY) >> 6;
                if (map.inBounds(mapX, mapY) && map.getMap()[mapY * map.getMapX() + mapX] == 1) {
                    float verticalDistance = dist(player.getPx(), player.getPy(), verticalX, verticalY);
                    if (verticalDistance < distanceToWall) distanceToWall = verticalDistance;
                    break;
                }
                verticalX += xOffset;
                verticalY += yOffset;
                depthOfField++;
            }
        } else if (rayAngle < P2 || rayAngle > P3) {
            verticalX = (((int) player.getPx() >> 6) << 6) + 64;
            verticalY = (player.getPx() - verticalX) * nTan + player.getPy();
            float xOffset = 64, yOffset = -xOffset * nTan;
            while (depthOfField < 8) {
                int mapX = (int) (verticalX) >> 6;
                int mapY = (int) (verticalY) >> 6;
                if (map.inBounds(mapX, mapY) && map.getMap()[mapY * map.getMapX() + mapX] == 1) {
                    float verticalDistance = dist(player.getPx(), player.getPy(), verticalX, verticalY);
                    if (verticalDistance < distanceToWall) distanceToWall = verticalDistance;
                    break;
                }
                verticalX += xOffset;
                verticalY += yOffset;
                depthOfField++;
            }
        }

        return distanceToWall;
    }

    /**
     * Calculates the distance between two points.
     * @param ax The x-coordinate of the first point.
     * @param ay The y-coordinate of the first point.
     * @param bx The x-coordinate of the second point.
     * @param by The y-coordinate of the second point.
     * @return The distance between the two points.
     */
    private float dist(float ax, float ay, float bx, float by) {
        return (float) Math.sqrt((bx - ax) * (bx - ax) + (by - ay) * (by - ay));
    }
}