package services;
import entities.Aluguel;
import entities.Cliente;
import entities.ItemAluguel;
import entities.StatusAluguel;
import repositories.AluguelRepository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

//estudar pois não entendi multa

public class AluguelService {
    private AluguelRepository aluguelRepository;
    private VeiculoService veiculoService;

    public AluguelService(){
        this.aluguelRepository = new AluguelRepository();
        this.veiculoService = new VeiculoService();
    }

    //estudar essa parte não entendi muito falta comprender a logica

    public Aluguel criarAluguel(Cliente cliente, int dias, List<ItemAluguel> veiculos){
        Aluguel aluguel = new Aluguel(cliente , dias);
        for (ItemAluguel item : veiculos) {
            aluguel.adicionarVeiculo(item);
            // Torna veículo INDISPONÍVEL
            veiculoService.atualizarDisponibilidade(item.getVeiculo().getId(), false);
        }
        cliente.adicionarAluguel(aluguel); // Adiciona ao histórico
        aluguelRepository.salvar(aluguel);
        return aluguel;
    }
    // FINALIZAR aluguel (devolução)
    public void finalizarAluguel(int aluguelId){
        Aluguel aluguel = aluguelRepository.buscarPorId(aluguelId);
        if(aluguel != null){
            aluguel.setStatus(StatusAluguel.FINALIZADO);
            aluguel.setDataAluguel(new Date());
            aluguel.calcularMulta();

            for(ItemAluguel item : aluguel.getVeiculos()){
                veiculoService.atualizarDisponibilidade(item.getVeiculo().getId(),true);
            }
        }
    }
    public List<Aluguel>listarAlugueisAtivos(){
        List<Aluguel> ativos = new ArrayList<>();
        for(Aluguel aluguel: aluguelRepository.listarTodos()){
            if(aluguel.getStatus()==StatusAluguel.ATIVO){
                ativos.add(aluguel);
            }
        }
        return ativos;
    }

    public List<Aluguel> listarTodosAlugueis(){
        return aluguelRepository.listarTodos();
    }
}
