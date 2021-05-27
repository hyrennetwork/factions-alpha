package net.hyren.factions.alpha

import net.hyren.core.shared.CoreProvider
import net.hyren.core.shared.applications.status.ApplicationStatus
import net.hyren.core.shared.applications.status.task.ApplicationStatusTask
import net.hyren.core.shared.scheduler.AsyncScheduler
import net.hyren.core.spigot.CoreSpigotConstants
import net.hyren.core.spigot.command.registry.CommandRegistry
import net.hyren.core.spigot.misc.plugin.CustomPlugin
import net.hyren.core.spigot.misc.utils.*
import net.hyren.factions.alpha.commands.GameModeCommand
import net.hyren.factions.alpha.misc.player.list.data.PlayerList
import net.minecraft.server.v1_8_R3.PacketPlayOutPlayerInfo
import org.bukkit.Bukkit
import java.util.concurrent.TimeUnit

/**
 * @author Gutyerrez
 */
class FactionsAlphaPlugin : CustomPlugin() {

    private var onlineSince = 0L

    override fun onEnable() {
        FactionsAlphaProvider.prepare()

        onlineSince = System.currentTimeMillis()

        /**
         * Commands
         */

        CommandRegistry.registerCommand(GameModeCommand())

        /**
         * World settings
         */

        Bukkit.getServer().worlds.forEach {
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

        /**
         * Application Status
         */

        AsyncScheduler.scheduleAsyncRepeatingTask(
            object : ApplicationStatusTask(
                ApplicationStatus(
                    CoreProvider.application.name,
                    CoreProvider.application.applicationType,
                    CoreProvider.application.server,
                    CoreProvider.application.address,
                    onlineSince
                )
            ) {
                override fun buildApplicationStatus(
                    applicationStatus: ApplicationStatus
                ) {
                    val runtime = Runtime.getRuntime()

                    applicationStatus.heapSize = runtime.totalMemory()
                    applicationStatus.heapMaxSize = runtime.maxMemory()
                    applicationStatus.heapFreeSize = runtime.freeMemory()

                    applicationStatus.onlinePlayers = Bukkit.getOnlinePlayers().size
                }
            },
            0,
            1,
            TimeUnit.SECONDS
        )

        /**
         * Protocol Handler
         */

        CoreSpigotConstants.PROTOCOL_HANDLER?.registerListener(
            object : PacketListener() {

                override fun onSent(
                    event: PacketEvent
                ) {
                    val player = event.player
                    val packet = event.packet

                    if (packet is PacketPlayOutPlayerInfo) {
                        if (!packet.channels.contains(PlayerList.CHANNEL_NAME)) {
                            println("Não contém")

                            event.isCancelled = true
                        } else {
                            println("Contém")

                            val toRemove = packet.b.stream().filter {
                                it.a().name == player.name
                            }.findFirst().orElse(null) ?: return

                            packet.b.remove(toRemove)
                        }
                    }
                }

            }
        )
    }

}