package letier.brandon.weatherapp.ui.wordlist.adapter;

import android.arch.paging.PagedListAdapter;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import letier.brandon.weatherapp.R;
import letier.brandon.weatherapp.data.LocationEntity;

public class LocationPagedListAdapter extends PagedListAdapter<LocationEntity, LocationPagedListAdapter.LocationItemHolder> {

    private final WordItemClickListener listener;
    public LocationPagedListAdapter(@NonNull LocationItemDiffCallback callback, WordItemClickListener listener) {
        super(callback);
        this.listener = listener;
    }

    @NonNull
    @Override
    public LocationItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int position) {
        return new LocationItemHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_word, parent, false), listener);
    }

    @Override
    public void onBindViewHolder(@NonNull LocationItemHolder holder, int position) {
        LocationEntity item = getItem(position);
        if (item != null) {
            holder.idTextView.setText(String.valueOf(item.getId()));
            holder.wordTextView.setText(item.getLocation());
            holder.deleteButton.setEnabled(true);
        } else {
            holder.idTextView.setText(null);
            holder.wordTextView.setText(null);
            holder.deleteButton.setEnabled(false);
        }
    }

    class LocationItemHolder extends RecyclerView.ViewHolder {
        private final TextView idTextView;
        private final TextView wordTextView;
        private final ImageView deleteButton;

        LocationItemHolder(@NonNull View itemView, WordItemClickListener listener) {
            super(itemView);
            idTextView = itemView.findViewById(R.id.id);
            wordTextView = itemView.findViewById(R.id.location);
            deleteButton = itemView.findViewById(R.id.delete);
            deleteButton.setOnClickListener(v -> listener.onItemDelete(getItem(getAdapterPosition())));
            itemView.setOnClickListener(v -> listener.onItemClicked(getItem(getAdapterPosition())));
        }
    }

    public interface WordItemClickListener {
        void onItemClicked(LocationEntity wordDto);
        void onItemDelete(LocationEntity wordDto);
    }
}
