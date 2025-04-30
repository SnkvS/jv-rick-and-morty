package mate.academy.rickandmorty.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import mate.academy.rickandmorty.dto.ResponseDto;
import mate.academy.rickandmorty.service.RickCharacterService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/characters")
public class CharacterController {
    private final RickCharacterService service;

    public CharacterController(RickCharacterService service) {
        this.service = service;
    }

    @GetMapping("/get_random")
    @Tag(name = "/get_random", description = "Retrieves random character from the Rick`s "
            + "Multiverse.")
    public ResponseEntity<ResponseDto> getRandomCharacter() {
        var response = service.getRandomCharacter();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/search")
    @Tag(name = "/search", description = "Retrieves all characters whose name contains search "
            + "string.")
    public ResponseEntity<Page<ResponseDto>> findByName(@RequestParam String name,
            Pageable pageable) {
        var response = service.searchByName(name, pageable);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
