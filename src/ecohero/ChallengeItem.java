package ecohero;

import javax.swing.ImageIcon;

public class ChallengeItem {
    private final String statement;
    private final boolean energySaving;
    private final String explanation;
    private final ImageIcon image;

    public ChallengeItem(String statement, boolean energySaving, String explanation, ImageIcon image) {
        this.statement = statement;
        this.energySaving = energySaving;
        this.explanation = explanation;
        this.image = image;
    }

    public String getStatement() {
        return statement;
    }

    public boolean isEnergySaving() {
        return energySaving;
    }

    public String getExplanation() {
        return explanation;
    }

    public ImageIcon getImage() {
        return image;
    }
}
