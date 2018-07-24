package it.unisa.tirocinio.gazzaladra;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileInputStream;
import java.util.List;

import it.unisa.tirocinio.gazzaladra.activity.SessionActivity;
import it.unisa.tirocinio.gazzaladra.database.User;
import it.unisa.tirocinio.gazzaladra.database.UserViewModel;

public class UserListAdapter extends RecyclerView.Adapter<UserListAdapter.UserViewHolder> {

	class UserViewHolder extends RecyclerView.ViewHolder {
		private final TextView nome;
		//private final TextView desc;
		private final ImageView im;
		private User user = null;

		private UserViewHolder(View userView) {
			super(userView);
			nome = itemView.findViewById(R.id.nome);
			//desc = itemView.findViewById(R.id.desc);
			im = itemView.findViewById(R.id.img);
			userView.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					Intent i = new Intent(context, SessionActivity.class);
					if (user != null) {
						i.putExtra("user", user);
					}
					context.startActivity(i);
				}
			});

			userView.setOnLongClickListener(new View.OnLongClickListener() {
				@Override
				public boolean onLongClick(View v) {
					AlertDialog.Builder builder = new AlertDialog.Builder(context);
					builder.setCancelable(true)
							.setMessage("Vuoi cancellare l'utente " + user.getName() + " " + user.getLastName() + "?")
							.setPositiveButton("Elimina", new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog, int id) {
									userViewModel.deleteUser(user);
									Toast.makeText(context, "Puff ded", Toast.LENGTH_SHORT).show();
									dialog.dismiss();
								}
							})
							.setNegativeButton("Annulla", new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog, int id) {
									dialog.dismiss();
								}
							})
							.create().show();
					return true;
				}
			});
		}
	}

	private final LayoutInflater inflater;
	private List<User> users;
	private Context context;
	private UserViewModel userViewModel;

	public UserListAdapter(Context c, UserViewModel uvm) {
		inflater = LayoutInflater.from(c);
		this.userViewModel = uvm;
		this.context = c;

	}

	@Override
	public UserViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		View item = inflater.inflate(R.layout.recycleview_item, parent, false);
		return new UserViewHolder(item);
	}

	@Override
	public void onBindViewHolder(UserViewHolder holder, int position) {
		if (users != null) {
			User user = users.get(position);

			holder.user = user;

			holder.nome.setText(user.getName() + " " + user.getLastName());
			//holder.desc.setText("4 test effettuati");

			FileInputStream inStream;
			try {
				inStream = context.openFileInput(user.getPhoto());
				Bitmap b = BitmapFactory.decodeStream(inStream);
				holder.im.setImageBitmap(b);
			} catch (Exception e) {
				Log.w("UserListAdapter", "Errore mentre si decoficicava la foto di "
						+ user.getName());
				e.printStackTrace();
			}
		}
	}

	public void setUsers(List<User> users) {
		this.users = users;
		notifyDataSetChanged();
	}

	public int getItemCount() {
		if (users != null)
			return users.size();
		else
			return 0;
	}
}