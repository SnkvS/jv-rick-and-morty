package mate.academy.rickandmorty.service.impl;

import jakarta.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import mate.academy.rickandmorty.dto.RequestCharacterDto;
import mate.academy.rickandmorty.dto.ResponseDto;
import mate.academy.rickandmorty.dto.RickRequestDto;
import mate.academy.rickandmorty.mapper.RickCharacterMapper;
import mate.academy.rickandmorty.repository.CharacterRepository;
import mate.academy.rickandmorty.service.RickCharacterService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class RickCharacterServiceImpl implements RickCharacterService {
    private final RickCharacterMapper mapper;
    private final CharacterRepository repository;
    private final Random random;
    private final RestTemplate restTemplate;
    @Value("${base_url}")
    private String targetEndpoint;

    public RickCharacterServiceImpl(RickCharacterMapper mapper, CharacterRepository repository,
            Random random, RestTemplate restTemplate) {
        this.mapper = mapper;
        this.repository = repository;
        this.random = random;
        this.restTemplate = restTemplate;
    }

    @Override
    public ResponseDto getRandomCharacter() {
        var count = (int) repository.count();
        return repository.findAll(PageRequest.of(random.nextInt(count), 1)).stream().findFirst()
                .map(mapper::toResponseDto).orElseThrow(RuntimeException::new);
    }

    @Override
    public Page<ResponseDto> searchByName(String name, Pageable pageable) {
        if (Objects.isNull(name) || name.isBlank()) {
            throw new IllegalArgumentException("Name cannot be empty.");
        }
        return repository.findByNameContainingIgnoreCase(name, pageable).map(mapper::toResponseDto);
    }

    private void loadCharacters() {
        repository.deleteAllInBatch();
        var characters = populateList(restTemplate, targetEndpoint);
        saveExternalCharacters(characters);
    }

    private List<RequestCharacterDto> populateList(RestTemplate restTemplate, String url) {
        List<RequestCharacterDto> list = new ArrayList<>();
        while (url != null) {
            RickRequestDto response = restTemplate.getForObject(url, RickRequestDto.class);
            if (response != null && response.getResults() != null) {
                list.addAll(response.getResults());
                url = response.getInfo() != null ? response.getInfo().getNext() : null;
            } else {
                url = null;
            }
        }
        return list;
    }

    private void saveExternalCharacters(List<RequestCharacterDto> list) {
        repository.saveAllAndFlush(list.stream().parallel().map(mapper::toModel).toList());

    }

    @PostConstruct
    private void init() {
        loadCharacters();
    }
}
