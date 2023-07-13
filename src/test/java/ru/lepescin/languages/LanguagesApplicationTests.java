package ru.lepescin.languages;

import org.junit.Rule;
import org.junit.jupiter.api.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import ru.lepescin.languages.exception.NotFoundException;
import ru.lepescin.languages.model.Language;
import ru.lepescin.languages.repository.LanguageRepository;
import ru.lepescin.languages.service.LanguageService;

import javax.validation.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
@ActiveProfiles("test")
class LanguagesApplicationTests {

	@Test
	void contextLoads() {
	}

	@Mock
	private LanguageRepository languageRepository;

	@InjectMocks
	private LanguageService languageService;

	@Rule
	public ExpectedException thrown = ExpectedException.none();

	private final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

	@Test
	public void create_ReturnLanguage() {
		Language language = new Language();
		language.setName("Java");
		language.setDescription("Java is the best language!");
		language.setRating(5);
		when(languageRepository.save(ArgumentMatchers.any(Language.class))).thenReturn(language);
		Language created = languageService.create(language);
		assertThat(created.getName()).isSameAs(language.getName());
		verify(languageRepository).save(language);
	}

	@Test
	public void create_ifNotAllowedLanguageName_ConstraintViolationExist() {
		Language language = new Language();
		language.setName("Not allowed language name");
		language.setDescription("Java is the best language!");
		language.setRating(5);
		Set<ConstraintViolation<Language>> violations = validator.validate(language);
		assertFalse(violations.isEmpty());
	}

	@Test
	public void create_ifNotAllowedRating_ConstraintViolationExist() {
		Language language = new Language();
		language.setName("Java");
		language.setDescription("Java is the best language!");
		language.setRating(6);
		Set<ConstraintViolation<Language>> violations = validator.validate(language);
		assertFalse(violations.isEmpty());
	}

	@Test
	public void getByName_ifFound_ReturnLanguage() {
		Language language = new Language();
		language.setName("Java");
		when(languageRepository.findByName(language.getName())).thenReturn(Optional.of(language));
		Language expected = languageService.getByName(language.getName());
		assertThat(expected).isSameAs(language);
		verify(languageRepository).findByName(language.getName());
	}

	@Test
	public void getByName_ifNotFound_ThrowNotFoundException() {
		Language language = new Language();
		language.setName("Java");
		given(languageRepository.findByName(anyString())).willReturn(Optional.empty());
		assertThatExceptionOfType(NotFoundException.class)
				.isThrownBy(() -> languageService.getByName(language.getName()))
				.withMessage("Language with name Java is not found");
	}

	@Test
	public void getAll_ReturnAllLanguages() {
		List<Language> languages = new ArrayList<>();
		languages.add(new Language());
		given(languageRepository.findAll()).willReturn(languages);
		List<Language> expected = languageService.getAll();
		assertEquals(expected, languages);
		verify(languageRepository).findAll();
	}

	@Test
	public void update_ifFound_shouldUpdateLanguage() {
		Language language = new Language();
		language.setId(1L);
		language.setName("Java");
		language.setDescription("Java is the best language");
		language.setRating(5);
		Language updatedLanguage = new Language();
		updatedLanguage.setId(1L);
		updatedLanguage.setName("Java");
		updatedLanguage.setDescription("New description");
		updatedLanguage.setRating(4);
		given(languageRepository.findByName(language.getName())).willReturn(Optional.of(language));
		languageService.update(language.getName(), updatedLanguage);
		verify(languageRepository).save(updatedLanguage);
		verify(languageRepository).findByName(language.getName());
	}

	@Test
	public void delete_ifFound_Deleted(){
		Language language = new Language();
		language.setId(1L);
		language.setName("Java");
		language.setDescription("Java is the best language");
		language.setRating(5);
		when(languageRepository.findByName(language.getName())).thenReturn(Optional.of(language));
		languageService.delete(language.getName());
		verify(languageRepository).deleteById(language.getId());
	}

	@Test
	public void delete_ifNotFound_ThrowNotFoundException() {
		Language language = new Language();
		language.setId(1L);
		language.setName("Java");
		language.setDescription("Java is the best language");
		language.setRating(5);
		given(languageRepository.findByName(anyString())).willReturn(Optional.empty());
		assertThatExceptionOfType(NotFoundException.class)
				.isThrownBy(() -> languageService.delete(language.getName()))
				.withMessage("Language with name Java is not found");
	}

}
