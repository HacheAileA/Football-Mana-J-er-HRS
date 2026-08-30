package app;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.kahrs.app.UserConfig;
import com.google.gson.JsonObject;

public class TestUserConfig {

    JsonObject json;

    @BeforeEach
    void setup() {
        json = UserConfig.getConfig();
    }

    @Test
    void testManagerId() {
        assertTrue(json.has("ManagerId"));
    }

    @Test
    void testPreferences() throws IOException {        
        assertTrue(json.has("preferences"));
    }

    @Test
    void testLanguage() {
        assertTrue(json.has("preferences"));
        JsonObject preferences = json.getAsJsonObject("preferences");
        assertTrue(preferences.has("language"));
    }

    @Test
    void testVolumeAndSounds() {
        assertTrue(json.has("preferences"));
        JsonObject preferences = json.getAsJsonObject("preferences");
        assertTrue(preferences.has("volume"));
        assertTrue(preferences.has("soundEffects"));
    }
}