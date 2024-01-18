package med.voll.api.patient;

import med.voll.api.address.Address;

public record DataPatientDetails(Long id, String name, String email, String cpf, String phone, Address address) {
    public DataPatientDetails(Patient patient) {
        this(patient.getId(), patient.getName(), patient.getEmail(), patient.getCpf(), patient.getPhone(), patient.getAddress());
    }
    
}
