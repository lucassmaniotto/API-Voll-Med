package med.voll.api.domain.doctor;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum Specialty {
    ORTOPEDIA,
    CARDIOLOGIA,
    GINECOLOGIA,
    DERMATOLOGIA;

    @JsonCreator
    public static Specialty fromString(String value) {
        for (Specialty specialty : Specialty.values()) {
            if (specialty.name().equalsIgnoreCase(value)) {
                return specialty;
            }
        }
        throw new IllegalArgumentException("Valor inválido para Especialidade: " + value);
    }
}
