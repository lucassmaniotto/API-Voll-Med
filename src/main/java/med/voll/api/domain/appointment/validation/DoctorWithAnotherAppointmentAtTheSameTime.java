package med.voll.api.domain.appointment.validation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import med.voll.api.domain.appointment.AppointmentRepository;
import med.voll.api.domain.appointment.AppointmentSchedulingData;

@Component
public class DoctorWithAnotherAppointmentAtTheSameTime implements AppointmentScheduleValidator {
    @Autowired
    private AppointmentRepository repository;

    public void validate(AppointmentSchedulingData data) {
        var doctorHasAnotherAppointmentAtTheSameTime = repository.existsByDoctorIdAndDate(data.idDoctor(), data.date());
        if (doctorHasAnotherAppointmentAtTheSameTime)
            throw new RuntimeException("Médico já possui consulta agendada para o mesmo horário");
    }
}