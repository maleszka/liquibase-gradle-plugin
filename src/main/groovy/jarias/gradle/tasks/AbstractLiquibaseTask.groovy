package jarias.gradle.tasks

import org.gradle.api.DefaultTask

/**
 * @author jarias
 * @since 4/5/13 7:46 PM 
 */
class AbstractLiquibaseTask extends DefaultTask {
    /**
     * The base path for the master change log
     */
    protected final String BASE_PATH = 'src/main/resources/db/changelog'
    /**
     * The base liquibase changelog XML content
     */
    protected final String LIQUIBASE_XML = '<?xml version="1.0" encoding="UTF-8"?><databaseChangeLog xmlns="http://www.liquibase.org/xml/ns/dbchangelog" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:schemaLocation="http://www.liquibase.org/xml/ns/dbchangelog http://www.liquibase.org/xml/ns/dbchangelog/dbchangelog-2.0.xsd"></databaseChangeLog>'
}
