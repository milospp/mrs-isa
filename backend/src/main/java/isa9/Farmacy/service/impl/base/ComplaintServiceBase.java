package isa9.Farmacy.service.impl.base;

import isa9.Farmacy.model.Complaint;
import isa9.Farmacy.model.Patient;
import isa9.Farmacy.model.SysAdmin;
import isa9.Farmacy.service.ComplaintService;
import isa9.Farmacy.service.UserService;
import isa9.Farmacy.utils.MailService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

public abstract class ComplaintServiceBase implements ComplaintService {

    protected UserService userService;
    protected MailService mailService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setMailService(MailService mailService) {
        this.mailService = mailService;
    }

    @Override
    public Complaint fileAComplaint(Complaint complaint) {
        complaint.getAuthor().getComplaints().add(complaint);
        this.save(complaint);
        this.userService.save(complaint.getAuthor());

        return complaint;
    }

    @Override
    public boolean saveResponse(String response, Long complaintId, Long respondentId) {
        Complaint toBeRespondedTo = this.findOne(complaintId);

        if(toBeRespondedTo == null) return false;

        toBeRespondedTo.setResponse(response);
        toBeRespondedTo.setRespondent((SysAdmin) this.userService.findOne(respondentId));
        mailService.sendResponseMail(toBeRespondedTo);
        this.save(toBeRespondedTo);

        return true;
    }

    @Override
    public List<Complaint> complaintsOfPatient(Patient patient) {
        List<Complaint> complaints = new ArrayList<>();

        for(Complaint c : this.findAll()){
            if(c.getAuthor().getId().equals(patient.getId())){
                complaints.add(c);
            }
        }

        return complaints;
    }

    @Override
    public List<Complaint> responsesOfAdmin(SysAdmin sysAdmin) {
        List<Complaint> responses = new ArrayList<>();

        for(Complaint c : this.findAll()){
            if(!c.getResponse().equals("")){
                if(c.getRespondent().getId() == sysAdmin.getId()){
                    responses.add(c);
                }
            }
        }

        return responses;
    }
}
