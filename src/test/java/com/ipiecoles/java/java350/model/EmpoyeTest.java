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

    // Correction
    @Test
    public void testCheckPrimeAnnuelleDefaut(){
        // Given
        Integer performance = 1;
        String matricule = "T12345";
        Double tauxActivite = 1.0;
        Long nbAnneeAnciennete = 0L;

        Employe employe = new Employe("Popo", "prenom", matricule, LocalDate.now().minusYears(nbAnneeAnciennete), 1000d, performance, tauxActivite);

        // When
        double primeAnnuelle = employe.getPrimeAnnuelle();

        // Then (org.assertj.core.api.Assertions)
        Assertions.assertThat(primeAnnuelle).isEqualTo(1000);
    }

    @ParameterizedTest
    @CsvSource({
            "1, 'T12345', 1.0, 0, 1000",
            "1, 'M12345', 1.0, 0, 1700",
            "5, 'T12345', 1.0, 0, 5300",
            "1, 'T12345', 2.0, 0, 2000",
            "1, 'T12345', 2.0, 5, 3000",
            "4, 'M12345', 3.0, 7, 7200",

    })
    void testCheckPrimeAnnuelleAll(Integer performance, String matricule, Double tauxActivite, Long nbAnneeAnciennete, Double result) {
        //Given
        Employe employe = new Employe("Popo", "prenom", matricule, LocalDate.now().minusYears(nbAnneeAnciennete), 1000d, performance, tauxActivite);
        // When
        double primeAnnuelle = employe.getPrimeAnnuelle();
        // Then
        Assertions.assertThat(primeAnnuelle).isEqualTo(result);
    }

    // Correction
    @Test
    public void testCheckPrimeAnnuellematriculeNull(){
        // Given
        Integer performance = 1;
        Double tauxActivite = 1.0;
        Long nbAnneeAnciennete = 0L;

        Employe employe = new Employe("Popo", "prenom", null, LocalDate.now().minusYears(nbAnneeAnciennete), 1000d, performance, tauxActivite);

        // When
        double primeAnnuelle = employe.getPrimeAnnuelle();

        // Then (org.assertj.core.api.Assertions)
        Assertions.assertThat(primeAnnuelle).isEqualTo(1000);
    }
}
