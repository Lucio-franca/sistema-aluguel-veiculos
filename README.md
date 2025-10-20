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

## 🔧 Classes e Códigos

### 1. **Classe Aluguel** (`Aluguel.java`)

```java
/**
 * Classe principal que representa um aluguel no sistema
 * Controla todo o ciclo de vida do aluguel
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
    
    // Construtor - Inicializa novo aluguel
    public Aluguel(Cliente cliente, int dias) {
        this.cliente = cliente;
        this.dias = dias;
        this.veiculos = new ArrayList<>();
        this.status = StatusAluguel.ATIVO;
        this.dataAluguel = new Date(); // Data atual
        this.multa = 0.0;
    }
    
    /**
     * Adiciona veículo ao aluguel e incrementa contador
     */
    public void adicionarVeiculo(ItemAluguel veiculo) {
        veiculos.add(veiculo);
        veiculo.getVeiculo().incrementarAluguel(); // Estatísticas
    }
    
    /**
     * Calcula valor total com descontos progressivos
     * - Até 7 dias: preço normal
     * - 8-15 dias: 10% desconto
     * - +15 dias: 20% desconto
     */
    public double calcularTotal() {
        double total = 0;
        
        // Soma subtotal de todos os veículos
        for (ItemAluguel item : veiculos) {
            total += item.getSubtotal();
        }
        
        total = total * dias; // Multiplica pelos dias
        
        // Aplica descontos progressivos
        if (dias > 7 && dias <= 15) {
            total *= 0.9; // 10% de desconto
        } else if (dias > 15) {
            total *= 0.8; // 20% de desconto
        }
        
        return total;
    }
    
    /**
     * Calcula multa por atraso na devolução
     * 10% do valor total por dia de atraso
     */
    public double calcularMulta() {
        if (dataDevolucao == null) return 0.0;
        
        // Calcula data prevista para devolução
        Calendar dataPrevista = Calendar.getInstance();
        dataPrevista.setTime(dataAluguel);
        dataPrevista.add(Calendar.DAY_OF_MONTH, dias);
        
        // Data real de devolução
        Calendar dataReal = Calendar.getInstance();
        dataReal.setTime(dataDevolucao);
        
        // Verifica atraso
        if (dataReal.after(dataPrevista)) {
            long diff = dataReal.getTimeInMillis() - dataPrevista.getTimeInMillis();
            int diasAtraso = (int) (diff / (1000 * 60 * 60 * 24));
            this.multa = diasAtraso * (calcularTotal() * 0.1);
            return this.multa;
        }
        return 0.0;
    }
}

/**
 * Define os possíveis status de um aluguel
 */
public enum StatusAluguel {
    ATIVO,         // Aluguel em andamento
    FINALIZADO,    // Devolução concluída
    CANCELADO,     // Aluguel cancelado
    PENDENTE       // Aguardando confirmação
}

