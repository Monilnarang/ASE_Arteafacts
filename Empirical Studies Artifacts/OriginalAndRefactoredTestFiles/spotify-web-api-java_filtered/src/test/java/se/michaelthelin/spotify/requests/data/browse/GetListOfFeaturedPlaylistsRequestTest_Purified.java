package se.michaelthelin.spotify.requests.data.browse;

import org.apache.hc.core5.http.ParseException;
import org.junit.jupiter.api.Test;
import se.michaelthelin.spotify.TestUtil;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
import se.michaelthelin.spotify.model_objects.special.FeaturedPlaylists;
import se.michaelthelin.spotify.requests.data.AbstractDataTest;
import java.io.IOException;
import java.util.concurrent.ExecutionException;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class GetListOfFeaturedPlaylistsRequestTest_Purified extends AbstractDataTest<FeaturedPlaylists> {

    private final GetListOfFeaturedPlaylistsRequest defaultRequest = SPOTIFY_API.getListOfFeaturedPlaylists().setHttpManager(TestUtil.MockedHttpManager.returningJson("requests/data/browse/GetListOfFeaturedPlaylistsRequest.json")).country(COUNTRY).limit(LIMIT).locale(LOCALE).offset(OFFSET).timestamp(TIMESTAMP).build();

    public GetListOfFeaturedPlaylistsRequestTest() throws Exception {
    }

    public void shouldReturnDefault(final FeaturedPlaylists featuredPlaylists) {
        assertEquals("Monday morning music, coming right up!", featuredPlaylists.getMessage());
        assertNotNull(featuredPlaylists.getPlaylists());
    }

    @Test
    public void shouldComplyWithReference_1() {
        assertHasAuthorizationHeader(defaultRequest);
    }

    @Test
    public void shouldComplyWithReference_2() {
        assertEquals("https://api.spotify.com:443/v1/browse/featured-playlists?country=SE&limit=10&locale=sv_SE&offset=0&timestamp=2014-10-23T09%3A00%3A00", defaultRequest.getUri().toString());
    }
}
