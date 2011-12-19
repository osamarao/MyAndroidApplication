package me.osama.TaskManager.tasks;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class TasksSQLLiteOpenHelper extends SQLiteOpenHelper {
	public static final int version = 2;
	public static final String DB_NAME  = "tasks_db.sqlite";
	public static final String TASKS_TABLE  = "tasks";
	public static final String TASK_ID = "id";
	public static final String TASK_NAME = "name";
	public static final String TASK_COMPLETE  = "complete";
	public static final String TASK_DESCRIPTION = "description";
	
	public TasksSQLLiteOpenHelper(Context context) {
		super(context, DB_NAME, null, version);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		createTable(db);
	}

	private void createTable(SQLiteDatabase db) {
		String createTableQuery = "create table " + TASKS_TABLE + " (" +
										TASK_ID + " integer primary key autoincrement not null," +
										TASK_NAME + " text," +
										TASK_COMPLETE + " text" +
										");";
		db.execSQL(createTableQuery);
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		String alterTable = "ALTER TABLE " + TASKS_TABLE + " ADD " + TASK_DESCRIPTION + " text";
		db.execSQL(alterTable);
	}
	
}
