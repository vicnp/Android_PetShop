package net.simplifiedcoding.androidphpmysql;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

public class ProfileActivity extends AppCompatActivity {


    private TextView textViewUsername, textViewUserEmail, textViewNomeUsuario, txtViewSobreNome, textViewCPF;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        if(!SharedPrefManager.getInstance(this).isLoggedIn()){
            finish();
            startActivity(new Intent(this, LoginActivity.class));
        }

        textViewUsername = (TextView) findViewById(R.id.textViewUsername);
        textViewUserEmail = (TextView) findViewById(R.id.textViewUseremail);
        textViewNomeUsuario = (TextView) findViewById(R.id.txtUser_Nome);
        txtViewSobreNome = (TextView) findViewById(R.id.txtSobrenome);
        textViewCPF = (TextView) findViewById(R.id.txtCpf);


        textViewCPF.setText(SharedPrefManager.getInstance(this).getUser_CPF());
        txtViewSobreNome.setText(SharedPrefManager.getInstance(this).getUser_SobreNome());
        textViewNomeUsuario.setText(SharedPrefManager.getInstance(this).getUser_Name());
        textViewUserEmail.setText(SharedPrefManager.getInstance(this).getUserEmail());
        textViewUsername.setText(SharedPrefManager.getInstance(this).getUsername());


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
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
