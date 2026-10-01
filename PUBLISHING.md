# Publishing and announcing the port

Use **Solid Gold Stairs: Unofficial Fabric Port** as the project name. State
Minecraft 26.2 in its description and version metadata. Describe it as a port
and prominently credit/link Steve_Steveson and Steve_StevesonB. Preserve MIT
and the acknowledgement of Smallinger. Do not imply the original authors
endorse or maintain this fork.

## Before submitting

Have a player test the release in SKlauncher: join a test server, craft/place
stairs and slabs, check inventory models, inner/outer corners, waterlogging,
and shaders. Take real in-game screenshots of gold stairs, mixed mineral
stairs/slabs, and copper stages. Automated server tests cannot verify shader
rendering or every client mod combination. Keep the first release marked beta
until players complete this visual/multiplayer check.

Make a distinct project icon for this unofficial port; do not present the
original project's unchanged branding as your own new project. Do not include
private server files, worlds, player identities, authentication databases, or
passwords in uploads.

## Modrinth

Sign in at [Modrinth](https://modrinth.com), create a **mod** project, and add
the description below, MIT license, GitHub source/issues links, categories
for decoration/building and redstone, and screenshots. Mark client and server
as required. In content disclosures, declare this is a fork using others'
work and include the original project/author links.

Upload only the regular mod JAR as a beta version, select **Minecraft 26.2**
and **Fabric**, and add **Fabric API** as a required dependency. Include the
changelog and submit for moderation. A Minecraft version port is a substantive
code change, but moderators make the final decision about fork eligibility.

Read [Content Rules](https://modrinth.com/legal/rules) and
[Content Disclosures](https://support.modrinth.com/en/articles/16567675-content-disclosures).

## CurseForge

Sign in and [create a Minecraft mod project](https://authors.curseforge.com/#/projects/create/choose-game).
Select MIT, link the original creator/project prominently, supply an original
description explaining the 26.2 port, and add a distinct 400×400 project icon
and real screenshots. Upload the regular JAR tagged **26.2 / Fabric / Beta**,
with Fabric API as a required dependency, and submit for review. GitHub links
can provide source and issue tracking; CurseForge expects files uploaded to
its own platform rather than third-party download links in the description.

Read [Creating and Submitting a Project](https://support.curseforge.com/support/solutions/articles/9000197241-creating-and-submitting-a-project)
and [Moderation Policies](https://support.curseforge.com/support/solutions/articles/9000197279).
MIT permits a credited fork; each hosting platform still applies its own
submission rules.

## Suggested project description

Solid Gold Stairs: Unofficial Fabric Port brings mineral stairs and slabs to
Minecraft 26.2 on Fabric. It adapts Steve_Steveson and Steve_StevesonB's Solid
Gold Stairs to modern Minecraft APIs, including recipes, item models, tags,
and lightning behavior. The original authors deserve credit for the mod's
design, content, and gameplay. This fork is independently maintained by
nicolas-mvd and is not an official release by the original authors.

Build with 38 variants across eleven mineral/quartz families plus copper's
oxidation and waxing stages. Coal can fuel furnaces, netherite protects itself
from explosions and fire, and redstone variants provide shape-sensitive
signals. Install it on both client and server with Minecraft 26.2, Fabric
Loader 0.19.3+, Java 25+, and Fabric API for 26.2.

Original source: https://github.com/Steve-Steveson/solid_gold_stairs_mod
Original project: https://www.curseforge.com/minecraft/mc-mods/solid-gold-stairs
Copper code acknowledgement: Smallinger, Copper Age Backport.
License: MIT. Source and support: https://github.com/nicolas-mvd/solid-gold-stairs

## Announcement draft

Title: Solid Gold Stairs: Unofficial Fabric Port — mineral stairs/slabs for 26.2

I needed Solid Gold Stairs on our existing Minecraft 26.2 Fabric server, so
I've published an unofficial MIT-licensed port. Original mod by Steve_Steveson
and Steve_StevesonB; this release adapts it to 26.2 and keeps their credits
and Git history, including the acknowledgement of Smallinger's copper work.

It adds 38 stairs/slabs, crafting and stonecutting recipes, copper oxidation
and waxing, coal fuel, blast/fire-resistant netherite, and redstone signals.
Requires the mod and Fabric API on both client and server. Existing 26.2
worlds can continue; back up before installation and keep the mod installed
once you use its blocks.

Source/downloads: https://github.com/nicolas-mvd/solid-gold-stairs
Original: https://www.curseforge.com/minecraft/mc-mods/solid-gold-stairs

Attach screenshots and link the approved Modrinth/CurseForge page when ready.
Check each community's current self-promotion rules before posting. Suitable
places to investigate include r/feedthebeast and Fabric/Minecraft modding
Discord showcase channels. Submit one useful announcement; answer bug reports
through GitHub Issues and avoid repeated promotional posts. Optionally offer
the port back to the original author via a pull request; no message has been
sent on your behalf.
