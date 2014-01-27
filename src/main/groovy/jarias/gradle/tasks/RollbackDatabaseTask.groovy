package jarias.gradle.tasks

import liquibase.Liquibase
import liquibase.database.DatabaseFactory
import liquibase.database.jvm.JdbcConnection
import liquibase.resource.FileSystemResourceAccessor
import org.gradle.api.tasks.TaskAction

import java.sql.Connection

/**
 * This task rollbacks the database via liquibase
 *
 * @author Julio Arias
 * @since 6/24/13 7:28 PM 
 */
class RollbackDatabaseTask extends AbstractLiquibaseTask {
    /**
     * Rollbacks the database
     */
    @TaskAction
    def rollbackDatabase() {
        doInLiquibaseClasspath {
            final Connection connection = datasource().connection
            Liquibase liquibase = new Liquibase(
                    "${configuration.masterChangelogName}",
                    new FileSystemResourceAccessor("$project.projectDir.absolutePath/$configuration.basePath"),
                    DatabaseFactory.getInstance().findCorrectDatabaseImplementation(new JdbcConnection(connection))
            )
            liquibase.rollback(1, null)
        }
    }
}
