/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package compilador;

/**
 *
 * @author victor
 */
public interface Visitor {
    
    public void visitorToken(Token t);
    
    public void visitorPrograma(Programa p);
    
    public void visitorDeclaracaoDeVariavel(declaracaoDeVariavel d);
    
    public void visitorDeclaracaoSequencial(declaracaoSequencial d);
    
    public void visitorExpressaoBinaria(expressaoBinaria e);
    
    public void visitorExpressaoSequencial(expressaoSequencial e);
    
    public void visitorIdentificadorSequencial(identificadorSequencial i);
    
    public void visitorIdentificadorSimples(identificadorSimples i);

    public void visitorTipoAgregado(tipoAgregado t);

    public void visitorTipoSimples(tipoSimples t);

    public void visitorComandoIterativo(comandoIterativo c);

    public void visitorComandoCondicional(comandoCondicional c);

    public void visitorComandoComposto(comandoComposto c);

    public void visitorComandoAtribuicao(comandoAtribuicao c);

    public void visitorVariavel(Variavel arg0);

    public void visitorLiteral(Literal l);

    public void visitorCorpo(Corpo Cp);

    public void visitorOperador(Operador O);
    
}
