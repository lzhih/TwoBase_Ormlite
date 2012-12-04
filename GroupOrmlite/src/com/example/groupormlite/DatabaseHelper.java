package com.example.groupormlite;
import java.sql.SQLException;
import java.util.concurrent.atomic.AtomicInteger;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

public class DatabaseHelper extends OrmLiteSqliteOpenHelper {
	    private final static String DATABASE_NAME="ormlite.db";
	    private final static int DATABASE_VERSION=3;
		private static DatabaseHelper helper = null;
	    public Dao<SimpleData, Integer> simpleDao;
		private static final AtomicInteger usageCounter = new AtomicInteger(0);
	    
	    public DatabaseHelper(Context context)
	    {
	        super(context, DATABASE_NAME,null, DATABASE_VERSION);
	    }

	    public static synchronized DatabaseHelper getHelper(Context context) {
			if (helper == null) {
				helper = new DatabaseHelper(context);
			}
			usageCounter.incrementAndGet();
			return helper;
		}
	    
	    
		@Override
		public void onCreate(SQLiteDatabase arg0, ConnectionSource arg1)
		{
			try
			{
				Log.i("xxxxxxxxxxx", "onCreate");
				TableUtils.createTable(arg1, SimpleData.class);
				
			} catch (SQLException e)
			{
				e.printStackTrace();
			}
			
		}



		public Dao<SimpleData, Integer> getSimpleDataDao() throws SQLException
		{
			if(simpleDao == null)
			{
				simpleDao = getDao(SimpleData.class);
			}
			
			return simpleDao;
		}

		@Override
		public void onUpgrade(SQLiteDatabase arg0, ConnectionSource arg1,
				int arg2, int arg3)
		{
			try {
				
				TableUtils.dropTable(connectionSource, SimpleData.class, true);
				// after we drop the old databases, we create the new ones
				onCreate(arg0, connectionSource);
			} catch (SQLException e) {
				throw new RuntimeException(e);
			}
		}	 
		
		public void close() {
			if (usageCounter.decrementAndGet() == 0) {
				super.close();
				simpleDao = null;
				helper = null;
			}
		}
}
