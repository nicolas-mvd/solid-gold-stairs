Unofficial Solid Gold Stairs port for **Minecraft 26.2 / Fabric**.

Original mod by **Steve_Steveson and Steve_StevesonB**:
https://github.com/Steve-Steveson/solid_gold_stairs_mod
https://www.curseforge.com/minecraft/mc-mods/solid-gold-stairs
Original copper-code acknowledgement: **Smallinger / Copper Age Backport**.
License remains **MIT**. This fork is independently maintained by nicolas-mvd.

Download **solid_gold_stairs-0.2.0+26.2.jar** and put it in your Minecraft
26.2 Fabric profile's `mods` folder. Install **Fabric API for 26.2** there too.
Requires Fabric Loader **0.19.3+** and Java **25+**. Install on both clients
and server. The `-sources.jar` is source code, not an installable mod.

Includes 38 stairs/slabs, 84 recipes, copper oxidation/waxing/scraping and
lightning cleaning, coal fuel/flammability, netherite protection, and
shape-aware redstone signals. See CHANGELOG.md for port details.

The dedicated-server integration checks passed against an isolated copy of
an existing world with Carpet and EasyAuth. Asset references were validated
against Minecraft 26.2. On 2026-10-01, the server owner confirmed successful
client connection and in-game stair crafting/use in SKlauncher and approved
promoting this release to stable. Shader compatibility depends on the client
mod combination and has not been separately verified.

**Worlds:** back up before installation. Continue your existing 26.2 world;
do not regenerate it. Once these blocks/items are in use, keep this mod
installed to preserve them.
