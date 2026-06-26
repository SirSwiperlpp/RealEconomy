# RealEconomy

A lightweight but production ready dual currency economy plugin for Minecraft 1.21, built with MySQL and Redis.

## Features

- **Dual Currency System**: Coins (tradeable) and Shards (non-tradeable, earned only)
- **MySQL persistence**: Player balances stored reliably across restarts
- **Redis integration**: Fast caching layer for high-performance networks
- **Public API**: Other plugins can hook into RealEconomy via `EcoAPI`
- **Fully translatable**: All messages configurable via `lang.ini`
- **Permission-based commands**: Granular access control out of the box

## Commands

| Command                                     | Description                      | Permission      |
|---------------------------------------------|----------------------------------|-----------------|
| `/pay <player> <amount>`                    | Transfer coins to another player | `pay.use`       |
| `/eco set <player> <coins/shards> <amount>` | Set a player's balance           | `eco.admin.set` |
| `/eco ` | Checks player balance            | `eco.use`       |

## API Usage

Other plugins can integrate with RealEconomy:

```java
// Add coins
EcoAPI.addCoins(player, 500);

// Remove coins (returns false if balance insufficient)
boolean success = EcoAPI.removeCoins(player, 200);

// Transfer between players
EcoAPI.sendCoins(sender, target, 100);

// Shards
EcoAPI.addShards(player, 10);
```

## Requirements

- Paper 1.21+
- MySQL database
- Redis server

## Installation

1. Drop `RealEconomy.jar` into your `plugins/` folder
2. Start the server once to generate `config.yml`
3. Fill in your MySQL and Redis credentials in `config.yml`
4. Restart the server

## Configuration

```yaml
# config.yml
mysql:
  host: localhost
  port: 3306
  database: realeconomy
  user: root
  pass: secret

redis:
  host: localhost
  port: 6379
  pass: secret
```

## Tech Stack

- Java 21
- Paper API 1.21
- MySQL via JDBC
- Redis via Jedis

---

Built with ❤️ by [Realityrift Studios](https://realityrift.de)