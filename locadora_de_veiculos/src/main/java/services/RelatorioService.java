package services;
import entities.Aluguel;
import entities.ItemAluguel;
import entities.Veiculo;
import entities.CategoriaVeiculo;
import repositories.VeiculoRepository;
import repositories.AluguelRepository;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

//ponto importante ver como funciona map e hasmap

//estudar para entender a logica
public class RelatorioService {

    private AluguelRepository aluguelRepository;
    private VeiculoRepository veiculoRepository;

    public RelatorioService(AluguelRepository aluguelRepo, VeiculoRepository veiculoRepo){
        this.aluguelRepository = aluguelRepo;
        this.veiculoRepository = veiculoRepo;
    }

    public void gerarRelatorioFaturamento(){
        System.out.println("===Faturamento Total===");

        List<Aluguel> todosAlugueis = aluguelRepository.listarTodos();
        double faturamentoTotal = 0;
        double multasTotal = 0;

        for(Aluguel aluguel : todosAlugueis){
            faturamentoTotal += aluguel.calcularTotal();
            multasTotal += aluguel.getMulta();

        }
        System.out.printf(" Faturamento com Aluguéis: R$ %.2f%n", faturamentoTotal);
        System.out.printf(" Multas Arrecadadas: R$ %.2f%n", multasTotal);
        System.out.printf(" Faturamento Líquido: R$ %.2f%n", faturamentoTotal + multasTotal);
        System.out.printf(" Total de Aluguéis: %d%n", todosAlugueis.size());
    }

    public void gerarRelatorioVeiculosPopulares() {
        System.out.println("/n Veiculos mais Populares");

        List<Veiculo> todosVeiculos = veiculoRepository.listarTodos();
        todosVeiculos.sort((a, b) -> Integer.compare(b.getTotalAlugueis(), a.getTotalAlugueis()));

        System.out.println("Posição | Veículo           | Categoria     | Total Aluguéis");
        System.out.println("--------|-------------------|--------------|---------------");

        int posicao = 1;
        for(Veiculo veiculo : todosVeiculos){
            if(veiculo.getTotalAlugueis() > 0){
                System.out.printf("%-7d | %-17s | %-12s | %-14d%n",
                posicao++,veiculo.getModelo(),veiculo.getCategoria().getDescricao(),
                veiculo.getTotalAlugueis());
            }
        }
        if (posicao == 1 ){
            System.out.println("Nenhum veiculo foi alugado ainda");
        }

    }
    public void gerarRelatorioPorCategoria(){
        System.out.println("Relatorio por categoria");

        Map<CategoriaVeiculo,Double> faturamentoCategoria = new HashMap<>();
        List<Aluguel> todosAlugueis = aluguelRepository.listarTodos();

        for(Aluguel aluguel : todosAlugueis){
            for (ItemAluguel item: aluguel.getVeiculos()){
                Veiculo veiculo = item.getVeiculo();
                double valor = item.getSubtotal() * aluguel.getDias();
                faturamentoCategoria.put(veiculo.getCategoria(),
                        faturamentoCategoria.getOrDefault(veiculo.getCategoria(),0.0)+valor);
            }
        }
        System.out.println("Categoria     | Faturamento");
        System.out.println("--------------|-------------");
        for(CategoriaVeiculo categoria : CategoriaVeiculo.values()){
            double valor = faturamentoCategoria.getOrDefault(categoria,0.0);
            System.out.printf("%-13s | R$ %-8.2f%n", categoria.getDescricao(), valor);
        }
    }


    public void gerarRealatorioClienteFies(){
        System.out.println("Clientes mais fies");

        Map<String,Integer> alugueisPorCliente = new HashMap<>();
        List<Aluguel> todosAlugueis = aluguelRepository.listarTodos();

        for(Aluguel aluguel : todosAlugueis) {
            String cliente = aluguel.getCliente().getNome();
            alugueisPorCliente.put(cliente,alugueisPorCliente.getOrDefault(cliente,0)+1);

        }
        alugueisPorCliente.entrySet().stream()
                .sorted((a, b) -> b.getValue().compareTo(a.getValue()))
                .forEach(entry -> {
                    System.out.printf("%-20s: %d aluguéis%n", entry.getKey(), entry.getValue());
                });

    }
}


