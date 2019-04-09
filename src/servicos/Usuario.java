/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servicos;

/**
 * Representa um usuário
 * @author  Samuel Lucas de Moura Ferino
 * @author  José Wellinton
 */
public class Usuario {

    public enum Genero{
        MASCULINO,
        FEMININO;
    }
    
    protected String login;
    protected String senha;
    protected String nome;
    protected String telefone;
    protected int idade;
    protected Genero genero;
    
    /**
     * Construtor padrão
     */
    public Usuario(){
        this.login = new String();
        this.senha = new String();
        this.nome  = new String();
        this.telefone = new String();
        this.idade = 0;
    }

    /**
     * Construtor parametrizado
     * @param login Login do usuário
     * @param senha Senha do usuário 
     */
    public Usuario( String login, String senha){
        
        this.login = login;
        this.senha = senha;
    
        this.nome  = new String();
        this.telefone = new String();
        this.idade = 0;
    }
    
    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public int getIdade() {
        return idade;
    }

    public void setIdade(int idade) {
        this.idade = idade;
    }

    public Genero getGenero() {
        return genero;
    }
    

    public void setGenero(Genero genero) {
        this.genero = genero;
    }
}
