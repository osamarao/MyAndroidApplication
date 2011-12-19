package me.osama.TaskManager;

import static me.osama.TaskManager.tasks.TasksSQLLiteOpenHelper.TASKS_TABLE;
import static me.osama.TaskManager.tasks.TasksSQLLiteOpenHelper.TASK_COMPLETE;
import static me.osama.TaskManager.tasks.TasksSQLLiteOpenHelper.TASK_DESCRIPTION;
import static me.osama.TaskManager.tasks.TasksSQLLiteOpenHelper.TASK_ID;
import static me.osama.TaskManager.tasks.TasksSQLLiteOpenHelper.TASK_NAME;

import java.util.ArrayList;

import me.osama.TaskManager.tasks.Task;
import me.osama.TaskManager.tasks.TasksSQLLiteOpenHelper;
import android.app.Application;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class TaskManagerApplication extends Application {

	private ArrayList<Task> currentTasks;
	private SQLiteDatabase database;

	@Override
	public void onCreate() {
		super.onCreate();
		TasksSQLLiteOpenHelper helper = new TasksSQLLiteOpenHelper(this);
		database = helper.getWritableDatabase();

		if (null == currentTasks) {
			loadTasks();
		}
	}

	private void loadTasks() {
		currentTasks = new ArrayList<Task>();
		Cursor tasksCursor = database.query(TASKS_TABLE, new String[] {	TASK_ID, TASK_NAME, TASK_DESCRIPTION, TASK_COMPLETE }, null, null, null, null,
				String.format("%s,%s", TASK_COMPLETE, TASK_NAME));
		tasksCursor.moveToFirst();
		Task t;
		if (!tasksCursor.isAfterLast()) {
			do {
				int id = tasksCursor.getInt(0);
				String name = tasksCursor.getString(1);
				String boolValue = tasksCursor.getString(2);
				String description = tasksCursor.getString(tasksCursor.getColumnIndex(TASK_DESCRIPTION));
				boolean complete = Boolean.parseBoolean(boolValue);
				t = new Task(name);
				t.setId(id);
				t.setComplete(complete);
				t.setDescription(description);
				currentTasks.add(t);
			} while (tasksCursor.moveToNext());
		}

		tasksCursor.close();

	}

	public void setCurrentTasks(ArrayList<Task> currentTasks) {
		this.currentTasks = currentTasks;
	}

	public ArrayList<Task> getCurrentTasks() {
		return currentTasks;
	}

	public void addTask(Task t) {

		assert (null != t);
		ContentValues values = new ContentValues();
		values.put(TASK_NAME, t.getName());
		values.put(TASK_COMPLETE, Boolean.toString(t.isComplete()));
		values.put(TASK_DESCRIPTION, t.getDescription());
		t.setId(database.insert(TASKS_TABLE, null, values));
		currentTasks.add(t);
	}
	
	public void deleteTasks(Long[] ids){
		StringBuffer idsList = new StringBuffer();
		for (int i = 0; i < ids.length; i++){
			idsList.append(ids[i]);
			if (i < ids.length - 1)
				idsList.append(",");
		}
	
		String where = String.format("%s in (%s)", TASK_ID, idsList);
		database.delete(TASKS_TABLE, where, null);
	}

}