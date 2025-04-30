package mate.academy.rickandmorty.dto;

import java.util.List;
import lombok.Data;

@Data
public class RequestCharacterDto {
    private long id;
    private String name;
    private String status;
    private String species;
    private String type;
    private String gender;
    private String image;
    private List<String> episode;
    private String url;
    private String created;
    private Origin origin;
    private Location location;

    @Data
    static class Origin {
        private String name;
        private String url;
    }

    @Data
    static class Location {
        private String name;
        private String url;
    }
}
