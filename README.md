#Liquibase Gradle Plugin

[![Build Status](https://drone.io/github.com/jarias/liquibase-gradle-plugin/status.png)](https://drone.io/github.com/jarias/liquibase-gradle-plugin/latest)

Gradle plugin for creating and running [Liquibase](http://www.liquibase.org/) based database migrations.

##Configuring

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
    //Your database properties
    jdbcUrl = 'jdbc:hsqldb:file:/tmp/testdb'
    jdbcDriverClassName = 'org.hsqldb.jdbc.JDBCDriver'
    jdbcUsername = 'sa'
    jdbcPassword = ''
}

dependencies {
    liquibase 'org.hsqldb:hsqldb:2.2.9' //Or whatever JDBC driver you need
}
```

##Tasks
     
* `masterChangelog`: Creates the master changelog in `src/main/resources/db/changelog`.
* `generateChangelog`: Creates a new changelog the name is set via the changelog property `-Pchangelog=test` the task also includes the new changelog in master changelog.
* `migrateDatabase`: Migrates the database to the latest changes.	
	
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
