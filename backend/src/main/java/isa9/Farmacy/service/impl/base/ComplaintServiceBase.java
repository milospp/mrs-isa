package isa9.Farmacy.service.impl.base;

import isa9.Farmacy.model.Complaint;
import isa9.Farmacy.service.ComplaintService;
import isa9.Farmacy.service.UserService;
import isa9.Farmacy.utils.MailService;
import org.springframework.beans.factory.annotation.Autowired;

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
    public boolean saveResponse(String response, Long complaintId) {
        Complaint toBeRespondedTo = this.findOne(complaintId);

        if(toBeRespondedTo == null) return false;

        toBeRespondedTo.setResponse(response);
        mailService.sendResponseMail(toBeRespondedTo);
        this.save(toBeRespondedTo);

        return true;
    }
}
