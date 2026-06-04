package ecohero;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.SwingConstants;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.util.LinkedHashMap;
import java.util.Map;

public class EcoHeroFrame extends JFrame implements GameController {
    public static final String SCREEN_MENU = "menu";
    public static final String SCREEN_LEARN = "learn";
    public static final String SCREEN_POLLUTION = "pollution";
    public static final String SCREEN_RECYCLING = "recycling";
    public static final String SCREEN_ENERGY = "energy";
    public static final String SCREEN_QUIZ = "quiz";
    public static final String SCREEN_RESULT = "result";

    private final GameState state = new GameState();
    private final CardLayout cardLayout = new CardLayout();
    private final JPanel cardPanel = new JPanel(cardLayout);
    private ResultPanel resultPanel;
    private final JLabel scoreValueLabel = new JLabel("0");
    private final JLabel progressValueLabel = new JLabel("0%");
    private final JProgressBar progressBar = new JProgressBar(0, 100);
    private final Map<String, ResettableScreen> resettableScreens = new LinkedHashMap<>();

    public EcoHeroFrame() {
        setTitle("EcoHero: Save the Earth");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(980, 680));
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        add(buildHeader(), BorderLayout.NORTH);
        add(cardPanel, BorderLayout.CENTER);

        registerScreens();
        showScreen(SCREEN_MENU);
        updateHud();
    }

    private JPanel buildHeader() {
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(new Color(18, 92, 68));
        header.setBorder(BorderFactory.createEmptyBorder(12, 18, 12, 18));

        JLabel title = new JLabel("EcoHero: Save the Earth");
        title.setForeground(Color.WHITE);
        title.setFont(new Font("SansSerif", Font.BOLD, 24));

        JLabel subtitle = new JLabel("Grade 6 ICSE Science Holiday Homework Project");
        subtitle.setForeground(new Color(222, 244, 232));
        subtitle.setFont(new Font("SansSerif", Font.PLAIN, 13));

        JPanel textPanel = new JPanel(new BorderLayout());
        textPanel.setOpaque(false);
        textPanel.add(title, BorderLayout.NORTH);
        textPanel.add(subtitle, BorderLayout.SOUTH);

        JPanel statusPanel = new JPanel();
        statusPanel.setOpaque(false);
        statusPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));

        JLabel scoreLabel = new JLabel("Score:");
        scoreLabel.setForeground(Color.WHITE);
        scoreLabel.setFont(new Font("SansSerif", Font.BOLD, 14));

        scoreValueLabel.setForeground(Color.WHITE);
        scoreValueLabel.setFont(new Font("SansSerif", Font.BOLD, 18));

        JLabel progressLabel = new JLabel("Progress:");
        progressLabel.setForeground(Color.WHITE);
        progressLabel.setFont(new Font("SansSerif", Font.BOLD, 14));

        progressValueLabel.setForeground(Color.WHITE);
        progressValueLabel.setFont(new Font("SansSerif", Font.BOLD, 18));

        progressBar.setPreferredSize(new Dimension(220, 18));
        progressBar.setStringPainted(false);
        progressBar.setBorder(BorderFactory.createLineBorder(new Color(255, 255, 255, 60)));
        progressBar.setForeground(new Color(124, 179, 66));
        progressBar.setBackground(new Color(232, 245, 233));

        statusPanel.add(scoreLabel);
        statusPanel.add(scoreValueLabel);
        statusPanel.add(progressLabel);
        statusPanel.add(progressValueLabel);
        statusPanel.add(progressBar);

        header.add(textPanel, BorderLayout.WEST);
        header.add(statusPanel, BorderLayout.EAST);
        return header;
    }

    private void registerScreens() {
        MainMenuPanel menuPanel = new MainMenuPanel(this);
        LearnPanel learnPanel = new LearnPanel(this);
        PollutionPanel pollutionPanel = new PollutionPanel(this);
        RecyclingPanel recyclingPanel = new RecyclingPanel(this);
        EnergyChallengePanel energyPanel = new EnergyChallengePanel(this);
        QuizPanel quizPanel = new QuizPanel(this);
        resultPanel = new ResultPanel(this);

        addScreen(SCREEN_MENU, menuPanel);
        addScreen(SCREEN_LEARN, learnPanel);
        addScreen(SCREEN_POLLUTION, pollutionPanel);
        addScreen(SCREEN_RECYCLING, recyclingPanel);
        addScreen(SCREEN_ENERGY, energyPanel);
        addScreen(SCREEN_QUIZ, quizPanel);
        addScreen(SCREEN_RESULT, resultPanel);
    }

    private void addScreen(String name, JPanel panel) {
        cardPanel.add(panel, name);
        if (panel instanceof ResettableScreen) {
            resettableScreens.put(name, (ResettableScreen) panel);
        }
    }

    @Override
    public void showScreen(String screenName) {
        if (SCREEN_RESULT.equals(screenName) && resultPanel != null) {
            resultPanel.refreshResult();
        }
        cardLayout.show(cardPanel, screenName);
    }

    @Override
    public void addScore(int points) {
        state.addScore(points);
        updateHud();
    }

    @Override
    public void completeSection(int sectionIndex) {
        state.completeSection(sectionIndex);
        updateHud();
    }

    @Override
    public void restartGame() {
        state.reset();
        for (ResettableScreen screen : resettableScreens.values()) {
            screen.resetScreen();
        }
        updateHud();
        showScreen(SCREEN_MENU);
    }

    @Override
    public GameState getGameState() {
        return state;
    }

    public void updateHud() {
        scoreValueLabel.setText(String.valueOf(state.getScore()));
        progressValueLabel.setText(state.getProgressPercent() + "%");
        progressBar.setValue(state.getProgressPercent());
    }
}
