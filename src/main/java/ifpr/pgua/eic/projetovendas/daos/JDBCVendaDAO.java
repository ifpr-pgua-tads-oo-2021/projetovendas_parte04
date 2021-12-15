package ifpr.pgua.eic.projetovendas.daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;

import ifpr.pgua.eic.projetovendas.daos.interfaces.VendaDAO;
import ifpr.pgua.eic.projetovendas.models.ItemVenda;
import ifpr.pgua.eic.projetovendas.models.Venda;
import ifpr.pgua.eic.projetovendas.utils.FabricaConexoes;

public class JDBCVendaDAO implements VendaDAO {

    private FabricaConexoes fabricaConexoes;

    public JDBCVendaDAO(FabricaConexoes fabricaConexoes){
        this.fabricaConexoes = fabricaConexoes;
    }

    private void salvarItens(Venda venda) throws Exception{
        Connection con = fabricaConexoes.getConnection();
        String sql = "INSERT INTO itenvendas(idVenda,idProduto,quantidade,valor) VALUES (?,?,?,?)";
        
        PreparedStatement pstmt = con.prepareStatement(sql);

        for(ItemVenda item:venda.getItens()){
            pstmt.setInt(1,venda.getId());
            pstmt.setInt(2,item.getProduto().getId());
            pstmt.setInt(3,item.getQuantidade());
            pstmt.setDouble(4, item.getValor());

            pstmt.execute();
        }

        pstmt.close();
        con.close();
    
    }


    @Override
    public boolean salvar(Venda venda) throws Exception {
        
        Connection con = fabricaConexoes.getConnection();

        String sql = "INSERT INTO vendas(idPessoa,dataHora,valorTotal) VALUES (?,?,?)";

        PreparedStatement pstmt = con.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
        
        pstmt.setInt(1, venda.getPessoa().getId());
        pstmt.setTimestamp(2, Timestamp.valueOf(venda.getDataHora()));
        pstmt.setDouble(3, venda.calcularTotal());

        pstmt.execute();

        ResultSet rsId = pstmt.getGeneratedKeys();
        rsId.next();
        int id = rsId.getInt(1);

        venda.setId(id);

        rsId.close();
        pstmt.close();
        con.close();
        
        salvarItens(venda);

        return true;
    }

    @Override
    public ArrayList<Venda> listar() throws Exception {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Venda buscar(int id) throws Exception {
        // TODO Auto-generated method stub
        return null;
    }
    



}
