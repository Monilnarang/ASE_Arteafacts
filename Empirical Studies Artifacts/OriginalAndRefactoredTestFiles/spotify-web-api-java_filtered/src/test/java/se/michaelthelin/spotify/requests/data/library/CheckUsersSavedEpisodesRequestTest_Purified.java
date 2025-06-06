package se.michaelthelin.spotify.requests.data.library;

import org.apache.hc.core5.http.ParseException;
import org.junit.jupiter.api.Test;
import se.michaelthelin.spotify.ITest;
import se.michaelthelin.spotify.TestUtil;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
import se.michaelthelin.spotify.requests.data.AbstractDataTest;
import java.io.IOException;
import java.util.concurrent.ExecutionException;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class CheckUsersSavedEpisodesRequestTest_Purified extends AbstractDataTest<Boolean[]> {

    private final CheckUsersSavedEpisodesRequest defaultRequest = ITest.SPOTIFY_API.checkUsersSavedEpisodes(ITest.ID_EPISODE, ITest.ID_EPISODE).setHttpManager(TestUtil.MockedHttpManager.returningJson("requests/data/library/CheckUsersSavedEpisodesRequest.json")).build();

    public CheckUsersSavedEpisodesRequestTest() throws Exception {
    }

    public void shouldReturnDefault(final Boolean[] booleans) {
        assertEquals(2, booleans.length);
    }

    @Test
    public void shouldComplyWithReference_1() {
        assertHasAuthorizationHeader(defaultRequest);
    }

    @Test
    public void shouldComplyWithReference_2() {
        assertEquals("https://api.spotify.com:443/v1/me/episodes/contains?ids=4GI3dxEafwap1sFiTGPKd1%2C4GI3dxEafwap1sFiTGPKd1", defaultRequest.getUri().toString());
    }
}
