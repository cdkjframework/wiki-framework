#!/usr/bin/env bash

set -euo pipefail

BIN_HOME="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
ROOT_DIR="$(cd "$BIN_HOME/.." && pwd)"
cd "$ROOT_DIR"

mvn -T 1C clean source:jar javadoc:javadoc install -Dmaven.test.skip=true -Dmaven.javadoc.skip=false
