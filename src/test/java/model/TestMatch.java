package model;

import com.kahrs.model.Match;
import com.kahrs.model.Team;
import com.kahrs.model.Player;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Classe TestMatch pour tester les méthodes de Match.
 * 
 * @author Ruben FOALEM
 *
 * @since 0.1
 *
 * @version 0.1
 */
public class TestMatch {

    private Match match;
    private LocalDate date;


    /**
     * Prépare l'environnement de test avant chaque méthode.
     * Initialise un match par défaut.
     */
    @BeforeEach
    public void setUp() {
        date = LocalDate.of(2023, 1, 1);
        Team home = new Team(0, "Home", false);
        Team away = new Team(1, "Away", false);
        match = new Match(0, home, away, date);
    }

    /**
     * Vérifie les méthodes de Match
     */
    @Test
    void testMatch() {
        match.butAway();
        match.butHome();

        assertEquals(match.getScoreHome(), 1);
        assertEquals(match.getScoreAway(), 1);

        match.butHome();
        assertEquals(match.getTeamIdWinner(), -2);

        match.setDuree(0);
        assertEquals(match.getTeamIdWinner(), match.getHomeId());

        match.butAway(); match.butAway();
        assertEquals(match.getTeamIdWinner(), match.getAwayId());
    }

    @Test
    void testId() {
        assertEquals(match.getId(), 0);
        match.setId(10);
        assertEquals(match.getId(), 10);
    }

    @Test
    void testTeamsId() {
        assertEquals(match.getHomeId(), 0);
        assertEquals(match.getAwayId(), 1);
    }

    @Test
    void testScore() {
        assertEquals(match.getScoreHome(), 0);
        match.setScoreHome(2);
        assertEquals(match.getScoreHome(), 2);

        assertEquals(match.getScoreAway(), 0);
        match.setScoreAway(4);
        assertEquals(match.getScoreAway(), 4);
    }

    @Test
    void testDate() {
        assertEquals(match.getDate(), date);
    }

    @Test
    void testDuree() {
        assertEquals(match.getDuree(), 90);
    }

    @Test
    void testMoyenneStats() {
        Team team = new Team("PSG", true);
        Player p1 = new Player(1, "P1", Player.Poste.ATT, Player.Status.GOOD, 90, 100, 80, 40, 90, 85, 70);
        Player p2 = new Player(2, "P2", Player.Poste.ATT, Player.Status.GOOD, 90, 100, 70, 50, 80, 75, 60);
        team.addPlayer(p1);
        team.addPlayer(p2);
        
        assertEquals(Match.getMoyenneStats(team, "attack"), 75.0);
        assertEquals(Match.getMoyenneStats(team, "defense"), 45.0);
        assertEquals(Match.getMoyenneStats(team, "speed"), 85.0);
        assertEquals(Match.getMoyenneStats(team, "shoot"), 80.0);
        assertEquals(Match.getMoyenneStats(team, "pass"), 65.0);
        
        assertEquals(Match.getMoyenneStats(team, "unknown"), 0.0);
    }

    @Test
    void testWinner() {
        match.setDuree(0);
        
        match.setScoreHome(2);
        match.setScoreAway(2);
        assertEquals(match.getTeamIdWinner(), -1);

        match.setScoreHome(4);
        match.setScoreAway(2);
        assertEquals(match.getTeamIdWinner(), 0);

        match.setScoreHome(2);
        match.setScoreAway(4);
        assertEquals(match.getTeamIdWinner(), 1);
    }
}
