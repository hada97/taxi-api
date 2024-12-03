document.addEventListener("DOMContentLoaded", () => {
  // Alternar exibição do formulário de cadastro de usuário
  document
    .getElementById("toggleUserForm")
    .addEventListener("click", function () {
      const formContainer = document.getElementById(
        "cadastroUserFormContainer"
      );
      formContainer.classList.toggle("hidden");
    });

  // Adicionar evento ao botão de cadastro de usuário
  document
    .getElementById("cadastroUserForm")
    .addEventListener("submit", function (event) {
      event.preventDefault();
      // Lógica para enviar os dados do usuário para a API
      const nome = document.getElementById("nomeUser").value;
      const email = document.getElementById("emailUser").value;
      const telefone = document.getElementById("telefoneUser").value;

      // Simulando o envio dos dados
      console.log("Usuário cadastrado:", nome, email, telefone);

      // Limpar formulário após cadastro
      document.getElementById("cadastroUserForm").reset();
    });

  // Listar usuários
  document
    .getElementById("btnListarUser")
    .addEventListener("click", function () {
      const userList = document.getElementById("UserList");
      // Lógica para obter dados dos usuários da API
      // Aqui você pode simular a adição de usuários na lista
      userList.innerHTML = "<p>Usuário 1</p><p>Usuário 2</p>";
    });

  // Exemplo de recarregar a página
  function recarregarPagina() {
    location.reload();
  }
});

document.addEventListener("DOMContentLoaded", () => {
  // Alternar exibição dos formulários de cadastro
  document
    .getElementById("toggleMotoristaForm")
    .addEventListener("click", function () {
      const formContainer = document.getElementById(
        "cadastroMotoristaFormContainer"
      );
      formContainer.classList.toggle("hidden");
    });

  // Adicionar evento ao botão de cadastro de motorista
  document
    .getElementById("cadastroMotoristaForm")
    .addEventListener("submit", function (event) {
      event.preventDefault();
      // Lógica para enviar os dados do motorista para a API
      const nome = document.getElementById("nomeMotorista").value;
      const email = document.getElementById("emailMotorista").value;
      const telefone = document.getElementById("telefoneMotorista").value;

      // Simulando o envio dos dados
      console.log("Motorista cadastrado:", nome, email, telefone);

      // Limpar formulário após cadastro
      document.getElementById("cadastroMotoristaForm").reset();
    });

  // Listar motoristas
  document
    .getElementById("btnListarMotoristas")
    .addEventListener("click", function () {
      const motoristaList = document.getElementById("motoristaList");
      // Lógica para obter dados dos motoristas da API
      // Aqui você pode simular a adição de motoristas na lista
      motoristaList.innerHTML = "<p>Motorista 1</p><p>Motorista 2</p>";
    });

  // Cadastro de corrida
  document
    .getElementById("cadastroCorridaForm")
    .addEventListener("submit", function (event) {
      event.preventDefault();
      const origem = document.getElementById("origemCorrida").value;
      const destino = document.getElementById("destinoCorrida").value;

      // Simulação de envio de dados da corrida
      console.log("Corrida cadastrada:", origem, destino);

      // Limpar o formulário após envio
      document.getElementById("cadastroCorridaForm").reset();
    });

  // Exemplo de recarregar a página
  function recarregarPagina() {
    location.reload();
  }
});

document.addEventListener("DOMContentLoaded", () => {
  // Alternar exibição do formulário de cadastro de corrida
  document
    .getElementById("toggleCorridaForm")
    .addEventListener("click", function () {
      const formContainer = document.getElementById(
        "cadastroCorridaFormContainer"
      );
      formContainer.classList.toggle("hidden");
    });

  // Adicionar evento ao botão de cadastro de corrida
  document
    .getElementById("cadastroCorridaForm")
    .addEventListener("submit", function (event) {
      event.preventDefault();
      // Lógica para enviar os dados da corrida para a API
      const idUser = document.getElementById("idUser").value;
      const origem = document.getElementById("origemCorrida").value;
      const destino = document.getElementById("destinoCorrida").value;

      // Simulando o envio dos dados
      console.log("Corrida cadastrada:", idUser, origem, destino);

      // Limpar formulário após cadastro
      document.getElementById("cadastroCorridaForm").reset();
    });

  // Exemplo de recarregar a página
  function recarregarPagina() {
    location.reload();
  }
});
