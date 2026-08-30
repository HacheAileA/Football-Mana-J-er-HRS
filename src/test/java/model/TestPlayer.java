package model;

import com.kahrs.model.Player;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Classe TestPlayer pour tester les méthodes de Player.
 *
 * @author Sofyane HARISSE
 *
 * @since 0.1
 *
 * @version 0.1
 */
public class TestPlayer {

    /** */
    private Player player;

    /**
     * Prépare l'environnement de test avant chaque méthode.
     * Initialise un joueur par défaut.
     */
    @BeforeEach
    void setUp(){
        // On initialise avec id=1, nom="Zidane" 
        player = new Player(1, "Zidane", Player.Poste.ATT, Player.Status.GOOD,  90, 100000, 50, 50, 50, 50, 50);
    }

    @Test
    void testConstructor() {
        Player player2 = new Player("Ronaldo", Player.Poste.ATT, Player.Status.GOOD, 90, 100000, 50, 50, 50, 50, 50);
        assertEquals(player2.getName(), "Ronaldo");
        assertEquals(player2.getPosition(), Player.Poste.ATT);
    }

     /**
     * Vérifie les méthodes en lien avec l'id du joueur
     */
    @Test
    void testId(){
        assertEquals(player.getId(), 1);
        player.setId(2);
        assertEquals(player.getId(), 2);
    }

    /**
     * On vérifie les méthodes en lien avec le nom du joueur
     */
    @Test
    void testName(){
        assertEquals(player.getName(), "Zidane");
    }

    /**
     * On vérifie que le Poste est correct
     */
    @Test
    void testPosition() {
        assertEquals(player.getPosition(), Player.Poste.ATT);
    }

    /**
     * On vérifie que la valeur est correcte
     */
    @Test
    void testValue() {
        assertEquals(player.getValue(), 100000);
    }

    /**
     * On vérifie que la note d'attaque est correcte
     */
    @Test
    void testAttack() {
        assertEquals(player.getAttack(), 50);
    }

    @Test
    void testSetAttack() {
        player.setAttack(80);
        assertEquals(player.getAttack(), 80);
        assertEquals(player.getNote(), 86);
    }

    /**
     * On vérifie que la note de défense est correcte
     */
    @Test
    void testDefense() {
        assertEquals(player.getDefense(), 50);
    }

    @Test
    void testSetDefense() {
        player.setDefense(70);
        assertEquals(player.getDefense(), 70);
        assertEquals(player.getNote(), 84);
    }

    @Test
    void testStatus() {
        assertEquals(player.getStatus(), Player.Status.GOOD);
        player.setState(Player.Status.INJURY);
        assertEquals(player.getStatus(), Player.Status.INJURY);
    }

    @Test
    void testCalculs() {
        player.setSpeed(80);
        assertEquals(player.getSpeed(), 80);
        
        player.setShoot(60);
        assertEquals(player.getShoot(), 60);
        
        assertEquals(player.getAttack(), 70);

        player.setPass(40);
        assertEquals(player.getPass(), 40);

        assertEquals(player.getDefense(), 60);

        assertEquals(player.getNote(), 86);
    }
}