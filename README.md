# Control Particles

A **client-side** Fabric mod for **Minecraft 26.3** that lets you decide which particles and screen effects you actually want to see. Nothing is changed on the server: no server install needed, safe to use on any server.

Free to use, modify and share (MIT).

## Features

- **Particle categories**: hide or reduce damage hearts (the little hearts when you hit someone), crit stars, enchanted-hit sparkles, sweep attacks, totems, explosions, fireworks, mob poofs, fire & smoke, drips, bubbles, ambient world particles, portals, sculk, and more.
- **Potion & effect particles**: turn off the swirls per effect (e.g. Invisibility) or per group, such as **Health generation** (Instant Health, Health Boost, Regeneration, Absorption, Saturation), Damage & decay, Movement, Combat buffs, Vision & stealth, Utility, Omens.
- **Walking particles**: sprint puffs are made of block-break particles in vanilla. Hide them, thin them out, or restyle them as white clouds, poofs or dust plumes. Block breaking and block hitting debris can be toggled separately.
- **Rain & snow**: hide the falling streaks *and* the ground splashes independently.
- **Screen effects (client-only)**: remove the Darkness pulse (Warden), Blindness fog and Nausea wobble from your own view. The effects still apply to you; you just don't see the visuals.
- **Every particle type**: a full list generated from the registry (modded particles included) for per-type control.
- **Presets**: Vanilla, PvP Clean, Performance, No Weather Clutter, Clear Screen, Calm, Minimal, plus your own saved presets.

Each setting cycles through **Shown / Reduced / Hidden**. *Reduced* keeps roughly one in four particles.

## Requirements

- Minecraft 26.3
- Fabric Loader 0.20.1 or newer
- [Fabric API](https://modrinth.com/mod/fabric-api)
- Java 25

## Usage

- Open the menu with the **Open Control Particles** key (unbound by default, set it under *Options > Controls > Key Binds > Control Particles*) or run `/controlparticles`.
- Commands:

| Command | What it does |
| --- | --- |
| `/controlparticles` | Open the settings menu |
| `/controlparticles preset list` | List all presets |
| `/controlparticles preset apply <name>` | Apply a preset |
| `/controlparticles preset save <name>` | Save your current settings as a preset |
| `/controlparticles preset delete <name>` | Delete one of your presets |
| `/controlparticles reset` | Back to vanilla |

Settings are stored in `config/controlparticles.json` and can be hand-edited.

## Building

```bash
./gradlew build
```

The jar ends up in `build/libs/`. You need JDK 25.

## How it works

All normal particles go through one place in the client (`ParticleEngine#createParticle`). The mod looks at the particle type, its categories and (for potion swirls) which effect produced it, then drops, thins or restyles it. Block debris, rain streaks and the darkness/blindness/nausea visuals have their own small hooks. Because it is purely client-side, it never touches game state.

## License

[MIT](LICENSE)
