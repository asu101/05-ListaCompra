package com.example.a05_listacompra.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.a05_listacompra.R;
import com.example.a05_listacompra.modelos.Producto;

import java.util.List;

public class ProductosAdapter extends RecyclerView.Adapter<ProductosAdapter.ProductoVH> {

    private List<Producto> objects;
    private int resource;
    private Context context;

    public ProductosAdapter(List<Producto> objects, int resource, Context context) {
        this.objects = objects;
        this.resource = resource;
        this.context = context;
    }

    @NonNull
    @Override
    public ProductoVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
        //View view = LayoutInflater.from(context).inflate(resource, parent, false);
        //return new ProductoVH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductoVH holder, int position) {
        //Producto producto = objects.get(position);
        //holder.txtNombre.setText(producto.getNombre());
        //holder.txtCantidad.setText(String.valueOf(producto.getCantidad()));
        //holder.txtPrecio.setText(String.valueOf(producto.getPrecio()));
        //holder.txtTotal.setText(String.valueOf(producto.getTotal()));
    }

    @Override
    public int getItemCount() {
        return objects.size();
    }


    public class ProductoVH extends RecyclerView.ViewHolder {

        TextView lblProducto;
        TextView txtCantidad;
        ImageButton btnEliminar;

        public ProductoVH(@NonNull View itemView) {
            super(itemView);
            lblProducto = itemView.findViewById(R.id.lblNombreProductoViewHolder);
            txtCantidad = itemView.findViewById(R.id.txtCantidadProductoViewHolder);
            btnEliminar = itemView.findViewById(R.id.btnEliminarProductoViewHolder);
        }
    }

}

