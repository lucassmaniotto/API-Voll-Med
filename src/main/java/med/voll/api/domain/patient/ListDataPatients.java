package med.voll.api.domain.patient;

public record ListDataPatients(Long id, String name, String email, String phone, String cpf) {
    public ListDataPatients(Patient patient) {
        this(patient.getId(), patient.getName(), patient.getEmail(), patient.getPhone(), patient.getCpf());
    }
    
}
