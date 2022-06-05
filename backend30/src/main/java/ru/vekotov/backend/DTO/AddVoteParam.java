package ru.vekotov.backend.DTO;

import lombok.Data;
import ru.vekotov.backend.Validators.PhoneNumberValidation;

import javax.validation.constraints.NotEmpty;

@Data
public class AddVoteParam {
    @PhoneNumberValidation
    private String phone;

    @NotEmpty
    private String artist;
}
