package org.apache.commons.dbcp2.managed;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
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
import java.util.concurrent.Executor;
import javax.transaction.xa.XAException;
import javax.transaction.xa.XAResource;
import javax.transaction.xa.Xid;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TestLocalXaResource_Purified {

    private static final class TestConnection implements Connection {

        public boolean throwWhenGetAutoCommit;

        public boolean throwWhenSetAutoCommit;

        boolean autoCommit;

        boolean readOnly;

        public boolean committed;

        public boolean rolledback;

        public boolean closed;

        @Override
        public void abort(final Executor executor) throws SQLException {
        }

        @Override
        public void clearWarnings() throws SQLException {
        }

        @Override
        public void close() throws SQLException {
            closed = true;
        }

        @Override
        public void commit() throws SQLException {
            committed = true;
        }

        @Override
        public Array createArrayOf(final String typeName, final Object[] elements) throws SQLException {
            return null;
        }

        @Override
        public Blob createBlob() throws SQLException {
            return null;
        }

        @Override
        public Clob createClob() throws SQLException {
            return null;
        }

        @Override
        public NClob createNClob() throws SQLException {
            return null;
        }

        @Override
        public SQLXML createSQLXML() throws SQLException {
            return null;
        }

        @Override
        public Statement createStatement() throws SQLException {
            return null;
        }

        @Override
        public Statement createStatement(final int resultSetType, final int resultSetConcurrency) throws SQLException {
            return null;
        }

        @Override
        public Statement createStatement(final int resultSetType, final int resultSetConcurrency, final int resultSetHoldability) throws SQLException {
            return null;
        }

        @Override
        public Struct createStruct(final String typeName, final Object[] attributes) throws SQLException {
            return null;
        }

        @Override
        public boolean getAutoCommit() throws SQLException {
            if (throwWhenGetAutoCommit) {
                throw new SQLException();
            }
            return autoCommit;
        }

        @Override
        public String getCatalog() throws SQLException {
            return null;
        }

        @Override
        public Properties getClientInfo() throws SQLException {
            return null;
        }

        @Override
        public String getClientInfo(final String name) throws SQLException {
            return null;
        }

        @Override
        public int getHoldability() throws SQLException {
            return 0;
        }

        @Override
        public DatabaseMetaData getMetaData() throws SQLException {
            return null;
        }

        @Override
        public int getNetworkTimeout() throws SQLException {
            return 0;
        }

        @Override
        public String getSchema() throws SQLException {
            return null;
        }

        @Override
        public int getTransactionIsolation() throws SQLException {
            return 0;
        }

        @Override
        public Map<String, Class<?>> getTypeMap() throws SQLException {
            return null;
        }

        @Override
        public SQLWarning getWarnings() throws SQLException {
            return null;
        }

        @Override
        public boolean isClosed() throws SQLException {
            return closed;
        }

        @Override
        public boolean isReadOnly() throws SQLException {
            return readOnly;
        }

        @Override
        public boolean isValid(final int timeout) throws SQLException {
            return false;
        }

        @Override
        public boolean isWrapperFor(final Class<?> iface) throws SQLException {
            return false;
        }

        @Override
        public String nativeSQL(final String sql) throws SQLException {
            return null;
        }

        @Override
        public CallableStatement prepareCall(final String sql) throws SQLException {
            return null;
        }

        @Override
        public CallableStatement prepareCall(final String sql, final int resultSetType, final int resultSetConcurrency) throws SQLException {
            return null;
        }

        @Override
        public CallableStatement prepareCall(final String sql, final int resultSetType, final int resultSetConcurrency, final int resultSetHoldability) throws SQLException {
            return null;
        }

        @Override
        public PreparedStatement prepareStatement(final String sql) throws SQLException {
            return null;
        }

        @Override
        public PreparedStatement prepareStatement(final String sql, final int autoGeneratedKeys) throws SQLException {
            return null;
        }

        @Override
        public PreparedStatement prepareStatement(final String sql, final int resultSetType, final int resultSetConcurrency) throws SQLException {
            return null;
        }

        @Override
        public PreparedStatement prepareStatement(final String sql, final int resultSetType, final int resultSetConcurrency, final int resultSetHoldability) throws SQLException {
            return null;
        }

        @Override
        public PreparedStatement prepareStatement(final String sql, final int[] columnIndexes) throws SQLException {
            return null;
        }

        @Override
        public PreparedStatement prepareStatement(final String sql, final String[] columnNames) throws SQLException {
            return null;
        }

        @Override
        public void releaseSavepoint(final Savepoint savepoint) throws SQLException {
        }

        @Override
        public void rollback() throws SQLException {
            rolledback = true;
        }

        @Override
        public void rollback(final Savepoint savepoint) throws SQLException {
        }

        @Override
        public void setAutoCommit(final boolean autoCommit) throws SQLException {
            if (throwWhenSetAutoCommit) {
                throw new SQLException();
            }
            this.autoCommit = autoCommit;
        }

        @Override
        public void setCatalog(final String catalog) throws SQLException {
        }

        @Override
        public void setClientInfo(final Properties properties) throws SQLClientInfoException {
        }

        @Override
        public void setClientInfo(final String name, final String value) throws SQLClientInfoException {
        }

        @Override
        public void setHoldability(final int holdability) throws SQLException {
        }

        @Override
        public void setNetworkTimeout(final Executor executor, final int milliseconds) throws SQLException {
        }

        @Override
        public void setReadOnly(final boolean readOnly) throws SQLException {
            this.readOnly = readOnly;
        }

        @Override
        public Savepoint setSavepoint() throws SQLException {
            return null;
        }

        @Override
        public Savepoint setSavepoint(final String name) throws SQLException {
            return null;
        }

        @Override
        public void setSchema(final String schema) throws SQLException {
        }

        @Override
        public void setTransactionIsolation(final int level) throws SQLException {
        }

        @Override
        public void setTypeMap(final Map<String, Class<?>> map) throws SQLException {
        }

        @Override
        public <T> T unwrap(final Class<T> iface) throws SQLException {
            return null;
        }
    }

    private static final class TestXid implements Xid {

        @Override
        public byte[] getBranchQualifier() {
            return null;
        }

        @Override
        public int getFormatId() {
            return 0;
        }

        @Override
        public byte[] getGlobalTransactionId() {
            return null;
        }
    }

    private Connection conn;

    private LocalXAConnectionFactory.LocalXAResource resource;

    @BeforeEach
    public void setUp() {
        conn = new TestConnection();
        resource = new LocalXAConnectionFactory.LocalXAResource(conn);
    }

    @Test
    public void testConstructor_1() {
        assertEquals(0, resource.getTransactionTimeout());
    }

    @Test
    public void testConstructor_2() {
        assertNull(resource.getXid());
    }

    @Test
    public void testConstructor_3() {
        assertFalse(resource.setTransactionTimeout(100));
    }

    @Test
    public void testConstructor_4() {
        assertEquals(0, resource.recover(100).length);
    }

    @Test
    public void testIsSame_1() {
        assertTrue(resource.isSameRM(resource));
    }

    @Test
    public void testIsSame_2() {
        assertFalse(resource.isSameRM(new LocalXAConnectionFactory.LocalXAResource(conn)));
    }
}
