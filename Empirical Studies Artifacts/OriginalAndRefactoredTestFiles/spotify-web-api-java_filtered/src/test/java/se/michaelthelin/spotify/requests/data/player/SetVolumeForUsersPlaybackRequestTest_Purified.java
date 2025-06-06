package se.michaelthelin.spotify.requests.data.player;

import org.apache.hc.core5.http.ParseException;
import org.junit.jupiter.api.Test;
import se.michaelthelin.spotify.ITest;
import se.michaelthelin.spotify.TestUtil;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
import se.michaelthelin.spotify.requests.data.AbstractDataTest;
import java.io.IOException;
import java.util.concurrent.ExecutionException;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static se.michaelthelin.spotify.Assertions.assertHasHeader;

public class SetVolumeForUsersPlaybackRequestTest_Purified extends AbstractDataTest<String> {

    private final SetVolumeForUsersPlaybackRequest defaultRequest = ITest.SPOTIFY_API.setVolumeForUsersPlayback(ITest.VOLUME_PERCENT).setHttpManager(TestUtil.MockedHttpManager.returningJson(null)).device_id(ITest.DEVICE_ID).build();

    public SetVolumeForUsersPlaybackRequestTest() throws Exception {
    }

    public void shouldReturnDefault(final String string) {
        assertNull(string);
    }

    @Test
    public void shouldComplyWithReference_1() {
        assertHasAuthorizationHeader(defaultRequest);
    }

    @Test
    public void shouldComplyWithReference_2() {
        assertHasHeader(defaultRequest, "Content-Type", "application/json");
    }

    @Test
    public void shouldComplyWithReference_3() {
        assertEquals("https://api.spotify.com:443/v1/me/player/volume?volume_percent=100&device_id=5fbb3ba6aa454b5534c4ba43a8c7e8e45a63ad0e", defaultRequest.getUri().toString());
    }
}
