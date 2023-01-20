package tt.hashtranslator.dto;

import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * Dto for Application.
 *
 * @see tt.hashtranslator.entity.Application
 */
@Getter
public class ApplicationDto {
    @NotNull
    @ArraySchema(schema = @Schema(implementation = String.class))
    private List<@NotBlank @Size(min = 32, max = 32) String> hashes;
}
