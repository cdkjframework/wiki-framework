#!/usr/bin/env bash

set -euo pipefail

ROOT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
cd "$ROOT_DIR"

help() {
  echo "--------------------------------------------------------------------------"
  echo ""
  echo "usage: ./wiki.sh [install | doc | pack]"
  echo ""
  echo "  install    Install wiki to your local Maven repository."
  echo "  doc        Generate aggregated Java API docs under target/site/apidocs."
  echo "  pack       Build jar packages by Maven."
  echo ""
  echo "--------------------------------------------------------------------------"
}

if [ -x "$ROOT_DIR/bin/logo.sh" ]; then
  "$ROOT_DIR/bin/logo.sh"
fi

case "${1:-}" in
  install)
    bash "$ROOT_DIR/bin/install.sh"
    ;;
  doc)
    bash "$ROOT_DIR/bin/javadoc.sh"
    ;;
  pack)
    bash "$ROOT_DIR/bin/package.sh"
    ;;
  -h|--help|help|"")
    help
    ;;
  *)
    echo "Unknown command: $1"
    help
    exit 1
    ;;
esac
