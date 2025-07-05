package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.RoundRectangle2D;
import game.Gesture;
import game.GameMode;
import audio.SoundManager;

/**
 * Professional Player vs Player panel with proper timeout handling
 */
public class PlayerVsPlayerPanel extends JPanel implements KeyListener {
    private static final long serialVersionUID = 1L;

    @Override
    public Dimension getPreferredSize() {
        // Make the PvP panel much larger for better layout
        return new Dimension(1300, 700);
    }
    
    private SoundManager soundManager;
    private int maxRounds = 5;
    private int currentRound = 1;
    private int player1Score = 0;
    private int player2Score = 0;
    private boolean waitingForInput = false;
    private Gesture player1Choice = null;
    private Gesture player2Choice = null;
    private boolean gameInProgress = false;
    
    // UI Components
    private JPanel leftPlayerPanel;
    private JPanel rightPlayerPanel;
    private JPanel centerPanel;
    private JPanel topPanel;
    private JPanel bottomPanel;
    
    // Player 1 (Left) components
    private JLabel player1NameLabel;
    private JLabel player1ScoreLabel;
    private JLabel player1GestureLabel;
    private JLabel player1StatusLabel;
    private JPanel player1ControlsPanel;
    private JButton player1RockBtn;
    private JButton player1PaperBtn;
    private JButton player1ScissorsBtn;
    
    // Player 2 (Right) components
    private JLabel player2NameLabel;
    private JLabel player2ScoreLabel;
    private JLabel player2GestureLabel;
    private JLabel player2StatusLabel;
    private JPanel player2ControlsPanel;
    private JButton player2RockBtn;
    private JButton player2PaperBtn;
    private JButton player2ScissorsBtn;
    
    // Center components
    private JLabel roundLabel;
    private JLabel countdownLabel;
    private JLabel resultLabel;
    private JLabel vsLabel;
    private JLabel instructionLabel;
    
    // Control components
    private JButton backButton;
    private JButton newGameButton;
    private JButton pauseButton;
    
    // Images
    private ImageIcon rockIcon;
    private ImageIcon paperIcon;
    private ImageIcon scissorsIcon;
    private ImageIcon questionIcon;
    
    // Timers
    private Timer countdownTimer;
    private Timer animationTimer;
    private int countdown;
    private int animationFrame = 0;
    
    // Colors - Professional theme
    private Color player1Color = new Color(41, 128, 185);
    private Color player2Color = new Color(39, 174, 96);
    private Color backgroundColor = new Color(44, 62, 80);
    private Color accentColor = new Color(231, 76, 60);
    private Color warningColor = new Color(230, 126, 34);
    
    public PlayerVsPlayerPanel() {
        loadImages();
        initializeComponents();
        setupLayout();
        setupEventHandlers();
        setFocusable(true);
        addKeyListener(this);
    }
    
    private void loadImages() {
        try {
            rockIcon = loadScaledIcon("/images/rock_hand.png", 120, 120);
            paperIcon = loadScaledIcon("/images/paper_hand.png", 120, 120);
            scissorsIcon = loadScaledIcon("/images/scissors_hand.png", 120, 120);
            questionIcon = createQuestionIcon();
            System.out.println("PvP Panel: Images loaded successfully");
        } catch (Exception e) {
            System.out.println("PvP Panel: Error loading images, using fallbacks");
            createFallbackIcons();
        }
    }
    
    private ImageIcon loadScaledIcon(String path, int width, int height) {
        try {
            ImageIcon originalIcon = new ImageIcon(getClass().getResource(path));
            if (originalIcon.getIconWidth() > 0) {
                Image image = originalIcon.getImage();
                Image scaledImage = image.getScaledInstance(width, height, Image.SCALE_SMOOTH);
                return new ImageIcon(scaledImage);
            }
        } catch (Exception e) {
            System.out.println("Could not load image: " + path);
        }
        return null;
    }
    
    private ImageIcon createQuestionIcon() {
        int size = 120;
        java.awt.image.BufferedImage img = new java.awt.image.BufferedImage(size, size, java.awt.image.BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = img.createGraphics();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        // Modern gradient background
        GradientPaint gradient = new GradientPaint(0, 0, new Color(52, 73, 94), 0, size, new Color(44, 62, 80));
        g2.setPaint(gradient);
        g2.fillRoundRect(5, 5, size-10, size-10, 25, 25);
        
        // Professional border
        g2.setColor(new Color(236, 240, 241));
        g2.setStroke(new BasicStroke(3));
        g2.drawRoundRect(5, 5, size-10, size-10, 25, 25);
        
        // Question mark with better typography
        g2.setFont(new Font("Segoe UI", Font.BOLD, 48));
        FontMetrics fm = g2.getFontMetrics();
        String text = "?";
        int x = (size - fm.stringWidth(text)) / 2;
        int y = (size + fm.getAscent()) / 2;
        
        // Text shadow for depth
        g2.setColor(new Color(0, 0, 0, 80));
        g2.drawString(text, x + 2, y + 2);
        
        // Main text
        g2.setColor(new Color(236, 240, 241));
        g2.drawString(text, x, y);
        
        g2.dispose();
        return new ImageIcon(img);
    }
    
    private void createFallbackIcons() {
        rockIcon = createColorIcon(new Color(231, 76, 60), "ROCK", "✊");
        paperIcon = createColorIcon(new Color(241, 196, 15), "PAPER", "✋");
        scissorsIcon = createColorIcon(new Color(155, 89, 182), "SCISSORS", "✌");
        questionIcon = createColorIcon(new Color(149, 165, 166), "?", "❓");
    }
    
    private ImageIcon createColorIcon(Color color, String text, String emoji) {
        int size = 120;
        java.awt.image.BufferedImage img = new java.awt.image.BufferedImage(size, size, java.awt.image.BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = img.createGraphics();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        // Professional gradient
        GradientPaint gradient = new GradientPaint(0, 0, color.brighter(), 0, size, color.darker());
        g2.setPaint(gradient);
        g2.fillRoundRect(5, 5, size-10, size-10, 25, 25);
        
        // Clean border
        g2.setColor(Color.WHITE);
        g2.setStroke(new BasicStroke(3));
        g2.drawRoundRect(5, 5, size-10, size-10, 25, 25);
        
        // Emoji
        g2.setFont(new Font("Segoe UI Emoji", Font.BOLD, 40));
        FontMetrics fm = g2.getFontMetrics();
        int x = (size - fm.stringWidth(emoji)) / 2;
        int y = (size + fm.getAscent()) / 2 - 10;
        g2.drawString(emoji, x, y);
        
        // Text label
        g2.setFont(new Font("Segoe UI", Font.BOLD, 12));
        fm = g2.getFontMetrics();
        x = (size - fm.stringWidth(text)) / 2;
        y = size - 15;
        g2.drawString(text, x, y);
        
        g2.dispose();
        return new ImageIcon(img);
    }
    
    private void initializeComponents() {
        // Main panels with professional styling
        leftPlayerPanel = new EnhancedPanel(player1Color);
        rightPlayerPanel = new EnhancedPanel(player2Color);
        centerPanel = new EnhancedPanel(backgroundColor);
        topPanel = new EnhancedPanel(new Color(52, 73, 94));
        bottomPanel = new EnhancedPanel(new Color(52, 73, 94));
        
        // Set main background
        setBackground(backgroundColor);
        
        // Player 1 components with modern styling
        player1NameLabel = createEnhancedLabel("PLAYER 1", 26, player1Color, true);
        player1ScoreLabel = createEnhancedLabel("Score: 0", 18, new Color(236, 240, 241), false);
        player1GestureLabel = createEnhancedImageLabel(questionIcon);
        player1StatusLabel = createEnhancedLabel("Ready to Play", 14, new Color(189, 195, 199), false);
        
        // Player 1 controls with improved design
        player1ControlsPanel = new EnhancedPanel(new Color(52, 152, 219, 40));
        player1RockBtn = createEnhancedButton("A", "ROCK", "✊", new Color(231, 76, 60));
        player1PaperBtn = createEnhancedButton("S", "PAPER", "✋", new Color(241, 196, 15));
        player1ScissorsBtn = createEnhancedButton("D", "SCISSORS", "✌", new Color(155, 89, 182));
        
        // Player 2 components
        player2NameLabel = createEnhancedLabel("PLAYER 2", 26, player2Color, true);
        player2ScoreLabel = createEnhancedLabel("Score: 0", 18, new Color(236, 240, 241), false);
        player2GestureLabel = createEnhancedImageLabel(questionIcon);
        player2StatusLabel = createEnhancedLabel("Ready to Play", 14, new Color(189, 195, 199), false);
        
        // Player 2 controls
        player2ControlsPanel = new EnhancedPanel(new Color(46, 204, 113, 40));
        player2RockBtn = createEnhancedButton("J", "ROCK", "✊", new Color(231, 76, 60));
        player2PaperBtn = createEnhancedButton("K", "PAPER", "✋", new Color(241, 196, 15));
        player2ScissorsBtn = createEnhancedButton("L", "SCISSORS", "✌", new Color(155, 89, 182));
        
        // Center components with professional styling
        roundLabel = createEnhancedLabel("Round 1 of 5", 22, new Color(236, 240, 241), true);
        countdownLabel = createEnhancedLabel("", 64, accentColor, true);
        resultLabel = createEnhancedLabel("", 24, new Color(46, 204, 113), true);
        vsLabel = createEnhancedLabel("VS", 42, new Color(236, 240, 241), true);
        
        // Clean instructions
        instructionLabel = createEnhancedLabel(
            "Player 1: A=Rock, S=Paper, D=Scissors | Player 2: J=Rock, K=Paper, L=Scissors",
            12, new Color(189, 195, 199), false
        );
        
        // Professional control buttons
        backButton = createControlButton("← Back", new Color(95, 106, 117));
        newGameButton = createControlButton("New Game", new Color(39, 174, 96));
        pauseButton = createControlButton("Pause", new Color(243, 156, 18));
        
        // Initialize timers
        setupTimers();
    }
    
    private void setupTimers() {
        // Countdown timer with proper timeout handling
        countdownTimer = new Timer(1000, e -> {
            countdown--;
            if (countdown > 0) {
                countdownLabel.setText(String.valueOf(countdown));
                if (soundManager != null) soundManager.playSound("countdown_tick");
                animateCountdown();
            } else {
                countdownLabel.setText("Time's Up!");
                if (soundManager != null) soundManager.playSound("countdown_tick");
                ((Timer) e.getSource()).stop();
                waitingForInput = false;
                
                Timer delayTimer = new Timer(1000, evt -> {
                    processRound();
                    ((Timer) evt.getSource()).stop();
                });
                delayTimer.start();
            }
        });
        
        // Animation timer for smooth effects
        animationTimer = new Timer(50, e -> {
            animationFrame++;
            repaint();
        });
        animationTimer.start();
    }
    
    private void animateCountdown() {
        // Professional pulse effect
        Timer pulseTimer = new Timer(150, null);
        pulseTimer.addActionListener(e -> {
            Color current = countdownLabel.getForeground();
            countdownLabel.setForeground(current.equals(accentColor) ? 
                accentColor.brighter() : accentColor);
            ((Timer) e.getSource()).stop();
        });
        pulseTimer.setRepeats(false);
        pulseTimer.start();
    }
    
    private JLabel createEnhancedLabel(String text, int fontSize, Color color, boolean bold) {
        JLabel label = new JLabel(text, SwingConstants.CENTER);
        label.setFont(new Font("Segoe UI", bold ? Font.BOLD : Font.PLAIN, fontSize));
        label.setForeground(color);
        label.setOpaque(false);
        return label;
    }
    
    private JLabel createEnhancedImageLabel(ImageIcon icon) {
        JLabel label = new JLabel(icon, SwingConstants.CENTER);
        label.setPreferredSize(new Dimension(150, 150));
        label.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(236, 240, 241), 3, true),
            BorderFactory.createEmptyBorder(8, 8, 8, 8)
        ));
        label.setOpaque(true);
        label.setBackground(new Color(52, 73, 94));
        return label;
    }
    
    private JButton createEnhancedButton(String key, String gesture, String emoji, Color color) {
        JButton button = new JButton();
        button.setLayout(new BorderLayout());
        button.setPreferredSize(new Dimension(85, 85));
        button.setBackground(color);
        button.setForeground(Color.WHITE);
        button.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Color.WHITE, 2, true),
            BorderFactory.createEmptyBorder(3, 3, 3, 3)
        ));
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Create professional button content
        JPanel content = new JPanel(new BorderLayout());
        content.setOpaque(false);

        JLabel keyLabel = new JLabel(key, SwingConstants.CENTER);
        keyLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        keyLabel.setForeground(Color.WHITE);

        // Use image instead of emoji
        ImageIcon icon = null;
        if ("ROCK".equals(gesture)) icon = rockIcon;
        else if ("PAPER".equals(gesture)) icon = paperIcon;
        else if ("SCISSORS".equals(gesture)) icon = scissorsIcon;
        JLabel imageLabel = new JLabel(icon);
        imageLabel.setHorizontalAlignment(SwingConstants.CENTER);

        JLabel gestureLabel = new JLabel(gesture, SwingConstants.CENTER);
        gestureLabel.setFont(new Font("Segoe UI", Font.BOLD, 9));
        gestureLabel.setForeground(Color.WHITE);

        content.add(keyLabel, BorderLayout.NORTH);
        content.add(imageLabel, BorderLayout.CENTER);
        content.add(gestureLabel, BorderLayout.SOUTH);

        button.add(content);

        // Professional hover effects
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(color.brighter());
                button.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(new Color(52, 152, 219), 3, true),
                    BorderFactory.createEmptyBorder(3, 3, 3, 3)
                ));
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(color);
                button.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(Color.WHITE, 2, true),
                    BorderFactory.createEmptyBorder(3, 3, 3, 3)
                ));
            }
        });

        return button;
    }
    
    private JButton createControlButton(String text, Color color) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.BOLD, 13));
        button.setForeground(Color.WHITE);
        button.setBackground(color);
        button.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(color.darker(), 2, true),
            BorderFactory.createEmptyBorder(8, 16, 8, 16)
        ));
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        // Professional hover effects
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(color.brighter());
            }
            
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(color);
            }
        });
        
        return button;
    }
    
    private void setupLayout() {
        setLayout(new BorderLayout(8, 8));
        setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));
        
        // Top panel with professional styling
        topPanel.setLayout(new BorderLayout());
        topPanel.add(roundLabel, BorderLayout.CENTER);
        topPanel.add(instructionLabel, BorderLayout.SOUTH);
        topPanel.setBorder(BorderFactory.createEmptyBorder(8, 0, 8, 0));
        
        // Enhanced player panels
        setupPlayerPanel(leftPlayerPanel, player1NameLabel, player1ScoreLabel, 
                        player1StatusLabel, player1GestureLabel, player1ControlsPanel,
                        player1RockBtn, player1PaperBtn, player1ScissorsBtn, player1Color);
        
        setupPlayerPanel(rightPlayerPanel, player2NameLabel, player2ScoreLabel,
                        player2StatusLabel, player2GestureLabel, player2ControlsPanel,
                        player2RockBtn, player2PaperBtn, player2ScissorsBtn, player2Color);
        
        // Professional center panel
        setupCenterPanel();
        
        // Clean bottom panel
        bottomPanel.setVisible(true);
        bottomPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 40, 12));
        bottomPanel.removeAll();
        bottomPanel.add(backButton);
        bottomPanel.add(newGameButton);
        bottomPanel.add(pauseButton);

        // Main layout assembly
        add(topPanel, BorderLayout.NORTH);
        add(leftPlayerPanel, BorderLayout.WEST);
        add(centerPanel, BorderLayout.CENTER);
        add(rightPlayerPanel, BorderLayout.EAST);
        add(bottomPanel, BorderLayout.SOUTH);
    }
    
    private void setupPlayerPanel(JPanel panel, JLabel nameLabel, JLabel scoreLabel,
                                 JLabel statusLabel, JLabel gestureLabel, JPanel controlsPanel,
                                 JButton rockBtn, JButton paperBtn, JButton scissorsBtn, Color color) {
        panel.setLayout(new BorderLayout(4, 4));
        panel.setPreferredSize(new Dimension(400, 520));
        panel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(color, 3, true),
            BorderFactory.createEmptyBorder(24, 24, 24, 24)
        ));
        
        // Info panel with modern design
        JPanel infoPanel = new EnhancedPanel(new Color(0, 0, 0, 60));
        infoPanel.setLayout(new BorderLayout(4, 4));
        infoPanel.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));
        infoPanel.add(nameLabel, BorderLayout.NORTH);
        infoPanel.add(scoreLabel, BorderLayout.CENTER);
        infoPanel.add(statusLabel, BorderLayout.SOUTH);
        
        // Gesture display with clean design
        JPanel gesturePanel = new EnhancedPanel(new Color(0, 0, 0, 30));
        gesturePanel.setLayout(new BorderLayout());
        gesturePanel.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));
        gesturePanel.add(gestureLabel, BorderLayout.CENTER);
        
        // Controls with professional styling
        controlsPanel.setLayout(new BoxLayout(controlsPanel, BoxLayout.X_AXIS));
        controlsPanel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(color, 2),
            "Controls",
            0, 0,
            new Font("Segoe UI", Font.BOLD, 11),
            new Color(236, 240, 241)
        ));
        controlsPanel.add(Box.createHorizontalGlue());
        controlsPanel.add(rockBtn);
        controlsPanel.add(Box.createHorizontalStrut(10));
        controlsPanel.add(paperBtn);
        controlsPanel.add(Box.createHorizontalStrut(10));
        controlsPanel.add(scissorsBtn);
        controlsPanel.add(Box.createHorizontalGlue());
        
        // Assembly
        panel.add(infoPanel, BorderLayout.NORTH);
        panel.add(gesturePanel, BorderLayout.CENTER);
        panel.add(controlsPanel, BorderLayout.SOUTH);
    }
    
    private void setupCenterPanel() {
        centerPanel.setLayout(new BorderLayout());
        centerPanel.setPreferredSize(new Dimension(280, 380));
        centerPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(236, 240, 241), 3, true),
            BorderFactory.createEmptyBorder(16, 16, 16, 16)
        ));
        
        JPanel centerContent = new JPanel();
        centerContent.setLayout(new BoxLayout(centerContent, BoxLayout.Y_AXIS));
        centerContent.setOpaque(false);
        
        // Center all components
        vsLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        countdownLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        resultLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        centerContent.add(Box.createVerticalGlue());
        centerContent.add(vsLabel);
        centerContent.add(Box.createVerticalStrut(25));
        centerContent.add(countdownLabel);
        centerContent.add(Box.createVerticalStrut(25));
        centerContent.add(resultLabel);
        centerContent.add(Box.createVerticalGlue());
        
        centerPanel.add(centerContent, BorderLayout.CENTER);
    }
    
    private void setupEventHandlers() {
        // Player 1 buttons
        player1RockBtn.addActionListener(e -> handlePlayer1Input(Gesture.ROCK));
        player1PaperBtn.addActionListener(e -> handlePlayer1Input(Gesture.PAPER));
        player1ScissorsBtn.addActionListener(e -> handlePlayer1Input(Gesture.SCISSORS));
        
        // Player 2 buttons
        player2RockBtn.addActionListener(e -> handlePlayer2Input(Gesture.ROCK));
        player2PaperBtn.addActionListener(e -> handlePlayer2Input(Gesture.PAPER));
        player2ScissorsBtn.addActionListener(e -> handlePlayer2Input(Gesture.SCISSORS));
        
        // Control buttons
        backButton.addActionListener(e -> {
            if (soundManager != null) soundManager.playSound("gesture_select");
            stopAllTimers();
            ((MainWindow) SwingUtilities.getWindowAncestor(this)).returnToMenu();
        });
        
        newGameButton.addActionListener(e -> {
            if (soundManager != null) soundManager.playSound("gesture_select");
            resetGame();
            startCountdown();
        });
        
        pauseButton.addActionListener(e -> {
            if (soundManager != null) soundManager.playSound("gesture_select");
            togglePause();
        });
    }
    
    private void stopAllTimers() {
        if (countdownTimer != null) countdownTimer.stop();
        if (animationTimer != null) animationTimer.stop();
    }
    
    private void togglePause() {
        if (gameInProgress && waitingForInput) {
            if (countdownTimer.isRunning()) {
                countdownTimer.stop();
                pauseButton.setText("Resume");
                resultLabel.setText("PAUSED");
                resultLabel.setForeground(warningColor);
            } else {
                countdownTimer.start();
                pauseButton.setText("Pause");
                resultLabel.setText("");
            }
        }
    }
    
    private void handlePlayer1Input(Gesture gesture) {
        if (waitingForInput && player1Choice == null) {
            if (soundManager != null) soundManager.playSound("gesture_select");
            player1Choice = gesture;
            player1StatusLabel.setText("Choice Made!");
            player1StatusLabel.setForeground(new Color(39, 174, 96));
            updatePlayerStatus();
        }
    }
    
    private void handlePlayer2Input(Gesture gesture) {
        if (waitingForInput && player2Choice == null) {
            if (soundManager != null) soundManager.playSound("gesture_select");
            player2Choice = gesture;
            player2StatusLabel.setText("Choice Made!");
            player2StatusLabel.setForeground(new Color(39, 174, 96));
            updatePlayerStatus();
        }
    }
    
    private void updatePlayerStatus() {
        if (player1Choice != null && player2Choice != null) {
            countdownTimer.stop();
            waitingForInput = false;
            resultLabel.setText("Revealing choices...");
            resultLabel.setForeground(new Color(189, 195, 199));
            
            Timer delayTimer = new Timer(1200, e -> {
                processRound();
                ((Timer) e.getSource()).stop();
            });
            delayTimer.start();
        }
    }
    
    private void startCountdown() {
        countdown = 3;
        waitingForInput = true;
        gameInProgress = true;
        player1Choice = null;
        player2Choice = null;
        
        player1StatusLabel.setText("Make your choice...");
        player1StatusLabel.setForeground(new Color(189, 195, 199));
        player2StatusLabel.setText("Make your choice...");
        player2StatusLabel.setForeground(new Color(189, 195, 199));
        
        countdownLabel.setText(String.valueOf(countdown));
        resultLabel.setText("");
        pauseButton.setText("Pause");
        
        player1GestureLabel.setIcon(rockIcon);
        player2GestureLabel.setIcon(rockIcon);
        
        countdownTimer.start();
    }
    
    private void processRound() {
        // Handle timeout properly - restart round if any player didn't choose
        if (player1Choice == null || player2Choice == null) {
            handleTimeout();
            return;
        }
        
        // Update gesture displays
        updateGestureDisplay(player1GestureLabel, player1Choice);
        updateGestureDisplay(player2GestureLabel, player2Choice);
        
        // Determine winner
        String result = determineWinner(player1Choice, player2Choice);
        displayResult(result);
        
        // Update scores and play sounds
        updateScoresAndSounds(result);
        
        // Check game end or continue
        if (currentRound >= maxRounds) {
            Timer endTimer = new Timer(2500, e -> {
                showGameEnd();
                ((Timer) e.getSource()).stop();
            });
            endTimer.start();
        } else {
            currentRound++;
            Timer nextRoundTimer = new Timer(3500, e -> {
                startCountdown();
                ((Timer) e.getSource()).stop();
            });
            nextRoundTimer.start();
        }
    }
    
    private void handleTimeout() {
        countdownTimer.stop();
        waitingForInput = false;
        
        String timeoutMessage = "";
        if (player1Choice == null && player2Choice == null) {
            timeoutMessage = "Both players timed out! Restarting round...";
            player1StatusLabel.setText("No choice made");
            player1StatusLabel.setForeground(warningColor);
            player2StatusLabel.setText("No choice made");
            player2StatusLabel.setForeground(warningColor);
        } else if (player1Choice == null) {
            timeoutMessage = "Player 1 timed out! Restarting round...";
            player1StatusLabel.setText("No choice made");
            player1StatusLabel.setForeground(warningColor);
            player2StatusLabel.setText("Waiting for restart");
            player2StatusLabel.setForeground(new Color(189, 195, 199));
        } else {
            timeoutMessage = "Player 2 timed out! Restarting round...";
            player2StatusLabel.setText("No choice made");
            player2StatusLabel.setForeground(warningColor);
            player1StatusLabel.setText("Waiting for restart");
            player1StatusLabel.setForeground(new Color(189, 195, 199));
        }
        
        resultLabel.setText(timeoutMessage);
        resultLabel.setForeground(warningColor);
        countdownLabel.setText("⚠");
        
        // Restart the same round after a delay
        Timer restartTimer = new Timer(2500, e -> {
            startCountdown();
            ((Timer) e.getSource()).stop();
        });
        restartTimer.start();
    }
    
    private void updateGestureDisplay(JLabel label, Gesture gesture) {
        Timer fadeTimer = new Timer(100, null);
        fadeTimer.addActionListener(e -> {
            label.setIcon(getGestureIcon(gesture));
            ((Timer) e.getSource()).stop();
        });
        fadeTimer.start();
    }
    
    private void displayResult(String result) {
        resultLabel.setText(result);
        
        // Professional color coding
        if (result.contains("Player 1 Wins")) {
            resultLabel.setForeground(player1Color);
        } else if (result.contains("Player 2 Wins")) {
            resultLabel.setForeground(player2Color);
        } else {
            resultLabel.setForeground(new Color(243, 156, 18));
        }
    }
    
    private void updateScoresAndSounds(String result) {
        if (result.contains("Player 1 Wins")) {
            player1Score++;
            if (soundManager != null) soundManager.playSound("win");
            player1StatusLabel.setText("WINNER!");
            player1StatusLabel.setForeground(new Color(39, 174, 96));
            player2StatusLabel.setText("Better luck next time");
            player2StatusLabel.setForeground(new Color(189, 195, 199));
        } else if (result.contains("Player 2 Wins")) {
            player2Score++;
            if (soundManager != null) soundManager.playSound("lose");
            player2StatusLabel.setText("WINNER!");
            player2StatusLabel.setForeground(new Color(39, 174, 96));
            player1StatusLabel.setText("Better luck next time");
            player1StatusLabel.setForeground(new Color(189, 195, 199));
        } else {
            if (soundManager != null) soundManager.playSound("draw");
            player1StatusLabel.setText("It's a tie!");
            player1StatusLabel.setForeground(new Color(243, 156, 18));
            player2StatusLabel.setText("It's a tie!");
            player2StatusLabel.setForeground(new Color(243, 156, 18));
        }
        
        updateScoreDisplay();
    }
    
    private String determineWinner(Gesture p1, Gesture p2) {
        if (p1 == p2) return "Draw!";
        
        boolean p1Wins = (p1 == Gesture.ROCK && p2 == Gesture.SCISSORS) ||
                         (p1 == Gesture.PAPER && p2 == Gesture.ROCK) ||
                         (p1 == Gesture.SCISSORS && p2 == Gesture.PAPER);
        
        return p1Wins ? "Player 1 Wins!" : "Player 2 Wins!";
    }
    
    private void updateScoreDisplay() {
        player1ScoreLabel.setText("Score: " + player1Score);
        player2ScoreLabel.setText("Score: " + player2Score);
        roundLabel.setText("Round " + currentRound + " of " + maxRounds);
    }
    
    private void showGameEnd() {
        gameInProgress = false;
        if (soundManager != null) soundManager.playSound("game_end");
        
        String winner = player1Score > player2Score ? "Player 1" : 
                       player2Score > player1Score ? "Player 2" : "Tie";
        
        String endMessage = winner.equals("Tie") ? "Game Tied!" : winner + " Wins!";
        resultLabel.setText(endMessage);
        countdownLabel.setText("🏆");
        
        if (winner.equals("Player 1")) {
            resultLabel.setForeground(player1Color);
            player1StatusLabel.setText("CHAMPION!");
            player1StatusLabel.setForeground(new Color(39, 174, 96));
            player2StatusLabel.setText("Good game!");
            player2StatusLabel.setForeground(new Color(189, 195, 199));
        } else if (winner.equals("Player 2")) {
            resultLabel.setForeground(player2Color);
            player2StatusLabel.setText("CHAMPION!");
            player2StatusLabel.setForeground(new Color(39, 174, 96));
            player1StatusLabel.setText("Good game!");
            player1StatusLabel.setForeground(new Color(189, 195, 199));
        } else {
            resultLabel.setForeground(new Color(243, 156, 18));
            player1StatusLabel.setText("Great game!");
            player1StatusLabel.setForeground(new Color(189, 195, 199));
            player2StatusLabel.setText("Great game!");
            player2StatusLabel.setForeground(new Color(189, 195, 199));
        }
        
        pauseButton.setText("Pause");
    }
    
    private ImageIcon getGestureIcon(Gesture gesture) {
        switch (gesture) {
            case ROCK: return rockIcon;
            case PAPER: return paperIcon;
            case SCISSORS: return scissorsIcon;
            default: return questionIcon;
        }
    }
    
    // Public methods
    public void setSoundManager(SoundManager soundManager) {
        this.soundManager = soundManager;
    }
    
    public void startGame(int rounds) {
        this.maxRounds = rounds;
        resetGame();
        
        if (soundManager != null) soundManager.playSound("game_start");
        
        Timer startTimer = new Timer(1200, e -> {
            startCountdown();
            ((Timer) e.getSource()).stop();
        });
        startTimer.start();
    }
    
    private void resetGame() {
        currentRound = 1;
        player1Score = 0;
        player2Score = 0;
        player1Choice = null;
        player2Choice = null;
        waitingForInput = false;
        gameInProgress = false;
        
        updateScoreDisplay();
        player1StatusLabel.setText("Ready to Play");
        player1StatusLabel.setForeground(new Color(189, 195, 199));
        player2StatusLabel.setText("Ready to Play");
        player2StatusLabel.setForeground(new Color(189, 195, 199));
        resultLabel.setText("");
        countdownLabel.setText("");
        player1GestureLabel.setIcon(questionIcon);
        player2GestureLabel.setIcon(questionIcon);
        pauseButton.setText("Pause");
        
        stopAllTimers();
        animationTimer.start();
    }
    
    @Override
    public void keyPressed(KeyEvent e) {
        if (!waitingForInput) return;
        
        char key = Character.toLowerCase((char) e.getKeyCode());
        
        // Player 1 controls
        if (player1Choice == null) {
            switch (key) {
                case 'a': handlePlayer1Input(Gesture.ROCK); break;
                case 's': handlePlayer1Input(Gesture.PAPER); break;
                case 'd': handlePlayer1Input(Gesture.SCISSORS); break;
            }
        }
        
        // Player 2 controls
        if (player2Choice == null) {
            switch (key) {
                case 'j': handlePlayer2Input(Gesture.ROCK); break;
                case 'k': handlePlayer2Input(Gesture.PAPER); break;
                case 'l': handlePlayer2Input(Gesture.SCISSORS); break;
            }
        }
    }
    
    @Override
    public void keyReleased(KeyEvent e) {}
    
    @Override
    public void keyTyped(KeyEvent e) {}
    
    // Enhanced Panel class with professional gradients
    private class EnhancedPanel extends JPanel {
        private Color baseColor;
        
        public EnhancedPanel(Color baseColor) {
            this.baseColor = baseColor;
            setOpaque(false);
        }
        
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g.create();
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            
            // Professional gradient background
            GradientPaint gradient = new GradientPaint(
                0, 0, baseColor,
                0, getHeight(), baseColor.darker()
            );
            g2d.setPaint(gradient);
            g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 12, 12);
            
            g2d.dispose();
        }
    }
}
