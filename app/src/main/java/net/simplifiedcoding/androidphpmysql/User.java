package net.simplifiedcoding.androidphpmysql;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.ArrayList;

public class User  implements Parcelable{
    int id;
    String nome;
    String sobrenome;
    String email;
    String username;
    String  cpf;
    String cidade;
    String bairro;
    String rua;
    String cep;
    String numero;
    String telefoneUm;
    String TelefoneDois;
    ArrayList<Pet> pets;

    public User(int id, String nome, String sobrenome, String email, String username, String cpf, String cidade, String bairro, String rua, String cep, String numero, String telefoneUm, String getTelefoneDois, ArrayList<Pet> pets) {
        this.id = id;
        this.nome = nome;
        this.sobrenome = sobrenome;
        this.email = email;
        this.username = username;
        this.cpf = cpf;
        this.cidade = cidade;
        this.bairro = bairro;
        this.rua = rua;
        this.cep = cep;
        this.numero = numero;
        this.telefoneUm = telefoneUm;
        this.TelefoneDois = getTelefoneDois;
        this.pets = pets;
    }

    protected User(Parcel in) {
        id = in.readInt();
        nome = in.readString();
        sobrenome = in.readString();
        email = in.readString();
        username = in.readString();
        cpf = in.readString();
        cidade = in.readString();
        bairro = in.readString();
        rua = in.readString();
        cep = in.readString();
        numero = in.readString();
        telefoneUm = in.readString();
        TelefoneDois = in.readString();
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSobrenome() {
        return sobrenome;
    }

    public void setSobrenome(String sobrenome) {
        this.sobrenome = sobrenome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getRua() {
        return rua;
    }

    public void setRua(String rua) {
        this.rua = rua;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getTelefoneUm() {
        return telefoneUm;
    }

    public void setTelefoneUm(String telefoneUm) {
        this.telefoneUm = telefoneUm;
    }

    public String getTelefoneDois() {
        return TelefoneDois;
    }

    public void setTelefoneDois(String getTelefoneDois) {
        this.TelefoneDois = getTelefoneDois;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(nome);
        dest.writeString(sobrenome);
        dest.writeString(email);
        dest.writeString(username);
        dest.writeString(cpf);
        dest.writeString(cidade);
        dest.writeString(bairro);
        dest.writeString(rua);
        dest.writeString(cep);
        dest.writeString(numero);
        dest.writeString(telefoneUm);
        dest.writeString(TelefoneDois);
    }
}
