package hellojpa.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@ToString
@Builder
@Setter
@Getter
@Entity
public class Member {
    @Id @GeneratedValue
    private Long id;

    @Column(name = "user_name")
    private String name;

    private int age;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id")
    private Team team;


//    @Temporal(TemporalType.TIMESTAMP)
    @CreationTimestamp
    private LocalDateTime regDate;

    @Enumerated(EnumType.STRING)
    private MemberType memberType;
}
