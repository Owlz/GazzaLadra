package it.unisa.tirocinio.gazzaladra.activity;

import android.Manifest;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import it.unisa.tirocinio.gazzaladra.R;
import it.unisa.tirocinio.gazzaladra.UserListAdapter;
import it.unisa.tirocinio.gazzaladra.database.User;
import it.unisa.tirocinio.gazzaladra.database.UserViewModel;

public class MainActivity extends AppCompatActivity {

	private RecyclerView rv;
	private UserViewModel uvm;
	private TextView warning;

	private final static int PERMISSION_REQUEST_CODE = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		warning = findViewById(R.id.warning);

		Toolbar toolbar = findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);

		FloatingActionButton fab = findViewById(R.id.fab);

		fab.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
				startActivityForResult(intent, 1);
			}
		});


		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
			List<String> permissions = new ArrayList<>();

			if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
				permissions.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
			}
			if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
				permissions.add(Manifest.permission.CAMERA);
			}
			if (checkSelfPermission(Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
				permissions.add(Manifest.permission.RECORD_AUDIO);
			}
			if (permissions.size() != 0)
				requestPermissions(permissions.toArray(new String[permissions.size()]), PERMISSION_REQUEST_CODE);
		}


		rv = findViewById(R.id.recycler_view);

		final UserListAdapter adapter = new UserListAdapter(this);
		uvm = ViewModelProviders.of(this).get(UserViewModel.class);
		uvm.getAllUsers().observe(this, new Observer<List<User>>() {
			@Override
			public void onChanged(@Nullable List<User> users) {
				adapter.setUsers(users);

			}
		});

		if (adapter.getItemCount() > 0) {
			Log.w("MainActivity", ">0");
			rv.setLayoutManager(new GridLayoutManager(this, 2));
			warning.setVisibility(View.GONE);
		} else {
			Log.w("MainActivity", "== 0");
			rv.setLayoutManager(null);
			warning.setVisibility(View.VISIBLE);

		}
		rv.setAdapter(adapter);
	}

	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		if (requestCode == 1) {
			if (resultCode == RESULT_OK) {
				User u = data.getParcelableExtra(LoginActivity.EXTRA_REPLY);
				uvm.insertUser(u);
			} else {
				Toast.makeText(getApplicationContext(), "errore", Toast.LENGTH_LONG)
						.show();
			}
		}
	}

	@Override
	public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
		switch (requestCode) {
			case PERMISSION_REQUEST_CODE: {
				for (int i = 0; i < grantResults.length; i++) {
					if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
						Toast.makeText(
								this,
								"Il permesso " + permissions[i] + " non è stato concesso",
								Toast.LENGTH_SHORT).show();
						finish();
					}
				}
			}
		}

	}

}