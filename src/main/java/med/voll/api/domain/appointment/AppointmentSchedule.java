package med.voll.api.domain.appointment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import med.voll.api.domain.ValidationException;
import med.voll.api.domain.appointment.validation.AppointmentScheduleValidator;
import med.voll.api.domain.doctor.Doctor;
import med.voll.api.domain.doctor.DoctorRepository;
import med.voll.api.domain.patient.PatientRepository;

import java.util.List;

@Service
@SuppressWarnings("null")
public class AppointmentSchedule {
    
    @Autowired
    private AppointmentRepository appointmentRepository;
    
    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private List<AppointmentScheduleValidator> validators;

    public void schedule(AppointmentSchedulingData data) {
        if (!patientRepository.existsById(data.idPatient()))
            throw new ValidationException(String.format("Paciente não encontrado"));

        if (data.idDoctor() != null && !doctorRepository.existsById(data.idDoctor()))
            throw new ValidationException(String.format("Médico não encontrado"));

        validators.forEach(validator -> validator.validate(data));

        var patient = patientRepository.getReferenceById(data.idPatient());
        var doctor = chooseDoctor(data);
        var appointment = new Appointment(null, doctor, patient, data.date());
        appointmentRepository.save(appointment);
    }

    private Doctor chooseDoctor(AppointmentSchedulingData data) {
        if (data.idDoctor() != null)
            return doctorRepository.getReferenceById(data.idDoctor());

        if (data.specialty() != null)
            throw new ValidationException("Especialidade é obrigatória para agendamento sem médico");

        try {
            var randomDoctor = doctorRepository.chooseAvailableRandomDoctorAtDate(data.specialty(), data.date());
            return randomDoctor;
        } catch (Exception e) {
            throw new ValidationException("Não foi possível encontrar um médico disponível para a data e especialidade informada");
        }
    }
}