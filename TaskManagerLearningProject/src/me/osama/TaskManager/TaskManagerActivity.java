package me.osama.TaskManager;

import android.app.Activity;

public class TaskManagerActivity extends Activity {

	public TaskManagerActivity() {
		super();
	}

	protected TaskManagerApplication getTaskManagerApplication() {
		return (TaskManagerApplication)getApplication();
	}

}