package br.com.app.template.utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.support.ConnectionSource;

/**
 * Created by artur on 05/08/15.
 *
 * OrmLite DBHelper
 *
 */
public class DBHelper extends OrmLiteSqliteOpenHelper {
	
	private static String DB_NAME = "foo.db4";
    private static int DB_VERSION = 1;
	
	public DBHelper(Context context) {
		super(context, DB_NAME, null, DB_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db, ConnectionSource con) {
		try{
//            TableUtils.createTable(con, Foo.class);
		} catch (Exception e) {
			
		}
	}

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {

    }
}
