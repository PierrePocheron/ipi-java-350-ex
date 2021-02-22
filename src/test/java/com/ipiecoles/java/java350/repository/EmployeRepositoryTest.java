package com.ipiecoles.java.java350.repository;

import com.ipiecoles.java.java350.model.Employe;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;


@SpringBootTest
class EmployeRepositoryTest {

    @Autowired
    EmployeRepository employeRepository;

    @Test
    void testFindLastMatricule0Employe()
    {
        //Given
        //employeRepository.deleteAll();

        //When
        String lastMatricule = employeRepository.findLastMatricule();

        //Then
        Assertions.assertThat(lastMatricule).isNull();
    }

    @Test
    void testFindLastMatricule1Employe()
    {
        //Given
        //Insérer des données en base
        employeRepository.save(new Employe("Doe", "John", "T12345", LocalDate.now(), 1500d, 1, 1.0));
        //When
        //Exécuter des requêtes en base
        String lastMatricule = employeRepository.findLastMatricule();
        //Then
        Assertions.assertThat(lastMatricule).isEqualTo("12345");
    }

    @Test
    void testFindLastMatriculeNEmploye()
    {
        //Given
        //Insérer des données en base
        employeRepository.save(new Employe("Doe", "John", "T12345", LocalDate.now(), 1500d, 1, 1.0));
        employeRepository.save(new Employe("Doe", "Jane", "M40325", LocalDate.now(), 1500d, 1, 1.0));
        employeRepository.save(new Employe("Doe", "Jim", "C06432", LocalDate.now(), 1500d, 1, 1.0));

        //When
        String lastMatricule = employeRepository.findLastMatricule();

        //Then
        Assertions.assertThat(lastMatricule).isEqualTo("40325");
    }


    @Test
    void testFindAvgPerformanceEmployeWhereMatricule()
    {
        //Given
        //Insérer des données en base
        employeRepository.save(new Employe("Doe", "John", "C12345", LocalDate.now(), 1500d, 10, 1.0));
        employeRepository.save(new Employe("Doe", "Jane", "C40325", LocalDate.now(), 1500d, 6, 1.0));
        employeRepository.save(new Employe("Doe", "Jim", "C06432", LocalDate.now(), 1500d, 2, 1.0));
        employeRepository.save(new Employe("Doe", "carter", "M06432", LocalDate.now(), 1500d, 12, 1.0));
        employeRepository.save(new Employe("Carglass", "Jim", "T06432", LocalDate.now(), 1500d, 12, 1.0));

        //When
        Double avgPreformance = employeRepository.avgPerformanceWhereMatriculeStartsWith("C");

        //Then
        Assertions.assertThat(avgPreformance).isEqualTo(6);
    }



    @BeforeEach
    @AfterEach
    void purgeBdd(){
        employeRepository.deleteAll();
    }

}