package com.example.note;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class Database extends SQLiteOpenHelper {
    private final static String TableName = "Note";
    private static final String ID = "Id";
    private final static String Title = "Title";
    private final static String Content = "Content";
    private final static String Date = "Date";
    private final static String Pin = "Pin";
    public Database(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE = "CREATE TABLE " + TableName + " (" +
                ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                Title + " TEXT, " +
                Content + " TEXT, " +
                Date + " TEXT, " +
                Pin + " INTEGER)";
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TableName);
        onCreate(db);
    }

    public ArrayList<Note> getAll() {
        ArrayList<Note> noteList = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TableName;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") int id = cursor.getInt(cursor.getColumnIndex(ID));
                @SuppressLint("Range") String title = cursor.getString(cursor.getColumnIndex(Title));
                @SuppressLint("Range") String content = cursor.getString(cursor.getColumnIndex(Content));
                @SuppressLint("Range") String date = cursor.getString(cursor.getColumnIndex(Date));
                @SuppressLint("Range") boolean pin = cursor.getInt(cursor.getColumnIndex(Pin)) == 1;
                Note note = new Note(id, title, content, date, pin);
                noteList.add(note);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return noteList;
    }

    public long addNote(Note note) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Title, note.getTitle());
        values.put(Content, note.getContent());
        values.put(Date, note.getDate());
        values.put(Pin, note.isPin() ? 1 : 0);

        long result = db.insert(TableName, null, values);
        db.close();
        return result;
    }

    public int updateNote(Note note) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Title, note.getTitle());
        values.put(Content, note.getContent());
        values.put(Date, note.getDate());
        values.put(Pin, note.isPin() ? 1 : 0);

        int result = db.update(TableName, values, ID + " = ?", new String[]{String.valueOf(note.getId())});
        db.close();
        return result;
    }

    public int deleteNote(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        int result = db.delete(TableName, ID + " = ?", new String[]{String.valueOf(id)});
        db.close();
        return result;
    }
}
