package model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mockStatic;

import java.util.ArrayList;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;

import com.kahrs.app.UserConfig;
import com.kahrs.database.dao.DataContract;
import com.kahrs.database.dao.DataManager;
import com.kahrs.database.dao.DataPlayer;
import com.kahrs.database.dao.DataTeam;
import com.kahrs.model.GameModel;
import com.kahrs.model.Manager;
import com.kahrs.model.Player;
import com.kahrs.model.Team;
import com.kahrs.view.GameView;

public class TestGameModel {

    @InjectMocks
    private GameModel gameModel;

    @Mock
    private GameView gameView;

    private MockedStatic<UserConfig> userConfigMock;
    private MockedStatic<DataManager> dataManagerMock;
    private MockedStatic<DataTeam> dataTeamMock;
    private MockedStatic<DataPlayer> dataPlayerMock;
    private MockedStatic<DataContract> dataContractMock;

    private Manager testManager;
    private Team testTeam;
    private ArrayList<Player> testPlayers;

    @BeforeEach
    void setUp() {
        testManager = new Manager(1, "Test Manager", 1, 100000L, 1, 1, 1);
        testTeam = new Team(1, "Test Team", false);
        testPlayers = new ArrayList<>();

        userConfigMock = mockStatic(UserConfig.class);
        dataManagerMock = mockStatic(DataManager.class);
        dataTeamMock = mockStatic(DataTeam.class);
        dataPlayerMock = mockStatic(DataPlayer.class);
        dataContractMock = mockStatic(DataContract.class);

        gameModel = new GameModel();
        gameModel.setManager(testManager);
        gameModel.setTeam(testTeam);
    }

    @AfterEach
    void closeMock() {
        userConfigMock.close();
        dataManagerMock.close();
        dataTeamMock.close();
        dataPlayerMock.close();
        dataContractMock.close();
    }

    @Test
    void testContructor() {
        assertEquals(testManager, gameModel.getManager());
        assertEquals(testTeam, gameModel.getTeam());
        assertEquals(testPlayers, gameModel.getAllPlayers());
    }

    @Test
    void testContructorReduce() {
        GameModel model = new GameModel();
        assertEquals(model.getAllPlayers().size(), 0);
    }

    @Test
    void testSetManager() {
        assertEquals(testManager, gameModel.getManager());

        Manager testManager2 = new Manager(2, "Test Manager2", 2, 200000L, 2, 2, 2);
        gameModel.setManager(testManager2);
        assertEquals(testManager2, gameModel.getManager());
        
    }
    
    @Test
    void testSetTeam() {
        assertEquals(testTeam, gameModel.getTeam());

        Team testTeam2 = new Team(2, "Test Team2", false);
        gameModel.setTeam(testTeam2);
        assertEquals(testTeam2, gameModel.getTeam());
    }

    @Test
    void testSetView() {
        gameModel.setView(gameView);
        assertEquals(gameView, gameModel.view);
    }
}