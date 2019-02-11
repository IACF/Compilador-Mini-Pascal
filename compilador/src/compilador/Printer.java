/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package compilador;

/**
 *
 * @author italo
 */
public class Printer implements Visitor {

    public void print (Programa P) {
        P.visit(this);
    }
    @Override
    public void visitorPrograma(Programa arg0) {
        if (arg0 != null) {
            arg0.I.visit(this);
            arg0.C.visit(this);
        } else {
            //errors
        }
    }

    @Override
    public void visitorDeclaracaoDeVariavel(declaracaoDeVariavel arg0) {
        arg0.I.visit(this);
        arg0.T.visit(this);
    }
    
    @Override
    public void visitorCorpo(Corpo arg0) {
        arg0.D.visit(this);
    }

    @Override
    public void visitorDeclaracaoSequencial(declaracaoSequencial arg0) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void visitorExpressaoBinaria(expressaoBinaria arg0) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void visitorExpressaoSequencial(expressaoSequencial arg0) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void visitorExpressaoUnaria(expressaoUnaria arg0) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void visitorIdentificadorSequencial(identificadorSequencial arg0) {
        arg0.I2.visit(this);
        arg0.I1.visit(this);
    }

    @Override
    public void visitorIdentificadorSimples(identificadorSimples arg0) {
        if (arg0 != null) {
            arg0.TK.visit(this);
        }
    }

    @Override
    public void visitorTipoAgregado(tipoAgregado arg0) {
        arg0.L2.visit(this);
        arg0.L1.visit(this);
        arg0.T.visit(this);
        
    }

    @Override
    public void visitorTipoSimples(tipoSimples arg0) {
        if (arg0 != null) {
            arg0.TK.visit(this);
        }
    }

    @Override
    public void visitordeclaracaoSequencial(comandoIterativo arg0) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void visitorComandoIterativo(comandoIterativo arg0) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void visitorComandoCondicional(comandoCondicional arg0) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void visitorComandoComposto(comandoComposto arg0) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void visitorComandoAtribuicao(comandoAtribuicao arg0) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void visitorVariavel(Variavel arg0) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void visitorLiteral(Literal arg0) {
        if (arg0 != null) {
            arg0.TK.visit(this);
        }
    }

    @Override
    public void visitorToken(Token t) {
        System.out.println(t.spelling + "\n");
    }
    
}
