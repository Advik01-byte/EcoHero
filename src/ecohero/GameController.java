package ecohero;

public interface GameController {
    void showScreen(String screenName);

    void addScore(int points);

    void completeSection(int sectionIndex);

    void restartGame();

    GameState getGameState();
}
