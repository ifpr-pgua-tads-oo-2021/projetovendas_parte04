package ifpr.pgua.eic.projetovendas.repositorios;

import java.time.LocalDateTime;

import ifpr.pgua.eic.projetovendas.daos.interfaces.VendaDAO;
import ifpr.pgua.eic.projetovendas.models.ItemVenda;
import ifpr.pgua.eic.projetovendas.models.Pessoa;
import ifpr.pgua.eic.projetovendas.models.Produto;
import ifpr.pgua.eic.projetovendas.models.Venda;

public class RepositorioVendas {
    
    private VendaDAO vendaDAO;

    private Venda venda;

    public RepositorioVendas(VendaDAO vendaDAO){
        this.vendaDAO = vendaDAO;
    }

    public void iniciaVenda(Pessoa pessoa){
        LocalDateTime dataHora = LocalDateTime.now();
        venda = new Venda(pessoa, dataHora);
    }

    public void adicionaProduto(Produto produto, int quantidade){

        ItemVenda itemVenda = new ItemVenda(produto, quantidade, produto.getValor());
        venda.adicionar(itemVenda);
    }

    public void finalizaVenda() throws Exception{
        
        venda.calcularTotal();
        vendaDAO.salvar(venda);

        venda = null;

    }

    public Venda getVenda(){
        return venda;
    }

}
