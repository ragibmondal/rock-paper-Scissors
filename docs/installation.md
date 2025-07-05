# 📦 Installation Guide

This guide will help you install and set up the Rock Paper Scissors game on your system.

## 🖥️ System Requirements

### Minimum Requirements
- **Operating System**: Windows 10, macOS 10.14+, or Linux (Ubuntu 18.04+)
- **Java**: Java 11 or higher
- **Memory**: 512 MB RAM
- **Storage**: 100 MB free space
- **Display**: 1024x768 resolution minimum

### Recommended Requirements
- **Operating System**: Windows 11, macOS 12+, or Linux (Ubuntu 20.04+)
- **Java**: Java 17 or higher
- **Memory**: 2 GB RAM
- **Storage**: 500 MB free space
- **Display**: 1920x1080 resolution
- **Audio**: Sound card with speakers/headphones

## 🔧 Installation Methods

### Method 1: Download Pre-built JAR (Recommended)

1. **Download the JAR file**
   ```bash
   # Download from releases
   wget https://github.com/ragibmondal/rock-paper-Scissors/releases/latest/download/rock-paper-scissors-game-1.0.0.jar
   ```

2. **Run the game**
   ```bash
   java -jar rock-paper-scissors-game-1.0.0.jar
   ```

### Method 2: Build from Source

1. **Clone the repository**
   ```bash
   git clone https://github.com/ragibmondal/rock-paper-Scissors.git
   cd rock-paper-Scissors
   ```

2. **Install Java and Maven**
   ```bash
   # Ubuntu/Debian
   sudo apt update
   sudo apt install openjdk-11-jdk maven

   # macOS (using Homebrew)
   brew install openjdk@11 maven

   # Windows
   # Download from https://adoptium.net/ and https://maven.apache.org/
   ```

3. **Build the project**
   ```bash
   mvn clean install
   ```

4. **Run the game**
   ```bash
   java -jar target/rock-paper-scissors-game-1.0.0.jar
   ```

### Method 3: Using Scripts

#### Linux/macOS
```bash
chmod +x run.sh
./run.sh
```

#### Windows
```bash
run.bat
```

## 🚀 First Launch

1. **Start the game** using any of the methods above
2. **Wait for initialization** - The game will load resources
3. **Main menu appears** - You're ready to play!

## 🔍 Troubleshooting

### Common Issues

#### "Java not found" Error
```bash
# Check Java installation
java -version

# If not installed, install Java 11+
sudo apt install openjdk-11-jdk  # Ubuntu/Debian
brew install openjdk@11          # macOS
```

#### "Maven not found" Error (for source builds)
```bash
# Check Maven installation
mvn -version

# If not installed, install Maven
sudo apt install maven           # Ubuntu/Debian
brew install maven               # macOS
```

#### Audio Issues
- Ensure your system has audio enabled
- Check volume settings
- Try running with `java -Djava.awt.headless=false -jar rock-paper-scissors-game-1.0.0.jar`

#### Display Issues
- Update your graphics drivers
- Try running in windowed mode
- Check display scaling settings

## 📞 Support

If you encounter any issues during installation:

- **Email**: [ragib5303721@gmail.com](mailto:ragib5303721@gmail.com)
- **GitHub Issues**: [Create an issue](https://github.com/ragibmondal/rock-paper-Scissors/issues)
- **Documentation**: [Troubleshooting Guide](troubleshooting.md)

---

**Next**: [Quick Start Guide](quick-start.md) 