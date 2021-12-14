package ifpr.pgua.eic.projetovendas.daos;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;


import ifpr.pgua.eic.projetovendas.daos.interfaces.ProdutoDAO;
import ifpr.pgua.eic.projetovendas.models.Produto;

public class JDBCProdutoDAO implements ProdutoDAO {

    @Override
    public boolean cadastrar(Produto p) throws Exception {
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/app", "user", "user12345");

        String sql = "INSERT INTO produtos(nome,descricao,quantidadeEstoque,valor) VALUES (?,?,?,?)";
        
        PreparedStatement pstmt = con.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);

        pstmt.setString(1, p.getNome());
        pstmt.setString(2, p.getDescricao());
        pstmt.setInt(3,p.getQuantidadeEstoque());
        pstmt.setDouble(4, p.getValor());
        
        pstmt.execute();

        // pegando o id gerado para o produto
        ResultSet rsId = pstmt.getGeneratedKeys();
        rsId.next();
        int id = rsId.getInt(1);

        p.setId(id);

        rsId.close();
        pstmt.close();
        con.close();

        return true;
    }

    @Override
    public boolean atualizar(int id, Produto p) throws Exception {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean remover(Produto p) throws Exception {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public ArrayList<Produto> lista() throws Exception {
        ArrayList<Produto> lista = new ArrayList<>();
        
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/app", "user", "user12345");

        String sql = "SELECT * FROM produtos";
        
        PreparedStatement pstmt = con.prepareStatement(sql);

        ResultSet rs = pstmt.executeQuery();

        while(rs.next()){
            int id = rs.getInt("id");
            String nome = rs.getString("nome");
            String descricao = rs.getString("descricao");
            int quantidadeEstoque = rs.getInt("quantidadeEstoque");
            double valor = rs.getDouble("valor");

            Produto p = new Produto(nome, descricao, quantidadeEstoque, valor);

            lista.add(p);
        }

        rs.close();
        pstmt.close();
        con.close();

        return lista;

    }

}
