package org.foden.tests;

import io.restassured.response.Response;
import org.foden.api.applicationApi.PlaylistApi;
import org.foden.pojo.Error;
import org.foden.pojo.PlayList;
import org.testng.annotations.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class PlayListTests {

    @Test
    public void createPlayList(){
        PlayList requestPlayList = new PlayList()
                .setName("Foden Like It")
                .setDescription("Top 10 songs that I really like.")
                .setPublic(true);
        Response response = PlaylistApi.post(requestPlayList);
        assertThat(response.statusCode(), equalTo(201));
        PlayList responsePlaylist = response.as(PlayList.class);
        assertThat(responsePlaylist.getName(), equalTo(requestPlayList.getName()));
        assertThat(responsePlaylist.getDescription(), equalTo(requestPlayList.getDescription()));
        assertThat(responsePlaylist.getPublic(), equalTo(requestPlayList.getPublic()));
    }

    @Test
    public void getPlayList(){
        PlayList requestPlayList = new PlayList()
                .setName("Foden Like It")
                .setDescription("Top 10 songs that I really like.")
                .setPublic(true);
        Response response = PlaylistApi.get("5ZCEHwn5JnhpXzDAhnYKah");
        assertThat(response.statusCode(), equalTo(200));
        PlayList responsePlaylist = response.as(PlayList.class);
        assertThat(responsePlaylist.getName(), equalTo(requestPlayList.getName()));
        assertThat(responsePlaylist.getDescription(), equalTo(requestPlayList.getDescription()));
        assertThat(responsePlaylist.getPublic(), equalTo(requestPlayList.getPublic()));

    }

    @Test
    public void updatePlayList(){
        PlayList requestPlayList = new PlayList()
                .setName("Top 5 Europe music songs")
                .setDescription("Top 5 music songs last week in Europe.")
                .setPublic(false);
        Response response = PlaylistApi.update("3pc06TbWbIgUD9tRABoeGR", requestPlayList);
        assertThat(response.statusCode(), equalTo(200));
    }

    @Test
    public void shouldNotBeAbleToCreatePlayListWithoutName(){
        PlayList requestPlayList = new PlayList()
                .setName("")
                .setDescription("Top 5 music songs last week in Europe.")
                .setPublic(false);
        Response response = PlaylistApi.post(requestPlayList);
        assertThat(response.statusCode(), equalTo(400));
        Error error = response.as(Error.class);
//        assertThat(error.getError().getStatus(), equalTo(400));
        assertThat(error.getError().getMessage(), equalTo("Missing required field: name"));
    }

    @Test
    public void shouldNotBeAbleToCreatePlayListWithExpiredToken(){
        PlayList requestPlayList = new PlayList()
                .setName("Foden PlayList")
                .setDescription("Top 5 music songs last week in Europe.")
                .setPublic(false);
        String expiredToken = "1BQDxcdWArcPgYiSjcLud_FIMjc7kK2_3O94q1HdXKEE4aHQpf1nAKSiL0_hOqavh0eAKjhO9-RVGX0hu_CkHzocpaJLL7uxpsXzyDEEUrcdfpfSai9aBaCLSeewIXdRrIbTw_F_wZVb1L7OxUTvqpuqeSZZSVRCQSR3dCU8_aqKunJK1B0wbSwT_4Gu-SY_EoZFGV00-VVlaZsTN_pi6VZaKfKo6h6D80jfwz_WgPV4mYAu_MmKzWlqmowDmeqwPsLPBfT3TOFtMgs7i-R0h7EQU";
        Response response = PlaylistApi.post(expiredToken ,requestPlayList);
        assertThat(response.statusCode(), equalTo(401));
        Error error = response.as(Error.class);
//        assertThat(error.getError().getStatus(), equalTo(401));
        assertThat(error.getError().getMessage(), equalTo("Invalid access token"));
    }
}
