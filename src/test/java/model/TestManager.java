package model;

import com.kahrs.model.Manager;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Classe TestManager pour tester les méthodes de Manager.
 *
 * @author Ruben FOALEM
 *
 * @since 0.1
 *
 * @version 0.1
 */
public class TestManager {
    private Manager manager;
    private Manager managerReduce;

    /**
     * Prépare l'environnement de test avant chaque méthode.
     * Initialise un manager par défaut.
     */
    @BeforeEach
    public void setUp() {
        manager = new Manager(0, "Luis Enrique", 0, 100000L, 0, 0, 0);
        managerReduce = new Manager("Zak Brown", 0);
    }

    /**
     * Vérifie les méthodes en lien avec le nom du manager (NOUVEAU)
     */
    @Test
    void testName() {
        assertEquals(manager.getName(), "Luis Enrique");
        assertEquals(managerReduce.getName(), "Zak Brown");
    }

    /**
     * Vérifie les méthodes en lien avec l'id du manager
     */
    @Test
    void testId() {
        assertEquals(manager.getId(), 0);
        manager.setId(2);
        assertEquals(manager.getId(), 2);

        assertEquals(manager.getTeamId(), 0);
    }

    /**
     * Vérifie que l'argent est bien initialisé
     */
    @Test
    void testMoney() {
        assertEquals(manager.getMoney(), 100000);
    }

    @Test
    void testSetMoney() {
        manager.setMoney(100L);
        assertEquals(manager.getMoney(), 100L);

        manager.setMoney(-500L);
        assertEquals(manager.getMoney(), 0L);
    }

    /**
     * Vérifie que les loses est bien initialisé
     */
    @Test
    void testWins() {
        assertEquals(manager.getWins(), 0);
    }

    @Test
    void testSetWins() {
        manager.setWins(10);
        assertEquals(manager.getWins(), 10);

        manager.setWins(-5);
        assertEquals(manager.getWins(), 0);
    }

    /**
     * Vérifie que les draws est bien initialisé
     */
    @Test
    void testDraws() {
        assertEquals(manager.getDraws(), 0);
    }

    @Test
    void testSetDraws() {
        manager.setDraws(10);
        assertEquals(manager.getDraws(), 10);

        manager.setDraws(-2);
        assertEquals(manager.getDraws(), 0);
    }

    /**
     * Vérifie que les loses est bien initialisé
     */
    @Test
    void testLoses() {
        assertEquals(manager.getLoses(), 0);
    }

    @Test
    void testSetLoses() {
        manager.setLoses(10);
        assertEquals(manager.getLoses(), 10);

        manager.setLoses(-10);
        assertEquals(manager.getLoses(), 0);
    }
}
