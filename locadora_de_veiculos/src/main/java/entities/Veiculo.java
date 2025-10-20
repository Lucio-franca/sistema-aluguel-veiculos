package entities;

public class Veiculo {

    private int id;
    private String modelo;
    private String placa;
    private double precoBaseDiaria;
    private CategoriaVeiculo categoria;
    private boolean disponivel;
    private int totalAlugueis;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public double getPrecoBaseDiaria() {
        return precoBaseDiaria;
    }

    public void setPrecoBaseDiaria(double precoBaseDiaria) {
        this.precoBaseDiaria = precoBaseDiaria;
    }

    public CategoriaVeiculo getCategoria() {
        return categoria;
    }

    public void setCategoria(CategoriaVeiculo categoria) {
        this.categoria = categoria;
    }

    public boolean isDisponivel() {
        return disponivel;
    }

    public void setDisponivel(boolean disponivel) {
        this.disponivel = disponivel;
    }

    public int getTotalAlugueis() {
        return totalAlugueis;
    }

    public void setTotalAlugueis(int totalAlugueis) {
        this.totalAlugueis = totalAlugueis;
    }

    public Veiculo(String modelo, String placa, double precoBaseDiaria, CategoriaVeiculo categoria){
        this.modelo = modelo;
        this.placa = placa;
        this.precoBaseDiaria = precoBaseDiaria;
        this.categoria = categoria;
        this.disponivel = true;
        this.totalAlugueis = 0;
    }
    // Calcula preço COM a categoria
    public double getPrecoDiario(){
        return precoBaseDiaria * categoria.getMutiplicadorPreco();
    }
    // Incrementa quando é alugado
    public void incrementarAluguel(){
        this.totalAlugueis++;
    }





}
