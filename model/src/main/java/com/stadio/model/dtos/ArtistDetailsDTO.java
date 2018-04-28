package com.stadio.model.dtos;

import com.stadio.model.documents.Artist;
import lombok.Data;

@Data
public class ArtistDetailsDTO {
    private String id;

    private String nconst;

    private String primaryName;

    private String image;

    private String birthYear;

    private String deathYear;

    private String primaryProfession;

    private String knownForTitles;

    private String description;

    public static ArtistDetailsDTO newInstance(Artist artist) {
        ArtistDetailsDTO artistDetailsDTO = new ArtistDetailsDTO();
        artistDetailsDTO.setId(artist.getId());
        artistDetailsDTO.setNconst(artist.getNconst());
        artistDetailsDTO.setPrimaryName(artist.getPrimaryName());
        artistDetailsDTO.setImage(artist.getImage());
        artistDetailsDTO.setBirthYear(artist.getBirthYear());
        artistDetailsDTO.setDeathYear(artist.getDeathYear());
        artistDetailsDTO.setPrimaryProfession(artist.getPrimaryProfession());
        artistDetailsDTO.setKnownForTitles(artist.getKnownForTitles());
        artistDetailsDTO.setDescription(artist.getDescription());
        return artistDetailsDTO;
    }
}
