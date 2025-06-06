package se.michaelthelin.spotify.requests.data.follow;

import org.apache.hc.core5.http.ParseException;
import org.junit.jupiter.api.Test;
import se.michaelthelin.spotify.TestUtil;
import se.michaelthelin.spotify.enums.ModelObjectType;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
import se.michaelthelin.spotify.requests.data.AbstractDataTest;
import java.io.IOException;
import java.util.concurrent.ExecutionException;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class CheckCurrentUserFollowsArtistsOrUsersRequestTest_Purified extends AbstractDataTest<Boolean[]> {

    private final CheckCurrentUserFollowsArtistsOrUsersRequest defaultRequest = SPOTIFY_API.checkCurrentUserFollowsArtistsOrUsers(ModelObjectType.ARTIST, new String[] { ID_ARTIST, ID_ARTIST }).setHttpManager(TestUtil.MockedHttpManager.returningJson("requests/data/follow/CheckCurrentUserFollowsArtistsOrUsersRequest.json")).build();

    public CheckCurrentUserFollowsArtistsOrUsersRequestTest() throws Exception {
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
        assertEquals("https://api.spotify.com:443/v1/me/following/contains?type=ARTIST&ids=0LcJLqbBmaGUft1e9Mm8HV%2C0LcJLqbBmaGUft1e9Mm8HV", defaultRequest.getUri().toString());
    }
}
