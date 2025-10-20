package entities;

public class ItemAluguel {
     private Veiculo veiculo;

     public ItemAluguel(Veiculo veiculo){
         this.veiculo = veiculo;
     }

    public Veiculo getVeiculo() {
        return veiculo;
    }

    public double getSubtotal(){
         return veiculo.getPrecoDiario();
     }

}
