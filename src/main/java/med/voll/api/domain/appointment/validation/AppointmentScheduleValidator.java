package med.voll.api.domain.appointment.validation;

import med.voll.api.domain.appointment.AppointmentSchedulingData;

public interface AppointmentScheduleValidator {
    void validate(AppointmentSchedulingData data);
}
