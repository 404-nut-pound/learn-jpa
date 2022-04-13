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

    Member member = new Member();
    member.setId(1L);
    member.setName("hskim");

    tx.begin();

    em.persist(member);

    tx.commit();

    em.close();

    emf.close();
  }
}
