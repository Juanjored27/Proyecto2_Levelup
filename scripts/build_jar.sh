#!/usr/bin/env bash
set -euo pipefail

PROJECT_DIR="$(cd "$(dirname "$0")/.." && pwd)"
cd "$PROJECT_DIR"
mkdir -p dist
./scripts/compile.sh
jar cfm dist/LevelUpArcade.jar scripts/manifest.mf -C out .
echo "JAR ejecutable generado en dist/LevelUpArcade.jar"
