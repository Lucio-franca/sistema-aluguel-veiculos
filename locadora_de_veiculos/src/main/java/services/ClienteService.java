package services;
import entities.Cliente;
import repositories.AluguelRepository;
import repositories.ClienteRepository;

public class ClienteService {

    private ClienteRepository clienteRepository;

    public ClienteService(){
        this.clienteRepository = new ClienteRepository();
    }

    public void cadastraCliente(String cpf,String nome,String telefone){
        Cliente cliente = new Cliente (cpf,nome,telefone);
        clienteRepository.salvar(cliente);
    }

    public Cliente buscarPorCpf(String cpf){
        return clienteRepository.BuscarPorCpf(cpf);
    }
    public void listarClientes() {
        System.out.println("\n===  TODOS OS CLIENTES ===");
        for (Cliente cliente : clienteRepository.listarTodos()) {
            System.out.printf("CPF: %s - %s - Tel: %s%n",
                    cliente.getCpf(), cliente.getNome(), cliente.getTelefone());
        }
    }
}

