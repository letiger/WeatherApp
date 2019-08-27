package letier.brandon.weatherapp.ui.forecastlist;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;

import java.util.List;

import javax.inject.Inject;

import dagger.android.AndroidInjection;
import letier.brandon.weatherapp.R;
import letier.brandon.weatherapp.injection.ViewModelFactory;
import letier.brandon.weatherapp.ui.forecastlist.adapter.ForecastDto;
import letier.brandon.weatherapp.ui.forecastlist.adapter.ForecastListAdapter;
import letier.brandon.weatherapp.util.Resource;
import letier.brandon.weatherapp.util.SimpleItemTouchHelperCallback;

public class ForecastListActivity extends AppCompatActivity {

    private SimpleItemTouchHelperCallback touchHelperCallback;

    @Inject
    ViewModelFactory factory;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forecast_list);

        setupToolbar();

        ForecastListViewModel viewModel = ViewModelProviders.of(this, factory)
                .get(ForecastListViewModel.class);

        viewModel.initialize();
        viewModel.getForecastList().observe(this, this::populateList);

    }

    private void setupToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.forecast_list);
        toolbar.setNavigationIcon(R.drawable.ic_back);

        toolbar.setNavigationOnClickListener(v -> onBackPressed());
    }



    private void populateList(Resource<List<ForecastDto>> resource) {
        if (resource != null) {
            switch (resource.getResourceState()) {
                case LOADING:
                    break;

                case ERROR:
                    break;

                case SUCCESS:
                    List<ForecastDto> data = resource.getData();
                    if (data != null && !data.isEmpty()) {
                        initialiseForecastList(data);
                    }
            }
        }
    }

    private void initialiseForecastList(List<ForecastDto> forecastList) {
        RecyclerView listView = findViewById(R.id.recycler);
        listView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));

        ForecastListAdapter listAdapter = new ForecastListAdapter(forecastDto -> {
            NotificationManagerCompat.from(this)
                    .cancel(forecastDto.getId());
        });

        listView.setAdapter(listAdapter);
        touchHelperCallback = new SimpleItemTouchHelperCallback(this, listAdapter);
        ItemTouchHelper mItemTouchHelper = new ItemTouchHelper(touchHelperCallback);
        mItemTouchHelper.attachToRecyclerView(listView);
        listAdapter.submitList(forecastList);
    }
}
