package com.example.glamour_connect.controller;

import com.example.glamour_connect.dto.MakeupDto;
import com.example.glamour_connect.dto.MakeupSearchDto;
import com.example.glamour_connect.service.MakeupService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("makeup")
public class MakeupController {

    private final MakeupService makeupService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/save")
    public ResponseEntity<MakeupDto> saveMakeup(@RequestBody MakeupDto makeupDto) {
        return makeupService.saveMakeup(makeupDto);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @GetMapping("/search")
    public ResponseEntity<List<MakeupDto>> searchMakeup(@RequestBody MakeupSearchDto makeupSearchDto) {
        return makeupService.searchMakeup(makeupSearchDto);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/update")
    public ResponseEntity<MakeupDto> updateMakeup(@RequestBody MakeupDto makeupDto) {
        return makeupService.updateMakeup(makeupDto);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteMakeup(@RequestParam long makeupId) {
        return makeupService.deleteMakeup(makeupId);
    }
}
