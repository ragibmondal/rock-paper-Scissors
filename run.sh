#!/bin/bash
# Quick start script for Rock Paper Scissors Game
echo "🎮 Starting Rock Paper Scissors Game..."
echo "📦 Compiling project..."
mvn compile
echo "🚀 Running game..."
mvn exec:java -Dexec.mainClass="ui.MainWindow"
