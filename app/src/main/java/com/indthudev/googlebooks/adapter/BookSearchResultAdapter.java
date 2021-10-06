package com.indthudev.googlebooks.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.indthudev.googlebooks.R;
import com.indthudev.googlebooks.utils.Utils;
import com.indthudev.googlebooks.model.Volume;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

public class BookSearchResultAdapter extends RecyclerView.Adapter<BookSearchResultAdapter.BookSearchResultHolder> {

    private List<Volume> volumeList = new ArrayList<>();
    OnItemClickListener clickListener;
    private Context context;

    public BookSearchResultAdapter(Context context, List<Volume> list, OnItemClickListener clickListener) {
        this.volumeList = list;
        this.clickListener = clickListener;
        this.context = context;
    }

    @NonNull
    @Override
    public BookSearchResultHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.book_item,parent, false);

        return new  BookSearchResultHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BookSearchResultHolder holder, int position) {

        Volume volume = volumeList.get(position);

        holder.titleTextView.setText(volume.getVolumeInfo().getTitle());
        holder.publishedDateTextView.setText(volume.getVolumeInfo().getPublishedDate());

        if (volume.getVolumeInfo().getImageLinks() != null) {
            String imageUrl = volume.getVolumeInfo().getImageLinks().getSmallThumbnail()
                    .replace("http://", "https://");

            Glide.with(holder.itemView)
                    .load(imageUrl)
                    .into(holder.smallThumbnailImageView);
        }

        if (volume.getVolumeInfo().getAuthors() != null) {
            Utils u = new Utils();
            String authors = u.StringJoin(volume.getVolumeInfo().getAuthors(), ", ");
            holder.authorsTextView.setText(authors);
        }

        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickListener.onItemClick(volume, holder.getAdapterPosition());
            }
        });
    }

    @Override
    public int getItemCount() {
        return volumeList.size();
    }

    public void setResults(List<Volume> results) {
        this.volumeList = results;
        notifyDataSetChanged();
    }

    class BookSearchResultHolder extends RecyclerView.ViewHolder {

        private TextView titleTextView;
        private TextView authorsTextView;
        private TextView publishedDateTextView;
        private ImageView smallThumbnailImageView;
        private ConstraintLayout layout;

        public BookSearchResultHolder(@NonNull View view) {
            super(view);

            titleTextView = itemView.findViewById(R.id.book_item_title);
            authorsTextView = itemView.findViewById(R.id.book_item_authors);
            publishedDateTextView = itemView.findViewById(R.id.book_item_publishedDate);
            smallThumbnailImageView = itemView.findViewById(R.id.book_item_smallThumbnail);
            layout = itemView.findViewById(R.id.book_list_item);
        }

    }

    public interface OnItemClickListener {
        void onItemClick(Volume volume, int position);
    }
}
