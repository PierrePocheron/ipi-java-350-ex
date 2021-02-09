package com.ipiecoles.java.java350.model;

import com.ipiecoles.java.java350.model.Employe;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.time.LocalDate;

public class EmpoyeTest {

    // Correction
    @Test
    public void testCheckNbAnneeAncienneteInfNow() {
        // Given
        Employe employe = new Employe("Popo", "prenom", "0", LocalDate.now().minusYears(15), 1000d, 10, 1d);

        // When
        Integer nbAnneeAnciennete = employe.getNombreAnneeAnciennete();

        // Then (org.assertj.core.api.Assertions)
        Assertions.assertThat(nbAnneeAnciennete).isEqualTo(15);
    }

    // Correction
    @Test
    public void testCheckNbAnneeAncienneteNow() {
        // Given
        Employe employe = new Employe("Popo", "prenom", "0", LocalDate.now(), 1000d, 10, 1d);

        // When
        Integer nbAnneeAnciennete = employe.getNombreAnneeAnciennete();

        // Then (org.assertj.core.api.Assertions)
        Assertions.assertThat(nbAnneeAnciennete).isEqualTo(0);
    }

    // Correction
    @Test
    public void testCheckNbAnneeAncienneteDateEmbaucheNull() {
        // Given
        Employe employe = new Employe();
        employe.setDateEmbauche(null);

        // When
        Integer nbAnneeAnciennete = employe.getNombreAnneeAnciennete();

        // Then (org.assertj.core.api.Assertions)
        Assertions.assertThat(nbAnneeAnciennete).isNull();
    }

    @Test
    public void testCheckNbAnneeAncienneteIsPositive() {
        // Given
        Employe employe = new Employe("Popo", "prenom", "0", LocalDate.now().minusYears(15), 1000d, 10, 1d);

        // When
        int nbAnneeAnciennete = employe.getNombreAnneeAnciennete();

        // Then (org.assertj.core.api.Assertions)
        Assertions.assertThat(nbAnneeAnciennete).isPositive();
    }

    @Test
    public void testCheckNbAnneeAncienneteGreaterThan5() {
        // Given
        Employe employe = new Employe("Popo", "prenom", "0", LocalDate.now().minusYears(15), 1000d, 10, 1d);

        // When
        int nbAnneeAnciennete = employe.getNombreAnneeAnciennete();

        // Then (org.assertj.core.api.Assertions)
        Assertions.assertThat(nbAnneeAnciennete).isGreaterThan(5);
    }

    @Test
    public void testCheckNbAnneeAncienneteIsLessThan50() {
        // Given
        Employe employe = new Employe("Popo", "prenom", "0", LocalDate.now().minusYears(15), 1000d, 10, 1d);

        // When
        int nbAnneeAnciennete = employe.getNombreAnneeAnciennete();

        // Then (org.assertj.core.api.Assertions)
        Assertions.assertThat(nbAnneeAnciennete).isLessThan(50);
    }


    @Test
    public void testCheckPrimeAnnuelle(){
        // Given
        Employe employe = new Employe("Popo", "prenom", "0", LocalDate.now().minusYears(15), 1000d, 10, 1d);

        // When
        double primeAnnuelle = employe.getPrimeAnnuelle();

        // Then (org.assertj.core.api.Assertions)
        Assertions.assertThat(primeAnnuelle).isPositive();
        Assertions.assertThat(primeAnnuelle).isLessThan(100000);
        Assertions.assertThat(primeAnnuelle).isGreaterThanOrEqualTo(100);
    }

    @Test
    public void testCheckPrimeAnnuelleDefaut(){
        // Given
        Employe employe = new Employe("Popo", "prenom", "T00001", LocalDate.now().minusYears(0), 1000d, 1, 1d);

        // When
        double primeAnnuelle = employe.getPrimeAnnuelle();

        // Then (org.assertj.core.api.Assertions)
        Assertions.assertThat(primeAnnuelle).isEqualTo(1000);
    }

    @Test
    public void testCheckPrimeAnnuelleManager(){
        // Given
        Employe employe = new Employe("Popo", "prenom", "M00001", LocalDate.now().minusYears(0), 1000d, 1, 1d);

        // When
        double primeAnnuelle = employe.getPrimeAnnuelle();

        // Then (org.assertj.core.api.Assertions)
        Assertions.assertThat(primeAnnuelle).isEqualTo(1700);
    }

    @Test
    public void testCheckPrimeAnnuellePerformance5(){
        // Given
        Employe employe = new Employe("Popo", "prenom", "C00001", LocalDate.now().minusYears(0), 1000d, 5, 1d);

        // When
        double primeAnnuelle = employe.getPrimeAnnuelle();

        // Then (org.assertj.core.api.Assertions)
        Assertions.assertThat(primeAnnuelle).isEqualTo(5300);
    }

    @Test
    public void testCheckPrimeAnnuelleTempsPartiel2(){
        // Given
        Employe employe = new Employe("Popo", "prenom", "C00001", LocalDate.now().minusYears(0), 1000d, 1, 2d);

        // When
        double primeAnnuelle = employe.getPrimeAnnuelle();

        // Then (org.assertj.core.api.Assertions)
        Assertions.assertThat(primeAnnuelle).isEqualTo(2000);
    }

    @Test
    public void testCheckPrimeAnnuelleAllParametres(){
        // Given
        Employe employe = new Employe("Popo", "prenom", "M00001", LocalDate.now().minusYears(0), 10000d, 10, 10d);

        // When
        double primeAnnuelle = employe.getPrimeAnnuelle();

        // Then (org.assertj.core.api.Assertions)
        Assertions.assertThat(primeAnnuelle).isEqualTo(17000);
    }

    @Test
    public void testCheckPrimeAnnuelleManagerPerf10(){
        // Given
        Employe employe = new Employe("Popo", "prenom", "M00001", LocalDate.now().minusYears(15), 10000d, 10, 1d);

        // When
        double primeAnnuelle = employe.getPrimeAnnuelle();

        // Then (org.assertj.core.api.Assertions)
        Assertions.assertThat(primeAnnuelle).isEqualTo(3200);
    }



//    @ParameterizedTest
//    @CsvSource({
//            "'XXXXX', false",
//            "'AA-123-BB', true"
//    })
//    void testCheckBadImmatriculation(String immat, Boolean result) {
//        //Given, When, Then
//        Assertions.assertThat(Employe.getPrimeAnnuelle()).isEqualTo(result);
//    }
}
