#!/usr/bin/env bash
set -euo pipefail

PROJECT_DIR="$(cd "$(dirname "$0")/.." && pwd)"
cd "$PROJECT_DIR"
rm -rf out_test
mkdir -p out_test
javac -cp "lib/*:out" -d out_test $(find test -name "*.java")
java -jar lib/junit-platform-console-standalone-1.10.2.jar -cp "out:out_test" --scan-classpath
