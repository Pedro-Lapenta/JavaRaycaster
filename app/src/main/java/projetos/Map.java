package projetos;

import org.lwjgl.opengl.GL11;

public class Map {

    private final int mapX = 8, mapY = 8, mapS = 64;
    private final int[] map = {
            1, 1, 1, 1, 1, 1, 1, 1,
            1, 0, 1, 0, 0, 0, 0, 1,
            1, 0, 1, 0, 0, 0, 0, 1,
            1, 0, 1, 0, 0, 0, 0, 1,
            1, 0, 0, 0, 0, 0, 0, 1,
            1, 0, 0, 0, 0, 1, 0, 1,
            1, 0, 0, 0, 0, 0, 0, 1,
            1, 1, 1, 1, 1, 1, 1, 1
    };

    public void drawMap2D(Player player) {
        for (int y = 0; y < mapY; y++) {
            for (int x = 0; x < mapX; x++) {
                if (map[y * mapX + x] == 1) {
                    GL11.glColor3f(1, 1, 1);
                } else {
                    GL11.glColor3f(0, 0, 0);
                }

                int xo = x * mapS;
                int yo = y * mapS;

                GL11.glBegin(GL11.GL_QUADS);
                GL11.glVertex2i(xo + 1, yo + 1);
                GL11.glVertex2i(xo + 1, yo + mapS - 1);
                GL11.glVertex2i(xo + mapS - 1, yo + mapS - 1);
                GL11.glVertex2i(xo + mapS - 1, yo + 1);
                GL11.glEnd();
            }
        }
        player.drawPlayerOnMap(mapS);
    }

    public boolean inBounds(int mx, int my) {
        return mx >= 0 && mx < mapX && my >= 0 && my < mapY;
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