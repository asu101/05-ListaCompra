package com.example.a05_listacompra.adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.a05_listacompra.R;
import com.example.a05_listacompra.modelos.Producto;
import com.google.android.material.snackbar.Snackbar;

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
        View productoView = LayoutInflater.from(context).inflate(resource, parent, false);
        RecyclerView.LayoutParams layoutParams = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        productoView.setLayoutParams(layoutParams);
        return new ProductoVH(productoView);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductoVH holder, int position) {
        Producto producto = objects.get(position);
        holder.lblProducto.setText(producto.getNombre());
        holder.txtCantidad.setText(String.valueOf(producto.getCantidad()));

        holder.btnEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                confirmDelete(producto, holder.getAdapterPosition()).show();
            }
        });

        holder.txtCantidad.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                int cantidad;

                try {
                    cantidad = Integer.parseInt(s.toString());
                } catch (NumberFormatException ex) {
                    cantidad = 0;
                }
                producto.setCantidad(cantidad);
                producto.updateTotal();
            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // AlertDialog con todos los campos a Editar
                // Necesita el producto
                // Necesita la Posición
                editProducto(producto, holder.getAdapterPosition()).show();
            }
        });
    }

    private AlertDialog confirmDelete(Producto producto, int adapterPosition) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Confirmar eliminación");
        builder.setCancelable(false);
        TextView mensaje = new TextView(context);
        mensaje.setText("¿Está seguro de eliminar el producto " + producto.getNombre() + "?");
        mensaje.setTextSize(24);
        mensaje.setTextColor(Color.RED);
        mensaje.setPadding(100,100,100,100);
        builder.setView(mensaje);

        builder.setNegativeButton("CANCELAR", null);
        builder.setPositiveButton("ELIMINAR", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                objects.remove(producto);
                notifyItemRemoved(adapterPosition);
            }
        });



        return builder.create();
    }

    private AlertDialog editProducto(Producto producto, int adapterPosition) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        builder.setTitle("Editar Producto");
        builder.setCancelable(false);

        // Necesitamos un conenido
        View productoView = LayoutInflater.from(context).inflate(R.layout.producto_view_alert, null);
        EditText txtNombre = productoView.findViewById(R.id.txtNombreProductoAlert);
        EditText txtCantidad = productoView.findViewById(R.id.txtCantidadProductoAlert);
        EditText txtPrecio = productoView.findViewById(R.id.txtPrecioProductoAlert);
        TextView lblTotal = productoView.findViewById(R.id.lblTotalProductoAlert);
        builder.setView(productoView);

        txtNombre.setText(producto.getNombre());
        txtCantidad.setText(String.valueOf(producto.getCantidad()));
        txtPrecio.setText(String.valueOf(producto.getPrecio()));
        lblTotal.setText(String.valueOf(producto.getTotal()));

        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(android.text.Editable s) {
                try {
                    String nombre = txtNombre.getText().toString();
                    String cantidad = txtCantidad.getText().toString();
                    String precio = txtPrecio.getText().toString();

                    if (nombre.isEmpty() || cantidad.isEmpty() || precio.isEmpty()) {
                        lblTotal.setText("0.0");
                    } else {
                        float total = Integer.parseInt(cantidad) * Float.parseFloat(precio);
                        lblTotal.setText(String.valueOf(total));
                    }
                }catch (NumberFormatException ex) {

                }

            }
        };

        txtCantidad.addTextChangedListener(textWatcher);
        txtPrecio.addTextChangedListener(textWatcher);

        builder.setPositiveButton("Guardar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (!txtNombre.getText().toString().isEmpty() && !txtCantidad.getText().toString().isEmpty() && !txtPrecio.getText().toString().isEmpty()) {
                    producto.setNombre(txtNombre.getText().toString());
                    producto.setCantidad(Integer.parseInt(txtCantidad.getText().toString()));
                    producto.setPrecio(Float.parseFloat(txtPrecio.getText().toString()));
                    producto.updateTotal();

                    notifyItemChanged(adapterPosition);
                } else {
                    Toast.makeText(context, "Debe llenar todos los campos", Toast.LENGTH_SHORT).show();

                }
            }
        });
        builder.setNegativeButton("Cancelar", null);


        return builder.create();
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

