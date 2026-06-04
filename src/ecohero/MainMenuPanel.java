package ecohero;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionListener;

public class MainMenuPanel extends JPanel implements ResettableScreen {
    private final GameController controller;

    public MainMenuPanel(GameController controller) {
        this.controller = controller;
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(new Color(240, 248, 240));
        setBorder(BorderFactory.createEmptyBorder(28, 30, 28, 30));

        JLabel title = new JLabel("Welcome to EcoHero");
        title.setAlignmentX(CENTER_ALIGNMENT);
        title.setFont(new Font("SansSerif", Font.BOLD, 34));
        title.setForeground(new Color(24, 90, 65));

        JLabel subtitle = new JLabel("Become an environmental hero and save the Earth");
        subtitle.setAlignmentX(CENTER_ALIGNMENT);
        subtitle.setFont(new Font("SansSerif", Font.PLAIN, 18));
        subtitle.setForeground(new Color(67, 97, 74));

        ImageIcon icon = ImageFactory.createHeroIcon(240, 240);
        JLabel image = new JLabel(icon);
        image.setAlignmentX(CENTER_ALIGNMENT);
        image.setBorder(BorderFactory.createEmptyBorder(16, 0, 16, 0));

        JTextArea info = new JTextArea(
                "This science game helps students learn about pollution, recycling, energy saving, and the environment in a fun way.\n\n"
                        + "Follow the steps, earn points, and complete the final quiz to become an EcoHero."
        );
        info.setWrapStyleWord(true);
        info.setLineWrap(true);
        info.setEditable(false);
        info.setOpaque(false);
        info.setFont(new Font("SansSerif", Font.PLAIN, 18));
        info.setForeground(new Color(40, 40, 40));
        info.setAlignmentX(CENTER_ALIGNMENT);
        info.setMaximumSize(new Dimension(760, 170));
        info.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        JButton startButton = createButton("Start Adventure", new Color(46, 125, 50));
        JButton howToPlayButton = createButton("How to Play", new Color(2, 119, 189));

        startButton.addActionListener(e -> controller.showScreen(EcoHeroFrame.SCREEN_POLLUTION));
        howToPlayButton.addActionListener(e -> javax.swing.JOptionPane.showMessageDialog(
                this,
                "Read the information, answer the questions, sort the waste, complete the energy challenge, and finish the quiz.",
                "How to Play",
                javax.swing.JOptionPane.INFORMATION_MESSAGE
        ));

        JPanel buttonRow = new JPanel();
        buttonRow.setOpaque(false);
        buttonRow.add(startButton);
        buttonRow.add(Box.createHorizontalStrut(14));
        buttonRow.add(howToPlayButton);
        buttonRow.setAlignmentX(CENTER_ALIGNMENT);

        add(title);
        add(Box.createVerticalStrut(8));
        add(subtitle);
        add(image);
        add(info);
        add(Box.createVerticalStrut(8));
        add(buttonRow);
    }

    private JButton createButton(String text, Color color) {
        JButton button = new JButton(text);
        button.setFocusPainted(false);
        button.setBackground(color);
        button.setForeground(Color.WHITE);
        button.setFont(new Font("SansSerif", Font.BOLD, 16));
        button.setBorder(BorderFactory.createEmptyBorder(12, 22, 12, 22));
        return button;
    }

    @Override
    public void resetScreen() {
        // Menu has no internal state.
    }
}
