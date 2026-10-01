#!/bin/bash
set -euo pipefail
if ((EUID != 0)); then
    printf 'Run as root to read the protected backup and run as minecraft.\n' >&2
    exit 1
fi
repo=$(cd -- "$(dirname -- "$0")/.." && pwd)
snapshot=${1:?Usage: sudo tools/run-smoke.sh /var/backups/minecraft.../timestamp}
[[ $snapshot == /var/backups/minecraft/* || $snapshot == /var/backups/minecraft-daily/* ]] || exit 2
[[ -f $snapshot/SHA256SUMS && -f $snapshot/minecraft-state.tar.gz ]] || exit 2
(cd -- "$snapshot" && sha256sum --check --strict --quiet SHA256SUMS)
test_root=$(mktemp -d /var/tmp/sgs-test-XXXXXX)
printf 'Isolated test directory: %s\n' "$test_root"
tar --no-same-owner -xzf "$snapshot/minecraft-state.tar.gz" -C "$test_root"
state=$test_root/minecraft
install -m 0644 "$repo/tools/smoke-server.properties" "$state/server.properties"
install -m 0644 "$repo/build/libs/solid_gold_stairs-0.2.0+26.2.jar" "$state/mods/"
install -m 0644 "$repo/build/libs/solid_gold_stairs-0.2.0+26.2-smoke-tests.jar" "$state/mods/"
chown -R minecraft:minecraft "$test_root"
cd -- "$state"
# The lifecycle test mod shuts this isolated server down cleanly after testing.
runuser -u minecraft -- /usr/bin/java --enable-native-access=ALL-UNNAMED \
    -Dfile.encoding=UTF-8 -Xms256M -Xmx1500M -XX:ActiveProcessorCount=2 \
    -jar /opt/minecraft/fabric-server-mc.26.2-loader.0.19.3-launcher.1.1.2.jar nogui
if ! grep -q 'SGS_SMOKE_PASS' "$state/logs/latest.log" || grep -q 'SGS_SMOKE_FAIL' "$state/logs/latest.log"; then
    printf 'Integration test failed; retained logs and test world at %s\n' "$test_root" >&2
    exit 1
fi
printf 'Integration test passed; retained test world and logs at %s\n' "$test_root"
