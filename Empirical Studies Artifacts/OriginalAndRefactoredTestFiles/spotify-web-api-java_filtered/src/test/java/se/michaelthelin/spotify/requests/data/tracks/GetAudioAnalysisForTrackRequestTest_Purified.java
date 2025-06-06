package se.michaelthelin.spotify.requests.data.tracks;

import org.apache.hc.core5.http.ParseException;
import org.junit.jupiter.api.Test;
import se.michaelthelin.spotify.ITest;
import se.michaelthelin.spotify.TestUtil;
import se.michaelthelin.spotify.enums.Modality;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
import se.michaelthelin.spotify.model_objects.miscellaneous.AudioAnalysis;
import se.michaelthelin.spotify.requests.data.AbstractDataTest;
import java.io.IOException;
import java.util.concurrent.ExecutionException;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class GetAudioAnalysisForTrackRequestTest_Purified extends AbstractDataTest<AudioAnalysis> {

    private final GetAudioAnalysisForTrackRequest defaultRequest = ITest.SPOTIFY_API.getAudioAnalysisForTrack(ITest.ID_TRACK).setHttpManager(TestUtil.MockedHttpManager.returningJson("requests/data/tracks/GetAudioAnalysisForTrackRequest.json")).build();

    public GetAudioAnalysisForTrackRequestTest() throws Exception {
    }

    public void shouldReturnDefault(final AudioAnalysis audioAnalysis) {
        assertEquals(1, audioAnalysis.getBars().length);
        assertEquals(1, audioAnalysis.getBeats().length);
        assertNotNull(audioAnalysis.getMeta());
        assertEquals(1, audioAnalysis.getSections().length);
        assertEquals(1, audioAnalysis.getSegments().length);
        assertEquals(1, audioAnalysis.getTatums().length);
        assertNotNull(audioAnalysis.getTrack(), "");
        assertEquals(Modality.MINOR, audioAnalysis.getTrack().getMode());
    }

    @Test
    public void shouldComplyWithReference_1() {
        assertHasAuthorizationHeader(defaultRequest);
    }

    @Test
    public void shouldComplyWithReference_2() {
        assertEquals("https://api.spotify.com:443/v1/audio-analysis/01iyCAUm8EvOFqVWYJ3dVX", defaultRequest.getUri().toString());
    }
}
