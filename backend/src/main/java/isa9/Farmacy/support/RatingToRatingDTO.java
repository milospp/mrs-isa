package isa9.Farmacy.support;

import isa9.Farmacy.model.Rating;
import isa9.Farmacy.model.dto.RatingDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Component
public class RatingToRatingDTO implements Converter<Rating, RatingDTO> {

    @Override
    public RatingDTO convert(Rating rating) {
        if (rating == null) return null;

        RatingDTO dto = new RatingDTO();

        dto.setId(rating.getId());
        dto.setRating(rating.getRating());
        dto.setUser(rating.getUser().getId());

        return dto;
    }

    public List<RatingDTO> convert(Collection<Rating> ratings) {
        List<RatingDTO> dto = new ArrayList<>();
        for (Rating rating : ratings){
            dto.add(convert(rating));
        }
        return dto;
    }
}
