package me.osama.TaskManager.adapter;

import java.util.ArrayList;

import me.osama.TaskManager.R;
import me.osama.TaskManager.tasks.Task;
import me.osama.TaskManager.views.TaskListItem;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class TaskListAdapter extends BaseAdapter {

	private ArrayList<Task> tasks;
	private Context context;
	
	public TaskListAdapter(ArrayList<Task> tasks, Context context) {
		super();
		this.tasks = tasks;
		this.context = context;
	}
	
	@Override
	public int getCount() {
		return tasks.size();
	}

	@Override
	public Task getItem(int position) {
		return (null == tasks) ? null : tasks.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		TaskListItem tli;
		if(null == convertView) {
			tli = (TaskListItem)View.inflate(context, R.layout.task_list_item, null);
		} else {
			tli = (TaskListItem)convertView;
		}
		tli.setTask(tasks.get(position));
		return tli;
	}

	public void forceReload() {
		notifyDataSetChanged();
		// notify anybody using this data set that something changed
		
	}

	public void toggleTaskCompleteAtPosition(int position) {
		Task t = tasks.get(position);
		t.toggleComplete();
		notifyDataSetChanged();
	}

	public Long[] removeCompletedTasks() {
		
		ArrayList<Long> completedIds = new ArrayList<Long>();
		ArrayList<Task> completedTasks = new ArrayList<Task>();
		for(Task task: tasks) {
			if(task.isComplete()) {
				completedIds.add(task.getId());
				completedTasks.add(task);
			}
		}
		tasks.removeAll(completedTasks);
		notifyDataSetChanged();
		
		return completedIds.toArray(new Long[] {});
	}
	
}