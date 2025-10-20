package repositories;
import entities.Veiculo;
import java.util.ArrayList;
import java.util.List;

public class VeiculoRepository {

    private List<Veiculo> veiculos = new ArrayList<>();
    private int nextId = 1;

    public void salvar(Veiculo veiculo){
        if(veiculo.getId()==0){
            veiculo.setId(nextId++);
        }
        veiculos.add(veiculo);
    }

    public List<Veiculo> listarTodos() {
        return new ArrayList<>(veiculos);
    }

    public List<Veiculo> listarDisponiveis() {
        List<Veiculo> disponiveis = new ArrayList<>();
        for (Veiculo veiculo : veiculos) {
            if (veiculo.isDisponivel()) {
                disponiveis.add(veiculo);
            }
        }
        return disponiveis;
    }

    public Veiculo buscarPorId(int id) {
        for (Veiculo veiculo : veiculos) {
            if (veiculo.getId() == id) {
                return veiculo;
            }
        }
        return null;
    }
}
