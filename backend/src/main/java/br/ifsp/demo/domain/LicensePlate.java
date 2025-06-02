package br.ifsp.demo.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotBlank;
import lombok.EqualsAndHashCode;
import org.checkerframework.common.aliasing.qual.Unique;

import java.util.regex.Pattern;

@Embeddable
@EqualsAndHashCode
public class LicensePlate {
    private static final Pattern OLD_BR_PATTERN = Pattern.compile("^[A-Z]{3}-\\d{4}$");
    private static final Pattern MERCOSUL_PATTERN = Pattern.compile("^[A-Z]{3}\\d[A-Z]\\d{2}$");

    @NotBlank
    @Column(name = "license_plate", nullable = false, unique = true)
    private String plate;

    protected LicensePlate() {
    }

    private LicensePlate(String plate) {
        this.plate = plate;
    }

    public static LicensePlate parse(String plate) {
        if (plate == null) {
            throw new IllegalArgumentException("License plate cannot be null.");
        }
        plate = plate.trim().toUpperCase();
        if(!isValid(plate)) {
            throw new IllegalArgumentException(
                    "Invalid license plate: \"" + plate + "\". " +
                            "Accepted formats: Old Model (ABC-1234) or Mercosur Model (ABC1D23)."
            );
        }
        return new LicensePlate(plate);
    }

    public static boolean isValidOldBr(String plate) {
        return plate != null && OLD_BR_PATTERN.matcher(plate).matches();
    }

    public static boolean isValidMercosul(String plate) {
        return plate != null && MERCOSUL_PATTERN.matcher(plate).matches();
    }

    public static boolean isValid(String plate) {
        return isValidOldBr(plate) || isValidMercosul(plate);
    }

    @Override
    public String toString() {
        return this.plate;
    }
}
