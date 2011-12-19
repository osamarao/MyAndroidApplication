package me.osama.TaskManager.views;

import me.osama.TaskManager.tasks.Task;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.CheckedTextView;
import android.widget.LinearLayout;

public class TaskListItem extends LinearLayout {
	
	Task task;
	CheckedTextView checkbox;
	//private TextView taskDescription;
	
	
	public TaskListItem(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	
	@Override
	protected void onFinishInflate() {
		super.onFinishInflate();
		checkbox = (CheckedTextView) findViewById(android.R.id.text1);
		//taskDescription = (TextView) findViewById(android.R.id.text2);
	}
	
	public Task getTask() {
		return task;
	}

	public void setTask(Task task) {
		this.task = task;
		checkbox.setText(task.getName());
		checkbox.setChecked(task.isComplete());
		//taskDescription.setText("HEELELELELE");
		//taskDescription.setText(task.getDescription());
	}


}
