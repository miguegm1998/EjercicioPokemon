package com.example.ejerciciopokemon;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.ejerciciopokemon.entity.Pokemon;
import com.example.ejerciciopokemon.view.MainViewModel;

public class insertar_nuevo extends AppCompatActivity {

    boolean imagePicked = false;
    String imagen;
    private static final int PHOTO_SELECTED = 1;
    EditText etName;
    ImageView ivPick;
    Spinner sp_Types;
    Button btSelect, btInsert;
    Pokemon pokemon;
    private MainViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insertar_nuevo);
        Intent i = getIntent();

        initComponents();
    }

    private void initComponents() {
        etName = findViewById(R.id.etName);
        sp_Types = findViewById(R.id.sp_types);
        ivPick = findViewById(R.id.ivPick);
        btInsert = findViewById(R.id.btInsert);
        btSelect = findViewById(R.id.btSeleccionar);

        pokemon = new Pokemon();

        viewModel = ViewModelProviders.of(this).get(MainViewModel.class);

        initEvents();
    }

    private void initEvents() {
        btInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(imagePicked == true) {
                    insertarPokemon();

                }else {
                    Toast.makeText(insertar_nuevo.this, R.string.err_selected, Toast.LENGTH_LONG).show();
                }
            }
        });

        btSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                seleccionarImagen();
            }
        });

    }

    private void insertarPokemon() {
        String name, type;

        name = etName.getText().toString();
        type = sp_Types.getSelectedItem().toString();


        pokemon.setName(name);
        pokemon.setType(type);
        pokemon.setImageID(imagen);

        viewModel.insert(pokemon);

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);

    }

    private void seleccionarImagen() {
        Intent f = new Intent(Intent.ACTION_GET_CONTENT, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(f, PHOTO_SELECTED);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PHOTO_SELECTED && resultCode == RESULT_OK &&
                data != null && data.getData() != null) {
            Uri imageUri = data.getData();


            Glide.with(this)
                    .load(imageUri)
                    .override(500, 500)// prueba de escalado
                    .into(ivPick);
            imagen = imageUri.toString();
            imagePicked = true;
        }
    }
}
