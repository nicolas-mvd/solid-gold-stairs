// Port of the upstream recipe/model/loot/tag providers to 26.2 JSON formats.
// Uses the author's checked-in stair rotations and references vanilla textures.
import fs from 'node:fs';
import path from 'node:path';
const root = 'src/main/resources';
const mod = 'solid_gold_stairs';
const id = name => `${mod}:${name}`;
const write = (file, data) => {
  const target = path.join(root, file);
  fs.mkdirSync(path.dirname(target), {recursive: true});
  fs.writeFileSync(target, JSON.stringify(data, null, 2) + '\n');
};
const originalStairs = fs.readFileSync(`${root}/assets/${mod}/blockstates/chiseled_quartz_stairs.json`, 'utf8');
const materials = [
  ['coal', 'coal_block', 'coal_block', 0],
  ['iron', 'iron_block', 'iron_block', 1],
  ['gold', 'gold_block', 'gold_block', 2],
  ['redstone', 'redstone_block', 'redstone_block', 0],
  ['emerald', 'emerald_block', 'emerald_block', 2],
  ['lapis', 'lapis_block', 'lapis_block', 1],
  ['diamond', 'diamond_block', 'diamond_block', 2],
  ['netherite', 'netherite_block', 'netherite_block', 3],
  ['chiseled_quartz', 'chiseled_quartz_block', 'chiseled_quartz_block', 0],
  ['quartz_brick', 'quartz_bricks', 'quartz_bricks', 0],
  ['amethyst', 'amethyst_block', 'amethyst_block', 0],
];
for (const wax of ['', 'waxed_']) for (const stage of ['', 'exposed_', 'weathered_', 'oxidized_']) {
  const block = stage ? `${stage}copper` : 'copper_block';
  materials.push([`${wax}${stage}copper`, `${wax}${block}`, block, 1]);
}
const blocks = [], tiers = [[], [], [], []], stairs = [], slabs = [];
function recipe(name, data, ingredient) {
  write(`data/${mod}/recipe/${name}.json`, data);
  write(`data/${mod}/advancement/recipes/${name}.json`, {
    parent: 'minecraft:recipes/root',
    criteria: {
      has_material: {trigger: 'minecraft:inventory_changed', conditions: {items: [{items: ingredient}]}},
      has_recipe: {trigger: 'minecraft:recipe_unlocked', conditions: {recipe: id(name)}},
    },
    requirements: [['has_material', 'has_recipe']], rewards: {recipes: [id(name)]},
  });
}
for (const [material, input, texture, tier] of materials) {
  for (const shape of ['stairs', 'slab']) {
    const name = `${material}_${shape}`, block = id(name);
    blocks.push(block); tiers[tier].push(block);
    (shape === 'stairs' ? stairs : slabs).push(block);
    const textures = {bottom: `minecraft:block/${texture}`, top: `minecraft:block/${texture}`, side: `minecraft:block/${texture}`};
    if (material === 'chiseled_quartz') {
      textures.top = textures.bottom = 'minecraft:block/chiseled_quartz_block_top';
    }
    const model = suffix => `${mod}:block/${name}${suffix}`;
    if (shape === 'stairs') {
      for (const [suffix, parent] of [['', 'stairs'], ['_inner', 'inner_stairs'], ['_outer', 'outer_stairs']]) {
        write(`assets/${mod}/models/block/${name}${suffix}.json`, {parent: `minecraft:block/${parent}`, textures});
      }
      write(`assets/${mod}/blockstates/${name}.json`, JSON.parse(originalStairs.replaceAll('chiseled_quartz_stairs', name)));
    } else {
      for (const [suffix, parent] of [['', 'slab'], ['_top', 'slab_top']]) {
        write(`assets/${mod}/models/block/${name}${suffix}.json`, {parent: `minecraft:block/${parent}`, textures});
      }
      write(`assets/${mod}/blockstates/${name}.json`, {variants: {
        'type=bottom': {model: model('')}, 'type=top': {model: model('_top')},
        'type=double': {model: `minecraft:block/${texture}`},
      }});
    }
    write(`assets/${mod}/models/item/${name}.json`, {parent: model('')});
    write(`assets/${mod}/items/${name}.json`, {model: {type: 'minecraft:model', model: model('')}});
    recipe(`crafting/${name}`, {
      type: 'minecraft:crafting_shaped', category: material === 'redstone' ? 'redstone' : 'building',
      key: {'#': `minecraft:${input}`}, pattern: shape === 'stairs' ? ['#  ', '## ', '###'] : ['###'],
      result: {id: block, count: shape === 'stairs' ? 4 : 6},
    }, `minecraft:${input}`);
    recipe(`stonecutting/${name}`, {
      type: 'minecraft:stonecutting', ingredient: `minecraft:${input}`,
      result: {id: block, count: shape === 'stairs' ? 1 : 2},
    }, `minecraft:${input}`);
    if (material.startsWith('waxed_')) {
      const unwaxed = id(name.slice(6));
      recipe(`crafting/${name}_from_honeycomb`, {
        type: 'minecraft:crafting_shapeless', category: 'building',
        ingredients: [unwaxed, 'minecraft:honeycomb'], result: {id: block, count: 1},
      }, unwaxed);
    }
    const functions = [];
    if (shape === 'slab') functions.push({function: 'minecraft:set_count', count: 2,
      conditions: [{condition: 'minecraft:block_state_property', block, properties: {type: 'double'}}]});
    functions.push({function: 'minecraft:explosion_decay'});
    write(`data/${mod}/loot_table/blocks/${name}.json`, {
      type: 'minecraft:block', pools: [{rolls: 1, entries: [{type: 'minecraft:item', name: block, functions}]}],
    });
  }
}
const tag = (type, name, values) => write(`data/minecraft/tags/${type}/${name}.json`, {replace: false, values});
tag('block', 'mineable/pickaxe', blocks);
tag('block', 'stairs', stairs); tag('block', 'slabs', slabs);
tag('block', 'needs_stone_tool', tiers[1]); tag('block', 'needs_iron_tool', tiers[2]); tag('block', 'needs_diamond_tool', tiers[3]);
tag('block', 'incorrect_for_wooden_tool', tiers.slice(1).flat());
tag('block', 'incorrect_for_gold_tool', tiers.slice(1).flat());
tag('block', 'incorrect_for_stone_tool', tiers.slice(2).flat());
tag('block', 'incorrect_for_copper_tool', tiers.slice(2).flat());
tag('block', 'incorrect_for_iron_tool', tiers[3]);
tag('block', 'guarded_by_piglins', [id('gold_stairs'), id('gold_slab')]);
tag('item', 'piglin_loved', [id('gold_stairs'), id('gold_slab')]);
write(`data/${mod}/tags/block/low_redstone_components.json`, JSON.parse(fs.readFileSync(`${root}/data/${mod}/tags/blocks/low_redstone_components.json`, 'utf8')));
// Preserve upstream's narrowed quartz recipes, excluding chiseled quartz/bricks.
for (const shape of ['stairs', 'slab']) write(`data/minecraft/recipe/quartz_${shape}.json`, {
  type: 'minecraft:crafting_shaped', category: 'building',
  key: {'#': ['minecraft:quartz_block', 'minecraft:quartz_pillar']},
  pattern: shape === 'stairs' ? ['#  ', '## ', '###'] : ['###'],
  result: {id: `minecraft:quartz_${shape}`, count: shape === 'stairs' ? 4 : 6},
});
console.log(`Generated 26.2 resources for ${blocks.length} blocks.`);
