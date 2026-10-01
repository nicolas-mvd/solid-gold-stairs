import fs from 'node:fs';
import path from 'node:path';
import assert from 'node:assert/strict';
import {execFileSync} from 'node:child_process';
const root = 'src/main/resources';
const vanillaJar = process.argv[2];
assert(vanillaJar, 'Usage: node tools/validate-resources.mjs /path/to/minecraft-client.jar');
const vanilla = new Set(execFileSync('unzip', ['-Z1', vanillaJar], {maxBuffer: 16 * 1024 * 1024}).toString().split('\n'));
const read = f => JSON.parse(fs.readFileSync(path.join(root, f), 'utf8'));
const exists = f => f.startsWith('assets/minecraft/') ? vanilla.has(f) : fs.existsSync(path.join(root, f));
const ref = (name, type, ext) => {
  const [ns, part] = name.split(':');
  const file = `assets/${ns}/${type}/${part}.${ext}`;
  assert(exists(file), `Missing ${file}`);
};
const statesDir = `${root}/assets/solid_gold_stairs/blockstates`;
const lang = read('assets/solid_gold_stairs/lang/en_us.json');
let states = 0, models = 0;
for (const f of fs.readdirSync(statesDir)) {
  const name = f.slice(0, -5);
  const state = read(`assets/solid_gold_stairs/blockstates/${f}`);
  assert.equal(Object.keys(state.variants).length, name.endsWith('_stairs') ? 40 : 3);
  for (const v of Object.values(state.variants)) ref(v.model, 'models', 'json');
  assert(lang[`block.solid_gold_stairs.${name}`], `Missing translation ${name}`);
  const item = read(`assets/solid_gold_stairs/items/${f}`);
  assert.equal(item.model.type, 'minecraft:model');
  ref(item.model.model, 'models', 'json');
  states++;
}
for (const f of fs.readdirSync(`${root}/assets/solid_gold_stairs/models/block`)) {
  const model = read(`assets/solid_gold_stairs/models/block/${f}`);
  ref(model.parent, 'models', 'json');
  for (const texture of Object.values(model.textures)) if (!texture.startsWith('#')) ref(texture, 'textures', 'png');
  models++;
}
assert.equal(states, 38);
console.log(`Validated ${states} blockstates/item definitions and ${models} models against Minecraft 26.2 assets.`);
