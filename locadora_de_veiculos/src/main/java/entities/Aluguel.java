package entities;
import java.util.List;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

// Classe que representa um Aluguel de veículos
public class Aluguel {
    // Atributos da classe
    private int id ;  // Identificador único do aluguel
    private Cliente cliente;  // Cliente que realizou o aluguel
    private List<ItemAluguel> veiculos;  // Lista de veículos alugados
    private int dias;  // Quantidade de dias do aluguel
    private StatusAluguel status;  // Status atual do aluguel (ATIVO, FINALIZADO, etc.)
    private Date dataAluguel ;  // Data em que o aluguel foi realizado
    private Date dataDevolucao;  // Data em que o veículo foi devolvido
    private double multa;  // Valor da multa por atraso na devolução

    // Métodos getters e setters - permitem acesso controlado aos atributos privados

    // Retorna o ID do aluguel
    public int getId() {
        return id;
    }

    // Define o ID do aluguel
    public void setId(int id) {
        this.id = id;
    }

    // Retorna o cliente do aluguel
    public Cliente getCliente() {
        return cliente;
    }

    // Define o cliente do aluguel
    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    // Retorna a lista de veículos alugados
    public List<ItemAluguel> getVeiculos() {
        return veiculos;
    }

    // Define a lista de veículos alugados
    public void setVeiculos(List<ItemAluguel> veiculos) {
        this.veiculos = veiculos;
    }

    // Retorna a quantidade de dias do aluguel
    public int getDias() {
        return dias;
    }

    // Define a quantidade de dias do aluguel
    public void setDias(int dias) {
        this.dias = dias;
    }

    // Retorna o status atual do aluguel
    public StatusAluguel getStatus() {
        return status;
    }

    // Define o status do aluguel
    public void setStatus(StatusAluguel status) {
        this.status = status;
    }

    // Retorna a data em que o aluguel foi realizado
    public Date getDataAluguel() {
        return dataAluguel;
    }

    // Define a data do aluguel
    public void setDataAluguel(Date dataAluguel) {
        this.dataAluguel = dataAluguel;
    }

    // Retorna a data de devolução do veículo
    public Date getDataDevolucao() {
        return dataDevolucao;
    }

    // Define a data de devolução do veículo (CORREÇÃO: faltou 'this.')
    public void setDataDevolucao(Date dataDevolucao) {
        this.dataDevolucao = dataDevolucao;  // Correção: adicionar 'this.'
    }

    // Retorna o valor da multa
    public double getMulta() {
        return multa;
    }

    // Define o valor da multa
    public void setMulta(double multa) {
        this.multa = multa;
    }

    // Construtor da classe Aluguel - inicializa um novo objeto Aluguel
    public Aluguel(Cliente cliente, int dias){
        this.cliente = cliente;  // Associa o cliente passado como parâmetro ao aluguel
        this.dias = dias ;  // Define a quantidade de dias do aluguel
        this.veiculos = new ArrayList<>(); // Cria uma lista vazia para armazenar os veículos alugados
        this.status = StatusAluguel.ATIVO; // Define o status inicial do aluguel como ATIVO
        this.dataAluguel = new Date(); // Registra a data/hora atual como data do aluguel
        this.multa = 0.0; // Inicializa o valor da multa como zero
    }

    //Método para adicionar um veículo ao aluguel atual
    public void adicionarVeiculo(ItemAluguel veiculo){
        veiculos.add(veiculo);  // Adiciona o veículo à lista de veículos do aluguel
        veiculo.getVeiculo().incrementarAluguel();  // Incrementa o contador de aluguéis do veículo
    }

    // Calcula o valor total do aluguel aplicando descontos progressivos
    public double calcularTotal() {
        double total = 0;
        // Soma o subtotal de todos os veículos alugados
        for (ItemAluguel item : veiculos) {
            total += item.getSubtotal();
        }

        total = total * dias;  // Multiplica pelo número de dias

        // Aplica descontos progressivos conforme a quantidade de dias
        if (dias > 7 && dias <= 15) {
            total *= 0.9; // Aplica 10% de desconto para aluguéis entre 8 e 15 dias
        } else if (dias > 15) {
            total *= 0.8; // Aplica 20% de desconto para aluguéis acima de 15 dias
        }

        return total;
    }

    // Calcula o valor da multa por atraso na devolução
    public double calcularMulta() {
        if (dataDevolucao == null) return 0.0;  // Se não há data de devolução, não há multa

        // Calcula a data prevista para devolução (data do aluguel + dias contratados)
        Calendar dataPrevista = Calendar.getInstance();
        dataPrevista.setTime(dataAluguel);
        dataPrevista.add(Calendar.DAY_OF_MONTH, dias);

        // Obtém a data real de devolução
        Calendar dataReal = Calendar.getInstance();
        dataReal.setTime(dataDevolucao);

        // Verifica se houve atraso na devolução
        if (dataReal.after(dataPrevista)) {
            // Calcula a diferença em milissegundos entre as datas
            long diff = dataReal.getTimeInMillis() - dataPrevista.getTimeInMillis();
            // Converte milissegundos para dias de atraso
            int diasAtraso = (int) (diff / (1000 * 60 * 60 * 24));
            // Calcula a multa: 10% do valor total por dia de atraso
            this.multa = diasAtraso * (calcularTotal() * 0.1);
            return this.multa;
        }
        return 0.0;  // Sem atraso, sem multa
    }

    // Calcula o valor total final incluindo a multa (se houver)
    public double calcularTotalComMulta() {
        return calcularTotal() + calcularMulta();
    }
}