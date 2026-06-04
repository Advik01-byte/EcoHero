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
                new Question("Which gas from burning petrol and diesel is a major greenhouse gas?", new String[]{"Oxygen", "Carbon dioxide", "Helium", "Hydrogen"}, 1),
                new Question("Why is it better to separate wet waste from dry waste?", new String[]{"It helps composting and recycling", "It makes the garbage heavier", "It creates more smoke", "It stops all waste from being used"}, 0),
                new Question("Which action saves the most electricity in daytime?", new String[]{"Leaving lights on for comfort", "Using sunlight instead of switching on lights", "Turning on every fan", "Keeping the TV on as background noise"}, 1),
                new Question("What is biodegradable waste?", new String[]{"Waste that cannot change ever", "Waste that can be broken down by microorganisms", "Only plastic waste", "Waste that is always poisonous"}, 1),
                new Question("Which of these helps reduce plastic waste the best?", new String[]{"Using a steel water bottle", "Buying a new plastic bottle every day", "Throwing wrappers on the ground", "Using more plastic plates"}, 0),
                new Question("Why are trees important in a city?", new String[]{"They make air cleaner and provide shade", "They increase traffic", "They make pollution stronger", "They remove oxygen"}, 0)
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
        ButtonStyles.apply(nextButton, new Color(46, 125, 50), Color.WHITE, new Color(27, 94, 32));
        ButtonStyles.setFont(nextButton, new Font("SansSerif", Font.BOLD, 16));
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
        ButtonStyles.apply(button, new Color(240, 247, 255), new Color(28, 42, 56), new Color(90, 135, 190));
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
