package pl.pjatk.clinic.stub;

import org.junit.jupiter.api.Test;
import pl.pjatk.clinic.model.Patient;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class StubTest {

    interface Service {
        List<Patient> getPatientList();
    }

    static class Doctor {

        Service service;

        public Doctor(Service service) {
            this.service = service;
        }

        public List<Patient> getPatientList() {
            List<Patient> listOfPatients = service.getPatientList();

            return listOfPatients;
        }
    }

}

class StubJavaTest {

    @Test
    public void whenCallServiceIsStubbed() {
        StubTest.Doctor service = new StubTest.Doctor(new StubService());

        assertEquals(3,service.getPatientList().size());
    }

    static class StubService implements StubTest.Service {
        public List<Patient> getPatientList() {
            Patient stub1 = new Patient();
            Patient stub2 = new Patient();
            Patient stub3 = new Patient();

            return Arrays.asList(stub1,stub2,stub3);
        }
    }
}
