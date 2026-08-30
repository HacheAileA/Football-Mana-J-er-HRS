package model;

import com.kahrs.model.Contract;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Classe TestContract pour tester les méthodes de Contract.
 * 
 * @author Ruben FOALEM
 *
 * @since 0.1
 *
 * @version 0.1
 */
public class TestContract {

    private Contract contract;
    private Contract contractReduce;

    /**
     * Prépare l'environnement de test avant chaque méthode.
     * Initialise un Contract par défaut.
     */
    @BeforeEach
    public void setUp() {
        contract = new Contract(1, 0, 1, 10, 1000);
        contractReduce = new Contract(0, 1);
    }


    /**
     * Vérifie les méthodes en lien avec l'id du Contract
     */
    @Test
    void testId() {
         assertEquals(contract.getId(),1);
         assertEquals(contract.getTeamId(),0);
         assertEquals(contract.getPlayerId(), 1);
         assertEquals(contract.getDuration(), 10);
         assertEquals(contract.getPrice(), 10000);

         assertEquals(contractReduce.getId(), 0);
         assertEquals(contractReduce.getTeamId(), 0);
         assertEquals(contractReduce.getPlayerId(), 1);
         assertEquals(contractReduce.getDuration(), Contract.DURATION_INIT);
         assertEquals(contractReduce.getPrice(), Contract.DURATION_INIT * 1000);
    }

    @Test
    void testSetId() {
        assertEquals(contract.getId(), 1);
        contract.setId(2);
        assertEquals(contract.getId(), 2);
    }

    /**
     * Vérifie les méthodes en lien avec la durée du Contract
     */
    @Test
    void testDuration() {
        assertEquals(contract.getDuration(), 10);
    }

    /**
     * Vérifie les méthodes de modification de la durée
     */
    @Test
    void testSetDuration() {
        contract.setDuration(5);
        assertEquals(contract.getDuration(), 5);
        assertEquals(contract.getPrice(), 5000); 
    }

    /**
     * Vérifie les méthodes en lien avec le prix du Contract
     */
    @Test
    void testPrice() {
        assertEquals(contract.getPrice(), 10000);
        assertEquals(contract.getPrice(), contract.getDuration() * 1000);
    }
}
