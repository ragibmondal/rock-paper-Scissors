# ❓ Frequently Asked Questions (FAQ)

Find answers to common questions about the Rock Paper Scissors game.

## 🚀 Installation & Setup

### Q: What are the system requirements?
**A**: You need Java 11 or higher, and at least 512 MB RAM. The game runs on Windows, macOS, and Linux.

### Q: How do I install Java?
**A**: 
- **Windows**: Download from [adoptium.net](https://adoptium.net/)
- **macOS**: Use `brew install openjdk@11`
- **Linux**: Use `sudo apt install openjdk-11-jdk`

### Q: The game won't start. What should I do?
**A**: Check that Java is installed by running `java -version` in your terminal. If Java isn't found, install it first.

### Q: Can I run the game without installing Java?
**A**: No, Java is required as this is a Java application. However, you can download a pre-built JAR file that only needs Java to run.

## 🎮 Gameplay

### Q: How do I play against the computer?
**A**: Select "Player vs Computer" from the main menu, choose difficulty, set rounds, and click the Rock, Paper, or Scissors buttons.

### Q: What are the keyboard controls for Player vs Player mode?
**A**: 
- **Player 1**: A (Rock), S (Paper), D (Scissors)
- **Player 2**: J (Rock), K (Paper), L (Scissors)

### Q: How long do I have to make my choice?
**A**: You have 10 seconds for each round. If you don't choose in time, it counts as a forfeit.

### Q: What happens if both players don't choose in time?
**A**: If both players forfeit, it counts as a draw.

### Q: How is the winner determined?
**A**: The player with the most wins after all rounds is the winner. If tied, an additional round is played.

## 🤖 AI & Difficulty

### Q: What's the difference between difficulty levels?
**A**: 
- **Easy**: Random choices, good for beginners
- **Medium**: Pattern recognition, moderate challenge
- **Hard**: Advanced algorithms, expert level

### Q: Can I beat the Hard AI?
**A**: Yes! The Hard AI uses advanced strategies but can be defeated with unpredictable play and mixed strategies.

### Q: Does the AI learn from my playing style?
**A**: The AI adapts to patterns it recognizes, so varying your strategy is important.

## 🎵 Audio & Settings

### Q: How do I adjust the volume?
**A**: Go to Settings from the main menu and use the volume slider.

### Q: Can I turn off sound effects?
**A**: Yes, use the mute toggle in the Settings panel.

### Q: The game has no sound. What's wrong?
**A**: Check your system volume, ensure audio is enabled, and verify the game's audio settings aren't muted.

## 🐛 Troubleshooting

### Q: The game crashes on startup
**A**: 
1. Check Java version: `java -version`
2. Ensure you have sufficient memory
3. Try running from command line to see error messages
4. Update your graphics drivers

### Q: The interface looks blurry or distorted
**A**: 
1. Update your graphics drivers
2. Check display scaling settings
3. Try running in windowed mode
4. Ensure your resolution is at least 1024x768

### Q: The game is slow or laggy
**A**: 
1. Close other applications to free up memory
2. Update Java to the latest version
3. Check if your system meets minimum requirements
4. Try reducing display resolution

### Q: I can't hear any sound
**A**: 
1. Check system volume
2. Verify audio drivers are working
3. Check game audio settings
4. Try running with: `java -Djava.awt.headless=false -jar rock-paper-scissors-game-1.0.0.jar`

## 🔧 Technical Questions

### Q: Can I modify the game?
**A**: Yes! The source code is available and licensed under MIT. You can modify, distribute, and use it commercially.

### Q: How do I build from source?
**A**: 
```bash
git clone https://github.com/ragibmondal/rock-paper-Scissors.git
cd rock-paper-Scissors
mvn clean install
java -jar target/rock-paper-scissors-game-1.0.0.jar
```

### Q: What technologies does the game use?
**A**: Java Swing for UI, Maven for building, JUnit for testing, and custom audio management.

### Q: Is the game open source?
**A**: Yes, it's licensed under the MIT License, which allows commercial use, modification, and distribution.

## 📞 Support

### Q: Where can I get help?
**A**: 
- **Email**: [ragib5303721@gmail.com](mailto:ragib5303721@gmail.com)
- **GitHub Issues**: [Create an issue](https://github.com/ragibmondal/rock-paper-Scissors/issues)
- **Documentation**: Check the [docs folder](../docs/)

### Q: How do I report a bug?
**A**: 
1. Go to [GitHub Issues](https://github.com/ragibmondal/rock-paper-Scissors/issues)
2. Click "New Issue"
3. Describe the problem with steps to reproduce
4. Include your system information

### Q: Can I request new features?
**A**: Yes! Use the same GitHub Issues page and label it as a feature request.

## 🎯 Game Strategy

### Q: What's the best strategy?
**A**: There's no guaranteed winning strategy, but being unpredictable and varying your choices works well.

### Q: How do I beat the Hard AI?
**A**: Use mixed strategies, avoid patterns, and don't be predictable. The AI learns from your patterns.

### Q: Is there a way to cheat?
**A**: The game is designed to be fair. Any modifications would be against the spirit of the game.

---

**Still have questions?** Contact us at [ragib5303721@gmail.com](mailto:ragib5303721@gmail.com) 