package my.edu.clhs.jdbc.jndi

import org.junit.runner.RunWith
import org.scalatest.Spec
import org.scalatest.junit.{MustMatchersForJUnit, JUnitRunner}
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
class JndiDataSourceDelegatingConnectionSpec extends Spec
        with MustMatchersForJUnit {
  val jndiName = "jdbc/testDataSource"
      
  val mockConn = new MockConnection
      
  val mockDs = new MockDataSource
  mockDs.setupConnection(mockConn)
      
  val namingContextBuilder =
    SimpleNamingContextBuilder.emptyActivatedContextBuilder()
  namingContextBuilder.bind(jndiName, mockDs)
    
  describe ("The JndiDataSorceDelegatingConnection") {
    val testInstance = new JndiDataSourceDelegatingConnection(jndiName)
    
    it ("must look up its wrapped connection in JNDI") {
      testInstance.asInstanceOf[Wrapper].
        unwrap(classOf[Connection]) must be theSameInstanceAs mockConn
    }
    
    // This is here for documentation purposes only
    it ("must delegate all method calls to the wrapped connection") (pending)
  }
}