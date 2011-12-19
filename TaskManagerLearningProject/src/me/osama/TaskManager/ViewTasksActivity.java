package me.osama.TaskManager;

import me.osama.TaskManager.adapter.TasksListsAdapter;
import me.osama.TaskManager.tasks.Task;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

public class ViewTasksActivity extends ListActivity {

	private Button addButton;
	private TasksListsAdapter adapter;
	private TaskManagerApplication app;
	private Button removeButton;
	@SuppressWarnings("unused")
	private ArrayAdapter<Task> adapter2;

	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        setUpViews();
        app = (TaskManagerApplication)getApplication();
        adapter = new TasksListsAdapter(this, app.getCurrentTasks());
        //adapter2 = new ArrayAdapter<Task>(this, android.R.layout.two_line_list_item, app.getCurrentTasks());
        for (Task task: app.getCurrentTasks()){
        	//i++;
        	Log.i("tasks", task.toString());
        }
        
        setListAdapter(adapter);
    }
	
	@Override
	protected void onResume() {
		super.onResume();
		adapter.forceReload();
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		try{
		super.onListItemClick(l, v, position, id);
		adapter.toggleTaskCompleteAtPosition(position);
		app.getCurrentTasks().get(position).getDescription().toString();
		Toast.makeText(this, app.getCurrentTasks().get(position).getDescription().toString(), Toast.LENGTH_SHORT).show();
		}
		catch(NullPointerException npe){
			Toast.makeText(this, "NPE Occured", Toast.LENGTH_SHORT);
		}
	}
	
	protected void removeCompletedTasks() {
		Long[] ids = adapter.removeCompletedTasks();
		app.deleteTasks(ids);
	}
	
	private void setUpViews() {
		addButton = (Button)findViewById(R.id.add_button);
		removeButton = (Button)findViewById(R.id.remove_button);
		
		addButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(ViewTasksActivity.this, AddTaskActivity.class);
				startActivity(intent);
			}
		});
		removeButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				removeCompletedTasks();
			}
		});

	}

}