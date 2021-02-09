package com.ipiecoles.java.java350.service;

import com.ipiecoles.java.java350.model.Employe;
import com.ipiecoles.java.java350.service.EmployeService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

public class EmployeServiceTest {
    @Test
    public void testCheckNbAnneeAnciennete() {
        // Given
        Employe employe = new Employe("Popo", "prenom", "0", LocalDate.parse("2010-01-01"), 1000d, 10, 1d);

        // When


        // Then (org.assertj.core.api.Assertions)

    }
}
