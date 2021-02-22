package com.ipiecoles.java.java350.service;

import com.ipiecoles.java.java350.exception.EmployeException;
import com.ipiecoles.java.java350.model.Employe;
import com.ipiecoles.java.java350.model.NiveauEtude;
import com.ipiecoles.java.java350.model.Poste;
import com.ipiecoles.java.java350.repository.EmployeRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.time.LocalDate;
import java.util.List;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import java.time.LocalDate;
import java.util.List;

@ExtendWith(MockitoExtension.class)
class EmployeServiceTest {
    @InjectMocks
    private EmployeService employeService;
    @Mock
    private EmployeRepository employeRepository;
    @Test
    void testEmbaucheEmployeExisteDeja() throws EmployeException {
        //Given Pas d'employés en base
        String nom = "Doe";
        String prenom = "John";
        Poste poste = Poste.TECHNICIEN;
        NiveauEtude niveauEtude = NiveauEtude.BTS_IUT;
        Double tempsPartiel = 1.0;
        Employe employeExistant = new Employe("Doe", "Jane", "T00001", LocalDate.now(), 1500d, 1, 1.0);
        //Simuler qu'aucun employé n'est présent (ou du moins aucun matricule)
        Mockito.when(employeRepository.findLastMatricule()).thenReturn(null);
        //Simuler que la recherche par matricule renvoie un employé (un employé a été embauché entre temps)
        Mockito.when(employeRepository.findByMatricule("T00001")).thenReturn(employeExistant);
        //When
        try {
            employeService.embaucheEmploye(nom, prenom, poste, niveauEtude, tempsPartiel);
            Assertions.fail("embaucheEmploye aurait dû lancer une exception");
        }catch (Exception e){
            //Then
            Assertions.assertThat(e).isInstanceOf(EntityExistsException.class);
            Assertions.assertThat(e.getMessage()).isEqualTo("L'employé de matricule T00001 existe déjà en BDD");
        }
    }



    @Test
    void testEmbauchePremierEmploye() throws EmployeException {
        //Given Pas d'employés en base
        String nom = "Doe";
        String prenom = "John";
        Poste poste = Poste.TECHNICIEN;
        NiveauEtude niveauEtude = NiveauEtude.BTS_IUT;
        Double tempsPartiel = 1.0;
        //Simuler qu'ail y ya 99999 employe en bas (ou du moins que lea matruclue le plus haut)
        Mockito.when(employeRepository.findLastMatricule()).thenReturn("99999");



        try {
            employeService.embaucheEmploye(nom, prenom, poste, niveauEtude, tempsPartiel);
            Assertions.fail("embaucheEmploye aurait du lancer exeption");
        } catch (EmployeException e) {
            Assertions.assertThat(e.getMessage()).isEqualTo("Limite des 100000 matricules atteinte !");
        }
        //When

        //Then
//        Employe employe = employeRepository.findByMatricule("T00001");
        //   Assertions.assertThat(employe).isNotNull();
        // Assertions.assertThat(employe.getNom()).isEqualTo(nom);
        //Assertions.assertThat(employe.getPrenom()).isEqualTo(prenom);
        //Assertions.assertThat(employe.getSalaire()).isEqualTo(1825.46);
        //Assertions.assertThat(employe.getTempsPartiel()).isEqualTo(1.0);
        //Assertions.assertThat(employe.getDateEmbauche()).isEqualTo(LocalDate.now());
        //Assertions.assertThat(employe.getMatricule()).isEqualTo("T00001");
    }





    @Test
    void testEmbauchePremierEmploye2() throws EmployeException {
        //Given Pas d'employés en base
        String nom = "Doe";
        String prenom = "John";
        Poste poste = Poste.TECHNICIEN;
        NiveauEtude niveauEtude = NiveauEtude.BTS_IUT;
        Double tempsPartiel = 1.0;
        //Simuler qu'aucun employé n'est présent (ou du moins aucun matricule)
        Mockito.when(employeRepository.findLastMatricule()).thenReturn(null);
        //Simuler que la recherche par matricule ne renvoie pas de résultats
        Mockito.when(employeRepository.findByMatricule("T00001")).thenReturn(null);
        Mockito.when(employeRepository.save(Mockito.any(Employe.class))).thenAnswer(invocationOnMock -> invocationOnMock.getArgument(0));
        //When
        employeService.embaucheEmploye(nom, prenom, poste, niveauEtude, tempsPartiel);
        //Then
        ArgumentCaptor<Employe> employeArgumentCaptor = ArgumentCaptor.forClass(Employe.class);
//        Mockito.verify(employeRepository, Mockito.times(1)).save(employeArgumentCaptor.capture());
        Mockito.verify(employeRepository).save(employeArgumentCaptor.capture());
        Employe employe = employeArgumentCaptor.getValue();
        Assertions.assertThat(employe).isNotNull();
        Assertions.assertThat(employe.getNom()).isEqualTo(nom);
        Assertions.assertThat(employe.getPrenom()).isEqualTo(prenom);
        Assertions.assertThat(employe.getSalaire()).isEqualTo(1825.46);
        Assertions.assertThat(employe.getTempsPartiel()).isEqualTo(1.0);
        Assertions.assertThat(employe.getDateEmbauche()).isEqualTo(LocalDate.now());
        Assertions.assertThat(employe.getMatricule()).isEqualTo("T00001");
    }









    //Test : calculPerformanceCommercial - Cas 1 - perf < employe
    @Test
    void testCalculPerformanceCommercialC1PerfInf() throws EmployeException {
        //Given
        Employe employe = new Employe("Popo", "pupu", "C00001", LocalDate.now().minusYears(0L), 1000d, 5, 1.0);
        Long caAct = 500L;
        Long caObj = 1500L;

        //When
        Mockito.when(employeRepository.findByMatricule(employe.getMatricule())).thenReturn(employe);
        Mockito.when(employeRepository.avgPerformanceWhereMatriculeStartsWith("C")).thenReturn(0D);
        employeService.calculPerformanceCommercial(employe.getMatricule(), caAct, caObj);

        //Then
        Assertions.assertThat(employe.getPerformance()).isEqualTo(2);
    }




    //Test : calculPerformanceCommercial - Cas 1 - perf > employe
    @Test
    void testCalculPerformanceCommercialC1PerfSup() throws EmployeException {
        //Given
        Employe employe = new Employe("Popo", "pupu", "C00001", LocalDate.now().minusYears(0L), 1000d, 5, 1.0);
        Long caAct = 500L;
        Long caObj = 1500L;

        //When
        Mockito.when(employeRepository.findByMatricule(employe.getMatricule())).thenReturn(employe);
        Mockito.when(employeRepository.avgPerformanceWhereMatriculeStartsWith("C")).thenReturn(2D);
        employeService.calculPerformanceCommercial(employe.getMatricule(), caAct, caObj);

        //Then
        Assertions.assertThat(employe.getPerformance()).isEqualTo(1);
    }
    
    //Test : calculPerformanceCommercial - Cas 2 - perf < employe
    @Test
    void testCalculPerformanceCommercialC2PerfInf() throws EmployeException {
        //Given
        Employe employe = new Employe("Popo", "pupu", "C00001", LocalDate.now().minusYears(0L), 1000d, 5, 1.0);
        Long caAct = 1300L;
        Long caObj = 1500L;

        //When
        Mockito.when(employeRepository.findByMatricule(employe.getMatricule())).thenReturn(employe);
        Mockito.when(employeRepository.avgPerformanceWhereMatriculeStartsWith("C")).thenReturn(0D);
        employeService.calculPerformanceCommercial(employe.getMatricule(), caAct, caObj);

        //Then
        Assertions.assertThat(employe.getPerformance()).isEqualTo(4);
    }

    //Test : calculPerformanceCommercial - Cas 2 - perf > employe
    @Test
    void testCalculPerformanceCommercialC2PerfSup() throws EmployeException {
        //Given
        Employe employe = new Employe("Popo", "pupu", "C00001", LocalDate.now().minusYears(0L), 1000d, 5, 1.0);
        Long caAct = 1300L;
        Long caObj = 1500L;

        //When
        Mockito.when(employeRepository.findByMatricule(employe.getMatricule())).thenReturn(employe);
        Mockito.when(employeRepository.avgPerformanceWhereMatriculeStartsWith("C")).thenReturn(5D);
        employeService.calculPerformanceCommercial(employe.getMatricule(), caAct, caObj);

        //Then
        Assertions.assertThat(employe.getPerformance()).isEqualTo(3);
    }

    //Test : calculPerformanceCommercial - Cas 3 - perf < employe
    @Test
    void testCalculPerformanceCommercialC3PerfInf() throws EmployeException {
        //Given
        Employe employe = new Employe("Popo", "pupu", "C00001", LocalDate.now().minusYears(0L), 1000d, 10, 1.0);
        Long caAct = 1505L;
        Long caObj = 1500L;

        //When
        Mockito.when(employeRepository.findByMatricule(employe.getMatricule())).thenReturn(employe);
        Mockito.when(employeRepository.avgPerformanceWhereMatriculeStartsWith("C")).thenReturn(5D);
        employeService.calculPerformanceCommercial(employe.getMatricule(), caAct, caObj);

        //Then
        Assertions.assertThat(employe.getPerformance()).isEqualTo(11);
    }

    //Test : calculPerformanceCommercial - Cas 3 - perf > employe
    @Test
    void testCalculPerformanceCommercialC3PerfSup() throws EmployeException {
        //Given
        Employe employe = new Employe("Popo", "pupu", "C00001", LocalDate.now().minusYears(0L), 1000d, 5, 1.0);
        Long caAct = 1505L;
        Long caObj = 1500L;

        //When
        Mockito.when(employeRepository.findByMatricule(employe.getMatricule())).thenReturn(employe);
        Mockito.when(employeRepository.avgPerformanceWhereMatriculeStartsWith("C")).thenReturn(10D);
        employeService.calculPerformanceCommercial(employe.getMatricule(), caAct, caObj);

        //Then
        Assertions.assertThat(employe.getPerformance()).isEqualTo(5);
    }

    //Test : calculPerformanceCommercial - Cas 4 - perf < employe
    @Test
    void testCalculPerformanceCommercialC4PerfInf() throws EmployeException {
        //Given
        Employe employe = new Employe("Popo", "pupu", "C00001", LocalDate.now().minusYears(0L), 1000d, 8, 1.0);
        Long caAct = 1700L;
        Long caObj = 1500L;

        //When
        Mockito.when(employeRepository.findByMatricule(employe.getMatricule())).thenReturn(employe);
        Mockito.when(employeRepository.avgPerformanceWhereMatriculeStartsWith("C")).thenReturn(5D);
        employeService.calculPerformanceCommercial(employe.getMatricule(), caAct, caObj);

        //Then
        Assertions.assertThat(employe.getPerformance()).isEqualTo(10);
    }

    //Test : calculPerformanceCommercial - Cas 4 - perf > employe
    @Test
    void testCalculPerformanceCommercialC4PerfSup() throws EmployeException {
        //Given
        Employe employe = new Employe("Popo", "pupu", "C00001", LocalDate.now().minusYears(0L), 1000d, 7, 1.0);
        Long caAct = 1700L;
        Long caObj = 1500L;

        //When
        Mockito.when(employeRepository.findByMatricule(employe.getMatricule())).thenReturn(employe);
        Mockito.when(employeRepository.avgPerformanceWhereMatriculeStartsWith("C")).thenReturn(10D);
        employeService.calculPerformanceCommercial(employe.getMatricule(), caAct, caObj);

        //Then
        Assertions.assertThat(employe.getPerformance()).isEqualTo(8);
    }

    //Test : calculPerformanceCommercial - Cas 5 - perf < employe
    @Test
    void testCalculPerformanceCommercialC5PerfInf() throws EmployeException {
        //Given
        Employe employe = new Employe("Popo", "pupu", "C00001", LocalDate.now().minusYears(0L), 1000d, 8, 1.0);
        Long caAct = 2000L;
        Long caObj = 1500L;

        //When
        Mockito.when(employeRepository.findByMatricule(employe.getMatricule())).thenReturn(employe);
        Mockito.when(employeRepository.avgPerformanceWhereMatriculeStartsWith("C")).thenReturn(11D);
        employeService.calculPerformanceCommercial(employe.getMatricule(), caAct, caObj);

        //Then
        Assertions.assertThat(employe.getPerformance()).isEqualTo(13);
    }

    //Test : calculPerformanceCommercial - Cas 5 - perf > employe
    @Test
    void testCalculPerformanceCommercialC5PerfSup() throws EmployeException {
        //Given
        Employe employe = new Employe("Popo", "pupu", "C00001", LocalDate.now().minusYears(0L), 1000d, 7, 1.0);
        Long caAct = 2000L;
        Long caObj = 1500L;

        //When
        Mockito.when(employeRepository.findByMatricule(employe.getMatricule())).thenReturn(employe);
        Mockito.when(employeRepository.avgPerformanceWhereMatriculeStartsWith("C")).thenReturn(12D);
        employeService.calculPerformanceCommercial(employe.getMatricule(), caAct, caObj);

        //Then
        Assertions.assertThat(employe.getPerformance()).isEqualTo(11);
    }


    //Test : calculPerformanceCommercial - Cas CaAct = null
    @Test
    void testCalculPerformanceCommercialCaActIsNull() {
        //Given
        Employe employe = new Employe("Popo", "pupu", "C00001", LocalDate.now().minusYears(0L), 1000d, 1, 1.0);
        Long caAct = null;
        Long caObj = 1500L;

        //Then
        Assertions.assertThatThrownBy(() ->
                employeService.calculPerformanceCommercial(employe.getMatricule(), caAct, caObj))
                .isInstanceOf(EmployeException.class)
                .hasMessageContaining("Le chiffre d'affaire traité ne peut être négatif ou null !");
    }

    //Test : calculPerformanceCommercial - Cas CaAct = negative
    @Test
    void testCalculPerformanceCommercialCaActIsNegative() {
        //Given
        Employe employe = new Employe("Popo", "pupu", "C00001", LocalDate.now().minusYears(0L), 1000d, 1, 1.0);
        Long caAct = -2000L;
        Long caObj = 1500L;

        //Then
        Assertions.assertThatThrownBy(() ->
                employeService.calculPerformanceCommercial(employe.getMatricule(), caAct, caObj))
                .isInstanceOf(EmployeException.class)
                .hasMessageContaining("Le chiffre d'affaire traité ne peut être négatif ou null !");
    }

    //Test : calculPerformanceCommercial - Cas CaObj = null
    @Test
    void testCalculPerformanceCommercialCaObjIsNull() {
        //Given
        Employe employe = new Employe("Popo", "pupu", "C00001", LocalDate.now().minusYears(0L), 1000d, 1, 1.0);
        Long caAct = 2000L;
        Long caObj = null;

        //Then
        Assertions.assertThatThrownBy(() ->
                employeService.calculPerformanceCommercial(employe.getMatricule(), caAct, caObj))
                .isInstanceOf(EmployeException.class)
                .hasMessageContaining("L'objectif de chiffre d'affaire ne peut être négatif ou null !");
    }

    //Test : calculPerformanceCommercial - Cas CaObj = negative
    @Test
    void testCalculPerformanceCommercialCaObjIsNegative() {
        //Given
        Employe employe = new Employe("Popo", "pupu", "C00001", LocalDate.now().minusYears(0L), 1000d, 1, 1.0);
        Long caAct = 2000L;
        Long caObj = -1800L;

        //Then
        Assertions.assertThatThrownBy(() ->
                employeService.calculPerformanceCommercial(employe.getMatricule(), caAct, caObj))
                .isInstanceOf(EmployeException.class)
                .hasMessageContaining("L'objectif de chiffre d'affaire ne peut être négatif ou null !");
    }


    //Test : calculPerformanceCommercial - Cas Matricule = null
    @Test
    void testCalculPerformanceCommercialCaMatriculeIsNull() {
        //Given
        Employe employe = new Employe("Popo", "pupu", null, LocalDate.now().minusYears(0L), 1000d, 1, 1.0);
        Long caAct = 2000L;
        Long caObj = 1800L;

        //Then
        Assertions.assertThatThrownBy(() ->
                employeService.calculPerformanceCommercial(employe.getMatricule(), caAct, caObj))
                .isInstanceOf(EmployeException.class)
                .hasMessageContaining("Le matricule ne peut être null et doit commencer par un C !");
    }

    //Test : calculPerformanceCommercial - Cas Matricule != Commercial
    @Test
    void testCalculPerformanceCommercialMatriculeIsNotCommercial() {
        //Given
        Employe employe = new Employe("Popo", "pupu", "T00001", LocalDate.now().minusYears(0L), 1000d, 1, 1.0);
        Long caAct = 2000L;
        Long caObj = 1800L;

        //Then
        Assertions.assertThatThrownBy(() ->
                employeService.calculPerformanceCommercial(employe.getMatricule(), caAct, caObj))
                .isInstanceOf(EmployeException.class)
                .hasMessageContaining("Le matricule ne peut être null et doit commencer par un C !");
    }

    //Test : calculPerformanceCommercial - Cas Matricule not find
    @Test
    void testCalculPerformanceCommercialMatriculeNotFind() {
        //Given
        Employe employe = new Employe("Popo", "pupu", "C00001", LocalDate.now().minusYears(0L), 1000d, 1, 1.0);
        Long caAct = 2000L;
        Long caObj = 1800L;

        //When
        Mockito.when(employeRepository.findByMatricule(Mockito.anyString())).thenReturn(null);

        //Then
        Assertions.assertThatThrownBy(() ->
                employeService.calculPerformanceCommercial(employe.getMatricule(), caAct, caObj))
                .isInstanceOf(EmployeException.class)
                .hasMessageContaining("Le matricule " + employe.getMatricule() + " n'existe pas !");
    }
}
