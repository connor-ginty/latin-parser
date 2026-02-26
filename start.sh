#!/usr/bin/env bash
set -euo pipefail

SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"

echo "=== Latin Parser ==="
echo ""

# ---- prerequisite checks ----
if ! command -v docker &> /dev/null; then
  echo "ERROR: Docker is not installed or not in PATH."
  echo "       Download Docker Desktop at https://www.docker.com/products/docker-desktop/"
  exit 1
fi

if ! docker info &> /dev/null 2>&1; then
  echo "ERROR: Docker daemon is not running. Please start Docker Desktop and try again."
  exit 1
fi

# ---- build and start ----
cd "$SCRIPT_DIR/docker"

echo "Building images and starting containers..."
echo "(First run downloads dependencies — this may take a few minutes.)"
echo ""

docker compose up --build -d

echo ""
echo "=== Containers started ==="
echo ""
echo "  Frontend  →  http://localhost:8081"
echo "  Backend   →  http://localhost:8080"
echo ""
echo "The backend needs ~30s to pass its health check before the frontend is reachable."
echo ""
echo "Useful commands:"
echo "  docker compose -f \"$SCRIPT_DIR/docker/docker-compose.yml\" logs -f          (follow logs)"
echo "  docker compose -f \"$SCRIPT_DIR/docker/docker-compose.yml\" ps               (container status)"
echo "  docker compose -f \"$SCRIPT_DIR/docker/docker-compose.yml\" down             (stop and remove)"
