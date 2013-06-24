#Liquibase Gradle Plugin

Gradle plugin for creating and running [Liquibase](http://www.liquibase.org/) based database migrations.

##Configuring

Currently the jar file is not hosted 

```groovy
buildscript {
    repositories {
    	ivy {
    		url = 'http://dl.bintray.com/jarias/gradle-plugins'
    	}
    }

    dependencies {
        classpath 'jarias.gradle:liquibase-gradle-plugin:0.0.1-SNAPSHOT'
    }
}

apply plugin: 'liquibase'
apply plugin: 'java' //or 'groovy'

repositories {
    mavenCentral()
}

liquibase {
    jdbcUrl = 'jdbc:hsqldb:file:/tmp/testdb'
    jdbcDriverClassName = 'org.hsqldb.jdbc.JDBCDriver'
    jdbcUsername = 'sa'
    jdbcPassword = ''
}

dependencies {
    liquibase 'org.hsqldb:hsqldb:2.2.9'
}
```

**Available Tasks:**

     #Creates the master changelog in src/main/resources/db/changelog
    gradle masterChangelog

	#Creates a new changelog with name pass as the changelog property and modifies the master to include this new changelog
	gradle generateChangelog -Pchangelog=test 
	
	#Migrates the database to the latest changes
	gradle migrateDatabase
	
	
##License

Copyright 2013 Julio Arias

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.