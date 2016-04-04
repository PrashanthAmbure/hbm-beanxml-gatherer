# hbm-beanxml-gatherer
maven plugin to gather spring beans and hbm files.

## Table of Contents

* [HibernateGathererMojo](#hibernate-mojo)
* [SpringGathererMojo](#spring-mojo)
* [Usage](#usage)

This project has two MOJO's implemented,

* HibernateGathererMojo.java
* SpringGathererMojo.java

<a id="hibernate-mojo">
### HibernateGathererMojo
</a>

This MOJO when attached to any other mavens build life cycle searches for _.hbm_ or _.hbm.xml_ files within the whole project directory and lists them inside a text file by creating the file _hibernate-hbm-listing.txt_ in mavens src directory location (src/main/java). Sample content of the text file is shown below,

_com/apkinc/core/model/Person.hbm.xml_
_com/apkinc/services/model/Address.hbm.xml_

<a id="spring-mojo">
### SpringGathererMojo
</a>

This MOJO when attached to any other mavens build life cycle searches for file names ending _-spring-context.xml_ within the whole project directory and lists them inside a text file by creating the file _spring-config-listing.txt_ in location configured as part of build life cycle where this maven plugin is being attached. Sample content of the text file is shown below,

_config/core-hibernate-spring-context.xml_

<a id="usage">
## Usage
</a>

To use this maven plugin in any other projects build life cycle(i.e. pom.xml), we will first have to install this projects jar in the local repository. To do this, on the command prompt being at the root of the project directory execute the below command.

_**mvn clean install**_