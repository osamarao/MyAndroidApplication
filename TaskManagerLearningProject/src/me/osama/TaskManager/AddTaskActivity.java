package me.osama.TaskManager;

import me.osama.TaskManager.tasks.Task;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class AddTaskActivity extends TaskManagerActivity {

	private EditText taskNameEditText;
	private Button addButton;
	private Button cancelButton;
	protected boolean changesPending; // initially initialized to false
	private AlertDialog unsavedChangesDialog;
	private EditText taskDescriptionEditText;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_tasks_activity);
		
		setUpViews();
	}
	
	
	
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		// write code that uses the back button
	}



	private void setUpViews() {
	
		taskNameEditText = (EditText)findViewById(R.id.task_name);
		taskDescriptionEditText = (EditText)findViewById(R.id.task_desc);
		addButton = (Button)findViewById(R.id.add_button);
		cancelButton = (Button) findViewById(R.id.cancel_button);
		
		addButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				addTask();
			}
		});
		
		cancelButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				cancel();
				
			}
			

		});
		
		taskNameEditText.addTextChangedListener(new TextWatcher() {
			
			public void onTextChanged(CharSequence s, int start, int before, int count ) {
				changesPending = true;
					
			}
			
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
			public void afterTextChanged(Editable s) {} // check allowable chars, spell checker
			
		});
		
		taskDescriptionEditText.addTextChangedListener(new TextWatcher() {
			
			public void onTextChanged(CharSequence s, int start, int before, int count ) {
				changesPending = true;
					
			}
			
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
			public void afterTextChanged(Editable s) {} // check allowable chars, spell checker
			
		});
	}

	protected void cancel() {
		// TODO Auto-generated method stub
		if(changesPending && !taskNameEditText.getText().toString().equals("")) { // and empty string
			unsavedChangesDialog = new AlertDialog.Builder(this)
			.setTitle(R.string.unsaved_changes_title)
			.setMessage(R.string.unsaved_changes_message)
			.setPositiveButton(R.string.add_task, new AlertDialog.OnClickListener() {
				public void onClick(DialogInterface dialog, int which) {
					addTask();
				}

			})
			.setNeutralButton(R.string.discard, new AlertDialog.OnClickListener() {
				public void onClick(DialogInterface dialog, int which) {
					finish();
				}
			})
			.setNegativeButton(android.R.string.cancel, new AlertDialog.OnClickListener() {
				public void onClick(DialogInterface dialog, int which) {
					unsavedChangesDialog.cancel();
					// cancels dialog
				}

			})
		
			.create();
			unsavedChangesDialog.show();
		}else {
			finish();
		}
		
	}

	protected void addTask() {
		// TODO Auto-generated method stub
		String taskName = taskNameEditText.getText().toString();
		Task t = new Task(taskName);
		t.setDescription(taskDescriptionEditText.getText().toString());
		getTaskManagerApplication().add(t);
		finish();
	}
}