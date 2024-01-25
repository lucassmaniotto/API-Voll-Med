package med.voll.api.domain.appointment.validation;

import java.time.DayOfWeek;

import org.springframework.stereotype.Component;

import med.voll.api.domain.appointment.AppointmentSchedulingData;

@Component
public class ClinicOpeningHoursValidator implements AppointmentScheduleValidator {
    public void validate(AppointmentSchedulingData data) {
        var appointmentDate = data.date();

        var sunday = appointmentDate.getDayOfWeek().equals(DayOfWeek.SUNDAY);
        var beforeOpeningHours = appointmentDate.getHour() < 7;
        var afterClosingHours = appointmentDate.getHour() > 18;

        if (sunday || beforeOpeningHours || afterClosingHours) {
            throw new RuntimeException("Consulta fora do horário de funcionamento da clínica");
        }
    }
}
