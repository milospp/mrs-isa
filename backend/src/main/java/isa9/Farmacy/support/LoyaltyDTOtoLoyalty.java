package isa9.Farmacy.support;

import isa9.Farmacy.model.LoyaltyProgram;
import isa9.Farmacy.model.dto.LoyaltyProgramDTO;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class LoyaltyDTOtoLoyalty implements Converter<LoyaltyProgramDTO, LoyaltyProgram> {
    @Override
    public LoyaltyProgram convert(LoyaltyProgramDTO loyaltyProgramDTO) {
        LoyaltyProgram loyaltyProgram = new LoyaltyProgram();

        loyaltyProgram.setId(loyaltyProgramDTO.getId());
        loyaltyProgram.setName(loyaltyProgramDTO.getName());
        loyaltyProgram.setDiscount(loyaltyProgramDTO.getDiscount());
        loyaltyProgram.setRequiredPoints(loyaltyProgramDTO.getRequiredPoints());


        return loyaltyProgram;
    }
}
