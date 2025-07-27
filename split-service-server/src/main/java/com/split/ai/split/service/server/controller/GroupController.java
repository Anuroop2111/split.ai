package com.split.ai.split.service.server.controller;

import com.split.ai.split.service.core.service.GroupService;
import com.split.ai.split.service.model.request.*;
import com.split.ai.split.service.model.response.GroupExpenseResponse;
import com.split.ai.split.service.model.response.GroupUserResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

/**
 * Controller for group operations.
 */
@RestController
@RequestMapping("/v1/groups")
@RequiredArgsConstructor
@Slf4j
public class GroupController {

    private final GroupService groupService;

    @GetMapping("/{groupId}/detailed")
    public ResponseEntity<GroupExpenseResponse> getGroupDetails(@PathVariable @NotBlank String groupId) {
        log.info("[GroupController : getGroupDetails] : {}", groupId);
        return ResponseEntity.ok(groupService.getGroupDetails(UUID.fromString(groupId)));
    }

    @PostMapping("/create")
    public ResponseEntity<Void> createGroup(@Valid @RequestBody CreateGroupRequest request) {
        log.info("[GroupController : createGroup] : {}", request);
        groupService.createGroup(request);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/add-user")
    public ResponseEntity<Void> addUser(@Valid @RequestBody AddUserToGroupRequest request) {
        log.info("[GroupController : addUser] : {}", request);
        groupService.addUserToGroup(request);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{groupId}/remove-user")
    public ResponseEntity<Void> removeUser(@PathVariable @NotBlank String groupId,
                                           @Valid @RequestBody RemoveUserFromGroupRequest request) {
        log.info("[GroupController : removeUser] : {}", request);
        groupService.removeUser(UUID.fromString(groupId), request);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/toggle-mode")
    public ResponseEntity<Void> toggleMode(@Valid @RequestBody ToggleSettleModeRequest request) {
        log.info("[GroupController : toggleMode] : {}", request);
        groupService.toggleSettleMode(request);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/leave")
    public ResponseEntity<Void> leaveGroup(@Valid @RequestBody LeaveGroupRequest request) {
        log.info("[GroupController : leaveGroup] : {}", request);
        groupService.leaveGroup(request);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Void> deleteGroup(@Valid @RequestBody DeleteGroupRequest request) {
        log.info("[GroupController : deleteGroup] : {}", request);
        groupService.deleteGroup(request);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/update/{groupId}")
    public ResponseEntity<Void> updateGroup(@PathVariable @NotBlank String groupId,
                                            @Valid @RequestBody UpdateGroupRequest request) {
        log.info("[GroupController : updateGroup] : {}", request);
        request.setGroupId(UUID.fromString(groupId));
        groupService.updateGroup(request);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{groupId}/members/invite")
    public ResponseEntity<Void> inviteMember(@PathVariable @NotBlank String groupId,
                                             @Valid @RequestBody InviteMembersToGroupRequest request) {
        log.info("[GroupController : inviteMember] : {}", request);
        groupService.inviteMember(UUID.fromString(groupId), request);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/members/{groupId}")
    public ResponseEntity<GroupUserResponse> getMembers(@PathVariable @NotBlank String groupId) {
        log.info("[GroupController : getMembers] : {}", groupId);
        return ResponseEntity.ok(groupService.getMembers(UUID.fromString(groupId)));
    }
}
