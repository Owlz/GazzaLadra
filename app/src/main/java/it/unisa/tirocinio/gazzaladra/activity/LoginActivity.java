package it.unisa.tirocinio.gazzaladra.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.otaliastudios.cameraview.CameraListener;
import com.otaliastudios.cameraview.CameraView;

import java.io.FileOutputStream;

import it.unisa.tirocinio.gazzaladra.R;
import it.unisa.tirocinio.gazzaladra.database.User;

public class LoginActivity extends AppCompatActivity {
	private EditText nome;
	private EditText cognome;
	private CameraView cv;
	private byte[] fotoBytes;
	private ImageView im;

	private Button tasto;
	private Button scattaFoto;
	private boolean isPhotoTaken;

	public static final String EXTRA_REPLY = "EXTRA_REPLY";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);

		Toolbar toolbar = findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);

		isPhotoTaken = false;

		nome = findViewById(R.id.nome);
		cognome = findViewById(R.id.cognome);
		cv = findViewById(R.id.camera);
		scattaFoto = findViewById(R.id.scanna);
		im = findViewById(R.id.imageView);

		scattaFoto.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (isPhotoTaken) {
					cv.setVisibility(View.VISIBLE);
					im.setVisibility(ImageView.INVISIBLE);
					scattaFoto.setText("Scatta foto");
					isPhotoTaken = false;
				} else {
					cv.capturePicture();
				}
			}
		});

		cv.addCameraListener(new CameraListener() {
			@Override
			public void onPictureTaken(byte[] jpeg) {
				fotoBytes = jpeg;
				im.setVisibility(ImageView.VISIBLE);
				cv.setVisibility(View.INVISIBLE);
				im.setImageBitmap(
						BitmapFactory.decodeByteArray(fotoBytes, 0, fotoBytes.length)
				);
				scattaFoto.setText("Scatta nuova foto");
				isPhotoTaken = true;
				super.onPictureTaken(jpeg);
			}
		});

		tasto = findViewById(R.id.tasto);
		tasto.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				String n = nome.getText().toString();
				String c = cognome.getText().toString();

				Intent replyIntent = new Intent();
				if (!isPhotoTaken || n.equals("") || c.equals("")) {
					setResult(RESULT_CANCELED, replyIntent);
				} else {
					String filename = n + "_" + c + ".jpg";
					FileOutputStream outputStream;

					try {
						outputStream = openFileOutput(filename, Context.MODE_PRIVATE);
						outputStream.write(fotoBytes);
						outputStream.close();
					} catch (Exception e) {
						e.printStackTrace();
					}
					replyIntent.putExtra(EXTRA_REPLY, new User(n, c, filename));
					setResult(RESULT_OK, replyIntent);
				}
				finish();
			}
		});
	}

	@Override
	protected void onResume() {
		super.onResume();
		cv.start();
	}

	@Override
	protected void onPause() {
		super.onPause();
		cv.stop();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		cv.destroy();
	}
}
