package my.edu.clhs.jdbc.jndi

import org.junit.runner.RunWith
import org.scalatest.junit.{MustMatchersForJUnit, JUnitRunner}
import org.scalatest.{WordSpec}
import org.springframework.mock.jndi.SimpleNamingContextBuilder
import com.mockrunner.mock.jdbc.{MockDataSource, MockConnection}
import java.sql.{Wrapper, Connection}

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
  
  "The JndiDataSorceDelegatingConnection" must {
    val testInstance = new JndiDataSourceDelegatingConnection(jndiName)
    
    "look up its wrapped connection in JNDI" in {
      testInstance.asInstanceOf[Wrapper].
        unwrap(classOf[Connection]) must be theSameInstanceAs mockConn
    }
    
    "be a wrapper for java.sql.Connection" in {
      testInstance.asInstanceOf[Wrapper].
        isWrapperFor(classOf[Connection]) must be (true)
    }
    
    "be a wrapper for java.sql.Connection implementations" in {
      testInstance.asInstanceOf[Wrapper].
        isWrapperFor(classOf[MockConnection]) must be (true)
    }
    
    "not be a wrapper for other classes" in {
      testInstance.asInstanceOf[Wrapper].
        isWrapperFor(classOf[Object]) must be (false)
    }
    
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
    
    "delegate \"close\" calls to the wrapped connection" in {
      testInstance.close()
      
      mockConn.isClosed() must be (true)
    }
  }
}