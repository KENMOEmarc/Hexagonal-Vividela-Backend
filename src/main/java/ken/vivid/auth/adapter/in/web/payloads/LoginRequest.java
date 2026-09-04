package ken.vivid.auth.adapter.in.web.payloads;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequest {

    @NotBlank(message = "L'identifiant (email ou username) est obligatoire")
    private String identifier;

    @NotBlank(message = "Le mot de passe est obligatoire")
    private String password;
}
