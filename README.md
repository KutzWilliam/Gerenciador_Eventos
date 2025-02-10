# Sistema de Gerenciamento de Eventos

Este é o repositório do Sistema de Gerenciamento de Eventos, um sistema que permite aos administradores gerenciar eventos e aos participantes realizarem inscrições, acompanharem participações e gerarem relatórios. O sistema utiliza a arquitetura em camadas (MVC), programação concorrente com Threads, e um banco de dados PostgreSQL.

## Tecnologias Utilizadas

- **Linguagem:** Java
- **Interface Gráfica:** JFrame (Swing)
- **Banco de Dados:** PostgreSQL
- **Arquitetura:** Camadas (Gui, Service, Entities, Dao)
- **Programação Concorrente:** Threads para processamento paralelo

## Estrutura do Projeto

### Camada de Apresentação (Gui)
- `LoginView.java` – Tela de login e autenticação.
- `CadastroUsuarioView.java` – Tela para cadastro de novos usuários.
- `CadastroParticipanteView.java` – Tela para inserir dados de usuários Participante.
- `CadastroAdministradorView.java` – Tela para inserir dados de usuários Administradores.
- `EventoView.java` – Tela para administração de eventos.
- `EventoForm.java` – Tela para cadastro de eventos.
- `InscricaoEventosView.java` – Tela para inscrição em eventos.
- `InscricaoCandidatoView.java` – Tela para visualização de eventos inscritos.
- `RelatoriosView.java` – Tela para geração de relatórios.

### Camada de Controle (Service)
- `UsuarioController.java`
- `EventoController.java`
- `InscricaoController.java`
- `RelatorioController.java`

### Camada de Modelo (Entities)
- `Usuario.java`
- `Administrador.java`
- `Participante.java`
- `Evento.java`
- `Inscricao.java`
- `Relatorio.java`

### Camada de Persistência (DAO)
- `DAO.java`
- `UsuarioDAO.java`
- `EventoDAO.java`
- `InscricaoDAO.java`
- `RelatorioDAO.java`

## Instruções para Execução

### 1. Banco de Dados

O banco de dados utilizado é o PostgreSQL, e deve ser nomeado como **"eventos"**. O script para criar as tabelas necessárias está localizado na pasta `libs`.

1. **Criação do Banco de Dados:**
   - Crie um banco de dados PostgreSQL com o nome **eventos**.
   - Execute o script presente na pasta `libs` para criar as tabelas necessárias.

2. **Configuração de Conexão com o Banco de Dados:**
   - A classe `DAO.java` é responsável pela conexão com o banco de dados. Edite essa classe com o seu usuário e senha local do PostgreSQL.
   - Certifique-se de que o banco de dados esteja rodando e que você tenha acesso a ele.

### 2. Dependências

Na pasta `libs`, você encontrará os arquivos `.jar` necessários para rodar o projeto. Certifique-se de adicioná-los ao classpath do seu projeto para garantir que todas as dependências sejam resolvidas corretamente.

### 3. Execução do Projeto

1. **Tela Inicial:** A aplicação inicia na tela de login, que é gerenciada pela classe `LoginView.java`. A partir desta tela, os usuários poderão acessar outras funcionalidades do sistema de acordo com seu tipo de usuário (Administrador ou Participante).
   
2. **Autenticação de Usuários:** Para fazer login, é necessário informar um e-mail e senha válidos. Caso o usuário não tenha uma conta, ele pode se cadastrar na tela de cadastro.

3. **Funcionalidades de Administradores:** Os administradores podem gerenciar eventos (criar, atualizar, excluir), visualizar relatórios e controlar inscrições.

4. **Funcionalidades de Participantes:** Os participantes podem se inscrever em eventos, cancelar inscrições antes do evento e visualizar os eventos aos quais estão inscritos.

### 4. Programação Concorrente

O sistema utiliza **Threads** para garantir que operações como exportação de relatórios, atualização de status de eventos e controle simultâneo de inscrições sejam feitas de forma eficiente e sem bloquear a interface do usuário.

## Funcionalidades

- **Cadastro e autenticação de usuários** com validação de dados.
- **Gerenciamento de eventos**: CRUD de eventos, controle de capacidade e status.
- **Inscrições em eventos**: Inscrição em eventos, cancelamento antes do evento iniciar e confirmação de presença.
- **Relatórios**: Relatórios detalhados para administradores e participantes, com opção de exportação para .xls.

## Conclusão

O Sistema de Gerenciamento de Eventos segue a arquitetura em camadas, garantindo uma estrutura organizada e escalável. A utilização de Threads permite que operações demoradas sejam realizadas de forma paralela, mantendo a interface do usuário responsiva. Certifique-se de configurar corretamente o banco de dados e as dependências para que o sistema funcione corretamente.

## Licença

Este projeto está licenciado sob a Licença MIT. Veja o arquivo [LICENSE](LICENSE) para mais detalhes.