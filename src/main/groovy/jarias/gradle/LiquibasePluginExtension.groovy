package jarias.gradle

/**
 * @author jarias
 * @since 4/3/13 10:34 PM 
 */
class LiquibasePluginExtension {
    static final String DEFAULT_MASTER_CHANGELOG_NAME = 'db.changelog-master.xml'
    /**
     * Name of the master changelog
     */
    String masterChangelogName = DEFAULT_MASTER_CHANGELOG_NAME
    /**
     * JDBC driver class name
     */
    String jdbcDriverClassName
    /**
     * JDBC database url
     */
    String jdbcUrl
    /**
     * JDBC database username
     */
    String jdbcUsername
    /**
     * JDBC database password
     */
    String jdbcPassword

    /**
     * The base path for the changelog and sql files
     */
    String basePath
}
