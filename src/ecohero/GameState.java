package ecohero;

public class GameState {
    public static final int TOTAL_SECTIONS = 5;
    public static final int MAX_SCORE = 188;

    private int score;
    private final boolean[] completedSections = new boolean[TOTAL_SECTIONS];

    public int getScore() {
        return score;
    }

    public void addScore(int points) {
        score += Math.max(0, points);
    }

    public void reset() {
        score = 0;
        for (int i = 0; i < completedSections.length; i++) {
            completedSections[i] = false;
        }
    }

    public void completeSection(int sectionIndex) {
        if (sectionIndex >= 0 && sectionIndex < completedSections.length) {
            completedSections[sectionIndex] = true;
        }
    }

    public boolean isSectionCompleted(int sectionIndex) {
        return sectionIndex >= 0 && sectionIndex < completedSections.length && completedSections[sectionIndex];
    }

    public int getCompletedCount() {
        int count = 0;
        for (boolean completed : completedSections) {
            if (completed) {
                count++;
            }
        }
        return count;
    }

    public int getProgressPercent() {
        return (int) Math.round((getCompletedCount() * 100.0) / TOTAL_SECTIONS);
    }

    public int getMaxScore() {
        return MAX_SCORE;
    }
}
