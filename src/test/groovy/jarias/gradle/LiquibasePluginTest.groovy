package jarias.gradle

import org.gradle.api.Project
import org.gradle.testfixtures.ProjectBuilder
import org.junit.Before
import org.junit.Test

/**
 * @author jarias
 * @since 4/3/13 10:39 PM 
 */
class LiquibasePluginTest {
    //** Fixtures **
    final String JDBC_DRIVER_CLASS_NAME = 'some.class.name'
    final String JDBC_URL = 'jdbc:engine://localhost/db'
    final String JDBC_USERNAME = 'username'
    final String JDBC_PASSWORD = 'password'
    final String MASTER_CHANGELOG_NAME = 'master.xml'
    //** End Fixtures **

    Project project

    @Before
    void setup() {
        project = ProjectBuilder.builder().build()
        project.apply plugin: 'liquibase'
    }

    @Test
    void 'liquibase plugin should create a liquibase extension'() {
        assert project.extensions.findByName(LiquibasePlugin.LIQUIBASE)
    }

    @Test
    void 'liquibase plugin should create a liquibase configuration'() {
        assert project.configurations.findByName(LiquibasePlugin.LIQUIBASE)
    }

    @Test
    void 'liquibase plugin extension should set a default master changelog filename is none is configured'() {
        assert LiquibasePluginExtension.DEFAULT_MASTER_CHANGELOG_NAME == project.extensions.findByType(LiquibasePluginExtension).masterChangelogName
    }

    @Test
    void 'liquibase plugin extension should allow to set master changelog filename'() {
        project.liquibase {
            masterChangelogName = MASTER_CHANGELOG_NAME
        }

        assert MASTER_CHANGELOG_NAME == project.extensions.findByType(LiquibasePluginExtension).masterChangelogName
    }

    @Test
    void 'liquibase plugin extension should allow to set JDBC driver class name'() {
        project.liquibase {
            jdbcDriverClassName = JDBC_DRIVER_CLASS_NAME
        }

        assert JDBC_DRIVER_CLASS_NAME == project.extensions.findByType(LiquibasePluginExtension).jdbcDriverClassName
    }

    @Test
    void 'liquibase plugin extension should allow to set JDBC URL'() {
        project.liquibase {
            jdbcUrl = JDBC_URL
        }

        assert JDBC_URL == project.extensions.findByType(LiquibasePluginExtension).jdbcUrl
    }

    @Test
    void 'liquibase plugin extension should allow to set JDBC username'() {
        project.liquibase {
            jdbcUsername = JDBC_USERNAME
        }

        assert JDBC_USERNAME == project.extensions.findByType(LiquibasePluginExtension).jdbcUsername
    }

    @Test
    void 'liquibase plugin extension should allow to set JDBC password'() {
        project.liquibase {
            jdbcPassword = JDBC_PASSWORD
        }

        assert JDBC_PASSWORD == project.extensions.findByType(LiquibasePluginExtension).jdbcPassword
    }
}
