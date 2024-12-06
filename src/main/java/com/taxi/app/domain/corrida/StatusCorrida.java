// StatusCorrida.java
package com.taxi.app.domain.corrida;

public enum StatusCorrida {

        PENDENTE,
        ANDAMENTO,
        CONCLUIDA,
        CANCELADA;

        // Adicionando um método para facilitar o controle dos status
        public static StatusCorrida getStatusByString(String status) {
                for (StatusCorrida s : StatusCorrida.values()) {
                        if (s.name().equalsIgnoreCase(status)) {
                                return s;
                        }
                }
                return PENDENTE; // Retorna PENDENTE por padrão
        }
}
