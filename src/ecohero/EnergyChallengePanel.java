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

public class EnergyChallengePanel extends JPanel implements ResettableScreen {
    private final GameController controller;
    private final ChallengeItem[] items;
    private final JButton[] choiceButtons;
    private final JLabel statementLabel = new JLabel();
    private final JLabel feedbackLabel = new JLabel("Choose the energy-saving option.");
    private final JButton nextButton;
    private int currentIndex;
    private boolean solved;

    public EnergyChallengePanel(GameController controller) {
        this.controller = controller;
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(new Color(249, 251, 244));
        setBorder(BorderFactory.createEmptyBorder(24, 28, 24, 28));

        JLabel title = new JLabel("Energy-Saving Challenge");
        title.setAlignmentX(CENTER_ALIGNMENT);
        title.setFont(new Font("SansSerif", Font.BOLD, 30));
        title.setForeground(new Color(255, 111, 0));

        JTextArea instructions = new JTextArea(
                "Energy saving means using electricity carefully. It helps reduce waste and protect natural resources."
        );
        instructions.setWrapStyleWord(true);
        instructions.setLineWrap(true);
        instructions.setEditable(false);
        instructions.setOpaque(false);
        instructions.setFont(new Font("SansSerif", Font.PLAIN, 18));
        instructions.setMaximumSize(new Dimension(820, 100));

        JLabel icon = new JLabel(ImageFactory.createEnergyIcon(220, 170));
        icon.setAlignmentX(CENTER_ALIGNMENT);
        icon.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));

        items = new ChallengeItem[]{
                new ChallengeItem(
                        "Turn off the lights when leaving a room.",
                        true,
                        "Correct! This saves electricity.",
                        ImageFactory.createChoiceIcon("Save", new Color(76, 175, 80), 130, 100)
                ),
                new ChallengeItem(
                        "Leave the television on even when no one is watching.",
                        false,
                        "Correct! Leaving it on wastes energy.",
                        ImageFactory.createChoiceIcon("Wait", new Color(244, 67, 54), 130, 100)
                ),
                new ChallengeItem(
                        "Use sunlight during the day instead of switching on lights.",
                        true,
                        "Correct! Natural light is a smart choice.",
                        ImageFactory.createChoiceIcon("Light", new Color(255, 193, 7), 130, 100)
                )
        };

        statementLabel.setAlignmentX(CENTER_ALIGNMENT);
        statementLabel.setFont(new Font("SansSerif", Font.BOLD, 20));
        statementLabel.setForeground(new Color(38, 50, 56));

        choiceButtons = new JButton[]{
                createChoiceButton("Energy Saver"),
                createChoiceButton("Energy Waster")
        };
        choiceButtons[0].addActionListener(e -> handleChoice(true));
        choiceButtons[1].addActionListener(e -> handleChoice(false));

        JPanel choicePanel = new JPanel();
        choicePanel.setOpaque(false);
        choicePanel.add(choiceButtons[0]);
        choicePanel.add(choiceButtons[1]);

        feedbackLabel.setAlignmentX(CENTER_ALIGNMENT);
        feedbackLabel.setFont(new Font("SansSerif", Font.BOLD, 16));
        feedbackLabel.setForeground(new Color(27, 94, 32));

        nextButton = new JButton("Go to Final Quiz");
        nextButton.setAlignmentX(CENTER_ALIGNMENT);
        ButtonStyles.apply(nextButton, new Color(46, 125, 50), Color.WHITE, new Color(27, 94, 32));
        ButtonStyles.setFont(nextButton, new Font("SansSerif", Font.BOLD, 16));
        nextButton.setVisible(false);
        nextButton.addActionListener(e -> controller.showScreen(EcoHeroFrame.SCREEN_QUIZ));

        add(title);
        add(instructions);
        add(icon);
        add(statementLabel);
        add(Box.createVerticalStrut(10));
        add(choicePanel);
        add(Box.createVerticalStrut(10));
        add(feedbackLabel);
        add(Box.createVerticalStrut(8));
        add(nextButton);

        updateItem();
    }

    private JButton createChoiceButton(String text) {
        JButton button = new JButton(text);
        button.setPreferredSize(new Dimension(170, 46));
        ButtonStyles.apply(button, new Color(255, 248, 230), new Color(110, 67, 0), new Color(189, 134, 0));
        ButtonStyles.setFont(button, new Font("SansSerif", Font.PLAIN, 15));
        return button;
    }

    private void handleChoice(boolean energySaving) {
        if (solved || currentIndex >= items.length) {
            return;
        }
        ChallengeItem current = items[currentIndex];
        if (current.isEnergySaving() == energySaving) {
            controller.addScore(5);
            feedbackLabel.setText(current.getExplanation());
            feedbackLabel.setForeground(new Color(27, 94, 32));
            currentIndex++;
            if (currentIndex < items.length) {
                updateItem();
            } else {
                solved = true;
                controller.addScore(10);
                controller.completeSection(2);
                disableButtons();
                statementLabel.setText("Energy challenge complete");
                statementLabel.setIcon(ImageFactory.createEnergyIcon(140, 110));
                nextButton.setVisible(true);
            }
        } else {
            feedbackLabel.setText("Try again. Think about whether this saves or wastes energy.");
            feedbackLabel.setForeground(new Color(183, 28, 28));
        }
    }

    private void updateItem() {
        ChallengeItem current = items[currentIndex];
        statementLabel.setText(current.getStatement());
        statementLabel.setIcon(current.getImage());
        statementLabel.setHorizontalTextPosition(JLabel.CENTER);
        statementLabel.setVerticalTextPosition(JLabel.BOTTOM);
        feedbackLabel.setText("Choose the energy-saving option.");
        feedbackLabel.setForeground(new Color(27, 94, 32));
    }

    private void disableButtons() {
        for (JButton button : choiceButtons) {
            button.setEnabled(false);
        }
    }

    @Override
    public void resetScreen() {
        currentIndex = 0;
        solved = false;
        for (JButton button : choiceButtons) {
            button.setEnabled(true);
        }
        nextButton.setVisible(false);
        updateItem();
    }
}
