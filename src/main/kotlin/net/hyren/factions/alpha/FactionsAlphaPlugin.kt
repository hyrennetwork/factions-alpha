package net.hyren.factions.alpha

import net.hyren.core.spigot.command.registry.CommandRegistry
import net.hyren.core.spigot.misc.plugin.CustomPlugin
import net.hyren.factions.alpha.commands.GameModeCommand
import org.bukkit.Bukkit

/**
 * @author Gutyerrez
 */
class FactionsAlphaPlugin : CustomPlugin() {

    override fun onEnable() {
        FactionsAlphaProvider.prepare()

        /**
         * Commands
         */

        CommandRegistry.registerCommand(GameModeCommand())

        /**
         * World settings
         */

        Bukkit.getServer().worlds.forEach {
            it.isAutoSave = false

            it.isThundering = false
            it.weatherDuration = 0

            it.ambientSpawnLimit = 0
            it.animalSpawnLimit = 0
            it.monsterSpawnLimit = 0

            it.setTicksPerAnimalSpawns(99999)
            it.setTicksPerMonsterSpawns(99999)

            it.setStorm(false)

            it.setGameRuleValue("randomTickSpeed", "-999")
            it.setGameRuleValue("mobGriefing", "false")
            it.setGameRuleValue("doMobSpawning", "false")
            it.setGameRuleValue("doMobLoot", "false")
            it.setGameRuleValue("doFireTick", "false")
            it.setGameRuleValue("doDaylightCycle", "false")

            it.time = 1200
        }
    }

}