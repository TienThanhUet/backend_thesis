package com.stadio.model.redisUtils;

import com.stadio.model.documents.Artist;

import java.util.List;
import java.util.Map;

public interface ArtistRedisRepository {
    void processPutArtist(Artist artist);

    void processPutAllArtist(List<Artist> artistList);

    void processDeleteArtist(String nconst);

    Map<String, String> processGetAllArtist();

    String processGetArtist(String nconst);
}
