package med.voll.api.domain.appointment.validation.scheduling;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import med.voll.api.domain.ValidationException;
import med.voll.api.domain.appointment.AppointmentRepository;
import med.voll.api.domain.appointment.AppointmentSchedulingData;

@Component
public class PatientWithoutAnotherAppointmentOnTheDay implements AppointmentScheduleValidator {
    @Autowired
    private AppointmentRepository repository;

    public void validate(AppointmentSchedulingData data) {
        var firstTime = data.date().withHour(7);
        var lastTime = data.date().withHour(18);
        var patientHasAnotherAppointmentOnTheDay = repository.existsByPatientIdAndDateBetween(data.idPatient(), firstTime, lastTime);

        if (patientHasAnotherAppointmentOnTheDay)
            throw new ValidationException("Paciente já possui consulta agendada para o mesmo dia");
    }
}
