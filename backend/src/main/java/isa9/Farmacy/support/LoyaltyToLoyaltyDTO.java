package isa9.Farmacy.support;

import isa9.Farmacy.model.Doctor;
import isa9.Farmacy.model.LoyaltyProgram;
import isa9.Farmacy.model.dto.DoctorDTO;
import isa9.Farmacy.model.dto.LoyaltyProgramDTO;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class LoyaltyToLoyaltyDTO implements Converter<LoyaltyProgram, LoyaltyProgramDTO> {

    @Override
    public LoyaltyProgramDTO convert(LoyaltyProgram loyaltyProgram) {
        LoyaltyProgramDTO dto = new LoyaltyProgramDTO();

        dto.setName(loyaltyProgram.getName());
        dto.setId(loyaltyProgram.getId());
        dto.setDiscount(loyaltyProgram.getDiscount());
        dto.setRequiredPoints(loyaltyProgram.getRequiredPoints());

        return dto;
    }

    public List<LoyaltyProgramDTO> convert(List<LoyaltyProgram> loyaltyPrograms) {
        List<LoyaltyProgramDTO> dtos = new ArrayList<>();
        for (LoyaltyProgram loyaltyProgram : loyaltyPrograms){
            dtos.add(convert(loyaltyProgram));
        }
        return dtos;
    }
}
