const baseUrl = "http://localhost:8080";
const apiUrlUsers = `${baseUrl}/users`;
const apiUrlMotoristas = `${baseUrl}/drivers`;
const apiUrlCorridas = `${baseUrl}/corridas`;
const apiUrlCorridasEM_ANDAMENTO = `${baseUrl}/corridas/andamento`;
const apiUrlCorridasCONCLUIDAS = `${baseUrl}/corridas/concluidas`;

function toggleLoader(ativo) {
  const loaderElement = document.getElementById("loader"); // Supondo que você tenha um elemento com id "loader"
  if (ativo) {
    loaderElement.classList.remove("hidden"); // Mostra o loader
  } else {
    loaderElement.classList.add("hidden"); // Esconde o loader
  }
}

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
      const name = document.getElementById("nomeUser").value;
      const email = document.getElementById("emailUser").value;
      const phone = document.getElementById("telefoneUser").value;

      if (!name || !email || !phone) {
        return;
      }

      try {
        const response = await fetch(apiUrlUsers, {
          method: "POST",
          headers: {
            "Content-Type": "application/json",
          },
          body: JSON.stringify({
            name,
            email,
            phone,
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
      const name = document.getElementById("nomeMotorista").value;
      const email = document.getElementById("emailMotorista").value;
      const phone = document.getElementById("telefoneMotorista").value;
      const cnh = document.getElementById("cnhMotorista").value;
      const placa = document.getElementById("placaMotorista").value;

      if (!name || !email || !phone || !cnh || !placa) {
        return;
      }

      try {
        const response = await fetch(apiUrlMotoristas, {
          method: "POST",
          headers: {
            "Content-Type": "application/json",
          },
          body: JSON.stringify({
            name,
            email,
            phone,
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
    .addEventListener("click", listarUsers);
  async function listarUsers() {
    try {
      toggleLoader(true); // Se você tiver uma função de loader, mostre o carregamento
      const response = await fetch(apiUrlUsers);
      const data = await response.json();
      console.log(data);
      const usuarioList = document.getElementById("UserList");
      usuarioList.innerHTML = ""; // Limpa a lista antes de adicionar novos itens

      if (response.ok) {
        data.forEach((usuario) => {
          const div = document.createElement("div");
          div.textContent = `ID: ${usuario.id}, ${usuario.name}`;
          usuarioList.appendChild(div);
        });
      } else {
        alert(
          "Erro ao listar usuários: " + (data.message || "Erro inesperado")
        );
      }
    } catch (error) {
      alert("Ocorreu um erro ao tentar listar usuários: " + error.message);
    } finally {
      toggleLoader(false); // Esconde o carregamento
    }
  }

  // Listar drivers
  document
    .getElementById("btnListarMotorista")
    .addEventListener("click", listarMotoristas);
  async function listarMotoristas() {
    try {
      toggleLoader(true);
      const response = await fetch(apiUrlMotoristas, {});
      const data = await response.json();
      const motoristaList = document.getElementById("motoristaList");
      motoristaList.innerHTML = "";
      if (response.ok) {
        data.forEach((motorista) => {
          const div = document.createElement("div");
          div.textContent = `${motorista.name}, Placa: ${motorista.placa}, ${motorista.status}`;
          motoristaList.appendChild(div);
        });
      }
    } catch (error) {
      alert("Ocorreu um erro ao tentar listar: " + error.message);
    } finally {
      toggleLoader(false);
    }
  }

  // Listar corridas em ANDAMENTO
  document
    .getElementById("btnListarCorrida")
    .addEventListener("click", listarCorridas);
  async function listarCorridas() {
    try {
      toggleLoader(true);
      const response = await fetch(apiUrlCorridasEM_ANDAMENTO, {
        headers: { "Content-Type": "application/json" },
      });
      const data = await response.json();
      const corridaList = document.getElementById("corridaList");
      corridaList.innerHTML = "";
      if (response.ok) {
        data.forEach((corrida) => {
          const div = document.createElement("div");
          div.textContent = `Corrida ${corrida.id}, User ${corrida.user.id}, Driver ${corrida.driver.id}, ${corrida.status}`;
          corridaList.appendChild(div);
        });
      }
    } catch (error) {
      alert("Ocorreu um erro ao tentar listar: " + error.message);
    } finally {
      toggleLoader(false);
    }
  }

  // Listar corridas em CONCLUIDAS
  document
    .getElementById("btnListarCorridaConcluidas")
    .addEventListener("click", listarCorridasCon);
  async function listarCorridasCon() {
    try {
      toggleLoader(true);
      const response = await fetch(apiUrlCorridasCONCLUIDAS, {
        headers: { "Content-Type": "application/json" },
      });
      const data = await response.json();
      const corridaList = document.getElementById("corridaList");
      corridaList.innerHTML = "";
      if (response.ok) {
        data.forEach((corrida) => {
          const div = document.createElement("div");
          div.textContent = `Corrida ${corrida.id}, User ${corrida.user.id}, Driver ${corrida.driver.id}, ${corrida.status}`;
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

// Detalhar Corrida
document
  .getElementById("btnDetalharCorrida")
  .addEventListener("click", async function DetalharCorridas() {
    try {
      const corridaId = document.getElementById("idCorrida").value; // Pegando o ID da corrida
      const corrida = await buscarCorrida(corridaId); // Buscando a corrida com o ID

      if (!corrida) {
        console.error("Corrida não encontrada.");
        return;
      }

      const origem = corrida.origem;
      const destino = corrida.destino;
      const preco = corrida.preco; // Preço da corrida vindo do objeto 'corrida'

      // Passo 2: Obter coordenadas de origem e destino
      const origemCoordinates = await geocode(origem);
      const destinoCoordinates = await geocode(destino);

      if (!origemCoordinates || !destinoCoordinates) {
        console.error("Erro ao obter coordenadas.");
        return;
      }

      // Passo 3: Requisição da rota via API TomTom
      const routeData = await obterRotaTomTom(
        origemCoordinates,
        destinoCoordinates
      );

      if (routeData) {
        // Limpar a lista de detalhes da corrida antes de exibir os novos dados
        const listContainer = document.getElementById("detalhecorridaList");
        listContainer.innerHTML = ""; // Limpar todos os elementos dentro da div

        // Acessando a distância corretamente dentro de 'summary'
        const distanceInMeters =
          routeData.routes[0].legs[0].summary.lengthInMeters;

        console.log("Distância em metros:", distanceInMeters); // Exibindo a distância em metros diretamente

        // Convertendo a distância para quilômetros
        const distanceInKm = (distanceInMeters / 1000).toFixed(1);

        // Criando a div para distância
        const divDistancia = document.createElement("div");
        divDistancia.textContent = `Distância: ${distanceInKm} Km`;
        listContainer.appendChild(divDistancia);

        // Criando a div para preço
        const divPreco = document.createElement("div");
        divPreco.textContent = `Preço: R$ ${preco}`;
        listContainer.appendChild(divPreco);

        // Inicializa o mapa
        var map = L.map("map").setView(origemCoordinates, 12); // Define o ponto de vista inicial no local de origem

        // Adiciona o tileLayer do OpenStreetMap
        L.tileLayer("https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png", {
          attribution:
            '&copy; <a href="https://www.openstreetmap.org/copyright">OpenStreetMap</a> contributors',
        }).addTo(map);

        // Desenha a rota no mapa se os dados estiverem corretos
        const routeCoordinates = routeData.routes[0].legs[0].points.map(
          (point) => [point.latitude, point.longitude]
        );

        L.polyline(routeCoordinates, { color: "blue", weight: 5 }).addTo(map);

        // Ajusta o mapa para os limites da rota
        map.fitBounds(L.polyline(routeCoordinates).getBounds());
      }
    } catch (error) {
      console.error("Erro geral:", error);
    }
  });

// Função para buscar corrida específica
async function buscarCorrida(id) {
  const response = await fetch(`http://localhost:8080/corridas/${id}`);
  if (!response.ok) {
    throw new Error("Erro ao buscar a corrida");
  }
  const corrida = await response.json();
  return corrida;
}

// Função para geocodificar (obter coordenadas)
async function geocode(local) {
  const apiKey = "FavFAG60A7v65P6j4vgAxOQ6qYATmwjf"; // Substitua pela sua chave do TomTom
  const url = `https://api.tomtom.com/search/2/geocode/${encodeURIComponent(
    local
  )}.json?key=${apiKey}`;

  const response = await fetch(url);
  if (!response.ok) {
    throw new Error("Erro ao buscar coordenadas");
  }

  const data = await response.json();
  if (data.results && data.results.length > 0) {
    return [data.results[0].position.lat, data.results[0].position.lon];
  } else {
    console.error("Nenhum resultado encontrado para:", local);
    return null;
  }
}

// Função para obter a rota via TomTom
async function obterRotaTomTom(origem, destino) {
  const apiKey = "FavFAG60A7v65P6j4vgAxOQ6qYATmwjf"; // Substitua pela sua chave do TomTom
  const url = `https://api.tomtom.com/routing/1/calculateRoute/${origem[0]},${origem[1]}:${destino[0]},${destino[1]}/json?key=${apiKey}`;

  const response = await fetch(url);
  if (!response.ok) {
    throw new Error("Erro ao buscar rota");
  }

  const data = await response.json();
  return data;
}
