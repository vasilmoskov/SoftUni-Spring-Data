package exam.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.modelmapper.AbstractConverter;
import org.modelmapper.AbstractProvider;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;

import org.modelmapper.Provider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Configuration
public class ApplicationBeanConfiguration {

    @Bean
    @Primary
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Bean(name = "withLocalDate")
    public ModelMapper modelMapperDate() {
        ModelMapper modelMapper = new ModelMapper();

        Converter<String, LocalDate> toLocalDate = new AbstractConverter<>() {
            @Override
            protected LocalDate convert(String source) {
                return LocalDate.parse(source, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            }
        };

        modelMapper.addConverter(toLocalDate);

        return modelMapper;
    }

    @Bean
    public Gson gson() {
        return new GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                .setPrettyPrinting()
                .create();
    }
}
