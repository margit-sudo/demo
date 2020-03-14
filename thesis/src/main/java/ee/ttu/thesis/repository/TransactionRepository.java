package ee.ttu.thesis.repository;

import ee.ttu.thesis.domain.TransactionDto;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.TransactionScoped;
import javax.transaction.Transactional;
import java.util.List;

@Repository
public class TransactionRepository {
    @PersistenceContext
    private EntityManager em;

    @Transactional
    public TransactionDto addTransactionToDb(TransactionDto t){
        em.persist(t);
        return t;
    }

   /* @Transactional
    public List<TransactionDto> getOrdersList() {
        String sqlStatement = "select t from transactions";
        return em.createQuery(sqlStatement, TransactionDto.class).getResultList();
    }*/
}
