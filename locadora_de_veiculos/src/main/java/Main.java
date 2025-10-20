import entities.Aluguel;
import services.VeiculoService;
import services.ClienteService;
import services.AluguelService;
import services.RelatorioService;
import entities.Veiculo;
import entities.Cliente;
import entities.ItemAluguel;
import repositories.AluguelRepository;
import repositories.VeiculoRepository;
import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args){
        // Inicializa todos os serviços
        AluguelRepository aluguelRepo = new AluguelRepository();
        VeiculoRepository veiculoRepo = new VeiculoRepository();

        VeiculoService veiculoService = new VeiculoService();
        ClienteService clienteService = new ClienteService();
        AluguelService aluguelService = new AluguelService();
        RelatorioService relatorioService = new RelatorioService(aluguelRepo, veiculoRepo);

        Scanner scanner = new Scanner(System.in);

        System.out.println(" BEM-VINDO À LOCADORA DE VEÍCULOS!");

        int opcao;
        do {
            System.out.println("\n=== MENU PRINCIPAL ===");
            System.out.println("1 - Listar Veículos Disponíveis");
            System.out.println("2 - Cadastrar Cliente");
            System.out.println("3 - Alugar Veículo");
            System.out.println("4 - Listar Aluguéis Ativos");
            System.out.println("5 - Finalizar Aluguel");
            System.out.println("6 - Listar Todos os Veículos");
            System.out.println("7 - Relatórios");
            System.out.println("8 - Sair");
            System.out.print("Escolha uma opção: ");

            opcao = scanner.nextInt();

            switch (opcao) {
                case 1:
                    listarVeiculosDisponiveis(veiculoService);
                    break;
                case 2:
                    cadastrarCliente(clienteService, scanner);
                    break;
                case 3:
                    alugarVeiculo(aluguelService, clienteService, veiculoService, scanner);
                    break;
                case 4:
                    listarAlugueisAtivos(aluguelService);
                    break;
                case 5:
                    finalizarAluguel(aluguelService, scanner);
                    break;
                case 6:
                    listarTodosVeiculos(veiculoService);
                    break;
                case 7:
                    exibirRelatorios(relatorioService);
                    break;
                case 8:
                    System.out.println("Obrigado por usar nossa locadora!");
                    break;
                default:
                    System.out.println("❌ Opção inválida!");
            }
        } while (opcao != 8);

        scanner.close();
    }

    // LISTAR VEÍCULOS DISPONÍVEIS
    private static void listarVeiculosDisponiveis(VeiculoService veiculoService) {
        System.out.println("\n===  VEÍCULOS DISPONÍVEIS ===");
        List<Veiculo> veiculos = veiculoService.listarDisponiveis();

        if (veiculos.isEmpty()) {
            System.out.println("Nenhum veículo disponível no momento.");
            return;
        }

        System.out.println("ID | Modelo           | Placa     | Categoria     | Preço/Dia");
        System.out.println("---|------------------|-----------|--------------|----------");
        for (Veiculo veiculo : veiculos) {
            System.out.printf("%-2d | %-16s | %-9s | %-12s | R$ %-6.2f%n",
                    veiculo.getId(), veiculo.getModelo(), veiculo.getPlaca(),
                    veiculo.getCategoria().getDescricao(), veiculo.getPrecoDiario());
        }
    }

    //  CADASTRAR CLIENTE
    private static void cadastrarCliente(ClienteService clienteService, Scanner scanner) {
        System.out.println("\n===  CADASTRAR CLIENTE ===");

        scanner.nextLine(); // Limpar buffer
        System.out.print("CPF: ");
        String cpf = scanner.nextLine();

        System.out.print("Nome: ");
        String nome = scanner.nextLine();

        System.out.print("Telefone: ");
        String telefone = scanner.nextLine();

        // Verifica se CPF já existe
        if (clienteService.buscarPorCpf(cpf) != null) {
            System.out.println(" Cliente já cadastrado com este CPF!");
            return;
        }

        clienteService.cadastraCliente(cpf, nome, telefone);
        System.out.println(" Cliente cadastrado com sucesso!");
    }

    //  ALUGAR VEÍCULO
    private static void alugarVeiculo(AluguelService aluguelService, ClienteService clienteService,
                                      VeiculoService veiculoService, Scanner scanner) {
        System.out.println("\n===  NOVO ALUGUEL ===");

        // 1. Verificar cliente
        scanner.nextLine();
        System.out.print("CPF do cliente: ");
        String cpf = scanner.nextLine();

        Cliente cliente = clienteService.buscarPorCpf(cpf);
        if (cliente == null) {
            System.out.println(" Cliente não encontrado! Cadastre primeiro.");
            return;
        }

        // 2. Quantos dias
        System.out.print("Quantos dias de aluguel: ");
        int dias = scanner.nextInt();

        // 3. Escolher veículos
        List<ItemAluguel> veiculosAlugados = new ArrayList<>();
        boolean continuar = true;

        while (continuar) {
            listarVeiculosDisponiveis(veiculoService);

            System.out.print("\nDigite o ID do veículo: ");
            int veiculoId = scanner.nextInt();

            Veiculo veiculo = veiculoService.buscarPorId(veiculoId);
            if (veiculo != null && veiculo.isDisponivel()) {
                veiculosAlugados.add(new ItemAluguel(veiculo));
                System.out.println(" " + veiculo.getModelo() + " adicionado ao aluguel!");
            } else {
                System.out.println(" Veículo não encontrado ou indisponível!");
            }

            System.out.print("Alugar mais veículos? (s/n): ");
            scanner.nextLine();
            continuar = scanner.nextLine().equalsIgnoreCase("s");
        }

        // 4. Criar aluguel
        if (!veiculosAlugados.isEmpty()) {
            Aluguel aluguel = aluguelService.criarAluguel(cliente, dias, veiculosAlugados);
            System.out.printf("\n Aluguel criado com sucesso!%n");
            System.out.printf(" Número do Aluguel: %d%n", aluguel.getId());
            System.out.printf(" Cliente: %s%n", cliente.getNome());
            System.out.printf(" Dias: %d%n", dias);
            System.out.printf(" Total: R$ %.2f%n", aluguel.calcularTotal());

            // Mostra descontos aplicados
            if (dias > 7) {
                System.out.println(" Desconto aplicado: " + (dias > 15 ? "20%" : "10%") + " para aluguéis longos!");
            }
        } else {
            System.out.println("  Nenhum veículo selecionado. Aluguel cancelado.");
        }
    }

    // 📋 LISTAR ALUGUÉIS ATIVOS
    private static void listarAlugueisAtivos(AluguelService aluguelService) {
        System.out.println("\n===  ALUGUÉIS ATIVOS ===");
        List<Aluguel> alugueis = aluguelService.listarAlugueisAtivos();

        if (alugueis.isEmpty()) {
            System.out.println("Nenhum aluguel ativo no momento.");
            return;
        }

        for (Aluguel aluguel : alugueis) {
            System.out.printf("\n Aluguel #%d%n", aluguel.getId());
            System.out.printf(" Cliente: %s%n", aluguel.getCliente().getNome());
            System.out.printf(" Dias: %d%n", aluguel.getDias());
            System.out.printf(" Veículos: ");
            for (ItemAluguel item : aluguel.getVeiculos()) {
                System.out.printf("%s ", item.getVeiculo().getModelo());
            }
            System.out.printf("%n Total: R$ %.2f%n", aluguel.calcularTotal());
        }
    }

    //  FINALIZAR ALUGUEL
    private static void finalizarAluguel(AluguelService aluguelService, Scanner scanner) {
        System.out.println("\n===  FINALIZAR ALUGUEL ===");

        List<Aluguel> alugueisAtivos = aluguelService.listarAlugueisAtivos();
        if (alugueisAtivos.isEmpty()) {
            System.out.println("Nenhum aluguel ativo para finalizar.");
            return;
        }

        // Mostra aluguéis ativos
        for (Aluguel aluguel : alugueisAtivos) {
            System.out.printf("#%d - %s (%d dias)%n",
                    aluguel.getId(), aluguel.getCliente().getNome(), aluguel.getDias());
        }

        System.out.print("Digite o número do aluguel a finalizar: ");
        int aluguelId = scanner.nextInt();

        aluguelService.finalizarAluguel(aluguelId);
        System.out.println(" Aluguel finalizado com sucesso!");
    }

    //  LISTAR TODOS OS VEÍCULOS
    private static void listarTodosVeiculos(VeiculoService veiculoService) {
        veiculoService.listarTodosVeiculos();
    }

    //  RELATÓRIOS
    private static void exibirRelatorios(RelatorioService relatorioService) {
        System.out.println("\n===  RELATÓRIOS ===");
        System.out.println("1 - Faturamento Total");
        System.out.println("2 - Veículos Mais Alugados");
        System.out.println("3 - Faturamento por Categoria");
        System.out.println("4 - Clientes Mais Fiéis");

        Scanner scanner = new Scanner(System.in);
        int opcao = scanner.nextInt();

        switch (opcao) {
            case 1:
                relatorioService.gerarRelatorioFaturamento();
                break;
            case 2:
                relatorioService.gerarRelatorioVeiculosPopulares();
                break;
            case 3:
                relatorioService.gerarRelatorioPorCategoria();
                break;
            case 4:
                relatorioService.gerarRealatorioClienteFies();
                break;
        }
    }
}