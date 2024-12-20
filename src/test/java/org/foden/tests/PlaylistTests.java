package org.foden.tests;

import io.qameta.allure.*;
import io.restassured.response.Response;
import org.foden.api.applicationApi.PlaylistApi;
import org.foden.pojo.Error;
import org.foden.pojo.Playlist;
import org.foden.utils.DataLoader;
import org.testng.annotations.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class PlaylistTests {


    @Feature("Play List")
    @Epic("SA-7 Manage Playlists Of Users")
    @Test(description = "Should be able to create a playlist")
    @Story("SA-6")
    @TmsLinks({@TmsLink("FMS-202"), @TmsLink("FMS-204")})
    @Description("Verify can create a playlist successfully via api request")
    @Owner("Foden Duong")
    public void createPlayList(){
        Playlist requestPlaylist = playlistBuilder("Foden Like It", "Top 10 songs that I really like.", true);
        Response response = PlaylistApi.post(requestPlaylist);
        assertStatusCode(response.statusCode(), 201);
        assertPlaylistEqual(response.as(Playlist.class), requestPlaylist);
    }

    @Feature("Play List")
    @Epic("SA-7 Manage Playlists Of Users")
    @Test(description = "Should not be able to create a playlist without name")
    @Story("SA-6")
    @TmsLinks({@TmsLink("FMS-202"), @TmsLink("FMS-204")})
    @Description("Verify should not be able to create a playlist without name")
    @Owner("Foden Duong")
    public void shouldNotBeAbleToCreatePlayListWithoutName(){
        Playlist requestPlaylist = playlistBuilder("", "Top 5 music songs last week in Europe", false);
        Response response = PlaylistApi.post(requestPlaylist);
        assertStatusCode(response.statusCode(), 400);
        assertError(response.as(Error.class), 400, "Missing required field: name");
    }

    @Feature("Play List")
    @Epic("SA-7 Manage Playlists Of Users")
    @Test(description = "Should not be able to create a playlist with expired token")
    @Story("SA-6")
    @TmsLinks({@TmsLink("FMS-202"), @TmsLink("FMS-204")})
    @Description("Verify should not be able to create a playlist with expired token")
    @Owner("Foden Duong")
    public void shouldNotBeAbleToCreatePlayListWithExpiredToken(){
        Playlist requestPlaylist = playlistBuilder("Foden Playlists", "Top 5 music songs last week in Europe.", false);
        String expiredToken = "1BQDxcdWArcPgYiSjcLud_FIMjc7kK2_3O94q1HdXKEE4aHQpf1nAKSiL0_hOqavh0eAKjhO9-RVGX0hu_CkHzocpaJLL7uxpsXzyDEEUrcdfpfSai9aBaCLSeewIXdRrIbTw_F_wZVb1L7OxUTvqpuqeSZZSVRCQSR3dCU8_aqKunJK1B0wbSwT_4Gu-SY_EoZFGV00-VVlaZsTN_pi6VZaKfKo6h6D80jfwz_WgPV4mYAu_MmKzWlqmowDmeqwPsLPBfT3TOFtMgs7i-R0h7EQU";
        Response response = PlaylistApi.post(expiredToken ,requestPlaylist);
        assertStatusCode(response.statusCode(), 401);
        assertError(response.as(Error.class), 401, "Invalid access token");
    }

    @Feature("Play List")
    @Epic("SA-7 Manage Playlists Of Users")
    @Test(description = "Should be able to get a playlist via api request")
    @Story("SA-9")
    @TmsLinks({@TmsLink("FMS-202"), @TmsLink("FMS-204")})
    @Description("Verify should be able to get a playlist successfully via api request")
    @Owner("Foden Duong")
    public void getPlayList(){
        Playlist requestPlaylist = playlistBuilder("Foden Like It", "Top 10 songs that I really like.", true);
        Response response = PlaylistApi.get(DataLoader.getInstance().getGetPlaylistId());
        assertStatusCode(response.statusCode(), 200);
        assertPlaylistEqual(response.as(Playlist.class), requestPlaylist);

    }

    @Feature("Play List")
    @Epic("SA-7 Manage Playlists Of Users")
    @Test(description = "Should be able to update a playlist via api request")
    @Story("SA-11")
    @TmsLinks({@TmsLink("FMS-202"), @TmsLink("FMS-204")})
    @Description("Verify should be able to update a playlist successfully via api request")
    @Owner("Foden Duong")
    public void updatePlayList(){
        Playlist requestPlaylist = playlistBuilder("Top 5 Europe music songs", "Top 5 music songs last week in Europe.", false);
        Response response = PlaylistApi.update(DataLoader.getInstance().getUpdatePlaylistId(), requestPlaylist);
        assertStatusCode(response.statusCode(), 200);
    }

    @Step
    public Playlist playlistBuilder(String name, String description, boolean _public){
        return Playlist.builder()
                .name(name)
                .description(description)
                ._public(_public)
                .build();
    }

    @Step
    public void assertPlaylistEqual(Playlist responsePlaylist, Playlist requestPlaylist){
        assertThat(responsePlaylist.getName(), equalTo(requestPlaylist.getName()));
        assertThat(responsePlaylist.getDescription(), equalTo(requestPlaylist.getDescription()));
        assertThat(responsePlaylist.get_public(), equalTo(requestPlaylist.get_public()));
    }

    @Step
    public void assertStatusCode(int actualStatusCode, int expectedStatusCode){
        assertThat(actualStatusCode, equalTo(expectedStatusCode));
    }

    public void assertError(Error responseErr, int expectedStatusCode, String expectedMessage){
        assertThat(responseErr.getError().getMessage(), equalTo(expectedMessage));
        assertThat(responseErr.getError().getStatus(), equalTo(expectedStatusCode));
    }
}
