package pragma.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

/**
 * No necesitas tocar esta clase.
 * Registra automáticamente como bean cualquier clase de pragma.usecase
 * cuyo nombre termine en "UseCase" (convención del scaffold de Bancolombia).
 */
@Configuration
@ComponentScan(basePackages = "pragma.usecase",
        includeFilters = {
                @ComponentScan.Filter(type = FilterType.REGEX, pattern = "^.+UseCase$")
        },
        useDefaultFilters = false)
public class UseCasesConfig {
}
