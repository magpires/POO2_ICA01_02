package visao;

import dao.IDAO;
import dao.ProdutoDAO;
import java.util.List;
import java.util.Scanner;
import modelo.Produto;

public class Programa {

    public static void cadastra() {
        //Criamos um objeto do tipo Produto que será passado como parâmetro para
        //o método de cadastro de produtos e criamos outro objeto do tipo IDAO
        //e instanciamos ele como ProdutoDAO para usarmos os métodos da
        //interface IDAO.
        Scanner sc = new Scanner(System.in);
        Produto produto = new Produto();
        IDAO dao = new ProdutoDAO();

        System.out.println("--------------------------------");
        System.out.println("Digite o nome do produto");
        produto.setNome(sc.nextLine());
        System.out.println("Digite o preço do produto");
        produto.setPreco(sc.nextDouble());

        dao.persist(produto);
        System.out.println("--------------------------------");
    }

    public static void apaga() {
        //Criamos um objeto do tipo Long que será passado para o método que
        //deleta do banco pelo id.
        Scanner sc = new Scanner(System.in);
        Long id;
        IDAO dao = new ProdutoDAO();

        System.out.println("--------------------------------");
        System.out.println("Digite o id do produto a ser excluido:");
        id = sc.nextLong();

        dao.delete(id);
        System.out.println("--------------------------------");
    }

    public static void exibePorId() {
        //Criamos um objeto do tipo Long que será passado para o método que
        //retorna o objeto do banco pelo id
        Scanner sc = new Scanner(System.in);
        Long id;
        IDAO dao = new ProdutoDAO();

        System.out.println("--------------------------------");
        System.out.println("Digite o id do produto");
        id = sc.nextLong();
        //Criamos um objeto do tipo Produto que vai receber o produto retornado
        //pelo método get da interface IDAO.
        Produto produto = (Produto) dao.get(id);

        //Caso o produto retornado seja diferente de nulo, vamos exibir as
        //informações deste produto na tela
        if (produto != null) {
            System.out.println("--------------------------------");
            System.out.println("Informações do produto!");
            System.out.println("ID: " + produto.getId());
            System.out.println("Nome: " + produto.getNome());
            System.out.println("Preco: " + produto.getPreco());
        }
        System.out.println("--------------------------------");
    }

    public static void atualiza() {
        //Criamos um objeto do tipo Produto que será passado como parâmetro para
        //o método que atualiza os produtos e criamos outro objeto do tipo IDAO
        //e instanciamos ele como ProdutoDAO para usarmos os métodos da
        //interface IDAO
        Scanner sc = new Scanner(System.in);
        Produto produto = new Produto();
        IDAO dao = new ProdutoDAO();

        System.out.println("--------------------------------");
        System.out.println("Digite o id do produto a ser atualizado:");
        produto.setId(sc.nextLong());
        //Criamos um objeto auxiliar do tipo Produto para exebirmos na tela os
        //dados dos produtos a ser modificado e também para verificar se o
        //produto retornado pelo método get não está nulo, pois caso este esteja
        //significa que o produto não foi encontrado. Logo, não há porque editar
        //algo que não existe no banco.
        Produto produtoAUX = (Produto) dao.get(produto.getId());
        if (produtoAUX != null) {
            System.out.println("--------------------------------");
            System.out.println("Informações do produto!");
            System.out.println("ID: " + produtoAUX.getId());
            System.out.println("Nome: " + produtoAUX.getNome());
            System.out.println("Preco: " + produtoAUX.getPreco());

            sc.nextLine();
            System.out.println("digite o novo nome do produto");
            produto.setNome(sc.nextLine());
            System.out.println("digite o novo preço do produto");
            produto.setPreco(sc.nextDouble());
            dao.update(produto);
        }
        System.out.println("--------------------------------");
    }
    
    public static void exibeTodos() {
        IDAO dao = new ProdutoDAO();
        
        //Criamos um objeto do tipo List que vai receber todos os produtos do
        //método getAll e vai exibí-los na tela
        List produtos = dao.getAll();
        
        System.out.println("--------------------------------");
        System.out.println("Produtos cadastrados");
        for (Object o : produtos) {
            System.out.println(((Produto) o));
        }
        System.out.println("--------------------------------");
    }

    public static void main(String[] args) {
        util.DBUtil.createTable(); //Criamos as tabelas da aplicação

        int opcao = -1;

        System.out.println("Bem vindo ao cadastro de produtos para venda!");
        System.out.println("Para começar, escolha uma das opções abaixo.");
        System.out.println("--------------------------------");
        while (opcao != 0) {
            System.out.println("1 - Cadastrar");
            System.out.println("2 - Excluir por ID");
            System.out.println("3 - Exibir dados por ID");
            System.out.println("4 - Alterar dados");
            System.out.println("5 - Exibir todos os produtos");
            System.out.println("0 - Sair");

            Scanner sc = new Scanner(System.in);
            opcao = sc.nextInt();

            if (opcao == 1) {
                cadastra();
            } else if (opcao == 2) {
                apaga();
            } else if (opcao == 3) {
                exibePorId();
            } else if (opcao == 4) {
                atualiza();
            } else if (opcao == 5) {
                exibeTodos();
            } else if (opcao == 0) {
                System.out.println("Obrigado por utilizar o sistema!");
            } else {
                System.out.println("Opção inválida");
            }
        }
    }
}
