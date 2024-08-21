package com.vmwavie.backend.utils;

import java.util.regex.Pattern;

public class Validations {

    /**
     * This function should validate if a given CPF is valid.

     * @param cpf String representing the CPF without special character ex: 12312312399.
     * @return true if the CPF is valid, false otherwise.
     */
    public static boolean isValidCPF(String cpf) {
        if (cpf == null || cpf.length() != 11 || cpf.matches("(\\d)\\1{10}")) {
            return false;
        }

        try {
            int sum = 0;
            for (int i = 0; i < 9; i++) {
                sum += (cpf.charAt(i) - '0') * (10 - i);
            }
            int firstCheckDigit = 11 - (sum % 11);
            if (firstCheckDigit >= 10) {
                firstCheckDigit = 0;
            }

            sum = 0;
            for (int i = 0; i < 10; i++) {
                sum += (cpf.charAt(i) - '0') * (11 - i);
            }
            int secondCheckDigit = 11 - (sum % 11);
            if (secondCheckDigit >= 10) {
                secondCheckDigit = 0;
            }

            return cpf.charAt(9) - '0' == firstCheckDigit && cpf.charAt(10) - '0' == secondCheckDigit;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * This function should validate if a given Whatsapp phone number is valid.

     * @param phoneNumber Long representing the Whatsapp phone number without special character ex: 46999999999.
     * @return true if the Whatsapp phone number is valid, false otherwise.
     *
     */
    public static boolean isValidWhatsappPhoneNumber(Long phoneNumber) {
        String phoneNumberStr = String.valueOf(phoneNumber);
        String regex = "^\\(?(\\d{2})\\)?[-.\\s]?\\d{4,5}[-.\\s]?\\d{4}$";
        Pattern pattern = Pattern.compile(regex);

        return pattern.matcher(phoneNumberStr).matches();
    }

    /**
     * This function should valid if a given Name have first name and last name.
     *
     * @param name String representing the Name.
     * @return true if the Name have first name and last name, false otherwise.
     */
    public static boolean isValidName(String name) {
        if (name == null || name.trim().isEmpty()) {
            return false;
        }
        String[] words = name.trim().split("\\s+");
        return words.length >= 2;
    }
}