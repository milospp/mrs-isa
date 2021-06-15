package isa9.Farmacy.service;

import isa9.Farmacy.model.Complaint;
import isa9.Farmacy.model.Patient;
import isa9.Farmacy.model.SysAdmin;

import java.util.List;

public interface ComplaintService extends GenericService<Complaint> {
    Complaint fileAComplaint(Complaint complaint);
    boolean saveResponse(String response, Long complaintId, Long respondentId);
    List<Complaint> complaintsOfPatient(Patient patient);
    List<Complaint> responsesOfAdmin(SysAdmin sysAdmin);
}
