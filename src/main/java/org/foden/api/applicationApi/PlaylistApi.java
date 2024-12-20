package org.foden.api.applicationApi;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import org.foden.api.RestResource;
import org.foden.pojo.Playlist;
import org.foden.utils.ConfigLoader;

import static org.foden.api.Route.*;

public class PlaylistApi {

    @Step
    public static Response post(Playlist requestPlaylist){
        return RestResource.post(USERS + "/" + ConfigLoader.getInstance().getUserId() + PLAYLISTS, requestPlaylist);
    }

    @Step
    public static Response post(String token , Playlist requestPlaylist){
        return RestResource.post(token, USERS + "/" + ConfigLoader.getInstance().getUserId() + PLAYLISTS, requestPlaylist);
    }

    @Step
    public static Response get(String playlistId){
        return RestResource.get(PLAYLISTS + "/" + playlistId);
    }

    @Step
    public static Response update(String playlistId, Playlist requestPlaylist){
        return RestResource.update(PLAYLISTS + "/" + playlistId, requestPlaylist);
    }
}
