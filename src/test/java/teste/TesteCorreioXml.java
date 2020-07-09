package teste;

import modelo.correio.CServico;
import org.junit.jupiter.api.Test;
import org.xml.sax.SAXException;

import javax.xml.bind.JAXBException;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TesteCorreioXml {

    @Test
    public void validaResponseCEP() throws JAXBException, IOException, ParserConfigurationException, JAXBException, SAXException {

        String soapBody = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:tem=\"http://tempuri.org/\">"
                + "<soapenv:Header/>"
                + "<soapenv:Body>"
                + "<tem:CalcPrazo>"
                + "<tem:nCdServico>10011</tem:nCdServico>"
                + "<tem:sCepOrigem>05873260</tem:sCepOrigem>"
                + "<tem:sCepDestino>05873260</tem:sCepDestino>"
                + "</tem:CalcPrazo>"
                + "</soapenv:Body>"
                + "</soapenv:Envelope>";

        Map<String, String> authhdrs = new HashMap<String, String>();
        authhdrs.put("SOAPAction", "http://tempuri.org/CalcPrazo");


        CServico response =
                given().request().headers(authhdrs)
                        .contentType("text/xml; charset=utf-8;").body(soapBody)
                        .when().post("http://ws.correios.com.br/calculador/CalcPrecoPrazo.asmx")
                        .andReturn()
                        .xmlPath()
                        .getObject(
                                "Envelope.Body.CalcPrazoResponse.CalcPrazoResult.Servicos.cServico", CServico.class);


        assertEquals(response.getCodigo(), 10011);
        assertEquals(response.getPrazoEntrega(), 0);
        assertEquals(response.getEntregaDomiciliar(), "");
        assertEquals(response.getEntregaSabado(), "");
        assertEquals(response.getErro(), 001);
        assertEquals(response.getMsgErro(), "Código de serviço inválido");
        assertEquals(response.getObsFim(), "");
        assertEquals(response.getDataMaxEntrega(), "");
    }
}
