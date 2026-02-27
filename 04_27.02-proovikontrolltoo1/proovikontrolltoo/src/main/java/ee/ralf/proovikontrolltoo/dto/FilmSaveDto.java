package ee.ralf.proovikontrolltoo.dto;

import ee.ralf.proovikontrolltoo.entity.FilmType;

public record FilmSaveDto(
        String title,
        FilmType type
) {
}
