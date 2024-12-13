package org.foden.api.applicationApi;

import io.restassured.response.Response;
import org.foden.api.RestResource;
import org.foden.pojo.PlayList;
import org.foden.utils.ConfigLoader;

import static org.foden.api.Route.*;

public class PlaylistApi {

    public static Response post(PlayList requestPlaylist){
        return RestResource.post(USERS + "/" + ConfigLoader.getInstance().getUserId() + PLAYLISTS, requestPlaylist);
    }

    public static Response post(String token ,PlayList requestPlaylist){
        return RestResource.post(token, USERS + "/" + ConfigLoader.getInstance().getUserId() + PLAYLISTS, requestPlaylist);
    }

    public static Response get(String playlistId){
        return RestResource.get(PLAYLISTS + "/" + playlistId);
    }

    public static Response update(String playlistId, PlayList requestPlaylist){
        return RestResource.update(PLAYLISTS + "/" + playlistId, requestPlaylist);
    }
}
