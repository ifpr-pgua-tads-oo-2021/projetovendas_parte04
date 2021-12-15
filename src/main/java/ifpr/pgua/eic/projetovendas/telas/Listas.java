package ifpr.pgua.eic.projetovendas.telas;

import java.util.List;

import ifpr.pgua.eic.projetovendas.App;
import ifpr.pgua.eic.projetovendas.models.Pessoa;
import ifpr.pgua.eic.projetovendas.models.Produto;
import ifpr.pgua.eic.projetovendas.repositorios.RepositorioPessoas;
import ifpr.pgua.eic.projetovendas.repositorios.RepositorioProdutos;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.Alert.AlertType;


public class Listas {

    @FXML
    private ListView<Pessoa> lstPessoas;

    @FXML
    private ListView<Produto> lstProdutos;

    @FXML
    private Label lbListaVaziaProdutos;


    
    private RepositorioProdutos repositorio;
    private RepositorioPessoas repositorioPessoas;

    public Listas(RepositorioPessoas repositorioPessoas, RepositorioProdutos repositorio){
        this.repositorio = repositorio;
        this.repositorioPessoas = repositorioPessoas;
    }

    public void initialize(){

        lstPessoas.setCellFactory(lista -> new ListCell<>(){
            protected void updateItem(Pessoa pessoa, boolean alterou) {
                super.updateItem(pessoa, alterou);
                if(pessoa != null){
                    setText("("+pessoa.getId()+")"+pessoa.getNome());
                }else{
                    setText(null);
                }
            };
        });

        try{
            lstPessoas.getItems().addAll(repositorioPessoas.listarPessoas());
            List<Produto> produtos = repositorio.listarProdutos();
            lstProdutos.getItems().addAll(produtos);
            
        }catch(Exception e){
            Alert alert = new Alert(AlertType.ERROR, e.getMessage());
            alert.showAndWait();
        }
        
    }


}
