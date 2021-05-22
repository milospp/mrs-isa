package isa9.Farmacy.service.impl.base;

import isa9.Farmacy.model.Complaint;
import isa9.Farmacy.service.ComplaintService;
import isa9.Farmacy.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class ComplaintServiceBase implements ComplaintService {

    protected UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public Complaint fileAComplaint(Complaint complaint) {
        complaint.getAuthor().getComplaints().add(complaint);
        this.save(complaint);
        this.userService.save(complaint.getAuthor());

        return complaint;
    }
}
