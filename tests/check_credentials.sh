#!/usr/bin/env bash
set -euo pipefail

ROOT="$(cd "$(dirname "${BASH_SOURCE[0]}")/.." && pwd)"

if rg -n --hidden --glob '!.git/**' --glob '!**/tests/check_credentials.sh' \
  '(AKIA[0-9A-Z]{16}|ASIA[0-9A-Z]{16}|AIza[0-9A-Za-z_-]{35}|github_pat_|ghp_|BEGIN (RSA |EC |OPENSSH )?PRIVATE KEY)' \
  "$ROOT"; then
  printf 'Credential-like value found\n' >&2
  exit 1
fi

if git -C "$ROOT" ls-files | rg -i 'application-credentials?\.ya?ml$|(^|/)\.env($|\.)'; then
  printf 'Credential file is tracked; commit an example file instead\n' >&2
  exit 1
fi

printf 'Credential safety checks passed\n'
