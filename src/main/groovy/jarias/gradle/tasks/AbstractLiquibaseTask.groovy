package jarias.gradle.tasks

import jarias.gradle.LiquibasePluginExtension
import org.gradle.api.DefaultTask

import javax.xml.transform.OutputKeys
import javax.xml.transform.Transformer
import javax.xml.transform.TransformerFactory
import javax.xml.transform.stream.StreamResult
import javax.xml.transform.stream.StreamSource

/**
 * @author jarias
 * @since 4/5/13 7:46 PM 
 */
abstract class AbstractLiquibaseTask extends DefaultTask {
    /**
     * The relative path of all changelog files, use to update the master changelog with the include tags
     */
    protected final String RELATIVE_PATH = 'db/changelog'
    /**
     * The base path for the master change log
     */
    protected final String BASE_PATH = "src/main/resources/$RELATIVE_PATH"
    /**
     * The base liquibase changelog XML content
     */
    protected final String LIQUIBASE_XML = '<?xml version="1.0" encoding="UTF-8"?><databaseChangeLog xmlns="http://www.liquibase.org/xml/ns/dbchangelog" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:schemaLocation="http://www.liquibase.org/xml/ns/dbchangelog http://www.liquibase.org/xml/ns/dbchangelog/dbchangelog-2.0.xsd"></databaseChangeLog>'

    /**
     * Returns the {@link LiquibasePluginExtension} of the project
     *
     * @return The LiquibasePluginExtension
     */
    LiquibasePluginExtension getConfiguration() {
        project.extensions.findByType(LiquibasePluginExtension)
    }

    /**
     * Writes and XML string with proper XML declaration and indentation using javax.xml.transform.Transformer
     *
     * @param xml The XML string
     * @param file A file to write the formatted XML
     */
    def writeXml(String xml, File file) {
        TransformerFactory factory = TransformerFactory.newInstance()
        factory.setAttribute("indent-number", 4)

        Transformer transformer = factory.newTransformer()
        transformer.setOutputProperty(OutputKeys.METHOD, "xml")
        transformer.setOutputProperty(OutputKeys.INDENT, "yes")
        transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8")
        transformer.setOutputProperty(OutputKeys.STANDALONE, "no")

        StreamResult result = new StreamResult(new OutputStreamWriter(new FileOutputStream(file), "UTF-8"))
        transformer.transform(new StreamSource(new ByteArrayInputStream(xml.bytes)), result)
    }
}
