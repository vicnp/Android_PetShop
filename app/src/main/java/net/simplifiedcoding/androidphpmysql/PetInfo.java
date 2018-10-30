package net.simplifiedcoding.androidphpmysql;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class PetInfo extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pet_info);

        TextView nome = (TextView) findViewById(R.id.txtNomePet);
        TextView raca = (TextView) findViewById(R.id.txtRacaPet);
        TextView sexo = (TextView) findViewById(R.id.txtSexoPet);
        TextView nasci = (TextView) findViewById(R.id.txtIdadePet);


        nome.setText(getIntent().getStringExtra("Nome"));
        raca.setText(getIntent().getStringExtra("Raca"));
        nasci.setText(getIntent().getStringExtra("Nascimento"));
        sexo.setText("TODO");
    }
}
