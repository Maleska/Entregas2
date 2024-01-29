package com.consulsoftware.entregas.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.consulsoftware.entregas.R;

import java.util.ArrayList;

public class docAdapter extends ArrayAdapter<ListAdapter> {
    private Context context;
    private ArrayList<ListAdapter> datos;

    public docAdapter(Context context, ArrayList<ListAdapter> datos){
        super(context, R.layout.adapter_lista,datos);

        this.context = context;
        this.datos = datos;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        final View item = LayoutInflater.from(context).inflate(
                R.layout.adapter_lista,null);

        TextView txnumpedido = item.findViewById(R.id.tvNumPedido);
        TextView nombre = item.findViewById(R.id.tvNombre);
        TextView total = item.findViewById(R.id.tvTotal);
        TextView docentry = item.findViewById(R.id.tvDocEntry);
        TextView un = item.findViewById(R.id.tv_UN);

        txnumpedido.setText(datos.get(position).getNumPedido());
        nombre.setText(datos.get(position).getNombre());
        total.setText(datos.get(position).getTotal());
        docentry.setText(datos.get(position).getDocEntry());
        un.setText(datos.get(position).getUN());
        return  item;

    }
}
