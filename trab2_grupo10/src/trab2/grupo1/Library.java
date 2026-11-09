package trab2.grupo1;

import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class Library {
    /**
     * Classe interna que representa uma entrada no catálogo da biblioteca, contendo um livro e a quantidade disponível.
     */
    public static class CatalogEntry {
        private final Book book;
        private int quantity;

        public CatalogEntry(Book book, int quantity) {
            this.book = book;
            this.quantity = quantity;
        }

        /**
         * Método accessor para o campo book.
         * @return O livro associado a esta entrada do catálogo.
         */
        public Book getBook() {
            return book;
        }

        /**
         * Método accessor para o campo quantity.
         * @return A quantidade disponível do livro associado a esta entrada do catálogo.
         */
        public int getQuantity() {
            return quantity;
        }

        /**
         * Método mutator para o campo quantity.
         * @param quantity A nova quantidade a ser definida.
         */
        public void setQuantity(int quantity) {
            this.quantity = quantity;
        }
    }

    // Campo correspondente ao catálogo da biblioteca: ISBN -> entrada de catálogo.
    private final Map<String, CatalogEntry> catalog = new HashMap<>();

    /**
     * Adiciona um livro à biblioteca. Se o livro já existir, atualiza a quantidade disponível; se não existir, cria uma entrada no catálogo.
     * @param book O livro a ser adicionado.
     * @param quantity A quantidade de livros a ser adicionado.
     * @throws LibraryException Se a quantidade de livros especificada for inferior ou igual a 0
     */
    public void addBook(Book book, int quantity) {
        if (quantity <= 0)
            throw new LibraryException("A quantidade deve ser superior a 0: " + quantity);
        catalog.merge(book.isbn(), new CatalogEntry(book, quantity), (existing, _) -> {
            existing.setQuantity(existing.getQuantity() + quantity);
            return existing;
        });
    }

    /**
     * Adiciona múltiplos livros à biblioteca. Se algum livro já existir, atualiza a quantidade disponível; se não existir, cria uma entrada no catálogo.
     * @param books Os livros a serem adicionados.
     */
    public void addAllBooks(Book... books) {
        for (Book book : books)
            addBook(book, 1);
    }

    /**
     * Remove um livro da biblioteca com base no ISBN. Se a quantidade disponível for maior que 1, apenas diminui a quantidade em 1; caso contrário, remove o livro completamente.
     * @param isbn O ISBN do livro a ser removido.
     * @return O livro removido.
     * @throws LibraryException Se o livro com o ISBN especificado não for encontrado na biblioteca.
     */
    public Book removeBook(String isbn) {
        CatalogEntry entry = catalog.get(isbn);
        if (entry == null)
            throw new LibraryException("Livro não encontrado: " + isbn);
        if (entry.getQuantity() > 1)
            entry.setQuantity(entry.getQuantity() - 1);
        else
            catalog.remove(isbn);
        return entry.getBook();
    }

    /**
     * Remove um livro da biblioteca com base no ISBN, independentemente da quantidade disponível.
     * @param isbn O ISBN do livro a ser removido.
     * @throws LibraryException Se o livro com o ISBN especificado não for encontrado na biblioteca.
     */
    public void deleteBook(String isbn) {
        if (catalog.remove(isbn) == null)
            throw new LibraryException("Livro não encontrado: " + isbn);
    }

    /**
     * Retorna o livro com um determinado ISBN.
     * @param isbn O ISBN do livro a ser retornado.
     * @return O livro com o ISBN especificado.
     * @throws LibraryException Se o livro com o ISBN especificado não for encontrado na biblioteca.
     */
    public Book getBookByISBN(String isbn) {
        CatalogEntry entry = catalog.get(isbn);
        if (entry == null)
            throw new LibraryException("Livro não encontrado: " + isbn);
        return entry.getBook();
    }

    /**
     * Retorna a quantidade disponível de um livro com um determinado ISBN.
     * @param isbn O ISBN do livro cuja quantidade disponível será retornada.
     * @return A quantidade disponível do livro. Retorna 0 se o livro não for encontrado na biblioteca.
     */
    public int getBookQuantity(String isbn) {
        CatalogEntry entry = catalog.get(isbn);
        return entry == null ? 0 : entry.getQuantity();
    }

    /**
     * Retorna a quantidade total de livros disponíveis que obedecem a um determinado filtro.
     * @param filter O filtro a ser aplicado.
     * @return A quantidade total de livros disponíveis que atendem aos critérios.
     */
    private int getBooksQuantity(Predicate<CatalogEntry> filter) {
        return catalog.values().stream()
                .filter(filter)
                .mapToInt(CatalogEntry::getQuantity)
                .sum();
    }

    /**
     * Retorna a quantidade total de livros disponíveis com um determinado título e autor, sem distinguir maiúsculas de minúsculas.
     * @param title O título dos livros cuja quantidade será retornada.
     * @param author O autor dos livros cuja quantidade será retornada.
     * @return A quantidade total de livros disponíveis com o título e autor especificados.
     */
    public int getBooksQuantity(String title, String author) {
        return getBooksQuantity(e -> e.getBook().title().equalsIgnoreCase(title)
                && e.getBook().author().equalsIgnoreCase(author));
    }

    /**
     * Retorna a quantidade total de livros disponíveis publicados num determinado ano e editados por um determinado editor, sem distinguir maiúsculas de minúsculas.
     * @param year O ano de publicação dos livros cuja quantidade será retornada.
     * @param editor O editor dos livros cuja quantidade será retornada.
     * @return A quantidade total de livros disponíveis com o ano e editor especificados.
     */
    public int getBooksQuantity(int year, String editor) {
        return getBooksQuantity(e -> e.getBook().year() == year
                && e.getBook().editor().equalsIgnoreCase(editor));
    }

    /**
     * Retorna a lista de livros que obedecem a um determinado filtro, mapeando-os para o tipo Book.
     * @param filter O filtro a ser aplicado.
     * @return A lista de livros que atendem aos critérios.
     */
    private List<Book> listBooks(Predicate<CatalogEntry> filter) {
        return catalog.values().stream()
                .filter(filter)
                .map(CatalogEntry::getBook)
                .toList();
    }

    /**
     * Retorna lista de todos os livros disponíveis na biblioteca, independentemente da categoria.
     */
    public List<Book> listAllBooks() {
        return listBooks(_ -> true);
    }

    /**
     * Retorna a lista de livros de uma determinada categoria (BookGenre).
     * @param genre A categoria de livros a ser listada.
     * @return A lista de livros da categoria especificada.
     */
    public List<Book> listBooksByGenre(BookGenre genre) {
        return listBooks(e -> e.getBook().genre() == genre);
    }

    /**
     * Retorna a lista de livros cujo título contenha uma determinada palavra-chave.
     * A comparação entre palavras-chave é insensível a maiúsculas e minúsculas.
     * @param keyword A palavra-chave a ser buscada nos títulos dos livros.
     * @return A lista de livros cujo título contenha a palavra-chave especificada.
     */
    public List<Book> listBooksByTitleKeyword(String keyword) {
        String key = keyword.toLowerCase();
        return listBooks(e -> e.getBook().title().toLowerCase().contains(key));
    }

    /**
     * Retorna a lista de livros que obedecem a um determinado filtro, mapeando-os para o tipo Book e ordenando-os conforme o comparador fornecido.
     * @param filter O filtro a ser aplicado.
     * @param sorter O comparador para ordenar os elementos.
     * @return A lista de livros que atendem aos critérios.
     */
    private List<Book> listBooks(Predicate<CatalogEntry> filter, Comparator<CatalogEntry> sorter) {
        return catalog.values().stream()
                .filter(filter)
                .sorted(sorter)
                .map(CatalogEntry::getBook)
                .toList();
    }

    /**
     * Retorna a lista de livros de um determinado título e autor, ordenados por ano de publicação, do mais recente para o mais antigo.
     * A comparação entre títulos e entre autores é insensível a maiúsculas e minúsculas.
     * @param title O título dos livros a serem listados.
     * @param author O autor dos livros a serem listados.
     * @return A lista de livros com o título e autor especificados, ordenados por ano de publicação.
     */
    public List<Book> listBooksByTitleAndAuthor(String title, String author) {
        return listBooks(
                e -> e.getBook().title().equalsIgnoreCase(title) && e.getBook().author().equalsIgnoreCase(author),
                Comparator.comparingInt((CatalogEntry e) -> e.getBook().year()).reversed());
    }

    /**
     * Retorna a lista de livros publicados num determinado ano, ordenados por autor, e, dentro do autor, ordenados pelo título.
     * @param year O ano dos livros a serem listados.
     * @return A lista de livros do ano especificado, ordenados por autor
     */
    public List<Book> listBooksByYear(int year) {
        return listBooks(
                e -> e.getBook().year() == year,
                Comparator.comparing((CatalogEntry e) -> e.getBook().author(), String.CASE_INSENSITIVE_ORDER)
                        .thenComparing(e -> e.getBook().title(), String.CASE_INSENSITIVE_ORDER));
    }

    /**
     * Método genérico que fatoriza o agrupamento de livros do catálogo.
     * @param groupedBooks Mapa (já criado pelo chamador) onde os grupos são acumulados; define o tipo de Map e a ordenação das chaves.
     * @param keyExtractor Função que obtém a chave de agrupamento a partir de um livro.
     * @param collectionFactory Fornecedor da coleção concreta de cada grupo (List, PriorityQueue, ...).
     * @param <K> Tipo da chave de agrupamento.
     * @param <C> Tipo da coleção de livros de cada grupo.
     * @return O mapa recebido, preenchido com os grupos.
     */
    private <K, C extends Collection<Book>> Map<K, C> groupBooks(
            Map<K, C> groupedBooks, Function<Book, K> keyExtractor, Supplier<C> collectionFactory) {
        for (CatalogEntry entry : catalog.values()) {
            Book book = entry.getBook();
            groupedBooks.computeIfAbsent(keyExtractor.apply(book), _ -> collectionFactory.get()).add(book);
        }
        return groupedBooks;
    }

    /**
     * Agrupa os livros por categoria (BookGenre).
     * @return Um mapa onde a chave é o género do livro e o valor é a lista de livros desse gênero.
     */
    public Map<BookGenre, List<Book>> groupBooksByGenre() {
        return groupBooks(new HashMap<>(), Book::genre, ArrayList::new);
    }

    /**
     * Agrupa os livros por autor, listando os autores em ordem alfabética (sem distinguir maiúsculas de minúsculas) e, para cada autor,
     * lista os livros publicados por ele, ordenados por ano de publicação, do mais recente para o mais antigo.
     * @return Um mapa onde a chave é o autor do livro e o valor é a fila de prioridade de livros desse autor.
     */
    public Map<String, PriorityQueue<Book>> groupBooksByAuthor() {
        return groupBooks(new TreeMap<>(String.CASE_INSENSITIVE_ORDER), Book::author,
                () -> new PriorityQueue<>(Comparator.comparingInt(Book::year).reversed()));
    }
}
