package net.hyren.factions.alpha

import net.hyren.core.shared.environment.Env
import net.hyren.core.shared.providers.databases.mariadb.MariaDBDatabaseProvider
import java.net.InetSocketAddress

/**
 * @author Gutyerrez
 */
object FactionsAlphaProvider {

    fun prepare() {
        Databases.MariaDB.MARIA_DB_FACTIONS_ALPHA.prepare()
    }

    object Databases {

        object MariaDB {

            val MARIA_DB_FACTIONS_ALPHA = MariaDBDatabaseProvider(
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