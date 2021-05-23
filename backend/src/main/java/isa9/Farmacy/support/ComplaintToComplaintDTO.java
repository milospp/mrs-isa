package isa9.Farmacy.support;

import isa9.Farmacy.model.Complaint;
import isa9.Farmacy.model.Offer;
import isa9.Farmacy.model.dto.ComplaintDTO;
import isa9.Farmacy.model.dto.OfferDTO;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ComplaintToComplaintDTO implements Converter<Complaint, ComplaintDTO> {
    @Override
    public ComplaintDTO convert(Complaint complaint) {
        ComplaintDTO dto = new ComplaintDTO();

        dto.setAuthor(complaint.getAuthor().getId());
        dto.setId(complaint.getId());
        dto.setDescription(complaint.getDescription());
        dto.setResponse(complaint.getResponse());
        dto.setDoctor(0L);
        dto.setPharmacy(0L);
        try{
            dto.setDoctor(complaint.getDoctor().getId());
            dto.setPharmacy(complaint.getPharmacy().getId());
        }catch(NullPointerException e){

        }
        return dto;
    }

    public List<ComplaintDTO> convert(List<Complaint> complaints) {
        List<ComplaintDTO> dtos = new ArrayList<>();
        for (Complaint c : complaints) dtos.add(convert(c));
        return dtos;
    }
}
