package isa9.Farmacy.controller;


import isa9.Farmacy.service.ComplaintService;
import isa9.Farmacy.support.ComplaintDTOtoComplaint;
import isa9.Farmacy.support.ComplaintToComplaintDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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


}
