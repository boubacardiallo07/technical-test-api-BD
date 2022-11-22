package com.technicaltest.api.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.technicaltest.api.validator.AgeRestrictions;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.*;
import java.util.Date;

/**
 * This class represents the user details with the constraints on the fields
 *
 * @author boubacar
 */
@Data
@AllArgsConstructor(staticName = "build")
@NoArgsConstructor
public class UserRequest {

    /**
     * name must not be null and must not be empty
     */
    @NotBlank(message = "username is mandatory")
    @NotNull(message = "username shouldn't be null")
    private String name;

    /**
     * birthDate must not be null and must have >= 18
     */
    @NotNull(message = "Birthdate is mandatory")
    @AgeRestrictions
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Temporal(TemporalType.DATE)
    private Date birthDate;

    /**
     * country must not be empty, and it has to equal 'France'
     */
    @NotBlank(message = "Country is mandatory")
    @Pattern(regexp = "France", message = "Only French users can register")
    private String country;

    /**
     * if phoneNumber is not empty then it must respect the constraint of 10 digits
     */
    @Pattern(regexp = "^\\d{10}$", message = "Invalid mobile number entered")
    private String phoneNumber;
    private String gender;
}
