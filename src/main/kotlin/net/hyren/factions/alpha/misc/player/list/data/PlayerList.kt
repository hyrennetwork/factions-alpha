package net.hyren.factions.alpha.misc.player.list.data

import com.mojang.authlib.GameProfile
import net.hyren.core.shared.CoreConstants
import net.hyren.core.shared.misc.utils.*
import net.hyren.core.spigot.misc.player.sendPacket
import net.minecraft.server.v1_8_R3.*
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

    private val SEQUENCE_PREFIX = SequencePrefix()

    private val PLAYERS = MutableList(80) {
        PacketPlayOutPlayerInfo.PlayerInfoData(
            GameProfile(
                UUID.randomUUID(),
                "__${SEQUENCE_PREFIX.next()}"
            ),
            0,
            WorldSettings.EnumGamemode.NOT_SET,
            ChatComponentText(
                generateRandomColors()
            )
        )
    }

    companion object {

        const val CHANNEL_NAME = "hyren_custom_player_list"

    }

    fun update(
        index: Int,
        text: String
    ) {
        val entityPlayer = (player as CraftPlayer).handle

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

        val removePlayerInfoPacket = PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.REMOVE_PLAYER, entityPlayer)

        player.sendPacket(removePlayerInfoPacket)
    }

}

internal fun generateRandomColors(): String {
    return buildString {
        for (i in 1..9) {
            val code = CoreConstants.RANDOM.nextInt(i)

            append(
                ChatColor.COLOR_CHAR + code
            )
        }
    }
}