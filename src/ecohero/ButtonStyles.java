package ecohero;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.plaf.basic.BasicButtonUI;
import java.awt.Color;
import java.awt.Font;

public final class ButtonStyles {
    private ButtonStyles() {
    }

    public static void apply(JButton button, Color background, Color foreground, Color borderColor) {
        button.setUI(new BasicButtonUI());
        button.setOpaque(true);
        button.setContentAreaFilled(true);
        button.setBorderPainted(true);
        button.setFocusPainted(false);
        button.setRolloverEnabled(true);
        button.setBackground(background);
        button.setForeground(foreground);
        button.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(borderColor, 2),
                BorderFactory.createEmptyBorder(10, 16, 10, 16)
        ));
    }

    public static void setFont(JButton button, Font font) {
        button.setFont(font);
    }
}
