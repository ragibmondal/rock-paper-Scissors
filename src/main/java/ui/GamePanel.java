package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import audio.SoundManager;
import game.GameMode;
import game.Gesture;

/**
 * Simple and effective GamePanel for Player vs Computer mode
 * After player turn, computer automatically selects random gesture
 */
public class GamePanel extends JPanel {
    private static final long serialVersionUID = 1L;
    
    @Override
    public Dimension getPreferredSize() {
        return new Dimension(1400, 800);
    }
    
    // Game logic
    private SoundManager soundManager;
    private int maxRounds = 5;
    private int currentRound = 1;
    private int playerScore = 0;
    private int computerScore = 0;
    private String difficultyName = "Medium";
    private boolean gameInProgress = false;
    private boolean waitingForPlayerInput = false;
    
    // UI Components
    private JPanel headerPanel;
    private JPanel gameAreaPanel;
    private JPanel controlPanel;
    
    // Game display components
    private JLabel roundLabel;
    private JLabel scoreLabel;
    private JLabel difficultyLabel;
    private JLabel playerChoiceLabel;
    private JLabel computerChoiceLabel;
    private JLabel resultLabel;
    private JLabel statusLabel;
    
    // Player controls
    private JButton rockButton;
    private JButton paperButton;
    private JButton scissorsButton;
    
    // Game controls
    private JButton backButton;
    private JButton newGameButton;
    
    // Images
    private ImageIcon rockIcon;
    private ImageIcon paperIcon;
    private ImageIcon scissorsIcon;
    private ImageIcon questionIcon;
    
    // Timers
    private Timer countdownTimer;
    private Timer computerThinkTimer;
    private Timer nextRoundTimer;
    private int countdown;
    private Gesture currentPlayerChoice; // Store player choice for computer processing
    
    // Modern color palette
    private static final Color PRIMARY_BG = new Color(10, 15, 35);
    private static final Color SECONDARY_BG = new Color(25, 35, 55);
    private static final Color GLASS_BG = new Color(255, 255, 255, 25);
    private static final Color GLASS_BORDER = new Color(255, 255, 255, 60);
    private static final Color ACCENT_RED = new Color(248, 113, 113);
    private static final Color ACCENT_YELLOW = new Color(251, 191, 36);
    private static final Color ACCENT_PURPLE = new Color(168, 85, 247);
    private static final Color ACCENT_BLUE = new Color(56, 189, 248);
    private static final Color ACCENT_GREEN = new Color(16, 185, 129);
    private static final Color TEXT_PRIMARY = new Color(255, 255, 255);
    private static final Color TEXT_SECONDARY = new Color(156, 163, 175);
    private static final Color SHADOW_COLOR = new Color(0, 0, 0, 80);
    
    public GamePanel() {
        loadImages();
        initializeComponents();
        setupLayout();
        setupEventHandlers();
        setFocusable(true);
    }
    
    private void loadImages() {
        try {
            rockIcon = loadScaledIcon("/images/rock_hand.png", 200, 200);
            paperIcon = loadScaledIcon("/images/paper_hand.png", 200, 200);
            scissorsIcon = loadScaledIcon("/images/scissors_hand.png", 200, 200);
            
            questionIcon = createQuestionIcon();
            
            if (rockIcon == null || paperIcon == null || scissorsIcon == null) {
                System.out.println("GamePanel: Some images failed to load, creating fallbacks");
                createFallbackIcons();
            } else {
                System.out.println("GamePanel: Images loaded successfully!");
            }
        } catch (Exception e) {
            System.out.println("GamePanel: Error loading images: " + e.getMessage());
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
        int size = 150;
        java.awt.image.BufferedImage img = new java.awt.image.BufferedImage(size, size, java.awt.image.BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = img.createGraphics();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        // Gradient background
        GradientPaint gradient = new GradientPaint(0, 0, SECONDARY_BG.brighter(), size, size, SECONDARY_BG.darker());
        g2.setPaint(gradient);
        g2.fillRoundRect(10, 10, size-20, size-20, 25, 25);
        
        // Border
        g2.setColor(GLASS_BORDER);
        g2.setStroke(new BasicStroke(2));
        g2.drawRoundRect(10, 10, size-20, size-20, 25, 25);
        
        // Question mark
        g2.setFont(new Font("Segoe UI", Font.BOLD, 50));
        FontMetrics fm = g2.getFontMetrics();
        String text = "?";
        int x = (size - fm.stringWidth(text)) / 2;
        int y = (size + fm.getAscent()) / 2;
        
        g2.setColor(new Color(0, 0, 0, 80));
        g2.drawString(text, x + 2, y + 2);
        
        g2.setColor(TEXT_PRIMARY);
        g2.drawString(text, x, y);
        
        g2.dispose();
        return new ImageIcon(img);
    }
    
    private void createFallbackIcons() {
        rockIcon = createEnhancedIcon(ACCENT_RED, "ROCK");
        paperIcon = createEnhancedIcon(ACCENT_YELLOW, "PAPER");
        scissorsIcon = createEnhancedIcon(ACCENT_PURPLE, "SCISSORS");
    }
    
    private ImageIcon createEnhancedIcon(Color color, String text) {
        int size = 150;
        java.awt.image.BufferedImage img = new java.awt.image.BufferedImage(size, size, java.awt.image.BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = img.createGraphics();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        GradientPaint gradient = new GradientPaint(0, 0, color.brighter(), size, size, color.darker());
        g2.setPaint(gradient);
        g2.fillRoundRect(10, 10, size-20, size-20, 25, 25);
        
        g2.setColor(TEXT_PRIMARY);
        g2.setStroke(new BasicStroke(2));
        g2.drawRoundRect(10, 10, size-20, size-20, 25, 25);
        
        // Draw gesture symbol
        g2.setColor(TEXT_PRIMARY);
        drawHandGesture(g2, text, size);
        
        // Text at bottom
        g2.setFont(new Font("Segoe UI", Font.BOLD, 12));
        FontMetrics fm = g2.getFontMetrics();
        int x = (size - fm.stringWidth(text)) / 2;
        int y = size - 15;
        
        g2.setColor(new Color(0, 0, 0, 80));
        g2.drawString(text, x + 1, y + 1);
        g2.setColor(TEXT_PRIMARY);
        g2.drawString(text, x, y);
        
        g2.dispose();
        return new ImageIcon(img);
    }
    
    private void drawHandGesture(Graphics2D g2, String gesture, int size) {
        g2.setStroke(new BasicStroke(3, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        int centerX = size / 2;
        int centerY = size / 2 - 5;
        
        switch (gesture) {
            case "ROCK":
                g2.fillOval(centerX - 20, centerY - 15, 40, 30);
                break;
            case "PAPER":
                for (int i = 0; i < 5; i++) {
                    int fingerX = centerX - 20 + (i * 10);
                    g2.drawLine(fingerX, centerY - 20, fingerX, centerY + 10);
                }
                g2.drawLine(centerX - 20, centerY + 10, centerX + 20, centerY + 10);
                break;
            case "SCISSORS":
                g2.drawLine(centerX - 10, centerY - 20, centerX - 10, centerY + 10);
                g2.drawLine(centerX + 10, centerY - 20, centerX + 10, centerY + 10);
                g2.drawLine(centerX - 10, centerY + 10, centerX + 10, centerY + 10);
                break;
        }
    }
    
    private void initializeComponents() {
        setBackground(PRIMARY_BG);
        
        headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(SECONDARY_BG);
        headerPanel.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));
        
        gameAreaPanel = new JPanel(new BorderLayout());
        gameAreaPanel.setBackground(PRIMARY_BG);
        gameAreaPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        controlPanel = new JPanel(new BorderLayout());
        controlPanel.setBackground(SECONDARY_BG);
        controlPanel.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));
        
        // Game display components
        roundLabel = createLabel("Round 1 of 5", 20, TEXT_PRIMARY, true);
        scoreLabel = createLabel("Player: 0 | Computer: 0", 16, TEXT_SECONDARY, false);
        difficultyLabel = createLabel("Difficulty: Medium", 14, ACCENT_BLUE, false);
        
        playerChoiceLabel = createImageLabel(questionIcon, "Your Choice");
        computerChoiceLabel = createImageLabel(questionIcon, "Computer's Choice");
        
        resultLabel = createLabel("", 24, ACCENT_GREEN, true);
        statusLabel = createLabel("Click any gesture button to start playing!", 14, TEXT_SECONDARY, false);
        
        // Player controls
        rockButton = createGameButton("ROCK", "✊", ACCENT_RED);
        paperButton = createGameButton("PAPER", "✋", ACCENT_YELLOW);
        scissorsButton = createGameButton("SCISSORS", "✌", ACCENT_PURPLE);
        
        // Game controls
        backButton = createControlButton("← Back to Menu", GLASS_BORDER);
        newGameButton = createControlButton("New Game", ACCENT_GREEN);
        
        // Initially enable player buttons so they can start the game
        setPlayerButtonsEnabled(true);
    }
    
    private JLabel createLabel(String text, int fontSize, Color color, boolean bold) {
        JLabel label = new JLabel(text, SwingConstants.CENTER);
        label.setFont(new Font("Segoe UI", bold ? Font.BOLD : Font.PLAIN, fontSize));
        label.setForeground(color);
        return label;
    }
    
    private JLabel createImageLabel(ImageIcon icon, String title) {
        JPanel container = new JPanel(new BorderLayout());
        container.setBackground(PRIMARY_BG);
        container.setPreferredSize(new Dimension(350, 300));
        
        JLabel titleLabel = new JLabel(title, SwingConstants.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        titleLabel.setForeground(TEXT_PRIMARY);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        
        JLabel imageLabel = new JLabel(icon, SwingConstants.CENTER);
        imageLabel.setPreferredSize(new Dimension(250, 200));
        
        container.add(titleLabel, BorderLayout.NORTH);
        container.add(imageLabel, BorderLayout.CENTER);
        
        container.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(GLASS_BORDER, 2),
            BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));
        
        // Store reference for updating
        imageLabel.putClientProperty("container", container);
        return imageLabel;
    }
    
    private JButton createGameButton(String text, String emoji, Color color) {
        JButton button = new JButton();
        button.setLayout(new BorderLayout());
        button.setPreferredSize(new Dimension(100, 70));
        button.setBackground(color);
        button.setForeground(TEXT_PRIMARY);
        button.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(color.darker(), 2),
            BorderFactory.createEmptyBorder(8, 8, 8, 8)
        ));
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        JPanel content = new JPanel(new BorderLayout());
        content.setBackground(color);
        
        JLabel emojiLabel = new JLabel(emoji, SwingConstants.CENTER);
        emojiLabel.setFont(new Font("Segoe UI Emoji", Font.BOLD, 24));
        emojiLabel.setForeground(TEXT_PRIMARY);
        
        JLabel textLabel = new JLabel(text, SwingConstants.CENTER);
        textLabel.setFont(new Font("Segoe UI", Font.BOLD, 10));
        textLabel.setForeground(TEXT_PRIMARY);
        
        content.add(emojiLabel, BorderLayout.CENTER);
        content.add(textLabel, BorderLayout.SOUTH);
        
        button.add(content);
        
        // Hover effects
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                if (button.isEnabled()) {
                    button.setBackground(color.brighter());
                    content.setBackground(color.brighter());
                    button.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(TEXT_PRIMARY, 2),
                        BorderFactory.createEmptyBorder(8, 8, 8, 8)
                    ));
                }
            }
            
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(color);
                content.setBackground(color);
                button.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(color.darker(), 2),
                    BorderFactory.createEmptyBorder(8, 8, 8, 8)
                ));
            }
        });
        
        return button;
    }
    
    private JButton createControlButton(String text, Color color) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        button.setForeground(TEXT_PRIMARY);
        button.setBackground(color);
        button.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(color.darker(), 1),
            BorderFactory.createEmptyBorder(10, 20, 10, 20)
        ));
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
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
        setLayout(new BorderLayout());
        
        // Header with round info and difficulty
        JPanel headerContent = new JPanel(new BorderLayout());
        headerContent.setBackground(SECONDARY_BG);
        
        JPanel topInfo = new JPanel(new FlowLayout(FlowLayout.CENTER));
        topInfo.setBackground(SECONDARY_BG);
        topInfo.add(roundLabel);
        
        JPanel bottomInfo = new JPanel(new FlowLayout(FlowLayout.CENTER, 30, 0));
        bottomInfo.setBackground(SECONDARY_BG);
        bottomInfo.add(difficultyLabel);
        bottomInfo.add(scoreLabel);
        
        headerContent.add(topInfo, BorderLayout.NORTH);
        headerContent.add(bottomInfo, BorderLayout.SOUTH);
        headerPanel.add(headerContent, BorderLayout.CENTER);
        
        // Game area with choices and result
        JPanel choicesPanel = new JPanel(new BorderLayout());
        choicesPanel.setBackground(PRIMARY_BG);
        
        JPanel playerPanel = new JPanel(new BorderLayout());
        playerPanel.setBackground(PRIMARY_BG);
        playerPanel.add((JPanel) playerChoiceLabel.getClientProperty("container"), BorderLayout.CENTER);
        
        JPanel computerPanel = new JPanel(new BorderLayout());
        computerPanel.setBackground(PRIMARY_BG);
        computerPanel.add((JPanel) computerChoiceLabel.getClientProperty("container"), BorderLayout.CENTER);
        
        // Center VS panel
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.setBackground(PRIMARY_BG);
        centerPanel.setPreferredSize(new Dimension(250, 300));
        centerPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(GLASS_BORDER, 3),
            BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));
        
        JLabel vsLabel = createLabel("VS", 42, TEXT_PRIMARY, true);
        vsLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        resultLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        centerPanel.add(Box.createVerticalGlue());
        centerPanel.add(vsLabel);
        centerPanel.add(Box.createVerticalStrut(15));
        centerPanel.add(resultLabel);
        centerPanel.add(Box.createVerticalGlue());
        
        choicesPanel.add(playerPanel, BorderLayout.WEST);
        choicesPanel.add(centerPanel, BorderLayout.CENTER);
        choicesPanel.add(computerPanel, BorderLayout.EAST);
        
        gameAreaPanel.add(choicesPanel, BorderLayout.CENTER);
        gameAreaPanel.add(statusLabel, BorderLayout.SOUTH);
        
        // Control panel with buttons
        JPanel controlContent = new JPanel(new BorderLayout());
        controlContent.setBackground(SECONDARY_BG);
        
        // Player controls
        JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        buttonsPanel.setBackground(SECONDARY_BG);
        buttonsPanel.add(rockButton);
        buttonsPanel.add(paperButton);
        buttonsPanel.add(scissorsButton);
        
        // Game controls
        JPanel gameControlsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 30, 10));
        gameControlsPanel.setBackground(SECONDARY_BG);
        gameControlsPanel.add(backButton);
        gameControlsPanel.add(newGameButton);
        
        controlContent.add(buttonsPanel, BorderLayout.CENTER);
        controlContent.add(gameControlsPanel, BorderLayout.SOUTH);
        
        controlPanel.add(controlContent, BorderLayout.CENTER);
        
        // Assemble main layout
        add(headerPanel, BorderLayout.NORTH);
        add(gameAreaPanel, BorderLayout.CENTER);
        add(controlPanel, BorderLayout.SOUTH);
    }
    
    private void setupEventHandlers() {
        rockButton.addActionListener(e -> playerMakeChoice(Gesture.ROCK));
        paperButton.addActionListener(e -> playerMakeChoice(Gesture.PAPER));
        scissorsButton.addActionListener(e -> playerMakeChoice(Gesture.SCISSORS));
        
        backButton.addActionListener(e -> {
            if (soundManager != null) soundManager.playSound("gesture_select");
            returnToMenu();
        });
        
        newGameButton.addActionListener(e -> {
            if (soundManager != null) soundManager.playSound("gesture_select");
            startNewGame();
        });
        
        // Countdown timer
        countdownTimer = new Timer(1000, e -> {
            countdown--;
            if (countdown > 0) {
                statusLabel.setText("Choose your gesture! Time remaining: " + countdown + " seconds");
                if (soundManager != null) soundManager.playSound("countdown_tick");
            } else {
                statusLabel.setText("Time's up! Computer wins this round!");
                if (soundManager != null) soundManager.playSound("lose");
                ((Timer) e.getSource()).stop();
                computerScore++;
                updateScoreDisplay();
                endRoundAfterDelay();
            }
        });
        
        // Computer thinking timer (adds suspense)
        computerThinkTimer = new Timer(800, e -> {
            ((Timer) e.getSource()).stop();
            // Computer makes random choice after thinking
            Gesture computerChoice = generateComputerChoice();
            updateChoiceDisplay(computerChoiceLabel, computerChoice);
            
            // Determine winner and show result using stored player choice
            showRoundResult(currentPlayerChoice, computerChoice);
        });
        
        // Next round timer
        nextRoundTimer = new Timer(2500, e -> {
            ((Timer) e.getSource()).stop();
            if (currentRound < maxRounds) {
                nextRound();
            } else {
                endGame();
            }
        });
    }
    
    private void playerMakeChoice(Gesture playerChoice) {
        System.out.println("Player chose: " + playerChoice + ", waitingForPlayerInput: " + waitingForPlayerInput + ", gameInProgress: " + gameInProgress);
        
        // If game hasn't started yet, start it automatically and process this choice
        if (!gameInProgress) {
            startNewGame();
            // Now continue to process the choice since the game has started
        }
        
        if (!waitingForPlayerInput) {
            statusLabel.setText("Please wait for your turn or start a new round!");
            return;
        }
        
        if (soundManager != null) soundManager.playSound("gesture_select");
        
        waitingForPlayerInput = false;
        setPlayerButtonsEnabled(false);
        countdownTimer.stop();
        
        // Update player display immediately
        updateChoiceDisplay(playerChoiceLabel, playerChoice);
        
        // Show computer thinking
        statusLabel.setText("Computer is thinking...");
        
        // Store player choice and start computer thinking timer
        currentPlayerChoice = playerChoice;
        computerThinkTimer.start();
    }
    
    private Gesture generateComputerChoice() {
        // Simple random choice with difficulty consideration
        Gesture[] choices = {Gesture.ROCK, Gesture.PAPER, Gesture.SCISSORS};
        
        if (difficultyName.contains("Easy")) {
            // Pure random
            return choices[(int) (Math.random() * choices.length)];
        } else if (difficultyName.contains("Hard")) {
            // Slightly more strategic but still mostly random
            // Could be enhanced with pattern recognition later
            return choices[(int) (Math.random() * choices.length)];
        } else {
            // Medium - pure random for now
            return choices[(int) (Math.random() * choices.length)];
        }
    }
    
    private void showRoundResult(Gesture playerChoice, Gesture computerChoice) {
        String result = determineWinner(playerChoice, computerChoice);
        Color resultColor;
        
        if (result.equals("You Win!")) {
            playerScore++;
            resultColor = ACCENT_GREEN;
            if (soundManager != null) soundManager.playSound("win");
        } else if (result.equals("Computer Wins!")) {
            computerScore++;
            resultColor = ACCENT_RED;
            if (soundManager != null) soundManager.playSound("lose");
        } else {
            resultColor = ACCENT_YELLOW;
            if (soundManager != null) soundManager.playSound("draw");
        }
        
        resultLabel.setText(result);
        resultLabel.setForeground(resultColor);
        updateScoreDisplay();
        
        statusLabel.setText(playerChoice.getDisplayName() + " vs " + computerChoice.getDisplayName());
        
        // Start next round timer
        nextRoundTimer.start();
    }
    
    private String determineWinner(Gesture player, Gesture computer) {
        if (player == computer) {
            return "Draw!";
        }
        
        boolean playerWins = (player == Gesture.ROCK && computer == Gesture.SCISSORS) ||
                           (player == Gesture.PAPER && computer == Gesture.ROCK) ||
                           (player == Gesture.SCISSORS && computer == Gesture.PAPER);
        
        return playerWins ? "You Win!" : "Computer Wins!";
    }
    
    private void updateChoiceDisplay(JLabel choiceLabel, Gesture choice) {
        ImageIcon icon = questionIcon;
        
        if (choice != null) {
            switch (choice) {
                case ROCK: icon = rockIcon; break;
                case PAPER: icon = paperIcon; break;
                case SCISSORS: icon = scissorsIcon; break;
            }
        }
        
        choiceLabel.setIcon(icon);
    }
    
    private void updateScoreDisplay() {
        scoreLabel.setText("Player: " + playerScore + " | Computer: " + computerScore);
    }
    
    private void setPlayerButtonsEnabled(boolean enabled) {
        rockButton.setEnabled(enabled);
        paperButton.setEnabled(enabled);
        scissorsButton.setEnabled(enabled);
    }
    
    private void startNewGame() {
        currentRound = 1;
        playerScore = 0;
        computerScore = 0;
        gameInProgress = true;
        
        roundLabel.setText("Round 1 of " + maxRounds);
        updateScoreDisplay();
        resetUI();
        startRound();
    }
    
    private void startRound() {
        waitingForPlayerInput = true;
        setPlayerButtonsEnabled(true);
        
        countdown = 15; // Give more time
        statusLabel.setText("Choose your gesture! Time remaining: " + countdown + " seconds");
        countdownTimer.start();
    }
    
    private void nextRound() {
        currentRound++;
        roundLabel.setText("Round " + currentRound + " of " + maxRounds);
        resetUI();
        startRound();
    }
    
    private void resetUI() {
        updateChoiceDisplay(playerChoiceLabel, null);
        updateChoiceDisplay(computerChoiceLabel, null);
        resultLabel.setText("");
    }
    
    private void endRoundAfterDelay() {
        Timer delayTimer = new Timer(2000, e -> {
            ((Timer) e.getSource()).stop();
            if (currentRound < maxRounds) {
                nextRound();
            } else {
                endGame();
            }
        });
        delayTimer.start();
    }
    
    private void endGame() {
        gameInProgress = false;
        String finalResult;
        
        if (playerScore > computerScore) {
            finalResult = "🎉 Congratulations! You won the game!";
            if (soundManager != null) soundManager.playSound("game_end");
        } else if (computerScore > playerScore) {
            finalResult = "🤖 Computer won the game! Better luck next time!";
            if (soundManager != null) soundManager.playSound("game_end");
        } else {
            finalResult = "🤝 It's a tie! Great game!";
            if (soundManager != null) soundManager.playSound("game_end");
        }
        
        statusLabel.setText(finalResult);
        resultLabel.setText("Final Score: " + playerScore + " - " + computerScore);
        setPlayerButtonsEnabled(false);
    }
    
    private void returnToMenu() {
        // Stop all timers
        if (countdownTimer.isRunning()) countdownTimer.stop();
        if (computerThinkTimer.isRunning()) computerThinkTimer.stop();
        if (nextRoundTimer.isRunning()) nextRoundTimer.stop();
        
        Container parent = getParent();
        while (parent != null && !(parent instanceof MainWindow)) {
            parent = parent.getParent();
        }
        if (parent instanceof MainWindow) {
            ((MainWindow) parent).returnToMenu();
        }
    }
    
    // Public methods
    public void setSoundManager(SoundManager soundManager) {
        this.soundManager = soundManager;
    }
    
    public void startGame(GameMode mode, int rounds, String difficulty) {
        this.maxRounds = rounds;
        this.difficultyName = difficulty;
        
        difficultyLabel.setText("Difficulty: " + difficulty);
        roundLabel.setText("Round 1 of " + rounds);
        
        resetUI();
        statusLabel.setText("Click 'New Game' to start playing!");
    }
    
    public int getCurrentRound() {
        return currentRound;
    }
}
