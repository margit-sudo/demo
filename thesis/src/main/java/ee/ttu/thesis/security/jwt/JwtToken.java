package ee.ttu.thesis.security.jwt;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class JwtToken {
    private String token;
    private String type = "Bearer";
    private String id;
    private String username;
    private String email;
}
