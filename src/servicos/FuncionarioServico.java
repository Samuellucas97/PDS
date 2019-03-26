/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor. 
 */
package servicos;

import dados.ClienteDAO;
import dados.ClienteDAOArquivo;
import dados.ServicoException;

/**
 *
 * @author SamDan
 */
public class FuncionarioServico {
    
    private ClienteDAO clienteDAO;

    public FuncionarioServico( String tipoDAO) {
        
        if( tipoDAO.equals("Arquivo"))
            clienteDAO = new ClienteDAOArquivo();
    
    }
    
    
    
    public Cliente consultarCliente( String loginCliente ) throws ServicoException{
       return clienteDAO.buscarCliente(loginCliente);
    }
    
    public void registrarCliente(   String login, 
                                    String senha, 
                                    String nome,
                                    String telefone,
                                    int idade,
                                    String genero ) throws ServicoException{
    
    
        Cliente cliente = new Cliente();
        
        cliente.setLogin(login);
        cliente.setSenha(senha);
        cliente.setNome(nome);
        cliente.setTelefone(telefone);
        cliente.setIdade(idade);
        cliente.setGenero(genero);
        
        this.clienteDAO.salvar( cliente );
        
    
    }
    public void bloqueioPermanenteDeCliente(){}
    public void registrarLivro(){}
    public void alterarLivro(){}
    public void bloqueioTemporarioDeCliente(){}

}