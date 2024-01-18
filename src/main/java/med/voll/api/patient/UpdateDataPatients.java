package med.voll.api.patient;

import jakarta.validation.constraints.NotNull;
import med.voll.api.address.AddressData;

public record UpdateDataPatients(
    @NotNull
    Long id,
    String name,
    String phone,
    AddressData address) {
}
