package ru.lepescin.languages.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.lepescin.languages.exception.NotFoundException;
import ru.lepescin.languages.model.Language;
import ru.lepescin.languages.repository.LanguageRepository;

import java.util.List;

@Service
public class LanguageService {

    private final LanguageRepository languageRepository;

    @Autowired
    public LanguageService(LanguageRepository languageRepository) {
        this.languageRepository = languageRepository;
    }

    public Language create(Language language) {
        return languageRepository.save(language);
    }

    public Language getByName(String name) {
        return languageRepository.findByName(name).orElseThrow(() -> new NotFoundException(createNotFoundByNameMessage(name)));
    }

    public List<Language> getAll() {
        return languageRepository.findAll();
    }

    public Language update(String name, Language languageToUpdate) {
        Language language = languageRepository.findByName(name).orElseThrow(() -> new NotFoundException(createNotFoundByNameMessage(name)));
        language.setName(name);
        language.setDescription(languageToUpdate.getDescription());
        return languageRepository.save(language);
    }

    public void delete(String name) {
        Language language = languageRepository.findByName(name).orElseThrow(() -> new NotFoundException(createNotFoundByNameMessage(name)));
        languageRepository.deleteById(language.getId());
    }

    private String createNotFoundByNameMessage(String name) {
        return String.format("Language with name %s is not found", name);
    }

}
