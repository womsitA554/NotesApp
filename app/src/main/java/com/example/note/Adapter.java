package com.example.note;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Adapter extends RecyclerView.Adapter<Adapter.viewholder> {
    Context context;
    ArrayList<Note> items;
    NoteClickListener noteClickListener;


    public Adapter(Context context, ArrayList<Note> items, NoteClickListener noteClickListener) {
        this.context = context;
        this.items = items;
        this.noteClickListener = noteClickListener;
    }

    @NonNull
    @Override
    public Adapter.viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.notes_item, parent, false);
        return new viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter.viewholder holder, int position) {
        holder.tvTitle.setText(items.get(position).getTitle());
        holder.tvContent.setText(items.get(position).getContent());
        holder.tvDate.setText(items.get(position).getDate());
        holder.tvDate.setSelected(true);

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                noteClickListener.onClick(items.get(holder.getAdapterPosition()));
            }
        });

        holder.cardView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                noteClickListener.onLongPress(items.get(holder.getAdapterPosition()), holder.cardView);
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class viewholder extends RecyclerView.ViewHolder {
        CardView cardView;
        TextView tvTitle, tvContent, tvDate;

        public viewholder(@NonNull View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.cardView);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvContent = itemView.findViewById(R.id.tvContent);
            tvDate = itemView.findViewById(R.id.tvDate);
        }
    }
}