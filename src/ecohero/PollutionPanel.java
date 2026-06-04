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
    private final Question[] questions;
    private final JLabel feedbackLabel = new JLabel("Choose the correct answer to continue.");
    private final JLabel questionCountLabel = new JLabel();
    private final JLabel questionLabel = new JLabel();
    private final JButton nextButton;
    private int currentIndex;
    private boolean answered;

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

        questionCountLabel.setAlignmentX(CENTER_ALIGNMENT);
        questionCountLabel.setFont(new Font("SansSerif", Font.BOLD, 16));
        questionCountLabel.setForeground(new Color(66, 66, 66));
        questionCountLabel.setHorizontalAlignment(JLabel.CENTER);
        questionCountLabel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 24));

        questionLabel.setAlignmentX(CENTER_ALIGNMENT);
        questionLabel.setFont(new Font("SansSerif", Font.BOLD, 20));
        questionLabel.setForeground(new Color(38, 50, 56));
        questionLabel.setHorizontalAlignment(JLabel.CENTER);
        questionLabel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 56));

        questions = new Question[]{
                new Question("Which of these causes pollution?", new String[]{
                        "Smoke from vehicles and factories",
                        "Planting more trees",
                        "Using clean water carefully"
                }, 0),
                new Question("What kind of pollution comes from loud sounds?", new String[]{
                        "Air pollution",
                        "Noise pollution",
                        "Water pollution"
                }, 1),
                new Question("Which habit helps reduce land pollution?", new String[]{
                        "Throwing waste on the road",
                        "Burning plastic in the open",
                        "Putting waste in the bin"
                }, 2),
                new Question("What should we do with plastic bottles after using them?", new String[]{
                        "Recycle them",
                        "Throw them into rivers",
                        "Bury them in the garden"
                }, 0),
                new Question("What kind of pollution is caused by dirty water from factories?", new String[]{
                        "Water pollution",
                        "Noise pollution",
                        "Light pollution"
                }, 0),
                new Question("Which action helps keep the air cleaner?", new String[]{
                        "Planting trees",
                        "Burning garbage near homes",
                        "Using more cars for short trips"
                }, 0)
        };

        answerButtons = new JButton[]{
                createAnswerButton("Option 1"),
                createAnswerButton("Option 2"),
                createAnswerButton("Option 3")
        };

        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false);
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        for (int i = 0; i < answerButtons.length; i++) {
            final int optionIndex = i;
            answerButtons[i].addActionListener(e -> handleAnswer(optionIndex));
            answerButtons[i].setAlignmentX(CENTER_ALIGNMENT);
            buttonPanel.add(answerButtons[i]);
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
        nextButton.addActionListener(e -> {
            if (answered) {
                nextStep();
            }
        });

        add(title);
        add(image);
        add(explanation);
        add(Box.createVerticalStrut(8));
        add(questionCountLabel);
        add(questionLabel);
        add(Box.createVerticalStrut(12));
        add(buttonPanel);
        add(feedbackLabel);
        add(Box.createVerticalStrut(8));
        add(nextButton);

        updateQuestion();
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

    private void handleAnswer(int optionIndex) {
        if (answered || currentIndex >= questions.length) {
            return;
        }

        Question current = questions[currentIndex];
        boolean correct = current.getCorrectIndex() == optionIndex;
        answered = true;

        if (correct) {
            controller.addScore(15);
            feedbackLabel.setText("Correct!");
            feedbackLabel.setForeground(new Color(27, 94, 32));
        } else {
            feedbackLabel.setText("Incorrect. The correct answer was: " + current.getOptions()[current.getCorrectIndex()]);
            feedbackLabel.setForeground(new Color(183, 28, 28));
        }

        disableButtons();
        nextButton.setVisible(true);
        nextButton.setText(currentIndex == questions.length - 1 ? "Continue to Recycling Game" : "Next Question");
    }

    private void disableButtons() {
        for (JButton button : answerButtons) {
            button.setEnabled(false);
        }
    }

    private void enableButtons() {
        for (JButton button : answerButtons) {
            button.setEnabled(true);
        }
    }

    private void updateQuestion() {
        Question current = questions[currentIndex];
        questionCountLabel.setText("<html><div style='text-align:center;'>Question " + (currentIndex + 1) + " of " + questions.length + "</div></html>");
        questionLabel.setText("<html><div style='text-align:center;'>" + current.getPrompt() + "</div></html>");
        String[] options = current.getOptions();
        for (int i = 0; i < answerButtons.length; i++) {
            answerButtons[i].setText(options[i]);
        }
        feedbackLabel.setText("Choose the correct answer to continue.");
        feedbackLabel.setForeground(new Color(27, 94, 32));
        nextButton.setVisible(false);
        enableButtons();
        answered = false;
    }

    private void nextStep() {
        if (currentIndex < questions.length - 1) {
            currentIndex++;
            updateQuestion();
        } else {
            controller.addScore(10);
            controller.completeSection(1);
            controller.showScreen(EcoHeroFrame.SCREEN_RECYCLING);
        }
    }

    @Override
    public void resetScreen() {
        currentIndex = 0;
        answered = false;
        updateQuestion();
    }
}
