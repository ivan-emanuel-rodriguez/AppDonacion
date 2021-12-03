package com.example.appdonacion.adaptadores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.appdonacion.R;
import com.example.appdonacion.entidades.DonacionesViewObject;

import java.util.ArrayList;

public class AdaptadorDonaciones extends RecyclerView.Adapter<AdaptadorDonaciones.ViewHolderDonaciones> implements View.OnClickListener {
    //Creo la lista de donaciones y su constructor
    ArrayList<DonacionesViewObject> listaDonaciones;
    private View.OnClickListener listener;
    Context context;

    public AdaptadorDonaciones(ArrayList<DonacionesViewObject> listaDonaciones, Context context) {
        this.listaDonaciones = listaDonaciones;
        this.context = context;
    }

    @NonNull
    @Override
    public AdaptadorDonaciones.ViewHolderDonaciones onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_donaciones, null, false);
        view.setOnClickListener(this);
        return new ViewHolderDonaciones(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdaptadorDonaciones.ViewHolderDonaciones holder, int position) {
        holder.etiquetaNombre.setText(listaDonaciones.get(position).getNombre());
        holder.etiquetaInformacion.setText(listaDonaciones.get(position).getInfo());
        holder.etiquetaNombreUsuario.setText(listaDonaciones.get(position).getNombreUsuario());
        holder.etiquetaUbiUsuario.setText(listaDonaciones.get(position).getNombreUbi());
//        holder.foto.setImageResource(listaDonaciones.get(position).getImagenId());

        if (!listaDonaciones.get(position).getUrlImagen().equals("")) {
            Glide.with(context)
                    .load(String.valueOf(listaDonaciones.get(position).getUrlImagen()))
                    .fitCenter()
                    .centerCrop()
                    .into(holder.foto);
        }
    }

    @Override
    public int getItemCount() {
        //retorno los elementos segun el tamaño de la lista
        return listaDonaciones.size();
    }

    public void setOnClickListener(View.OnClickListener listener) {
        this.listener = listener;

    }

    @Override
    public void onClick(View view) {
        if (listener != null) {
            listener.onClick(view);
        }

    }

    public class ViewHolderDonaciones extends RecyclerView.ViewHolder {

        TextView etiquetaNombre, etiquetaInformacion, etiquetaNombreUsuario, etiquetaUbiUsuario ;
        ImageView foto;

        public ViewHolderDonaciones(@NonNull View itemView) {
            super(itemView);
            etiquetaNombre = (TextView) itemView.findViewById(R.id.idNombre);
            etiquetaInformacion = (TextView) itemView.findViewById(R.id.idInfo);
            etiquetaNombreUsuario = (TextView) itemView.findViewById(R.id.nombreUsuario);
            etiquetaUbiUsuario = (TextView) itemView.findViewById(R.id.nombreUbi);
            foto = (ImageView) itemView.findViewById(R.id.idImagen);
        }
    }
}
