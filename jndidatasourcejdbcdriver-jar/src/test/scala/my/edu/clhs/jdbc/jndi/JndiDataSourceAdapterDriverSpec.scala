/*
 * JndiDataSourceAdapterDriverSpec.scala
 *
 * Copyright 2010 Jack Leow
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package my.edu.clhs.jdbc.jndi

import java.io.{PrintWriter, StringWriter}
import java.sql.DriverManager

import org.junit.runner.RunWith
import org.scalatest.junit.{JUnitRunner, MustMatchersForJUnit}
import org.scalatest.{WordSpec}

/**
 * {@link JndiDataSourceAdapterDriver} specifications.
 * 
 * @author Jack Leow
 * @version 1.0
 * @since 1.0 (June 5, 2010)
 */
@RunWith(classOf[JUnitRunner])
class JndiDataSourceAdapterDriverSpec extends WordSpec
    with MustMatchersForJUnit {
  "The JndiDataSourceAdapterDriver" must {
    val driver = new JndiDataSourceAdapterDriver
      
    "connect using a delegating connection with the given jndiName" in {
      val jndiName = "java:comp/env/jdbc/testDataSource"
      val conn = driver.connect("jdbc:jndi:" + jndiName, null)
      
      conn.asInstanceOf[JndiDataSourceDelegatingConnection].
        getJndiName() must equal (jndiName)
    }
    
    "not log any warnings when connecting using a res-ref JNDI name" in {
      val logWriter = new StringWriter
      DriverManager.setLogWriter(new PrintWriter(logWriter))
      
      driver.connect("jdbc:jndi:java:comp/env/jdbc/testDataSource", null)
      
      logWriter.toString must have length (0)
    }
    
    "log a warning when connecting using a non-standard JNDI name" in {
      val logWriter = new StringWriter
      DriverManager.setLogWriter(new PrintWriter(logWriter))
      
      driver.connect("jdbc:jndi:nonStandardDataSource", null)
      
      logWriter.toString must startWith ("WARNING:")
    }
    
    "not connect when given an unaccepted URL" in {
      driver.connect("jdbc:db:foobar", null) must be (null)
    }
    
    "not connect when given a null URL" in {
      driver.connect(null, null) must be (null)
    }
    
    "accept URLs starting with \"jdbc:jndi:\"" in {
      driver.acceptsURL("jdbc:jndi:jdbc/testDataSource") must be (true)
    }
    
    "not accept null URLs" in {
      driver.acceptsURL(null) must be (false)
    }
    
    "not accept URLs not starting with \"jdbc:jndi:\"" in {
      driver.acceptsURL("jdbc:jndijdbc/testDataSource") must be (false)
    }
    
    "not require any driver properties" in {
      driver.getPropertyInfo(null, null) must be ('empty)
    }
    
    "have a major version number of 1" in {
      driver.getMajorVersion() must equal (1)
    }
    
    "have a minor version number of 0" in {
      driver.getMinorVersion() must equal (0)
    }
    
    "not be JDBC compliant - that is up to the wrapped implementation" in {
      driver.jdbcCompliant must be (false)
    }
  }
}