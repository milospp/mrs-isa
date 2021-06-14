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

    @Override
    public int hashCode() {
        return Objects.hash(search, filterIme, filterPrez, filterBroj, filterAdrD, filterAdrG, filterAdrU, filterAdrB);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SearchHelp that = (SearchHelp) o;
        return Objects.equals(search, that.search) && Objects.equals(filterIme, that.filterIme) && Objects.equals(filterPrez, that.filterPrez) && Objects.equals(filterBroj, that.filterBroj) && Objects.equals(filterAdrD, that.filterAdrD) && Objects.equals(filterAdrG, that.filterAdrG) && Objects.equals(filterAdrU, that.filterAdrU) && Objects.equals(filterAdrB, that.filterAdrB);
    }
}
