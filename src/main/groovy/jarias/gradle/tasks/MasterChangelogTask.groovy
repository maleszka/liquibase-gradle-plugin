package jarias.gradle.tasks

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction

/**
 * This task generates the master changelog file in "src/main/resources/db/changelog" folder,
 * the task should be idempotent so the file doesn't gets overwritten if called multiple times
 *
 * @author jarias
 * @since 4/5/13 5:52 PM 
 */
class MasterChangelogTask extends DefaultTask {
    /**
     * The base path for the master change log
     */
    static final String BASE_PATH = 'src/main/resources/db/changelog'
    /**
     * The base liquibase changelog XML content
     */
    static final String LIQUIBASE_XML = '<?xml version="1.0" encoding="UTF-8"?><databaseChangeLog xmlns="http://www.liquibase.org/xml/ns/dbchangelog" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:schemaLocation="http://www.liquibase.org/xml/ns/dbchangelog http://www.liquibase.org/xml/ns/dbchangelog/dbchangelog-2.0.xsd"></databaseChangeLog>'

    /**
     * Generates the directories and changelog file if needed using Gradle's API,
     * if the file already exists it should not overwrite its contents
     */
    @TaskAction
    def generateMasterChangelog() {
        logger.debug('Creating master changelog file [{}] on [{}]', project.liquibase.masterChangelogName, BASE_PATH)
        project.mkdir(BASE_PATH)
        File masterChangelog = project.file("$BASE_PATH/${project.liquibase.masterChangelogName}")
        masterChangelog.write LIQUIBASE_XML, 'UTF-8'
    }
}
