package pl.pjatk.clinic.service;

import org.springframework.stereotype.Service;
import pl.pjatk.clinic.exception.DoctorException;
import pl.pjatk.clinic.exception.PatientException;
import pl.pjatk.clinic.model.Doctor;
import pl.pjatk.clinic.model.Patient;

import java.time.LocalDate;
import java.util.Optional;

@Service
public class ConsultationService {

    DoctorService doctorService;
    PatientService patientService;

    public ConsultationService(DoctorService doctorService, PatientService patientService) {
        this.doctorService = doctorService;
        this.patientService = patientService;
    }

    public void delayConsultationByWeekById(int doctorId, int patientId) throws DoctorException, PatientException {
        Optional<Patient> patientById = patientService.findById(patientId);
        Patient patientGet = patientById.get();
        int patientsDoctorId = patientGet.getDoctor().getId();
        Optional<Doctor> doctorOfPatient = doctorService.findById(doctorId);

        if (doctorId != patientsDoctorId) {
            throw new DoctorException("You can use this only on your patients!");
        } else {
            LocalDate laterDate = patientGet.getDateOfConsultation().plusDays(7L);
            patientGet.setDateOfConsultation(laterDate);
            patientService.update(patientGet.getPesel(), patientGet);
        }
    }
}
