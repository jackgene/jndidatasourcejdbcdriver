/*
 * JndiDataSourceDelegatingConnection.java
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

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.Savepoint;
import java.sql.Statement;
import java.util.Map;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

/**
 * Delegating {@link java.sql.Connection} proxy. This connection delegates
 * method invocations to the connection acquired from the data source in the
 * JNDI directory.
 *
 * Note: Lazy loading is necessary to avoid deadlocks when looking up 
 * data sources connections in hybrid environments (environments where
 * parts of the application uses JNDI connections directly, and other parts use
 * this driver). It is imperative that connection look up is NOT done during
 * object initialization. See {@link JndiDataSourceAdapterDriver} documentation
 * for more details.
 * 
 * @author Jack Leow
 * @version 1.0
 * @since 1.0 (June 8, 2010)
 */
class JndiDataSourceDelegatingConnection implements Connection {
    private String jndiName; // JNDI name of the Data Source
    private Connection delegate; // The physical connection
    
    JndiDataSourceDelegatingConnection(String jndiName) {
        this.jndiName = jndiName;
    }
    
    String getJndiName() {
        return jndiName;
    }
    
    private void handleException(Exception e) throws SQLException {
        if (DriverManager.getLogWriter() != null) {
            e.printStackTrace(DriverManager.getLogWriter());
        }
        
        SQLException sqle = new SQLException(e.getMessage());
        try {
            Method initCause = SQLException.class.getMethod(
                "initCause", new Class[] {Exception.class});
            
            initCause.invoke(sqle, new Object[] {e});
        } catch (NoSuchMethodException e1) {
            // Do nothing.
            // There's no java.lang.Throwable#initCause(Exception) in J2SE 1.3
        } catch (SecurityException e1) {
            throw new RuntimeException(e1.toString());
        } catch (InvocationTargetException e1) {
            throw new RuntimeException(e1.toString());
        } catch (IllegalAccessException e1) {
            throw new RuntimeException(e1.toString());
        }
        
        throw sqle;
    }
    
    /**
     * Acquires the actually physical connection, looking it up from the Data
     * Source in JNDI if necessary.
     * 
     * @return the actual physical connection.
     * @throws java.sql.SQLException if the connection cannot be acquired.
     */
    private Connection getPhysicalConnection() throws SQLException {
        if (delegate == null) {
            try {
                InitialContext ctx = new InitialContext();
                DataSource ds = (DataSource)ctx.lookup(jndiName);
                delegate = ds.getConnection();
            } catch (ClassCastException e) {
                handleException(e);
            } catch (NamingException e) {
                handleException(e);
            }
        }
        
        return delegate;
    }
    
    public Statement createStatement() throws SQLException {
        return getPhysicalConnection().createStatement();
    }
    
    public PreparedStatement prepareStatement(String sql) throws SQLException {
        return getPhysicalConnection().prepareStatement(sql);
    }
    
    public CallableStatement prepareCall(String sql) throws SQLException {
        return getPhysicalConnection().prepareCall(sql);
    }
    
    public String nativeSQL(String sql) throws SQLException {
        return getPhysicalConnection().nativeSQL(sql);
    }
    
    public void setAutoCommit(boolean autoCommit) throws SQLException {
        getPhysicalConnection().setAutoCommit(autoCommit);
    }
    
    public boolean getAutoCommit() throws SQLException {
        return getPhysicalConnection().getAutoCommit();
    }
    
    public void commit() throws SQLException {
        getPhysicalConnection().commit();
    }
    
    public void rollback() throws SQLException {
        getPhysicalConnection().rollback();
    }
    
    public void close() throws SQLException {
        getPhysicalConnection().close();
    }
    
    public boolean isClosed() throws SQLException {
        return getPhysicalConnection().isClosed();
    }
    
    public DatabaseMetaData getMetaData() throws SQLException {
        return getPhysicalConnection().getMetaData();
    }
    
    public void setReadOnly(boolean readOnly) throws SQLException {
        getPhysicalConnection().setReadOnly(readOnly);
    }
    
    public boolean isReadOnly() throws SQLException {
        return getPhysicalConnection().isReadOnly();
    }
    
    public void setCatalog(String catalog) throws SQLException {
        getPhysicalConnection().setCatalog(catalog);
    }
    
    public String getCatalog() throws SQLException {
        return getPhysicalConnection().getCatalog();
    }
    
    public void setTransactionIsolation(int level) throws SQLException {
        getPhysicalConnection().setTransactionIsolation(level);
    }
    
    public int getTransactionIsolation() throws SQLException {
        return getPhysicalConnection().getTransactionIsolation();
    }
    
    public SQLWarning getWarnings() throws SQLException {
        return getPhysicalConnection().getWarnings();
    }
    
    public void clearWarnings() throws SQLException {
        getPhysicalConnection().clearWarnings();
    }
    
    public Statement createStatement(
            int resultSetType, int resultSetConcurrency)
            throws SQLException {
        return getPhysicalConnection().createStatement(
            resultSetType, resultSetConcurrency);
    }
    
    public PreparedStatement prepareStatement(
            String sql, int resultSetType, int resultSetConcurrency)
            throws SQLException {
        return getPhysicalConnection().prepareStatement(
            sql, resultSetType, resultSetConcurrency);
    }
    
    public CallableStatement prepareCall(
            String sql, int resultSetType, int resultSetConcurrency)
            throws SQLException {
        return getPhysicalConnection().prepareCall(
            sql, resultSetType, resultSetConcurrency);
    }
    
    public Map getTypeMap() throws SQLException {
        return getPhysicalConnection().getTypeMap();
    }
    
    public void setTypeMap(Map map) throws SQLException {
        getPhysicalConnection().setTypeMap(map);
    }
    
    public void setHoldability(int holdability) throws SQLException {
        getPhysicalConnection().setHoldability(holdability);
    }
    
    public int getHoldability() throws SQLException {
        return getPhysicalConnection().getHoldability();
    }
    
    public Savepoint setSavepoint() throws SQLException {
        return getPhysicalConnection().setSavepoint();
    }
    
    public Savepoint setSavepoint(String name) throws SQLException {
        return getPhysicalConnection().setSavepoint(name);
    }
    
    public void rollback(Savepoint savepoint) throws SQLException {
        getPhysicalConnection().rollback(savepoint);
    }
    
    public void releaseSavepoint(Savepoint savepoint) throws SQLException {
        getPhysicalConnection().releaseSavepoint(savepoint);
    }
    
    public Statement createStatement(
            int resultSetType, int resultSetConcurrency,
            int resultSetHoldability) throws SQLException {
        return getPhysicalConnection().createStatement(
            resultSetType, resultSetConcurrency, resultSetHoldability);
    }
    
    public PreparedStatement prepareStatement(
            String sql, int resultSetType, int resultSetConcurrency,
            int resultSetHoldability) throws SQLException {
        return getPhysicalConnection().prepareStatement(sql, resultSetType,
            resultSetConcurrency, resultSetHoldability);
    }
    
    public CallableStatement prepareCall(
            String sql, int resultSetType, int resultSetConcurrency,
            int resultSetHoldability) throws SQLException {
        return getPhysicalConnection().prepareCall(
            sql, resultSetType, resultSetConcurrency, resultSetHoldability);
    }
    
    public PreparedStatement prepareStatement(String sql, int autoGeneratedKeys)
            throws SQLException {
        return getPhysicalConnection().prepareStatement(sql, autoGeneratedKeys);
    }
    
    public PreparedStatement prepareStatement(String sql, int[] columnIndexes)
            throws SQLException {
        return getPhysicalConnection().prepareStatement(sql, columnIndexes);
    }
    
    public PreparedStatement prepareStatement(String sql, String[] columnNames)
            throws SQLException {
        return getPhysicalConnection().prepareStatement(sql, columnNames);
    }
}