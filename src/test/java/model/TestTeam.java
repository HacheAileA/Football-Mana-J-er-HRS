package model;

import com.kahrs.model.Team;
import com.kahrs.model.Player;
import com.kahrs.model.Contract;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;

/**
 * Classe TestTeam pour tester les méthodes de Team.
 *
 * @author Sofyane Harisse
 *
 * @since 0.1
 *
 * @version 0.1
 */
public class TestTeam {

    /** */
    private Team team;
    /** */
    private Player player1;
    /** */
    private Player player2;
    
    private Contract contract1;
    private Contract contract2;

    private Team teamReduce;
    private Team teamName;

    /**
     * Initialise une équipe et des joueurs par défaut.
     */
    @BeforeEach
    public void setUp() {
        team = new Team(1, "Equipe de France", false);
        player1 = new Player(1, "Mbappé", Player.Poste.ATT, Player.Status.GOOD, 90, 100000, 50, 50, 50, 50, 50);
        player2 = new Player(2, "Griezmann", Player.Poste.MIL ,Player.Status.GOOD, 85, 75000, 50, 50, 50, 50, 50);

        contract1 = new Contract(1, 1, 1, 10, 1000);
        contract2 = new Contract(2, 1, 2, 10, 1000);

        teamReduce = new Team("NameBot", true);
        teamName = new Team("Name");
    }

    /**
     * Vérifie les méthodes en lien avec l'id de l'équipe
     */
    @Test
    void testId() {
        assertEquals(team.getId(), 1);
        team.setId(5);
        assertEquals(team.getId(), 5);

        assertEquals(teamReduce.getId(), 0);
        assertEquals(teamName.getId(), 0);
    }

    /**
     * Vérifie les méthodes en lien avec le nom de l'équipe
     */
    @Test
    void testName() {
        assertEquals(team.getName(), "Equipe de France");
    }

    /**
     * Vérifie les méthodes en lien avec le boolean bot de l'équipe
     */
    @Test
    void testIsBot() {
        assertEquals(team.isBot(), false);
    }

    /**
     * Vérifie les méthodes d'ajout de joueurs (addPlayer et addPlayers)
     * et vérifie la contrainte d'unicité (contains), cas ou il y'aurait un doublon.
     */
    @Test
    void testAddPlayers() {
        // Vérification si la liste est vide au début
        assertTrue(team.getStarters().isEmpty());
        assertTrue(team.getSubstitutes().isEmpty());

        // Ajout d'un joueur
        team.addPlayer(player1);
        assertEquals(team.getSubstitutes().size(), 1);
        assertTrue(team.getSubstitutes().contains(player1));

        // On essaie d'ajouter un joueur déja présent
        team.addPlayer(player1);
        assertEquals(team.getSubstitutes().size(), 1);

        // Test de l'ajout d'une liste de joueurs
        ArrayList<Player> newPlayers = new ArrayList<>();
        newPlayers.add(player2);

        team.addPlayers(newPlayers);
        assertEquals(team.getSubstitutes().size(), 2);
        assertTrue(team.getSubstitutes().contains(player2));
    }

    @Test
    void testAddContracts() {
        assertTrue(team.getContracts().isEmpty());

        assertTrue(team.addContract(contract1));
        assertEquals(team.getContracts().size(), 1);
        assertTrue(team.getContracts().contains(contract1));

        assertFalse(team.addContract(contract1));
        assertEquals(team.getContracts().size(), 1);

        ArrayList<Contract> newContracts = new ArrayList<>();
        newContracts.add(contract2);

        team.addContracts(newContracts);
        assertEquals(team.getContracts().size(), 2);
        assertTrue(team.getContracts().contains(contract2));
    }

    @Test
    void testRemoveContract() {
        team.addContract(contract1);
        team.addContract(contract2);
        assertEquals(team.getContracts().size(), 2);

        assertTrue(team.removeContract(contract1));
        assertEquals(team.getContracts().size(), 1);
        assertFalse(team.getContracts().contains(contract1));
        assertTrue(team.getContracts().contains(contract2));

        assertFalse(team.removeContract(contract1));
        assertEquals(team.getContracts().size(), 1);
    }

    @Test
    void testGetContractPlayerId() {
        team.addContract(contract1);
        team.addContract(contract2);

        assertEquals(team.getContractPlayerId(1), contract1);
        assertEquals(team.getContractPlayerId(2), contract2);
        
        assertNull(team.getContractPlayerId(99));
    }
}