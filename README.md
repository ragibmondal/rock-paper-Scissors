# 🎮 Rock Paper Scissors

<div align="center">

<p>
  <img src="https://img.shields.io/badge/Java-11%2B-orange?style=for-the-badge&logo=java"/>
  <img src="https://img.shields.io/badge/Maven-3.6%2B-blue?style=for-the-badge&logo=apache-maven"/>
  <img src="https://img.shields.io/badge/License-MIT-green?style=for-the-badge&logo=opensourceinitiative"/>
  <img src="https://img.shields.io/badge/Platform-Cross--Platform-lightgrey?style=for-the-badge"/>
</p>

**A modern Rock Paper Scissors game with glassmorphism design and intelligent AI**

[🎥 **Watch Demo Video**](#-demo-video) • [🚀 **Quick Start**](#-quick-start) • [🎯 **Features**](#-features)

</div>

---

<div align="center">

## 🎬 Demo

[![Rock Paper Scissors Demo](https://img.youtube.com/vi/z91_9Y9oRec/0.jpg)](https://www.youtube.com/watch?v=z91_9Y9oRec)

**[Watch on YouTube](https://www.youtube.com/watch?v=z91_9Y9oRec)**

</div>

---

## 📖 Overview

Experience the classic Rock Paper Scissors game reimagined with modern design principles and premium user experience. This Java-based desktop application features a beautiful glassmorphism interface, immersive sound effects, and intelligent AI opponents across multiple difficulty levels.

### ✨ Key Highlights

- 🎨 **Modern Glassmorphism Design** - Beautiful translucent effects and smooth animations
- 🎵 **Immersive Audio Experience** - High-quality sound effects and audio feedback
- 🤖 **Intelligent AI Opponents** - Multiple difficulty levels with adaptive gameplay
- 👥 **Multiplayer Support** - Player vs Player and Player vs Computer modes
- ⚙️ **Customizable Settings** - Volume controls and game configuration options
- 🎯 **Professional UI/UX** - Clean, intuitive interface with responsive controls

---

## 🎯 Features

### 🎮 Game Modes

| Mode | Description | Controls |
|------|-------------|----------|
| **Player vs Computer** | Challenge AI opponents with varying difficulty levels | Mouse clicks for gestures |
| **Player vs Player** | Local multiplayer with keyboard controls | Player 1: A/S/D • Player 2: J/K/L |

### 🎨 Visual Design

- **Glassmorphism Interface** - Translucent panels with blur effects
- **Dynamic Animations** - Smooth hover effects and transitions
- **Premium Color Palette** - Sophisticated dark theme with accent colors
- **Responsive Layout** - Adapts to different screen sizes
- **Professional Typography** - Clean, readable fonts with proper hierarchy

### 🎵 Audio Features

- **Sound Effects** - Gesture selection, countdown, win/lose/draw sounds
- **Audio Controls** - Volume adjustment and mute options
- **Immersive Experience** - Audio feedback for all game interactions

### 🤖 AI Intelligence

| Difficulty | Strategy | Challenge Level |
|------------|----------|-----------------|
| **Easy** | Random choices | Beginner friendly |
| **Medium** | Pattern recognition | Moderate challenge |
| **Hard** | Advanced algorithms | Expert level |

### ⚙️ Configuration Options

- **Round Settings** - 1-99 rounds per game
- **Difficulty Selection** - Choose AI opponent strength
- **Audio Settings** - Volume and sound effect controls
- **Game Preferences** - Customizable game parameters

---

## 🚀 Quick Start

### Prerequisites

- **Java 11 or higher**
- **Maven 3.6+** (for building from source)
- **Operating System** - Windows, macOS, or Linux with GUI support

### Installation

#### Option 1: Download Pre-built JAR
```bash
# Download the latest release JAR file
java -jar rock-paper-scissors-game-1.0.0.jar
```

#### Option 2: Build from Source
```bash
# Clone the repository
git clone https://github.com/ragibmondal/rock-paper-Scissors.git
cd rock-paper-Scissors

# Build the project
mvn clean install

# Run the game
java -jar target/rock-paper-scissors-game-1.0.0.jar
```

#### Option 3: Use Scripts
```bash
# Linux/macOS
./run.sh

# Windows
run.bat
```

---

## 🎮 How to Play

### Player vs Computer Mode
1. **Select Game Mode** - Click "Player vs Computer" from the main menu
2. **Choose Difficulty** - Select Easy, Medium, or Hard AI opponent
3. **Set Rounds** - Configure the number of rounds (1-99)
4. **Make Your Choice** - Click Rock, Paper, or Scissors buttons
5. **Watch the Countdown** - 10-second timer for each round
6. **See Results** - View round outcomes and overall game statistics

### Player vs Player Mode
1. **Select Game Mode** - Click "Player vs Player" from the main menu
2. **Configure Settings** - Set number of rounds
3. **Player Controls**:
   - **Player 1**: A (Rock), S (Paper), D (Scissors)
   - **Player 2**: J (Rock), K (Paper), L (Scissors)
4. **Simultaneous Play** - Both players choose during countdown
5. **View Results** - See round outcomes and final scores

### Game Rules
- **Rock** beats Scissors
- **Paper** beats Rock  
- **Scissors** beats Paper
- **Same gesture** results in a draw
- **Time limit** of 10 seconds per round
- **Best of rounds** determines the winner

---

## 🏗️ Project Structure

```
rockpaper/
├── 📁 src/main/java/
│   ├── 🎮 game/              # Core game logic
│   │   ├── GameEngine.java       # Main game controller
│   │   ├── GameState.java        # Game state management
│   │   ├── GameMode.java         # Game mode definitions
│   │   └── Gesture.java          # Gesture logic
│   ├── 👤 player/             # Player implementations
│   │   ├── Player.java           # Base player interface
│   │   ├── HumanPlayer.java      # Human player logic
│   │   └── ComputerPlayer.java   # AI player implementation
│   ├── 🎨 ui/                 # User interface components
│   │   ├── MainWindow.java       # Main application window
│   │   ├── GamePanel.java        # Game interface panel
│   │   ├── PlayerVsPlayerPanel.java # PvP interface
│   │   └── SettingsDialog.java   # Settings configuration
│   ├── 🔊 audio/              # Audio management
│   │   └── SoundManager.java     # Sound effects controller
│   └── 🛠️ util/              # Utility classes
│       └── InputHandler.java     # Input processing
├── 📁 src/main/resources/
│   ├── 🖼️ images/            # Game assets
│   │   ├── rock_hand.png         # Rock gesture image
│   │   ├── paper_hand.png        # Paper gesture image
│   │   ├── scissors_hand.png     # Scissors gesture image
│   │   └── background.png        # Background images
│   ├── 🔊 sounds/             # Audio files
│   │   ├── win.wav               # Victory sound
│   │   ├── lose.wav              # Defeat sound
│   │   ├── draw.wav              # Draw sound
│   │   └── gesture_select.wav    # Selection sound
│   └── ⚙️ config/             # Configuration files
│       ├── audio.properties      # Audio settings
│       ├── game.properties       # Game configuration
│       └── ui.properties         # UI settings
├── 📁 src/test/java/          # Unit tests
├── 📁 target/                 # Build output
├── 🎥 demo.mp4                # Demo video
└── 📄 README.md               # This file
```

---

## 🛠️ Technical Details

### Architecture
- **Java Swing** - Native desktop UI framework
- **Maven** - Build automation and dependency management
- **JUnit 5** - Unit testing framework
- **Mockito** - Mocking framework for tests

### Design Patterns
- **Observer Pattern** - Game event notifications
- **Strategy Pattern** - AI difficulty implementations
- **Factory Pattern** - Player creation
- **MVC Architecture** - Separation of concerns

### Performance Features
- **Lightweight** - Minimal resource usage
- **Responsive** - Smooth 60 FPS animations
- **Memory Efficient** - Proper resource management
- **Cross-Platform** - Works on Windows, macOS, and Linux

---

## 🎨 Design Philosophy

This project demonstrates modern software design principles:

### 🎯 User Experience
- **Intuitive Interface** - Easy to learn and use
- **Visual Feedback** - Clear indication of game state
- **Accessibility** - High contrast and readable text
- **Responsive Design** - Adapts to user interactions

### 🎨 Visual Design
- **Glassmorphism** - Modern translucent effects
- **Consistent Theming** - Unified color palette and styling
- **Smooth Animations** - Professional transitions and effects
- **Clean Typography** - Readable and aesthetically pleasing fonts

### 🏗️ Code Quality
- **Clean Architecture** - Well-organized, maintainable code
- **Comprehensive Testing** - Unit tests for core functionality
- **Documentation** - Clear code comments and documentation
- **Best Practices** - Following Java and Swing conventions

---

## 🧪 Testing

Run the test suite to ensure everything works correctly:

```bash
# Run all tests
mvn test

# Run specific test class
mvn test -Dtest=GameStateTest

# Generate test coverage report
mvn jacoco:report
```

### Test Coverage
- **Game Logic** - Core game mechanics and rules
- **Player Classes** - Human and computer player behavior
- **UI Components** - Interface functionality
- **Audio System** - Sound effect management

---

## 🤝 Contributing

We welcome contributions! Here's how you can help:

### 🐛 Reporting Issues
1. Check existing issues first
2. Create a detailed bug report
3. Include system information and steps to reproduce

### 💡 Feature Requests
1. Describe the feature clearly
2. Explain the use case and benefits
3. Consider implementation complexity

### 🔧 Code Contributions
1. Fork the repository
2. Create a feature branch
3. Make your changes with tests
4. Submit a pull request

### 📋 Development Setup
```bash
# Clone the repository
git clone https://github.com/ragibmondal/rock-paper-Scissors.git
cd rock-paper-Scissors

# Install dependencies
mvn install

# Run in development mode
mvn exec:java -Dexec.mainClass="ui.MainWindow"
```

---

## 📄 License

<div align="center">

![MIT License](https://img.shields.io/badge/License-MIT-green?style=for-the-badge&logo=opensourceinitiative)

**This project is licensed under the MIT License** - see the [LICENSE](LICENSE) file for details.

</div>

### 📜 License Summary

| Permission | Status | Description |
|------------|--------|-------------|
| **Commercial Use** | ✅ Allowed | Use in commercial projects |
| **Modification** | ✅ Allowed | Modify and adapt the code |
| **Distribution** | ✅ Allowed | Distribute the software |
| **Private Use** | ✅ Allowed | Use in private projects |
| **Liability** | ❌ Not provided | No warranty provided |
| **Warranty** | ❌ Not provided | Software provided "as is" |

### 🔗 License Links
- 📄 **[Full License Text](LICENSE)**
- 📋 **[MIT License Info](https://opensource.org/licenses/MIT)**
- 🏷️ **[License Badge](https://img.shields.io/badge/License-MIT-green?style=for-the-badge&logo=opensourceinitiative)**

---

## 🙏 Acknowledgments

- **Java Swing Team** - For the robust UI framework
- **Maven Community** - For excellent build tools
- **Open Source Contributors** - For inspiration and best practices
- **Game Design Community** - For UI/UX insights and feedback

---

## 📞 Support

Need help? Here are your options:

- 🐛 **Bug Reports** - [Create an issue](../../issues)
- 💡 **Feature Requests** - [Submit a request](../../issues)
- 📧 **Email Support** - [Contact us](mailto:ragiv5303721@gmail.com)
- 📖 **Documentation** - [Read the docs](docs/)

---

<div align="center">

**Made with ❤️ and ☕ by ragibmondal**

[⬆️ Back to Top](#-rock-paper-scissors)

</div>

