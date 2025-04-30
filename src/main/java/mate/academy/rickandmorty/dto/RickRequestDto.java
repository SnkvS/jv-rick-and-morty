package mate.academy.rickandmorty.dto;

import java.util.List;
import lombok.Data;

@Data
public class RickRequestDto {
    private Info info;
    private List<RequestCharacterDto> results;

    @Data
    public static class Info {
        private long count;
        private long pages;
        private String next;
        private String prev;
    }
}


