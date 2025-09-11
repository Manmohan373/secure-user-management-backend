package com.example.secureusermanagement.service;

import com.example.secureusermanagement.dto.RegisterRequest;
import com.example.secureusermanagement.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.stereotype.Service;

import javax.ws.rs.core.Response;
import java.util.List;

@Service
@RequiredArgsConstructor
public class KeycloakService {

    private final UserRepository userRepository;
    private final Keycloak keycloak;

    public void createUser(RegisterRequest request) {
        UserRepresentation user = new UserRepresentation();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setEnabled(true);

        CredentialRepresentation credential = new CredentialRepresentation();
        credential.setType(CredentialRepresentation.PASSWORD);
        credential.setValue(request.getPassword());
        credential.setTemporary(false);

        user.setCredentials(List.of(credential));

        Response response = keycloak.realm("MyAppRealm").users().create(user);
        if (response.getStatus() == 201) {
            com.example.secureusermanagement.entity.User dbUser =
                    com.example.secureusermanagement.entity.User.builder()
                            .username(request.getUsername())
                            .email(request.getEmail())
                            .address(request.getAddress())
                            .salary(request.getSalary())
                            .build();
            userRepository.save(dbUser);

            System.out.println("User registered successfully!");
        } else {
            System.out.println("Failed to create user. Status: " + response.getStatus());
            System.out.println("Error: " + response.getEntity());
        }
    }
}

