package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import audio.SoundManager;

/**
 * Clean and modern settings dialog for the Rock Paper Scissors game
 * Focused on simplicity and user-friendliness
 */
public class SettingsDialog extends JDialog {
    private static final long serialVersionUID = 1L;
    
    private SoundManager soundManager;
    private JSlider volumeSlider;
    private JCheckBox soundEnabledCheckBox;
    private JButton testSoundButton;
    private JButton okButton;
    private JButton cancelButton;
    
    // Refined color palette
    private static final Color PRIMARY_BG = new Color(44, 62, 80);      // Dark Slate Blue
    private static final Color SECONDARY_BG = new Color(52, 73, 94);    // Charcoal Grey
    private static final Color ACCENT_BLUE = new Color(52, 152, 219);   // Refined Blue
    private static final Color ACCENT_GREEN = new Color(39, 174, 96);   // Refined Green
    private static final Color ACCENT_RED = new Color(231, 76, 60);     // Refined Red
    private static final Color TEXT_PRIMARY = new Color(236, 240, 241); // Light Grey
    private static final Color TEXT_SECONDARY = new Color(189, 195, 199); // Muted Grey
    private static final Color BORDER_COLOR = new Color(149, 165, 166); // Subtle Border
    
    public SettingsDialog(Frame parent, SoundManager soundManager) {
        super(parent, "Settings", true);
        this.soundManager = soundManager;
        
        initializeComponents();
        setupLayout();
        setupEventHandlers();
        setupDialog();
        loadCurrentSettings();
    }
    
    private void initializeComponents() {
        // Volume control
        volumeSlider = new JSlider(0, 100, 50);
        volumeSlider.setMajorTickSpacing(25);
        volumeSlider.setMinorTickSpacing(5);
        volumeSlider.setPaintTicks(true);
        volumeSlider.setPaintLabels(true);
        volumeSlider.setBackground(SECONDARY_BG);
        volumeSlider.setForeground(TEXT_PRIMARY);
        
        // Sound enabled checkbox
        soundEnabledCheckBox = new JCheckBox("Enable Sound Effects");
        soundEnabledCheckBox.setSelected(true);
        soundEnabledCheckBox.setBackground(SECONDARY_BG);
        soundEnabledCheckBox.setForeground(TEXT_PRIMARY);
        soundEnabledCheckBox.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        
        // Test sound button
        testSoundButton = createCleanButton("Test Sound", ACCENT_BLUE);
        
        // Dialog buttons
        okButton = createCleanButton("OK", ACCENT_GREEN);
        cancelButton = createCleanButton("Cancel", ACCENT_RED);
    }
    
    private JButton createCleanButton(String text, Color color) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        button.setForeground(TEXT_PRIMARY);
        button.setBackground(color);
        button.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(color.darker(), 1),
            BorderFactory.createEmptyBorder(8, 16, 8, 16)
        ));
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        // Subtle hover effects
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
        getContentPane().setBackground(PRIMARY_BG);
        
        // Main content panel
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBackground(SECONDARY_BG);
        contentPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(BORDER_COLOR, 1),
            BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));
        
        // Title
        JLabel titleLabel = new JLabel("Audio Settings", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        titleLabel.setForeground(TEXT_PRIMARY);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 15, 0));
        
        // Sound enabled section
        JPanel soundEnabledPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        soundEnabledPanel.setBackground(SECONDARY_BG);
        soundEnabledPanel.add(soundEnabledCheckBox);
        
        // Volume section
        JLabel volumeLabel = new JLabel("Volume:");
        volumeLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        volumeLabel.setForeground(TEXT_PRIMARY);
        
        JPanel volumePanel = new JPanel(new BorderLayout());
        volumePanel.setBackground(SECONDARY_BG);
        volumePanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        volumePanel.add(volumeLabel, BorderLayout.NORTH);
        volumePanel.add(volumeSlider, BorderLayout.CENTER);
        
        // Test sound section
        JPanel testPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        testPanel.setBackground(SECONDARY_BG);
        testPanel.add(testSoundButton);
        
        // Add components to content panel
        contentPanel.add(titleLabel);
        contentPanel.add(soundEnabledPanel);
        contentPanel.add(volumePanel);
        contentPanel.add(testPanel);
        
        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        buttonPanel.setBackground(PRIMARY_BG);
        buttonPanel.add(cancelButton);
        buttonPanel.add(okButton);
        
        // Add to dialog
        add(contentPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }
    
    private void setupEventHandlers() {
        // Volume slider
        volumeSlider.addChangeListener(e -> {
            if (soundManager != null) {
                soundManager.setVolume(volumeSlider.getValue() / 100.0f);
            }
        });
        
        // Sound enabled checkbox
        soundEnabledCheckBox.addActionListener(e -> {
            boolean enabled = soundEnabledCheckBox.isSelected();
            volumeSlider.setEnabled(enabled);
            testSoundButton.setEnabled(enabled);
            if (soundManager != null) {
                soundManager.setSoundEnabled(enabled);
            }
        });
        
        // Test sound button
        testSoundButton.addActionListener(e -> {
            if (soundManager != null && soundEnabledCheckBox.isSelected()) {
                soundManager.playSound("gesture_select");
            }
        });
        
        // Dialog buttons
        okButton.addActionListener(e -> {
            saveSettings();
            dispose();
        });
        
        cancelButton.addActionListener(e -> {
            dispose();
        });
    }
    
    private void setupDialog() {
        setSize(350, 250);
        setLocationRelativeTo(getParent());
        setResizable(false);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }
    
    private void loadCurrentSettings() {
        if (soundManager != null) {
            // Load current volume (assuming 50% as default)
            volumeSlider.setValue(50);
            
            // Load current sound enabled state (assuming true as default)
            soundEnabledCheckBox.setSelected(true);
            
            // Update component states
            boolean enabled = soundEnabledCheckBox.isSelected();
            volumeSlider.setEnabled(enabled);
            testSoundButton.setEnabled(enabled);
        }
    }
    
    private void saveSettings() {
        if (soundManager != null) {
            soundManager.setVolume(volumeSlider.getValue() / 100.0f);
            soundManager.setSoundEnabled(soundEnabledCheckBox.isSelected());
        }
    }
}

