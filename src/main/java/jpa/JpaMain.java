package jpa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import jpa.shop.Customer;

public class JpaMain {
  public static void main(String[] args) {
    EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");

    EntityManager em = emf.createEntityManager();

    EntityTransaction tx = em.getTransaction();

    tx.begin();

    try {
      Customer customer = new Customer();
      customer.setId(1L);
      customer.setName("hskim");

      // insert
      em.persist(customer);

      List<Customer> memberList = em.createQuery("select m from Member as m", Customer.class).setFirstResult(0)
          .setMaxResults(10).getResultList();

      System.out.println("memberList - " + memberList);

      // select
      Customer findMember = em.find(Customer.class, 1L);

      System.out.println("findMemeber => " + findMember.toString());

      // update
      findMember.setName("404-nut-pound");

      findMember = em.find(Customer.class, 1L);

      System.out.println("findMemeber after update => " + findMember.toString());

      // delete
      em.remove(customer);

//      Team team = new Team();
//      team.setName("TeamA");
//      em.persist(team);
//      Member member = new Member();
//      member.setUserName("member1");
//      // 역방향(주인이 아닌 방향)만 연관관계 설정
//      // team.getMemberList().add(member); // 매핑 편의를 위해 Member.setTeam에 해당 코드를 위치
//      // 연관관계의 주인에 값 설정
//      member.setTeam(team); // **
//      // 양방향 매핑을 사용할 경우 toString()과 같은 무한 루프를 조심하자
//      em.persist(member);

      tx.commit();
    } catch (Exception e) {
      tx.rollback();
    } finally {
      em.close();
    }

    emf.close();
  }
}
