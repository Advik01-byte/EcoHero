package ecohero;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public final class ImageFactory {
    private static final String ASSET_DIR = "assets";

    private ImageFactory() {
    }

    public static ImageIcon createHeroIcon(int width, int height) {
        return loadIcon("Planet-Earth.jpg", width, height, placeholder(width, height, "Earth"));
    }

    public static ImageIcon createPollutionIcon(int width, int height) {
        return loadIcon("Air pollution.jpg", width, height, placeholder(width, height, "Pollution"));
    }

    public static ImageIcon createRecyclingIcon(int width, int height) {
        return loadIcon("Recycle Symbol.jpg", width, height, placeholder(width, height, "Recycle"));
    }

    public static ImageIcon createEnergyIcon(int width, int height) {
        return loadIcon("CompactFluorescentLightBulb.jpg", width, height, placeholder(width, height, "Energy"));
    }

    public static ImageIcon createItemIcon(String itemName, int width, int height) {
        String fileName;
        if ("Plastic Bottle".equalsIgnoreCase(itemName)) {
            fileName = "Disposable plastic bottle-Corning-01.jpg";
        } else if ("Newspaper".equalsIgnoreCase(itemName)) {
            fileName = "Just printed newspaper.jpg";
        } else if ("Banana Peel".equalsIgnoreCase(itemName)) {
            fileName = "Banana peel.jpg";
        } else {
            fileName = "Planet-Earth.jpg";
        }
        return loadIcon(fileName, width, height, placeholder(width, height, itemName));
    }

    public static ImageIcon createChoiceIcon(String title, Color accent, int width, int height) {
        String lower = title == null ? "" : title.toLowerCase();
        String fileName;
        if (lower.contains("save")) {
            fileName = "Green-check-mark.png";
        } else if (lower.contains("wait") || lower.contains("waste")) {
            fileName = "Red X.png";
        } else if (lower.contains("light")) {
            fileName = "CompactFluorescentLightBulb.jpg";
        } else {
            fileName = "Planet-Earth.jpg";
        }
        return loadIcon(fileName, width, height, placeholder(width, height, title));
    }

    private static ImageIcon loadIcon(String fileName, int width, int height, BufferedImage fallback) {
        BufferedImage image = tryLoadFromAssets(fileName);
        if (image == null) {
            image = tryLoadFromWiki(fileName);
        }
        if (image == null) {
            image = fallback;
        }
        Image scaled = image.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return new ImageIcon(scaled);
    }

    private static BufferedImage tryLoadFromAssets(String fileName) {
        File file = new File(ASSET_DIR, fileName);
        if (!file.exists()) {
            return null;
        }
        try {
            return ImageIO.read(file);
        } catch (IOException e) {
            return null;
        }
    }

    private static BufferedImage tryLoadFromWiki(String fileName) {
        String url = "https://commons.wikimedia.org/wiki/Special:FilePath/"
                + URLEncoder.encode(fileName, StandardCharsets.UTF_8).replace("+", "%20");
        try {
            return ImageIO.read(new URL(url));
        } catch (IOException e) {
            return null;
        }
    }

    private static BufferedImage placeholder(int width, int height, String label) {
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = image.createGraphics();
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setColor(new Color(245, 248, 245));
        g.fillRoundRect(0, 0, width, height, 28, 28);
        g.setColor(new Color(130, 160, 135));
        g.setStroke(new BasicStroke(Math.max(2, width / 80f)));
        g.drawRoundRect(1, 1, width - 2, height - 2, 28, 28);
        g.setFont(new Font("SansSerif", Font.BOLD, Math.max(14, width / 10)));
        int textWidth = g.getFontMetrics().stringWidth(label);
        g.drawString(label, Math.max(10, (width - textWidth) / 2), height / 2);
        g.dispose();
        return image;
    }
}
