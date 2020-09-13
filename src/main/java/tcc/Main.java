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
    	
        // palavras que serão removidas por não serem relevantes.
        ArrayList<String> stopWords = new ArrayList<>(Arrays.asList("de","a","o","que","e","é","do","da","em","um","para","com","não","uma","os","no","se","na","por","mais","as","dos","como","mas","ao","ele","das","à","seu","sua","ou","quando","muito","nos","já","eu","também","só","pelo","pela","até","isso","ela","entre","depois","sem","mesmo","aos","seus","quem","nas","me","esse","eles","você","essa","num","nem","suas","meu","às","minha","numa","pelos","elas","qual","nós","lhe","deles","essas","esses","pelas","este","dele","tu","te","vocês","vos","lhes","meus","minhas","teu","tua","teus","tuas","nosso","nossa","nossos","nossas","dela","delas","esta","estes","estas","aquele","aquela","aqueles","aquelas","isto","aquilo","estou","está","estamos","estão","estive","esteve","estivemos","estiveram","estava","estávamos","estavam","estivera","estivéramos","esteja","estejamos","estejam","estivesse","estivéssemos","estivessem","estiver","estivermos","estiverem","hei","há","havemos","hão","houve","houvemos","houveram","houvera","houvéramos","haja","hajamos","hajam","houvesse","houvéssemos","houvessem","houver","houvermos","houverem","houverei","houverá","houveremos","houverão","houveria","houveríamos","houveriam","sou","somos","são","era","éramos","eram","fui","foi","fomos","foram","fora","fôramos","seja","sejamos","sejam","fosse","fôssemos","fossem","for","formos","forem","serei","será","seremos","serão","seria","seríamos","seriam","tenho","tem","temos","tém","tinha","tínhamos","tinham","tive","teve","tivemos","tiveram","tivera","tivéramos","tenha","tenhamos","tenham","tivesse","tivéssemos","tivessem","tiver","tivermos","tiverem","terei","terá","teremos","terão","teria","teríamos","teriam"));

        // resposta dada pelo aluno
        String resposta = "o estudante  deve  analisar  o  posicionamento  da  empresa  no  tocante  a  aspectos mercadológicos e  éticos,  de  forma  a  considerar  consequências  do vazamento  de  dados  e  das ações   levadas   a   frente   pela   empresa (comunicação   com   os   clientes   e   suporte   no monitoramento da identidade) com relação à reputação da empresa, à sua imagem, frente aos clientes  atuais  e  potenciais,  à  dificuldade  em  se  manterem  os  clientes  ou  de  se  conseguirem novos, ao impacto do problema frente ao valor do serviço oferecido pela empresa. Além disso, o  estudante  deve  apontar  que  o  vazamento  das  informações  está  relacionado  a  aspectos  da segurança  da  informação.  As  empresas  devem  garantir  que  a  informação  seja  acessada somente  por  pessoas  autorizadas  e  garantir  que  o  conteúdo  da  mensagem  não  seja  alterado ou violado";
        resposta = formataResposta(resposta);
        ArrayList<String> respostaEmLista = removeStopWord(stopWords, resposta);
        System.out.println("resposta em lista: " + respostaEmLista);
        double tamanhoResposta = respostaEmLista.size();
        System.out.println("tamanho resposta em lista: " + tamanhoResposta);

        //Caso precise juntar a frase de novo
        //respostaEmLista.stream().collect(Collectors.joining(" "));

        // resposta do gabarito.
        String respostaCorreta = "devemos analisar que o posicionamento da empresa, deve sem dúvida, olhar para o mercado, de modo a considerar que existem algumas consequênicas no que está sendo feito, e estas ações podem comprometer a reputação da mesma";
        respostaCorreta = formataResposta(respostaCorreta);
        ArrayList<String> respostaCorretaEmLista = removeStopWord(stopWords, respostaCorreta);
        System.out.println("resposta correta em lista: " + respostaCorretaEmLista);
        double tamanhoRespostaCorreta = respostaCorretaEmLista.size();
        System.out.println("tamanho resposta correta em lista: " + tamanhoRespostaCorreta);

        // precisa fazer Stemming ou lemmatizing antes de qualquer comparação.

        // Removendo as palavras que não deram match entre as duas listas
        respostaEmLista.retainAll(respostaCorretaEmLista);

        double tamanhoRespostaListAposComparacao = respostaEmLista.size();

        System.out.println("tamanho após comparação: "+ tamanhoRespostaListAposComparacao);

        double porcentagem = (tamanhoRespostaListAposComparacao / tamanhoRespostaCorreta) * 100;

        System.out.println("A porcentagem de acerto é: " + porcentagem + "%");

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
