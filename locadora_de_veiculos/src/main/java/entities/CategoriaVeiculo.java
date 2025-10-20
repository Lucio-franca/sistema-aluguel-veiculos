package entities;

public enum CategoriaVeiculo {
    ECONOMICO(1.0, "Econômico"),      // Preço normal
    INTERMEDIARIO(1.2, "Intermediário"), // 20% mais caro
    SUV(1.5, "SUV"),                  // 50% mais caro
    LUXO(2.0, "Luxo");               // 100% mais caro


    private double mutiplicadorPreco;
    private String descricao;

    CategoriaVeiculo(double mutiplicadorPreco,String descricao){
        this.mutiplicadorPreco = mutiplicadorPreco;
        this.descricao = descricao;
    }

    public double getMutiplicadorPreco(){
        return mutiplicadorPreco;
    }

    public String getDescricao(){
        return descricao;
    }

}
