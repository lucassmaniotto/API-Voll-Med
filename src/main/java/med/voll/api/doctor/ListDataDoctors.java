package med.voll.api.doctor;

public record ListDataDoctors(Long id, String name, String email, String crm, Specialty specialty) {
    public ListDataDoctors(Doctor doctor) {
        this(doctor.getId(), doctor.getName(), doctor.getEmail(), doctor.getCrm(), doctor.getSpecialty());
    }
}
