package tcc;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.parser.PdfTextExtractor;

public class Main {
    public static void main(String []args){

    	try {
		    PdfReader reader = new PdfReader("C:/Users/cassi/Documents/workspace-spring-tool-suite-4-4.7.1.RELEASE/tcc/src/main/resources/padrao_resposta_ciencia_computacao_bacharelado.pdf");
		    System.out.println("This PDF has "+reader.getNumberOfPages()+" pages.");
		    String page = PdfTextExtractor.getTextFromPage(reader, 2);
		    System.out.println("Page Content:\n\n"+page+"\n\n");
		    System.out.println("Is this document tampered: "+reader.isTampered());
		    System.out.println("Is this document encrypted: "+reader.isEncrypted());
		} catch (IOException e) {
		    e.printStackTrace();
		}
    	
        // palavras que ser�o removidas por n�o serem relevantes.
        ArrayList<String> stopWords = new ArrayList<>(Arrays.asList("de","a","o","que","e","�","do","da","em","um","para","com","n�o","uma","os","no","se","na","por","mais","as","dos","como","mas","ao","ele","das","�","seu","sua","ou","quando","muito","nos","j�","eu","tamb�m","s�","pelo","pela","at�","isso","ela","entre","depois","sem","mesmo","aos","seus","quem","nas","me","esse","eles","voc�","essa","num","nem","suas","meu","�s","minha","numa","pelos","elas","qual","n�s","lhe","deles","essas","esses","pelas","este","dele","tu","te","voc�s","vos","lhes","meus","minhas","teu","tua","teus","tuas","nosso","nossa","nossos","nossas","dela","delas","esta","estes","estas","aquele","aquela","aqueles","aquelas","isto","aquilo","estou","est�","estamos","est�o","estive","esteve","estivemos","estiveram","estava","est�vamos","estavam","estivera","estiv�ramos","esteja","estejamos","estejam","estivesse","estiv�ssemos","estivessem","estiver","estivermos","estiverem","hei","h�","havemos","h�o","houve","houvemos","houveram","houvera","houv�ramos","haja","hajamos","hajam","houvesse","houv�ssemos","houvessem","houver","houvermos","houverem","houverei","houver�","houveremos","houver�o","houveria","houver�amos","houveriam","sou","somos","s�o","era","�ramos","eram","fui","foi","fomos","foram","fora","f�ramos","seja","sejamos","sejam","fosse","f�ssemos","fossem","for","formos","forem","serei","ser�","seremos","ser�o","seria","ser�amos","seriam","tenho","tem","temos","t�m","tinha","t�nhamos","tinham","tive","teve","tivemos","tiveram","tivera","tiv�ramos","tenha","tenhamos","tenham","tivesse","tiv�ssemos","tivessem","tiver","tivermos","tiverem","terei","ter�","teremos","ter�o","teria","ter�amos","teriam"));

        // resposta dada pelo aluno
        String resposta = "o estudante  deve  analisar  o  posicionamento  da  empresa  no  tocante  a  aspectos mercadol�gicos e  �ticos,  de  forma  a  considerar  consequ�ncias  do vazamento  de  dados  e  das a��es   levadas   a   frente   pela   empresa (comunica��o   com   os   clientes   e   suporte   no monitoramento da identidade) com rela��o � reputa��o da empresa, � sua imagem, frente aos clientes  atuais  e  potenciais,  �  dificuldade  em  se  manterem  os  clientes  ou  de  se  conseguirem novos, ao impacto do problema frente ao valor do servi�o oferecido pela empresa. Al�m disso, o  estudante  deve  apontar  que  o  vazamento  das  informa��es  est�  relacionado  a  aspectos  da seguran�a  da  informa��o.  As  empresas  devem  garantir  que  a  informa��o  seja  acessada somente  por  pessoas  autorizadas  e  garantir  que  o  conte�do  da  mensagem  n�o  seja  alterado ou violado";
        resposta = formataResposta(resposta);
        ArrayList<String> respostaEmLista = removeStopWord(stopWords, resposta);
        System.out.println("resposta em lista: " + respostaEmLista);
        double tamanhoResposta = respostaEmLista.size();
        System.out.println("tamanho resposta em lista: " + tamanhoResposta);

        //Caso precise juntar a frase de novo
        //respostaEmLista.stream().collect(Collectors.joining(" "));

        // resposta do gabarito.
        String respostaCorreta = "devemos analisar que o posicionamento da empresa, deve sem d�vida, olhar para o mercado, de modo a considerar que existem algumas consequ�nicas no que est� sendo feito, e estas a��es podem comprometer a reputa��o da mesma";
        respostaCorreta = formataResposta(respostaCorreta);
        ArrayList<String> respostaCorretaEmLista = removeStopWord(stopWords, respostaCorreta);
        System.out.println("resposta correta em lista: " + respostaCorretaEmLista);
        double tamanhoRespostaCorreta = respostaCorretaEmLista.size();
        System.out.println("tamanho resposta correta em lista: " + tamanhoRespostaCorreta);

        // precisa fazer Stemming ou lemmatizing antes de qualquer compara��o.

        // Removendo as palavras que n�o deram match entre as duas listas
        respostaEmLista.retainAll(respostaCorretaEmLista);

        double tamanhoRespostaListAposComparacao = respostaEmLista.size();

        System.out.println("tamanho ap�s compara��o: "+ tamanhoRespostaListAposComparacao);

        double porcentagem = (tamanhoRespostaListAposComparacao / tamanhoRespostaCorreta) * 100;

        System.out.println("A porcentagem de acerto �: " + porcentagem + "%");

    }

    private static ArrayList<String> removeStopWord(ArrayList<String> stopWords, String resposta) {
        ArrayList<String> respostaEmLista =
                Stream.of(resposta.toLowerCase().split(" "))
                        .collect(Collectors.toCollection(ArrayList<String>::new));
        respostaEmLista.removeAll(stopWords);
        respostaEmLista.removeAll(Arrays.asList("", null));
        return respostaEmLista;
    }

    private static String formataResposta(String resposta) {
        resposta = resposta.replace(",", "");
        resposta = resposta.replace(".", "");
        resposta = resposta.replace("(", "");
        resposta = resposta.replace(")", "");
        return resposta;
    }
}
