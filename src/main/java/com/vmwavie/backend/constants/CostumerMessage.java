package com.vmwavie.backend.constants;

public class CostumerMessage {
    public static final ErrorMessages ERROR_MESSAGES = new ErrorMessages();
    public static final SuccessMessages SUCCESS_MESSAGES = new SuccessMessages();

    public static class ErrorMessages {
        public final String COSTUMER_NOT_FOUND = "The customer was not found.";
        public final String COSTUMER_ALREADY_EXISTS = "The customer already exists.";

        public final String COSTUMER_INVALID_NAME = "The provided name is invalid, please provide first and last name.";
        public final String COSTUMER_INVALID_CPF = "The provided CPF is invalid, please provide a CPF in the format: 00000000000.";
        public final String COSTUMER_INVALID_WHATSAPP = "The provided Whatsapp number is invalid, please provide a number in the format: (00) 0000-0000.";
    }

    public static class SuccessMessages {
        public final String COSTUMER_SAVED_SUCCESSFULLY = "Customer created successfully.";
        public final String COSTUMER_UPDATED_SUCCESSFULLY = "Customer updated successfully.";
        public final String COSTUMER_DELETED_SUCCESSFULLY = "Customer deleted successfully.";
    }
}