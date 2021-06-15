package isa9.Farmacy.controller;


import isa9.Farmacy.model.Complaint;
import isa9.Farmacy.model.Patient;
import isa9.Farmacy.model.SysAdmin;
import isa9.Farmacy.model.dto.ComplaintDTO;
import isa9.Farmacy.service.ComplaintService;
import isa9.Farmacy.service.UserService;
import isa9.Farmacy.support.ComplaintDTOtoComplaint;
import isa9.Farmacy.support.ComplaintToComplaintDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = {"http://localhost:3000", "https://pharmacy9.herokuapp.com"})
@RequestMapping("/api/complaints")
public class ComplaintController {

    private final ComplaintService complaintService;
    private final ComplaintToComplaintDTO complaintToComplaintDTO;
    private final ComplaintDTOtoComplaint complaintDTOtoComplaint;
    private final UserService userService;

    @Autowired
    public ComplaintController(ComplaintService complaintService, ComplaintToComplaintDTO complaintToComplaintDTO, ComplaintDTOtoComplaint complaintDTOtoComplaint, UserService userService) {
        this.complaintService = complaintService;
        this.complaintToComplaintDTO = complaintToComplaintDTO;
        this.complaintDTOtoComplaint = complaintDTOtoComplaint;
        this.userService = userService;
    }

    @PostMapping("/newComplaint")
    @PreAuthorize("hasAuthority('PATIENT')")
    public ResponseEntity<Integer> addComplaint(@RequestBody ComplaintDTO complaintDTO){
        System.out.println(
                this.complaintService.fileAComplaint(this.complaintDTOtoComplaint.convert(complaintDTO))
        );
        return new ResponseEntity<>(0, HttpStatus.OK);
    }

    @GetMapping("/")
    @PreAuthorize("hasAuthority('SYS_ADMIN')")
    public ResponseEntity<List<ComplaintDTO>> getAllComplaints(){
        List<ComplaintDTO> allComplaints = this.complaintToComplaintDTO.convert(this.complaintService.findAll());
        return new ResponseEntity<>(allComplaints, HttpStatus.OK);
    }

    @PostMapping("/responseToComplaint")
    @PreAuthorize("hasAuthority('SYS_ADMIN')")
    public ResponseEntity<Boolean> responseToComplaint(@RequestBody ComplaintDTO dto){
        return new ResponseEntity<>( this.complaintService.saveResponse(dto.getResponse(), dto.getId(), dto.getRespondent()), HttpStatus.OK);
    }

    @GetMapping("complaintsByPatient/{id}")
    @PreAuthorize("hasAuthority('PATIENT')")
    public ResponseEntity<List<ComplaintDTO>> getPatientsComplaints(@PathVariable Long id){
        Patient patient = (Patient) this.userService.findOne(id);
        List<Complaint> patientsComplaints = this.complaintService.complaintsOfPatient(patient);
        return new ResponseEntity<>(this.complaintToComplaintDTO.convert(patientsComplaints), HttpStatus.OK);
    }

    @GetMapping("responsesBySysAdmin/{id}")
    @PreAuthorize("hasAuthority('SYS_ADMIN')")
    public ResponseEntity<List<ComplaintDTO>> getAdminResponses(@PathVariable Long id){
        SysAdmin sysAdmin = (SysAdmin) this.userService.findOne(id);
        List<Complaint> adminResponses = this.complaintService.responsesOfAdmin(sysAdmin);
        return new ResponseEntity<>(this.complaintToComplaintDTO.convert(adminResponses), HttpStatus.OK);
    }
}
