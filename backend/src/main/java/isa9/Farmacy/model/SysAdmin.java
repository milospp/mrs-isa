package isa9.Farmacy.model;

import javax.persistence.Entity;
import javax.persistence.Table;
import lombok.*;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@Builder

@Entity
@Table(name = "sys_admin")
public class SysAdmin extends User{



}
