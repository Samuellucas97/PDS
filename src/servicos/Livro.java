/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servicos;

import dados.ServicoException;
import java.util.ArrayList;

/**
 *  Representa um livro
 */
public class Livro implements java.io.Serializable{

    /**
     * Representa uma data temporal
     * @author Samuel Lucas de Moura Ferino
     */
    public class Data implements java.io.Serializable{

        private String dia;
        private String mes;
        private String ano;
        private String data; /// USADO APENAS INTERNAMENTE

        /**
         * Construtor da classe Data
         * @param data  Data em formato de string a ser armazenada
         * @throws ServicoException 
         */
        Data(String data) throws ServicoException{

            String diaTemporario = data.split("/")[0];
            String mesTemporario = data.split("/")[1];
            String anoTemporario = data.split("/")[2];

            if( diaTemporario.length() != 2
                ||    Integer.parseInt( diaTemporario ) < 1 
                || ( Integer.parseInt( diaTemporario ) > 31  && (  Integer.parseInt( mesTemporario ) == 1  || 
                                                                Integer.parseInt( mesTemporario ) == 3  || Integer.parseInt( mesTemporario ) == 5 || 
                                                                Integer.parseInt( mesTemporario ) == 7  || Integer.parseInt( mesTemporario ) == 8 ||
                                                                Integer.parseInt( mesTemporario ) == 10 || Integer.parseInt( mesTemporario ) == 12  ) 

                || ( Integer.parseInt( diaTemporario ) > 30  && (  Integer.parseInt( mesTemporario ) == 4  || Integer.parseInt( mesTemporario ) == 6 || 
                                                                Integer.parseInt( mesTemporario ) == 9  || Integer.parseInt( mesTemporario ) == 11 ) )

                || ( Integer.parseInt( diaTemporario ) > 28  && (  Integer.parseInt( mesTemporario ) == 2 )    )

                )  
               )
                throw new ServicoException("Dia invalido");

            if( mesTemporario.length() != 2 || 
                Integer.parseInt(mesTemporario) < 1 || 
                Integer.parseInt(mesTemporario) > 12 )
                throw new ServicoException("Mes invalido");

            if( anoTemporario.length() != 4  ||
                Integer.parseInt(anoTemporario) < 1920 ||
                Integer.parseInt(anoTemporario) > 2050 )
                throw new ServicoException("Ano invalido");

            this.dia = diaTemporario;
            this.mes = mesTemporario;
            this.ano = anoTemporario;
            this.data = data;
        }

        public String getDia() {
            return this.dia;
        }

        public String getMes() {
            return this.mes;
        }

        public String getAno() {
            return this.ano;
        }

        /**
         * Envia a string que contém a data
         * @return  A data em formato de string
         */
        @Override
        public String toString(){
            return this.data;
        }
    }
    
    /**
     * Representa o estado de um livro
     */
    public enum EstadoLivro{
        DISPONIVEL,
        ALUGADO,
        BLOQUEADO_TEMPORARIAMENTE,
        BLOQUEADO_PERMANENTEMENTE;
    }
    
    private String id;
    private EstadoLivro estadoLivro;
    private String edicao;
    private int volume;
    private String editora;
    private String titulo;
    private String autor;
    private ArrayList<String> assunto;
    private Data dataDeLancamento;
    private int quantidadeDeTotalDeExemplares;
    private int quantidadeDeExemplaresEmprestados;
    
    public Livro(){
        this.assunto = new ArrayList<>();
        this.quantidadeDeTotalDeExemplares = 1;
        this.estadoLivro = EstadoLivro.DISPONIVEL;
        this.quantidadeDeExemplaresEmprestados = 0;
    }
    
    public Livro(  String  id, 
            String  edicao,
            int     volume,
            String  editora,
            String  titulo, 
            String  autor, 
            ArrayList<String> assunto,
            String  dataDeLancamento, 
            int quantidadeTotalDeExemplares) throws ServicoException{
        
        this.id = id;
        this.estadoLivro = EstadoLivro.DISPONIVEL;
        this.edicao = edicao;
        this.volume = volume;
        this.editora = editora;
        this.titulo = titulo;
        this.autor = autor;
        this.dataDeLancamento =  new Data(dataDeLancamento);
        this.assunto = assunto;
        this.quantidadeDeTotalDeExemplares = quantidadeTotalDeExemplares;
        this.quantidadeDeExemplaresEmprestados = 0;
    }
   
    public String getId(){
        return this.id;
    }
    
    public EstadoLivro getEstadoLivro(){
        return this.estadoLivro;
    }
    
    public String getEdicao() {
        return this.edicao;
    }

    public int getVolume() {
        return this.volume;
    }

    public String getEditora() {
        return this.editora;
    }

    public ArrayList<String> getAssunto() {
        return this.assunto;
    }
       
    public String getTitulo() {
        return this.titulo;
    }

    public String getAutor() {
        return this.autor;
    }


    public String getDataDeLancamento() {
        return this.dataDeLancamento.toString();
    }
    
    public int getQuantidadeDeExemplaresEmprestados(){
        return this.quantidadeDeExemplaresEmprestados;
    }
    
    public int getQuantidadeDeTotalDeExemplares() {
        return this.quantidadeDeTotalDeExemplares;
    }
    
    public void setQuantidadeDeTotalDeExemplares(int quantidadeDeTotalDeExemplares) throws ServicoException {
        if(quantidadeDeTotalDeExemplares < this.quantidadeDeExemplaresEmprestados) 
            throw new ServicoException("Quantidade inválida de exemplares!");
        
        if(quantidadeDeTotalDeExemplares == this.quantidadeDeExemplaresEmprestados 
                && this.getEstadoLivro().equals(EstadoLivro.DISPONIVEL))
            this.setEstadoLivro(EstadoLivro.ALUGADO);
        
        this.quantidadeDeTotalDeExemplares = quantidadeDeTotalDeExemplares;
    }
    
    public void setEstadoLivro(EstadoLivro estado){
        this.estadoLivro = estado;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setEdicao(String edicao) {
        this.edicao = edicao;
    }

    public void setVolume(int volume) {
        this.volume = volume;
    }

    public void setEditora(String editora) {
        this.editora = editora;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public void setAssunto(ArrayList<String> assunto) {
        this.assunto = assunto;
    }

    public void setDataDeLancamento(String dataDeLancamento) throws ServicoException {
        this.dataDeLancamento = new Data(dataDeLancamento);
    }

    public void setQuantidadeDeExemplaresEmprestados(int quantidadeDeExemplaresEmprestados) throws ServicoException {
         if(quantidadeDeExemplaresEmprestados < 0 && quantidadeDeExemplaresEmprestados > this.quantidadeDeTotalDeExemplares) 
            throw new ServicoException("Quantidade inválida de livros emprestados!");
         
         if(this.quantidadeDeTotalDeExemplares == quantidadeDeExemplaresEmprestados 
                && this.getEstadoLivro().equals(EstadoLivro.DISPONIVEL))
            this.setEstadoLivro(EstadoLivro.ALUGADO);
         
        this.quantidadeDeExemplaresEmprestados = quantidadeDeExemplaresEmprestados;
    }
}
