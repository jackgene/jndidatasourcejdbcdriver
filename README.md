Introduction
============

A [JDBC](http://java.sun.com/products/jdbc/overview.html) Driver that delegates to connections acquired from JNDI data sources.

Most people will not have a need for this driver. However, you may find it useful if all of the following apply to you: 1. You are developing a Java EE web application or EJB 1. You are using a library/framework that uses JDBC API exclusively for database operations (i.e., it's not JNDI-aware) 1. You are unable to make changes to the library/framework 1. You want to avoid hard coding JDBC URLs/connection properties for these libraries

Most libraries/frameworks these days are written with Java EE in mind, as such it is usually possible to configure them to use JNDI data sources.

However, if you're stuck using a legacy library (or for that matter a modern library that isn't meant to be used in a Java EE environment), and JNDI is not supported, this driver allows you to configure your database connection using a JDBC driver class name (`Class.forName(...)`), and JDBC URL (`DriverManager.getConnection(...)`), while delegating all actual operations to a database connection acquired through a JNDI data source. The JDBC URL contains no actual database connection information, just the JNDI name of the data source, making your application portable between deployments.

Getting Started
===============

Assuming you have your data sources set up, all you have to do is add the driver's JAR file to your classpath, and configure your library as follows:

| **Configuration** | **Value** |
|------------------|----------------------------------------------------------------| 
| Driver Class | `my.edu.clhs.jdbc.jndi.JndiDataSourceAdapterDriver` | 
| JDBC URL | `jdbc:jndi:`data dource JNDI name e.g., `jdbc:jndi:java:comp/env/jdbc/exampleDataSource`|

For more concrete examples, please refer to the accompanying sample applications.

Defining the Data Source in JNDI
================================

The actual method of defining data sources in an application server varies from vendor to vendor, as such it will not be covered here. Please refer to the documentation for your particular vendor: * [Adobe JRun](https://www.adobe.com/livedocs/jrun/4/JRun_Administrators_Guide/resources2.htm) * [Apache Tomcat](http://tomcat.apache.org/tomcat-6.0-doc/jndi-datasource-examples-howto.html) * [Glassfish](http://docs.sun.com/app/docs/doc/820-4335/ablky?l=en&a=view) * [IBM WebSphere](http://publib.boulder.ibm.com/infocenter/wasinfo/v7r0/index.jsp?topic=/com.ibm.websphere.express.doc/info/exp/ae/twim_fedmap_datasconf.html) * [Jetty](http://docs.codehaus.org/display/JETTY/JNDI#JNDI-ConfiguringDataSources) * [Oracle WebLogic](http://download-llnw.oracle.com/docs/cd/E13222_01/wls/docs90/jdbc_admin/jdbc_datasources.html)

Best Practices
==============

In general, this driver isn't much different from any other JDBC drivers, and the same best practices apply. However, there are a couple of things to take into consideration with this: 1. **If the legacy library/framework provides its own connection pooling, it should be disabled**. Virtually all Java EE container provides connection pooling with their data source. The legacy library/framework is just pooling connections that are already being pooled by the container. At best, you will not get any performance gains; at worst, you may prematurely exhaust your container's connection pool, leading erratic behaviors. 1. **Use resource-refs for all JNDI resources**. This is a Java EE/JNDI best practice and is discussed at length [here](http://www.ibm.com/developerworks/java/library/j-jndi/).
