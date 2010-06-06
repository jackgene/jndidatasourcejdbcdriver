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

import org.scalatest.Spec
import org.junit.runner.RunWith
import org.scalatest.junit.{JUnitRunner, MustMatchersForJUnit}

/**
 * JndiDataSourceAdapterDriver specifications.
 * 
 * @author Jack Leow
 * @version 1.0
 * @since 1.0 (June 5, 2010)
 */
@RunWith(classOf[JUnitRunner])
class JndiDataSourceAdapterDriverSpec extends Spec with MustMatchersForJUnit {
  describe ("The JndiDataSourceAdapterDriver") {
    val driver = new JndiDataSourceAdapterDriver
      
    it ("must connect using delegating connection with the given jndiName") {
      val jndiName = "jdbc/testDataSource"
      val conn = driver.connect("jdbc:jndi:" + jndiName, null)
      
      conn.asInstanceOf[JndiDataSourceDelegatingConnection].
        getJndiName() must equal (jndiName)
    }
    
    it ("must not connect when given an unaccepted URL") {
      driver.connect(null, null) must be (null)
    }
    
    it ("must accept URLs starting with \"jdbc:jndi:\"") {
      driver.acceptsURL("jdbc:jndi:jdbc/testDataSource") must be (true)
    }
    
    it ("must not accept null URLs") {
      driver.acceptsURL(null) must be (false)
    }
    
    it ("must not accept any other URLs") {
      driver.acceptsURL("jdbc:jdbc/testDataSource") must be (false)
    }
    
    it ("must not require any driver properties") {
      driver.getPropertyInfo(null, null) must be ('empty)
    }
    
    it ("must have a major version number of 1") {
      driver.getMajorVersion() must equal (1)
    }
    
    it ("must have a minor version number of 0") {
      driver.getMinorVersion() must equal (0)
    }
    
    it ("must not be JDBC compliant (dependent on underlying implementation)") {
      driver.jdbcCompliant must be (false)
    }
  }
}