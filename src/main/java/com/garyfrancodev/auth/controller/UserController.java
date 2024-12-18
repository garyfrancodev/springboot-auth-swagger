package com.garyfrancodev.auth.controller;

import com.garyfrancodev.auth.model.User;
import com.garyfrancodev.auth.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/user")
@Tag(name = "Users", description = "CRUD Users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Operation(summary = "Crear un nuevo usuario", description = "Crea un usuario con los datos proporcionados.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Usuario creado con éxito",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = User.class)))
    })
    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) {
        return ResponseEntity.ok(userService.createUser(user));
    }

    @Operation(summary = "Obtener un usuario por ID", description = "Obtiene un usuario específico basado en el ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuario encontrado",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = User.class))),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado", content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<User> readUser(@PathVariable Long id) {
        Optional<User> user = userService.readUser(id);
        return user.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "Actualizar un usuario existente", description = "Actualiza los datos de un usuario basado en el ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuario actualizado con éxito",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = User.class))),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado", content = @Content)
    })
    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User userUpdate) {
        return ResponseEntity.ok(userService.updateUser(id, userUpdate));
    }

    @Operation(summary = "Eliminar un usuario", description = "Elimina un usuario basado en el ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Usuario eliminado con éxito"),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado", content = @Content)
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}
