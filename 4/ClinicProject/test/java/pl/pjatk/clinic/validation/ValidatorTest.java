package pl.pjatk.clinic.validation;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pl.pjatk.clinic.model.Doctor;
import pl.pjatk.clinic.validators.Validator;

import java.time.LocalDate;

public class ValidatorTest {

    @Test
    void testPesel() {
        Validator validator = new Validator();
        String pesel = "67062173664";
        //Returns NULL when everything is ok
        Assertions.assertNull(validator.validatePesel(pesel));
    }

    @Test
    void testPeselNull() {
        Validator validator = new Validator();
        String pesel = null;
        //Returns String "Pesel is NULL" when pesel is null
        Assertions.assertEquals("Pesel is NULL", validator.validatePesel(pesel));
    }

    @Test
    void testPeselTooLong() {
        Validator validator = new Validator();
        String pesel = "6706217366444";
        //Returns String "Pesel has incorrect length" when it's too long
        Assertions.assertEquals("Pesel has incorrect length", validator.validatePesel(pesel));
    }

    @Test
    void testPeselIsIncorrect() {
        Validator validator = new Validator();
        String pesel = "00000032664";
        //Returns String "Pesel is incorrect" when it's correct length but wrong
        Assertions.assertEquals("Pesel is incorrect", validator.validatePesel(pesel));

    }

    @Test
    void testName() {
        Validator validator = new Validator();
        String name = "Pawel";
        Assertions.assertNull(validator.validateName(name));
    }

    @Test
    void testNameIsEmpty() {
        Validator validator = new Validator();
        String name = "";
        Assertions.assertEquals("Name cannot be empty", validator.validateName(name));
    }

    @Test
    void testNameHasNumber() {
        Validator validator = new Validator();
        String name = "P4wel";
        Assertions.assertEquals("Name can consist of only letters", validator.validateName(name));
    }

    @Test
    void testSurname() {
        Validator validator = new Validator();
        String surName = "Badyliano";
        Assertions.assertNull(validator.validateSurName(surName));
    }

    @Test
    void testSurnameIsEmpty() {
        Validator validator = new Validator();
        String surName = "";
        Assertions.assertEquals("Surname cannot be empty", validator.validateSurName(surName));
    }

    @Test
    void testSurnameHasNumber() {
        Validator validator = new Validator();
        String surName = "B4dyli4n0";
        Assertions.assertEquals("Surname can consist of only letters", validator.validateSurName(surName));
    }

    @Test
    void testDate() {
        Validator validator = new Validator();
        LocalDate date = LocalDate.parse("2021-02-13");
        Assertions.assertNull(validator.validateDate(date));
    }

    @Test
    void testDateIsNull() {
        Validator validator = new Validator();
        LocalDate date = null;
        Assertions.assertEquals("Date is empty, please use \"yyyy-MM-dd\" format", validator.validateDate(date));
    }

    @Test
    void testHasDoctor() {
        Validator validator = new Validator();
        Doctor doctor = new Doctor(1);
        Assertions.assertNull(validator.validateHasDoctor(doctor));
    }

    @Test
    void testHasDoctorNull() {
        Validator validator = new Validator();
        Doctor doctor = null;
        Assertions.assertEquals("You cannot save patient without giving doctor ID", validator.validateHasDoctor(doctor));
    }
}
