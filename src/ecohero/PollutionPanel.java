package ecohero;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

public class PollutionPanel extends JPanel implements ResettableScreen {
    private final GameController controller;
    private final JButton[] answerButtons;
    private final JLabel feedbackLabel = new JLabel("Choose the correct answer to continue.");
    private final JButton nextButton;
    private boolean solved;

    public PollutionPanel(GameController controller) {
        this.controller = controller;
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(new Color(247, 252, 247));
        setBorder(BorderFactory.createEmptyBorder(24, 28, 24, 28));

        JLabel title = new JLabel("Learn About Pollution");
        title.setAlignmentX(CENTER_ALIGNMENT);
        title.setFont(new Font("SansSerif", Font.BOLD, 30));
        title.setForeground(new Color(27, 94, 32));

        ImageIcon icon = ImageFactory.createPollutionIcon(240, 180);
        JLabel image = new JLabel(icon);
        image.setAlignmentX(CENTER_ALIGNMENT);
        image.setBorder(BorderFactory.createEmptyBorder(12, 0, 12, 0));

        JTextArea explanation = new JTextArea(
                "Pollution makes the air, water, and land dirty. It can harm plants, animals, and people. "
                        + "We can reduce pollution by keeping our surroundings clean, using less plastic, and planting trees."
        );
        explanation.setWrapStyleWord(true);
        explanation.setLineWrap(true);
        explanation.setEditable(false);
        explanation.setOpaque(false);
        explanation.setFont(new Font("SansSerif", Font.PLAIN, 18));
        explanation.setForeground(new Color(50, 50, 50));
        explanation.setAlignmentX(CENTER_ALIGNMENT);
        explanation.setMaximumSize(new Dimension(820, 120));

        JLabel question = new JLabel("Which of these causes pollution?");
        question.setAlignmentX(CENTER_ALIGNMENT);
        question.setFont(new Font("SansSerif", Font.BOLD, 20));
        question.setForeground(new Color(38, 50, 56));

        answerButtons = new JButton[]{
                createAnswerButton("Smoke from vehicles and factories"),
                createAnswerButton("Planting more trees"),
                createAnswerButton("Using clean water carefully")
        };

        answerButtons[0].addActionListener(e -> handleAnswer(true, "Correct! Smoke from vehicles and factories causes air pollution."));
        answerButtons[1].addActionListener(e -> handleAnswer(false, "Not quite. Planting trees helps reduce pollution."));
        answerButtons[2].addActionListener(e -> handleAnswer(false, "Not quite. Using water carefully is good for the environment."));

        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false);
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        for (JButton button : answerButtons) {
            button.setAlignmentX(CENTER_ALIGNMENT);
            buttonPanel.add(button);
            buttonPanel.add(Box.createVerticalStrut(10));
        }

        feedbackLabel.setAlignmentX(CENTER_ALIGNMENT);
        feedbackLabel.setFont(new Font("SansSerif", Font.BOLD, 16));
        feedbackLabel.setForeground(new Color(27, 94, 32));

        nextButton = new JButton("Continue to Recycling Game");
        nextButton.setAlignmentX(CENTER_ALIGNMENT);
        ButtonStyles.apply(nextButton, new Color(46, 125, 50), Color.WHITE, new Color(27, 94, 32));
        ButtonStyles.setFont(nextButton, new Font("SansSerif", Font.BOLD, 16));
        nextButton.setVisible(false);
        nextButton.addActionListener(e -> controller.showScreen(EcoHeroFrame.SCREEN_RECYCLING));

        add(title);
        add(image);
        add(explanation);
        add(Box.createVerticalStrut(8));
        add(question);
        add(Box.createVerticalStrut(12));
        add(buttonPanel);
        add(feedbackLabel);
        add(Box.createVerticalStrut(8));
        add(nextButton);
    }

    private JButton createAnswerButton(String text) {
        JButton button = new JButton(text);
        button.setAlignmentX(CENTER_ALIGNMENT);
        button.setMaximumSize(new Dimension(620, 46));
        button.setPreferredSize(new Dimension(620, 46));
        ButtonStyles.apply(button, new Color(245, 250, 245), new Color(25, 55, 35), new Color(86, 140, 96));
        ButtonStyles.setFont(button, new Font("SansSerif", Font.PLAIN, 16));
        return button;
    }

    private void handleAnswer(boolean correct, String message) {
        if (solved) {
            return;
        }
        feedbackLabel.setText(message);
        feedbackLabel.setForeground(correct ? new Color(27, 94, 32) : new Color(183, 28, 28));
        if (correct) {
            solved = true;
            controller.addScore(15);
            controller.completeSection(0);
            for (JButton button : answerButtons) {
                button.setEnabled(false);
            }
            nextButton.setVisible(true);
        }
    }

    @Override
    public void resetScreen() {
        solved = false;
        feedbackLabel.setText("Choose the correct answer to continue.");
        feedbackLabel.setForeground(new Color(27, 94, 32));
        for (JButton button : answerButtons) {
            button.setEnabled(true);
        }
        nextButton.setVisible(false);
    }
}
