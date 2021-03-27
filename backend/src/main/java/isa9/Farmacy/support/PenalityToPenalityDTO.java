package isa9.Farmacy.support;


        import isa9.Farmacy.model.*;
        import isa9.Farmacy.model.dto.PenalityDTO;
        import org.springframework.core.convert.converter.Converter;
        import org.springframework.stereotype.Component;

        import java.util.ArrayList;
        import java.util.List;
        import java.util.Set;
        import java.util.stream.Collectors;

@Component
public class PenalityToPenalityDTO implements Converter<Penality, PenalityDTO> {

    @Override
    public PenalityDTO convert(Penality penality) {
        PenalityDTO dto = new PenalityDTO();

        dto.setId(penality.getId());
        dto.setReason(penality.getReason());
        dto.setDate(penality.getDate());
        return dto;
    }

    public List<PenalityDTO> convert(List<Penality> penalties) {
        List<PenalityDTO> dto = new ArrayList<>();
        for (Penality penality : penalties){
            dto.add(convert(penality));
        }
        return dto;
    }

    public List<PenalityDTO> convert(Set<Penality> penalties) {
        List<PenalityDTO> dto = new ArrayList<>();
        for (Penality penality : penalties){
            dto.add(convert(penality));
        }
        return dto;
    }
}
