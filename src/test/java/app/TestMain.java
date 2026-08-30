package app;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mockConstruction;

import org.junit.jupiter.api.Test;
import org.mockito.MockedConstruction;

import com.kahrs.app.Main;
import com.kahrs.controller.GameController;
import com.kahrs.model.GameModel;
import com.kahrs.view.GameView;

public class TestMain {

    @Test
    void testInitialisation() {
        try (MockedConstruction<GameModel> mockedModel = mockConstruction(GameModel.class);
                MockedConstruction<GameView> mockedView = mockConstruction(GameView.class);
                MockedConstruction<GameController> mockedController = mockConstruction(GameController.class)) {

            assertDoesNotThrow(() -> Main.main(new String[] {}));

            assertEquals(1, mockedModel.constructed().size());
            assertEquals(1, mockedView.constructed().size());
            assertEquals(1, mockedController.constructed().size());
        }
    }
}
