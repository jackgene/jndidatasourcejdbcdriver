/*
 * JndiDataSourceDelegatingConnectionSpec.scala
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

import com.mockrunner.mock.jdbc.{MockDataSource, MockConnection}

import java.sql.SQLException

import org.junit.runner.RunWith
import org.scalatest.junit.{MustMatchersForJUnit, JUnitRunner}
import org.scalatest.{WordSpec}
import org.springframework.mock.jndi.SimpleNamingContextBuilder

/**
 * JndiDataSourceDelegatingConnection specifications.
 * 
 * @author Jack Leow
 * @version 1.0
 * @since 1.0 (June 5, 2010)
 */
@RunWith(classOf[JUnitRunner])
class JndiDataSourceDelegatingConnectionSpec extends WordSpec
        with MustMatchersForJUnit {
  val namingContextBuilder =
    SimpleNamingContextBuilder.emptyActivatedContextBuilder()
  
  "The JndiDataSourceDelegatingConnection" when {
    "given a valid JNDI name" must {
      val jndiName = "java:comp/env/jdbc/testDataSource"
      
      val mockConn = new MockConnection
      
      val mockDs = new MockDataSource
      mockDs.setupConnection(mockConn)
      
      namingContextBuilder.bind(jndiName, mockDs)
      
      val testInstance = new JndiDataSourceDelegatingConnection(jndiName)
      
      "delegate \"createStatement\" calls to the wrapped connection" in {
        val handler = mockConn.getStatementResultSetHandler()
        val testStmt = testInstance.createStatement()
        val expectedStmt = List.fromArray(handler.getStatements().toArray)
        
        List(testStmt) must equal (expectedStmt)
      }
      
      "delegate \"prepareStatement\" calls to the wrapped connection" in {
        val handler = mockConn.getPreparedStatementResultSetHandler()
        val testStmt = testInstance.prepareStatement("")
        val expectedStmt =
          List.fromArray(handler.getPreparedStatements().toArray)
        
        List(testStmt) must equal (expectedStmt)
      }
      
      "delegate \"prepareCall\" calls to the wrapped connection" in {
        val handler = mockConn.getCallableStatementResultSetHandler()
        val testStmt = testInstance.prepareCall("")
        val expectedStmt =
          List.fromArray(handler.getCallableStatements().toArray)
        
        List(testStmt) must equal (expectedStmt)
      }
      
      "delegate \"getAutoCommit\" calls to the wrapped connection" in {
        mockConn.setAutoCommit(false)
        testInstance.getAutoCommit() must be (false)
        
        mockConn.setAutoCommit(true)
        testInstance.getAutoCommit() must be (true)
      }
      
      "delegate \"setAutoCommit\" calls to the wrapped connection" in {
        mockConn.setAutoCommit(false)
        testInstance.setAutoCommit(true)
        mockConn.getAutoCommit() must be (true)
        
        mockConn.setAutoCommit(true)
        testInstance.setAutoCommit(false)
        mockConn.getAutoCommit() must be (false)
      }
      
      "delegate \"commit\" calls to the wrapped connection" in {
        mockConn.resetNumberCommits()
        testInstance.commit()
        mockConn.getNumberCommits() must equal (1)
      }
      
      "delegate \"rollback\" calls to the wrapped connection" in {
        mockConn.resetNumberRollbacks()
        testInstance.rollback()
        mockConn.getNumberRollbacks() must equal (1)
      }
      
      "delegate \"close\" calls to the wrapped connection" in {
        mockConn.isClosed() must be (false)
        
        testInstance.close()
        mockConn.isClosed() must be (true)
      }
    }
    
    "given a JNDI name referencing nothing" must {
      val testInstance = new JndiDataSourceDelegatingConnection(
        "java:comp/env/param/nonExistent")
      
      "complain when any method is invoked on it" in {
        evaluating { testInstance.isClosed() } must produce [SQLException]
      }
    }
    
    "given a JNDI name referencing things other than a DataSource" must {
      val jndiName = "java:comp/env/param/arbitraryObject"
      
      namingContextBuilder.bind(jndiName, new Object)
      
      val testInstance = new JndiDataSourceDelegatingConnection(jndiName)
      
      "complain when any method is invoked on it" in {
        evaluating { testInstance.isClosed() } must produce [SQLException]
      }
    }
  }
}