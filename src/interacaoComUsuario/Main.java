package interacaoComUsuario;

import dados.ServicoException;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import servicos.Administrador;
import servicos.Cliente;
import servicos.Livro;
import servicos.Operador;
import servicos.Usuario;

/**
 * Implementação da Main
 * @author	Samuel Lucas de Moura Ferino
 * @author      Thiago da Costa Monteiro   
 * @author      José Wellinton Nunes Jr.
 * @since	08.03.2019 
 * @version	0.0.1
 */
public class Main {
    private static Contexto estados = Contexto.getInstance();
    private static Usuario usr = new Usuario();
   
	
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        
        while(true){
            // Se estado for um UsuarioTerminal
            if(estados.getEstado().getClass().isInstance(new UsuarioTerminal())){
                String escolha = estados.getEstado().apresentacao();
                Object obj = estados.getEstado().tratamentoEscolha(escolha);
                switch(escolha){
                    case ("1"):
                        if(obj == null) {
                            break;
                        }
                        else if(obj.getClass().isInstance(new ClienteTerminal())){
                            usr = ((UsuarioTerminal)(estados.getEstado())).getUsuario();
                            ((ClienteTerminal) obj).SetCliente((Cliente) usr);
                            estados.setEstado((ClienteTerminal) obj);
                        }
                        else if(obj.getClass().isInstance(new OperadorTerminal())){
                            usr = ((UsuarioTerminal)(estados.getEstado())).getUsuario();
                            ((OperadorTerminal) obj).SetOperador((Operador) usr);

                            estados.setEstado((OperadorTerminal) obj);
                        }
                        else 
                            try {
                                if(obj.getClass().isInstance(new AdministradorTerminal())){
                                    usr = ((UsuarioTerminal)(estados.getEstado())).getUsuario();
                                    ((AdministradorTerminal) obj).SetAdministrador((Administrador) usr);

                                    estados.setEstado((AdministradorTerminal) obj);
                                }
                                else{
                                    usr = null;
                                    estados.setEstado((UsuarioTerminal) obj);
                                }
                            } catch (ServicoException ex) {
                                            System.err.println(ex.getMessage());
                            }
                        break;
                        
                    case("2"):  
                        if(obj == null) break;
                        List<Livro> livros = (List<Livro>)obj;
                        
                        System.out.println("Lista de livros: \n \n");
                        
                        if(livros.isEmpty()){
                            System.out.println("Não existem livros correspondendo a sua busca!");
                        }
                        else{
                            System.out.println("Lista de livros: ");
                            for (Livro livro : livros) {
                                System.out.println("Título: " + livro.getTitulo());
                                System.out.println("Autor: " + livro.getAutor());
                                System.out.println("Assunto:" + livro.getAssunto());
                                System.out.println("Data de Lançamento: " + livro.getDataDeLancamento());
                                System.out.println("Id: " + livro.getId());
                                //System.out.println("Cidade de publicação: "+ livro.getCidadeDePublicacao());
                                System.out.println("Edição: " + livro.getEdicao()); 
                                System.out.println("Editora: " + livro.getEditora());
                                System.out.println("Volume: " + livro.getVolume());
                                System.out.println("Estado: " + livro.getEstadoLivro());
                                System.out.println("Disponibilidade: " + livro.getQuantidadeDeExemplaresEmprestados()
                                                    + " de " + livro.getQuantidadeDeTotalDeExemplares() + "livros alugados no momento. \n");               
                            }
                            
                        }
                        break;
                        
                    default:
                        //TO DO ERRO
                        break;
                }

            
            }
            // Se estado for um ClienteTerminal
            else if(estados.getEstado().getClass().isInstance(new ClienteTerminal())){
                String escolha = estados.getEstado().apresentacao();
                Object obj = estados.getEstado().tratamentoEscolha(escolha);
                switch(escolha){
                    case ("1"):
                        if(obj == null) {
                            break;
                        }
                        else if(obj == "Lista de livros pegos emprestado vazia!"){
                            System.out.println("\n" + obj + "\n");
                            break;

                        }
                        else if(obj == "Livro avaliado com sucesso!"){
                            System.out.println(obj);
                            break;
                        }
                        else{
                            //TO DO ERRO
                            break;
                        }

                    case("2"):
                        List<Livro> livros = (List<Livro>)obj;
                        
                        System.out.println("Lista de livros: \n \n");
                        
                        if(livros == null || livros.isEmpty()){
                           System.out.println("Não existem livros correspondendo a sua busca! \n");
                           break;
                        }
                        else{
                            System.out.println("Lista de livros: ");
                            for (Livro livro : livros) {
                                System.out.println("Título: " + livro.getTitulo());
                                System.out.println("Autor: " + livro.getAutor());
                                System.out.println("Assunto:" + livro.getAssunto());
                                System.out.println("Data de Lançamento: " + livro.getDataDeLancamento());
                                System.out.println("Id: " + livro.getId());
                                //System.out.println("Cidade de publicação: "+ livro.getCidadeDePublicacao());
                                System.out.println("Edição: " + livro.getEdicao()); 
                                System.out.println("Editora: " + livro.getEditora());
                                System.out.println("Volume: " + livro.getVolume());
                                System.out.println("Estado: " + livro.getEstadoLivro());
                                System.out.println("Disponibilidade: " + livro.getQuantidadeDeExemplaresEmprestados()
                                                    + " de " + livro.getQuantidadeDeTotalDeExemplares() + "livros alugados no momento. \n");               
                            }
                            
                        }
                        break;
                        
                    case("3"):
                        usr = null;
                        estados.setEstado((UsuarioTerminal) obj);
                        break;
                        
                    default:
                        //TO DO ERRO
                        break;
                }

            
            }
            // Se estado for um OperadorTerminal
            else if(estados.getEstado().getClass().isInstance(new OperadorTerminal())){
                String escolha = estados.getEstado().apresentacao();
                Object obj = estados.getEstado().tratamentoEscolha(escolha);
                
                switch(escolha){
                    case ("1"):
                        if(obj == null) {
                            break;
                        }
                        else if(obj == "Livro avaliado com sucesso!"){
                            System.out.println(obj);
                            break;
                        }
                        else{
                            //TO DO ERRO
                            break;
                        }

                    case("2"):
                        List<Livro> livros = (List<Livro>)obj;
                        
                        System.out.println("Lista de livros: \n \n");
                        
                        if(livros.isEmpty()){
                           System.out.println("Não existem livros correspondendo a sua busca!");
                        }
                        else{
                            System.out.println("Lista de livros: ");
                            for (Livro livro : livros) {
                                System.out.println("Título: " + livro.getTitulo());
                                System.out.println("Autor: " + livro.getAutor());
                                System.out.println("Assunto:" + livro.getAssunto());
                                System.out.println("Data de Lançamento: " + livro.getDataDeLancamento());
                                System.out.println("Id: " + livro.getId());
                                //System.out.println("Cidade de publicação: "+ livro.getCidadeDePublicacao());
                                System.out.println("Edição: " + livro.getEdicao()); 
                                System.out.println("Editora: " + livro.getEditora());
                                System.out.println("Volume: " + livro.getVolume());
                                System.out.println("Estado: " + livro.getEstadoLivro());
                                System.out.println("Disponibilidade: " + livro.getQuantidadeDeExemplaresEmprestados()
                                                    + " de " + livro.getQuantidadeDeTotalDeExemplares() + "livros alugados no momento. \n");               
                            }
                            
                        }
                        break;
                        
                    case("3"):
                        usr = null;
                        estados.setEstado((UsuarioTerminal) obj);
                        break;
                        
                    default:
                        //TO DO ERRO
                        break;
                }
            }
            else{
            
            
            }       
        
        }
        
        
    }

	
}
