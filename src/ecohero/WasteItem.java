package ecohero;

import javax.swing.ImageIcon;

public class WasteItem {
    private final String name;
    private final RecyclingBin correctBin;
    private final ImageIcon image;

    public WasteItem(String name, RecyclingBin correctBin, ImageIcon image) {
        this.name = name;
        this.correctBin = correctBin;
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public RecyclingBin getCorrectBin() {
        return correctBin;
    }

    public ImageIcon getImage() {
        return image;
    }

    public enum RecyclingBin {
        PAPER,
        PLASTIC,
        ORGANIC
    }
}
