package com.yunseojin.attoassignment.host.controller;

import com.yunseojin.attoassignment.host.dto.HostRequest;
import com.yunseojin.attoassignment.host.service.HostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/hosts")
public class HostController {

    private final HostService hostService;

    @PostMapping("")
    public ResponseEntity<?> create(
            @RequestBody @Validated HostRequest hostRequest) {

        return new ResponseEntity<>(hostService.createHost(hostRequest), HttpStatus.CREATED);
    }

    @PutMapping("/{host_id}")
    public ResponseEntity<?> update(
            @PathVariable("host_id") Long hostId,
            @RequestBody @Validated HostRequest hostRequest) {

        return new ResponseEntity<>(hostService.updateHost(hostId, hostRequest), HttpStatus.OK);
    }

    @DeleteMapping("/{host_id}")
    public ResponseEntity<?> delete(
            @PathVariable("host_id") Long hostId) {

        hostService.deleteHost(hostId);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/{host_id}/is_alive")
    public ResponseEntity<?> isAlive(
            @PathVariable("host_id") Long hostId) {

        return new ResponseEntity<>(hostService.isAliveHost(hostId), HttpStatus.OK);
    }

    @GetMapping("/{host_id}")
    public ResponseEntity<?> getHost(
            @PathVariable("host_id") Long hostId) {

        return new ResponseEntity<>(hostService.getHost(hostId), HttpStatus.OK);
    }

    @GetMapping("")
    public ResponseEntity<?> getAllHosts() {

        return new ResponseEntity<>(hostService.getAllHosts(), HttpStatus.OK);
    }
}
