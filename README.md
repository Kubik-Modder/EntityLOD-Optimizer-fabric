# ELO - Entity LOD Optimizer 🚀
*A smart performance booster for Minecraft servers*

![Mod Version](https://img.shields.io/badge/version-1.1a-blue)  
![Minecraft](https://img.shields.io/badge/Minecraft-1.20.x-green)  
![Loader](https://img.shields.io/badge/loader-Fabric-orange)  
[![GitHub Issues](https://img.shields.io/github/issues/Kubik-Modder/EntityLOD-Optimizer-fabric)](https://github.com/Kubik-Modder/EntityLOD-Optimizer-fabric/issues)

---

## 🔍 Overview
**Entity LOD Optimizer** is a **server-side performance mod** for Minecraft (Fabric 1.20.x) that dynamically optimizes entity AI processing based on player proximity. Perfect for crowded servers or large worlds!

✅ **Reduces CPU load** - entity-heavy scenarios  
✅ **Zero-config** – works out of the box  
✅ **Scalable** - for singleplayer and multiplayer **(untested on multiplayer!)**

---

## ⚡ Features

### 🎯 Smart AI Tiers
| Distance from Player | AI Mode          | Behavior                              |  
|----------------------|------------------|---------------------------------------|  
| **< 10 blocks**      | **Full AI**      | Normal Minecraft behavior             |  
| **10-100 blocks**    | **Simplified AI**| Reduced updates (40-tick interval)    |  
| **> 100 blocks**     | **Disabled AI**  | Frozen (no CPU usage)                 |  

### 🚀 Performance Benefits
- **CPU**: Cuts redundant AI calculations for distant mobs
- **Memory**: Lightweight entity state management
- **Network**: Fewer unnecessary entity updates

---

## ⚠️ Important Notes
> 🚨 **Backup your worlds!**
> - Automated farms **>100 blocks away** may break (e.g., iron farms, mob grinders).

---

## 📥 Installation
1. Install [Fabric Loader](https://fabricmc.net/use/) for **1.20.x**
2. Install [Fabric API](https://modrinth.com/mod/fabric-api) for **1.20.x**
3. Download the latest `.jar` from [Versions](https://modrinth.com/mod/elo/versions)
4. Drop it into your `mods` folder

---

## ❓ Support & Contributing
- **Bug Reports**: [Open an Issue](https://github.com/Kubik-Modder/EntityLOD-Optimizer-fabric/issues)
- **Contributing**: Read the [Guidelines](https://github.com/Kubik-Modder/EntityLOD-Optimizer-fabric/blob/master/CONTRIBUTING.md)

---

- **Like this mod?** ⭐ Star it on [GitHub](https://github.com/Kubik-Modder/EntityLOD-Optimizer-fabric)!