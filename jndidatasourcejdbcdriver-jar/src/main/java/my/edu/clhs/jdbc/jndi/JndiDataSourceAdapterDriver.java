/*
 * JndiDataSourceAdapterDriver.java
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
package my.edu.clhs.jdbc.jndi;

import java.sql.*;
import java.util.Properties;

/**
 * JDBC Driver that abstracts JNDI data source connections for software packages
 * (typically legacy web frameworks, libraries) that are not JNDI-aware to be
 * used in a Java EE web application or EJB.
 *
 * <p>This driver supports URLs of the form:</p>
 * <blockquote>
 *  <p><code>jdbc:jndi:</code><i>JNDI name</i><br /></p>
 *  <p>e.g., <code>jdbc:jndi:java:comp/env/jdbc/exampleDataSource</code></p>
 * </blockquote>
 *
 * <p>Due to the nature of this Driver,
 * {@link java.sql.DriverManager#getConnection(String)}) is invoked before 
 * the JNDI directory look up is done. This will result in
 * <tt>InitialContext#lookup(...)</tt> being executed within 
 * <tt>DriverManager.getConnection(...)</tt> if invoked synchronously, which 
 * could result in deadlocks (in virtually all other data source usage 
 * scenarios, the reverse occurs - <tt>DriverManager.getConnection(...)</tt>
 * executes within <tt>InitialContext#lookup(...)</tt>).</p>
 * 
 * <p>To avoid this, {@link #connect(String,Properties)} returns an unitialized
 * proxy connection, and defers JNDI data source look up to the connection,
 * after DriverManager locks have been released. As a consequence a
 * {@link Connection} may be returned even if the request data source does not
 * exist or is misconfigured, or if the target database is down. An exception
 * is thrown only when the connection is used.</p>
 * 
 * @author Jack Leow
 * @version 1.0
 * @since 1.0 (June 8, 2010)
 */
public class JndiDataSourceAdapterDriver implements Driver {
    private static final String URL_PREFIX = "jdbc:jndi:";
    
    static {
        try {
            DriverManager.registerDriver(new JndiDataSourceAdapterDriver());
        } catch (SQLException e) {
            if (DriverManager.getLogWriter() != null) {
                e.printStackTrace(DriverManager.getLogWriter());
            }
            throw new ExceptionInInitializerError(e);
        }
    }
    
    public Connection connect(String url, Properties info) throws SQLException {
        if (acceptsURL(url)) {
            String jndiName = url.substring(URL_PREFIX.length());
            if (!jndiName.startsWith("java:comp/env/jdbc/")) {
                DriverManager.println(
                    "WARNING: the JNDI name (" + jndiName + ") does not " +
                    "follow Java EE resource reference conventions. " +
                    "(it does not start with java:comp/env/jdbc/...)");
            }
            
            return new JndiDataSourceDelegatingConnection(jndiName);
        } else {
            DriverManager.println(
                JndiDataSourceAdapterDriver.class +
                " does not accept URL: " + url);
            return null;
        }
    }
    
    public boolean acceptsURL(String url) throws SQLException {
        return url != null && url.startsWith(URL_PREFIX);
    }
    
    public DriverPropertyInfo[] getPropertyInfo(String url, Properties info)
            throws SQLException {
        return new DriverPropertyInfo[0];
    }
    
    public int getMajorVersion() {
        return 1;
    }
    
    public int getMinorVersion() {
        return 0;
    }
    
    public boolean jdbcCompliant() {
        return false;
    }
}