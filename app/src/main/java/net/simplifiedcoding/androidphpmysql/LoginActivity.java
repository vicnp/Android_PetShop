package net.simplifiedcoding.androidphpmysql;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText editTextUsername, editTextPassword;
    private Button buttonLogin;
    private Button buttonRecover;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try
        {
            this.getSupportActionBar().hide();
        }
        catch (NullPointerException e){

        }

        setContentView(R.layout.activity_login);

        if(SharedPrefManager.getInstance(this).isLoggedIn()){
            finish();
            startActivity(new Intent(this, ProfileActivity.class));
            return;
        }

        editTextUsername = (EditText) findViewById(R.id.editTextUsername);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        buttonLogin = (Button) findViewById(R.id.buttonLogin);
        buttonRecover = (Button) findViewById(R.id.button_recover);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Aguarde...");

        buttonLogin.setOnClickListener(this);
        buttonRecover.setOnClickListener(this);
    }

    private void userLogin(){
        final String username = editTextUsername.getText().toString().trim();
        final String password = editTextPassword.getText().toString().trim();

        progressDialog.show();

        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                Constants.URL_LOGIN,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        try {
                            JSONObject obj = new JSONObject(response);
                            if(!obj.getBoolean("error")){

                                ArrayList<Pet> user_pets = new ArrayList<>();

                                JSONArray ja_data = obj.getJSONArray("pets");
                                int length = ja_data.length();

                                for(int i=0; i<length; i++)
                                {

                                    JSONObject jObj = ja_data.getJSONObject(i);
                                    Pet pet_i = new Pet(jObj.getString("petname").toString(),
                                            jObj.getString("raca").toString(),
                                            Float.parseFloat(jObj.getString("peso").toString()),
                                            jObj.getString("ano_nascimento").toString());
                                    user_pets.add(pet_i);
                                }

                                User user = new User(
                                        Integer.parseInt(obj.getString("id")),
                                        obj.getString("nome"),
                                        obj.getString("sobrenome"),
                                        obj.getString("email"),
                                        obj.getString("username"),
                                        obj.getString("cpf"),
                                        obj.getString("cidade"),
                                        obj.getString("bairro"),
                                        obj.getString("rua"),
                                        obj.getString("cep"),
                                        Integer.toString(obj.getInt("numero")),
                                        obj.getString("telefoneUm"),
                                        obj.getString("telefoneDois"),
                                        user_pets
                                );


                                SharedPrefManager.getInstance(getApplicationContext())
                                        .userLogin(user);
                                Intent intent = new Intent(getApplicationContext(), ProfileActivity.class);
                                intent.putExtra("PetArray", user_pets);

                                startActivity(intent);
                                finish();

                            }else{
                                Toast.makeText(
                                        getApplicationContext(),
                                        obj.getString("message"),
                                        Toast.LENGTH_LONG
                                ).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();

                        Toast.makeText(
                                getApplicationContext(),
                               error.getMessage(),
                                Toast.LENGTH_LONG
                        ).show();
                    }
                }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("username", username);
                params.put("password", password);
                return params;
            }

        };

        RequestHandler.getInstance(this).addToRequestQueue(stringRequest);
    }

    @Override
    public void onClick(View view) {
        if(view == buttonLogin){
            userLogin();
        }else if(view == buttonRecover){
            userRecover();
        }
    }

    private void userRecover() {
        startActivity(new Intent(getApplicationContext(), Send_Recovery_Email.class));
    }
}
