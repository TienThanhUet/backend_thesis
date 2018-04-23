package com.stadio.model.redisUtils.impl;

import com.stadio.common.utils.JsonUtils;
import com.stadio.model.documents.Artist;
import com.stadio.model.dtos.ArtistDetailsDTO;
import com.stadio.model.redisUtils.ArtistRedisRepository;
import com.stadio.model.redisUtils.RedisConst;
import com.stadio.model.redisUtils.RedisRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class ArtistRedisRepositoryImpl implements ArtistRedisRepository {
    @Autowired
    RedisRepository redisRepository;

    @Override
    public void processPutArtist(Artist artist) {
        redisRepository.select(RedisConst.DB_ARTIST);
        String key = RedisConst.ARTIST_DETAILS;
        redisRepository.hput(key, artist.getNconst(), JsonUtils.pretty(ArtistDetailsDTO.newInstance(artist)));
        redisRepository.expire(key,RedisConst.TIME_TO_LIVE_LONG);
    }

    @Override
    public void processPutAllArtist(List<Artist> artistList) {
        redisRepository.select(RedisConst.DB_ARTIST);
        String key = RedisConst.ARTIST_DETAILS;
        artistList.forEach(artist -> {
            redisRepository.hput(key, artist.getNconst(), JsonUtils.pretty(ArtistDetailsDTO.newInstance(artist)));
        });
        redisRepository.expire(key,RedisConst.TIME_TO_LIVE_LONG);
    }

    @Override
    public void processDeleteArtist(String nconst) {
        redisRepository.select(RedisConst.DB_ARTIST);
        redisRepository.hdelete(RedisConst.ARTIST_DETAILS, nconst);
    }

    @Override
    public Map<String, String> processGetAllArtist() {
        redisRepository.select(RedisConst.DB_ARTIST);
        Map<String, String> artistMap = redisRepository.hgetAll(RedisConst.ARTIST_DETAILS);
        return artistMap;
    }

    @Override
    public String processGetArtist(String nconst) {
        redisRepository.select(RedisConst.DB_ARTIST);
        String artistStr = redisRepository.hget(RedisConst.ARTIST_DETAILS, nconst);
        return artistStr;
    }
}
