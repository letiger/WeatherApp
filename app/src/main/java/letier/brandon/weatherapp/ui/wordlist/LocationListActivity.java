package letier.brandon.weatherapp.ui.wordlist;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import javax.inject.Inject;

import dagger.android.AndroidInjection;
import letier.brandon.weatherapp.R;
import letier.brandon.weatherapp.data.LocationEntity;
import letier.brandon.weatherapp.injection.ViewModelFactory;
import letier.brandon.weatherapp.ui.wordlist.adapter.LocationSpacerItemDecorator;
import letier.brandon.weatherapp.ui.wordlist.adapter.LocationItemDiffCallback;
import letier.brandon.weatherapp.ui.wordlist.adapter.LocationPagedListAdapter;
import letier.brandon.weatherapp.ui.wordlist.add.AddLocationActivity;

public class LocationListActivity extends AppCompatActivity implements LocationPagedListAdapter.WordItemClickListener {
    private LocationListViewModel viewModel;
    private LocationPagedListAdapter adapter;

    @Inject
    ViewModelFactory factory;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_word_list);

        initializeLayout();

        viewModel = ViewModelProviders.of(this,factory)
                .get(LocationListViewModel.class);
        viewModel.getWordEntityList().observe(this, wordEntities -> {
            if (wordEntities != null && !wordEntities.isEmpty()) {
                adapter.submitList(wordEntities);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_word_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add:
                startActivity(AddLocationActivity.newLocationInstance(this));
                return true;

                default:
                    return super.onOptionsItemSelected(item);
        }
    }

    private void initializeLayout() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.word_list);
        toolbar.setNavigationIcon(R.drawable.ic_back);

        toolbar.setNavigationOnClickListener(v -> onBackPressed());

        adapter = new LocationPagedListAdapter(new LocationItemDiffCallback(), this);
        RecyclerView listView = findViewById(R.id.recycler);
        listView.addItemDecoration(new LocationSpacerItemDecorator(this));
        listView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        listView.setAdapter(adapter);
    }

    @Override
    public void onItemClicked(LocationEntity location) {
        startActivity(AddLocationActivity.updateLocationInstance(this, location));
    }

    @Override
    public void onItemDelete(LocationEntity location) {
        AlertDialog alertDialog = new AlertDialog.Builder(this)
                .setMessage(getString(R.string.are_you_sure, location.getLocation()))
                .setPositiveButton("Yes", (dialog, which) -> viewModel.deleteLocation(location))
                .setNegativeButton("No", (dialog, which) -> dialog.dismiss())
                .create();

        alertDialog.show();
    }
}
