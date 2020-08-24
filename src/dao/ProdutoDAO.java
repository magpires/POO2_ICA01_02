package dao;

import factory.ConnectionFactory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import modelo.Produto;

public class ProdutoDAO implements IDAO {

    @Override
    public void persist(Object o) {
        //Criamos um objeto do tipo PreparedStatement para receber os
        //comandos SQL que serão preparados antes de serem executados.
        PreparedStatement ps = null;
        //Criamos a conexão com o banco.
        Connection conexao = ConnectionFactory.getConnection();

        try {
            //Criamos o comando que será preparado posteriormente.
            String comando = "insert into vendas.produto (nome, preco) values( ?,  ?);";

            //Aqui passamos o comando e a conexão ao banco de dados para a 
            //variável PreparedStatement.
            ps = (PreparedStatement) conexao.prepareStatement(comando);

            //Preparamos o ps subistituindo os "?" pelos valores do objeto
            //passado como parâmetro.
            ps.setString(1, ((Produto) o).getNome());
            ps.setDouble(2, ((Produto) o).getPreco());

            //Aqui nós verificamos se o executeUpdate retornou um inteiro maior
            //que zero. Caso positivo, o produto foi cadastrado. Caso negativo,
            //um erro inesperado ocorreu.
            if (ps.executeUpdate() > 0) {
                System.out.println("Produto cadastrado!");
            } else {
                System.err.println("Erro inesperado ao cadastrar produto!");
            }
        } catch (SQLException ex) {
            try {
                //Fazemos um rollback caso aconteça algum erro ao cadastrar o
                //produto no banco.
                conexao.rollback();
                System.err.println("Erro ao cadastrar o contato!");
            } catch (SQLException e) {
                System.out.println("Erro ao fazer o rollback");
            }
            throw new RuntimeException(ex);
        } finally {
            //No finally, a conexão com o banco é encerrada assim que a
            //tentativa de cadastro é feita.
            try {
                if (ps != null) {
                    ps.close();
                }
                if (conexao != null) {
                    conexao.close();
                }
            } catch (SQLException e) {
                System.err.println("Erro ao fechar conexão!");
            }
        }
    }

    @Override
    public void delete(Object id) {
        //Criamos um objeto do tipo PreparedStatement para receber os
        //comandos SQL que serão preparados antes de serem executados.
        PreparedStatement ps = null;
        //Criamos a conexão com o banco.
        Connection conexao = ConnectionFactory.getConnection();

        try {
            String comando = "DELETE FROM vendas.produto WHERE produto.id = ?";

            ps = (PreparedStatement) conexao.prepareStatement(comando);

            //Assim como no método que insere os cadastros no banco, vamos
            //preparar o ps novamente.
            ps.setLong(1, ((Long) id));

            //Se o executeUpdate retornar um inteiro maior que zero, isso
            //significa que o banco encontrou o produto e o eliminou. Caso seja
            //igual a zero, significa que nenhum item do banco foi eliminado.
            if (ps.executeUpdate() > 0) {
                System.out.println("Produto excluido!");
            } else {
                System.err.println("Produto não encontrado!");
            }
        } catch (SQLException e) {
            System.err.println("Erro ao apagar!");
        } finally {
            //No finally, a conexão com o banco é encerrada assim que a
            //tentativa de cadastro é feita.
            try {
                if (ps != null) {
                    ps.close();
                }
                if (conexao != null) {
                    conexao.close();
                }
            } catch (SQLException e) {
                System.err.println("Erro ao fechar conexão!");
            }
        }
    }

    @Override
    public void update(Object o) {
        PreparedStatement ps = null;
        Connection conexao = ConnectionFactory.getConnection();

        try {
            String comando = "UPDATE vendas.produto SET nome = ?, preco = ? WHERE id = ?";

            ps = (PreparedStatement) conexao.prepareStatement(comando);

            ps.setString(1, ((Produto) o).getNome());
            ps.setDouble(2, ((Produto) o).getPreco());
            ps.setLong(3, ((Produto) o).getId());

            if (ps.executeUpdate() > 0) {
                System.out.println("Produto modificado!");
            } else {
                System.err.println("Produto não encontrado!");
            }
        } catch (SQLException e) {
            System.err.println("Erro ao atualizar o produto!");
        } finally {
            //No finally, a conexão com o banco é encerrada assim que a
            //tentativa de cadastro é feita.
            try {
                if (ps != null) {
                    ps.close();
                }
                if (conexao != null) {
                    conexao.close();
                }
            } catch (SQLException e) {
                System.err.println("Erro ao fechar conexão!");
            }
        }
    }

    @Override
    public Object get(Object id) {
        PreparedStatement ps = null;
        Connection conexao = ConnectionFactory.getConnection();

        try {
            String comando = "SELECT * FROM vendas.produto WHERE id = ?;";

            ps = (PreparedStatement) conexao.prepareStatement(comando);

            ps.setLong(1, ((Long) id));

            ResultSet rs = ps.executeQuery();

            //Criamos um produto que será retornado para que seus dados sejam
            //exibidos na tela!
            Produto produto = new Produto();

            while (rs.next()) {
                produto.setId(rs.getLong("id"));
                produto.setNome(rs.getString("nome"));
                produto.setPreco(rs.getDouble("preco"));
            }

            //O método só irá retornar o produto, caso este realmente seja
            //um produto que veio do banco de dados. Para isso, verificamos
            //se seu ID é maior que zero. Uma vez que um ID auto increment
            //nunca é salvo no banco com valor 0.
            if (produto.getId() > 0) {
                return produto;
            } else {
                System.err.println("Produto não encontrado");
                //Se o produto não for encontrado no banco de dados, retornamos
                //nulo.
                return null;
            }
        } catch (SQLException e) {
            System.err.println("Erro na consulta!");
            return null;
        } finally {
            //No finally, a conexão com o banco é encerrada assim que a
            //tentativa de cadastro é feita.
            try {
                if (ps != null) {
                    ps.close();
                }
                if (conexao != null) {
                    conexao.close();
                }
            } catch (SQLException e) {
                System.err.println("Erro ao fechar conexão!");
            }
        }
    }

    @Override
    public List getAll() {
        //Vamos criar uma lista que irá ser retornada com os produtos coletados
        //do banco.
        List lista = new ArrayList();
        //Aqui temos um comando SQL que vai retornar para nós os produtos
        //cadastrados no banco ordenados por nome.
        String comando = "select id, nome, preco "
                + " from produto "
                + " order by nome ";
        Connection con = null;
        try {
            con = ConnectionFactory.getConnection();
            PreparedStatement ps = con.prepareStatement(comando);
            ResultSet rs = ps.executeQuery();
            
            //Neste laço, enquanto houver produtos no resultset, estes serão
            //inseridos na lista criada anteriormente.
            while (rs.next()) {
                //Criamos o produto, setamos os seus valores de acordo com os
                //valores presentes no resultset e em seguida salvamos ele na
                //lista.
                Produto p = new Produto();
                p.setId(rs.getLong(1));
                p.setNome(rs.getString(2));
                p.setPreco(rs.getDouble(3));
                lista.add(p);
            }

        } catch (Exception ex) {
            System.out.println("Erro ao coletar dados do banco");
        } finally {
            try {
                con.close();
            } catch (Exception e) {

            }
        }

        //Por fim, retornamos a lista.
        return lista;
    }
}
