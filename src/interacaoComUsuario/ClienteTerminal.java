/**
 * @author Thiago da Costa Monteiro
 * 
 * 
 */
package interacaoComUsuario;

import dados.ServicoException;
import java.util.List;
import java.util.Scanner;
import servicos.ClienteServico;
import servicos.Usuario;
import servicos.IUsuarioServico;
import servicos.Livro;
import servicos.LivroServico;



public class ClienteTerminal extends Terminal{
    
    private final IUsuarioServico clienteServico;
    private LivroServico livroServico; 
    private Usuario cliente;

    public ClienteTerminal() {
        this.livroServico = new LivroServico();      
        this.clienteServico = (IUsuarioServico) new ClienteServico();
    }
    
    @Override
    protected void autenticacao(String login, String senha){
        try{
            this.cliente = this.clienteServico.autenticacao(login, senha);
           
        }
        catch (ServicoException ex) {
            System.err.println(ex.getMessage());
        }
    }

    @Override
    public String apresentacao( ){
        Scanner entradaUsuario = new Scanner(System.in);
        System.out.println("Bem vindo(a), " + cliente.getNome() + " ao LibraryManage! \n"
                        + "Digite o número de uma das opções: \n"
                        + "(1)Avaliar livro \n"
                        + "(2)Buscar livro \n"
                        + "(3)Realizar logout \n");
        while(true){
            String entradaNumero = entradaUsuario.next();
            if(entradaNumero.equals("1") 
               || entradaNumero.equals("2")
               || entradaNumero.equals("3")){
                return entradaNumero;
            }
            else{
                 System.out.println("Escolha inválida! \n" 
                                    + "Digite o número de uma das opções: \n"
                                    + "(1)Avaliar livro \n"
                                    + "(2)Buscar livro \n"
                                    + "(3)Realizar logout \n");
            }
        }
    }

    @Override
    public Object tratamentoEscolha(String escolha) {
        Scanner entradaUsuario = new Scanner(System.in);
        if(escolha.equals("1")){
            List<Livro> livrosAvaliados = livroServico.listaLivrosAlugados();
            System.out.print("Você escolheu a opção (1) - Avaliar livro \n");
            System.out.print("Gerando lista de livros que foram alugados por você... \n");
            for (int i = 0; i < livrosAvaliados.size(); i++) {
                System.out.println("Lívro (" + i + ")");
                System.out.println("Título: " + livrosAvaliados.get(i).getTitulo());
                System.out.println("Autor: " + livrosAvaliados.get(i).getAutor());
            }
            System.out.println("Escolha o número do livro que você quer que seja avaliado");
            
            boolean numeric = true;
            Double num = 0.0;
            while(true){
                String livroEscolhido = entradaUsuario.next();
                
                try {
                    num = Double.parseDouble(livroEscolhido);
                } 
                catch (NumberFormatException e) {
                    numeric = false;
                }

                if(numeric && (num>0) && (num<=livrosAvaliados.size())){
                    double avaliacao = 0.0;
                    System.out.println("De uma nota de 0 a 5 para o livro: " + livrosAvaliados.get(num.intValue()).getTitulo());
                    Double num2 = 0.0;
                    boolean key2while = true;
                    while(key2while){
                        livroEscolhido = entradaUsuario.next();
                        
                        try {
                            num2 = Double.parseDouble(livroEscolhido);
                        } 
                        catch (NumberFormatException e) {
                            numeric = false;
                        }
                        if(numeric && (num>=0) && (num<=5)){
                        avaliacao = num;
                        key2while = false;
                        }
                        else{
                            System.out.println("Entrada incorreta! Insira um número de 0 a 5 para o livro "
                                                + livrosAvaliados.get(num.intValue()).getTitulo());
                        }
                    }
                    
                    return livroServico.avaliarLivro(livrosAvaliados.get(num.intValue()), avaliacao);
                }
                else{
                    System.out.println("Escolha inválida! \n"); 
                    System.out.println("Escolha o número de um dos livros:");
                    for (int i = 0; i < livrosAvaliados.size(); i++) {
                        System.out.println("Lívro (" + i + ")");
                        System.out.println("Título: " + livrosAvaliados.get(i).getTitulo());
                        System.out.println("Autor: " + livrosAvaliados.get(i).getAutor());
                    }
                }
            }

        }
        else if(escolha.equals("2")){
            String op;
            String key;
            System.out.println("Você escolheu a opção (2) - Buscar livro");
            System.out.println("As opções de busca são:");
            System.out.println("(1) Buscar por Título");
            System.out.println("(2) Buscar por Autor");
            System.out.println("(3) Buscar por Assunto");
            System.out.println("(4) Buscar por Data de Lançamento");
            System.out.println("(5) Buscar por Edição");
            System.out.println("(6) Buscar por Editora");
            System.out.println("(7) Buscar por ID do livro");


            while(true){
                op = entradaUsuario.next();
                if(op.equals("1")){
                    System.out.print("Digite o título do livro: ");
                    key = entradaUsuario.next();

                    return efetuarBusca("Titulo" , key);
                }
                else if(op.equals("2")){
                    System.out.print("Digite o nome do autor do livro: ");
                    key = entradaUsuario.next();

                    return efetuarBusca("Autor" , key);
                }
                else if(op.equals("3")){
                    System.out.print("Digite o assunto do livro: ");
                    key = entradaUsuario.next();

                    return efetuarBusca("Assunto" , key);
                }
                else if(op.equals("4")){
                    System.out.print("Digite a data de lançamento do livro: ");
                    key = entradaUsuario.next();

                    return efetuarBusca("DataDeLancamento" , key);
                }
                else if(op.equals("5")){
                    System.out.print("Digite a edição do livro: ");
                    key = entradaUsuario.next();

                    return efetuarBusca("Edicao" , key);
                }
                else if(op.equals("6")){
                    System.out.print("Digite o nome da editora: ");
                    key = entradaUsuario.next();

                    return efetuarBusca("Editora" , key);
                }
                else if(op.equals("7")){
                    System.out.print("Digite o ID do livro: ");
                    key = entradaUsuario.next();

                    return efetuarBusca("ID" , key);
                }
                else{
                    System.out.println("Escolha inválida! \n"); 
                    System.out.println("As opções de busca são:");
                    System.out.println("(1) Buscar por Título");
                    System.out.println("(2) Buscar por Autor");
                    System.out.println("(3) Buscar por Assunto");
                    System.out.println("(4) Buscar por Data de Lançamento");
                    System.out.println("(5) Buscar por Edição");
                    System.out.println("(6) Buscar por Editora");
                    System.out.println("(7) Buscar por ID do livro");
                }
            }
            
             

        }
        
        else if(escolha.equals("3")){
        System.out.println("Você escolheu a opção (3) - Realizar logout");
        System.out.println("REALIZANDO LOGOUT...");

        return new UsuarioTerminal();
        
        }
        
        return null;   
    }
    private List<Livro> efetuarBusca(String param, String key){
        return livroServico.consultaLivros(param, key);

    }

    private List<Livro> efetuarBusca(List<String> params, List<String> keys){
        return this.livroServico.consultaLivros(params, keys);

    }
        

}
