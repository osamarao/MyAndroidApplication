package me.osama.TaskManager;

import me.osama.TaskManager.tasks.Task;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddTaskActivity extends TaskManagerActivity {
	Button addButton, cancelButton;
	EditText taskNameTextField;
	private boolean changesPending;
	protected AlertDialog unsavedChangesDialog;
	EditText taskDescription;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_tasks_activity);
		setupViews();
	}

	private void setupViews() {
		addButton = (Button) findViewById(R.id.add_button);
		cancelButton = (Button) findViewById(R.id.cancel_button);
		taskNameTextField = (EditText) findViewById(R.id.task_name);
		taskDescription = (EditText) findViewById(R.id.task_description);
		taskNameTextField.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				changesPending = true;

			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,	int after) {
			}

			@Override
			public void afterTextChanged(Editable s) {
			}
		});
		
		taskDescription.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				changesPending = true;

			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,	int after) {
			}

			@Override
			public void afterTextChanged(Editable s) {
			}
		});

		addButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (taskNameTextField.getText().toString().equals("")){
					Toast.makeText(getApplicationContext(), "Please Enter Task Name", Toast.LENGTH_SHORT).show();
				}
				else{
					addTask();
				}
			}
		});

		cancelButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				cancel();

			}
		});

	}

	protected void cancel() {
		if (changesPending) {
			unsavedChangesDialog = new AlertDialog.Builder(this)
					.setTitle(R.string.unsaved_changes_title)
					.setMessage(R.string.unsaved_changes_message)
					.setPositiveButton(R.string.add_task, new AlertDialog.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int which) {
									addTask();
								}
							})
					.setNeutralButton(R.string.discard,	new AlertDialog.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int which) {
									finish();
								}
							})
					.setNegativeButton(android.R.string.cancel,	new AlertDialog.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int which) {
									unsavedChangesDialog.cancel();
								}
							})
					.create();
			unsavedChangesDialog.show();
		} else {
			finish();
		}
	}

	protected void addTask() {
		String taskName = taskNameTextField.getText().toString();
		String description = taskDescription.getText().toString();
		Task t = new Task(taskName);
		t.setDescription(description);
		getStuffApplication().addTask(t);
		finish();

	}

}