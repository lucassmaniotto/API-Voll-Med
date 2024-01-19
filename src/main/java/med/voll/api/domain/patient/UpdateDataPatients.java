package med.voll.api.domain.patient;

import jakarta.validation.constraints.NotNull;
import med.voll.api.domain.address.AddressData;

public record UpdateDataPatients(
    @NotNull
    Long id,
    String name,
    String phone,
    AddressData address) {
}
