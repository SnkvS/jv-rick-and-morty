package mate.academy.rickandmorty.mapper;

import mate.academy.rickandmorty.dto.RequestCharacterDto;
import mate.academy.rickandmorty.dto.ResponseDto;
import mate.academy.rickandmorty.model.RickCharacter;
import org.springframework.stereotype.Component;

@Component
public class RickCharacterMapper {
    public RickCharacter toModel(RequestCharacterDto dto) {
        var model = new RickCharacter();
        model.setExternalId(dto.getId());
        model.setName(dto.getName());
        model.setGender(dto.getGender());
        model.setStatus(dto.getStatus());
        return model;
    }

    public ResponseDto toResponseDto(RickCharacter rickCharacter) {
        return new ResponseDto(rickCharacter.getId(), rickCharacter.getExternalId(),
                rickCharacter.getName(),
                rickCharacter.getStatus(), rickCharacter.getGender());
    }
}
