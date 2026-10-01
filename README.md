# Solid Gold Stairs — Unofficial Fabric Port

An unofficial Minecraft **26.2** port of **Solid Gold Stairs** by
[Steve_Steveson and Steve_StevesonB](https://github.com/Steve-Steveson/solid_gold_stairs_mod).
The original mod is on [CurseForge](https://www.curseforge.com/minecraft/mc-mods/solid-gold-stairs).
The original authors created the content and gameplay; nicolas-mvd maintains
this version port. No affiliation or endorsement by the original authors is implied.

Adds 38 stair and slab variants for coal, iron, gold, redstone, emerald, lapis,
diamond, netherite, chiseled quartz, quartz bricks, amethyst, and uncut copper
in every oxidation and waxing stage. Includes crafting, stonecutting, and
waxing recipes. Coal variants burn and fuel furnaces; netherite resists blasts
and its items resist fire. Copper oxidizes, waxes, scrapes, and cleans under
lightning. Redstone stairs output 11, slabs 7, and double slabs 15, with the
original shape-aware connection rules.

## Install in SKlauncher

1. Select your **Minecraft 26.2 Fabric** installation. Use Fabric Loader
   **0.19.3 or newer** and Java **25 or newer**.
2. Open that installation's directory through SKlauncher. Its `mods` directory
   may differ from your default `.minecraft/mods` directory.
3. Put `solid_gold_stairs-0.2.0+26.2.jar` and a **26.2 Fabric API** JAR there.
   The tested Fabric API version is **0.152.2+26.2**. Obtain Fabric API from its
   [official project](https://modrinth.com/mod/fabric-api/versions?g=26.2).
4. Start the Fabric installation and join the server. Every player and the
   server must have this mod. Iris/Sodium/shaders can remain in the profile,
   provided they also support Minecraft 26.2.

Download the regular JAR from [GitHub Releases](https://github.com/nicolas-mvd/solid-gold-stairs/releases).
Do not install the `-sources.jar` or `-smoke-tests.jar`.
Do not install the original 1.20.1/Forge/NeoForge mod alongside this port.
See [SKlauncher's Fabric instructions](https://docs.skmedix.pl/modding/mod-loaders/fabric).

## Existing worlds

This port adds blocks and does not generate a replacement world. It retains
the `solid_gold_stairs` namespace and original block IDs. Minecraft 26.2 worlds
can continue in place. Adding this mod does **not** make older Minecraft worlds
safe to open directly in 26.2; normal Minecraft version migration rules apply.

Make a verified backup before adding any content mod. Once its blocks/items
are saved in a world, keep the mod installed. Removing it and opening the
world can remove those blocks/items. Never downgrade Minecraft or restore an
old world snapshot over newer play progress as part of ordinary mod removal.

## Development

Use JDK 25. The Gradle wrapper downloads its pinned Gradle distribution.

```sh
bash ./gradlew build smokeJar
node tools/generate-resources.mjs
node tools/validate-resources.mjs "$HOME/.gradle/caches/fabric-loom/26.2/minecraft-client.jar"
```

The normal artifact is in `build/libs`. Original 1.20.1 Java datagen sources
are retained as historical reference and excluded from compilation. The Node
generator ports their recipes, loot, tags, and models to 26.2 formats.
`tools/migrate-names.mjs` documents the one-time initial name migration; do
not run it against the already migrated source.

The separate `smokeJar` integration-test mod tests registration, recipes,
placement, drops, fuel, netherite properties, redstone output, copper
interactions, oxidation, and lightning. It refuses to run outside
`/var/tmp/sgs-test-*`. It must never be installed on a live server.

`sudo bash tools/run-smoke.sh /var/backups/minecraft-daily/<timestamp>` is a
host-specific test helper: it verifies a snapshot, restores an isolated copy,
loads the full Fabric/Carpet/EasyAuth stack on localhost port 25566, tests it,
and shuts it down cleanly. It retains its test directory and logs.

## License and credits

MIT, matching the original project's declared license. See [LICENSE](LICENSE)
and [NOTICE](NOTICE). The original Git history is preserved. The original
WeatheringHelper acknowledges **Smallinger's Copper Age Backport**; that
credit is preserved, and its repository currently publishes under CC0-1.0.
Minecraft textures are referenced from the game, not bundled.

See [PUBLISHING.md](PUBLISHING.md) for project submission steps and an announcement draft.
