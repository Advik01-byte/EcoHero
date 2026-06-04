package ecohero;

import javax.swing.ImageIcon;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;

public final class ImageFactory {
    private ImageFactory() {
    }

    public static ImageIcon createHeroIcon(int width, int height) {
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = prepare(image);
        drawBackground(g, width, height, new Color(227, 245, 255));
        drawEarth(g, width, height, 0.26f, 0.18f, 0.48f);
        g.setColor(new Color(46, 125, 50));
        g.fillOval((int) (width * 0.18), (int) (height * 0.67), (int) (width * 0.18), (int) (height * 0.12));
        g.fillOval((int) (width * 0.64), (int) (height * 0.67), (int) (width * 0.18), (int) (height * 0.12));
        g.setColor(new Color(255, 193, 7));
        g.fillPolygon(new int[]{width / 2, (int) (width * 0.58), (int) (width * 0.42)},
                new int[]{(int) (height * 0.28), (int) (height * 0.46), (int) (height * 0.46)}, 3);
        g.dispose();
        return new ImageIcon(image);
    }

    public static ImageIcon createPollutionIcon(int width, int height) {
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = prepare(image);
        drawBackground(g, width, height, new Color(241, 248, 233));
        g.setColor(new Color(120, 144, 156));
        g.fillRect((int) (width * 0.12), (int) (height * 0.42), (int) (width * 0.28), (int) (height * 0.28));
        g.fillRect((int) (width * 0.34), (int) (height * 0.32), (int) (width * 0.22), (int) (height * 0.38));
        g.setColor(new Color(69, 90, 100));
        g.fillRect((int) (width * 0.17), (int) (height * 0.58), (int) (width * 0.04), (int) (height * 0.12));
        g.fillRect((int) (width * 0.44), (int) (height * 0.48), (int) (width * 0.05), (int) (height * 0.22));
        g.setColor(new Color(117, 117, 117, 200));
        g.fillOval((int) (width * 0.18), (int) (height * 0.15), (int) (width * 0.18), (int) (height * 0.12));
        g.fillOval((int) (width * 0.30), (int) (height * 0.10), (int) (width * 0.20), (int) (height * 0.14));
        g.fillOval((int) (width * 0.44), (int) (height * 0.14), (int) (width * 0.20), (int) (height * 0.12));
        g.dispose();
        return new ImageIcon(image);
    }

    public static ImageIcon createRecyclingIcon(int width, int height) {
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = prepare(image);
        drawBackground(g, width, height, new Color(232, 245, 233));
        g.setColor(new Color(56, 142, 60));
        g.setStroke(new BasicStroke(Math.max(6, width / 18)));
        int cx = width / 2;
        int cy = height / 2;
        g.drawArc(cx - width / 5, cy - height / 5, width / 3, height / 3, 35, 255);
        g.drawArc(cx - width / 5, cy - height / 5, width / 3, height / 3, 155, 255);
        g.drawArc(cx - width / 5, cy - height / 5, width / 3, height / 3, 275, 255);
        g.fillPolygon(new int[]{cx + width / 10, cx + width / 10 + 14, cx + width / 10 + 4},
                new int[]{cy - height / 6, cy - height / 6 + 18, cy - height / 6 + 18}, 3);
        g.fillPolygon(new int[]{cx - width / 6, cx - width / 6 - 16, cx - width / 6 - 4},
                new int[]{cy + height / 9, cy + height / 9 - 10, cy + height / 9 - 18}, 3);
        g.fillPolygon(new int[]{cx + width / 20, cx + width / 20 + 18, cx + width / 20 + 2},
                new int[]{cy + height / 10, cy + height / 10 + 4, cy + height / 10 + 18}, 3);
        g.dispose();
        return new ImageIcon(image);
    }

    public static ImageIcon createEnergyIcon(int width, int height) {
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = prepare(image);
        drawBackground(g, width, height, new Color(255, 248, 225));
        g.setColor(new Color(255, 193, 7));
        g.fillRoundRect((int) (width * 0.38), (int) (height * 0.15), (int) (width * 0.24), (int) (height * 0.34), 20, 20);
        g.fillRect((int) (width * 0.45), (int) (height * 0.49), (int) (width * 0.1), (int) (height * 0.18));
        g.setColor(new Color(255, 87, 34));
        g.fillPolygon(new int[]{(int) (width * 0.5), (int) (width * 0.42), (int) (width * 0.54), (int) (width * 0.46), (int) (width * 0.58)},
                new int[]{(int) (height * 0.18), (int) (height * 0.47), (int) (height * 0.47), (int) (height * 0.78), (int) (height * 0.44)}, 5);
        g.dispose();
        return new ImageIcon(image);
    }

    public static ImageIcon createItemIcon(String itemName, int width, int height) {
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = prepare(image);
        drawBackground(g, width, height, new Color(250, 250, 250));

        if ("Plastic Bottle".equals(itemName)) {
            g.setColor(new Color(102, 187, 106));
            g.fillRoundRect((int) (width * 0.4), (int) (height * 0.18), (int) (width * 0.2), (int) (height * 0.48), 20, 20);
            g.fillRect((int) (width * 0.46), (int) (height * 0.1), (int) (width * 0.08), (int) (height * 0.12));
            g.setColor(new Color(46, 125, 50));
            g.fillRect((int) (width * 0.43), (int) (height * 0.14), (int) (width * 0.14), (int) (height * 0.04));
        } else if ("Newspaper".equals(itemName)) {
            g.setColor(new Color(224, 224, 224));
            g.fillRoundRect((int) (width * 0.22), (int) (height * 0.2), (int) (width * 0.56), (int) (height * 0.48), 12, 12);
            g.setColor(new Color(120, 144, 156));
            g.drawLine((int) (width * 0.3), (int) (height * 0.3), (int) (width * 0.62), (int) (height * 0.3));
            g.drawLine((int) (width * 0.3), (int) (height * 0.38), (int) (width * 0.62), (int) (height * 0.38));
            g.drawLine((int) (width * 0.3), (int) (height * 0.46), (int) (width * 0.54), (int) (height * 0.46));
        } else if ("Banana Peel".equals(itemName)) {
            g.setColor(new Color(255, 214, 0));
            g.fillArc((int) (width * 0.28), (int) (height * 0.18), (int) (width * 0.42), (int) (height * 0.38), 20, 240);
            g.setColor(new Color(139, 195, 74));
            g.fillOval((int) (width * 0.58), (int) (height * 0.28), (int) (width * 0.09), (int) (height * 0.09));
        } else {
            g.setColor(new Color(96, 125, 139));
            g.fillOval((int) (width * 0.34), (int) (height * 0.22), (int) (width * 0.32), (int) (height * 0.32));
        }

        g.dispose();
        return new ImageIcon(image);
    }

    public static ImageIcon createChoiceIcon(String title, Color accent, int width, int height) {
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = prepare(image);
        drawBackground(g, width, height, new Color(250, 250, 250));
        g.setColor(accent);
        g.fillOval((int) (width * 0.24), (int) (height * 0.18), (int) (width * 0.52), (int) (height * 0.52));
        g.setColor(Color.WHITE);
        g.setStroke(new BasicStroke(Math.max(5, width / 22)));
        g.drawLine((int) (width * 0.38), (int) (height * 0.44), (int) (width * 0.46), (int) (height * 0.52));
        g.drawLine((int) (width * 0.46), (int) (height * 0.52), (int) (width * 0.64), (int) (height * 0.34));
        g.setColor(new Color(60, 60, 60));
        g.drawString(title, (int) (width * 0.18), (int) (height * 0.9));
        g.dispose();
        return new ImageIcon(image);
    }

    private static Graphics2D prepare(BufferedImage image) {
        Graphics2D g = image.createGraphics();
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        return g;
    }

    private static void drawBackground(Graphics2D g, int width, int height, Color color) {
        g.setColor(color);
        g.fillRoundRect(0, 0, width, height, 28, 28);
    }

    private static void drawEarth(Graphics2D g, int width, int height, float x, float y, float size) {
        int d = (int) (Math.min(width, height) * size);
        int px = (int) (width * x);
        int py = (int) (height * y);
        g.setColor(new Color(33, 150, 243));
        g.fill(new Ellipse2D.Double(px, py, d, d));
        g.setColor(new Color(76, 175, 80));
        g.fillOval(px + d / 6, py + d / 5, d / 4, d / 6);
        g.fillOval(px + d / 2, py + d / 4, d / 5, d / 4);
        g.fillOval(px + d / 3, py + d / 2, d / 5, d / 6);
    }
}
