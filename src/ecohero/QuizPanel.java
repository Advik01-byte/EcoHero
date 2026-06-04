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

public class QuizPanel extends JPanel implements ResettableScreen {
    private final GameController controller;
    private final Question[] questions;
    private final JButton[] optionButtons;
    private final JLabel questionLabel = new JLabel();
    private final JLabel feedbackLabel = new JLabel("Answer the final quiz questions.");
    private final JLabel questionCountLabel = new JLabel();
    private final JButton nextButton;
    private int currentIndex;
    private boolean answered;
    private int correctCount;

    public QuizPanel(GameController controller) {
        this.controller = controller;
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(new Color(250, 252, 255));
        setBorder(BorderFactory.createEmptyBorder(24, 28, 24, 28));

        JLabel title = new JLabel("Final Quiz");
        title.setAlignmentX(CENTER_ALIGNMENT);
        title.setFont(new Font("SansSerif", Font.BOLD, 30));
        title.setForeground(new Color(25, 118, 210));

        JTextArea instructions = new JTextArea(
                "This quiz checks what you learned from the project. Try to answer carefully and finish like a true EcoHero."
        );
        instructions.setWrapStyleWord(true);
        instructions.setLineWrap(true);
        instructions.setEditable(false);
        instructions.setOpaque(false);
        instructions.setFont(new Font("SansSerif", Font.PLAIN, 18));
        instructions.setMaximumSize(new Dimension(820, 90));

        questionCountLabel.setAlignmentX(CENTER_ALIGNMENT);
        questionCountLabel.setFont(new Font("SansSerif", Font.BOLD, 16));
        questionCountLabel.setForeground(new Color(66, 66, 66));

        questionLabel.setAlignmentX(CENTER_ALIGNMENT);
        questionLabel.setFont(new Font("SansSerif", Font.BOLD, 22));
        questionLabel.setForeground(new Color(38, 50, 56));
        questionLabel.setHorizontalAlignment(JLabel.CENTER);
        questionLabel.setMaximumSize(new Dimension(820, 120));

        questions = new Question[]{
                new Question("What does pollution do to the Earth?", new String[]{"Makes it cleaner", "Makes it dirty", "Makes it bigger", "Makes it faster"}, 1),
                new Question("Which action helps recycling?", new String[]{"Throwing everything together", "Sorting waste into bins", "Burning plastic", "Littering"}, 1),
                new Question("How can we save electricity?", new String[]{"Leave lights on", "Use more fans for no reason", "Switch off unused appliances", "Keep doors open all night"}, 2),
                new Question("Which of these is a good environmental habit?", new String[]{"Using reusable bags", "Wasting paper", "Throwing waste on roads", "Cutting many trees"}, 0),
                new Question("Why should we protect nature?", new String[]{"It keeps Earth healthy", "It makes pollution worse", "It has no use", "It removes fresh air"}, 0)
        };

        optionButtons = new JButton[4];
        JPanel optionsPanel = new JPanel();
        optionsPanel.setOpaque(false);
        optionsPanel.setLayout(new BoxLayout(optionsPanel, BoxLayout.Y_AXIS));
        for (int i = 0; i < optionButtons.length; i++) {
            optionButtons[i] = createOptionButton();
            final int optionIndex = i;
            optionButtons[i].addActionListener(e -> handleAnswer(optionIndex));
            optionsPanel.add(optionButtons[i]);
            optionsPanel.add(Box.createVerticalStrut(10));
        }

        feedbackLabel.setAlignmentX(CENTER_ALIGNMENT);
        feedbackLabel.setFont(new Font("SansSerif", Font.BOLD, 16));
        feedbackLabel.setForeground(new Color(27, 94, 32));

        nextButton = new JButton("Finish Project");
        nextButton.setAlignmentX(CENTER_ALIGNMENT);
        nextButton.setFont(new Font("SansSerif", Font.BOLD, 16));
        nextButton.setBackground(new Color(46, 125, 50));
        nextButton.setForeground(Color.WHITE);
        nextButton.setFocusPainted(false);
        nextButton.setBorder(BorderFactory.createEmptyBorder(12, 22, 12, 22));
        nextButton.setVisible(false);
        nextButton.addActionListener(e -> {
            if (answered) {
                nextStep();
            }
        });

        add(title);
        add(instructions);
        add(Box.createVerticalStrut(8));
        add(questionCountLabel);
        add(Box.createVerticalStrut(10));
        add(questionLabel);
        add(Box.createVerticalStrut(12));
        add(optionsPanel);
        add(feedbackLabel);
        add(Box.createVerticalStrut(8));
        add(nextButton);

        updateQuestion();
    }

    private JButton createOptionButton() {
        JButton button = new JButton();
        button.setAlignmentX(CENTER_ALIGNMENT);
        button.setMaximumSize(new Dimension(700, 48));
        button.setPreferredSize(new Dimension(700, 48));
        button.setFocusPainted(false);
        button.setFont(new Font("SansSerif", Font.PLAIN, 16));
        button.setBackground(Color.WHITE);
        button.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(208, 225, 245)),
                BorderFactory.createEmptyBorder(8, 14, 8, 14)
        ));
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
            correctCount++;
            controller.addScore(8);
            feedbackLabel.setText("Correct!");
            feedbackLabel.setForeground(new Color(27, 94, 32));
        } else {
            feedbackLabel.setText("Incorrect. The correct answer was: " + current.getOptions()[current.getCorrectIndex()]);
            feedbackLabel.setForeground(new Color(183, 28, 28));
        }

        disableOptions();
        nextButton.setVisible(true);
        if (currentIndex == questions.length - 1) {
            nextButton.setText("See Final Result");
        } else {
            nextButton.setText("Next Question");
        }
    }

    private void disableOptions() {
        for (JButton button : optionButtons) {
            button.setEnabled(false);
        }
    }

    private void enableOptions() {
        for (JButton button : optionButtons) {
            button.setEnabled(true);
        }
    }

    private void updateQuestion() {
        Question current = questions[currentIndex];
        questionCountLabel.setText("Question " + (currentIndex + 1) + " of " + questions.length);
        questionLabel.setText("<html><div style='text-align:center;'>" + current.getPrompt() + "</div></html>");
        String[] options = current.getOptions();
        for (int i = 0; i < optionButtons.length; i++) {
            optionButtons[i].setText(options[i]);
        }
        feedbackLabel.setText("Answer the final quiz questions.");
        feedbackLabel.setForeground(new Color(27, 94, 32));
        nextButton.setVisible(false);
        enableOptions();
        answered = false;
    }

    private void nextStep() {
        if (currentIndex < questions.length - 1) {
            currentIndex++;
            updateQuestion();
        } else {
            controller.addScore(10);
            controller.completeSection(3);
            controller.showScreen(EcoHeroFrame.SCREEN_RESULT);
        }
    }

    @Override
    public void resetScreen() {
        currentIndex = 0;
        answered = false;
        correctCount = 0;
        nextButton.setText("Next Question");
        updateQuestion();
    }
}
