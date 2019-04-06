/**
 * @author Thiago da Costa Monteiro
 * 
 */
package interacaoComUsuario;

import dados.ServicoException;
import java.util.Scanner;
import servicos.Cliente;
import servicos.ClienteServico;
import servicos.ClienteServicoConcreto;
import servicos.Usuario;
import servicos.UsuarioServico;
import servicos.UsuarioServicoConcreto;



public class ClienteTerminal extends Terminal{
    
    UsuarioServico clienteServico;
    Usuario cliente;

    public ClienteTerminal() {
        this.clienteServico = (UsuarioServico) new ClienteServicoConcreto();
    }

    //public void avaliarLivros(int nota){}
    
    @Override
    protected Usuario autenticacao(String login, String senha){
        try{
            this.cliente = this.clienteServico.autenticacao(login, senha);
           
        }
        catch (ServicoException ex) {
            System.err.println(ex.getMessage());
        }
        return this.cliente;
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
}

    @Override
    public Object tratamentoEscolha(String escolha) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
