package net.simplifiedcoding.androidphpmysql;

public class Pet {
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
}
