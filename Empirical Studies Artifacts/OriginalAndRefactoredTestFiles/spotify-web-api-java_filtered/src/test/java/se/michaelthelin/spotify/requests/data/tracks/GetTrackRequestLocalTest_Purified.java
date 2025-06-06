package se.michaelthelin.spotify.requests.data.tracks;

import org.apache.hc.core5.http.ParseException;
import org.junit.jupiter.api.Test;
import se.michaelthelin.spotify.ITest;
import se.michaelthelin.spotify.TestUtil;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
import se.michaelthelin.spotify.model_objects.specification.Track;
import se.michaelthelin.spotify.requests.data.AbstractDataTest;
import java.io.IOException;
import java.util.concurrent.ExecutionException;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class GetTrackRequestLocalTest_Purified extends AbstractDataTest<Track> {

    private final GetTrackRequest defaultRequest = ITest.SPOTIFY_API.getTrack(ITest.ID_TRACK).setHttpManager(TestUtil.MockedHttpManager.returningJson("requests/data/tracks/GetTrackRequestLocal.json")).market(ITest.MARKET).build();

    public GetTrackRequestLocalTest() throws Exception {
    }

    public void shouldReturnDefault(final Track track) {
        assertEquals(222200, (int) track.getDurationMs());
    }

    @Test
    public void shouldComplyWithReference_1() {
        assertHasAuthorizationHeader(defaultRequest);
    }

    @Test
    public void shouldComplyWithReference_2() {
        assertEquals("https://api.spotify.com:443/v1/tracks/01iyCAUm8EvOFqVWYJ3dVX?market=SE", defaultRequest.getUri().toString());
    }
}
