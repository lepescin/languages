package ru.lepescin.languages.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.lepescin.languages.mapper.LanguageMapper;
import ru.lepescin.languages.model.*;
import ru.lepescin.languages.service.LanguageService;

import java.util.List;

@RestController
@RequestMapping("/language")
@Api
public class LanguageController {

    private final LanguageService service;

    private final LanguageMapper mapper = Mappers.getMapper(LanguageMapper.class);

    @Autowired
    public LanguageController(LanguageService languageService) {
        this.service = languageService;
    }

    @PostMapping(value = "")
    @ApiOperation(value = "create")
    public ResponseEntity<ApiLanguageSaveResponse> createLanguage(@RequestBody ApiLanguageCreateRequest request) {
        ApiLanguageSaveResponse apiLanguageSaveResponse = new ApiLanguageSaveResponse();
        ApiLanguage apiLanguage = new ApiLanguage();
        apiLanguage.name(request.getName());
        apiLanguage.description(request.getDescription());
        apiLanguage.rating(request.getRating());
        Language language = mapper.toLanguage(apiLanguage);
        apiLanguageSaveResponse.body(mapper.toApiLanguage(service.create(language)));
        return new ResponseEntity<>(apiLanguageSaveResponse, HttpStatus.CREATED);
    }

    @GetMapping(value = "/{name}")
    @ApiOperation(value = "get")
    public ResponseEntity<ApiLanguageGetResponse> getLanguage(@PathVariable String name) {
        ApiLanguageGetResponse apiLanguageGetResponse = new ApiLanguageGetResponse();
        apiLanguageGetResponse.language(mapper.toApiLanguage(service.getByName(name)));
        return new ResponseEntity<>(apiLanguageGetResponse, HttpStatus.OK);
    }

    @GetMapping(value = "")
    @ApiOperation(value = "getAll")
    public ResponseEntity<?> getAllLanguages() {
        List<Language> languageList = service.getAll();
        List<ApiLanguage> apiLanguageList = languageList.stream().map(mapper::toApiLanguage).toList();
        return new ResponseEntity<>(apiLanguageList, HttpStatus.OK);
    }

    @PutMapping(value = "/{name}")
    @ApiOperation(value = "update")
    public ResponseEntity<ApiLanguageSaveResponse> updateLanguage(@PathVariable String name, @RequestBody ApiLanguageUpdateRequest request) {
        ApiLanguageSaveResponse apiLanguageSaveResponse = new ApiLanguageSaveResponse();
        ApiLanguage apiLanguage = new ApiLanguage();
        apiLanguage.description(request.getDescription());
        apiLanguage.rating(request.getRating());
        Language language = mapper.toLanguage(apiLanguage);
        apiLanguageSaveResponse.body(mapper.toApiLanguage(service.update(name, language)));
        return new ResponseEntity<>(apiLanguageSaveResponse, HttpStatus.OK);
    }

    @DeleteMapping(value = "/{name}")
    @ApiOperation(value = "delete")
    public ResponseEntity<ApiStateResponse> deleteLanguage(@PathVariable String name) {
        service.delete(name);
        return new ResponseEntity<>(new ApiStateResponse(), HttpStatus.NO_CONTENT);
    }
}
