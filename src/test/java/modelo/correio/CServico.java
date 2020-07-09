package modelo.correio;

import lombok.Getter;
import lombok.Setter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@Getter
@Setter
@XmlRootElement(name = "cServico", namespace = "http://tempuri.org/")
@XmlAccessorType(XmlAccessType.FIELD)
public class CServico {
    @XmlElement(name = "Codigo", namespace = "http://tempuri.org/")
    private long Codigo;
    @XmlElement(name = "PrazoEntrega", namespace = "http://tempuri.org/")
    private long PrazoEntrega;
    @XmlElement(name = "EntregaDomiciliar", namespace = "http://tempuri.org/")
    private String EntregaDomiciliar;
    @XmlElement(name = "EntregaSabado", namespace = "http://tempuri.org/")
    private String EntregaSabado;
    @XmlElement(name = "Erro", namespace = "http://tempuri.org/")
    private long Erro;
    @XmlElement(name = "MsgErro", namespace = "http://tempuri.org/")
    private String MsgErro;
    @XmlElement(name = "obsFim", namespace = "http://tempuri.org/")
    private String obsFim;
    @XmlElement(name = "DataMaxEntrega", namespace = "http://tempuri.org/")
    private String DataMaxEntrega;
}