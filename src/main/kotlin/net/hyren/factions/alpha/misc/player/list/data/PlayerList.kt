package net.hyren.factions.alpha.misc.player.list.data

import com.mojang.authlib.GameProfile
import net.hyren.core.shared.misc.utils.SequencePrefix
import net.hyren.core.spigot.misc.player.sendPacket
import net.minecraft.server.v1_8_R3.*
import org.bukkit.entity.Player
import java.util.*

/**
 * @author Gutyerrez
 */
data class PlayerList(
    val player: Player,
    private val size: Int = 80
) {

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

        fun getDefaultPacket(): PacketPlayOutPlayerInfo {
            val sequencePrefix = SequencePrefix()
            val packet = PacketPlayOutPlayerInfo()

            packet.channels.add(CHANNEL_NAME)

            packet.a = PacketPlayOutPlayerInfo.EnumPlayerInfoAction.ADD_PLAYER
            packet.b = MutableList(1000) {
                PacketPlayOutPlayerInfo.PlayerInfoData(
                    GameProfile(
                        UUID.randomUUID(),
                        "__${sequencePrefix.next()}"
                    ),
                    0,
                    WorldSettings.EnumGamemode.NOT_SET,
                    ChatComponentText("§0")
                )
            }

            return packet
        }

    }

    fun update(
        index: Int,
        text: String
    ) {
        val updatePlayerInfo = PacketPlayOutPlayerInfo()

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

        updatePlayerInfo.channels.add(CHANNEL_NAME)

        updatePlayerInfo.a = PacketPlayOutPlayerInfo.EnumPlayerInfoAction.ADD_PLAYER
        updatePlayerInfo.b = PLAYERS

        player.sendPacket(updatePlayerInfo)
    }

}