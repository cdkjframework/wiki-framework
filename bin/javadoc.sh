#!/usr/bin/env bash

set -euo pipefail

BIN_HOME="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
ROOT_DIR="$(cd "$BIN_HOME/.." && pwd)"
cd "$ROOT_DIR"

# Multi-module aggregated API docs are generated under target/site/apidocs.
mvn javadoc:aggregate

CUSTOM_INDEX="$ROOT_DIR/docs/apidocs/index.html"
TARGET_INDEX="$ROOT_DIR/target/site/apidocs/index.html"

if [ -f "$CUSTOM_INDEX" ] && [ -d "$(dirname "$TARGET_INDEX")" ]; then
  cp -vf "$CUSTOM_INDEX" "$TARGET_INDEX"
fi
