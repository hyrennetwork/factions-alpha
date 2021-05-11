package net.hyren.factions.alpha

import net.hyren.core.spigot.misc.plugin.CustomPlugin

/**
 * @author Gutyerrez
 */
class FactionsAlphaPlugin : CustomPlugin(false) {

    override fun onEnable() {
        super.onEnable()

        FactionsAlphaProvider.prepare()
    }

}