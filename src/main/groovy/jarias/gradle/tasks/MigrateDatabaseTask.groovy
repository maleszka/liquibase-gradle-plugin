package jarias.gradle.tasks

import liquibase.Liquibase
import liquibase.database.DatabaseFactory
import liquibase.database.jvm.JdbcConnection
import liquibase.resource.FileSystemResourceAccessor
import org.apache.commons.dbcp.BasicDataSource
import org.gradle.api.tasks.TaskAction

/**
 * This task migrates the database via liquibase
 *
 * @author jarias
 * @since 5/24/13 6:09 PM 
 */
class MigrateDatabaseTask extends AbstractLiquibaseTask {

    /**
     * Migrates the database base on the configuration
     */
    @TaskAction
    def migrateDatabase() {
        Liquibase liquibase = new Liquibase(
                "$BASE_PATH/${configuration.masterChangelogName}",
                new FileSystemResourceAccessor(project.projectDir.absolutePath),
                DatabaseFactory.getInstance().findCorrectDatabaseImplementation(new JdbcConnection(datasource().connection))
        )
        liquibase.update(null)
    }

    /**
     * Creates and returns the datasource
     *
     * @return A DBCP datasource based on the extension configuration
     */
    def datasource() {
        new BasicDataSource(
                driverClassName: configuration.jdbcDriverClassName,
                username: configuration.jdbcUsername,
                password: configuration.jdbcPassword,
                url: configuration.jdbcUrl
        )
    }
}
