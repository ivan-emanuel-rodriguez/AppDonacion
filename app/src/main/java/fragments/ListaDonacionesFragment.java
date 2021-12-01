package fragments;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.appdonacion.DonacionSharePreferences;
import com.example.appdonacion.InterfaceComunicaFragments;
import com.example.appdonacion.R;

import java.util.ArrayList;

import adaptadores.AdaptadorDonaciones;
import entidades.DonacionesViewObject;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ListaDonacionesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ListaDonacionesFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    ArrayList<DonacionesViewObject> listaDonaciones;
    RecyclerView recyclerDonaciones;

    Activity activity;
    InterfaceComunicaFragments interfaceComunicaFragments;

    public ListaDonacionesFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ListaDonacionesFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ListaDonacionesFragment newInstance(String param1, String param2) {
        ListaDonacionesFragment fragment = new ListaDonacionesFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View vista= inflater.inflate(R.layout.fragment_lista_donaciones, container, false);
        listaDonaciones=new ArrayList<>();
        recyclerDonaciones=vista.findViewById(R.id.recyclerId);
        recyclerDonaciones.setLayoutManager(new LinearLayoutManager(getContext()));

        //metodo para cargar la lista de donaciones
        llenarDonaciones();

        AdaptadorDonaciones adapter=new AdaptadorDonaciones(listaDonaciones);
        recyclerDonaciones.setAdapter(adapter);

        adapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Obtengo el nombre del objeto donado
                Toast.makeText(getContext(), "Selecciona: "
                        +listaDonaciones.get(recyclerDonaciones
                        .getChildAdapterPosition(view)).getNombre(), Toast.LENGTH_SHORT).show();
                //Obtengo el objeto donado y lo envio al reclyclerview
                interfaceComunicaFragments.enviarDonacion(listaDonaciones.get(recyclerDonaciones.getChildAdapterPosition(view)));
            }
        });

        TextView tituloTextView= vista.findViewById(R.id.textUsuarioId);
        tituloTextView.setText(DonacionSharePreferences.getUsuario(getContext()));

        return vista;
    }

    private void llenarDonaciones(){
        listaDonaciones.add(new DonacionesViewObject(getString(R.string.aceite),getString(R.string.aceiteInfo),getString(R.string.aceiteDesc),R.drawable.aceite, R.drawable.aceite));
        listaDonaciones.add(new DonacionesViewObject(getString(R.string.arroz),getString(R.string.arrozInfo),getString(R.string.arrozDesc),R.drawable.arroz, R.drawable.arroz));
        listaDonaciones.add(new DonacionesViewObject(getString(R.string.fideo),getString(R.string.fideoInfo),getString(R.string.fideoDesc),R.drawable.fideo, R.drawable.fideo));
        listaDonaciones.add(new DonacionesViewObject(getString(R.string.lenteja),getString(R.string.lentejaInfo),getString(R.string.lentejaDesc),R.drawable.lentejas, R.drawable.lentejas));
        listaDonaciones.add(new DonacionesViewObject(getString(R.string.polenta),getString(R.string.polentaInfo),getString(R.string.polentaDesc),R.drawable.polenta, R.drawable.polenta));
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        //Consulto si el contexto es instancia de una activity y luego llamo ese contexto a mi interface
        if(context instanceof Activity ){
            this.activity= (Activity) context;
            interfaceComunicaFragments= (InterfaceComunicaFragments) this.activity;
        }
    }

}