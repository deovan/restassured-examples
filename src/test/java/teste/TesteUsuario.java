package teste;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.path.xml.XmlPath;
import modelo.Leilao;
import modelo.Usuario;
import org.junit.Before;
import org.junit.jupiter.api.Test;

import java.util.List;

import static io.restassured.RestAssured.expect;
import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TesteUsuario {

    @Before
    public void setUP() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = 8080;
    }

    @Test
    public void deveRetornarListaDeUsuarios() {
        XmlPath path = given().header("Accept", "application/xml")
                .get("/usuarios").andReturn().xmlPath();
        List<Usuario> usuarios = path.getList("list.usuario", Usuario.class);

        Usuario esperado1 = new Usuario(1L, "Mauricio Aniche", "mauricio.aniche@caelum.com.br");
        Usuario esperado2 = new Usuario(2L, "Guilherme Silveira", "guilherme.silveira@caelum.com.br");

        assertEquals(esperado1, usuarios.get(0));
        assertEquals(esperado2, usuarios.get(1));
    }

    @Test
    public void deveRetornarListaDeUsuariosJson() {
        JsonPath path = given()
                .header("Accept", "application/json")
                .get("/usuarios")
                .andReturn().jsonPath();

        List<Usuario> usuarios = path.getList("list", Usuario.class);
        Usuario esperado1 = new Usuario(1L, "Mauricio Aniche", "mauricio.aniche@caelum.com.br");
        Usuario esperado2 = new Usuario(2L, "Guilherme Silveira", "guilherme.silveira@caelum.com.br");

        assertEquals(esperado1, usuarios.get(0));
        assertEquals(esperado2, usuarios.get(1));

    }

    @Test
    public void deveRetornarUsuarioPeloId() {
        JsonPath path = given()
                .param("usuario.id", 1)
                .header("Accept", "application/json")
                .get("/usuarios/show")
                .andReturn().jsonPath();

        Usuario usuario = path.getObject("usuario", Usuario.class);
        Usuario esperado = new Usuario(1L, "Mauricio Aniche", "mauricio.aniche@caelum.com.br");

        assertEquals(esperado, usuario);

    }

    @Test
    public void deveRetornarLeilaoPeloId() {
        JsonPath path = given()
                .param("leilao.id", 1)
                .header("Accept", "application/json")
                .get("/leiloes/show")
                .andReturn().jsonPath();

        Leilao leilao = path.getObject("leilao", Leilao.class);
        Usuario usuario = new Usuario(1L, "Mauricio Aniche", "mauricio.aniche@caelum.com.br");
        Leilao esperado = new Leilao(1L, "Geladeira", 800.0, usuario, false);

        assertEquals(esperado, leilao);

    }

    @Test
    public void deveRetornarTotalDeLeiloes() {
        XmlPath path = given()
                .param("leilao.id", 1)
                .header("Accept", "application/xml")
                .get("/leiloes/total")
                .andReturn().xmlPath();

        int total = path.getInt("int");
        assertEquals(3, total);
    }

    @Test
    public void deveAdicionarUmUsuario() {
        Usuario joao = new Usuario("Joao da Silva", "joao@dasilva.com");

        XmlPath retorno =
                given()
                        .header("Accept", "application/xml")
                        .contentType("application/xml")
                        .body(joao)
                        .expect()
                        .statusCode(200)
                        .when()
                        .post("/usuarios")
                        .andReturn()
                        .xmlPath();

        Usuario resposta = retorno.getObject("usuario", Usuario.class);

        assertEquals("Joao da Silva", resposta.getNome());
        assertEquals("joao@dasilva.com", resposta.getEmail());

        given()
                .contentType("application/xml").body(resposta)
                .expect().statusCode(200)
                .when().delete("/usuarios/deleta").andReturn().asString();

    }

    @Test
    public void deveAdicionarUmLeilao() {
        Usuario usuario = new Usuario(1L, "Mauricio Aniche", "mauricio.aniche@caelum.com.br");
        Leilao leilao = new Leilao("Geladeira", 800.0, usuario, false);

        XmlPath retorno =
                given()
                        .header("Accept", "application/xml")
                        .contentType("application/xml")
                        .body(leilao)
                        .expect()
                        .statusCode(200)
                        .when()
                        .post("/leiloes")
                        .andReturn()
                        .xmlPath();

        Leilao resposta = retorno.getObject("leilao", Leilao.class);

        assertEquals(leilao.getNome(), resposta.getNome());
        assertEquals(leilao.getValorInicial(), resposta.getValorInicial());
        assertEquals(leilao.isUsado(), resposta.isUsado());
        assertEquals(leilao.getUsuario(), usuario);

        given()
                .contentType("application/xml").body(resposta)
                .expect().statusCode(200)
                .when().delete("/leiloes/deletar").andReturn().asString();

    }

    @Test
    public void deveGerarUmCookie() {
        expect()
                .cookie("rest-assured", "funciona")
                .when()
                .get("/cookie/teste");
    }

    @Test
    public void deveGerarUmHeader() {
        expect()
                .header("novo-header", "abc")
                .when()
                .get("/cookie/teste");
    }

}