package com.nimble.payment.utils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ValidateCpfTest {

    @Test
    void isCPF_ShouldReturnTrue_WhenValidCPF() {
        // Arrange
        String validCpf = "12345678909";

        // Act
        boolean result = ValidateCpf.isCPF(validCpf);

        // Assert
        assertTrue(result, "Expected CPF to be valid");
    }

    @Test
    void isCPF_ShouldReturnFalse_WhenInvalidCPF() {
        // Arrange
        String invalidCpf = "12345678901";

        // Act
        boolean result = ValidateCpf.isCPF(invalidCpf);

        // Assert
        assertFalse(result, "Expected CPF to be invalid");
    }

    @Test
    void isCPF_ShouldReturnFalse_WhenCPFWithAllSameDigits() {
        // Arrange
        String sameDigitsCpf = "11111111111";

        // Act
        boolean result = ValidateCpf.isCPF(sameDigitsCpf);

        // Assert
        assertFalse(result, "Expected CPF with same digits to be invalid");
    }

    @Test
    void isCPF_ShouldReturnFalse_WhenCPFWithWrongLength() {
        // Arrange
        String shortCpf = "123456789";
        String longCpf = "123456789012";

        // Act
        boolean resultShort = ValidateCpf.isCPF(shortCpf);
        boolean resultLong = ValidateCpf.isCPF(longCpf);

        // Assert
        assertFalse(resultShort, "Expected CPF with less than 11 digits to be invalid");
        assertFalse(resultLong, "Expected CPF with more than 11 digits to be invalid");
    }

    @Test
    void isCPF_ShouldReturnFalse_WhenCPFContainsNonNumericCharacters() {
        // Arrange
        String cpfWithLetters = "12345a78901";
        String cpfWithSymbols = "12345-78901";

        // Act
        boolean resultLetters = ValidateCpf.isCPF(cpfWithLetters);
        boolean resultSymbols = ValidateCpf.isCPF(cpfWithSymbols);

        // Assert
        assertFalse(resultLetters, "Expected CPF with letters to be invalid");
        assertFalse(resultSymbols, "Expected CPF with symbols to be invalid");
    }

    @Test
    void isCPF_ShouldReturnFalse_WhenCPFIsEmpty() {
        // Arrange
        String emptyCpf = "";

        // Act
        boolean result = ValidateCpf.isCPF(emptyCpf);

        // Assert
        assertFalse(result, "Expected empty CPF to be invalid");
    }

    @Test
    void isCPF_ShouldReturnFalse_WhenCPFIsNull() {
        // Arrange
        String nullCpf = null;

        // Act & Assert
        assertThrows(NullPointerException.class, () -> ValidateCpf.isCPF(nullCpf),
                "Expected NullPointerException for null CPF");
    }
}
