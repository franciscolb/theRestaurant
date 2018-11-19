package comInfo;

import java.io.*;
import javax.xml.parsers.*;
import org.xml.sax.*;
import org.xml.sax.helpers.*;
import genclass.GenericIO;

/**
 *   Este tipo de dados define as mensagens que são trocadas entre os clientes e o servidor numa solução do Problema
 *   dos Barbeiros Sonolentos que implementa o modelo cliente-servidor de tipo 2 (replicação do servidor) com lançamento
 *   estático dos threads barbeiro.
 *   A comunicação propriamente dita baseia-se na troca de objectos de tipo Message num canal TCP.
 */

public class Message
{
  /* Tipos das mensagens */

  /**
   *  Inicialização do ficheiro de logging (operação pedida pelo cliente)
   */

   public static final int SETLOG  =  1;

  /**
   *  Ficheiro de logging foi inicializado (resposta enviada pelo servidor)
   */

   public static final int LOGDONE =  2;

  /**
   *  Watch news (operação pedida pelo chef)
   */

   public static final int cWATCHNEWS  =  3;

  /**
   *  Hand note to the chef (resposta enviada pelo servidor, após mensagem do waiter)
   */

   public static final int wHANDNOTE =  4;

  /**
   *  Start cooking (resposta enviada pelo chef para o servidor kitchen)
   */

   public static final int cSTARTCOOK   =  5;

  /**
   *  Student return to bar 
   */

   public static final int wRETURNBAR    =  6;

  /**
   *  Chef alert waiter when portion is ready
   */

   public static final int cPORTIONREADY      =  7;

  /**
   *  Student collect portion in kitchen 
   */

   public static final int wCOLLECTPORTION  =  8;

  /**
   *  Continue the preparation of the chef 
   */

   public static final int cCONTINUEPREPARATION     =  9;

  /**
   *  Student in bar waiting for tasks to do
   */

   public static final int wLOOKAROUND      = 10;

   // Messages between students and waiter
   
  /**
   *  Student arrived
   */  

   public static final int sENTERRESTAURANT = 11;

   /**
    *  Student sitting on table
    */

   
   public static final int sSITTING = 12;

  /**
   *  Student salutes the client
   */

   public static final int wSALUTE   = 13;

  /**
   *  Message send by the first student
   */

   public static final int sFIRSTSTUDENT   = 14;

  /**
   *  Shutdown do servidor (operação pedida pelo cliente)
   */

   public static final int sLASTSTUDENT  = 15;

   /**
    * First student describe the order to the waiter
    */

   public static final int sDESCRIBEORDER  = 16;

   /**
    * Students chatting
    */

   public static final int sCHATING  = 17;
   
   /**
    * Student alerts students that the food is on table
    */

   public static final int wFOODONTABLE  = 18;

   /**
    * Student finish eating
    */

   public static final int sFINISHEATING  = 19;
   
   /**
    * Get number of students that finished eating
    */	

   public static final int sSTUDENTSFINISHED  = 20;
   
   /**
    * Student alert waiter
    */

   public static final int sALERTWAITER  = 21;
   
   /**
    * All students finished eating
    */

   public static final int sALLFINISHED  = 22;
   
   /**
    * Studens finished their meal
    */

   public static final int sMEALCOMPLETED  = 23;
   
   /**
    * Students waiting for others to finish their meal
    */

   public static final int sWAITOTHERS  = 24;
   
   /**
    * Last student ask waiter for the bill
    */

   public static final int sASKBILL  = 25;
   
   /**
    * Student processes the bill and delivers to the student
    */

   public static final int wRECEIVEPAYMENT  = 26;
   
   /**
    * Start paying the bill when waiter is in the table
    */

   public static final int sREQUESTBILL  = 27;
   
   /**
    * Student waiting for payment
    */

   public static final int wWAITINGFORPAYMENT  = 28;

   /**
    * Students waiting for the last student to pay the meal
    */

   public static final int sWAITFORPAYMENT  = 29;

   /**
    * Last student pays the meal
    */

   public static final int sPAYMENTDONE  = 30;

   /**
    * Students go home
    */

   public static final int sGOHOME  = 31;
   
   /**
    *  Shutdown do servidor (operação pedida pelo cliente)
    */

   public static final int SHUT   = 32;
   
   /**
    *  Operação realizada com sucesso (resposta enviada pelo servidor)
    */

    public static final int ACK      =  33;
    
    public static final int cALERTWAITER = 34;

    public static final int FIRST = 35;
    
    public static final int OTHER = 36;
    
    public static final int LAST = 37;
    
    public static final int FINISHEATTRUE = 38;
    
    public static final int FINISHEATFALSE = 39;

    public static final int FINISHCOURSE = 40;
    
    /**
     * chef update (try)
     */
    public static final int CHEFUPDATE = 41;
    
    /**
     * chef update (try)
     */
    public static final int WAITERUPDATE = 42;
    
    /**
     * chef update (try)
     */
    public static final int STUDENTUPDATE = 43;
    
    
   /* Campos das mensagens */

   /**
    *  Tipo da mensagem
    */

   private int msgType = -1;

 
   /**
    * chef state
    */
   private String state = null;
   private int studentId = -1;
 
   /**
   *  Instanciação de uma mensagem (forma 1).
   *
   *    @param type tipo da mensagem
   */

   public Message (int type)
   {
      msgType = type;
      
   }

  /**
   *  Instanciação de uma mensagem (forma 2).
   *
   *    @param type tipo da mensagem
   *    @param state state do student
   */ 


   public Message (int type, String state)
   {
      msgType = type;
      this.state = state;
   }

   
  /**
   *  Instanciação de uma mensagem (forma 3).
   *
   *    @param type tipo da mensagem
   *    @param studentId identificação do student
   *    @param state estado novo do student
   */

   public Message (int type, int studentId, String state)
   {
      msgType = type;
      this.studentId= studentId;
      this.state = state;
   }

  /**
   *  Instanciação de uma mensagem (forma 4).
   *
   *    @param type tipo da mensagem
   *    @param studentId id do student
   */

   public Message (int type, int studentId)
   {
      msgType = type;
      this.studentId = studentId;
   }
   

   /**
    *  Instanciação de uma mensagem (forma 5).
    *
    *    @param stringXML mensagem em formato XML
    */

   public Message (int type, String name, int nIter)
   {
      msgType = type;
   }
   
    public Message (String stringXML)
    {
       InputSource in = new InputSource (new StringReader (stringXML));
       SAXParserFactory spf;
       SAXParser saxParser = null;

       spf = SAXParserFactory.newInstance ();
       spf.setNamespaceAware (false);
       spf.setValidating (false);
       try
       { saxParser = spf.newSAXParser ();
         saxParser.parse (in, new HandlerXML ());
       }
       catch (ParserConfigurationException e)
       { GenericIO.writelnString ("Erro na instanciação do parser (configuração): " + e.getMessage () + "!");
         System.exit (1);
       }
       catch (SAXException e)
       { GenericIO.writelnString ("Erro na instanciação do parser (SAX): " + e.getMessage () + "!");
         System.exit (1);
       }
       catch (IOException e)
       { GenericIO.writelnString ("Erro na execução do parser (SAX): " + e.getMessage () + "!");
         System.exit (1);
       }
    }
   
   
  /**
   *  Obtenção do valor do campo tipo da mensagem.
   *
   *    @return tipo da mensagem
   */

   public String getState() {
	   return this.state;
   }
   
   public int getStudentId() {
	   return this.studentId;
   }
   
   public int getType ()
   {
      return (msgType);
   }

  /**
   *  Impressão dos campos internos.
   *  Usada para fins de debugging.
   *
   *    @return string contendo o numero de mensagem
   */

   @Override
   public String toString ()
   {
      return ("Tipo = " + msgType );
   }
   
   
   /**
    *  Conversão para um string XML.
    *
    *    @return string contendo a descrição XML da mensagem
    */

    public String toXMLString ()
    {
       return ("<Message>" +
                 "<Type>" + msgType + "</Type>" +
                 "<IdStudent>" + studentId + "</IdStudent>" +
                 "<StudentState>" + state + "</StudentState>" +
               "</Message>");
    }
    
    /**
     *  Este tipo de dados define o handler que gere os acontecimentos que ocorrem durante o parsing de um string XML.
     *
     */

     private class HandlerXML extends DefaultHandler
     {

       /**
        *  Parsing do string XML em curso
        *    @serialField parsing
        */

        private boolean parsing;

       /**
        *  Parsing de um elemento em curso
        *    @serialField element
        */

        private boolean element;

       /**
        *  Nome do elemento em processamento
        *    @serialField elemName
        */

        private String elemName;

       /**
        *  Início do processamento do string XML.
        *
        */

        @Override
        public void startDocument () throws SAXException
        {
           msgType = -1;
           studentId = -1;
           state = null;
           parsing = true;
        }


       /**
        *  Fim do processamento do string XML.
        *
        */

        @Override
        public void endDocument () throws SAXException
        {
           parsing = false;
        }

       /**
        *  Início do processamento de um elemento do string XML.
        *
        */

        @Override
        public void startElement(String namespaceURI, String localName,
                                 String qName, Attributes atts) throws SAXException
        {
           element = parsing;
           if (parsing) elemName = qName;
        }

       /**
        *  Fim do processamento de um elemento do string XML.
        *
        */

        @Override
        public void endElement(String namespaceURI, String localName, String qName) throws SAXException
        {
           element = false;
           elemName = null;
        }

       /**
        *  Processamento do conteúdo de um elemento do string XML.
        *
        */

        
		@Override
        public void characters(char[] ch, int start, int length) throws SAXException
        {
           String elem;

           elem = new String (ch, start, length);
           if (parsing && element)
              { if (elemName.equals ("Type")) {msgType = new Integer (elem); return;}
                if (elemName.equals ("IdStudent")) {studentId = new Integer (elem); return;}
                if (elemName.equals ("StudentState")) {state = new String (elem); return;}
              }
        }
     }
     
     public static void main (String [] args)
     {
        Message msg1 = new Message (3, "logging.txt", 10);
        String str = msg1.toXMLString ();
        Message msg2 = new Message (str);
        System.out.println ("Mensagem" + msg2.toString ());
     }
     
     
}
