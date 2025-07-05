package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.RoundRectangle2D;
import game.GameMode;
import game.GameEngine;
import audio.SoundManager;

/**
 * Stunning main window with modern glassmorphism design and beautiful animations
 * Features premium UI/UX with sophisticated visual effects
 */
public class MainWindow extends JFrame {
    private static final long serialVersionUID = 1L;
    
    private GamePanel gamePanel;
    private PlayerVsPlayerPanel pvpPanel;
    private GameEngine gameEngine;
    private SoundManager soundManager;
    
    // UI Components
    private GlassPanel mainPanel;
    private GlassPanel headerPanel;
    private JPanel contentPanel;
    private GlassPanel footerPanel;
    
    // Menu components
    private JLabel titleLabel;
    private JLabel subtitleLabel;
    private GlassButton pvpButton;
    private GlassButton pvcButton;
    private GlassButton settingsButton;
    private GlassButton exitButton;
    
    // Game info components
    private JLabel roundsLabel;
    private JSpinner roundsSpinner;
    private JComboBox<String> difficultyCombo;
    
    // Enhanced premium color palette with magical gradients
    private static final Color PRIMARY_BG = new Color(10, 15, 35);        // Midnight Blue
    private static final Color SECONDARY_BG = new Color(25, 35, 55);      // Deep Slate
    private static final Color GLASS_BG = new Color(255, 255, 255, 25);   // Enhanced Glass
    private static final Color GLASS_BORDER = new Color(255, 255, 255, 60); // Brighter Glass Border
    private static final Color ACCENT_BLUE = new Color(56, 189, 248);     // Sky Blue
    private static final Color ACCENT_PURPLE = new Color(168, 85, 247);   // Electric Purple
    private static final Color ACCENT_GREEN = new Color(16, 185, 129);    // Emerald Green
    private static final Color ACCENT_RED = new Color(248, 113, 113);     // Coral Red
    private static final Color ACCENT_GOLD = new Color(251, 191, 36);     // Bright Gold
    private static final Color ACCENT_PINK = new Color(236, 72, 153);     // Hot Pink
    private static final Color TEXT_PRIMARY = new Color(255, 255, 255);   // Pure White
    private static final Color TEXT_SECONDARY = new Color(156, 163, 175); // Warm Gray
    private static final Color SHADOW_COLOR = new Color(0, 0, 0, 80);     // Deeper Shadow
    private static final Color GLOW_COLOR = new Color(139, 92, 246, 30);  // Purple Glow
    
    public MainWindow() {
        initializeComponents();
        setupLayout();
        setupEventHandlers();
        setupWindow();
    }
    
    private void initializeComponents() {
        // Initialize managers
        soundManager = new SoundManager();
        gameEngine = new GameEngine();
        
        // Premium glassmorphism panels
        mainPanel = new GlassPanel(PRIMARY_BG);
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        
        headerPanel = new GlassPanel(GLASS_BG);
        contentPanel = new JPanel(new CardLayout());
        contentPanel.setBackground(PRIMARY_BG);
        footerPanel = new GlassPanel(GLASS_BG);
        
        // Stunning title with gradient effect
        titleLabel = createGradientLabel("🎮 ROCK PAPER SCISSORS", 42, true);
        subtitleLabel = createGradientLabel("Choose Your Game Mode", 16, false);
        
        // Beautiful glassmorphism buttons
        pvpButton = createGlassButton("👥  Player vs Player", ACCENT_BLUE, "Battle against a friend");
        pvcButton = createGlassButton("🤖  Player vs Computer", ACCENT_PURPLE, "Challenge the AI");
        settingsButton = createGlassButton("⚙️  Settings", ACCENT_GOLD, "Customize your experience");
        exitButton = createGlassButton("🚪  Exit", ACCENT_RED, "Leave the game");
        
        // Premium game configuration
        roundsLabel = createStyledLabel("Rounds:", 14, TEXT_SECONDARY);
        roundsSpinner = createGlassSpinner();
        
        String[] difficulties = {"🟢 Easy", "🟡 Medium", "🔴 Hard"};
        difficultyCombo = createGlassComboBox(difficulties);
        
        // Initialize game panels
        gamePanel = new GamePanel();
        pvpPanel = new PlayerVsPlayerPanel();
    }
    
    private JPanel createCleanPanel(Color backgroundColor) {
        JPanel panel = new JPanel();
        panel.setBackground(backgroundColor);
        return panel;
    }
    
    private void styleCleanSpinner(JSpinner spinner) {
        spinner.setBackground(SECONDARY_BG);
        spinner.setForeground(TEXT_PRIMARY);
        spinner.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(GLASS_BORDER, 1),
            BorderFactory.createEmptyBorder(5, 8, 5, 8)
        ));
        
        JComponent editor = spinner.getEditor();
        if (editor instanceof JSpinner.DefaultEditor) {
            JTextField textField = ((JSpinner.DefaultEditor) editor).getTextField();
            textField.setBackground(SECONDARY_BG);
            textField.setForeground(TEXT_PRIMARY);
            textField.setBorder(BorderFactory.createEmptyBorder());
            textField.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        }
    }
    
    private void styleCleanComboBox(JComboBox<String> comboBox) {
        comboBox.setBackground(SECONDARY_BG);
        comboBox.setForeground(TEXT_PRIMARY);
        comboBox.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(GLASS_BORDER, 1),
            BorderFactory.createEmptyBorder(5, 8, 5, 8)
        ));
        comboBox.setPreferredSize(new Dimension(100, 30));
        comboBox.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        
        // Clean dropdown renderer
        comboBox.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index,
                    boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                setBackground(isSelected ? ACCENT_BLUE : SECONDARY_BG);
                setForeground(TEXT_PRIMARY);
                setBorder(BorderFactory.createEmptyBorder(8, 12, 8, 12));
                setFont(new Font("Segoe UI", Font.PLAIN, 12));
                return this;
            }
        });
    }
    
    private JButton createModernButton(String text, Color color, String tooltip) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        button.setForeground(TEXT_PRIMARY);
        button.setBackground(color);
        button.setBorder(BorderFactory.createCompoundBorder(
    BorderFactory.createLineBorder(color.darker(), 1),
    BorderFactory.createEmptyBorder(12, 24, 12, 24)
));
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setPreferredSize(new Dimension(200, 50));
        button.setToolTipText(tooltip);
        
        // Subtle hover effects - no overhyped animations
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(color.brighter());
                button.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(TEXT_PRIMARY, 1),
                    BorderFactory.createEmptyBorder(12, 24, 12, 24)
                ));
            }
            
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(color);
                button.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(color.darker(), 1),
                    BorderFactory.createEmptyBorder(12, 24, 12, 24)
                ));
            }
        });
        
        return button;
    }
    
    private void setupLayout() {
        // Stunning glassmorphism header
        headerPanel.setLayout(new BorderLayout());
        headerPanel.setBorder(BorderFactory.createEmptyBorder(40, 40, 30, 40));
        
        GlassPanel titlePanel = new GlassPanel(new Color(0, 0, 0, 0));
        titlePanel.setLayout(new BoxLayout(titlePanel, BoxLayout.Y_AXIS));
        
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        subtitleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        titlePanel.add(titleLabel);
        titlePanel.add(Box.createVerticalStrut(10));
        titlePanel.add(subtitleLabel);
        
        headerPanel.add(titlePanel, BorderLayout.CENTER);
        
        // Premium main menu with glassmorphism
        GlassPanel menuPanel = new GlassPanel(new Color(0, 0, 0, 0));
        menuPanel.setLayout(new BoxLayout(menuPanel, BoxLayout.Y_AXIS));
        menuPanel.setBorder(BorderFactory.createEmptyBorder(50, 60, 50, 60));
        
        // Center align all components
        pvpButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        pvcButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        settingsButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        exitButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        // Add buttons with elegant spacing
        menuPanel.add(Box.createVerticalGlue());
        menuPanel.add(pvpButton);
        menuPanel.add(Box.createVerticalStrut(20));
        menuPanel.add(pvcButton);
        menuPanel.add(Box.createVerticalStrut(20));
        menuPanel.add(settingsButton);
        menuPanel.add(Box.createVerticalStrut(20));
        menuPanel.add(exitButton);
        menuPanel.add(Box.createVerticalGlue());
        
        // Premium configuration panel
        GlassPanel configPanel = new GlassPanel(GLASS_BG);
        configPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 25, 15));
        configPanel.setBorder(BorderFactory.createEmptyBorder(15, 25, 15, 25));
        
        configPanel.add(roundsLabel);
        configPanel.add(roundsSpinner);
        
        JLabel difficultyLabel = createStyledLabel("Difficulty:", 14, TEXT_SECONDARY);
        configPanel.add(difficultyLabel);
        configPanel.add(difficultyCombo);
        
        menuPanel.add(configPanel);
        menuPanel.add(Box.createVerticalStrut(25));
        
        // Elegant footer - remove version info
        footerPanel.setBorder(BorderFactory.createEmptyBorder(15, 0, 15, 0));
        footerPanel.setLayout(new BorderLayout());
        footerPanel.setVisible(false);
        
        // Add panels to content
        contentPanel.add(menuPanel, "MENU");
        contentPanel.add(gamePanel, "GAME");
        contentPanel.add(pvpPanel, "PVP");
        
        // Main panel assembly
        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(contentPanel, BorderLayout.CENTER);
        mainPanel.add(footerPanel, BorderLayout.SOUTH);
        
        add(mainPanel);
    }
    
    private void setupEventHandlers() {
        pvpButton.addActionListener(e -> {
            if (soundManager != null) soundManager.playSound("gesture_select");
            startPlayerVsPlayerGame();
        });
        
        pvcButton.addActionListener(e -> {
            if (soundManager != null) soundManager.playSound("gesture_select");
            startGame(GameMode.PLAYER_VS_COMPUTER);
        });
        
        settingsButton.addActionListener(e -> {
            if (soundManager != null) soundManager.playSound("gesture_select");
            showSettings();
        });
        
        exitButton.addActionListener(e -> {
            if (soundManager != null) soundManager.playSound("gesture_select");
            // Clean exit with proper cleanup
            if (soundManager != null) {
                soundManager.cleanup();
            }
            System.exit(0);
        });
    }
    
    private void setupWindow() {
        setTitle("Rock Paper Scissors - Enhanced Edition");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);
        setResizable(false);
        
        // Set window icon if available
        try {
            ImageIcon icon = new ImageIcon(getClass().getResource("/images/icon.png"));
            if (icon.getIconWidth() > 0) {
                setIconImage(icon.getImage());
            }
        } catch (Exception e) {
            // Use default icon if file not found
        }
        
        // Show menu by default
        CardLayout cl = (CardLayout) contentPanel.getLayout();
        cl.show(contentPanel, "MENU");
        
        // Add window listener for cleanup
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                if (soundManager != null) {
                    soundManager.cleanup();
                }
            }
        });
    }
    
    private void startPlayerVsPlayerGame() {
        int rounds = (Integer) roundsSpinner.getValue();

        // Set window size larger for PvP mode
        setSize(1400, 800);
        setLocationRelativeTo(null);

        // Switch to PvP panel
        pvpPanel.setSoundManager(soundManager);
        pvpPanel.startGame(rounds);

        CardLayout cl = (CardLayout) contentPanel.getLayout();
        cl.show(contentPanel, "PVP");

        // Update title
        titleLabel.setText("Player vs Player");
        subtitleLabel.setText("Round 1 of " + rounds);
    }
    
    private void startGame(GameMode mode) {
        int rounds = (Integer) roundsSpinner.getValue();
        String difficulty = (String) difficultyCombo.getSelectedItem();
        
        // Set window size larger for PvC mode (same as PvP)
        setSize(1400, 800);
        setLocationRelativeTo(null);
        
        // Switch to game panel for PvC
        gamePanel.setSoundManager(soundManager);
        gamePanel.startGame(mode, rounds, difficulty);
        
        CardLayout cl = (CardLayout) contentPanel.getLayout();
        cl.show(contentPanel, "GAME");
        
        // Update title
        titleLabel.setText("Player vs Computer");
        subtitleLabel.setText("Choose your difficulty and click 'New Game'");
    }
    
    public void returnToMenu() {
        CardLayout cl = (CardLayout) contentPanel.getLayout();
        cl.show(contentPanel, "MENU");
        
        // Reset window size to original
        setSize(800, 600);
        setLocationRelativeTo(null);
        
        // Reset title
        titleLabel.setText("🎮 ROCK PAPER SCISSORS");
        subtitleLabel.setText("Choose Your Game Mode");
    }
    
    private void showSettings() {
        SettingsDialog dialog = new SettingsDialog(this, soundManager);
        dialog.setVisible(true);
    }
    
    // ============ PREMIUM UI COMPONENTS ============
    
    private JLabel createGradientLabel(String text, int fontSize, boolean bold) {
        JLabel label = new JLabel(text, SwingConstants.CENTER) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
                
                // Create beautiful gradient text
                GradientPaint gradient = new GradientPaint(
                    0, 0, ACCENT_BLUE.brighter(),
                    getWidth(), getHeight(), ACCENT_PURPLE.brighter()
                );
                g2d.setPaint(gradient);
                
                FontMetrics fm = g2d.getFontMetrics();
                int x = (getWidth() - fm.stringWidth(getText())) / 2;
                int y = (getHeight() + fm.getAscent()) / 2;
                
                // Add subtle shadow
                g2d.setColor(SHADOW_COLOR);
                g2d.drawString(getText(), x + 2, y + 2);
                
                // Draw gradient text
                g2d.setPaint(gradient);
                g2d.drawString(getText(), x, y);
                
                g2d.dispose();
            }
        };
        
        label.setFont(new Font("Segoe UI", bold ? Font.BOLD : Font.PLAIN, fontSize));
        label.setOpaque(false);
        return label;
    }
    
    private JLabel createStyledLabel(String text, int fontSize, Color color) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Segoe UI", Font.PLAIN, fontSize));
        label.setForeground(color);
        return label;
    }
    
    private GlassButton createGlassButton(String text, Color accentColor, String tooltip) {
        GlassButton button = new GlassButton(text, accentColor);
        button.setToolTipText(tooltip);
        button.setPreferredSize(new Dimension(280, 60));
        button.setMaximumSize(new Dimension(280, 60));
        return button;
    }
    
    private JSpinner createGlassSpinner() {
        JSpinner spinner = new JSpinner(new SpinnerNumberModel(5, 1, 99, 1));
        spinner.setPreferredSize(new Dimension(80, 35));
        
        // Style with glass effect
        spinner.setBackground(GLASS_BG);
        spinner.setForeground(TEXT_PRIMARY);
        spinner.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(GLASS_BORDER, 1, true),
            BorderFactory.createEmptyBorder(8, 12, 8, 12)
        ));
        
        JComponent editor = spinner.getEditor();
        if (editor instanceof JSpinner.DefaultEditor) {
            JTextField textField = ((JSpinner.DefaultEditor) editor).getTextField();
            textField.setBackground(new Color(0, 0, 0, 0));
            textField.setForeground(TEXT_PRIMARY);
            textField.setBorder(BorderFactory.createEmptyBorder());
            textField.setFont(new Font("Segoe UI", Font.BOLD, 14));
            textField.setHorizontalAlignment(JTextField.CENTER);
        }
        
        return spinner;
    }
    
    private JComboBox<String> createGlassComboBox(String[] items) {
        JComboBox<String> comboBox = new JComboBox<>(items);
        comboBox.setSelectedIndex(1);
        comboBox.setPreferredSize(new Dimension(120, 35));
        comboBox.setBackground(GLASS_BG);
        comboBox.setForeground(TEXT_PRIMARY);
        comboBox.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(GLASS_BORDER, 1, true),
            BorderFactory.createEmptyBorder(8, 12, 8, 12)
        ));
        comboBox.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        
        // Glass renderer
        comboBox.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index,
                    boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                setBackground(isSelected ? ACCENT_BLUE : SECONDARY_BG);
                setForeground(TEXT_PRIMARY);
                setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
                setFont(new Font("Segoe UI", Font.PLAIN, 13));
                return this;
            }
        });
        
        return comboBox;
    }
    
    // ============ CUSTOM GLASS COMPONENTS ============
    
    private class GlassPanel extends JPanel {
        private Color baseColor;
        private Timer backgroundTimer;
        private float animationPhase = 0f;
        
        public GlassPanel(Color baseColor) {
            this.baseColor = baseColor;
            setOpaque(false);
            
            // Start subtle background animation for main panel
            if (baseColor.equals(PRIMARY_BG)) {
                backgroundTimer = new Timer(50, e -> {
                    animationPhase += 0.02f;
                    if (animationPhase > Math.PI * 2) animationPhase = 0f;
                    repaint();
                });
                backgroundTimer.start();
            }
        }
        
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g.create();
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            
            // Create enhanced glassmorphism effect
            if (baseColor.getAlpha() < 255) {
                // Glass effect with enhanced blur simulation
                g2d.setColor(baseColor);
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 25, 25);
                
                // Add animated glow border
                Color glowBorder = new Color(
                    GLASS_BORDER.getRed(),
                    GLASS_BORDER.getGreen(),
                    GLASS_BORDER.getBlue(),
                    (int) (GLASS_BORDER.getAlpha() + Math.sin(animationPhase) * 20)
                );
                g2d.setColor(glowBorder);
                g2d.setStroke(new BasicStroke(2f));
                g2d.drawRoundRect(1, 1, getWidth() - 2, getHeight() - 2, 25, 25);
                
                // Add inner highlight
                g2d.setColor(new Color(255, 255, 255, 15));
                g2d.setStroke(new BasicStroke(1f));
                g2d.drawRoundRect(3, 3, getWidth() - 6, getHeight() - 6, 22, 22);
                
            } else if (baseColor.equals(PRIMARY_BG)) {
                // Animated starfield background for main panel
                RadialGradientPaint radialGradient = new RadialGradientPaint(
                    getWidth() / 2f, getHeight() / 2f, Math.max(getWidth(), getHeight()) / 2f,
                    new float[]{0f, 0.7f, 1f},
                    new Color[]{
                        new Color(20, 30, 60),
                        new Color(15, 20, 45),
                        PRIMARY_BG
                    }
                );
                g2d.setPaint(radialGradient);
                g2d.fillRect(0, 0, getWidth(), getHeight());
                
                // Add floating particles/stars
                g2d.setColor(new Color(255, 255, 255, 30));
                for (int i = 0; i < 50; i++) {
                    float x = (float) ((i * 37.5 + animationPhase * 10) % getWidth());
                    float y = (float) ((i * 23.7 + animationPhase * 5) % getHeight());
                    float size = (float) (1 + Math.sin(animationPhase + i) * 0.5);
                    g2d.fillOval((int) x, (int) y, (int) size, (int) size);
                }
                
                // Add subtle moving gradients
                float alpha = (float) (0.1 + Math.sin(animationPhase) * 0.05);
                Color movingColor = new Color(ACCENT_PURPLE.getRed(), ACCENT_PURPLE.getGreen(), 
                                             ACCENT_PURPLE.getBlue(), (int) (alpha * 255));
                g2d.setColor(movingColor);
                g2d.fillOval((int) (getWidth() * 0.8 + Math.cos(animationPhase) * 50), 
                           (int) (getHeight() * 0.2 + Math.sin(animationPhase) * 30), 100, 100);
                
                Color movingColor2 = new Color(ACCENT_BLUE.getRed(), ACCENT_BLUE.getGreen(), 
                                              ACCENT_BLUE.getBlue(), (int) (alpha * 255));
                g2d.setColor(movingColor2);
                g2d.fillOval((int) (getWidth() * 0.2 + Math.sin(animationPhase * 0.7) * 40), 
                           (int) (getHeight() * 0.8 + Math.cos(animationPhase * 0.7) * 25), 80, 80);
                
            } else {
                // Enhanced gradient for other panels
                GradientPaint gradient = new GradientPaint(
                    0, 0, baseColor,
                    getWidth(), getHeight(), baseColor.darker()
                );
                g2d.setPaint(gradient);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
            
            g2d.dispose();
        }
    }
    
    private class GlassButton extends JButton {
        private Color accentColor;
        private boolean isHovered = false;
        private Timer animationTimer;
        private float glowIntensity = 0f;
        
        public GlassButton(String text, Color accentColor) {
            super(text);
            this.accentColor = accentColor;
            
            setFont(new Font("Segoe UI", Font.BOLD, 16));
            setForeground(TEXT_PRIMARY);
            setFocusPainted(false);
            setBorderPainted(false);
            setContentAreaFilled(false);
            setCursor(new Cursor(Cursor.HAND_CURSOR));
            
            // Smooth hover animation
            addMouseListener(new java.awt.event.MouseAdapter() {
                @Override
                public void mouseEntered(java.awt.event.MouseEvent e) {
                    isHovered = true;
                    startGlowAnimation(true);
                }
                
                @Override
                public void mouseExited(java.awt.event.MouseEvent e) {
                    isHovered = false;
                    startGlowAnimation(false);
                }
            });
        }
        
        private void startGlowAnimation(boolean increase) {
            if (animationTimer != null) animationTimer.stop();
            
            animationTimer = new Timer(16, e -> {
                if (increase) {
                    glowIntensity = Math.min(1f, glowIntensity + 0.1f);
                } else {
                    glowIntensity = Math.max(0f, glowIntensity - 0.1f);
                }
                
                repaint();
                
                if ((increase && glowIntensity >= 1f) || (!increase && glowIntensity <= 0f)) {
                    ((Timer) e.getSource()).stop();
                }
            });
            animationTimer.start();
        }
        
        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2d = (Graphics2D) g.create();
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            
            // Glass background
            Color bgColor = new Color(
                accentColor.getRed(),
                accentColor.getGreen(),
                accentColor.getBlue(),
                (int) (30 + glowIntensity * 40)
            );
            g2d.setColor(bgColor);
            g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);
            
            // Glow effect on hover
            if (glowIntensity > 0) {
                Color glowColor = new Color(
                    accentColor.getRed(),
                    accentColor.getGreen(),
                    accentColor.getBlue(),
                    (int) (glowIntensity * 100)
                );
                g2d.setColor(glowColor);
                g2d.setStroke(new BasicStroke(3f));
                g2d.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 15, 15);
            }
            
            // Glass border
            g2d.setColor(GLASS_BORDER);
            g2d.setStroke(new BasicStroke(1.5f));
            g2d.drawRoundRect(1, 1, getWidth() - 2, getHeight() - 2, 15, 15);
            
            g2d.dispose();
            super.paintComponent(g);
        }
    }
    
    public static void main(String[] args) {
        try {
            // Modern look and feel
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            // Use default if unavailable
        }
        
        SwingUtilities.invokeLater(() -> {
            new MainWindow().setVisible(true);
        });
    }
}
