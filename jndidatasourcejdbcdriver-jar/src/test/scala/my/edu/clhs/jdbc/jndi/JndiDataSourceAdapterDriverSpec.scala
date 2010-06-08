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

import org.junit.runner.RunWith
import org.scalatest.junit.{JUnitRunner, MustMatchersForJUnit}
import org.scalatest.{WordSpec}

/**
 * JndiDataSourceAdapterDriver specifications.
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
      val jndiName = "jdbc/testDataSource"
      val conn = driver.connect("jdbc:jndi:" + jndiName, null)
      
      conn.asInstanceOf[JndiDataSourceDelegatingConnection].
        getJndiName() must equal (jndiName)
    }
    
    "not connect when given an unaccepted URL" in {
      driver.connect(null, null) must be (null)
    }
    
    "accept URLs starting with \"jdbc:jndi:\"" in {
      driver.acceptsURL("jdbc:jndi:jdbc/testDataSource") must be (true)
    }
    
    "not accept null URLs" in {
      driver.acceptsURL(null) must be (false)
    }
    
    "not accept any other URLs" in {
      driver.acceptsURL("jdbc:jdbc/testDataSource") must be (false)
    }
    
    "list \"java.naming.factory.initial\" as an optional driver property" in {
      driver.getPropertyInfo(null, null) must be ('empty)
    }
    
    "have a major version number of 1" in {
      driver.getMajorVersion() must equal (1)
    }
    
    "have a minor version number of 0" in {
      driver.getMinorVersion() must equal (0)
    }
    
    "not be JDBC compliant - compliance is up to wrapped implementation" in {
      driver.jdbcCompliant must be (false)
    }
  }
}