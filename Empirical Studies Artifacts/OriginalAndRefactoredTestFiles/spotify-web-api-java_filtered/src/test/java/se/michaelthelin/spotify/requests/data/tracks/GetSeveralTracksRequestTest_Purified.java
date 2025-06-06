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

public class GetSeveralTracksRequestTest_Purified extends AbstractDataTest<Track[]> {

    private final GetSeveralTracksRequest defaultRequest = ITest.SPOTIFY_API.getSeveralTracks(ITest.ID_TRACK).setHttpManager(TestUtil.MockedHttpManager.returningJson("requests/data/tracks/GetSeveralTracksRequest.json")).market(ITest.MARKET).build();

    public GetSeveralTracksRequestTest() throws Exception {
    }

    public void shouldReturnDefault(final Track[] tracks) {
        assertEquals(2, tracks.length);
    }

    @Test
    public void shouldComplyWithReference_1() {
        assertHasAuthorizationHeader(defaultRequest);
    }

    @Test
    public void shouldComplyWithReference_2() {
        assertEquals("https://api.spotify.com:443/v1/tracks?ids=01iyCAUm8EvOFqVWYJ3dVX&market=SE", defaultRequest.getUri().toString());
    }
}
