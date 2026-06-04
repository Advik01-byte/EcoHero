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

public class RecyclingPanel extends JPanel implements ResettableScreen {
    private final GameController controller;
    private final WasteItem[] items;
    private final JButton[] binButtons;
    private final JLabel itemLabel = new JLabel();
    private final JLabel feedbackLabel = new JLabel("Sort each item into the correct bin.");
    private final JButton nextButton;
    private int currentIndex;
    private boolean solved;

    public RecyclingPanel(GameController controller) {
        this.controller = controller;
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(new Color(248, 252, 248));
        setBorder(BorderFactory.createEmptyBorder(24, 28, 24, 28));

        JLabel title = new JLabel("Recycling Game");
        title.setAlignmentX(CENTER_ALIGNMENT);
        title.setFont(new Font("SansSerif", Font.BOLD, 30));
        title.setForeground(new Color(27, 94, 32));

        JTextArea instructions = new JTextArea(
                "Help EcoHero sort waste into the correct bin. This teaches us how to recycle properly and keep the planet clean."
        );
        instructions.setWrapStyleWord(true);
        instructions.setLineWrap(true);
        instructions.setEditable(false);
        instructions.setOpaque(false);
        instructions.setFont(new Font("SansSerif", Font.PLAIN, 18));
        instructions.setMaximumSize(new Dimension(820, 100));

        JLabel icon = new JLabel(ImageFactory.createRecyclingIcon(230, 170));
        icon.setAlignmentX(CENTER_ALIGNMENT);
        icon.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));

        itemLabel.setAlignmentX(CENTER_ALIGNMENT);
        itemLabel.setFont(new Font("SansSerif", Font.BOLD, 22));
        itemLabel.setForeground(new Color(38, 50, 56));
        itemLabel.setHorizontalTextPosition(JLabel.CENTER);
        itemLabel.setVerticalTextPosition(JLabel.BOTTOM);

        items = new WasteItem[]{
                new WasteItem("Plastic Bottle", WasteItem.RecyclingBin.PLASTIC, ImageFactory.createItemIcon("Plastic Bottle", 160, 120)),
                new WasteItem("Newspaper", WasteItem.RecyclingBin.PAPER, ImageFactory.createItemIcon("Newspaper", 160, 120)),
                new WasteItem("Banana Peel", WasteItem.RecyclingBin.ORGANIC, ImageFactory.createItemIcon("Banana Peel", 160, 120))
        };

        binButtons = new JButton[]{
                createBinButton("Paper Bin"),
                createBinButton("Plastic Bin"),
                createBinButton("Organic Bin")
        };

        binButtons[0].addActionListener(e -> handleChoice(WasteItem.RecyclingBin.PAPER, "Paper goes into the paper bin."));
        binButtons[1].addActionListener(e -> handleChoice(WasteItem.RecyclingBin.PLASTIC, "Plastic items go into the plastic bin."));
        binButtons[2].addActionListener(e -> handleChoice(WasteItem.RecyclingBin.ORGANIC, "Organic waste goes into the organic bin."));

        JPanel binPanel = new JPanel();
        binPanel.setOpaque(false);
        for (JButton button : binButtons) {
            binPanel.add(button);
        }

        feedbackLabel.setAlignmentX(CENTER_ALIGNMENT);
        feedbackLabel.setFont(new Font("SansSerif", Font.BOLD, 16));
        feedbackLabel.setForeground(new Color(27, 94, 32));

        nextButton = new JButton("Continue to Energy Challenge");
        nextButton.setAlignmentX(CENTER_ALIGNMENT);
        ButtonStyles.apply(nextButton, new Color(46, 125, 50), Color.WHITE, new Color(27, 94, 32));
        ButtonStyles.setFont(nextButton, new Font("SansSerif", Font.BOLD, 16));
        nextButton.setVisible(false);
        nextButton.addActionListener(e -> controller.showScreen(EcoHeroFrame.SCREEN_ENERGY));

        add(title);
        add(instructions);
        add(icon);
        add(Box.createVerticalStrut(6));
        add(itemLabel);
        add(Box.createVerticalStrut(10));
        add(binPanel);
        add(Box.createVerticalStrut(10));
        add(feedbackLabel);
        add(Box.createVerticalStrut(8));
        add(nextButton);

        updateItem();
    }

    private JButton createBinButton(String text) {
        JButton button = new JButton(text);
        button.setPreferredSize(new Dimension(180, 46));
        ButtonStyles.apply(button, new Color(236, 248, 236), new Color(25, 55, 35), new Color(76, 132, 84));
        ButtonStyles.setFont(button, new Font("SansSerif", Font.PLAIN, 15));
        return button;
    }

    private void handleChoice(WasteItem.RecyclingBin choice, String correctHint) {
        if (solved || currentIndex >= items.length) {
            return;
        }
        WasteItem current = items[currentIndex];
        if (current.getCorrectBin() == choice) {
            controller.addScore(5);
            feedbackLabel.setText("Correct! " + correctHint);
            feedbackLabel.setForeground(new Color(27, 94, 32));
            currentIndex++;
            if (currentIndex < items.length) {
                updateItem();
            } else {
                solved = true;
                controller.addScore(10);
                controller.completeSection(1);
                disableButtons();
                feedbackLabel.setText("Excellent! You sorted all the waste correctly.");
                nextButton.setVisible(true);
                itemLabel.setText("Recycling complete");
                itemLabel.setIcon(ImageFactory.createRecyclingIcon(160, 120));
            }
        } else {
            feedbackLabel.setText("Try again. That item belongs in a different bin.");
            feedbackLabel.setForeground(new Color(183, 28, 28));
        }
    }

    private void updateItem() {
        WasteItem current = items[currentIndex];
        itemLabel.setText(current.getName());
        itemLabel.setIcon(current.getImage());
    }

    private void disableButtons() {
        for (JButton button : binButtons) {
            button.setEnabled(false);
        }
    }

    @Override
    public void resetScreen() {
        currentIndex = 0;
        solved = false;
        for (JButton button : binButtons) {
            button.setEnabled(true);
        }
        feedbackLabel.setText("Sort each item into the correct bin.");
        feedbackLabel.setForeground(new Color(27, 94, 32));
        nextButton.setVisible(false);
        updateItem();
    }
}
