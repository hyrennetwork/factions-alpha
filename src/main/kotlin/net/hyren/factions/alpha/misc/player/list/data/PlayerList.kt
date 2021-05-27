package net.hyren.factions.alpha.misc.player.list.data

import com.mojang.authlib.GameProfile
import net.hyren.core.shared.misc.utils.SequencePrefix
import net.hyren.core.spigot.misc.player.sendPacket
import net.minecraft.server.v1_8_R3.*
import org.bukkit.Bukkit
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer
import org.bukkit.entity.Player
import java.util.*

/**
 * @author Gutyerrez
 */
data class PlayerList(
    private val player: Player,
    private val size: Int = 80
) {

    private var initialized = false

    private val SEQUENCE_PREFIX = SequencePrefix()

    private val PLAYERS = MutableList(80) {
        PacketPlayOutPlayerInfo.PlayerInfoData(
            GameProfile(
                UUID.randomUUID(),
                "__${SEQUENCE_PREFIX.next()}"
            ),
            0,
            WorldSettings.EnumGamemode.NOT_SET,
            ChatComponentText("§0")
        )
    }

    companion object {

        const val CHANNEL_NAME = "hyren_custom_player_list"

    }

    fun update(
        index: Int,
        text: String
    ) {
        if (!initialized) {
            val addPlayerInfoPacket = PacketPlayOutPlayerInfo()

            addPlayerInfoPacket.channels.add(CHANNEL_NAME)

            addPlayerInfoPacket.a = PacketPlayOutPlayerInfo.EnumPlayerInfoAction.ADD_PLAYER
            addPlayerInfoPacket.b = PLAYERS

            player.sendPacket(addPlayerInfoPacket)
        }

// Remove current lines
//        val removePlayerInfoPacket = PacketPlayOutPlayerInfo()
//
//        removePlayerInfoPacket.channels.add(CHANNEL_NAME)
//
//        removePlayerInfoPacket.a = PacketPlayOutPlayerInfo.EnumPlayerInfoAction.REMOVE_PLAYER
//        removePlayerInfoPacket.b = listOf(
//            PLAYERS[index]
//        )
//
//        player.sendPacket(removePlayerInfoPacket)

        val updatePlayerInfoPacket = PacketPlayOutPlayerInfo()

        val updatePlayerInfoData = PacketPlayOutPlayerInfo.PlayerInfoData(
            GameProfile(
                UUID.randomUUID(),
                SEQUENCE_PREFIX.next()
            ),
            0,
            WorldSettings.EnumGamemode.NOT_SET,
            ChatComponentText(text)
        )

        PLAYERS[index] = updatePlayerInfoData

        updatePlayerInfoPacket.channels.add(CHANNEL_NAME)

        updatePlayerInfoPacket.a = PacketPlayOutPlayerInfo.EnumPlayerInfoAction.UPDATE_DISPLAY_NAME
        updatePlayerInfoPacket.b = PLAYERS

        player.sendPacket(updatePlayerInfoPacket)

        Bukkit.getOnlinePlayers().forEach {
            val entityPlayer = (it as CraftPlayer).handle

            val removePlayerInfoPacket = PacketPlayOutPlayerInfo(
                PacketPlayOutPlayerInfo.EnumPlayerInfoAction.REMOVE_PLAYER,
                entityPlayer
            )

            player.sendPacket(removePlayerInfoPacket)
            it.sendPacket(removePlayerInfoPacket)

            val spawnEntityPacket = PacketPlayOutSpawnEntity(entityPlayer, 0)

            player.sendPacket(spawnEntityPacket)
            it.sendPacket(spawnEntityPacket)
            player.sendPacket(removePlayerInfoPacket)
            it.sendPacket(removePlayerInfoPacket)
        }
    }

}