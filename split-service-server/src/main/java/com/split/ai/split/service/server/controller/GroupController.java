package com.split.ai.split.service.server.controller;

import com.split.ai.split.service.core.service.IGroupService;
import com.split.ai.split.service.model.request.group.AddUserToGroupRequest;
import com.split.ai.split.service.model.request.group.CreateGroupRequest;
import com.split.ai.split.service.model.request.group.DeleteGroupRequest;
import com.split.ai.split.service.model.request.group.InviteMembersToGroupRequest;
import com.split.ai.split.service.model.request.group.LeaveGroupRequest;
import com.split.ai.split.service.model.request.group.RemoveUserFromGroupRequest;
import com.split.ai.split.service.model.request.group.ToggleGroupSettleMode;
import com.split.ai.split.service.model.request.group.UpdateGroupRequest;
import com.split.ai.split.service.model.response.group.GroupExpenseResponse;
import com.split.ai.split.service.model.response.group.GroupUserResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

/**
 * Controller for group operations.
 */
@RestController
@RequestMapping("/v1/groups")
@RequiredArgsConstructor
@Slf4j
public class GroupController {

    private final IGroupService groupService;

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

    @PutMapping("{groupId}/add-user")
    public ResponseEntity<Void> addUser(@PathVariable @NotBlank UUID groupId, @Valid @RequestBody AddUserToGroupRequest request) {
        log.info("[GroupController : addUser] : {}", request);
        groupService.addUserToGroup(groupId, request);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{groupId}/remove-user")
    public ResponseEntity<Void> removeUser(@PathVariable @NotBlank UUID groupId,
                                           @Valid @RequestBody RemoveUserFromGroupRequest request) {
        log.info("[GroupController : removeUser] : {}", request);
        groupService.removeUser(groupId, request);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/toggle-mode")
    public ResponseEntity<Void> toggleMode(@Valid @RequestBody ToggleGroupSettleMode request) {
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
