package jarias.gradle.tasks

import org.custommonkey.xmlunit.Diff
import org.gradle.api.Project
import org.gradle.api.tasks.TaskAction
import org.gradle.testfixtures.ProjectBuilder
import org.junit.After
import org.junit.Before
import org.junit.Test

import java.lang.reflect.Method

/**
 * @author jarias
 * @since 4/5/13 5:58 PM 
 */
class MasterChangelogTaskTest {

    MasterChangelogTask task
    Project project
    String masterChangelogFullPath

    final String PROJECT_PATH = '/tmp/liquibasePluginTest'
    final String LIQUIBASE_XML = '<databaseChangeLog xmlns="http://www.liquibase.org/xml/ns/dbchangelog" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:schemaLocation="http://www.liquibase.org/xml/ns/dbchangelog http://www.liquibase.org/xml/ns/dbchangelog/dbchangelog-2.0.xsd"></databaseChangeLog>'

    @Before
    void setup() {
        project = ProjectBuilder.builder().withProjectDir(new File(PROJECT_PATH)).build()
        project.apply plugin: 'liquibase'
        task = project.tasks.masterChangelog
        masterChangelogFullPath = "$PROJECT_PATH/src/main/resources/db/changelog/${project.liquibase.masterChangelogName}"
    }

    @After
    void teardown() {
        new File(PROJECT_PATH).deleteDir()
    }

    @Test
    void 'generateMasterChangelog should be the task action'() {
        assert MasterChangelogTask.declaredMethods.find { Method m -> m.name == 'generateMasterChangelog' }.isAnnotationPresent(TaskAction)
    }

    @Test
    void 'generateMaterChangelog should create the master changelog XML file'() {
        task.generateMasterChangelog()


        assert new File(masterChangelogFullPath).exists()
    }

    @Test
    void 'generateMasterChangelog should be idempotent'() {
        task.generateMasterChangelog()

        new File(masterChangelogFullPath).write 'User change'

        task.generateMasterChangelog()

        assert 'User change' == new File(masterChangelogFullPath).text
    }

    @Test
    void 'the generated master changelog file should contain the proper liquibase XML'() {
        task.generateMasterChangelog()

        Diff diff = new Diff(LIQUIBASE_XML, new File(masterChangelogFullPath).text)
        assert diff.similar()
    }
}
