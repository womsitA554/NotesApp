package com.example.note;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.PopupMenu;
import androidx.cardview.widget.CardView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener {
    RecyclerView rcvNote;
    Adapter adapter;
    Database database;
    ArrayList<Note> arrayList = new ArrayList<>();
    AppCompatButton btnAdd;
    Note selectedNote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        btnAdd = findViewById(R.id.btnAdd);
        rcvNote = findViewById(R.id.rcvNote);

        database = new Database(MainActivity.this, "NoteApp", null, 1);
        arrayList = new ArrayList<>();

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, NoteActivity.class);
                startActivityForResult(intent, 100);
            }
        });

        setUpRecycleView();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100) {
            if (resultCode == Activity.RESULT_OK) {
                Note newNote = (Note) data.getSerializableExtra("note");
                database.addNote(newNote);
                arrayList.clear();
                arrayList.addAll(database.getAll());
                adapter.notifyDataSetChanged();

                Log.d("check", "ok");
            } else {
                Log.d("check", "failed");
            }
        } else if (requestCode == 101) {
            if (resultCode == Activity.RESULT_OK) {
                Note newNote = (Note) data.getSerializableExtra("note");
                database.updateNote(newNote);
                arrayList.clear();
                arrayList.addAll(database.getAll());
                adapter.notifyDataSetChanged();
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        arrayList.clear();
        arrayList.addAll(database.getAll());
        adapter.notifyDataSetChanged();
    }

    private void setUpRecycleView() {
        rcvNote.setHasFixedSize(true);
        rcvNote.setLayoutManager(new StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL));
        adapter = new Adapter(MainActivity.this, arrayList, noteClickListener);
        rcvNote.setAdapter(adapter);
    }

    private final NoteClickListener noteClickListener = new NoteClickListener() {
        @Override
        public void onClick(Note note) {
            Intent intent = new Intent(MainActivity.this, NoteActivity.class);
            intent.putExtra("old_note", note);
            startActivityForResult(intent, 101);
        }

        @Override
        public void onLongPress(Note note, CardView cardView) {
            selectedNote = new Note();
            selectedNote = note;
            showBtnDelete(cardView);
        }
    };

    private void showBtnDelete(CardView cardView) {
        PopupMenu popupMenu = new PopupMenu(this, cardView);
        popupMenu.setOnMenuItemClickListener(this);
        popupMenu.inflate(R.menu.btn_delete);
        popupMenu.show();
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        if (item.getItemId() == R.id.btnDelete) {
            database.deleteNote(selectedNote.getId());
            arrayList.remove(selectedNote);
            adapter.notifyDataSetChanged();
            Toast.makeText(this, "Delete successfully", Toast.LENGTH_SHORT).show();
        }
        return false;
    }
}
