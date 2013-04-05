package jarias.gradle

import jarias.gradle.tasks.MasterChangelogTask
import org.gradle.api.Plugin
import org.gradle.api.Project

/**
 * @author jarias
 * @since 4/3/13 10:29 PM 
 */
class LiquibasePlugin implements Plugin<Project> {
    static final String LIQUIBASE = 'liquibase'

    @Override
    void apply(Project target) {
        //liquibase extension, for plugin configuration
        target.extensions.create(LIQUIBASE, LiquibasePluginExtension)
        //liquibase configuration
        target.configurations.create(LIQUIBASE)
                .setVisible(false)
                .setTransitive(true)
                .setDescription('The Liquibase libraries and JDBC driver to use for this project.')
        //liquibase tasks
        target.task('masterChangelog', type: MasterChangelogTask)
    }
}
