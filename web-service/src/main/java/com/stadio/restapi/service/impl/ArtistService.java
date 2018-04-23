package com.stadio.restapi.service.impl;

import com.stadio.common.utils.JsonUtils;
import com.stadio.model.documents.Artist;
import com.stadio.model.dtos.ArtistDetailsDTO;
import com.stadio.model.redisUtils.ArtistRedisRepository;
import com.stadio.model.repository.ArtistRepository;
import com.stadio.restapi.response.ResponseResult;
import com.stadio.restapi.service.IArtistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArtistService extends BaseService implements IArtistService {

    @Autowired
    ArtistRepository artistRepository;

    @Autowired
    ArtistRedisRepository artistRedisRepository;

    @Override
    public ResponseResult ProcessGetDetails(String nconst) {
        String artistStr = artistRedisRepository.processGetArtist(nconst);
        if(!artistStr.isEmpty()){
            ArtistDetailsDTO artistDetailsDTO = JsonUtils.parse(artistStr,ArtistDetailsDTO.class);
            return ResponseResult.newSuccessInstance(artistDetailsDTO);
        }else{
            List<Artist> artistList = artistRepository.findByNconst(nconst);
            if(!artistList.isEmpty()){
                Artist artist =artistList.get(0);
                artistRedisRepository.processPutArtist(artist);
                ArtistDetailsDTO artistDetailsDTO = ArtistDetailsDTO.newInstance(artist);
                return ResponseResult.newSuccessInstance(artistDetailsDTO);
            }else{
                return ResponseResult.newErrorInstance("400","not found");
            }
        }
    }
}
