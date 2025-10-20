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

### 🧩 **1. Enum `StatusAluguel`**
Define os **possíveis estados** de um aluguel no sistema.

```java
 * Define os possíveis status de um aluguel

public enum StatusAluguel {
    ATIVO,         // Aluguel em andamento
    FINALIZADO,    // Devolução concluída
    CANCELADO,     // Aluguel cancelado
    PENDENTE       // Aguardando confirmação
}



