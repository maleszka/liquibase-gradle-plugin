package jarias.gradle.tasks

import groovy.xml.StreamingMarkupBuilder
import org.gradle.api.tasks.TaskAction
import org.joda.time.DateTime

/**
 * @author jarias
 * @since 4/5/13 7:07 PM 
 */
class GenerateChangelogTask extends AbstractLiquibaseTask {
    @TaskAction
    def generateChangelog() {
        if (!project.hasProperty('changelog')) {
            throw new IllegalArgumentException('Changelog name not set, you must pass -Dchangelog=some-name to the task invocation')
        }

        //It should exist just making sure
        if(configuration.basePath) {
            project.mkdir(configuration.basePath)
        }
        String changelogName = project.property('changelog')
        String path = "db.changelog-${changelogName}.xml"
        if(configuration.basePath) {
          path = "$configuration.basePath/$path"
        }
        File changelog = project.file(path)
        writeXml(generateXml(), changelog)
        updateMasterChangelog(changelogName)
    }

    def generateXml() {
        String xml = new StreamingMarkupBuilder().bind() {
            databaseChangeLog(xmlns: "http://www.liquibase.org/xml/ns/dbchangelog",
                    "xmlns:xsi": "http://www.w3.org/2001/XMLSchema-instance",
                    "xsi:schemaLocation": "http://www.liquibase.org/xml/ns/dbchangelog http://www.liquibase.org/xml/ns/dbchangelog/dbchangelog-2.0.xsd") {
                changeSet(id: "${DateTime.now().millis}-1", author: System.properties.getProperty("user.name"))
            }
        }
        xml
    }

    def updateMasterChangelog(String changelogName) {
        def path = project.liquibase.masterChangelogName
        if(configuration.basePath) {
          path = "$configuration.basePath/$path"
        }
        File masterChangelog = project.file(path)
        def root = new XmlSlurper(false, false).parse(masterChangelog)

        root.appendNode {
            include(file: "db.changelog-${changelogName}.xml", relativeToChangelogFile: "true")
        }

        String xml = (new StreamingMarkupBuilder()).bind { mkp.yield root }
        writeXml(xml, masterChangelog)
    }
}
