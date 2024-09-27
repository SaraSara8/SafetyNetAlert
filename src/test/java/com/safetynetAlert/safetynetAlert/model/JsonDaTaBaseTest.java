package com.safetynetAlert.safetynetAlert.model;

import com.safetynetAlert.SafetynetAlertApplication;
import com.safetynetAlert.mapper.PersonStationDTOMapper;
import com.safetynetAlert.model.*;
import com.safetynetAlert.service.MedicalRecordService;
import com.safetynetAlert.service.URLService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@SpringBootTest(classes = SafetynetAlertApplication.class)
class JsonDataBaseTest {

    private static Data data;
    private static List<FireStation> fireStations = new ArrayList<>();
    private static List<Person> persons = new ArrayList<>();
    private static List<MedicalRecord> medicalRecords = new ArrayList<>();

    private MedicalRecord medicalRecord1;

    @InjectMocks
    private URLService urlService;

    @Mock
    private static JsonDataBase jsonData;

    @Mock
    private MedicalRecordService medicalRecordService;

    @Mock
    PersonStationDTOMapper personStationDTOMapper;

    @BeforeEach
    void setUp() throws Exception {

        MockitoAnnotations.openMocks(this);

        lenient().doNothing().when(jsonData).loadData();
        lenient().doNothing().when(jsonData).init();
        lenient().doNothing().when(jsonData).saveData(data);

        persons = Arrays.asList(
                new Person("John", "Doe", "123 Main St", "New York", "97451", "841-874-6512", "jaboyd@email.com"),
                new Person("Lily", "Cooper", "456 Pine St", "Chicago", "97455", "841-874-6544", "licooper@email.com"));

        fireStations = new ArrayList<>(Arrays.asList(
                new FireStation("123 Main St", "1"),
                new FireStation("456 Pine St", "2")));

        List<String> medications1 = Arrays.asList("aznol:350mg", "hydrapermazol:100mg");
        List<String> allergies1 = Arrays.asList("allergy1", "allergy2");

        medicalRecord1 = new MedicalRecord("John", "Doe", "01/01/2014", medications1, allergies1);

        List<String> medications2 = Arrays.asList("pharmacol:5000mg", "terazine:10mg");
        List<String> allergies2 = Arrays.asList("allergy1", "allergy2");

        MedicalRecord medicalRecord2 = new MedicalRecord("Lily", "Cooper", "01/01/1980", medications2, allergies2);

        medicalRecords = new ArrayList<>(Arrays.asList(medicalRecord1, medicalRecord2));

        data = new Data(persons, fireStations, medicalRecords);

    }

    @Test
    void saveDataTest() throws Exception {
        jsonData.saveData(data);
        verify(jsonData, times(1)).saveData(data);
    }

    @Test
    void initTest() throws IOException {
        lenient().when(jsonData.getData()).thenReturn(data);
        Data data = jsonData.getData();

        assertFalse(data.getPersons().isEmpty());
        assertFalse(data.getFirestations().isEmpty());
        assertFalse(data.getMedicalrecords().isEmpty());

        for (Person person : persons) {
            assertNotNull(person.getFirstName());
            assertNotNull(person.getLastName());
        }

        for (FireStation fireStation : fireStations) {
            assertNotNull(fireStation.getAddress());
            assertNotNull(fireStation.getStation());
        }

        for (MedicalRecord medicalRecord : medicalRecords) {
            assertNotNull(medicalRecord.getFirstName());
            assertNotNull(medicalRecord.getLastName());
        }
    }


}
