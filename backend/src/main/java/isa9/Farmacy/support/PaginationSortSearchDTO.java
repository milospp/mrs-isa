package isa9.Farmacy.support;

import lombok.*;

import java.util.Map;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaginationSortSearchDTO {
    private int pageNo;
    private int pageSize;
    private String sortBy;
    private boolean ascending;
    private Map<String, String> searchParams;
}
