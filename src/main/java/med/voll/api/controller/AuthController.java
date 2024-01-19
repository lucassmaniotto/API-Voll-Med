package med.voll.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import med.voll.api.domain.user.AuthData;
import med.voll.api.domain.user.User;
import med.voll.api.infra.security.TokenJWTData;
import med.voll.api.infra.security.TokenService;

@RestController
@RequestMapping("/login")
public class AuthController {
    @Autowired
    private AuthenticationManager manager;

    @Autowired
    private TokenService tokenService;
    
    @PostMapping
    public ResponseEntity<Object> login(@RequestBody @Valid AuthData data) {
        try {
            var authToken = new UsernamePasswordAuthenticationToken(data.login(), data.password());
            var authentication = manager.authenticate(authToken);

            var tokenJWT = tokenService.generateToken((User) authentication.getPrincipal());

            return ResponseEntity.ok(new TokenJWTData(tokenJWT));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        
    }
}
