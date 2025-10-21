# 🚗 Sistema de Aluguel de Veículos

Backend para sistema de **aluguel de carros** em **Java OOP**.  
Inclui **CRUD completo**, **cálculos automáticos de valores e multas**, **status de aluguéis**, **histórico de clientes** e **relatórios**.

---

## 📋 Índice
- [Visão Geral](#-visão-geral)
- [Funcionamento do Sistema](#-funcionamento-do-sistema)
- [Estrutura do Projeto](#-estrutura-do-projeto)
- [Classes e Códigos](#-classes-e-códigos)
- [Como Executar](#-como-executar)
- [Funcionalidades](#-funcionalidades)

---

## 🎯 Visão Geral

Este sistema foi desenvolvido em **Java**, seguindo os princípios de **Orientação a Objetos**.  
Ele controla todo o ciclo de locação de veículos — desde o **cadastro**, **aluguel**, **cálculo do valor** e **devolução**, até a **geração de relatórios** com histórico e estatísticas.

---

## ⚙️ Funcionamento do Sistema

### 🔁 Fluxo Principal:
1. **Cadastro** → Clientes e veículos são registrados no sistema.  
2. **Aluguel** → O cliente seleciona um ou mais veículos e define o período.  
3. **Cálculo** → O sistema calcula automaticamente o valor total com possíveis **descontos progressivos**.  
4. **Devolução** → Quando o veículo é devolvido, o sistema verifica **atrasos e aplica multas**.  
5. **Relatórios** → Geração de **histórico de aluguéis** e **estatísticas de uso**.

---

## 📁 Estrutura do Projeto

---
## 📝 Classes e Códigos

O sistema é estruturado em **quatro camadas principais**: Entities, Repositories, Services e Main.

---

### 1️⃣ Entities
Representam os **modelos de dados** do sistema, com encapsulamento e responsabilidade única.

- **Veiculo**: Contém informações do veículo, disponibilidade e histórico de aluguéis.
- **Cliente**: Armazena dados do cliente e total de aluguéis realizados.
- **Aluguel**: Representa um aluguel, incluindo veículos, cliente, dias, status e multas.
- **ItemAluguel**: Representa cada veículo dentro de um aluguel.
- **Enums**:
  - `StatusAluguel` → ATIVO, FINALIZADO, CANCELADO, PENDENTE.
  - `TipoVeiculo` → CARRO, MOTO, CAMINHAO, SUV, SEDAN.

---

### 2️⃣ Repositories
Simulam o **banco de dados em memória**, armazenando e gerenciando entidades.

- **VeiculoRepository** → Salva, lista e busca veículos; filtra veículos disponíveis.
- **ClienteRepository** → Salva, lista e busca clientes por CPF.
- **AluguelRepository** → Salva, lista e busca aluguéis por ID.

---

### 3️⃣ Services
Implementam a **lógica de negócio**, coordenando entidades e repositórios.

- **VeiculoService** → Gerencia cadastro, listagem e disponibilidade de veículos.
- **ClienteService** → Gerencia cadastro, busca e listagem de clientes.
- **AluguelService** → Cria, finaliza e lista aluguéis; calcula valores e multas.
- **RelatorioService** → Gera relatórios de faturamento, veículos mais alugados, clientes fiéis e faturamento por categoria.

---

### 4️⃣ Main
Classe principal que **executa o sistema**, com **menu interativo**:

- Listar veículos disponíveis  
- Cadastrar clientes  
- Criar e finalizar aluguéis  
- Listar aluguéis ativos  
- Gerar relatórios  
- Listar todos os veículos  
- Sair do sistema

O **menu interativo** permite ao usuário operar todas as funcionalidades do sistema diretamente pelo console.


## 🏗️ Entities

A pasta `entities` contém todas as **classes de modelo** que representam os principais elementos do sistema: veículos, clientes, aluguéis e seus relacionamentos.  
Cada entidade foi desenvolvida com foco em **responsabilidade única** e **encapsulamento**, seguindo os pilares da **Programação Orientada a Objetos (POO)**.

---

### 🧩 1. Enum `StatusAluguel`
```java
// Define os possíveis status de um aluguel
public enum StatusAluguel {
    ATIVO,       // Aluguel em andamento
    FINALIZADO,  // Devolução concluída
    CANCELADO,   // Aluguel cancelado
    PENDENTE     // Aguardando confirmação
}
```

### 🚘 2. Enum `TipoVeiculo`
```java
/**
 * Define os tipos de veículos disponíveis no sistema
 */ 
public enum TipoVeiculo {
    CARRO,     // Veículo de passeio
    MOTO,      // Motocicleta
    CAMINHAO,  // Veículo de carga
    SUV,       // Sport Utility Vehicle
    SEDAN      // Carro de porte médio
}
 ```
### 🚗 3. Classe `Veiculo`
```java
import java.util.Date;

/**
 * Representa um veículo que pode ser alugado
 */
public class Veiculo {
    private int id;                  // Identificador único do veículo
    private String modelo;           // Modelo do veículo
    private String placa;            // Placa do veículo
    private String marca;            // Marca do veículo
    private int ano;                 // Ano de fabricação
    private TipoVeiculo tipo;        // Tipo do veículo (enum TipoVeiculo)
    private double valorDiaria;      // Valor da diária de aluguel
    private boolean disponivel;      // Disponibilidade para aluguel
    private int totalAlugueis;       // Total de vezes que o veículo foi alugado
    private Date dataCadastro;       // Data de cadastro do veículo
    
    // Construtor principal
    public Veiculo(String modelo, String placa, String marca, int ano, 
                   TipoVeiculo tipo, double valorDiaria) {
        this.modelo = modelo;
        this.placa = placa;
        this.marca = marca;
        this.ano = ano;
        this.tipo = tipo;
        this.valorDiaria = valorDiaria;
        this.disponivel = true;
        this.totalAlugueis = 0;
        this.dataCadastro = new Date();
    }
    
    // Incrementa o total de alugueis do veículo
    public void incrementarAluguel() { this.totalAlugueis++; }
    
    // Marca o veículo como alugado
    public void alugar() { this.disponivel = false; }
    
    // Marca o veículo como disponível
    public void devolver() { this.disponivel = true; }
    
    // Getters e Setters omitidos para brevidade
}

```
### 👤 4. Classe `Cliente`
```java
import java.util.Date;

/**
 * Representa um cliente que pode alugar veículos
 */
public class Cliente {
    private int id;                // Identificador do cliente
    private String nome;           // Nome do cliente
    private String cpf;            // CPF do cliente
    private String email;          // E-mail do cliente
    private String telefone;       // Telefone do cliente
    private String endereco;       // Endereço do cliente
    private Date dataCadastro;     // Data de cadastro do cliente
    private int totalAlugueis;    // Total de alugueis feitos pelo cliente
    
    // Construtor principal
    public Cliente(String nome, String cpf, String email, String telefone) {
        this.nome = nome;
        this.cpf = cpf;
        this.email = email;
        this.telefone = telefone;
        this.dataCadastro = new Date();
        this.totalAlugueis = 0;
    }
    
    // Incrementa o total de alugueis do cliente
    public void incrementarAluguel() { this.totalAlugueis++; }
    
    // Getters e Setters omitidos para brevidade
}

```
### 📦 5. Classe `ItemAluguel`
```java
/**
 * Representa um item (veículo) dentro de um aluguel
 */
public class ItemAluguel {
    private Veiculo veiculo;        // Veículo alugado
    private int quantidade;         // Quantidade (geralmente 1)
    private double valorDiaria;     // Valor da diária do veículo
    
    // Construtor principal
    public ItemAluguel(Veiculo veiculo, int quantidade, double valorDiaria) {
        this.veiculo = veiculo;
        this.quantidade = quantidade;
        this.valorDiaria = valorDiaria;
    }
    
    // Calcula o subtotal do item (diária * quantidade)
    public double getSubtotal() { return valorDiaria * quantidade; }
    
    // Getters e Setters omitidos para brevidade
}

```
### 📝 6. Classe `Aluguel`
```java
import java.util.List;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Classe principal que representa um aluguel no sistema
 */
public class Aluguel {
    private int id;                      // Identificador do aluguel
    private Cliente cliente;             // Cliente que realizou o aluguel
    private List<ItemAluguel> veiculos; // Lista de veículos alugados
    private int dias;                    // Número de dias do aluguel
    private StatusAluguel status;        // Status do aluguel
    private Date dataAluguel;            // Data em que o aluguel foi iniciado
    private Date dataDevolucao;          // Data de devolução do veículo
    private double multa;                // Valor da multa (se houver atraso)
    
    // Construtor principal
    public Aluguel(Cliente cliente, int dias) {
        this.cliente = cliente;
        this.dias = dias;
        this.veiculos = new ArrayList<>();
        this.status = StatusAluguel.ATIVO;
        this.dataAluguel = new Date();
        this.multa = 0.0;
    }
    
    // Adiciona um veículo ao aluguel
    public void adicionarVeiculo(ItemAluguel veiculo) {
        veiculos.add(veiculo);
        veiculo.getVeiculo().incrementarAluguel(); // Atualiza histórico do veículo
        veiculo.getVeiculo().alugar();             // Marca como alugado
    }
    
    // Finaliza o aluguel, devolvendo os veículos e calculando multa
    public void finalizarAluguel() {
        this.dataDevolucao = new Date();
        this.status = StatusAluguel.FINALIZADO;
        calcularMulta();
        for (ItemAluguel item : veiculos) {
            item.getVeiculo().devolver(); // Marca cada veículo como disponível
        }
        cliente.incrementarAluguel();     // Atualiza histórico do cliente
    }
    
    // Calcula o valor total do aluguel
    public double calcularTotal() {
        double total = 0;
        for (ItemAluguel item : veiculos) { total += item.getSubtotal(); }
        total *= dias;
        if (dias > 7 && dias <= 15) total *= 0.9;  // Desconto progressivo 10%
        else if (dias > 15) total *= 0.8;           // Desconto progressivo 20%
        return total;
    }
    
    // Calcula multa em caso de atraso
    public double calcularMulta() {
        if (dataDevolucao == null) return 0.0;
        Calendar dataPrevista = Calendar.getInstance();
        dataPrevista.setTime(dataAluguel);
        dataPrevista.add(Calendar.DAY_OF_MONTH, dias);
        Calendar dataReal = Calendar.getInstance();
        dataReal.setTime(dataDevolucao);
        if (dataReal.after(dataPrevista)) {
            long diff = dataReal.getTimeInMillis() - dataPrevista.getTimeInMillis();
            int diasAtraso = (int)(diff / (1000 * 60 * 60 * 24));
            this.multa = diasAtraso * (calcularTotal() * 0.1); // 10% por dia de atraso
            return this.multa;
        }
        return 0.0;
    }
    
    // Calcula o total incluindo multa
    public double calcularTotalComMulta() { return calcularTotal() + calcularMulta(); }
    
    // Getters e Setters omitidos para brevidade
}

```
## 💾 Repositories

A pasta `repositories` contém todas as **classes responsáveis por armazenar, buscar e gerenciar os dados em memória**.  
Cada repositório simula o funcionamento de um **banco de dados interno**, mantendo as informações das entidades enquanto o sistema está em execução.  
Cada classe de repositório foi desenvolvida com foco em **responsabilidade única** e **isolamento da lógica de dados**, seguindo os pilares da **Programação Orientada a Objetos (POO)**.

---

### 📦 1. `AluguelRepository`
```java
package repositories;

import entities.Aluguel;
import java.util.ArrayList;
import java.util.List;

/**
 * Repositório responsável por gerenciar os registros de aluguéis.
 * Armazena dados em memória e fornece métodos para salvar, listar e buscar por ID.
 */
public class AluguelRepository {

    private List<Aluguel> alugueis = new ArrayList<>();
    private int nextId = 1;

    /**
     * Salva um novo aluguel no repositório.
     * Caso o aluguel ainda não tenha ID, ele é gerado automaticamente.
     */
    public void salvar(Aluguel aluguel) {
        if (aluguel.getId() == 0) {
            aluguel.setId(nextId++);
        }
        alugueis.add(aluguel);
    }

    /**
     * Retorna uma lista com todos os aluguéis cadastrados.
     */
    public List<Aluguel> listarTodos() {
        return new ArrayList<>(alugueis);
    }

    /**
     * Busca um aluguel específico pelo seu ID.
     * @param id Identificador único do aluguel.
     * @return O aluguel correspondente ou null, caso não exista.
     */
    public Aluguel buscarPorId(int id) {
        for (Aluguel aluguel : alugueis) {
            if (aluguel.getId() == id) {
                return aluguel;
            }
        }
        return null;
    }
}
```
### 👥 2. `ClienteRepository`
```java
package repositories;

import entities.Cliente;
import java.util.ArrayList;
import java.util.List;

/**
 * Repositório responsável por gerenciar os clientes cadastrados.
 * Permite salvar, listar e buscar clientes por CPF.
 */
public class ClienteRepository {

    private List<Cliente> clientes = new ArrayList<>();

    /**
     * Adiciona um novo cliente à lista.
     */
    public void salvar(Cliente cliente) {
        clientes.add(cliente);
    }

    /**
     * Busca um cliente pelo CPF.
     * @param cpf CPF do cliente a ser encontrado.
     * @return Cliente correspondente ou null, caso não exista.
     */
    public Cliente buscarPorCpf(String cpf) {
        for (Cliente cliente : clientes) {
            if (cliente.getCpf().equals(cpf)) {
                return cliente;
            }
        }
        return null;
    }

    /**
     * Retorna uma lista com todos os clientes cadastrados.
     */
    public List<Cliente> listarTodos() {
        return new ArrayList<>(clientes);
    }
}

````

### 🚗 3. `VeiculoRepository`
```java
package repositories;

import entities.Veiculo;
import java.util.ArrayList;
import java.util.List;

/**
 * Repositório responsável por armazenar e gerenciar os veículos disponíveis.
 * Contém métodos para salvar, listar e buscar veículos.
 */
public class VeiculoRepository {

    private List<Veiculo> veiculos = new ArrayList<>();
    private int nextId = 1;

    /**
     * Salva um novo veículo no repositório.
     * Caso o veículo não tenha ID, um novo é atribuído automaticamente.
     */
    public void salvar(Veiculo veiculo) {
        if (veiculo.getId() == 0) {
            veiculo.setId(nextId++);
        }
        veiculos.add(veiculo);
    }

    /**
     * Retorna uma lista com todos os veículos cadastrados.
     */
    public List<Veiculo> listarTodos() {
        return new ArrayList<>(veiculos);
    }

    /**
     * Retorna apenas os veículos disponíveis para aluguel.
     */
    public List<Veiculo> listarDisponiveis() {
        List<Veiculo> disponiveis = new ArrayList<>();
        for (Veiculo veiculo : veiculos) {
            if (veiculo.isDisponivel()) {
                disponiveis.add(veiculo);
            }
        }
        return disponiveis;
    }

    /**
     * Busca um veículo específico pelo seu ID.
     * @param id Identificador único do veículo.
     * @return Veículo correspondente ou null, caso não exista.
     */
    public Veiculo buscarPorId(int id) {
        for (Veiculo veiculo : veiculos) {
            if (veiculo.getId() == id) {
                return veiculo;
            }
        }
        return null;
    }
}
````
## 🧠 Services

A pasta `services` contém as **classes responsáveis pela lógica de negócio** do sistema.  
Cada *Service* coordena as operações entre as entidades e os repositórios, garantindo o correto fluxo de dados.  
Essas classes implementam as principais **regras de negócio**, como criação de aluguéis, cálculos de valores, controle de disponibilidade e geração de relatórios.

---

### 🚘 1. `AluguelService`
```java
package services;

import entities.Aluguel;
import entities.Cliente;
import entities.ItemAluguel;
import entities.StatusAluguel;
import repositories.AluguelRepository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Responsável por gerenciar a criação, finalização e listagem dos aluguéis.
 * Controla a disponibilidade dos veículos e calcula multas quando necessário.
 */
public class AluguelService {

    private AluguelRepository aluguelRepository;
    private VeiculoService veiculoService;

    public AluguelService() {
        this.aluguelRepository = new AluguelRepository();
        this.veiculoService = new VeiculoService();
    }

    /**
     * Cria um novo aluguel, adicionando os veículos e atualizando suas disponibilidades.
     */
    public Aluguel criarAluguel(Cliente cliente, int dias, List<ItemAluguel> veiculos) {
        Aluguel aluguel = new Aluguel(cliente, dias);

        for (ItemAluguel item : veiculos) {
            aluguel.adicionarVeiculo(item);
            veiculoService.atualizarDisponibilidade(item.getVeiculo().getId(), false); // Veículo fica indisponível
        }

        cliente.adicionarAluguel(aluguel);
        aluguelRepository.salvar(aluguel);

        return aluguel;
    }

    /**
     * Finaliza um aluguel, definindo o status e liberando os veículos.
     */
    public void finalizarAluguel(int aluguelId) {
        Aluguel aluguel = aluguelRepository.buscarPorId(aluguelId);
        if (aluguel != null) {
            aluguel.setStatus(StatusAluguel.FINALIZADO);
            aluguel.setDataAluguel(new Date());
            aluguel.calcularMulta();

            for (ItemAluguel item : aluguel.getVeiculos()) {
                veiculoService.atualizarDisponibilidade(item.getVeiculo().getId(), true);
            }
        }
    }

    /**
     * Lista apenas os aluguéis que estão ativos.
     */
    public List<Aluguel> listarAlugueisAtivos() {
        List<Aluguel> ativos = new ArrayList<>();
        for (Aluguel aluguel : aluguelRepository.listarTodos()) {
            if (aluguel.getStatus() == StatusAluguel.ATIVO) {
                ativos.add(aluguel);
            }
        }
        return ativos;
    }

    /**
     * Retorna todos os aluguéis cadastrados.
     */
    public List<Aluguel> listarTodosAlugueis() {
        return aluguelRepository.listarTodos();
    }
}
```
### 👤 2. `ClienteService`
```java
package services;

import entities.Cliente;
import repositories.ClienteRepository;

/**
 * Responsável por gerenciar as operações relacionadas aos clientes.
 * Permite cadastro, busca e listagem de clientes.
 */
public class ClienteService {

    private ClienteRepository clienteRepository;

    public ClienteService() {
        this.clienteRepository = new ClienteRepository();
    }

    /**
     * Cadastra um novo cliente no sistema.
     */
    public void cadastraCliente(String cpf, String nome, String telefone) {
        Cliente cliente = new Cliente(cpf, nome, telefone);
        clienteRepository.salvar(cliente);
    }

    /**
     * Busca um cliente pelo CPF.
     */
    public Cliente buscarPorCpf(String cpf) {
        return clienteRepository.BuscarPorCpf(cpf);
    }

    /**
     * Exibe todos os clientes cadastrados.
     */
    public void listarClientes() {
        System.out.println("\n=== TODOS OS CLIENTES ===");
        for (Cliente cliente : clienteRepository.listarTodos()) {
            System.out.printf("CPF: %s - %s - Tel: %s%n",
                    cliente.getCpf(), cliente.getNome(), cliente.getTelefone());
        }
    }
}

````
### 📊 3. `RelatorioService`
```java
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

/**
 * Gera relatórios de desempenho e estatísticas do sistema.
 * Inclui faturamento total, veículos mais alugados e clientes mais fiéis.
 */
public class RelatorioService {

    private AluguelRepository aluguelRepository;
    private VeiculoRepository veiculoRepository;

    public RelatorioService(AluguelRepository aluguelRepo, VeiculoRepository veiculoRepo) {
        this.aluguelRepository = aluguelRepo;
        this.veiculoRepository = veiculoRepo;
    }

    /**
     * Gera relatório de faturamento total (aluguéis + multas).
     */
    public void gerarRelatorioFaturamento() {
        System.out.println("=== Faturamento Total ===");

        List<Aluguel> todosAlugueis = aluguelRepository.listarTodos();
        double faturamentoTotal = 0;
        double multasTotal = 0;

        for (Aluguel aluguel : todosAlugueis) {
            faturamentoTotal += aluguel.calcularTotal();
            multasTotal += aluguel.getMulta();
        }

        System.out.printf("Faturamento com Aluguéis: R$ %.2f%n", faturamentoTotal);
        System.out.printf("Multas Arrecadadas: R$ %.2f%n", multasTotal);
        System.out.printf("Faturamento Líquido: R$ %.2f%n", faturamentoTotal + multasTotal);
        System.out.printf("Total de Aluguéis: %d%n", todosAlugueis.size());
    }

    /**
     * Exibe os veículos mais alugados do sistema.
     */
    public void gerarRelatorioVeiculosPopulares() {
        System.out.println("\nVeículos mais Populares");

        List<Veiculo> todosVeiculos = veiculoRepository.listarTodos();
        todosVeiculos.sort((a, b) -> Integer.compare(b.getTotalAlugueis(), a.getTotalAlugueis()));

        System.out.println("Posição | Veículo           | Categoria     | Total Aluguéis");
        System.out.println("--------|-------------------|--------------|---------------");

        int posicao = 1;
        for (Veiculo veiculo : todosVeiculos) {
            if (veiculo.getTotalAlugueis() > 0) {
                System.out.printf("%-7d | %-17s | %-12s | %-14d%n",
                        posicao++, veiculo.getModelo(),
                        veiculo.getCategoria().getDescricao(),
                        veiculo.getTotalAlugueis());
            }
        }

        if (posicao == 1) {
            System.out.println("Nenhum veículo foi alugado ainda.");
        }
    }

    /**
     * Exibe o faturamento agrupado por categoria de veículo.
     */
    public void gerarRelatorioPorCategoria() {
        System.out.println("Relatório por categoria");

        Map<CategoriaVeiculo, Double> faturamentoCategoria = new HashMap<>();
        List<Aluguel> todosAlugueis = aluguelRepository.listarTodos();

        for (Aluguel aluguel : todosAlugueis) {
            for (ItemAluguel item : aluguel.getVeiculos()) {
                Veiculo veiculo = item.getVeiculo();
                double valor = item.getSubtotal() * aluguel.getDias();
                faturamentoCategoria.put(veiculo.getCategoria(),
                        faturamentoCategoria.getOrDefault(veiculo.getCategoria(), 0.0) + valor);
            }
        }

        System.out.println("Categoria     | Faturamento");
        System.out.println("--------------|-------------");
        for (CategoriaVeiculo categoria : CategoriaVeiculo.values()) {
            double valor = faturamentoCategoria.getOrDefault(categoria, 0.0);
            System.out.printf("%-13s | R$ %-8.2f%n", categoria.getDescricao(), valor);
        }
    }

    /**
     * Lista os clientes com maior número de aluguéis realizados.
     */
    public void gerarRelatorioClientesFieis() {
        System.out.println("Clientes mais fiéis");

        Map<String, Integer> alugueisPorCliente = new HashMap<>();
        List<Aluguel> todosAlugueis = aluguelRepository.listarTodos();

        for (Aluguel aluguel : todosAlugueis) {
            String cliente = aluguel.getCliente().getNome();
            alugueisPorCliente.put(cliente, alugueisPorCliente.getOrDefault(cliente, 0) + 1);
        }

        alugueisPorCliente.entrySet().stream()
                .sorted((a, b) -> b.getValue().compareTo(a.getValue()))
                .forEach(entry -> {
                    System.out.printf("%-20s: %d aluguéis%n", entry.getKey(), entry.getValue());
                });
    }
}

````
### 🚗 4. `VeiculoService`
```java
package services;

import entities.CategoriaVeiculo;
import repositories.VeiculoRepository;
import entities.Veiculo;
import java.util.List;

/**
 * Controla todas as operações relacionadas aos veículos:
 * - Cadastro inicial
 * - Listagem
 * - Busca por ID
 * - Atualização de disponibilidade
 */
public class VeiculoService {

    private VeiculoRepository veiculoRepository;

    public VeiculoService() {
        this.veiculoRepository = new VeiculoRepository();
        inicializarVeiculos();
    }

    /**
     * Adiciona alguns veículos padrão ao iniciar o sistema.
     */
    private void inicializarVeiculos() {
        veiculoRepository.salvar(new Veiculo("Fiat Uno", "ABC-1234", 50.00, CategoriaVeiculo.ECONOMICO));
        veiculoRepository.salvar(new Veiculo("VW Gol", "DEF-5678", 60.00, CategoriaVeiculo.ECONOMICO));
        veiculoRepository.salvar(new Veiculo("HB20", "GHI-9012", 80.00, CategoriaVeiculo.INTERMEDIARIO));
        veiculoRepository.salvar(new Veiculo("Onix", "JKL-3456", 90.00, CategoriaVeiculo.INTERMEDIARIO));
        veiculoRepository.salvar(new Veiculo("Jeep Compass", "MNO-7890", 120.00, CategoriaVeiculo.SUV));
        veiculoRepository.salvar(new Veiculo("BMW X1", "PQR-1234", 200.00, CategoriaVeiculo.LUXO));
    }

    /**
     * Lista todos os veículos disponíveis para aluguel.
     */
    public List<Veiculo> listarDisponiveis() {
        return veiculoRepository.listarDisponiveis();
    }

    /**
     * Busca um veículo específico pelo ID.
     */
    public Veiculo buscarPorId(int id) {
        return veiculoRepository.buscarPorId(id);
    }

    /**
     * Atualiza o status de disponibilidade de um veículo.
     */
    public void atualizarDisponibilidade(int veiculoId, boolean disponivel) {
        Veiculo veiculo = veiculoRepository.buscarPorId(veiculoId);
        if (veiculo != null) {
            veiculo.setDisponivel(disponivel);
        }
    }

    /**
     * Exibe todos os veículos cadastrados no sistema.
     */
    public void listarTodosVeiculos() {
        List<Veiculo> veiculos = veiculoRepository.listarTodos();

        System.out.println("\n=== TODOS OS VEÍCULOS CADASTRADOS ===");

        if (veiculos.isEmpty()) {
            System.out.println("Nenhum veículo cadastrado.");
            return;
        }

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

````
## 🚀 Main

A classe `Main` é o **ponto de entrada do sistema**.  
Ela contém o **menu principal** que permite ao usuário interagir com o sistema de aluguel de veículos, chamando os serviços responsáveis por cada funcionalidade.  

Cada opção do menu executa operações como listar veículos, cadastrar clientes, criar aluguéis, gerar relatórios, entre outras.

---

### 🧩 `Main.java`

```java
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
        // 🔧 Inicializa todos os serviços e repositórios
        AluguelRepository aluguelRepo = new AluguelRepository();
        VeiculoRepository veiculoRepo = new VeiculoRepository();

        VeiculoService veiculoService = new VeiculoService();
        ClienteService clienteService = new ClienteService();
        AluguelService aluguelService = new AluguelService();
        RelatorioService relatorioService = new RelatorioService(aluguelRepo, veiculoRepo);

        Scanner scanner = new Scanner(System.in);

        System.out.println("🚗 BEM-VINDO À LOCADORA DE VEÍCULOS!");

        int opcao;
        do {
            // 🧭 Menu principal
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
                case 1 -> listarVeiculosDisponiveis(veiculoService);
                case 2 -> cadastrarCliente(clienteService, scanner);
                case 3 -> alugarVeiculo(aluguelService, clienteService, veiculoService, scanner);
                case 4 -> listarAlugueisAtivos(aluguelService);
                case 5 -> finalizarAluguel(aluguelService, scanner);
                case 6 -> listarTodosVeiculos(veiculoService);
                case 7 -> exibirRelatorios(relatorioService);
                case 8 -> System.out.println("✅ Obrigado por usar nossa locadora!");
                default -> System.out.println("❌ Opção inválida!");
            }
        } while (opcao != 8);

        scanner.close();
    }

    // 🚘 LISTAR VEÍCULOS DISPONÍVEIS
    private static void listarVeiculosDisponiveis(VeiculoService veiculoService) {
        System.out.println("\n=== VEÍCULOS DISPONÍVEIS ===");
        List<Veiculo> veiculos = veiculoService.listarDisponiveis();

        if (veiculos.isEmpty()) {
            System.out.println("Nenhum veículo disponível no momento.");
            return;
        }

        System.out.println("ID | Modelo           | Placa     | Categoria     | Preço/Dia");
        System.out.println("---|------------------|-----------|---------------|-----------");
        for (Veiculo veiculo : veiculos) {
            System.out.printf("%-2d | %-16s | %-9s | %-12s | R$ %-6.2f%n",
                    veiculo.getId(), veiculo.getModelo(), veiculo.getPlaca(),
                    veiculo.getCategoria().getDescricao(), veiculo.getPrecoDiario());
        }
    }

    // 👤 CADASTRAR CLIENTE
    private static void cadastrarCliente(ClienteService clienteService, Scanner scanner) {
        System.out.println("\n=== CADASTRAR CLIENTE ===");

        scanner.nextLine(); // Limpar buffer
        System.out.print("CPF: ");
        String cpf = scanner.nextLine();

        System.out.print("Nome: ");
        String nome = scanner.nextLine();

        System.out.print("Telefone: ");
        String telefone = scanner.nextLine();

        // ⚠️ Verifica se já existe cliente com o mesmo CPF
        if (clienteService.buscarPorCpf(cpf) != null) {
            System.out.println("❌ Cliente já cadastrado com este CPF!");
            return;
        }

        clienteService.cadastraCliente(cpf, nome, telefone);
        System.out.println("✅ Cliente cadastrado com sucesso!");
    }

    // 🔑 ALUGAR VEÍCULO
    private static void alugarVeiculo(AluguelService aluguelService, ClienteService clienteService,
                                      VeiculoService veiculoService, Scanner scanner) {
        System.out.println("\n=== NOVO ALUGUEL ===");

        // 1️⃣ Verifica cliente existente
        scanner.nextLine();
        System.out.print("CPF do cliente: ");
        String cpf = scanner.nextLine();

        Cliente cliente = clienteService.buscarPorCpf(cpf);
        if (cliente == null) {
            System.out.println("❌ Cliente não encontrado! Cadastre primeiro.");
            return;
        }

        // 2️⃣ Solicita quantidade de dias
        System.out.print("Quantos dias de aluguel: ");
        int dias = scanner.nextInt();

        // 3️⃣ Escolher veículos para o aluguel
        List<ItemAluguel> veiculosAlugados = new ArrayList<>();
        boolean continuar = true;

        while (continuar) {
            listarVeiculosDisponiveis(veiculoService);

            System.out.print("\nDigite o ID do veículo: ");
            int veiculoId = scanner.nextInt();

            Veiculo veiculo = veiculoService.buscarPorId(veiculoId);
            if (veiculo != null && veiculo.isDisponivel()) {
                veiculosAlugados.add(new ItemAluguel(veiculo));
                System.out.println("✅ " + veiculo.getModelo() + " adicionado ao aluguel!");
            } else {
                System.out.println("❌ Veículo não encontrado ou indisponível!");
            }

            System.out.print("Alugar mais veículos? (s/n): ");
            scanner.nextLine();
            continuar = scanner.nextLine().equalsIgnoreCase("s");
        }

        // 4️⃣ Cria o aluguel
        if (!veiculosAlugados.isEmpty()) {
            Aluguel aluguel = aluguelService.criarAluguel(cliente, dias, veiculosAlugados);
            System.out.printf("\n✅ Aluguel criado com sucesso!%n");
            System.out.printf("Número do Aluguel: %d%n", aluguel.getId());
            System.out.printf("Cliente: %s%n", cliente.getNome());
            System.out.printf("Dias: %d%n", dias);
            System.out.printf("Total: R$ %.2f%n", aluguel.calcularTotal());

            // 🏷️ Mostra descontos aplicados
            if (dias > 7) {
                System.out.println("💸 Desconto aplicado: " + (dias > 15 ? "20%" : "10%") + " para aluguéis longos!");
            }
        } else {
            System.out.println("❌ Nenhum veículo selecionado. Aluguel cancelado.");
        }
    }

    // 📋 LISTAR ALUGUÉIS ATIVOS
    private static void listarAlugueisAtivos(AluguelService aluguelService) {
        System.out.println("\n=== ALUGUÉIS ATIVOS ===");
        List<Aluguel> alugueis = aluguelService.listarAlugueisAtivos();

        if (alugueis.isEmpty()) {
            System.out.println("Nenhum aluguel ativo no momento.");
            return;
        }

        for (Aluguel aluguel : alugueis) {
            System.out.printf("\n📦 Aluguel #%d%n", aluguel.getId());
            System.out.printf("Cliente: %s%n", aluguel.getCliente().getNome());
            System.out.printf("Dias: %d%n", aluguel.getDias());
            System.out.printf("Veículos: ");
            for (ItemAluguel item : aluguel.getVeiculos()) {
                System.out.printf("%s ", item.getVeiculo().getModelo());
            }
            System.out.printf("%nTotal: R$ %.2f%n", aluguel.calcularTotal());
        }
    }

    // ✅ FINALIZAR ALUGUEL
    private static void finalizarAluguel(AluguelService aluguelService, Scanner scanner) {
        System.out.println("\n=== FINALIZAR ALUGUEL ===");

        List<Aluguel> alugueisAtivos = aluguelService.listarAlugueisAtivos();
        if (alugueisAtivos.isEmpty()) {
            System.out.println("Nenhum aluguel ativo para finalizar.");
            return;
        }

        // Mostra todos os aluguéis ativos
        for (Aluguel aluguel : alugueisAtivos) {
            System.out.printf("#%d - %s (%d dias)%n",
                    aluguel.getId(), aluguel.getCliente().getNome(), aluguel.getDias());
        }

        System.out.print("Digite o número do aluguel a finalizar: ");
        int aluguelId = scanner.nextInt();

        aluguelService.finalizarAluguel(aluguelId);
        System.out.println("✅ Aluguel finalizado com sucesso!");
    }

    // 🚗 LISTAR TODOS OS VEÍCULOS
    private static void listarTodosVeiculos(VeiculoService veiculoService) {
        veiculoService.listarTodosVeiculos();
    }

    // 📊 RELATÓRIOS
    private static void exibirRelatorios(RelatorioService relatorioService) {
        System.out.println("\n=== RELATÓRIOS ===");
        System.out.println("1 - Faturamento Total");
        System.out.println("2 - Veículos Mais Alugados");
        System.out.println("3 - Faturamento por Categoria");
        System.out.println("4 - Clientes Mais Fiéis");

        Scanner scanner = new Scanner(System.in);
        int opcao = scanner.nextInt();

        switch (opcao) {
            case 1 -> relatorioService.gerarRelatorioFaturamento();
            case 2 -> relatorioService.gerarRelatorioVeiculosPopulares();
            case 3 -> relatorioService.gerarRelatorioPorCategoria();
            case 4 -> relatorioService.gerarRelatorioClientesFieis();
            default -> System.out.println("❌ Opção inválida!");
        }
    }
}
```
## 💻 Como Executar

1. **Pré-requisitos**:
   - Java JDK 11 ou superior instalado.
   - IDE como IntelliJ, Eclipse ou VSCode (opcional, mas recomendado).

2. **Passos para execução**:
   1. Clone ou baixe o projeto.
   2. Abra o projeto em sua IDE ou compile via terminal:
      ```bash
      javac -d bin $(find . -name "*.java")
      ```
   3. Execute a classe principal `Main`:
      ```bash
      java -cp bin Main
      ```
   4. Siga as instruções do menu interativo no console.

3. **Observações**:
   - Todos os dados ficam em memória enquanto o programa está em execução.
   - Ao fechar o programa, os cadastros e aluguéis serão perdidos (não há persistência em banco de dados).
   - Para reiniciar o sistema com dados limpos, basta executar novamente a classe `Main`.

