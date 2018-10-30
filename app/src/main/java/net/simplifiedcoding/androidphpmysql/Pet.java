package net.simplifiedcoding.androidphpmysql;

import android.os.Parcel;
import android.os.Parcelable;

public class Pet implements Parcelable{
    String petnome;
    String raca;
    float peso;
    String ano_nascimento;

    public Pet(String petnome, String raca, float peso, String ano_nascimento) {
        this.petnome = petnome;
        this.raca = raca;
        this.peso = peso;
        this.ano_nascimento = ano_nascimento;
    }

    protected Pet(Parcel in) {
        petnome = in.readString();
        raca = in.readString();
        peso = in.readFloat();
        ano_nascimento = in.readString();
    }

    public static final Creator<Pet> CREATOR = new Creator<Pet>() {
        @Override
        public Pet createFromParcel(Parcel in) {
            return new Pet(in);
        }

        @Override
        public Pet[] newArray(int size) {
            return new Pet[size];
        }
    };

    public String getPetnome() {
        return petnome;
    }

    public void setPetnome(String petnome) {
        this.petnome = petnome;
    }

    public String getRaca() {
        return raca;
    }

    public void setRaca(String raca) {
        this.raca = raca;
    }

    public float getPeso() {
        return peso;
    }

    public void setPeso(float peso) {
        this.peso = peso;
    }

    public String getAno_nascimento() {
        return ano_nascimento;
    }

    public void setAno_nascimento(String ano_nascimento) {
        this.ano_nascimento = ano_nascimento;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(petnome);
        dest.writeString(raca);
        dest.writeFloat(peso);
        dest.writeString(ano_nascimento);
    }
}
