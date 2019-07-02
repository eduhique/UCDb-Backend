package psoft.backend.documentacao;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select().apis(RequestHandlerSelectors.basePackage("psoft.backend.controller"))
                .build()
                .apiInfo(metaData());
    }

    private ApiInfo metaData() {
        return new ApiInfoBuilder()
                .title("UCDb: classificações e reviews de cursos da UFCG")
                .description("O UFCG Cursos database é uma aplicação para classificação e reviews de disciplinas de " +
                        "cursos da UFCG. Por enquanto, a versão 1 do sistema será alimentada apenas com disciplinas do " +
                        "curso de Ciência da Computação. Os usuários dessa aplicação irão construir conteúdo sobre as " +
                        "disciplinas de forma colaborativa através de comentários e likes nas disciplinas. O sistema " +
                        "deve usar essa informação construída para rankear as disciplinas do curso." +
                        "Esta API RESTful pode recebe e entrega dados em formato JSON e tenta se aderir ao máximo à" +
                        " arquitetura REST. Nesta página é possivél reconhecer e testar as rotas de acesso ao dados," +
                        "conhecer os parâmetro nescessários e as estruturas de dados retornadas. Além disso esta API" +
                        "tenta ao também ao máximo seguir boas práticas para nomes de rotas e uso de métodos HTTP. Por" +
                        " fim, está API trata de mananeira adequada os erros mais recorrentes que podem surgir.")
                .version("1.0")
                .contact(new Contact("Eduardo Henrique Silva", "", "eduardo.henrique.silva@ccc.ufcg.edu.br"))
                .license("Apache License 2.0")
                .licenseUrl("https://www.apache.org/licenses/LICENSE-2.0")
                .build();
    }
}
