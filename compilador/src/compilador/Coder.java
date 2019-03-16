/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package compilador;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author victor
 */
public class Coder implements Visitor {
    BufferedWriter buffWrite;
    Map<String, Byte> tamanhoTipos;
    int countIds;

    public Coder() throws IOException {
        this.buffWrite = new BufferedWriter(new FileWriter("assembly.asm"));
        
         this.tamanhoTipos = new HashMap<>()
        {{
            put("boolean",(byte) 1);
            put("integer",(byte) 4);
            put("real", (byte) 8);
        }};
         this.countIds = 0;
    }
    
    public void escrever(String texto) throws IOException{     
        buffWrite.append(texto + "\n");
    }
    
    public void fecharArquivo() throws IOException{ 
        try {
        buffWrite.close();
        } catch (IOException ex) {
            Logger.getLogger(Coder.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Override
    public void visitorToken(Token t) {
    }

    @Override
    public void visitorPrograma(Programa p) {
        p.I.visit(this);
        p.C.visit(this);
        
        try {
            escrever("HALT");
        } catch (IOException ex) {
            Logger.getLogger(Coder.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        try {
            this.fecharArquivo();
        } catch (IOException ex) {
            Logger.getLogger(Coder.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void visitorDeclaracaoDeVariavel(declaracaoDeVariavel arg0) {
        if(arg0 != null){
           tipoSimples tp;
           this.countIds = 0;
           
           arg0.I.visit(this);
            System.out.println(this.countIds);
            
            if(arg0.T instanceof tipoSimples){
                for (int i = 0; i < this.countIds; i++) {
                    tp = (tipoSimples) arg0.T;
                    try {
                        escrever("PUSH "+ this.tamanhoTipos.get(tp.TK.spelling));
                    } catch (IOException ex) {
                        Logger.getLogger(Coder.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }  
            }else{
                int tamanho = 0;
                tipoAgregado t;
                Tipo aux = arg0.T;

                while(aux instanceof tipoAgregado){
                    t = (tipoAgregado) aux;
                    tamanho += (Integer.parseInt(t.L2.TK.spelling) - Integer.parseInt(t.L1.TK.spelling) + 1);
                    aux = t.T;
                }
                
                tp = (tipoSimples) aux;
                tamanho *= this.tamanhoTipos.get(tp.TK.spelling);
                
                for (int i = 0; i < this.countIds; i++) {
                    try {
                        escrever("PUSH "+ tamanho);
                    } catch (IOException ex) {
                        Logger.getLogger(Coder.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }  
       
                
            }
          
        }
    }

    @Override
    public void visitorDeclaracaoSequencial(declaracaoSequencial d) {
        if(d != null){
            d.D1.visit(this);
            d.D2.visit(this);
        }
    }

    @Override
    public void visitorExpressaoBinaria(expressaoBinaria e) {
         if(e != null){
            e.E1.visit(this);
            e.E2.visit(this);
            e.O.visit(this);
         }
    }

    @Override
    public void visitorExpressaoSequencial(expressaoSequencial e) {
        if(e != null){
            e.E1.visit(this);
            e.E2.visit(this);
        }
    }

    @Override
    public void visitorIdentificadorSequencial(identificadorSequencial i) {
        if(i != null){
            i.I1.visit(this);
            i.I2.visit(this);
        }
    }

    @Override
    public void visitorIdentificadorSimples(identificadorSimples i) {
        if(i != null){
            this.countIds++;
            i.TK.visit(this);
        }
    }

    @Override
    public void visitorTipoAgregado(tipoAgregado t) {
        t.L1.visit(this);
        t.L2.visit(this);
        t.T.visit(this);
    }

    @Override
    public void visitorTipoSimples(tipoSimples t) {
        if (t != null) {
            t.TK.visit(this);      
        }
    }

    @Override
    public void visitorComandoIterativo(comandoIterativo c) {
         if (c != null) {    
            c.E.visit(this);
            c.C.visit(this);
        }
    }

    @Override
    public void visitorComandoCondicional(comandoCondicional arg0) {
        if (arg0 != null) {
            arg0.E.visit(this);
            arg0.C1.visit(this);
            arg0.C2.visit(this);
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
          }
    }

    @Override
    public void visitorVariavel(Variavel arg0) {
        if (arg0 != null) {
            identificadorSimples i = (identificadorSimples) arg0.I;
              System.out.println(i.TK.spelling+ " = " + arg0.endereco);
            arg0.I.visit(this);
             
            if (arg0.E != null)
                arg0.E.visit(this);
        }
    }

    @Override
    public void visitorLiteral(Literal arg0) {
        if (arg0 != null) {
            try {
                escrever("LOADL "+ arg0.TK.spelling);
            } catch (IOException ex) {
                Logger.getLogger(Coder.class.getName()).log(Level.SEVERE, null, ex);
            }
            arg0.TK.visit(this);
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
    public void visitorOperador(Operador O) {
        String aux = null;
        
        switch(O.TK.spelling){
            case "+":
               aux = "add";break;
            case "-":
               aux = "sub";break;
            case "/":
               aux = "div";break;
            case "*":
               aux = "mult";break;
            case ">=":
               aux = "gte";break;
            case "<=":
               aux = "lte";break;
            case ">":
               aux = "gt";break;
            case "<":
               aux = "lt";break;
            case "and":
               aux = "and";break;
            case "or":
               aux = "or";break;
        }
        
        try {
            escrever("CALL " + aux);
        } catch (IOException ex) {
            Logger.getLogger(Coder.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        O.TK.visit(this);
    }
    
}
