package net.hyren.factions.alpha.misc.minecraft

import net.hyren.core.shared.misc.kotlin.isInt
import org.bukkit.GameMode

/**
 * @author Gutyerrez
 */
internal val SURVIVAL_PATTERN = Regex("(s|survival)", RegexOption.IGNORE_CASE)
internal val CREATIVE_PATTERN = Regex("(c|creative|criativo)", RegexOption.IGNORE_CASE)
internal val ADVENTURE_PATTERN = Regex("(a|ad|adventure|av|aventura)", RegexOption.IGNORE_CASE)
internal val SPECTATOR_PATTERN = Regex("(spe|spec|spectator|esp|espe|espec|espectador)", RegexOption.IGNORE_CASE)

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
        SURVIVAL_PATTERN.matches(this) -> GameMode.SURVIVAL
        CREATIVE_PATTERN.matches(this) -> GameMode.CREATIVE
        ADVENTURE_PATTERN.matches(this) -> GameMode.ADVENTURE
        SPECTATOR_PATTERN.matches(this) -> GameMode.SPECTATOR
    }

    return null
}

val GameMode.portugueseName get() = when (this) {
    GameMode.SURVIVAL -> "sobrevivência"
    GameMode.CREATIVE -> "criativo"
    GameMode.ADVENTURE -> "aventura"
    GameMode.SPECTATOR -> "espectador"
}