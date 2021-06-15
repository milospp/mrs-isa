package isa9.Farmacy.model.dto;

import isa9.Farmacy.model.Pharmacist;
import lombok.*;

import java.util.Objects;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SearchHelp {
    private String search ;
    private String filterIme;
    private String filterPrez;
    private String filterBroj;
    private String filterAdrD;
    private String filterAdrG;
    private String filterAdrU;
    private String filterAdrB;

}
