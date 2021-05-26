package net.hyren.factions.alpha.misc.minecraft

import net.hyren.core.shared.misc.kotlin.isInt
import org.bukkit.GameMode

/**
 * @author Gutyerrez
 */
internal val SURVIVAL_PATTERN = Regex("G(s|survival)")
internal val CREATIVE_PATTERN = Regex("G(c|creative|criativo)")
internal val ADVENTURE_PATTERN = Regex("G(a|ad|adventure|av|aventura)")
internal val SPECTATOR_PATTERN = Regex("G(spe|spec|spectator|esp|espe|espec|espectador)")

fun String.findGameMode(): GameMode? {
    when {
        this.isInt() -> {
            val value = this.toInt()

            if (value < 3) {
                return GameMode.values()[(when (value) {
                    0 -> 1
                    1 -> 0
                    else -> value
                })]
            }
        }
        this.matches(SURVIVAL_PATTERN) -> GameMode.SURVIVAL
        this.matches(CREATIVE_PATTERN) -> GameMode.CREATIVE
        this.matches(ADVENTURE_PATTERN) -> GameMode.ADVENTURE
        this.matches(SPECTATOR_PATTERN) -> GameMode.SPECTATOR
    }

    return null
}

val GameMode.portugueseName get() = when (this) {
    GameMode.SURVIVAL -> "sobrevivência"
    GameMode.CREATIVE -> "criativo"
    GameMode.ADVENTURE -> "aventura"
    GameMode.SPECTATOR -> "espectador"
}