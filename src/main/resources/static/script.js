const baseUrl = "http://localhost:8080";
const apiUrlUsers = `${baseUrl}/users`;
const apiUrlMotoristas = `${baseUrl}/drivers`;
const apiUrlCorridas = `${baseUrl}/corridas`;

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

  // Alternar exibição do formulário de cadastro de motorista
  document
    .getElementById("toggleMotoristaForm")
    .addEventListener("click", function () {
      const formContainer = document.getElementById(
        "cadastroMotoristaFormContainer"
      );
      formContainer.classList.toggle("hidden");
    });

  // Alternar exibição do formulário de cadastro de corrida
  document
    .getElementById("toggleCorridaForm")
    .addEventListener("click", function () {
      const formContainer = document.getElementById(
        "cadastroCorridaFormContainer"
      );
      formContainer.classList.toggle("hidden");
    });

  // Cadastro de User
  document
    .getElementById("cadastroUserForm")
    .addEventListener("submit", async function (event) {
      event.preventDefault();
      const nome = document.getElementById("nomeUser").value;
      const email = document.getElementById("emailUser").value;
      const telefone = document.getElementById("telefoneUser").value;

      if (!nome || !email || !telefone) {
        return;
      }

      try {
        const response = await fetch(apiUrlUsers, {
          method: "POST",
          headers: {
            "Content-Type": "application/json",
          },
          body: JSON.stringify({
            nome,
            email,
            telefone,
          }),
        });

        if (response.ok) {
          listarUsers(); // Função para listar os usuários cadastrados
          alert("Usuário cadastrado com sucesso!");
          document.getElementById("cadastroUserForm").reset();
        } else {
          const data = await response.json();
          alert("Erro: " + data.message);
        }
      } catch (error) {
        alert("Ocorreu um erro ao tentar cadastrar: " + error.message);
      }
    });

  // Cadastro de Motorista
  document
    .getElementById("cadastroMotoristaForm")
    .addEventListener("submit", async function (event) {
      event.preventDefault();
      const nome = document.getElementById("nomeMotorista").value;
      const email = document.getElementById("emailMotorista").value;
      const telefone = document.getElementById("telefoneMotorista").value;
      const cnh = document.getElementById("cnhMotorista").value;
      const placa = document.getElementById("placaMotorista").value;

      if (!nome || !email || !telefone || !cnh || !placa) {
        return;
      }

      try {
        const response = await fetch(apiUrlMotoristas, {
          method: "POST",
          headers: {
            "Content-Type": "application/json",
          },
          body: JSON.stringify({
            nome,
            email,
            telefone,
            cnh,
            placa,
          }),
        });

        if (response.ok) {
          listarMotoristas(); // Função para listar os motoristas cadastrados
          alert("Motorista cadastrado com sucesso!");
          document.getElementById("cadastroMotoristaForm").reset();
        } else {
          const data = await response.json();
          alert("Erro: " + data.message);
        }
      } catch (error) {
        alert("Ocorreu um erro ao tentar cadastrar: " + error.message);
      }
    });

  // Cadastro de Corrida
  document
    .getElementById("cadastroCorridaForm")
    .addEventListener("submit", async function (event) {
      event.preventDefault();
      const idUser = document.getElementById("idUser").value;
      const origem = document.getElementById("origemCorrida").value;
      const destino = document.getElementById("destinoCorrida").value;

      if (!idUser || !origem || !destino) {
        return;
      }

      try {
        const response = await fetch(apiUrlCorridas, {
          method: "POST",
          headers: {
            "Content-Type": "application/json",
          },
          body: JSON.stringify({
            idUser,
            origem,
            destino,
          }),
        });

        if (response.ok) {
          listarCorridas(); // Função para listar as corridas cadastradas
          alert("Corrida cadastrada com sucesso!");
          document.getElementById("cadastroCorridaForm").reset();
        } else {
          const data = await response.json();
          alert("Erro: " + data.message);
        }
      } catch (error) {
        alert("Ocorreu um erro ao tentar cadastrar: " + error.message);
      }
    });

  document
    .getElementById("btnListarUser")
    .addEventListener("click", listarUsuarios);
  async function listarUsuarios() {
    try {
      toggleLoader(true);
      const response = await fetch(apiUrlUsers, {
        headers: { Authorization: `Bearer ${token}` },
      });
      const data = await response.json();
      const usuarioList = document.getElementById("UserList");
      usuarioList.innerHTML = "";
      if (response.ok) {
        data.content.forEach((usuario) => {
          const div = document.createElement("div");
          div.textContent = `ID: ${usuario.id}, ${usuario.nome}, email: ${usuario.email}, Telefone: ${usuario.telefone}`;
          usuarioList.appendChild(div);
        });
      }
    } catch (error) {
      alert("Ocorreu um erro ao tentar listar: " + error.message);
    } finally {
      toggleLoader(false);
    }
  }

  document
    .getElementById("btnListarMotorista")
    .addEventListener("click", listarMotoristas);
  async function listarMotoristas() {
    try {
      toggleLoader(true);
      const response = await fetch(apiUrlMotoristas, {
        headers: { Authorization: `Bearer ${token}` },
      });
      const data = await response.json();
      const motoristaList = document.getElementById("MotoristaList");
      motoristaList.innerHTML = "";
      if (response.ok) {
        data.content.forEach((motorista) => {
          const div = document.createElement("div");
          div.textContent = `Nome: ${motorista.nome}, Email: ${motorista.email}, Telefone: ${motorista.telefone}, CNH: ${motorista.cnh}, Placa: ${motorista.placa}`;
          motoristaList.appendChild(div);
        });
      }
    } catch (error) {
      alert("Ocorreu um erro ao tentar listar: " + error.message);
    } finally {
      toggleLoader(false);
    }
  }

  // Listar corridas
  document
    .getElementById("btnListarCorrida")
    .addEventListener("click", listarCorridas);
  async function listarCorridas() {
    try {
      toggleLoader(true);
      const response = await fetch(apiUrlCorridas, {
        headers: { "Content-Type": "application/json" },
      });
      const data = await response.json();
      const corridaList = document.getElementById("corridaList");
      corridaList.innerHTML = "";
      if (response.ok) {
        data.content.forEach((corrida) => {
          const div = document.createElement("div");
          div.textContent = `ID do Usuário: ${corrida.idUser}, Origem: ${corrida.origem}, Destino: ${corrida.destino}`;
          corridaList.appendChild(div);
        });
      }
    } catch (error) {
      alert("Ocorreu um erro ao tentar listar: " + error.message);
    } finally {
      toggleLoader(false);
    }
  }

  // Exemplo de recarregar a página
  function recarregarPagina() {
    location.reload();
  }
});
