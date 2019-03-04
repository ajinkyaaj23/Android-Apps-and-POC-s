package com.a629258.mvvm;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Toast;

public class AddEditNoteActivity extends AppCompatActivity {

    private EditText edtTitle;
    private EditText edtDescription;
    private NumberPicker numberPicker;

    public static final String EXTRA_ID = "com.a629258.mvvm.EXTRA_ID";
    public static final String EXTRA_TITLE = "com.a629258.mvvm.EXTRA_TITLE";
    public static final String EXTRA_DESCRIPTION = "com.a629258.mvvm.EXTRA_DESCRIPTION";
    public static final String EXTRA_PRIORITY = "com.a629258.mvvm.EXTRA_PRIORITY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);

        edtTitle = (EditText) findViewById(R.id.editTextTitle);
        edtDescription = (EditText) findViewById(R.id.editTextDescription);
        numberPicker = (NumberPicker) findViewById(R.id.numberPickerPriority);

        numberPicker.setMinValue(1);
        numberPicker.setMaxValue(10);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);

        Intent intent = getIntent();
        if (intent.hasExtra(EXTRA_ID)){
            setTitle("Edit Note");
            edtTitle.setText(intent.getStringExtra(EXTRA_TITLE));
            edtDescription.setText(intent.getStringExtra(EXTRA_DESCRIPTION));
            numberPicker.setValue(intent.getIntExtra(EXTRA_PRIORITY,1));
        }else {
            setTitle("Add Note");
        }

    }

    public void saveNote(){
        String title = edtTitle.getText().toString();
        String description = edtDescription.getText().toString();
        int priority = numberPicker.getValue();

        if (title.trim().isEmpty() || description.trim().isEmpty()){
            Toast.makeText(AddEditNoteActivity.this,"Please enter Title and Description",Toast.LENGTH_SHORT).show();
            return;
        }

        Intent data = new Intent();
        data.putExtra(EXTRA_TITLE, title);
        data.putExtra(EXTRA_DESCRIPTION, description);
        data.putExtra(EXTRA_PRIORITY,priority);

        int id = getIntent().getIntExtra(EXTRA_ID,-1);
        if (id != -1){
            data.putExtra(EXTRA_ID, id);
        }

        setResult(RESULT_OK,data);
        finish();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.add_note_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.save_note:
                saveNote();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
