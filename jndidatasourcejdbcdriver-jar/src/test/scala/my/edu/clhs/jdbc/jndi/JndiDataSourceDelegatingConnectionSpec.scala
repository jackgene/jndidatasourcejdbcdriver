package my.edu.clhs.jdbc.jndi

import java.sql.SQLException
import org.junit.runner.RunWith
import org.scalatest.junit.{MustMatchersForJUnit, JUnitRunner}
import org.scalatest.{WordSpec}
import org.springframework.mock.jndi.SimpleNamingContextBuilder
import com.mockrunner.mock.jdbc.{MockDataSource, MockConnection}

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
  val jndiName = "java:comp/env/jdbc/testDataSource"
  
  val mockConn = new MockConnection
  
  val mockDs = new MockDataSource
  mockDs.setupConnection(mockConn)
  
  val namingContextBuilder =
    SimpleNamingContextBuilder.emptyActivatedContextBuilder()
  namingContextBuilder.bind(jndiName, mockDs)
  
  "The JndiDataSorceDelegatingConnection" when {
    "given a valid JNDI name" must {
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
        val expectedStmt = List.fromArray(handler.getPreparedStatements().toArray)
      
        List(testStmt) must equal (expectedStmt)
      }
    
      "delegate \"prepareCall\" calls to the wrapped connection" in {
        val handler = mockConn.getCallableStatementResultSetHandler()
        val testStmt = testInstance.prepareCall("")
        val expectedStmt = List.fromArray(handler.getCallableStatements().toArray)
      
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
    
    "given an invalid JNDI name" must {
      val testInstance = new JndiDataSourceDelegatingConnection("invalidName")
      
      "complain when any method is invoked on it" in {
        evaluating { testInstance.isClosed() } must produce [SQLException]
      }
    }
  }
}