package jpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class JpaMain {
  public static void main(String[] args) {
    EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");

    EntityManager em = emf.createEntityManager();

    EntityTransaction tx = em.getTransaction();

    tx.begin();

    try {
      Member member = new Member();
      member.setId(1L);
      member.setName("hskim");
      // insert
      em.persist(member);

      // select
      Member findMember = em.find(Member.class, 1L);

      System.out.println("findMemeber => " + findMember.toString());

      // update
      findMember.setName("404-nut-pound");

      findMember = em.find(Member.class, 1L);

      System.out.println("findMemeber after update => " + findMember.toString());

      // delete
      em.remove(member);

      tx.commit();
    } catch (Exception e) {
      tx.rollback();
    } finally {
      em.close();
    }

    emf.close();
  }
}
