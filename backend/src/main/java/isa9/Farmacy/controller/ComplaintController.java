package isa9.Farmacy.controller;


import isa9.Farmacy.model.dto.ComplaintDTO;
import isa9.Farmacy.service.ComplaintService;
import isa9.Farmacy.support.ComplaintDTOtoComplaint;
import isa9.Farmacy.support.ComplaintToComplaintDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/complaints")
public class ComplaintController {

    private final ComplaintService complaintService;
    private final ComplaintToComplaintDTO complaintToComplaintDTO;
    private final ComplaintDTOtoComplaint complaintDTOtoComplaint;

    @Autowired
    public ComplaintController(ComplaintService complaintService, ComplaintToComplaintDTO complaintToComplaintDTO, ComplaintDTOtoComplaint complaintDTOtoComplaint) {
        this.complaintService = complaintService;
        this.complaintToComplaintDTO = complaintToComplaintDTO;
        this.complaintDTOtoComplaint = complaintDTOtoComplaint;
    }

    @PostMapping("/newComplaint")
    @PreAuthorize("hasAuthority('PATIENT')")
    public ResponseEntity<Integer> addComplaint(@RequestBody ComplaintDTO complaintDTO){
        System.out.println(
                this.complaintService.fileAComplaint(this.complaintDTOtoComplaint.convert(complaintDTO))
        );
        return new ResponseEntity<>(0, HttpStatus.OK);
    }

    @PostMapping("/")
    @PreAuthorize("hasAuthority('SYS_ADMIN')")
    public ResponseEntity<List<ComplaintDTO>> getAllComplaints(){
        List<ComplaintDTO> allComplaints = this.complaintToComplaintDTO.convert(this.complaintService.findAll());
        return new ResponseEntity<>(allComplaints, HttpStatus.OK);
    }
}
