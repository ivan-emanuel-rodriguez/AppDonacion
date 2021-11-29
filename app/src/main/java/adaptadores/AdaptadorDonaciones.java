package adaptadores;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appdonacion.R;

import org.w3c.dom.Text;

import java.util.ArrayList;

import entidades.DonacionesViewObject;

public class AdaptadorDonaciones extends RecyclerView.Adapter<AdaptadorDonaciones.ViewHolderDonaciones> implements View.OnClickListener{
    //Creo la lista de donaciones y su constructor
    ArrayList<DonacionesViewObject> listaDonaciones;
    private View.OnClickListener listener;

    public AdaptadorDonaciones(ArrayList<DonacionesViewObject> listaDonaciones) {
        this.listaDonaciones = listaDonaciones;
    }

    @NonNull
    @Override
    public AdaptadorDonaciones.ViewHolderDonaciones onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_donaciones, null, false);
        view.setOnClickListener(this);
        return new ViewHolderDonaciones(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdaptadorDonaciones.ViewHolderDonaciones holder, int position) {
        holder.etiquetaNombre.setText(listaDonaciones.get(position).getNombre());
        holder.etiquetaInformacion.setText(listaDonaciones.get(position).getInfo());
        holder.foto.setImageResource(listaDonaciones.get(position).getImagenId());


    }

    @Override
    public int getItemCount() {
        //retorno los elementos segun el tamaño de la lista
        return listaDonaciones.size();
    }

    public void setOnClickListener (View.OnClickListener listener){
        this.listener=listener;

    }

    @Override
    public void onClick(View view) {
        if(listener!=null){
            listener.onClick(view);
        }

    }

    public class ViewHolderDonaciones extends RecyclerView.ViewHolder {

        TextView etiquetaNombre, etiquetaInformacion;
        ImageView foto;
        public ViewHolderDonaciones(@NonNull View itemView) {
            super(itemView);
            etiquetaNombre=(TextView) itemView.findViewById(R.id.idNombre);
            etiquetaInformacion=(TextView) itemView.findViewById(R.id.idInfo);
            foto=(ImageView) itemView.findViewById(R.id.idImagen);
        }
    }
}
