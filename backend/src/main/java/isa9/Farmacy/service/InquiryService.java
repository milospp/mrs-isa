package isa9.Farmacy.service;

import isa9.Farmacy.model.InquiryMedicine;

import java.util.List;

public interface InquiryService extends GenericService<InquiryMedicine> {
    List<InquiryMedicine> getAllForPharmacy(Long pharmacyId);
}
