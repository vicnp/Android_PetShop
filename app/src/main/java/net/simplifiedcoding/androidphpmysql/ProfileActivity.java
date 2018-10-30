package net.simplifiedcoding.androidphpmysql;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class ProfileActivity extends AppCompatActivity {


    private TextView textViewUsername, textViewUserEmail, textViewNomeUsuario, txtViewSobreNome, textViewCPF;
    private ListView listView;
    ArrayList<Pet> pets;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        if(!SharedPrefManager.getInstance(this).isLoggedIn()){
            finish();
            startActivity(new Intent(this, LoginActivity.class));
        }
        //getIntent().getParcelableArrayListExtra("PetArray");
        pets = (ArrayList<Pet>) getIntent().getSerializableExtra("PetArray");

        final List<String> list = new ArrayList<String>();
        if(pets != null){
            for (Pet pet:pets) {
               list.add(pet.getPetnome());
            }
        }

        listView = (ListView) findViewById(R.id.listview);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,list);

        listView.setAdapter(adapter);
         listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
             @Override
             public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                 String petname = (String) listView.getItemAtPosition(position);
                 for (Pet pet:pets) {
                    if(pet.getPetnome().equals(petname)){
                        Intent intent = new Intent(getApplicationContext(), PetInfo.class);
                        intent.putExtra("Nome", pet.getPetnome());
                        intent.putExtra("Raca", pet.getRaca());
                        intent.putExtra("Nascimento", pet.getAno_nascimento());
                        startActivity(intent);
                    }
                 }
             }
         });





        instatiateUserItens();

        setData();
    }

    private void instatiatePetItens() {
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }
    private void instatiateUserItens(){
        textViewUsername = (TextView) findViewById(R.id.textViewUsername);
        textViewUserEmail = (TextView) findViewById(R.id.textViewUseremail);
        textViewNomeUsuario = (TextView) findViewById(R.id.txtUser_Nome);
        txtViewSobreNome = (TextView) findViewById(R.id.txtSobrenome);
        textViewCPF = (TextView) findViewById(R.id.txtCpf);
    }
    private void setData(){
        textViewCPF.setText(SharedPrefManager.getInstance(this).getUser_CPF());
        txtViewSobreNome.setText(SharedPrefManager.getInstance(this).getUser_SobreNome());
        textViewNomeUsuario.setText(SharedPrefManager.getInstance(this).getUser_Name());
        textViewUserEmail.setText(SharedPrefManager.getInstance(this).getUserEmail());
        textViewUsername.setText(SharedPrefManager.getInstance(this).getUsername());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.menuLogout:
                SharedPrefManager.getInstance(this).logout();
                finish();
                startActivity(new Intent(this, LoginActivity.class));
                break;

        }
        return true;
    }
}
