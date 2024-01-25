package med.voll.api.domain.appointment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import med.voll.api.domain.ValidationException;
import med.voll.api.domain.appointment.validation.cancellation.AppointmentCancellationValidator;
import med.voll.api.domain.appointment.validation.scheduling.AppointmentScheduleValidator;
import med.voll.api.domain.doctor.Doctor;
import med.voll.api.domain.doctor.DoctorRepository;
import med.voll.api.domain.patient.PatientRepository;

import java.util.List;

@Service
public class AppointmentSchedule {
    
    @Autowired
    private AppointmentRepository appointmentRepository;
    
    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private List<AppointmentScheduleValidator> scheduleValidators;

    @Autowired
    private List<AppointmentCancellationValidator> cancelValidators;

    public SchedulingDetailsData schedule(AppointmentSchedulingData data) {
        if (!patientRepository.existsById(data.idPatient()))
            throw new ValidationException(String.format("Paciente não encontrado"));

        if (data.idDoctor() != null && !doctorRepository.existsById(data.idDoctor()))
            throw new ValidationException(String.format("Médico não encontrado"));

        scheduleValidators.forEach(validator -> validator.validate(data));

        var patient = patientRepository.getReferenceById(data.idPatient());
        var doctor = chooseDoctor(data);

        if (doctor == null)
            throw new ValidationException("Não há médicos disponíveis para a data e especialidade informadas");
            
        var appointment = new Appointment(null, doctor, patient, data.date(), null);
        appointmentRepository.save(appointment);

        return new SchedulingDetailsData(appointment);
    }

    public void cancel(AppointmentCancellationData data) {
        if (!appointmentRepository.existsById(data.idAppointment()))
            throw new ValidationException(String.format("Consulta não encontrada"));

        cancelValidators.forEach(validator -> validator.validate(data));

        var appointment = appointmentRepository.getReferenceById(data.idAppointment());
        appointment.cancel(data.reason());
    }

    private Doctor chooseDoctor(AppointmentSchedulingData data) {
        if (data.idDoctor() != null)
            return doctorRepository.getReferenceById(data.idDoctor());

        if (data.specialty() == null)
            throw new ValidationException("Especialidade é obrigatória para agendamento sem médico");

        var randomDoctor = doctorRepository.chooseAvailableRandomDoctorAtDate(data.specialty(), data.date());
        return randomDoctor;
        
    }
}