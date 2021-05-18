package net.hyren.factions.alpha

import net.hyren.core.shared.environment.Env
import net.hyren.core.shared.providers.databases.postgresql.PostgreSQLDatabaseProvider
import java.net.InetSocketAddress

/**
 * @author Gutyerrez
 */
object FactionsAlphaProvider {

    fun prepare() {
        Databases.PostgreSQL.POSTGRESQL_FACTIONS_ALPHA.prepare()
    }

    object Databases {

        object PostgreSQL {

            val POSTGRESQL_FACTIONS_ALPHA = PostgreSQLDatabaseProvider(
                InetSocketAddress(
                    Env.getString("databases.postgresql.host"),
                    Env.getInt("databases.postgresql.port")
                ),
                Env.getString("databases.postgresql.user"),
                Env.getString("databases.postgresql.password"),
                Env.getString("databases.postgresql.database"),
                Env.getString("databases.postgresql.factions.alpha")
            )

        }

    }

}