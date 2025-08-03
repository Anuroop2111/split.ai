package com.split.ai.split.service.server.controller;

import com.split.ai.split.service.core.service.IUserService;
import com.split.ai.split.service.model.request.user.UpdateUserProfileRequest;
import com.split.ai.split.service.model.response.user.UserGroupsResponse;
import com.split.ai.split.service.model.response.user.UserProfileResponse;
import com.split.ai.split.service.model.response.user.UserSuggestResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

/**
 * Controller handling user related operations.
 */
@RestController
@RequestMapping("/v1/users")
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final IUserService userService;

    @GetMapping("/profile/{userId}")
    public ResponseEntity<UserProfileResponse> getProfile(@PathVariable @NotBlank String userId) {
        log.info("[UserController : getProfile] : fetching profile for {}", userId);
        return ResponseEntity.ok(userService.getProfile(UUID.fromString(userId)));
    }

    @PatchMapping("/update/{userId}")
    public ResponseEntity<Void> updateProfile(@PathVariable @NotBlank String userId,
                                              @Valid @RequestBody UpdateUserProfileRequest request) {
        log.info("[UserController : updateProfile] : updating profile {}", request);
        userService.updateProfile(UUID.fromString(userId), request);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/suggest")
    public ResponseEntity<UserSuggestResponse> suggestUsers(@RequestParam("query") String query,
                                                            @RequestParam(value = "limit", required = false) Integer limit) {
        log.info("[UserController : suggestUsers] : query {}", query);
        return ResponseEntity.ok(userService.suggestUsers(query, limit));
    }

    @GetMapping("/{userId}/groups")
    public ResponseEntity<UserGroupsResponse> getUserGroups(@PathVariable @NotBlank String userId,
                                                            @RequestParam(value = "page", required = false) Integer page,
                                                            @RequestParam(value = "size", required = false) Integer size) {
        log.info("[UserController : getUserGroups] : groups for {}", userId);
        return ResponseEntity.ok(userService.getGroups(UUID.fromString(userId), page, size));
    }
}
