# Sistema de Aluguel de Veículos 🚗

Backend para sistema de aluguel de carros em Java OOP. Features: CRUD completo, cálculos dinâmicos, status de aluguéis, histórico e relatórios.

## 📋 Índice
- [Visão Geral](#visão-geral)
- [Funcionamento do Sistema](#funcionamento-do-sistema)
- [Estrutura do Projeto](#estrutura-do-projeto)
- [Classes e Códigos](#classes-e-códigos)
- [Como Executar](#como-executar)
- [Funcionalidades](#funcionalidades)

## 🎯 Visão Geral

Sistema completo para gestão de locadora de veículos desenvolvido em Java seguindo os princípios de Orientação a Objetos. Controla todo o ciclo de aluguel desde a reserva até a devolução com cálculo automático de valores e multas.

## ⚙️ Funcionamento do Sistema

### Fluxo Principal:
1. **Cadastro** → Clientes e veículos são cadastrados no sistema
2. **Aluguel** → Cliente seleciona veículos e define período
3. **Cálculo** → Sistema calcula valor total com descontos progressivos
4. **Devolução** → Veículo é devolvido e multas são calculadas se houver atraso
5. **Relatórios** → Geração de históricos e estatísticas

## 📁 Estrutura do Projeto
sistema-aluguel-veiculos/
├── src/
│   ├── entities/
│   │   ├── Aluguel.java
│   │   ├── Cliente.java
│   │   ├── Veiculo.java
│   │   ├── ItemAluguel.java
│   │   ├── StatusAluguel.java
│   │   └── TipoVeiculo.java
│   ├── services/
│   │   ├── AluguelService.java
│   │   └── VeiculoService.java
│   ├── repositories/
│   │   ├── AluguelRepository.java
│   │   └── VeiculoRepository.java
│   └── Main.java
├── README.md
└── LICENSE

## 🔧 Classes e Códigos

## 🏗️ Entities
### 1. **Enum StatusAluguel** (`StatusAluguel.java`)

/**
 * Define os possíveis status de um aluguel
 */
public enum StatusAluguel {
    ATIVO,         // Aluguel em andamento
    FINALIZADO,    // Devolução concluída
    CANCELADO,     // Aluguel cancelado
    PENDENTE       // Aguardando confirmação
}
### 2. **TipoVeiculo** (`TipoVeiculo.java`)
/**
 * Define os tipos de veículos disponíveis no sistema
 */
public enum TipoVeiculo {
    CARRO,
    MOTO,
    CAMINHAO,
    SUV,
    SEDAN
}
### 3. **Veiculo ** (`Veiculo.java`)
import java.util.Date;

/**
 * Representa um veículo que pode ser alugado
 */
public class Veiculo {
    private int id;
    private String modelo;
    private String placa;
    private String marca;
    private int ano;
    private TipoVeiculo tipo;
    private double valorDiaria;
    private boolean disponivel;
    private int totalAlugueis;
    private Date dataCadastro;
    
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
    
    public void incrementarAluguel() {
        this.totalAlugueis++;
    }
    
    public void alugar() {
        this.disponivel = false;
    }
    
    public void devolver() {
        this.disponivel = true;
    }
    
    // Getters e Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getModelo() { return modelo; }
    public void setModelo(String modelo) { this.modelo = modelo; }
    public String getPlaca() { return placa; }
    public void setPlaca(String placa) { this.placa = placa; }
    public String getMarca() { return marca; }
    public void setMarca(String marca) { this.marca = marca; }
    public int getAno() { return ano; }
    public void setAno(int ano) { this.ano = ano; }
    public TipoVeiculo getTipo() { return tipo; }
    public void setTipo(TipoVeiculo tipo) { this.tipo = tipo; }
    public double getValorDiaria() { return valorDiaria; }
    public void setValorDiaria(double valorDiaria) { this.valorDiaria = valorDiaria; }
    public boolean isDisponivel() { return disponivel; }
    public void setDisponivel(boolean disponivel) { this.disponivel = disponivel; }
    public int getTotalAlugueis() { return totalAlugueis; }
    public void setTotalAlugueis(int totalAlugueis) { this.totalAlugueis = totalAlugueis; }
    public Date getDataCadastro() { return dataCadastro; }
    public void setDataCadastro(Date dataCadastro) { this.dataCadastro = dataCadastro; }
}
### 4. **Cliente ** (`Cliente.java`)
import java.util.Date;

/**
 * Representa um cliente que pode alugar veículos
 */
public class Cliente {
    private int id;
    private String nome;
    private String cpf;
    private String email;
    private String telefone;
    private String endereco;
    private Date dataCadastro;
    private int totalAlugueis;
    
    public Cliente(String nome, String cpf, String email, String telefone) {
        this.nome = nome;
        this.cpf = cpf;
        this.email = email;
        this.telefone = telefone;
        this.dataCadastro = new Date();
        this.totalAlugueis = 0;
    }
    
    public void incrementarAluguel() {
        this.totalAlugueis++;
    }
    
    // Getters e Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getCpf() { return cpf; }
    public void setCpf(String cpf) { this.cpf = cpf; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getTelefone() { return telefone; }
    public void setTelefone(String telefone) { this.telefone = telefone; }
    public String getEndereco() { return endereco; }
    public void setEndereco(String endereco) { this.endereco = endereco; }
    public Date getDataCadastro() { return dataCadastro; }
    public void setDataCadastro(Date dataCadastro) { this.dataCadastro = dataCadastro; }
    public int getTotalAlugueis() { return totalAlugueis; }
    public void setTotalAlugueis(int totalAlugueis) { this.totalAlugueis = totalAlugueis; }
}
### 5. **ItemAluguel ** (`ItemAluguel.java`)
/**
 * Representa um item (veículo) dentro de um aluguel
 */
public class ItemAluguel {
    private Veiculo veiculo;
    private int quantidade;
    private double valorDiaria;
    
    public ItemAluguel(Veiculo veiculo, int quantidade, double valorDiaria) {
        this.veiculo = veiculo;
        this.quantidade = quantidade;
        this.valorDiaria = valorDiaria;
    }
    
    public double getSubtotal() {
        return valorDiaria * quantidade;
    }
    
    // Getters e Setters
    public Veiculo getVeiculo() { return veiculo; }
    public void setVeiculo(Veiculo veiculo) { this.veiculo = veiculo; }
    public int getQuantidade() { return quantidade; }
    public void setQuantidade(int quantidade) { this.quantidade = quantidade; }
    public double getValorDiaria() { return valorDiaria; }
    public void setValorDiaria(double valorDiaria) { this.valorDiaria = valorDiaria; }
}
### 6. **Aluguel ** (`Aluguel.java`)
import java.util.List;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Classe principal que representa um aluguel no sistema
 */
public class Aluguel {
    private int id;
    private Cliente cliente;
    private List<ItemAluguel> veiculos;
    private int dias;
    private StatusAluguel status;
    private Date dataAluguel;
    private Date dataDevolucao;
    private double multa;
    
    public Aluguel(Cliente cliente, int dias) {
        this.cliente = cliente;
        this.dias = dias;
        this.veiculos = new ArrayList<>();
        this.status = StatusAluguel.ATIVO;
        this.dataAluguel = new Date();
        this.multa = 0.0;
    }
    
    public void adicionarVeiculo(ItemAluguel veiculo) {
        veiculos.add(veiculo);
        veiculo.getVeiculo().incrementarAluguel();
        veiculo.getVeiculo().alugar();
    }
    
    public void finalizarAluguel() {
        this.dataDevolucao = new Date();
        this.status = StatusAluguel.FINALIZADO;
        calcularMulta();
        
        for (ItemAluguel item : veiculos) {
            item.getVeiculo().devolver();
        }
        
        cliente.incrementarAluguel();
    }
    
    public double calcularTotal() {
        double total = 0;
        
        for (ItemAluguel item : veiculos) {
            total += item.getSubtotal();
        }
        
        total = total * dias;
        
        if (dias > 7 && dias <= 15) {
            total *= 0.9;
        } else if (dias > 15) {
            total *= 0.8;
        }
        
        return total;
    }
    
    public double calcularMulta() {
        if (dataDevolucao == null) return 0.0;
        
        Calendar dataPrevista = Calendar.getInstance();
        dataPrevista.setTime(dataAluguel);
        dataPrevista.add(Calendar.DAY_OF_MONTH, dias);
        
        Calendar dataReal = Calendar.getInstance();
        dataReal.setTime(dataDevolucao);
        
        if (dataReal.after(dataPrevista)) {
            long diff = dataReal.getTimeInMillis() - dataPrevista.getTimeInMillis();
            int diasAtraso = (int) (diff / (1000 * 60 * 60 * 24));
            this.multa = diasAtraso * (calcularTotal() * 0.1);
            return this.multa;
        }
        return 0.0;
    }
    
    public double calcularTotalComMulta() {
        return calcularTotal() + calcularMulta();
    }
    
    // Getters e Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public Cliente getCliente() { return cliente; }
    public void setCliente(Cliente cliente) { this.cliente = cliente; }
    public List<ItemAluguel> getVeiculos() { return veiculos; }
    public void setVeiculos(List<ItemAluguel> veiculos) { this.veiculos = veiculos; }
    public int getDias() { return dias; }
    public void setDias(int dias) { this.dias = dias; }
    public StatusAluguel getStatus() { return status; }
    public void setStatus(StatusAluguel status) { this.status = status; }
    public Date getDataAluguel() { return dataAluguel; }
    public void setDataAluguel(Date dataAluguel) { this.dataAluguel = dataAluguel; }
    public Date getDataDevolucao() { return dataDevolucao; }
    public void setDataDevolucao(Date dataDevolucao) { this.dataDevolucao = dataDevolucao; }
    public double getMulta() { return multa; }
    public void setMulta(double multa) { this.multa = multa; }
}
