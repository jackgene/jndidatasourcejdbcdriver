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

import java.sql.Array;
import java.sql.Blob;
import java.sql.CallableStatement;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.NClob;
import java.sql.PreparedStatement;
import java.sql.SQLClientInfoException;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.SQLXML;
import java.sql.Savepoint;
import java.sql.Statement;
import java.sql.Struct;
import java.util.Map;
import java.util.Properties;

/**
 * Delegating {@link java.sql.Connection} proxy. This connection delegates
 * method invocations to the connection acquired from the data source in the
 * JNDI directory.
 *
 * @author Jack Leow
 * @version 1.0
 * @since 1.0 (June 5, 2010)
 */
class JndiDataSourceDelegatingConnection implements Connection {
    JndiDataSourceDelegatingConnection(String jndiName) {
        
    }
    
    String getJndiName() {
        return null;
    }
    
    public Statement createStatement() throws SQLException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public PreparedStatement prepareStatement(String sql) throws SQLException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public CallableStatement prepareCall(String sql) throws SQLException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public String nativeSQL(String sql) throws SQLException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public void setAutoCommit(boolean autoCommit) throws SQLException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public boolean getAutoCommit() throws SQLException {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public void commit() throws SQLException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void rollback() throws SQLException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void close() throws SQLException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public boolean isClosed() throws SQLException {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public DatabaseMetaData getMetaData() throws SQLException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public void setReadOnly(boolean readOnly) throws SQLException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public boolean isReadOnly() throws SQLException {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public void setCatalog(String catalog) throws SQLException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public String getCatalog() throws SQLException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public void setTransactionIsolation(int level) throws SQLException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public int getTransactionIsolation() throws SQLException {
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public SQLWarning getWarnings() throws SQLException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public void clearWarnings() throws SQLException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public Statement createStatement(
            int resultSetType, int resultSetConcurrency) throws SQLException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public PreparedStatement prepareStatement(
            String sql, int resultSetType, int resultSetConcurrency)
            throws SQLException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public CallableStatement prepareCall(
            String sql, int resultSetType, int resultSetConcurrency)
            throws SQLException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public Map getTypeMap() throws SQLException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public void setTypeMap(Map map) throws SQLException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void setHoldability(int holdability) throws SQLException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public int getHoldability() throws SQLException {
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public Savepoint setSavepoint() throws SQLException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public Savepoint setSavepoint(String name) throws SQLException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public void rollback(Savepoint savepoint) throws SQLException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void releaseSavepoint(Savepoint savepoint) throws SQLException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public Statement createStatement(
            int resultSetType, int resultSetConcurrency,
            int resultSetHoldability) throws SQLException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public PreparedStatement prepareStatement(
            String sql, int resultSetType, int resultSetConcurrency,
            int resultSetHoldability) throws SQLException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public CallableStatement prepareCall(
            String sql, int resultSetType, int resultSetConcurrency,
            int resultSetHoldability) throws SQLException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public PreparedStatement prepareStatement(String sql, int autoGeneratedKeys)
            throws SQLException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public PreparedStatement prepareStatement(String sql, int[] columnIndexes)
            throws SQLException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public PreparedStatement prepareStatement(String sql, String[] columnNames)
            throws SQLException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public Clob createClob() throws SQLException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public Blob createBlob() throws SQLException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public NClob createNClob() throws SQLException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public SQLXML createSQLXML() throws SQLException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public boolean isValid(int timeout) throws SQLException {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public void setClientInfo(String name, String value)
            throws SQLClientInfoException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void setClientInfo(Properties properties)
            throws SQLClientInfoException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public String getClientInfo(String name) throws SQLException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public Properties getClientInfo() throws SQLException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public Array createArrayOf(String typeName, Object[] elements)
            throws SQLException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public Struct createStruct(String typeName, Object[] attributes)
            throws SQLException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public Object unwrap(Class iface) throws SQLException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public boolean isWrapperFor(Class iface) throws SQLException {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }
}