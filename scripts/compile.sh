#!/usr/bin/env bash
set -euo pipefail

PROJECT_DIR="$(cd "$(dirname "$0")/.." && pwd)"
cd "$PROJECT_DIR"
rm -rf out
mkdir -p out
javac -cp "lib/*" -d out $(find src -name "*.java")
cp config.properties out/
echo "Proyecto compilado correctamente en out/"
