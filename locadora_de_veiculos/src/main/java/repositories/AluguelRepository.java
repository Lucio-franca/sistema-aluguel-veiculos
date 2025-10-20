package repositories;
import entities.Aluguel;
import java.util.ArrayList;
import java.util.List;


public class AluguelRepository {

    private List<Aluguel> alugueis = new ArrayList<>();
    private int nextId = 1;

    public void salvar(Aluguel aluguel){

        if (aluguel.getId()==0){
            aluguel.setId(nextId++);
        }
        alugueis.add(aluguel);
    }
    public List<Aluguel> listarTodos(){
        return new ArrayList<>(alugueis);
    }

    public Aluguel buscarPorId(int id){
        for(Aluguel aluguel: alugueis){
            if(aluguel.getId() == id ){
                return aluguel;
            }

        }
        return null;
    }



}
