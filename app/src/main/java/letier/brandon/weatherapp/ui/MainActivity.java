package letier.brandon.weatherapp.ui;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import javax.inject.Inject;

import dagger.android.AndroidInjection;
import letier.brandon.weatherapp.R;
import letier.brandon.weatherapp.injection.ViewModelFactory;

public class MainActivity extends AppCompatActivity {

    @Inject
    ViewModelFactory factory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(view ->
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show());

        final TextView text = findViewById(R.id.text);
        MainViewModel viewModel = ViewModelProviders.of(this, factory)
                .get(MainViewModel.class);
        viewModel.initialize();
        viewModel.getMessage().observe(this, message -> {
            if (message !=null && !message.isEmpty()) {
                text.setText(message);
            }
        });
    }

}
