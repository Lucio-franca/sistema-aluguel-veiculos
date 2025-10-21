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
