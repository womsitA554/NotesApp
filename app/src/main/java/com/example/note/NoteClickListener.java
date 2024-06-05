package com.example.note;

import androidx.cardview.widget.CardView;

public interface NoteClickListener {
    void onClick(Note note);
    void onLongPress(Note note, CardView cardView);
}
