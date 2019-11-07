package com.example.ejerciciopokemon;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
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

import java.util.List;

public class activity_edit_delete extends AppCompatActivity {

    boolean picked = false;
    String imageIDE;
    private static final int PHOTO_SELECTED = 1;
    Intent intent;
    MainViewModel viewModel;
    EditText etNameED;
    ImageView ivPickED;
    Spinner spTypeED;
    Button btEdit, btDelete, btSelectED;
    Pokemon pokemon, pokemonEdited;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_delete);
        intent = getIntent();


        initComponents();


    }

    private void initComponents() {
        int posicion;
        etNameED = findViewById(R.id.etNameED);
        spTypeED = findViewById(R.id.sp_typesED);
        ivPickED = findViewById(R.id.ivPickED);

        btEdit = findViewById(R.id.btEditED);
        btDelete = findViewById(R.id.btDeleteED);
        btSelectED = findViewById(R.id.btSeleccionarED);

        viewModel = ViewModelProviders.of(this).get(MainViewModel.class);

        pokemon = intent.getParcelableExtra("pokemonID");
            Uri imageID = Uri.parse(pokemon.getImageID());

            Glide.with(this)
                    .load(imageID)
                    .override(500, 500)
                    .into(ivPickED);
            etNameED.setText(pokemon.getName());
            posicion = setSpinner();
            if(posicion == -1){
                spTypeED.setSelection(0);
            }else{
                spTypeED.setSelection(posicion);
            }
            imageIDE = pokemon.getImageID();




        InitEvents();


    }

    private void InitEvents() {
        btSelectED.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                seleccionarImagen();
            }
        });

        btEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editarPokemon();
                Intent itEdit = new Intent(activity_edit_delete.this, MainActivity.class);
                startActivity(itEdit);
            }
        });

        btDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewModel.delete(pokemon);
                Intent itdelete = new Intent(activity_edit_delete.this, MainActivity.class);
                startActivity(itdelete);
            }
        });
    }

    private void editarPokemon() {
        pokemonEdited = pokemon;
        String nameE, typeE;
        nameE = etNameED.getText().toString();
        typeE = spTypeED.getSelectedItem().toString();

        if(picked){
            pokemonEdited.setImageID(imageIDE);
            pokemonEdited.setName(nameE);
            pokemonEdited.setType(typeE);
            viewModel.edit(pokemonEdited);
        }else{
            pokemonEdited.setName(nameE);
            pokemonEdited.setType(typeE);
            viewModel.edit(pokemonEdited);
        }

    }

    private void seleccionarImagen() {
        Intent f = new Intent(Intent.ACTION_GET_CONTENT, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(f, PHOTO_SELECTED);
        picked = true;

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
                    .into(ivPickED);
            imageIDE = imageUri.toString();
        }
    }

    private int setSpinner(){
        String type = pokemon.getType();
        for (int i = 0; i < 18; i++){
            spTypeED.setSelection(i);
            if(spTypeED.getSelectedItem().toString().compareTo(type) == 0){
                return i;
            }
        }
        return -1;
    }
}
