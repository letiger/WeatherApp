package letier.brandon.weatherapp.ui.wordlist.add;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import javax.inject.Inject;

import dagger.android.AndroidInjection;
import letier.brandon.weatherapp.R;
import letier.brandon.weatherapp.data.LocationEntity;
import letier.brandon.weatherapp.injection.ViewModelFactory;

public class AddLocationActivity extends AppCompatActivity implements TextWatcher, View.OnClickListener {

    private static final String UPDATE_LOCATION = "UPDATE_LOCATION";
    private static final String CURRENT_LOCATION = "CURRENT_LOCATION";

    private Button saveButton;
    private AddLocationViewModel viewModel;

    @Inject
    ViewModelFactory factory;

    public static Intent newLocationInstance(Context context) {
        Intent intent = new Intent(context, AddLocationActivity.class);
        intent.putExtra(UPDATE_LOCATION, false);
        return intent;
    }

    public static Intent updateLocationInstance(Context context, LocationEntity location) {
        Intent intent = new Intent(context, AddLocationActivity.class);
        intent.putExtra(UPDATE_LOCATION, true);
        intent.putExtra(CURRENT_LOCATION, location);
        return intent;
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_location);

        initializeLayout();

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {

            viewModel = ViewModelProviders.of(this,factory)
                    .get(AddLocationViewModel.class);
            viewModel.initialize(bundle.getBoolean(UPDATE_LOCATION),
                    (LocationEntity) bundle.get(CURRENT_LOCATION));
            viewModel.getViewState().observe(this, this::updateView);
            viewModel.getOnComplete().observe(this, isComplete -> {
                if (isComplete != null && isComplete) {
                    finish();
                }
            });
        }
    }

    private void initializeLayout() {
        saveButton = findViewById(R.id.button_save);
        saveButton.setOnClickListener(this);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        toolbar.setNavigationIcon(R.drawable.ic_back);
        toolbar.setNavigationOnClickListener(v -> onBackPressed());

        EditText updateEditTextView = findViewById(R.id.edit_word);
        updateEditTextView.addTextChangedListener(this);
    }

    private void updateView(AddLocationViewModel.ViewState viewState) {
        if (viewState != null) {
            getSupportActionBar().setTitle(viewState.title);
            saveButton.setText(viewState.buttonText);
            saveButton.setEnabled(viewState.isButtonEnabled);
        }
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        // Left blank intentionally.
    }

    @Override
    public void onTextChanged(CharSequence s, int i, int i1, int i2) {
        if (viewModel != null) {
           viewModel.validateLocation(s.toString());
        }
    }

    @Override
    public void afterTextChanged(Editable editable) {
        // Left blank intentionally.
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.button_save) {
            if (viewModel != null) {
                viewModel.onSaveClicked();
            }
        }
    }
}
