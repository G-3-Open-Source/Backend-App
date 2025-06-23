// UserController.java
package pe.edu.upc.center.platform.profiles.interfaces.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.upc.center.platform.profiles.interfaces.acl.UsersContextFacade;
import pe.edu.upc.center.platform.profiles.interfaces.rest.resources.CreateUserResource;
import pe.edu.upc.center.platform.profiles.interfaces.rest.resources.UserResource;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {
    private final UsersContextFacade facade;

    public UserController(UsersContextFacade facade) {
        this.facade = facade;
    }

    @GetMapping
    public List<UserResource> listAll() {
        return facade.fetchAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResource> getById(@PathVariable Long id) {
        return facade.fetchById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Long> create(@RequestBody CreateUserResource r) {
        var newId = facade.create(r);
        return ResponseEntity.ok(newId);
    }
}
