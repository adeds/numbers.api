package com.noesdev.ade.numbers.adapter;


import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.noesdev.ade.numbers.R;
import com.noesdev.ade.numbers.db.FavHelper;
import com.noesdev.ade.numbers.fragment.InfoFragment;
import com.noesdev.ade.numbers.model.favoritez;

import static android.provider.BaseColumns._ID;
import static com.noesdev.ade.numbers.db.DBContract.CONTENT_URI;
import static com.noesdev.ade.numbers.db.DBContract.FavKolom.ID_NOMOR;

public class FavAdapter extends RecyclerView.Adapter<FavAdapter.Holder> {
    private Context context;
    private Cursor listFav;
    private favoritez model;
    private FavHelper favHelper;
   private int idKey;
    InfoFragment infoFragment;
    public FavAdapter(Context context) {
        this.context = context;
        infoFragment = new InfoFragment();
    }

    public void setListNotes(Cursor listFav) {
        this.listFav = listFav;
    }

    private favoritez getItem(int position){
        if (!listFav.moveToPosition(position)) {
            throw new IllegalStateException("Position invalid");
        }
        return new favoritez(listFav);
    }
    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fav_item, parent, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        final favoritez model = getItem(position);
        holder.tv_desc_fav.setText(model.getDesc());
        holder.ibt_clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            // deletFav(model.getId());
                AlertDialog.Builder builder;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    builder = new AlertDialog.Builder(context, android.R.style.Theme_Material_Dialog_Alert);
                } else {
                    builder = new AlertDialog.Builder(context);
                }
                builder.setTitle("Delete entry")
                        .setMessage("Are you sure you want to delete this entry?")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                deletFav(model.getId());
                            }
                        })
                        .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // do nothing
                            }
                        })
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();

            }
        });
    }

    private void deletFav(int id) {
        loadFav(id);
        Uri uri = Uri.parse(CONTENT_URI+"/"+idKey);
     //   getContentResolver().delete(uri, null,null);
context.getContentResolver().delete(uri, null, null);




    }

    private void loadFav(int id) {
        favHelper = new FavHelper(context);
        favHelper.open();
        Cursor cursor = favHelper.queryByIdProvider(String.valueOf(id));
        Log.e("data", String.valueOf(cursor.getCount()));
        if (cursor.getCount() == 1 ){
//            while (cursor.moveToFirst()){
            cursor.moveToFirst();
            idKey = cursor.getInt(cursor.getColumnIndex(ID_NOMOR));
            Log.e("_ID ", String.valueOf(idKey));

        }
        favHelper.close();
    }

    @Override
    public int getItemCount() {
        if (listFav == null) return 0;
        return listFav.getCount();
    }


    class Holder extends RecyclerView.ViewHolder {
        TextView tv_desc_fav;
        ImageButton ibt_clear;
        public Holder(View itemView) {
            super(itemView);
            tv_desc_fav = itemView.findViewById(R.id.tv_fav_desc);
            ibt_clear = itemView.findViewById(R.id.ibt_clear);
        }
    }
}
