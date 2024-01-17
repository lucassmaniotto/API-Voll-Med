package med.voll.api.doctor;

import jakarta.validation.constraints.NotNull;
import med.voll.api.address.AddressData;

public record UpdateDataDoctors(
    @NotNull Long id,
    String name, 
    String phone,
    AddressData address) {
}
