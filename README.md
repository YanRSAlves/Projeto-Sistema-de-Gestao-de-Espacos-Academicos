# Sistema de Gestão de Espaços Acadêmicos

*(logo)*

---

## 1. Descrição do Projeto

Este é o projeto final da disciplina de Linguagem de Programação 2, um sistema completo para gerenciar a utilização e reserva de espaços em uma instituição de ensino. A aplicação foi desenvolvida em Java, utilizando conceitos de Programação Orientada a Objetos, persistência de dados com um banco de dados relacional e uma interface gráfica para o usuário.

---

## 2. Funcionalidades

O sistema oferece diferentes funcionalidades baseadas no nível de acesso do usuário:

#### Administrador
-   **Gerenciamento de Usuários:** Criar, listar, atualizar e excluir usuários do sistema.
-   **Gerenciamento de Espaços:** Criar, listar, atualizar e excluir espaços (salas, laboratórios, auditórios).
-   **Visualização Completa:** Acesso a todas as reservas cadastradas no sistema.
-   **Relatórios e Exportação:** Geração de relatórios estatísticos de uso e exportação de dados de reservas para o formato CSV.

#### Usuário Comum
-   **Cadastro e Login:** Autenticação segura e autocadastro no sistema.
-   **Consulta de Espaços:** Visualização de todos os espaços disponíveis para reserva.
-   **Realização de Reservas:** Capacidade de reservar um espaço em uma data e horário específicos, com verificação de conflitos.
-   **Consulta Pessoal:** Visualização de seu próprio histórico de reservas.

---

## 3. Tecnologias Utilizadas

-   **Linguagem:** Java
-   **Interface Gráfica (GUI):** Swing
-   **Banco de Dados:** SQLite
-   **IDE de Desenvolvimento:** NetBeans

---

## 4. Manual de Uso

Esta seção descreve o passo a passo para utilizar as principais funcionalidades do sistema.

#### 4.1. Login e Cadastro

Ao iniciar a aplicação, a tela de login é exibida.

![Tela de Login](https://i.imgur.com/k6b3YyW.png)

* **Para Entrar:** Insira seu email e senha nos campos correspondentes e clique no botão **"Entrar"**. Se as credenciais estiverem corretas, o sistema abrirá o painel correspondente ao seu nível de acesso (Administrador ou Usuário).
* **Para se Cadastrar:** Se você for um novo usuário, clique no botão **"Cadastrar-se"**. Uma nova janela se abrirá para que você possa criar sua conta.

#### 4.2. Painel do Administrador

Ao fazer o login com uma conta de administrador, o usuário tem acesso ao painel de controle completo do sistema.

![Painel do Administrador](https://i.imgur.com/8i9b7gR.png)

O menu lateral permite ao administrador executar todas as funções de gerenciamento:

* **Listar (Usuários, Espaços, Reservas):** Ao clicar em um desses botões, uma tabela com os dados correspondentes é exibida na área principal da janela.
* **Criar Novo Espaço:** Abre um formulário em uma nova janela para cadastrar um novo espaço no sistema.
* **Atualizar/Excluir (Usuário ou Espaço):** Para usar estas funções, o administrador deve primeiro listar os itens (usuários ou espaços), selecionar uma linha na tabela e, em seguida, clicar no botão de ação desejado. O sistema abrirá uma janela de edição ou uma caixa de diálogo para confirmação.

#### 4.3. Cadastrando um Novo Espaço

No painel do administrador, ao clicar em "Criar Novo Espaço", uma janela de formulário é aberta para que os detalhes do novo local sejam inseridos.

![Tela de Cadastro de Espaço](https://i.imgur.com/vH3j05c.png)

O administrador deve preencher os campos com as informações do espaço. Campos como "Equipamentos" ou "Possui Projetor" são utilizados para tipos específicos de espaços, como Laboratórios ou Auditórios. Ao clicar em "Salvar", o novo espaço é adicionado ao banco de dados e fica disponível para reservas.

#### 4.4. Painel do Usuário

Usuários comuns têm acesso a um painel mais simplificado, focado nas funcionalidades de consulta e reserva.

![Painel do Usuário](https://i.imgur.com/K3Zl71h.png)

* **Ver Espaços Disponíveis:** Exibe uma tabela com todos os espaços cadastrados no sistema.
* **Fazer Nova Reserva:** Para reservar um espaço, o usuário deve primeiro selecioná-lo na tabela e, em seguida, clicar neste botão.
* **Ver Minhas Reservas:** Mostra uma lista com todas as reservas já realizadas pelo usuário logado.

#### 4.5. Realizando uma Reserva

Após selecionar um espaço na tabela e clicar em "Fazer Nova Reserva", a seguinte janela é aberta para que o usuário informe os detalhes do agendamento.

![Tela de Nova Reserva](https://i.imgur.com/Kqf4b3L.png)

O usuário deve preencher a data e os horários de início e fim. O sistema automaticamente verificará se há conflitos com outras reservas existentes para aquele espaço. Se o horário estiver livre, a reserva é confirmada e adicionada à lista de "Minhas Reservas".

## 5. Desenvolvedores

Este projeto foi desenvolvido por:

* Yan
* Pedro
* Paulo
