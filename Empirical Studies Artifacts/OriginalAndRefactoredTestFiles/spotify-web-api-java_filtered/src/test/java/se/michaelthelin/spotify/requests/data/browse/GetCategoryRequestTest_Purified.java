package se.michaelthelin.spotify.requests.data.browse;

import org.apache.hc.core5.http.ParseException;
import org.junit.jupiter.api.Test;
import se.michaelthelin.spotify.TestUtil;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
import se.michaelthelin.spotify.model_objects.specification.Category;
import se.michaelthelin.spotify.requests.data.AbstractDataTest;
import java.io.IOException;
import java.util.concurrent.ExecutionException;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class GetCategoryRequestTest_Purified extends AbstractDataTest<Category> {

    private final GetCategoryRequest defaultRequest = SPOTIFY_API.getCategory(CATEGORY_ID).setHttpManager(TestUtil.MockedHttpManager.returningJson("requests/data/browse/GetCategoryRequest.json")).country(COUNTRY).locale(LOCALE).build();

    public GetCategoryRequestTest() throws Exception {
    }

    public void shouldReturnDefault(final Category category) {
        assertEquals("https://api.spotify.com/v1/browse/categories/party", category.getHref());
        assertEquals(1, category.getIcons().length);
        assertEquals("party", category.getId());
        assertEquals("Party", category.getName());
    }

    @Test
    public void shouldComplyWithReference_1() {
        assertHasAuthorizationHeader(defaultRequest);
    }

    @Test
    public void shouldComplyWithReference_2() {
        assertEquals("https://api.spotify.com:443/v1/browse/categories/dinner?country=SE&locale=sv_SE", defaultRequest.getUri().toString());
    }
}
