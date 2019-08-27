package letier.brandon.weatherapp.ui.forecastlist.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import letier.brandon.weatherapp.R;
import letier.brandon.weatherapp.util.ItemTouchHelperAdapter;
import letier.brandon.weatherapp.util.Strings;

public class ForecastListAdapter extends RecyclerView.Adapter<ForecastListAdapter.ForecastHolder>
        implements ItemTouchHelperAdapter {


    private List<ForecastDto> forecastList = new ArrayList<>();
    private final ForecastActionListener actionsListener;

    public ForecastListAdapter(ForecastActionListener actionsListener) {
        this.actionsListener = actionsListener;
    }

    @NonNull
    @Override
    public ForecastHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        return new ForecastHolder(
                LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.ma_list_item_forecast, parent, false)
        );
    }

    @Override
    public int getItemCount() {
        if (forecastList != null) return forecastList.size();
        return 0;
    }

    @Override
    public void onBindViewHolder(@NonNull ForecastHolder holder, int position) {
        ForecastDto forecast = forecastList.get(position);
        holder.idTextView.setText(String.valueOf(forecast.getId()));
        holder.nameTextView.setText(forecast.getName());
        holder.tempTextView.setText(Strings.temperature(forecast.getTemp_max()));
    }

    public void submitList(List<ForecastDto> forecastList) {
        this.forecastList.clear();
        this.forecastList.addAll(forecastList);
        notifyDataSetChanged();
    }

    @Override
    public boolean onItemMove(int fromPosition, int toPosition) {
        return false;
    }

    @Override
    public void onItemDismiss(int position) {
        ForecastDto notification = forecastList.get(position);
        forecastList.remove(notification);
        actionsListener.onForecastRemoved(notification);
        this.notifyItemRemoved(position);
    }

    class ForecastHolder extends RecyclerView.ViewHolder {
        TextView idTextView;
        TextView nameTextView;
        TextView tempTextView;
        ForecastHolder(View itemView) {
            super(itemView);

            idTextView = itemView.findViewById(R.id.id);
            nameTextView = itemView.findViewById(R.id.name);
            tempTextView = itemView.findViewById(R.id.temperature);
        }
    }

    public interface ForecastActionListener {
        void onForecastRemoved(ForecastDto forecastDto);
    }
}
