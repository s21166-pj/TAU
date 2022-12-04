package pl.pjatk.clinic.validators;

import pl.pjatk.clinic.model.Doctor;
import pl.pjatk.clinic.model.Patient;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class Validator {

    public Validator() {
    }

    public List<String> validatePatient(Patient patient) {
        List<String> listOfErrors = new ArrayList<>();
        listOfErrors.add(validateName(patient.getName()));
        listOfErrors.add(validateSurName(patient.getSurname()));
        listOfErrors.add(validatePesel(patient.getPesel()));
        listOfErrors.add(validateDate(patient.getDateOfConsultation()));
        listOfErrors.add(validateHasDoctor(patient.getDoctor()));

        return listOfErrors.parallelStream()
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    public List<String> validateDoctor(Doctor doctor) {
        List<String> listOfErrors = new ArrayList<>();
        listOfErrors.add(validateName(doctor.getName()));
        listOfErrors.add(validateSurName(doctor.getSurname()));

        return listOfErrors.parallelStream()
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    public String validateName(String name) {
//        if (!name.isEmpty() && name.matches("[a-zA-Z]+"))
        if (name.isEmpty()) {
            return "Name cannot be empty";
        } else if (!name.matches("[a-zA-Z]+")) {
            return "Name can consist of only letters";
        } else {
            return null;
        }
    }

    public String validateSurName(String surname) {
//        if (!surname.isEmpty() && surname.matches("[a-zA-Z]+")) {
        if (surname.isEmpty()) {
            return "Surname cannot be empty";
        } else if (!surname.matches("[a-zA-Z]+")) {
            return "Surname can consist of only letters";
        } else {
            return null;
        }
    }

    public String validatePesel(String pesel) {
        int[] controlWeight = {1, 3, 7, 9, 1, 3, 7, 9, 1, 3};

        if (pesel == null) {
            return "Pesel is NULL";
        } else if (pesel.length() != 11) {
            return "Pesel has incorrect length";
        }
        int controlSum = 0;
        for (int i = 0; i < 10; i++)

            controlSum += Integer.parseInt(pesel.substring(i, i + 1)) * controlWeight[i];

        int controlDigit = Integer.parseInt(pesel.substring(10, 11));

        controlSum %= 10;
        controlSum = 10 - controlSum;
        controlSum %= 10;

        if ((controlSum == controlDigit)) {
            return null;
        } else {
            return "Pesel is incorrect";
        }
    }

    public String validateDate(LocalDate dateToFormat) {
        if (dateToFormat == null) {
            return "Date is empty, please use \"yyyy-MM-dd\" format";
        } else {
            return null;
        }
    }

    public String validateHasDoctor(Doctor doctor) {
        if (doctor == null) {
            return "You cannot save patient without giving doctor ID";
        } else {
            return null;
        }
    }
}
