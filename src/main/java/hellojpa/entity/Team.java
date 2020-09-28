package hellojpa.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Builder
@Setter
@Getter
@Entity
public class Team {
    @Id @GeneratedValue
    private Long id;

    private String name;

    @OneToMany(mappedBy = "team", fetch = FetchType.LAZY)
    List<Member> members = new ArrayList<>();
}
