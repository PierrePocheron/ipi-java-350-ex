package com.ipiecoles.java.java350.model;

import com.ipiecoles.java.java350.model.Employe;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class EmpoyeTest {

    // Correction
    @Test
    void testCheckNbAnneeAncienneteInfNow() {
        // Given
        Employe employe = new Employe("Popo", "pupu", "0", LocalDate.now().minusYears(15), 1000d, 10, 1d);

        // When
        Integer nbAnneeAnciennete = employe.getNombreAnneeAnciennete();

        // Then (org.assertj.core.api.Assertions)
        Assertions.assertThat(nbAnneeAnciennete).isEqualTo(15);
    }

    // Correction
    @Test
    void testCheckNbAnneeAncienneteNow() {
        // Given
        Employe employe = new Employe("Popo", "pupu", "0", LocalDate.now(), 1000d, 10, 1d);

        // When
        Integer nbAnneeAnciennete = employe.getNombreAnneeAnciennete();

        // Then (org.assertj.core.api.Assertions)
        Assertions.assertThat(nbAnneeAnciennete).isEqualTo(0);
    }

    // Correction
    @Test
    void testCheckNbAnneeAncienneteDateEmbaucheNull() {
        // Given
        Employe employe = new Employe();
        employe.setDateEmbauche(null);

        // When
        Integer nbAnneeAnciennete = employe.getNombreAnneeAnciennete();

        // Then (org.assertj.core.api.Assertions)
        Assertions.assertThat(nbAnneeAnciennete).isNull();
    }

    @Test
    void testCheckNbAnneeAncienneteIsPositive() {
        // Given
        Employe employe = new Employe("Popo", "pupu", "0", LocalDate.now().minusYears(15), 1000d, 10, 1d);

        // When
        int nbAnneeAnciennete = employe.getNombreAnneeAnciennete();

        // Then (org.assertj.core.api.Assertions)
        Assertions.assertThat(nbAnneeAnciennete).isPositive();
    }

    @Test
    void testCheckNbAnneeAncienneteGreaterThan5() {
        // Given
        Employe employe = new Employe("Popo", "pupu", "0", LocalDate.now().minusYears(15), 1000d, 10, 1d);

        // When
        int nbAnneeAnciennete = employe.getNombreAnneeAnciennete();

        // Then (org.assertj.core.api.Assertions)
        Assertions.assertThat(nbAnneeAnciennete).isGreaterThan(5);
    }

    @Test
    void testCheckNbAnneeAncienneteIsLessThan50() {
        // Given
        Employe employe = new Employe("Popo", "pupu", "0", LocalDate.now().minusYears(15), 1000d, 10, 1d);

        // When
        int nbAnneeAnciennete = employe.getNombreAnneeAnciennete();

        // Then (org.assertj.core.api.Assertions)
        Assertions.assertThat(nbAnneeAnciennete).isLessThan(50);
    }

    // Correction
    @Test
    void testCheckPrimeAnnuelleDefaut(){
        // Given
        Integer performance = 1;
        String matricule = "T12345";
        Double tauxActivite = 1.0;
        Long nbAnneeAnciennete = 0L;

        Employe employe = new Employe("Popo", "pupu", matricule, LocalDate.now().minusYears(nbAnneeAnciennete), 1000d, performance, tauxActivite);

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
        Employe employe = new Employe("Popo", "pupu", matricule, LocalDate.now().minusYears(nbAnneeAnciennete), 1000d, performance, tauxActivite);
        // When
        double primeAnnuelle = employe.getPrimeAnnuelle();
        // Then
        Assertions.assertThat(primeAnnuelle).isEqualTo(result);
    }

    // Correction
    @Test
    void testCheckPrimeAnnuellematriculeNull(){
        // Given
        Integer performance = 1;
        Double tauxActivite = 1.0;
        Long nbAnneeAnciennete = 0L;

        Employe employe = new Employe("Popo", "pupu", null, LocalDate.now().minusYears(nbAnneeAnciennete), 1000d, performance, tauxActivite);

        // When
        double primeAnnuelle = employe.getPrimeAnnuelle();

        // Then (org.assertj.core.api.Assertions)
        Assertions.assertThat(primeAnnuelle).isEqualTo(1000);
    }

    // Methode augmenterSalaire
    @Test
    void testAugmenterSalaireNormal(){
        // Given
        Double pourcentage = 10d;
        Employe employe = new Employe("Popo", "pupu", "T12345", LocalDate.now().minusYears(0L), 1000d, 1, 1.0);

        // When
        employe.augmenterSalaire(pourcentage);

        // Then (org.assertj.core.api.Assertions)
        Assertions.assertThat(employe.getSalaire()).isEqualTo(1100);
    }

    @Test
    void testAugmenterSalairePourcentageNull(){
        // Given
        Double pourcentage = null;
        Employe employe = new Employe("Popo", "pupu", "T12345", LocalDate.now().minusYears(0L), 1000d, 1, 1.0);

        // When
        assertThatThrownBy(() -> employe.augmenterSalaire(pourcentage))
                // Then
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining(employe.EXCEPTION_NULL_PERCENTAGE);
    }



    @Test
    void testAugmenterSalairePourcentage0(){
        // Given
        Double pourcentage = 0d;
        Employe employe = new Employe("Popo", "pupu", "T12345", LocalDate.now().minusYears(0L), 1000d, 1, 1.0);

        // When
        employe.augmenterSalaire(pourcentage);

        // Then (org.assertj.core.api.Assertions)
        Assertions.assertThat(employe.getSalaire()).isEqualTo(1000);
    }


    @Test
    void testAugmenterSalairePourcentageNegatif(){
        // Given
        Double pourcentage = -1d;
        Employe employe = new Employe("Popo", "pupu", "T12345", LocalDate.now().minusYears(0L), 1000d, 1, 1.0);

        // When
        assertThatThrownBy(() -> employe.augmenterSalaire(pourcentage))
                // Then
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining(employe.EXCEPTION_NEGATIVE_PERCENTAGE);
    }

    @Test
    void testAugmenterSalaireNegatif(){
        // Given
        Double pourcentage = 1d;
        Double salaire = -1000d;

        Employe employe = new Employe("Popo", "pupu", "T12345", LocalDate.now().minusYears(0L), salaire, 1, 1.0);

        // When
        assertThatThrownBy(() -> employe.augmenterSalaire(pourcentage))
                // Then
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining(employe.EXCEPTION_NEGATIVE_SALARY);
    }

    @Test
    void testAugmenterSalaireNull(){
        // Given
        Double pourcentage = 1d;
        Double salaire = null;

        Employe employe = new Employe("Popo", "pupu", "T12345", LocalDate.now().minusYears(0L), salaire, 1, 1.0);

        // When
        assertThatThrownBy(() -> employe.augmenterSalaire(pourcentage))
                // Then
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining(employe.EXCEPTION_NULL_SALARY);
    }

    @Test
    void testAugmenterSalaire0(){
        // Given
        Double pourcentage = 1d;
        Double salaire = 0d;

        Employe employe = new Employe("Popo", "pupu", "T12345", LocalDate.now().minusYears(0L), salaire, 1, 1.0);

        // When
        employe.augmenterSalaire(pourcentage);

        // Then (org.assertj.core.api.Assertions)
        Assertions.assertThat(employe.getSalaire()).isEqualTo(0);
    }
}
