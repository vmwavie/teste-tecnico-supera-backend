package com.vmwavie.backend.constants;

public class CostumerMessage {
    public static final ErrorMessages ERROR_MESSAGES = new ErrorMessages();
    public static final SuccessMessages SUCCESS_MESSAGES = new SuccessMessages();

    public static class ErrorMessages {
        public final String COSTUMER_NOT_FOUND = "O cliente não foi encontrado.";
        public final String COSTUMER_ALREADY_EXISTS = "O cliente já existe.";

        public final String COSTUMER_INVALID_NAME = "O nome informado é inválido, por favor informe nome e sobrenome.";
        public final String COSTUMER_INVALID_CPF = "O CPF informado é inválido, por favor informe um CPF no formato: 000.000.000-00.";
        public final String COSTUMER_INVALID_WHATSAPP = "O número do Whatsapp informado é inválido, por favor informe um número no formato: (00) 0000-0000.";
    }

    public static class SuccessMessages {
        public final String COSTUMER_SAVED_SUCCESSFULLY = "Cliente criado com sucesso.";
        public final String COSTUMER_UPDATED_SUCCESSFULLY = "Cliente atualizado com sucesso.";
        public final String COSTUMER_DELETED_SUCCESSFULLY = "Cliente excluído com sucesso.";
    }
}
