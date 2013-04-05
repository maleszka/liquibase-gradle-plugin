package jarias.gradle.tasks

import org.gradle.api.tasks.TaskAction

/**
 * This task generates the master changelog file in "src/main/resources/db/changelog" folder,
 * the task should be idempotent so the file doesn't gets overwritten if called multiple times
 *
 * @author jarias
 * @since 4/5/13 5:52 PM 
 */
class MasterChangelogTask extends AbstractLiquibaseTask {
    /**
     * Generates the directories and changelog file if needed using Gradle's API,
     * if the file already exists it should not overwrite its contents
     */
    @TaskAction
    def generateMasterChangelog() {
        logger.debug('Creating master changelog file [{}] on [{}]', project.liquibase.masterChangelogName, BASE_PATH)
        project.mkdir(BASE_PATH)
        File masterChangelog = project.file("$BASE_PATH/${project.liquibase.masterChangelogName}")
        if (!masterChangelog.exists()) {
            logger.debug('Master changelog doesn\'t exits, writing default XML')
            masterChangelog.write LIQUIBASE_XML, 'UTF-8'
        } else {
            logger.debug('Master changelog does exits, doing nothing')
        }
    }
}
