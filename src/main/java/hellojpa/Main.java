package hellojpa;

import hellojpa.entity.Member;
import hellojpa.entity.MemberType;
import hellojpa.entity.Team;

import javax.persistence.*;
import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");

        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        tx.begin();
        try {

            // team은 주인이 아니므로 read용도..
            // 여기에 값을 저장해도 db에 반영되지 않음
            Team team = Team.builder()
                    .name("team_name")
                    .build();

            em.persist(team);

            Member member = Member.builder()
                    .name("hello")
                    .memberType(MemberType.USER)
                    .team(team) //member가 주인으므로 (fk가 있는 곳) db team_id값이 저장 됨..
                    .build();

            em.persist(member);

            //해당 flush가 없으면 아래 regData는  null임
            //이유는 @CreationTimestamp은 DB에 저장되는 시점 
            //그러나 flush를 안하면 DB에 저장되지 않음
            em.flush();


            Member helloMember = em.find(Member.class, member.getId());
            System.out.println("HelloMember : " + helloMember);
            System.out.println("###########################################");
            System.out.println("regData : " + helloMember.getRegDate().toString());
            System.out.println("###########################################");

            /*
            원래는 여기서 flush를 해서 DB와 persistence Context와 동기화를 맞춰야 하지만 
            createQuery하는 시점에 자동으로 flush를 해줌..
            타 DB Lib (mybatis등등) 병행해서 사용할 경우 flush를 신경 써줘야함
             */
            
            TypedQuery query = em.createQuery("select m from Member m", Member.class);
            List<Member> members = query.getResultList();

            tx.commit();
        } catch (Exception e) {
            System.out.println(Arrays.toString(e.getStackTrace()));
            tx.rollback();
        } finally {
            em.close();
        }
        emf.close();
    }
}
