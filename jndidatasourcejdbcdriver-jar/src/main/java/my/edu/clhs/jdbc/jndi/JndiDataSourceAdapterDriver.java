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

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverPropertyInfo;
import java.sql.SQLException;
import java.util.Properties;

/**
 * JDBC Driver that abstracts JNDI data source connections for software packages
 * (typically legacy web frameworks, libraries) that are not JNDI-aware to be
 * used in a web application.
 *
 * This driver supports URLs of the form:
 * <code>
 *  jdbc:jndi:[resource-ref JNDI name]
 *  e.g., jdbc:jndi:java:comp/env/jdbc/myDataSource
 * </code>
 *
 * @author Jack Leow
 * @version 1.0
 * @since 1.0 (June 5, 2010)
 */
public class JndiDataSourceAdapterDriver implements Driver {
    public Connection connect(String url, Properties info) throws SQLException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public boolean acceptsURL(String url) throws SQLException {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public DriverPropertyInfo[] getPropertyInfo(String url, Properties info)
            throws SQLException {
        return new DriverPropertyInfo[0];  //To change body of implemented methods use File | Settings | File Templates.
    }

    public int getMajorVersion() {
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public int getMinorVersion() {
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public boolean jdbcCompliant() {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
