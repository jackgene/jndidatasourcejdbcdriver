Introduction
============

This project includes two sample applications that demonstrates how the JNDI-JDBC driver can be used. One is based on the introductory Hibernate tutorial, the second is based on the Spring MVC Step-by-step tutorial.

In both cases, the general steps to integrate the JNDI-JDBC driver were the same: 1. Define data source in application server 1. Define resource-refs in `web.xml` (optional by recommended) 1. Replace original application JDBC driver with `jndijdbcdriver.jar` 1. Update the application database connection settings (JDBC driver and URL) 1. Disable in-application connection pooling

Note that these examples were chosen because they are relatively well known tutorials that make use of a database. If you are developing a real Hibernate or Spring MVC application, you **should not** be using this driver as both Hibernate and Spring have very good native support for Data Sources and JNDI.

Hibernate Tutorial based
------------------------

This sample application uses [the introductory Hibernate Tutorial](http://docs.jboss.org/hibernate/stable/core/reference/en/html_single/#tutorial) as a starting point. The original application is configured using a direct JDBC connection, as shown [on lines 11-16 in the hibernate.cfg.xml](http://code.google.com/p/jndidatasourcejdbcdriver/source/browse/hibernate-tutorial-war/src/main/resources/hibernate.cfg.xml?spec=svn5935cfcc497209da18374d14a54ae7c16d80e1f1&r=5935cfcc497209da18374d14a54ae7c16d80e1f1#10).

The following steps were applied to modify the base application to use the JNDI-JDBC driver: 1. The data source is defined on the application by adding the HSQL JDBC driver to the application server's classpath. 1. The `<resource-ref>` element was added to the `web.xml` file ([changeset lines 34-38](http://code.google.com/p/jndidatasourcejdbcdriver/source/diff?spec=svnfca6dbb2e8a128357264df3fc45876ace294ce01&r=fca6dbb2e8a128357264df3fc45876ace294ce01&format=side&path=/hibernate-tutorial-war/src/main/webapp/WEB-INF/web.xml)) 1. The database driver is replaced ([changeset](http://code.google.com/p/jndidatasourcejdbcdriver/source/diff?spec=svnfca6dbb2e8a128357264df3fc45876ace294ce01&r=fca6dbb2e8a128357264df3fc45876ace294ce01&format=side&path=/hibernate-tutorial-war/pom.xml)) 1. The Hibernate connection settings were updated ([changeset](http://code.google.com/p/jndidatasourcejdbcdriver/source/diff?spec=svnfca6dbb2e8a128357264df3fc45876ace294ce01&r=fca6dbb2e8a128357264df3fc45876ace294ce01&format=side&path=/hibernate-tutorial-war/src/main/resources/hibernate.cfg.xml)) 1. Disable in-application connection pooling ([changeset](http://code.google.com/p/jndidatasourcejdbcdriver/source/diff?spec=svn197a87465ffaba2e0a10969de2a33a43aa56e6c4&r=197a87465ffaba2e0a10969de2a33a43aa56e6c4&format=side&path=/hibernate-tutorial-war/src/main/resources/hibernate.cfg.xml))

This sample application is included with the project source, and can be browsed [here](http://code.google.com/p/jndidatasourcejdbcdriver/source/browse/hibernate-tutorial-war/).

Spring MVC Step-by-step Tutorial based
--------------------------------------

This is the same thing as the Hibernate Tutorial based sample, but uses the [Spring MVC Step-by-step Tutorial](http://static.springsource.org/docs/Spring-MVC-step-by-step/) as its starting point. The original tutorial also configured using a direct JDBC connection, link to file.

1.  Define data source in application server ([changeset](http://code.google.com/p/jndidatasourcejdbcdriver/source/diff?spec=svn839a3ac42f81ca7552275ecb1da7f56dd8515bbd&r=839a3ac42f81ca7552275ecb1da7f56dd8515bbd&format=side&path=/springmvc-stepbystep-war/pom.xml), lines 111-135), Tomcat ([changeset](http://code.google.com/p/jndidatasourcejdbcdriver/source/diff?spec=svn839a3ac42f81ca7552275ecb1da7f56dd8515bbd&r=839a3ac42f81ca7552275ecb1da7f56dd8515bbd&format=side&path=/springmvc-stepbystep-war/src/main/webapp/META-INF/context.xml)), Jetty ([changeset](http://code.google.com/p/jndidatasourcejdbcdriver/source/diff?spec=svn839a3ac42f81ca7552275ecb1da7f56dd8515bbd&r=839a3ac42f81ca7552275ecb1da7f56dd8515bbd&format=side&path=/springmvc-stepbystep-war/src/main/webapp/WEB-INF/jetty-env.xml))
2.  Define resource-refs in `web.xml` (optional by recommended) ([changeset](http://code.google.com/p/jndidatasourcejdbcdriver/source/diff?spec=svn839a3ac42f81ca7552275ecb1da7f56dd8515bbd&r=839a3ac42f81ca7552275ecb1da7f56dd8515bbd&format=side&path=/springmvc-stepbystep-war/src/main/webapp/WEB-INF/web.xml), lines 35-41)
3.  Replace original application JDBC driver with `jndijdbcdriver.jar` ([changeset](http://code.google.com/p/jndidatasourcejdbcdriver/source/diff?spec=svn839a3ac42f81ca7552275ecb1da7f56dd8515bbd&r=839a3ac42f81ca7552275ecb1da7f56dd8515bbd&format=side&path=/springmvc-stepbystep-war/pom.xml), lines 42-55)
4.  Update the application database connection settings (JDBC driver and URL) ([changeset](http://code.google.com/p/jndidatasourcejdbcdriver/source/diff?spec=svn839a3ac42f81ca7552275ecb1da7f56dd8515bbd&r=839a3ac42f81ca7552275ecb1da7f56dd8515bbd&format=side&path=/springmvc-stepbystep-war/src/main/resources/jdbc.properties))
5.  Disable in-application connection pooling ([changeset](http://code.google.com/p/jndidatasourcejdbcdriver/source/diff?spec=svn9d22ec39084a6434b0c432cf7e7b93ba4b668806&r=9d22ec39084a6434b0c432cf7e7b93ba4b668806&format=side&path=/springmvc-stepbystep-war/src/main/webapp/WEB-INF/applicationContext.xml))

This sample application is included with the project source, and can be browsed [here](http://code.google.com/p/jndidatasourcejdbcdriver/source/browse/springmvc-stepbystep-war/).
