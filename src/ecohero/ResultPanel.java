package ecohero;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

public class ResultPanel extends JPanel implements ResettableScreen {
    private final GameController controller;
    private final JLabel resultLabel = new JLabel();
    private final JTextArea messageArea = new JTextArea();

    public ResultPanel(GameController controller) {
        this.controller = controller;
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(new Color(245, 250, 245));
        setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

        JLabel title = new JLabel("Mission Complete");
        title.setAlignmentX(CENTER_ALIGNMENT);
        title.setFont(new Font("SansSerif", Font.BOLD, 32));
        title.setForeground(new Color(27, 94, 32));

        JLabel icon = new JLabel(ImageFactory.createHeroIcon(220, 220));
        icon.setAlignmentX(CENTER_ALIGNMENT);
        icon.setBorder(BorderFactory.createEmptyBorder(12, 0, 12, 0));

        resultLabel.setAlignmentX(CENTER_ALIGNMENT);
        resultLabel.setFont(new Font("SansSerif", Font.BOLD, 24));
        resultLabel.setForeground(new Color(38, 50, 56));

        messageArea.setEditable(false);
        messageArea.setOpaque(false);
        messageArea.setLineWrap(true);
        messageArea.setWrapStyleWord(true);
        messageArea.setFont(new Font("SansSerif", Font.PLAIN, 18));
        messageArea.setMaximumSize(new Dimension(800, 140));

        JButton playAgain = new JButton("Play Again");
        playAgain.setAlignmentX(CENTER_ALIGNMENT);
        playAgain.setFont(new Font("SansSerif", Font.BOLD, 16));
        playAgain.setBackground(new Color(46, 125, 50));
        playAgain.setForeground(Color.WHITE);
        playAgain.setFocusPainted(false);
        playAgain.setBorder(BorderFactory.createEmptyBorder(12, 22, 12, 22));
        playAgain.addActionListener(e -> controller.restartGame());

        JButton menuButton = new JButton("Back to Main Menu");
        menuButton.setAlignmentX(CENTER_ALIGNMENT);
        menuButton.setFont(new Font("SansSerif", Font.BOLD, 16));
        menuButton.setBackground(new Color(2, 119, 189));
        menuButton.setForeground(Color.WHITE);
        menuButton.setFocusPainted(false);
        menuButton.setBorder(BorderFactory.createEmptyBorder(12, 22, 12, 22));
        menuButton.addActionListener(e -> controller.showScreen(EcoHeroFrame.SCREEN_MENU));

        JPanel buttonRow = new JPanel();
        buttonRow.setOpaque(false);
        buttonRow.add(playAgain);
        buttonRow.add(Box.createHorizontalStrut(12));
        buttonRow.add(menuButton);

        add(title);
        add(icon);
        add(resultLabel);
        add(Box.createVerticalStrut(8));
        add(messageArea);
        add(Box.createVerticalStrut(8));
        add(buttonRow);
    }

    public void refreshResult() {
        int score = controller.getGameState().getScore();
        int progress = controller.getGameState().getProgressPercent();
        resultLabel.setText("Final Score: " + score + " points");
        messageArea.setText(
                "You completed the EcoHero project and learned about pollution, recycling, and saving energy.\n\n"
                        + "Progress completed: " + progress + "%\n"
                        + "Keep using these habits in real life to protect the Earth."
        );
    }

    @Override
    public void resetScreen() {
        refreshResult();
    }
}
