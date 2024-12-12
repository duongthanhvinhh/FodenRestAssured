package org.foden.api.applicationApi;

import io.restassured.response.Response;
import org.foden.api.RestResource;
import org.foden.pojo.PlayList;

import static io.restassured.RestAssured.given;
import static org.foden.api.SpecBuilder.getRequestSpec;
import static org.foden.api.SpecBuilder.getResponseSpec;

public class PlaylistApi {

    public static Response post(PlayList requestPlaylist){
        return RestResource.post("/users/31c3hrbpm75afpco7533kex5fhyi/playlists", requestPlaylist);
    }

    public static Response post(String token ,PlayList requestPlaylist){
        return RestResource.post(token, "/users/31c3hrbpm75afpco7533kex5fhyi/playlists", requestPlaylist);
    }

    public static Response get(String playlistId){
        return RestResource.get("/playlists/" + playlistId);
    }

    public static Response update(String playlistId, PlayList requestPlaylist){
        return RestResource.update("/playlists/" + playlistId, requestPlaylist);
    }
}
