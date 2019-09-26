package letier.brandon.weatherapp.ui.wordlist.adapter;

import android.support.annotation.NonNull;
import android.support.v7.util.DiffUtil;

import letier.brandon.weatherapp.data.LocationEntity;

public class LocationItemDiffCallback extends DiffUtil.ItemCallback<LocationEntity> {
    @Override
    public boolean areItemsTheSame(@NonNull LocationEntity oldItem, @NonNull LocationEntity newItem) {
        return oldItem.getId() == newItem.getId();
    }

    @Override
    public boolean areContentsTheSame(@NonNull LocationEntity oldItem, @NonNull LocationEntity newItem) {
        return oldItem.getId() == newItem.getId() && oldItem.getLocation().equals(newItem.getLocation());
    }
}
