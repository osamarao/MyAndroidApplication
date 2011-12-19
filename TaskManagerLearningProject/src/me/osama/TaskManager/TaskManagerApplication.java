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
	SQLiteDatabase database;

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		TasksSQLLiteOpenHelper helper = new TasksSQLLiteOpenHelper(this);
		database = helper.getWritableDatabase();
		if (null == currentTasks) {
			loadTasks();
		}
	}

	private void loadTasks() {
		currentTasks = new ArrayList<Task>();
		try {
			Cursor tasksCursor = database.query(TASKS_TABLE, new String[] {
					TASK_ID, TASK_NAME, TASK_COMPLETE, TASK_DESCRIPTION },
					null, null, null, null,
					String.format("%s,%s", TASK_COMPLETE, TASK_NAME));
			tasksCursor.moveToFirst();
			Task t;
			if (!tasksCursor.isAfterLast())
				do {
					long id = tasksCursor.getLong(0);
					String name = tasksCursor.getString(1);
					String boolValue = tasksCursor.getString(2);
					String description = tasksCursor.getString(3);
					boolean complete = Boolean.parseBoolean(boolValue);
					t = new Task(name);
					t.setId(id);
					t.setComplete(complete);
					t.setDescription(description);
					currentTasks.add(t);
				} while (tasksCursor.moveToNext());
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void setCurrentTasks(ArrayList<Task> currentTasks) {
		this.currentTasks = currentTasks;

	}

	public ArrayList<Task> getCurrentTasks() {
		return currentTasks;
	}

	public void add(Task t) {
		assert (null != t); // catches if t references null

		// if task name is "", don't add
		if (!t.getName().equals("")) {

			ContentValues values = new ContentValues();
			values.put(TASK_NAME, t.getName());
			values.put(TASK_COMPLETE, Boolean.toString(t.isComplete()));
			values.put(TASK_DESCRIPTION, t.getDescription());

			long id = database.insert(TASKS_TABLE, null, values); // returns an
																	// id after
																	// insert

			t.setId(id);
			currentTasks.add(t);

		} // end if

	}

	public void saveTask(Task t) {
		assert (null != t);

		ContentValues values = new ContentValues();
		values.put(TASK_NAME, t.getName());
		values.put(TASK_COMPLETE, Boolean.toString(t.isComplete()));
		values.put(TASK_DESCRIPTION, t.getDescription());

		long id = t.getId();
		String where = String.format("%s = ?", TASK_ID);

		// %s = ?
		// place holder where null is

		try {
			database.update(TASKS_TABLE, values, where,
					new String[] { id + "" }); // protect against sql injections
												// (sanitization process)
		} catch (Exception e) {
			e.printStackTrace();
		}
		;
	}

	public void deleteTasks(Long[] ids) {
		StringBuffer idList = new StringBuffer();
		for (int i = 0; i < ids.length; i++) {
			idList.append(ids[i]);
			if (i < ids.length - 1) {
				idList.append(",");
			}
		}

		// delete from tasks
		// where ids in (1,42,5)
		String where = String.format("%s in (%s)", TASK_ID, idList);
		database.delete(TASKS_TABLE, where, null);
	}

}