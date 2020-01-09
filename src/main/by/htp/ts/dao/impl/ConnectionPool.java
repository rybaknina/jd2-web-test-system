package main.by.htp.ts.dao.impl;

import java.sql.Statement;
import java.sql.Array;
import java.sql.Blob;
import java.sql.CallableStatement;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.NClob;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLClientInfoException;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.SQLXML;
import java.sql.Savepoint;
import java.sql.Struct;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executor;
import java.util.logging.Level;
import java.util.logging.Logger;


public final class ConnectionPool {
    private static final Logger LOGGER = Logger.getLogger(ConnectionPool.class.getName());

	private BlockingQueue<Connection> connectionQueue;
	private BlockingQueue<Connection> givenAwayConQueue;
	private String driverName;
	
	private String url;
	private String user;
	private String password;
	private int poolSize;
    private static ConnectionPool pool;
    public static ConnectionPool getConnectionPool(){
        if(pool==null){
            return new ConnectionPool();
        }
        return pool;
    }
    private ConnectionPool() {
        DBResourceManager dbResourceManager = DBResourceManager.getInstance();
        this.driverName = dbResourceManager.getValue(DBParameter.DB_DRIVER);
        this.url = dbResourceManager.getValue(DBParameter.DB_URL);
        this.user = dbResourceManager.getValue(DBParameter.DB_USER);
        this.password = dbResourceManager.getValue(DBParameter.DB_PASSWORD);

        try {
            this.poolSize = Integer.parseInt(dbResourceManager.getValue(DBParameter.DB_POLL_SIZE));
        } catch (NumberFormatException e) {
            poolSize = 5;
        }

    }

    public void initPoolData() throws ConnectionPoolException {
        try{
            Class.forName(driverName);
            givenAwayConQueue = new ArrayBlockingQueue<Connection>(poolSize);
            connectionQueue = new ArrayBlockingQueue<Connection>(poolSize);

            for (int i = 0; i < poolSize; i++) {

                Connection connection = DriverManager.getConnection(url, user, password);

                PooledConnection pooledConnection = new PooledConnection(connection);

                connectionQueue.add(pooledConnection);
            }
        }catch (SQLException e){
            throw new ConnectionPoolException("SQLexception in ConnectionPool",e);
        } catch (ClassNotFoundException e){
            throw new ConnectionPoolException("Cant find database driver class",e);

        }
    }

	public void dispose(){
		clearConnectionQueue();
	}
	private void clearConnectionQueue(){
		try{
			closeConnectionQueue(givenAwayConQueue);
			closeConnectionQueue(connectionQueue);
		}catch (SQLException e){
            LOGGER.log(Level.SEVERE,"Error closing the connection.",e);
		}
	}
	public Connection takeConnection() throws ConnectionPoolException{
		Connection connection=null;
		try{
			connection=connectionQueue.take();
			givenAwayConQueue.add(connection);
		} catch(InterruptedException e){
			throw new ConnectionPoolException("Error connecting to the data source.",e);
		}
		return connection;
	}
	public void closeConnection(Connection con, Statement st, ResultSet rs){
		try{
			con.close();
		}catch (SQLException e){
			e.getMessage();
		}
		try{
			rs.close();
		}catch (SQLException e){
			e.getMessage();
		}
		try{
			((Statement) st).close();
		}catch(SQLException e){
			e.getMessage();
		}
	}
	
	public void closeConnection (Connection con, Statement st){
		try{
			con.close();
		}
		catch(SQLException e){
			e.getMessage();
		}
		try{
			((Statement) st).close();
		}catch(SQLException e){
			e.getStackTrace();
		}
	}
	public void closeConnection (Connection con, PreparedStatement ps){
		try{
			con.close();
		}
		catch(SQLException e){
			e.getMessage();
		}
		try{
			((PreparedStatement) ps).close();
		}catch(SQLException e){
			e.getStackTrace();
		}
	}
	public void closeConnection (Connection con, PreparedStatement ps, ResultSet rs){
		closeConnection(con,ps);
		try {
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	private void closeConnectionQueue(BlockingQueue<Connection> queue) throws SQLException{
		Connection connection;
		while((connection=queue.poll()) !=null){
			if(! connection.getAutoCommit()){
				connection.commit();
			}
			((PooledConnection)connection).reallyClose();
		}
		
	}

	public PreparedStatement createPrepareStatement(String sql) throws SQLException{
		PreparedStatement st = null;
		try {
			PooledConnection pc = new PooledConnection(takeConnection());
			st = pc.prepareStatement(sql);
		} catch (ConnectionPoolException e) {
			e.printStackTrace();
		}
		return st;
		}
	
	// **
	
	
	private class PooledConnection implements Connection{
		private Connection connection;

		public PooledConnection(Connection c)throws SQLException{
			this.connection=c;
			this.connection.setAutoCommit(true);
		}
		public void reallyClose() throws SQLException{
			connection.close();
		}
		@Override
		public void clearWarnings() throws SQLException{
			connection.clearWarnings();
		}
		@Override
		public void close() throws SQLException{
			if(connection.isClosed()){
				throw new SQLException("Attempting to close closed connection");
			}
			if (connection.isReadOnly()){
				connection.setReadOnly(false);
			}
			if(!givenAwayConQueue.remove(this)){
				throw new SQLException("Error deleting connection from given away connections pool");
			}
			if(!connectionQueue.offer(this)){
				throw new SQLException("Error allocating connection in the pool");
			}
		}
		@Override
		public void commit() throws SQLException{
			connection.commit();
		}
		@Override
		public Array createArrayOf(String typeName,Object[] elements) throws SQLException{
			return connection.createArrayOf(typeName, elements);
		}
		@Override
		public Blob createBlob() throws SQLException{
			return connection.createBlob();
		}
		@Override
		public Clob createClob() throws SQLException{
			return connection.createClob();
		}
		@Override
		public NClob createNClob() throws SQLException{
			return connection.createNClob();
		}
		@Override
		public SQLXML createSQLXML() throws SQLException{
			return connection.createSQLXML();
		}
		@Override
		public Statement createStatement() throws SQLException{
			return connection.createStatement();
		}
		@Override
		public Statement createStatement(int resultSetType,int resultSetConcurrency) throws SQLException{
			return connection.createStatement(resultSetType,resultSetConcurrency);
		}
		@Override
		public Statement createStatement(int resultSetType,int resultSetConcurrency, int resultSetHoldability) throws SQLException{
			return connection.createStatement(resultSetType,resultSetConcurrency,resultSetHoldability);
		}
		@Override
		public Struct createStruct(String typeName,Object[] attributes) throws SQLException{
			return connection.createStruct(typeName, attributes);
		}
		@Override
		public boolean getAutoCommit() throws SQLException{
			return connection.getAutoCommit();
		}
		@Override
		public String getCatalog() throws SQLException{
			return connection.getCatalog();
		}
		@Override
		public Properties getClientInfo() throws SQLException{
			return connection.getClientInfo();
		}
		@Override
		public String getClientInfo(String name) throws SQLException{
			return connection.getClientInfo(name);
		}
		@Override
		public int getHoldability() throws SQLException{
			return connection.getHoldability();
		}
		@Override
		public DatabaseMetaData getMetaData() throws SQLException{
			return connection.getMetaData();
		}
		@Override
		public int getTransactionIsolation() throws SQLException{
			return connection.getTransactionIsolation();
		}
		@Override
		public Map<String, Class<?>> getTypeMap() throws SQLException{
			return connection.getTypeMap();
		}
		@Override
		public SQLWarning getWarnings() throws SQLException{
			return connection.getWarnings();
		}
		@Override
		public boolean isClosed() throws SQLException{
			return connection.isClosed();
		}
		@Override
		public boolean isReadOnly() throws SQLException{
			return connection.isReadOnly();
		}
		@Override
		public boolean isValid(int timeout) throws SQLException{
			return connection.isValid(timeout);
		}
		@Override
		public String nativeSQL(String sql) throws SQLException{
			return connection.nativeSQL(sql);
		}
		@Override
		public CallableStatement prepareCall(String sql) throws SQLException{
			return connection.prepareCall(sql);
		}
		@Override
		public CallableStatement prepareCall(String sql, int resultSetType,int resultSetConcurrency) throws SQLException{
			return connection.prepareCall(sql,resultSetType,resultSetConcurrency);
		}
		@Override
		public CallableStatement prepareCall(String sql, int resultSetType,int resultSetConcurrency, int resultSetHoldability) throws SQLException{
			return connection.prepareCall(sql,resultSetType,resultSetConcurrency,resultSetHoldability);
		}
		@Override
		public PreparedStatement prepareStatement(String sql) throws SQLException{
			return connection.prepareStatement(sql);
		}
		@Override
		public PreparedStatement prepareStatement(String sql, int autoGeneratedKeys) throws SQLException{
			return connection.prepareStatement(sql, autoGeneratedKeys);
		}
		@Override
		public PreparedStatement prepareStatement(String sql, int[] columnNames) throws SQLException{
			return connection.prepareStatement(sql, columnNames);
		}
		@Override
		public PreparedStatement prepareStatement(String sql, int resultSetType,int resultSetConcurrency) throws SQLException{
			return connection.prepareStatement(sql,resultSetType,resultSetConcurrency);
		}
		@Override
		public PreparedStatement prepareStatement(String sql, int resultSetType,int resultSetConcurrency, int resultSetHoldability) throws SQLException{
			return connection.prepareStatement(sql,resultSetType,resultSetConcurrency,resultSetHoldability);
		}
		@Override
		public void rollback() throws SQLException{
			connection.rollback();;
		}
		@Override
		public void setAutoCommit(boolean autoCommit) throws SQLException{
			connection.setAutoCommit(autoCommit);;
		}
		@Override
		public void setCatalog(String catalog) throws SQLException{
			connection.setCatalog(catalog);
		}
		@Override
		public void setClientInfo(String name,String value) throws SQLClientInfoException{
			connection.setClientInfo(name, value);
		}
		@Override
		public void setHoldability(int holdability) throws SQLException{
			connection.setHoldability(holdability);
		}
		@Override
		public void setReadOnly(boolean readOnly) throws SQLException{
			connection.setReadOnly(readOnly);;
		}
		@Override
		public Savepoint setSavepoint() throws SQLException{
			return connection.setSavepoint();
		}
		@Override
		public Savepoint setSavepoint(String name) throws SQLException{
			return connection.setSavepoint(name);
		}
		@Override
		public void setTransactionIsolation(int level) throws SQLException{
			connection.setTransactionIsolation(level);
		}
		@Override
		public boolean isWrapperFor(Class<?> iface) throws SQLException{
			return connection.isWrapperFor(iface);
		}
		@Override
		public <T> T unwrap(Class<T> iface) throws SQLException{
			return connection.unwrap(iface);
		}
		@Override
		public void abort(Executor arg0) throws SQLException{
			connection.abort(arg0);;
		}
		@Override
		public int getNetworkTimeout () throws SQLException{
			return connection.getNetworkTimeout();
		}
		@Override
		public String getSchema() throws SQLException{
			return connection.getSchema();
		}
		@Override
		public void releaseSavepoint(Savepoint arg0) throws SQLException{
			connection.releaseSavepoint(arg0);
		}
		@Override
		public void rollback(Savepoint arg0) throws SQLException{
			connection.rollback(arg0);
		}
		@Override
		public void setClientInfo(Properties arg0) throws SQLClientInfoException{
			connection.setClientInfo(arg0);
		}
		@Override
		public void setNetworkTimeout(Executor arg0,int arg1) throws SQLException{
			connection.setNetworkTimeout(arg0, arg1);;
		}
		@Override
		public void setSchema(String arg0) throws SQLException{
			connection.setSchema(arg0);
		}
		@Override
		public void setTypeMap (Map<String, Class<?>> args0) throws SQLException{
			connection.setTypeMap(args0);
		}
		@Override
		public PreparedStatement prepareStatement(String sql, String[] columnNames) throws SQLException {
			return connection.prepareStatement(sql,columnNames);
		}
		

	}


}
	


