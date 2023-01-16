package tt.hashtranslator.dto;

import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
public class ApplicationDto {
    @NotNull
    private List<@NotBlank String> hashes;
}
