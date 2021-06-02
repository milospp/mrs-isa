package isa9.Farmacy.support;

import isa9.Farmacy.model.Complaint;
import isa9.Farmacy.model.Doctor;
import isa9.Farmacy.model.Patient;
import isa9.Farmacy.model.SysAdmin;
import isa9.Farmacy.model.dto.ComplaintDTO;
import isa9.Farmacy.service.PharmacyService;
import isa9.Farmacy.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ComplaintDTOtoComplaint implements Converter<ComplaintDTO, Complaint> {

    private final UserService userService;
    private final PharmacyService pharmacyService;

    @Autowired
    public ComplaintDTOtoComplaint(UserService userService, PharmacyService pharmacyService) {
        this.userService = userService;
        this.pharmacyService = pharmacyService;
    }

    @Override
    public Complaint convert(ComplaintDTO complaintDTO) {
        Complaint complaint = new Complaint();

        complaint.setDescription(complaintDTO.getDescription());
        complaint.setResponse(complaintDTO.getResponse());
        complaint.setAuthor((Patient) this.userService.findOne(complaintDTO.getAuthor()));
        complaint.setDoctor(null);
        complaint.setPharmacy(null);
        complaint.setRespondent(null);
        try{
            complaint.setDoctor((Doctor) this.userService.findOne(complaintDTO.getDoctor()));
        }catch (NullPointerException e){

        }
        try{
            complaint.setPharmacy(this.pharmacyService.findOne(complaintDTO.getPharmacy()));
        }catch (NullPointerException e){

        }
        try{
            complaint.setRespondent((SysAdmin) this.userService.findOne(complaintDTO.getRespondent()));
        }catch (NullPointerException e){

        }

        return complaint;
    }

    public List<Complaint> convert(List<ComplaintDTO> dtos) {
        List<Complaint> complaints = new ArrayList<>();
        for (ComplaintDTO dto : dtos) complaints.add(convert(dto));
        return complaints;
    }
}
