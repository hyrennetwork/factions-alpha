package net.hyren.factions.alpha

import net.hyren.core.shared.environment.Env
import net.hyren.core.shared.providers.databases.postgres.PostgresDatabaseProvider
import java.net.InetSocketAddress

/**
 * @author Gutyerrez
 */
object FactionsAlphaProvider {

    fun prepare() {
        Databases.Postgres.FACTIONS_ALPHA_POSTGRES.prepare()
    }

    object Databases {

        object Postgres {

            val FACTIONS_ALPHA_POSTGRES = PostgresDatabaseProvider(
                InetSocketAddress(
                    Env.getString("databases.postgresql.host"),
                    Env.getInt("databases.postgresql.port")
                ),
                Env.getString("databases.postgresql.user"),
                Env.getString("databases.postgresql.password"),
                Env.getString("databases.postgresql.factions_database"),
                Env.getString("databases.factions_alpha")
            )

        }

    }

}