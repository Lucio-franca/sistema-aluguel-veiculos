package services;
import entities.CategoriaVeiculo;
import repositories.VeiculoRepository;
import entities.Veiculo;
import java.util.List;

public class VeiculoService {


    private VeiculoRepository veiculoRepository;

    public VeiculoService(){
        this.veiculoRepository = new VeiculoRepository();
        inicializarVeiculos();
    }

    private void inicializarVeiculos(){
        veiculoRepository.salvar(new Veiculo("Fiat Uno", "ABC-1234", 50.00, CategoriaVeiculo.ECONOMICO));
        veiculoRepository.salvar(new Veiculo("VW Gol", "DEF-5678", 60.00, CategoriaVeiculo.ECONOMICO));
        veiculoRepository.salvar(new Veiculo("HB20", "GHI-9012", 80.00, CategoriaVeiculo.INTERMEDIARIO));
        veiculoRepository.salvar(new Veiculo("Onix", "JKL-3456", 90.00, CategoriaVeiculo.INTERMEDIARIO));
        veiculoRepository.salvar(new Veiculo("Jeep Compass", "MNO-7890", 120.00, CategoriaVeiculo.SUV));
        veiculoRepository.salvar(new Veiculo("BMW X1", "PQR-1234", 200.00, CategoriaVeiculo.LUXO));
    }
    public List<Veiculo> listarDisponiveis(){
        return veiculoRepository.listarDisponiveis();
    }
    public Veiculo buscarPorId(int id){
        return veiculoRepository.buscarPorId(id);
    }
    public void atualizarDisponibilidade(int veiculoId, boolean disponivel) {
        Veiculo veiculo = veiculoRepository.buscarPorId(veiculoId);
        if (veiculo != null) {
            veiculo.setDisponivel(disponivel);
        }
    }

    public void listarTodosVeiculos() {
        List<Veiculo> veiculos = veiculoRepository.listarTodos();

        System.out.println("\n===  TODOS OS VEÍCULOS CADASTRADOS ===");

        if (veiculos.isEmpty()) {
            System.out.println("Nenhum veículo cadastrado.");
            return;
        }

        //  ADICIONE ESTE LOOP PARA MOSTRAR OS VEÍCULOS:
        System.out.println("ID | Modelo           | Placa     | Categoria     | Preço/Dia");
        System.out.println("---|------------------|-----------|---------------|------------");

        for (Veiculo veiculo : veiculos) {
            System.out.printf("%-2d | %-16s | %-9s | %-12s | R$ %-6.2f%n",
                    veiculo.getId(),
                    veiculo.getModelo(),
                    veiculo.getPlaca(),
                    veiculo.getCategoria().getDescricao(),
                    veiculo.getPrecoDiario());
        }
    }
}
