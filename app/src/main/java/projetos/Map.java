package projetos;

import org.lwjgl.opengl.GL11;

public class Map {

    private final int mapX = 8, mapY = 8, mapS = 64;
    private final int[] map = {
            1, 1, 1, 1, 1, 1, 1, 1,
            1, 0, 0, 0, 0, 0, 0, 1,
            1, 0, 0, 0, 0, 1, 1, 1,
            1, 0, 1, 0, 0, 0, 0, 1,
            1, 0, 1, 1, 0, 0, 0, 1,
            1, 0, 0, 0, 0, 1, 0, 1,
            1, 0, 0, 0, 0, 0, 0, 1,
            1, 1, 1, 1, 1, 1, 1, 1
    };

    /**
     * Checks if the given coordinates are within the map bounds.
     * @param mx The x-coordinate to check.
     * @param my The y-coordinate to check.
     * @return True if the coordinates are within bounds, false otherwise.
     */
    public boolean inBounds(int mx, int my) {
        return mx >= 0 && mx < mapX && my >= 0 && my < mapY;
    }

    /**
     * Checks if the given position is a wall.
     * @param x The x-coordinate to check.
     * @param y The y-coordinate to check.
     * @return True if the position is a wall, false otherwise.
     */
    public boolean isWall(float x, float y) {
        int mx = (int) x / mapS;
        int my = (int) y / mapS;
        return map[my * mapX + mx] == 1;
    }

    public int getMapX() {
        return mapX;
    }

    public int getMapY() {
        return mapY;
    }

    public int getMapS() {
        return mapS;
    }

    public int[] getMap() {
        return map;
    }
}