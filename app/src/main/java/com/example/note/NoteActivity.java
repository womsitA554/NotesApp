package com.example.note;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.text.SimpleDateFormat;
import java.util.Date;

public class NoteActivity extends AppCompatActivity {
    EditText etTitle, etContent;
    Button btnSave;
    Note note;
    boolean isOldNote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_note);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        btnSave = findViewById(R.id.btnSave);
        etTitle = findViewById(R.id.etTitle);
        etContent = findViewById(R.id.etContent);

        note = new Note();
        try {
            note = (Note) getIntent().getSerializableExtra("old_note");
            etTitle.setText(note.getTitle());
            etContent.setText(note.getContent());
            isOldNote = true;
            if (note != null) {
                Log.d("check1", "ok");
            } else {
                Log.d("check1", "failed");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isOldNote) {
                    note = new Note();
                }

                String title = etTitle.getText().toString();
                String content = etContent.getText().toString();
                SimpleDateFormat format = new SimpleDateFormat("EEE, d MMM YYYY HH:mm");
                Date date = new Date();
                if (content.isEmpty()) {
                    etContent.setError("In value");
                } else {
                    if (title.isEmpty()) {
                        note.setTitle("Untitled");
                    } else {
                        note.setTitle(title);
                    }
                    note.setContent(content);
                    note.setDate(format.format(date));

                    Intent intent = new Intent();
                    intent.putExtra("note", note);
                    setResult(Activity.RESULT_OK, intent);
                    finish();
                    Toast.makeText(NoteActivity.this, "Add successfully", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }
}