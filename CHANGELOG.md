# 0.2.0+26.2 — Unofficial Fabric port

- Port the original 1.20.1 Fabric source to Minecraft 26.2, Java 25, and
  unobfuscated Mojang names.
- Target the existing server's Fabric Loader 0.19.3 and Fabric API 0.152.2+26.2.
- Preserve all 38 original block IDs and the mineral/copper gameplay.
- Update registration keys, item settings, creative tabs, fuel registration,
  copper interactions, lightning hooks, and redstone connections.
- Generate 26.2 recipes, advancements, singular data directories, loot,
  mining tags, block models, and new item definitions.
- Fix top redstone slabs declaring themselves non-emitting despite defining
  a signal strength, so their intended output can reach adjacent components.
- Add a separate isolated-server integration test mod and asset validation.
- Preserve upstream history and authorship, add the declared MIT license
  text, and carry the copper-code contributor acknowledgement and license.

Automated validation covers the dedicated server and resource references.
Promoted to stable on 2026-10-01 after the server owner confirmed successful
SKlauncher connection and in-game stair crafting/use. The JAR is unchanged
from the tested beta. Shader combinations have not been separately verified.
