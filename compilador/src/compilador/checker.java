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
public class checker implements Visitor{
    
    identificationTable table;
    
    public void checker (Programa P) {
        this.table = new identificationTable();
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

    public boolean identificaSequencia(Identificador i, Tipo t){
        identificadorSequencial gambirra;
        
        if(i instanceof  identificadorSimples){
            table.insert((identificadorSimples) i, t);
            return true;
        }
        
        gambirra = (identificadorSequencial) i;
        table.insert((identificadorSimples) gambirra.I1, t);
        
        return identificaSequencia(gambirra.I2, t);
    }
    
    @Override
    public void visitorDeclaracaoDeVariavel(declaracaoDeVariavel arg0) {
        if(arg0 != null){
            if(arg0.I instanceof  identificadorSimples){
                table.insert((identificadorSimples) arg0.I, arg0.T);
            }else{
                identificaSequencia(arg0.I, arg0.T);
            }
            
            arg0.I.visit(this);
            arg0.T.visit(this);
        }
    }
    
    @Override
    public void visitorCorpo(Corpo arg0) {
        if(arg0 != null){
            arg0.D.visit(this);
            arg0.C.visit(this);
        }
    }

    @Override
    public void visitorDeclaracaoSequencial(declaracaoSequencial arg0) {
        if(arg0 != null){
            arg0.D1.visit(this);
            arg0.D2.visit(this);
        }
    }

    @Override
    public void visitorExpressaoBinaria(expressaoBinaria arg0) {
        if(arg0 != null){
            
            arg0.E1.visit(this);
            arg0.O.visit(this);
//            if(arg0.O.TK.spelling.equals("+")) {
//                System.out.println("operação de adição");
//                
//            }

            
            arg0.E2.visit(this);
            if(arg0.E1 instanceof Variavel) {
               Variavel gambirraT = (Variavel) arg0.E1;
                arg0.tipo = tipagemExpressao(gambirraT.tipo, arg0.O, arg0.E2.tipo);
                System.out.println("Tipo varial:" + arg0.tipo);
            }
            
            if(arg0.E1 instanceof Literal) {
               Literal gambirraT = (Literal) arg0.E1;
                arg0.tipo = tipagemExpressao(gambirraT.tipo, arg0.O, arg0.E2.tipo);
                System.out.println("Tipo literal:" + arg0.tipo);
            }
        }
    }

    @Override
    public void visitorExpressaoSequencial(expressaoSequencial arg0) {
        if(arg0 != null){
            
            arg0.E1.visit(this);
            arg0.E2.visit(this);
            
            
        }
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
   
        arg0.L1.visit(this);
        System.out.println("tipoAgregado:" + arg0.L1.tipo);
        
        arg0.L2.visit(this);
        System.out.println("tipoAgregado:" + arg0.L2.tipo);
        arg0.T.visit(this);
  

    }

    @Override
    public void visitorTipoSimples(tipoSimples arg0) {
        if (arg0 != null) {
            arg0.TK.visit(this);      
        }
    }

  
    @Override
    public void visitorComandoIterativo(comandoIterativo arg0) {
        if (arg0 != null) {    
            arg0.E.visit(this);
//            if (arg0.E.tipo.equals("boolean")) {
//                System.out.println("Tipo while: " + arg0.E.tipo);
                arg0.C.visit(this);
//            } else {
//                System.out.println("ERRO DE WHILE");
//            }
        }
    }

    @Override
    public void visitorComandoCondicional(comandoCondicional arg0) {
        if (arg0 != null) {
            arg0.E.visit(this);
            if("boolean".equals(arg0.E.tipo)) {
                arg0.C1.visit(this);
                arg0.C2.visit(this);
            } else {
                System.out.println("errooooo");
            }
                
            
  
        }
    }

    @Override
    public void visitorComandoComposto(comandoComposto arg0) {
        if (arg0 != null) {
           
            arg0.C1.visit(this);
            if (arg0.C2 != null) 
                arg0.C2.visit(this);
            
        }
    }

    @Override
    public void visitorComandoAtribuicao(comandoAtribuicao arg0) {
        if (arg0 != null) {

            arg0.V.visit(this);
            arg0.E.visit(this);
            
            if (!arg0.E.tipo.equals(arg0.V.tipo))
                System.out.println("errado2");
            
            
            
        }
    }

    @Override
    public void visitorVariavel(Variavel arg0) {
        if (arg0 != null) {
            tipoSimples  tSimples;
            
            arg0.I.visit(this);
            
            identificadorSimples I = (identificadorSimples) arg0.I;
            
            identificationTableElement element = table.retrieve(I.TK.spelling);
            if(element == null)
                throw new Error(I);
            
            arg0.setPonteiroDeclaracao(element);
            
            if(element.tipo instanceof tipoSimples){
                tSimples = (tipoSimples) element.tipo;
                arg0.tipo = tSimples.TK.spelling;
            }
            
            if (arg0.E != null)
                arg0.E.visit(this);

        }
    }

    @Override
    public void visitorLiteral(Literal arg0) {
        if (arg0 != null) {
            arg0.TK.visit(this);
        }
    }

    @Override
    public void visitorToken(Token t) {
          
        System.out.println(" { " + t.spelling + " } ");
        
    }

    @Override
    public void visitorOperador(Operador arg0) {
        
       
        arg0.TK.visit(this);
        
        

    }
    
    public String tipagemExpressao(String tipoE1,Operador Operador, String tipoE2) {
        
        
        if (Operador.TK.kind >= 19 && Operador.TK.kind <= 23) {
            if(tipoE1.equals("integer") && tipoE2.equals("integer"))
                return "integer";
            if(tipoE1.equals("real") && tipoE2.equals("real"))
                return "real";
            if(tipoE1.equals("boolean") && tipoE2.equals("boolean"))
                return "erro";
            if(
                (tipoE1.equals("real") && tipoE2.equals("integer")) || 
                (tipoE1.equals("integer") && tipoE2.equals("real"))
            )
                return "real";
            if(
                (tipoE1.equals("boolean") && tipoE2.equals("integer")) || 
                (tipoE1.equals("integer") && tipoE2.equals("boolean")) ||
                (tipoE1.equals("real") && tipoE2.equals("boolean")) ||
                (tipoE1.equals("boolean") && tipoE2.equals("real"))
            )
                return "erro";
        }
        
        if( Operador.TK.kind >= 25 && Operador.TK.kind <= 29) {
            if(
                (tipoE1.equals("boolean") && tipoE2.equals("integer")) || 
                (tipoE1.equals("integer") && tipoE2.equals("boolean")) ||
                (tipoE1.equals("real") && tipoE2.equals("boolean")) ||
                (tipoE1.equals("boolean") && tipoE2.equals("real"))
            )
                return "erro";
            return "boolean";
        }
        
        return "ok";
    }
}
