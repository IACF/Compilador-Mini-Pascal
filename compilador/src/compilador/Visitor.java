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
    
    public void visitorPrograma(Programa p);
    
    public void visitorDeclaracaoDeVariavel(declaracaoDeVariavel d);
    
    public void visitorDeclaracaoSequencial(declaracaoSequencial d);
    
    public void visitorExpressaoBinaria(expressaoBinaria e);
    
    public void visitorExpressaoSequencial(expressaoSequencial e);
    
    public void visitorExpressaoUnaria(expressaoUnaria e);
    
    public void visitorIdSequencial(idSequencial i);
    
    public void visitorIdentificadorSimples(identificadorSimples i);

    public void visitorTipoAgregado(tipoAgregado t);

    public void visitorTipoSimples(tipoSimples t);

    public void visitordeclaracaoSequencial(comandoIterativo c);

    public void visitorComandoIterativo(comandoIterativo c);

    public void visitorComandoCondicional(comandoCondicional c);

    public void visitorComandoComposto(comandoComposto c);

    public void visitorComandoAtribuicao(comandoAtribuicao c);

    public void visitorVariavel(Variavel v);

    public void visitorLiteral(Literal l);
    
}
