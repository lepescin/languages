package ru.lepescin.languages.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.lepescin.languages.model.ApiLanguage;
import ru.lepescin.languages.model.Language;

@Mapper
public interface LanguageMapper {

    @Mapping(source = "name", target = "name")
    @Mapping(source = "description", target = "description")
    @Mapping(source = "rating", target = "rating")
    Language toLanguage(ApiLanguage apiLanguage);

    @Mapping(source = "name", target = "name")
    @Mapping(source = "description", target = "description")
    @Mapping(source = "rating", target = "rating")
    ApiLanguage toApiLanguage(Language language);
}
