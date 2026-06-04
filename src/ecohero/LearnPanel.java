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

public class LearnPanel extends JPanel implements ResettableScreen {
    private final GameController controller;
    private final Slide[] slides;
    private final JLabel slideTitle = new JLabel();
    private final JLabel slideCounter = new JLabel();
    private final JLabel slideImage = new JLabel();
    private final JTextArea slideText = new JTextArea();
    private final JButton prevButton = new JButton("Previous");
    private final JButton nextButton = new JButton("Next");
    private final JButton startAdventureButton = new JButton("Start Adventure");
    private int currentIndex;

    public LearnPanel(GameController controller) {
        this.controller = controller;
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(new Color(244, 250, 244));
        setBorder(BorderFactory.createEmptyBorder(24, 28, 24, 28));

        slides = new Slide[]{
                new Slide(
                        "What is Pollution?",
                        "Pollution means making the air, water, or land dirty. It can harm plants, animals, and people.",
                        ImageFactory.createPollutionIcon(260, 180)
                ),
                new Slide(
                        "Why Recycle?",
                        "Recycling means using old things again instead of throwing them away. It helps reduce waste.",
                        ImageFactory.createRecyclingIcon(260, 180)
                ),
                new Slide(
                        "How to Save Energy",
                        "We can save energy by switching off lights, using sunlight in the daytime, and not wasting electricity.",
                        ImageFactory.createEnergyIcon(260, 180)
                ),
                new Slide(
                        "Be an EcoHero",
                        "Small actions can make a big difference. Plant trees, reduce waste, and keep your surroundings clean.",
                        ImageFactory.createHeroIcon(240, 240)
                )
        };

        JLabel title = new JLabel("Learn");
        title.setAlignmentX(CENTER_ALIGNMENT);
        title.setFont(new Font("SansSerif", Font.BOLD, 32));
        title.setForeground(new Color(20, 94, 68));

        JLabel subtitle = new JLabel("A short slideshow before the game begins");
        subtitle.setAlignmentX(CENTER_ALIGNMENT);
        subtitle.setFont(new Font("SansSerif", Font.PLAIN, 18));
        subtitle.setForeground(new Color(70, 100, 75));

        slideCounter.setAlignmentX(CENTER_ALIGNMENT);
        slideCounter.setFont(new Font("SansSerif", Font.BOLD, 16));
        slideCounter.setForeground(new Color(72, 72, 72));

        slideTitle.setAlignmentX(CENTER_ALIGNMENT);
        slideTitle.setFont(new Font("SansSerif", Font.BOLD, 26));
        slideTitle.setForeground(new Color(24, 90, 65));
        slideTitle.setHorizontalAlignment(JLabel.CENTER);

        slideImage.setAlignmentX(CENTER_ALIGNMENT);
        slideImage.setBorder(BorderFactory.createEmptyBorder(12, 0, 12, 0));

        slideText.setWrapStyleWord(true);
        slideText.setLineWrap(true);
        slideText.setEditable(false);
        slideText.setOpaque(false);
        slideText.setFont(new Font("SansSerif", Font.PLAIN, 18));
        slideText.setForeground(new Color(40, 40, 40));
        slideText.setAlignmentX(CENTER_ALIGNMENT);
        slideText.setMaximumSize(new Dimension(800, 180));
        slideText.setBorder(BorderFactory.createEmptyBorder(8, 18, 8, 18));

        ButtonStyles.apply(prevButton, new Color(255, 248, 230), new Color(110, 67, 0), new Color(189, 134, 0));
        ButtonStyles.setFont(prevButton, new Font("SansSerif", Font.BOLD, 15));
        prevButton.addActionListener(e -> showSlide(currentIndex - 1));

        ButtonStyles.apply(nextButton, new Color(46, 125, 50), Color.WHITE, new Color(27, 94, 32));
        ButtonStyles.setFont(nextButton, new Font("SansSerif", Font.BOLD, 15));
        nextButton.addActionListener(e -> {
            if (currentIndex < slides.length - 1) {
                showSlide(currentIndex + 1);
            } else {
                controller.showScreen(EcoHeroFrame.SCREEN_MENU);
            }
        });

        ButtonStyles.apply(startAdventureButton, new Color(2, 119, 189), Color.WHITE, new Color(1, 87, 155));
        ButtonStyles.setFont(startAdventureButton, new Font("SansSerif", Font.BOLD, 16));
        startAdventureButton.addActionListener(e -> {
            controller.completeSection(0);
            controller.showScreen(EcoHeroFrame.SCREEN_POLLUTION);
        });

        JPanel navRow = new JPanel();
        navRow.setOpaque(false);
        navRow.add(prevButton);
        navRow.add(Box.createHorizontalStrut(12));
        navRow.add(nextButton);

        add(title);
        add(Box.createVerticalStrut(6));
        add(subtitle);
        add(Box.createVerticalStrut(8));
        add(slideCounter);
        add(slideTitle);
        add(slideImage);
        add(slideText);
        add(Box.createVerticalStrut(8));
        add(navRow);
        add(Box.createVerticalStrut(10));
        add(startAdventureButton);

        showSlide(0);
    }

    private void showSlide(int index) {
        if (index < 0 || index >= slides.length) {
            return;
        }
        currentIndex = index;
        Slide slide = slides[currentIndex];
        slideCounter.setText("Slide " + (currentIndex + 1) + " of " + slides.length);
        slideTitle.setText(slide.title);
        slideImage.setIcon(slide.image);
        slideText.setText(slide.body);
        prevButton.setEnabled(currentIndex > 0);
        nextButton.setEnabled(true);
        if (currentIndex == slides.length - 1) {
            nextButton.setText("Back to Home");
        } else {
            nextButton.setText("Next");
        }
    }

    @Override
    public void resetScreen() {
        showSlide(0);
    }

    private static final class Slide {
        private final String title;
        private final String body;
        private final javax.swing.ImageIcon image;

        private Slide(String title, String body, javax.swing.ImageIcon image) {
            this.title = title;
            this.body = body;
            this.image = image;
        }
    }
}
