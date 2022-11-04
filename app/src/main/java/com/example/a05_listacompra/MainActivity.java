package com.example.a05_listacompra;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import com.example.a05_listacompra.modelos.Producto;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;


import com.example.a05_listacompra.databinding.ActivityMainBinding;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private ArrayList<Producto> productos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.toolbar);

        productos = new ArrayList<>();


        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               createProducto().show();
            }
        });
    }

    private AlertDialog createProducto() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Nuevo producto");
        builder.setCancelable(false);

        // Necesitamos un conenido
        View productoView = LayoutInflater.from(this).inflate(R.layout.producto_view_alert, null);
        EditText txtNombre = productoView.findViewById(R.id.txtNombreProductoAlert);
        EditText txtCantidad = productoView.findViewById(R.id.txtCantidadProductoAlert);
        EditText txtPrecio = productoView.findViewById(R.id.txtPrecioProductoAlert);
        TextView lblTotal = productoView.findViewById(R.id.lblTotalProductoAlert);
        builder.setView(productoView);

        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(android.text.Editable s) {
                String nombre = txtNombre.getText().toString();
                String cantidad = txtCantidad.getText().toString();
                String precio = txtPrecio.getText().toString();

                if (nombre.isEmpty() || cantidad.isEmpty() || precio.isEmpty()) {
                    lblTotal.setText("0.0");
                } else {
                    float total = Integer.parseInt(cantidad) * Float.parseFloat(precio);
                    lblTotal.setText(String.valueOf(total));
                }
            }
        };

        txtCantidad.addTextChangedListener(textWatcher);
        txtPrecio.addTextChangedListener(textWatcher);

        builder.setPositiveButton("Guardar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (txtNombre.getText().toString().isEmpty() || txtCantidad.getText().toString().isEmpty() || txtPrecio.getText().toString().isEmpty()) {
                    Producto producto = new Producto(txtNombre.getText().toString(),
                            Integer.parseInt(txtCantidad.getText().toString()),
                            Float.parseFloat(txtPrecio.getText().toString()));
                    productos.add(producto);
                } else {
                    Snackbar.make(binding.getRoot(), "No puede haber campos vacios", Snackbar.LENGTH_LONG).show();


                    Snackbar.make(binding.getRoot(), "Producto guardado", Snackbar.LENGTH_LONG).show();
                }
            }
        });
        builder.setNegativeButton("Cancelar", null);


        return builder.create();
    }

}