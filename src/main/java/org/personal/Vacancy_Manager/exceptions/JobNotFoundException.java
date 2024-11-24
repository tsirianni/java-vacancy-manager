package org.personal.Vacancy_Manager.exceptions;

public class JobNotFoundException extends RuntimeException {
    public JobNotFoundException() {
        super("Opening Not Found");
    }
}
