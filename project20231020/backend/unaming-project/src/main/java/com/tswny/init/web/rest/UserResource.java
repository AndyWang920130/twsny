package com.tswny.init.web.rest;

import com.tswny.init.service.UserService;
import com.tswny.init.service.dto.UserDTO;
import com.tswny.init.service.vm.SubscribeVM;
import com.tswny.init.web.rest.vm.UserVM;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
public class UserResource {
    private final Logger log = LoggerFactory.getLogger(UserResource.class);

    private final UserService userService;

    public UserResource(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/test")
    public ResponseEntity<UserDTO> createTest(@RequestBody UserVM userVM) {
        log.info("create user: {}", userVM);
        UserDTO userDTO =  userService.createUser(userVM);
        return ResponseEntity.ok(userDTO);
    }

    @PostMapping
    public ResponseEntity<UserDTO> create(@RequestBody UserVM userVM) {
        log.info("create user: {}", userVM);
        UserDTO userDTO = userService.createUser(userVM);
        return ResponseEntity.ok(userDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> findById(@PathVariable Long id) {
        UserDTO userDTO = userService.findById(id);
        return ResponseEntity.ok(userDTO);
    }

    @GetMapping("/login")
    public ResponseEntity<UserDTO> findByLogin(@RequestParam(required = true) String loginName) {
        UserDTO userDTO = userService.findByLogin(loginName);
        return ResponseEntity.ok(userDTO);
    }

    @GetMapping("/getPublishers/{userName}")
    public ResponseEntity<List<UserDTO>> getPublishers(@PathVariable String userName) {
        List<UserDTO> userDTOList = userService.getPublishers(userName);
        return ResponseEntity.ok(userDTOList);
    }

    @GetMapping("/getSubscribers/{userName}")
    public ResponseEntity<List<UserDTO>> getSubscribers(@PathVariable String userName) {
        List<UserDTO> userDTOList = userService.getSubscribers(userName);
        return ResponseEntity.ok(userDTOList);
    }

    @PutMapping("/subscribe")
    public ResponseEntity<Void> subscribe(@RequestBody SubscribeVM subscribeVM) {
        userService.subscribe(subscribeVM);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/unsubscribe")
    public ResponseEntity<Void> unsubscribe(@RequestBody SubscribeVM subscribeVM) {
        userService.unsubscribe(subscribeVM);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/isSubscribed")
    public ResponseEntity<Boolean> isSubscribed(@ModelAttribute SubscribeVM subscribeVM) {
        boolean isSubscribed = userService.isSubscribed(subscribeVM);
        return ResponseEntity.ok(isSubscribed);
    }
}
