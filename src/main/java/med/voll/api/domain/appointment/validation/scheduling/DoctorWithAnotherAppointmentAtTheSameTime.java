package med.voll.api.domain.appointment.validation.scheduling;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import med.voll.api.domain.ValidationException;
import med.voll.api.domain.appointment.AppointmentRepository;
import med.voll.api.domain.appointment.AppointmentSchedulingData;

@Component
public class DoctorWithAnotherAppointmentAtTheSameTime implements AppointmentScheduleValidator {
    @Autowired
    private AppointmentRepository repository;

    public void validate(AppointmentSchedulingData data) {
        var doctorHasAnotherAppointmentAtTheSameTime = repository.existsByDoctorIdAndDateAndCancelReasonIsNull(data.idDoctor(), data.date());
        if (doctorHasAnotherAppointmentAtTheSameTime)
            throw new ValidationException("Médico já possui consulta agendada para o mesmo horário");
    }
}
