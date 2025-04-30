package mate.academy.rickandmorty.service;

import mate.academy.rickandmorty.dto.ResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface RickCharacterService {
    ResponseDto getRandomCharacter();

    Page<ResponseDto> searchByName(String name, Pageable pageable);
}
