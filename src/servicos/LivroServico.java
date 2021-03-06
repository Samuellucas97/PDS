/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servicos;

import dados.LivroDAOArquivo;
import dados.ServicoException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import dados.ILivroDAO;

/**
 * Implementa a lógica de negócios relativa a Livro
 * @author Samuel Lucas de Moura Ferino
 * @author José Wellinton Nunes Júnior
 */
public class LivroServico implements ILivroServico{
    private static LivroServico instance;
    private static ILivroDAO livroDAO;
    
    private LivroServico() throws ServicoException{        
        LivroServico.livroDAO = LivroDAOArquivo.getInstancia();
    }
    
    public static LivroServico getInstance() throws ServicoException{
        if(LivroServico.instance == null){
            LivroServico.instance = new LivroServico();            
        }
        return LivroServico.instance; 
    }
    
    @Override
    public Livro consultaLivro(String idLivro) throws ServicoException{
        return LivroServico.livroDAO.consultaLivro(idLivro);
    }    
    
    @Override
    public void registrarLivro(Livro livro) throws ServicoException{
        LivroServico.livroDAO.registrarLivro(livro);
    }
             
    @Override
    public void efetuarBloqueioTemporarioDeLivro(Livro livro) throws ServicoException{
        this.alterar(livro, "EstadoLivro", "BLOQUEADO_TEMPORARIAMENTE");
    }
    
    
    @Override
    public void efetuarBloqueioPermanenteDeLivro(Livro livro) throws ServicoException{
        this.alterar(livro, "EstadoLivro", "BLOQUEADO_PERMANENTEMENTE");
    }
    
    @Override
    public void efetuarDesbloqueioDeLivro(Livro livro) throws ServicoException{
        try{
            this.alterar(livro, "EstadoLivro", "DISPONIVEL");
        }
        catch(ServicoException ex){
            if(ex.getMessage().equals("Estado do livro não permitido!")) 
                try{
                    this.alterar(livro, "EstadoLivro", "DISPONIVEL");
                }
            catch(ServicoException ex2){
                if(ex2.getMessage().equals("Estado do livro não permitido!"))
                    throw new ServicoException("Livro indisponivel para desbloqueio!");
            }
        }
    }
    
    @Override
    public void excluirLivro(Livro livro) throws ServicoException{
        LivroServico.livroDAO.excluirLivro(livro);
    }

    
    @Override
    public void efetuarDevolucaoDeLivro(Livro livro) throws ServicoException  {    
        try{
            this.alterar(livro, "QuantidadeDeExemplaresEmprestados", 
                    String.valueOf(livro.getQuantidadeDeExemplaresEmprestados() - 1));
        }
        catch(ServicoException ex){
            if(ex.getMessage().equals("Quantidade inválida de livros emprestados!")){
                throw new ServicoException("Impossibilitado de efetuar a devolução!");
            }
            else
                throw new ServicoException(ex.getMessage());
        }
    }
    
    @Override
    public void efetuarEmprestimoDeLivro(Livro livro) throws ServicoException {
        try{        
            this.alterar(livro, "QuantidadeDeExemplaresEmprestados", 
                    String.valueOf(livro.getQuantidadeDeExemplaresEmprestados() + 1));
        }
        catch(ServicoException ex){
            if(ex.getMessage().equals("Quantidade inválida de livros emprestados!")){
                throw new ServicoException("Impossibilitado de efetuar o emprestimo!");
            }
            else
                throw new ServicoException(ex.getMessage());
        }
    }

    @Override
    public List<Livro> consultaLivros(String param, String key) throws ServicoException {
        return LivroServico.livroDAO.consultaLivros(param, key);
    }

    @Override
    public List<Livro> consultaLivros(List<String> params, List<String> keys) throws ServicoException {
        return LivroServico.livroDAO.consultaLivros(params, keys);
    }

        
    @Override
    public Livro copia(Livro livro) throws ServicoException{
        ArrayList<String> listAssunto;
        String[] assuntos;
        Livro livroCopiado = new Livro();
        
        livroCopiado.setId(livro.getId());
        livroCopiado.setEdicao(livro.getEdicao());
        livroCopiado.setVolume(livro.getVolume());
        livroCopiado.setEditora(livro.getEditora());
        livroCopiado.setTitulo(livro.getTitulo());
        livroCopiado.setAutor(livro.getAutor());

        listAssunto = new ArrayList<>();
        assuntos = (String[]) livro.getAssunto().toArray();
        listAssunto.addAll(Arrays.asList(assuntos));
        livroCopiado.setAssunto(listAssunto);

        livroCopiado.setDataDeLancamento(livro.getDataDeLancamento());
        livroCopiado.setQuantidadeDeTotalDeExemplares(livro.getQuantidadeDeTotalDeExemplares());
        livroCopiado.setQuantidadeDeExemplaresEmprestados(livro.getQuantidadeDeExemplaresEmprestados());
        livroCopiado.setEstadoLivro(livro.getEstadoLivro());
        
        return livroCopiado;
    }
    
    @Override
    public void alterar(Livro livro, List<String> params, List<String> keys) throws ServicoException{
        Livro livroAlterado = this.copia(livro);
        
        if(params.size() != keys.size()) 
            throw new ServicoException("Quantidade de chaves não confere com a quantidade de parâmetros!");
        
        int cont = 0;
        for(String param : params) {
            livroAlterado = this.alterarSemSalvar(livroAlterado, param, keys.get(cont++));
        }
        
        LivroServico.livroDAO.alterarLivro(livro.getId(), livroAlterado);
    }
    
    @Override
    public void alterar(Livro livro, String param, String key) throws ServicoException{
        Livro livroAlterado = this.copia(livro);
        ArrayList<String> listAssunto;
        String[] assuntos;
        
        switch(param){
            case "ID":
                livroAlterado.setId(key);
                break;
            case "Edicao":
                livroAlterado.setEdicao(key);
                break;
            case "Volume":
                try {
                    Integer it = new Integer(key);
                    livroAlterado.setVolume(it);                    
                }
                catch(NumberFormatException ex){
                    throw new ServicoException("Volume inválido!");
                }
                break;                
            case "Editora":
                livroAlterado.setEditora(key);
                break;
            case "Titulo":
                livroAlterado.setTitulo(key);
                break;
            case "Autor":
                livroAlterado.setAutor(key);
                break;
            case "Assunto":
                listAssunto = new ArrayList<>();
                assuntos = key.split(",");
                listAssunto.addAll(Arrays.asList(assuntos));
                livroAlterado.setAssunto(listAssunto);
                break;
            case "DataDeLancamento":
                livroAlterado.setDataDeLancamento(key);
                break;
            case "QuantidadeTotalDeExemplares":
                try {
                    Integer it = new Integer(key);
                    livroAlterado.setQuantidadeDeTotalDeExemplares(it);                   
                }
                catch(NumberFormatException ex){
                    throw new ServicoException("Quantidade total de exemplares inválida!");
                }
                break;
            case "QuantidadeDeExemplaresEmprestados":
                try {
                    Integer it = new Integer(key);
                    livroAlterado.setQuantidadeDeExemplaresEmprestados(it);
                }
                catch(NumberFormatException ex){
                    throw new ServicoException("Quantidade de exemplares emprestados inválida!");
                }
                break;
            case "EstadoLivro":
                if("DISPONIVEL".toLowerCase().contains(key.toLowerCase())){
                    if(livro.getQuantidadeDeTotalDeExemplares() > livro.getQuantidadeDeExemplaresEmprestados())
                        livroAlterado.setEstadoLivro(Livro.EstadoLivro.DISPONIVEL);
                    else
                        throw new ServicoException("Estado do livro não permitido!");
                }
                else if("ALUGADO".toLowerCase().contains(key.toLowerCase())){
                    if(livro.getQuantidadeDeTotalDeExemplares() == livro.getQuantidadeDeExemplaresEmprestados())
                        livroAlterado.setEstadoLivro(Livro.EstadoLivro.ALUGADO);
                    else
                        throw new ServicoException("Estado do livro não permitido!");
                }
                else if("BLOQUEADO_TEMPORARIAMENTE".toLowerCase().contains(key.toLowerCase()))
                    livroAlterado.setEstadoLivro(Livro.EstadoLivro.BLOQUEADO_TEMPORARIAMENTE);
                
                else if("BLOQUEADO_PERMANENTEMENTE".toLowerCase().contains(key.toLowerCase()))
                    livroAlterado.setEstadoLivro(Livro.EstadoLivro.BLOQUEADO_PERMANENTEMENTE);
                
                else{
                    throw new ServicoException("Estado do livro inválido!");
                }
                break;
            default:
                throw new ServicoException("O parâmetro" + key +  " é invalido!");
        }        
        LivroServico.livroDAO.alterarLivro(livro.getId(), livroAlterado);
    }
    
    private Livro alterarSemSalvar(Livro livro, String param, String key) throws ServicoException{
        Livro livroAlterado = this.copia(livro);
        ArrayList<String> listAssunto;
        String[] assuntos;
        
        switch(param){
            case "ID":
                livroAlterado.setId(key);
                break;
            case "Edicao":
                livroAlterado.setEdicao(key);
                break;
            case "Volume":
                try {
                    Integer it = new Integer(key);
                    livroAlterado.setVolume(it);                    
                }
                catch(NumberFormatException ex){
                    throw new ServicoException("Volume inválido!");
                }
                break;                
            case "Editora":
                livroAlterado.setEditora(key);
                break;
            case "Titulo":
                livroAlterado.setTitulo(key);
                break;
            case "Autor":
                livroAlterado.setAutor(key);
                break;
            case "Assunto":
                listAssunto = new ArrayList<>();
                assuntos = key.split(",");
                listAssunto.addAll(Arrays.asList(assuntos));
                livroAlterado.setAssunto(listAssunto);
                break;
            case "DataDeLancamento":
                livroAlterado.setDataDeLancamento(key);
                break;
            case "QuantidadeTotalDeExemplares":
                try {
                    Integer it = new Integer(key);
                    livroAlterado.setQuantidadeDeTotalDeExemplares(it);                   
                }
                catch(NumberFormatException ex){
                    throw new ServicoException("Quantidade total de exemplares inválida!");
                }
                break;
            case "QuantidadeDeExemplaresEmprestados":
                try {
                    Integer it = new Integer(key);
                    livroAlterado.setQuantidadeDeExemplaresEmprestados(it);
                }
                catch(NumberFormatException ex){
                    throw new ServicoException("Quantidade de exemplares emprestados inválida!");
                }
                break;
            case "EstadoLivro":
                if("DISPONIVEL".toLowerCase().contains(key.toLowerCase())){
                    if(livro.getQuantidadeDeTotalDeExemplares() > livro.getQuantidadeDeExemplaresEmprestados())
                        livroAlterado.setEstadoLivro(Livro.EstadoLivro.DISPONIVEL);
                    else
                        throw new ServicoException("Estado do livro não permitido!");
                }
                else if("ALUGADO".toLowerCase().contains(key.toLowerCase())){
                    if(livro.getQuantidadeDeTotalDeExemplares() == livro.getQuantidadeDeExemplaresEmprestados())
                        livroAlterado.setEstadoLivro(Livro.EstadoLivro.ALUGADO);
                    else
                        throw new ServicoException("Estado do livro não permitido!");
                }
                else if("BLOQUEADO_TEMPORARIAMENTE".toLowerCase().contains(key.toLowerCase()))
                    livroAlterado.setEstadoLivro(Livro.EstadoLivro.BLOQUEADO_TEMPORARIAMENTE);
                
                else if("BLOQUEADO_PERMANENTEMENTE".toLowerCase().contains(key.toLowerCase()))
                    livroAlterado.setEstadoLivro(Livro.EstadoLivro.BLOQUEADO_PERMANENTEMENTE);
                
                else{
                    throw new ServicoException("Estado do livro inválido!");
                }
                break;
            default:
                
        }
        return livroAlterado;
    }
    
}
