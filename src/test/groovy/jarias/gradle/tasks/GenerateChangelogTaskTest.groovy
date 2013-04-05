package jarias.gradle.tasks

import org.custommonkey.xmlunit.Diff
import org.custommonkey.xmlunit.XMLUnit
import org.gradle.api.Project
import org.gradle.api.tasks.TaskAction
import org.gradle.testfixtures.ProjectBuilder
import org.joda.time.DateTime
import org.joda.time.DateTimeUtils
import org.junit.After
import org.junit.Before
import org.junit.Test

import java.lang.reflect.Method

/**
 * @author jarias
 * @since 4/5/13 7:18 PM 
 */
class GenerateChangelogTaskTest {
    GenerateChangelogTask task
    Project project
    String changelogFullPath
    String changelogXML

    final String PROJECT_PATH = '/tmp/liquibasePluginTest'
    final String CHANGELOG_NAME = 'test.changelog'

    @Before
    void setup() {
        project = ProjectBuilder.builder().withProjectDir(new File(PROJECT_PATH)).build()
        project.apply plugin: 'liquibase'
        task = project.tasks.generateChangelog
        changelogFullPath = "$PROJECT_PATH/src/main/resources/db/changelog/$CHANGELOG_NAME"

        XMLUnit.ignoreWhitespace = true

        DateTimeUtils.currentMillisFixed = 1

        changelogXML = """
<databaseChangeLog xmlns="http://www.liquibase.org/xml/ns/dbchangelog" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:schemaLocation="http://www.liquibase.org/xml/ns/dbchangelog http://www.liquibase.org/xml/ns/dbchangelog/dbchangelog-2.0.xsd">
    <changeSet author="${System.properties.getProperty("user.name")}" id="${DateTime.now().millis}-1">
    </changeSet>
</databaseChangeLog>
"""
    }

    @After
    void teardown() {
        new File(PROJECT_PATH).deleteDir()
    }

    @Test
    void 'generateChangelog should be the task action'() {
        assert GenerateChangelogTask.declaredMethods.find { Method m -> m.name == 'generateChangelog' }.isAnnotationPresent(TaskAction)
    }

    @Test(expected = IllegalArgumentException)
    void 'generateChangelog should raise IllegalArgumentException if no name is pass as a system property'() {
        task.generateChangelog()
    }

    @Test
    void 'generateChangelog should create a new changelog file with the given name pass as a system property'() {
        project.extensions.add('changelog', CHANGELOG_NAME)
        task.generateChangelog()

        assert new File(changelogFullPath).exists()
    }

    @Test
    void 'the generated changelog file should contain the proper liquibase XML'() {
        project.extensions.add('changelog', CHANGELOG_NAME)
        task.generateChangelog()
        Diff diff = new Diff(changelogXML, new File(changelogFullPath).text)
        assert diff.similar()
    }
}
