package isa9.Farmacy.service;

import isa9.Farmacy.model.Complaint;

public interface ComplaintService extends GenericService<Complaint> {
    Complaint fileAComplaint(Complaint complaint);
}
