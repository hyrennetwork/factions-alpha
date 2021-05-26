package net.hyren.factions.alpha.commands

import net.hyren.core.shared.commands.argument.Argument
import net.hyren.core.shared.commands.restriction.CommandRestriction
import net.hyren.core.shared.commands.restriction.entities.implementations.GroupCommandRestrictable
import net.hyren.core.shared.groups.Group
import net.hyren.core.shared.users.data.User
import net.hyren.core.spigot.command.CustomCommand
import net.hyren.factions.alpha.misc.minecraft.findGameMode
import net.hyren.factions.alpha.misc.minecraft.portugueseName
import net.md_5.bungee.api.chat.TextComponent
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

/**
 * @author Gutyerrez
 */
class GameModeCommand : CustomCommand("gamemode"), GroupCommandRestrictable {

    override fun getCommandRestriction() = CommandRestriction.GAME

    override fun getAliases0() = arrayOf(
        "gm"
    )

    override fun getArguments() = listOf(
        Argument("modo")
    )

    override fun onCommand(
        commandSender: CommandSender,
        user: User?,
        args: Array<out String>
    ): Boolean {
        commandSender as Player

        val gameMode = args[0].findGameMode()

        if (gameMode == null) {
            commandSender.sendMessage(
                TextComponent("§cEsse modo de jogo não existe.")
            )
            return false
        }

        commandSender.gameMode = gameMode
        commandSender.sendMessage(
            TextComponent("§aSeu modo de jogo foi atualizado para ${gameMode.portugueseName}.")
        )
        return true
    }

    override fun getGroup() = Group.MANAGER

}