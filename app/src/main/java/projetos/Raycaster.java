package projetos;

import org.lwjgl.opengl.GL11;

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

    public void drawRays2D() {
        float ra = player.getPa() - DR * 30;
        if (ra < 0) ra += 2 * PI;
        if (ra > 2 * PI) ra -= 2 * PI;

        GL11.glLineWidth(12.0f); // Set the line width to 12.0

        for (int r = 0; r < 60; r++) {
            float disT = castRay(ra);

            float ca = player.getPa() - ra;
            if (ca < 0) ca += 2 * PI;
            if (ca > 2 * PI) ca -= 2 * PI;
            disT *= Math.cos(ca);

            float lineH = (map.getMapS() * 320) / disT;
            if (lineH > 320) lineH = 320;
            float lineO = 160 - lineH / 2;

            GL11.glColor3f(1, 0, 0);
            GL11.glBegin(GL11.GL_LINES);
            GL11.glVertex2i(r * 8 + 530, (int) lineO);
            GL11.glVertex2i(r * 8 + 530, (int) (lineO + lineH));
            GL11.glEnd();

            ra += DR;
            if (ra < 0) ra += 2 * PI;
            if (ra > 2 * PI) ra -= 2 * PI;
        }
    }

    private float castRay(float ra) {
        float disT = 1000000;

        // Horizontal check
        float hx = 0, hy = 0;
        float aTan = -1 / (float) Math.tan(ra);
        int dof = 0;

        if (ra > PI) {
            hy = (((int) player.getPy() >> 6) << 6) - 0.0001f;
            hx = (player.getPy() - hy) * aTan + player.getPx();
            float yo = -64, xo = -yo * aTan;
            while (dof < 8) {
                int mx = (int) (hx) >> 6;
                int my = (int) (hy) >> 6;
                if (map.inBounds(mx, my) && map.getMap()[my * map.getMapX() + mx] == 1) {
                    disT = dist(player.getPx(), player.getPy(), hx, hy);
                    break;
                }
                hx += xo;
                hy += yo;
                dof++;
            }
        } else if (ra < PI) {
            hy = (((int) player.getPy() >> 6) << 6) + 64;
            hx = (player.getPy() - hy) * aTan + player.getPx();
            float yo = 64, xo = -yo * aTan;
            while (dof < 8) {
                int mx = (int) (hx) >> 6;
                int my = (int) (hy) >> 6;
                if (map.inBounds(mx, my) && map.getMap()[my * map.getMapX() + mx] == 1) {
                    disT = dist(player.getPx(), player.getPy(), hx, hy);
                    break;
                }
                hx += xo;
                hy += yo;
                dof++;
            }
        }

        // Vertical check
        float vx = 0, vy = 0;
        float nTan = - (float) Math.tan(ra);
        dof = 0;

        if (ra > P2 && ra < P3) {
            vx = (((int) player.getPx() >> 6) << 6) - 0.0001f;
            vy = (player.getPx() - vx) * nTan + player.getPy();
            float xo = -64, yo = -xo * nTan;
            while (dof < 8) {
                int mx = (int) (vx) >> 6;
                int my = (int) (vy) >> 6;
                if (map.inBounds(mx, my) && map.getMap()[my * map.getMapX() + mx] == 1) {
                    float distV = dist(player.getPx(), player.getPy(), vx, vy);
                    if (distV < disT) disT = distV;
                    break;
                }
                vx += xo;
                vy += yo;
                dof++;
            }
        } else if (ra < P2 || ra > P3) {
            vx = (((int) player.getPx() >> 6) << 6) + 64;
            vy = (player.getPx() - vx) * nTan + player.getPy();
            float xo = 64, yo = -xo * nTan;
            while (dof < 8) {
                int mx = (int) (vx) >> 6;
                int my = (int) (vy) >> 6;
                if (map.inBounds(mx, my) && map.getMap()[my * map.getMapX() + mx] == 1) {
                    float distV = dist(player.getPx(), player.getPy(), vx, vy);
                    if (distV < disT) disT = distV;
                    break;
                }
                vx += xo;
                vy += yo;
                dof++;
            }
        }

        return disT;
    }

    private float dist(float ax, float ay, float bx, float by) {
        return (float) Math.sqrt((bx - ax) * (bx - ax) + (by - ay) * (by - ay));
    }
}