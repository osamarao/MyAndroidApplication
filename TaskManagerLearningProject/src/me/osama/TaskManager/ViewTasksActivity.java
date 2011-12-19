package me.osama.TaskManager;

import me.osama.TaskManager.adapter.TaskListAdapter;
import me.osama.TaskManager.tasks.Task;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

public class ViewTasksActivity extends ListActivity {
    private Button addButton;
	private TaskManagerApplication app;
	private TaskListAdapter adapter;
	private Button removeButton;

	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        setUpViews();
        
        app = (TaskManagerApplication)getApplication();
        adapter = new TaskListAdapter(app.getCurrentTasks(), this);
        setListAdapter(adapter); // list knows where to get its data
    }
    
    protected void onResume() {
    	super.onResume(); 
    	// tickle list adapter
    	adapter.forceReload();
    
    }
    
    @Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		adapter.toggleTaskCompleteAtPosition(position);
		
		Task t = adapter.getItem(position);
		app.saveTask(t);
		
	}

    protected void removeCompletedTasks() {
    	Long[] ids = adapter.removeCompletedTasks();
    	app.deleteTasks(ids);
    }
    
	private void setUpViews() {
    	addButton = (Button)findViewById(R.id.add_button);
    	removeButton = (Button)findViewById(R.id.remove_button);
    	
    	removeButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				removeCompletedTasks();
				
			}
		});
    	
    	addButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(ViewTasksActivity.this, AddTaskActivity.class);
				startActivity(intent);
				
			}
		});
    }
    
}