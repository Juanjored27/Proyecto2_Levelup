#!/usr/bin/env bash
set -euo pipefail

PROJECT_DIR="$(cd "$(dirname "$0")/.." && pwd)"
CONFIG_FILE="$PROJECT_DIR/config.properties"
BACKUP_DIR="$PROJECT_DIR/backups"

if [ ! -f "$CONFIG_FILE" ]; then
  echo "No se encuentra config.properties"
  exit 1
fi

mkdir -p "$BACKUP_DIR"

DB_URL=$(grep '^db.url=' "$CONFIG_FILE" | cut -d'=' -f2-)
DB_USER=$(grep '^db.user=' "$CONFIG_FILE" | cut -d'=' -f2-)
DB_PASSWORD=$(grep '^db.password=' "$CONFIG_FILE" | cut -d'=' -f2-)

DB_HOST=$(echo "$DB_URL" | sed -E 's#.*//([^:/?]+).*#\1#')
DB_PORT=$(echo "$DB_URL" | sed -E 's#.*//[^:/?]+:([0-9]+).*#\1#')
DB_NAME=$(echo "$DB_URL" | sed -E 's#.*//[^/]+/([^?]+).*#\1#')
TIMESTAMP=$(date +%Y%m%d_%H%M%S)
OUTPUT_FILE="$BACKUP_DIR/${DB_NAME}_${TIMESTAMP}.sql"

mysqldump -h "$DB_HOST" -P "$DB_PORT" -u "$DB_USER" -p"$DB_PASSWORD" --databases "$DB_NAME" > "$OUTPUT_FILE"

echo "Backup creado correctamente: $OUTPUT_FILE"
