package jarias.gradle.tasks

import groovy.sql.Sql
import org.gradle.api.Project
import org.gradle.api.tasks.TaskAction
import org.gradle.testfixtures.ProjectBuilder
import org.junit.After
import org.junit.Before
import org.junit.Test

import java.lang.reflect.Method

/**
 * @author jarias
 * @since 5/24/13 5:58 PM 
 */
class MigrateDatabaseTaskTest {

    Project project
    MigrateDatabaseTask task
    def sql

    final String JDBC_URL = 'jdbc:hsqldb:file:/tmp/testdb'
    final String DRIVER_CLASS_NAME = 'org.hsqldb.jdbc.JDBCDriver'
    final String PROJECT_PATH = '/tmp/liquibasePluginTest'
    final String SAMPLE_CHANGELOG = '''
<databaseChangeLog xmlns="http://www.liquibase.org/xml/ns/dbchangelog" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
                   xsi:schemaLocation="http://www.liquibase.org/xml/ns/dbchangelog http://www.liquibase.org/xml/ns/dbchangelog/dbchangelog-2.0.xsd">
    <changeSet id="1" author="test">
        <createTable tableName="test">
            <column name="id" type="INTEGER"/>
        </createTable>
    </changeSet>
</databaseChangeLog>
'''

    @Before
    void setup() {
        project = ProjectBuilder.builder().withProjectDir(new File(PROJECT_PATH)).build()
        project.apply plugin: 'liquibase'
        project.liquibase {
            jdbcUrl = JDBC_URL
            jdbcDriverClassName = DRIVER_CLASS_NAME
            jdbcUsername = 'sa'
            jdbcPassword = ''
        }
        task = project.tasks.migrateDatabase

        //Create sample master changelog for integration testing
        project.mkdir('src/main/resources/db/changelog')
        new File("$PROJECT_PATH/src/main/resources/db/changelog/${project.liquibase.masterChangelogName}").write(SAMPLE_CHANGELOG)
    }

    @After
    void teardown() {
        new File(PROJECT_PATH).deleteDir()
        new File('/tmp/testdb').delete()
    }

    @Test
    void 'migrateDatabase should be the task action'() {
        assert MigrateDatabaseTask.declaredMethods.find { Method m -> m.name == 'migrateDatabase' }.isAnnotationPresent(TaskAction)
    }

    @Test
    void 'migrateDatabase should migrate the database'() {
        task.migrateDatabase()

        sql = Sql.newInstance(JDBC_URL, 'sa', '', DRIVER_CLASS_NAME)

        sql.execute("INSERT INTO TEST (id) values (1)")

        assert 1 == sql.firstRow('SELECT id FROM test').id
    }
}
