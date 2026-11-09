package trab2.grupo1;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class LibraryTest {
    private Library library;
    private final Book[] books = {
            /*00*/new Book("Ensaio sobre a Cegueira", "José Saramago", 2021,"Porto Editora", "9789720039903", BookGenre.FICTION),
            /*01*/new Book("Ensaio sobre a Cegueira", "José Saramago", 2026,"Porto Editora", "9789720056566", BookGenre.FICTION),
            /*02*/new Book("Memorial do Convento", "José Saramago", 2014,"Porto Editora", "9789720046710", BookGenre.FICTION),
            /*03*/new Book("Memorial do Convento", "José Saramago", 2021,"Porto Editora", "9789720039927", BookGenre.FICTION),
            /*04*/new Book("O Ano da Morte de Ricardo Reis", "José Saramago", 2021,"Porto Editora", "9789720039910", BookGenre.FICTION),
            /*05*/new Book("O Evangelho segundo Jesus Cristo", "José Saramago", 2021,"Porto Editora", "9789720039941", BookGenre.FICTION),
            /*06*/new Book("Ensaio sobre a Lucidez", "José Saramago", 2014,"Porto Editora", "9789720046529", BookGenre.FICTION),
            /*07*/new Book("A Fórmula de Deus", "José Rodrigues dos Santos", 2006,"Gradiva", "9789896161392", BookGenre.FICTION),
            /*08*/new Book("O Sétimo Selo", "José Rodrigues dos Santos", 2007,"Gradiva", "9789896162085", BookGenre.FICTION),
            /*09*/new Book("O Codex 632", "José Rodrigues dos Santos", 2005,"Gradiva", "9789896160722", BookGenre.FICTION),
            /*10*/new Book("O Código da Vinci", "Dan Brown", 2025,"Planeta", "9789895870707", BookGenre.FICTION),
            /*11*/new Book("Anjos e Demónios", "Dan Brown", 2013,"11 X 17", "9789722526753", BookGenre.FICTION),
            /*12*/new Book("Inferno", "Dan Brown", 2013,"Bertrand Editora", "9789722526920", BookGenre.FICTION),
            /*13*/new Book("Inferno", "Dan Brown", 2016,"11 X 17", "9789722532594", BookGenre.FICTION),
            /*14*/new Book("Breve História do Tempo", "Stephen Hawking", 2000,"Gradiva", "9789726620105", BookGenre.SCIENCE),
            /*15*/new Book("Cosmos", "Carl Sagan", 1996,"Gradiva", "9789726624523", BookGenre.SCIENCE),
            /*16*/new Book("Cosmos", "Carl Sagan", 2001,"Gradiva", "9789726628071", BookGenre.SCIENCE),
            /*17*/new Book("O Gene Egoísta", "Richard Dawkins", 2018,"Gradiva", "9789896168285", BookGenre.SCIENCE),
            /*18*/new Book("Orgulho e Preconceito", "Jane Austen", 2014,"Editorial Presença", "9789722352291", BookGenre.ROMANCE),
            /*19*/new Book("Amor de Perdição", "Camilo Castelo Branco", 1998,"Livraria Civilização Editora", "9789722615129", BookGenre.ROMANCE),
            /*20*/new Book("Os Maias", "Eça de Queirós", 2001,"Dom Quixote", "9789722019989", BookGenre.ROMANCE),
            /*21*/new Book("Drácula", "Bram Stoker", 2009,"Publicações Europa-América", "9789727312450", BookGenre.HORROR),
            /*22*/new Book("Drácula", "Bram Stoker", 2014,"Edições ASA", "9789892326313", BookGenre.HORROR),
            /*23*/new Book("Frankenstein", "Mary Shelley", 2022,"Saída de Emergência", "9789897734427", BookGenre.HORROR),
            /*24*/new Book("Steve Jobs", "Walter Isaacson", 2011,"Bnomics", "9789896721213", BookGenre.BIOGRAPHY),
            /*25*/new Book("Steve Jobs", "Walter Isaacson", 2021,"Lua de Papel", "9789897844317", BookGenre.BIOGRAPHY),
            /*26*/new Book("Einstein", "Walter Isaacson", 2020,"Elsinore", "9789895640768", BookGenre.BIOGRAPHY),
            /*27*/new Book("Um Longo Caminho para a Liberdade", "Nelson Mandela", 2020,"Objectiva", "9789895495108", BookGenre.BIOGRAPHY),
            /*28*/new Book("Sapiens — História Breve da Humanidade", "Yuval Noah Harari", 2020,"Elsinore", "9789895640188", BookGenre.HISTORY),
            /*29*/new Book("História de Portugal","Rui Ramos, Bernardo Vasconcelos e Sousa e Nuno Gonçalo Monteiro", 2012,"A Esfera dos Livros", "9789896263669", BookGenre.HISTORY),
            /*30*/new Book("SPQR — Uma História da Roma Antiga", "Mary Beard", 2016,"Bertrand Editora", "9789722532945", BookGenre.HISTORY),
            /*31*/new Book("SPQR — Uma História da Roma Antiga", "Mary Beard", 2025,"Crítica", "9789899103412", BookGenre.HISTORY),
            /*32*/new Book("Mensagem", "Fernando Pessoa", 2010,"Porto Editora", "9789720049759", BookGenre.POETRY),
            /*33*/new Book("Livro Sexto", "Sophia de Mello Breyner Andresen", 2003,"Editorial Caminho", "9789722115919", BookGenre.POETRY),
            /*34*/new Book("Livro Sexto", "Sophia de Mello Breyner Andresen", 2014,"Assírio & Alvim", "9789723717839", BookGenre.POETRY),
            /*35*/new Book("Os Lusíadas", "Luís de Camões", 2025,"Imprensa Nacional-Casa da Moeda", "9789722732758", BookGenre.POETRY),
    };

    @BeforeEach
    void setUp() {
        library = new Library();
    }

    @Test
    void testEquals() {
        assertEquals(
                books[0],
                new Book("dummy for equals", "dummy for equals", 0, "dummy for equals", books[0].isbn(), null)
        );
        assertNotEquals(
                books[0],
                new Book(books[0].title(), books[0].author(), books[0].year(), books[0].editor(), books[0].isbn()+1, books[0].genre())
        );
    }

    @Test
    void testHashCode() {
        assertEquals(
                books[0].hashCode(),
                (new Book("dummy for equals", "dummy for equals", 0, "dummy for equals", books[0].isbn(), null)).hashCode()
        );
    }

    @Test
    void addBook() {
        library.addBook(books[0], 5);
        assertEquals(5, library.getBookQuantity(books[0].isbn()));
        assertThrows(LibraryException.class, () -> library.addBook(books[1], 0));
        assertThrows(LibraryException.class, () -> library.addBook(books[1], -1));
    }

    @Test
    void addAllBooks() {
        library.addAllBooks(books);
        assertEquals(books.length, library.listAllBooks().size());
    }

    @Test
    void removeBook() {
        library.addBook(books[0], 2);
        assertDoesNotThrow(() -> library.removeBook(books[0].isbn()));
        assertEquals(1, library.getBookQuantity(books[0].isbn()));
        assertDoesNotThrow(() -> library.removeBook(books[0].isbn()));
        assertEquals(0, library.getBookQuantity(books[0].isbn()));
        assertThrows(LibraryException.class, () -> library.removeBook(books[0].isbn()));
    }

    @Test
    void deleteBook() {
        library.addBook(books[0], 5);
        assertDoesNotThrow(() -> library.deleteBook(books[0].isbn()));
        assertEquals(0, library.getBookQuantity(books[0].isbn()));
        assertThrows(LibraryException.class, () -> library.deleteBook(books[0].isbn()));
    }

    @Test
    void getBookByISBN() {
        library.addAllBooks(books);
        Book book = assertDoesNotThrow(() -> library.getBookByISBN(books[0].isbn()));
        assertEquals(books[0], book);
        assertThrows(LibraryException.class, () -> library.getBookByISBN("invalid_isbn"));
    }

    @Test
    void getBookQuantityByISBN() {
        library.addBook(books[0], 3);
        assertEquals(3, library.getBookQuantity(books[0].isbn()));
        library.removeBook(books[0].isbn());
        assertEquals(2, library.getBookQuantity(books[0].isbn()));
        library.addBook(books[0], 1);
        assertEquals(3, library.getBookQuantity(books[0].isbn()));
        assertEquals(0, library.getBookQuantity("invalid_isbn"));
    }

    @Test
    void getBooksQuantityByTitleAndAuthor() {
        library.addAllBooks(books);
        assertEquals(1, library.getBooksQuantity("Sapiens — História Breve da Humanidade", "Yuval Noah Harari"));
        assertEquals(2, library.getBooksQuantity("Cosmos", "Carl Sagan"));
        assertEquals(0, library.getBooksQuantity("Nonexistent Book", "José Saramago"));
        assertEquals(0, library.getBooksQuantity("Ensaio sobre a Cegueira", "Nonexistent Author"));
    }

    @Test
    void getBooksQuantityByYearAndEditor() {
        library.addAllBooks(books);
        assertEquals(2, library.getBooksQuantity(2014, "Porto Editora")); // Memorial do Convento (2014) and Ensaio sobre a Lucidez (2014)
        assertEquals(4, library.getBooksQuantity(2021, "Porto Editora")); // Ensaio sobre a Cegueira (2021), Memorial do Convento (2021), O Ano da Morte de Ricardo Reis (2021), O Evangelho segundo Jesus Cristo (2021)
        assertEquals(0, library.getBooksQuantity(2025, "Porto Editora")); // No books from 2025 by Porto Editora
    }

    @Test
    void listBooksByGenre() {
        library.addAllBooks(books);
        List<Book> historyBooks = library.listBooksByGenre(BookGenre.HISTORY);
        assertEquals(4, historyBooks.size());
        historyBooks.forEach(book -> assertEquals(BookGenre.HISTORY, book.genre()));
        assertTrue(historyBooks.contains(books[28])); // Sapiens
        assertTrue(historyBooks.contains(books[29])); // História de Portugal
        assertTrue(historyBooks.contains(books[30])); // SPQR — Uma História da Roma Antiga
        assertTrue(historyBooks.contains(books[31])); // SPQR — Uma História da Roma Antiga (2025 edition)
        List<Book> childrenBooks = library.listBooksByGenre(BookGenre.CHILDREN);
        assertEquals(0, childrenBooks.size());
    }

    @Test
    void listBooksByTitleKeyword() {
        library.addAllBooks(books);
        assertEquals(5, library.listBooksByTitleKeyword("os").size());
        assertEquals(0, library.listBooksByTitleKeyword("nonexistent").size());
    }

    @Test
    void listBooksByTitleAndAuthor() {
        library.addAllBooks(books);
        List<Book> specificBooks = library.listBooksByTitleAndAuthor("Ensaio sobre a Cegueira", "José Saramago");
        assertEquals(2, specificBooks.size());
        assertTrue(specificBooks.contains(books[0]));
        assertTrue(specificBooks.contains(books[1]));
        List<Book> noTitleBooks = library.listBooksByTitleAndAuthor("Nonexistent Title", "José Saramago");
        assertEquals(0, noTitleBooks.size());
        List<Book> noAuthorBooks = library.listBooksByTitleAndAuthor("Ensaio sobre a Cegueira", "Nonexistent Author");
        assertEquals(0, noAuthorBooks.size());
        List<Book> noMatchBooks = library.listBooksByTitleAndAuthor("Nonexistent Title", "Nonexistent Author");
        assertEquals(0, noMatchBooks.size());
    }

    @Test
    void listBooksByYear() {
        library.addAllBooks(books);
        List<Book> books2021 = library.listBooksByYear(2021);
        assertEquals(5, books2021.size());
        assertEquals("Ensaio sobre a Cegueira", books2021.getFirst().title());
        assertEquals("Memorial do Convento", books2021.get(1).title());
        assertEquals("O Ano da Morte de Ricardo Reis", books2021.get(2).title());
        assertEquals("O Evangelho segundo Jesus Cristo", books2021.get(3).title());
        assertEquals("Steve Jobs", books2021.get(4).title());
        assertEquals(1, library.listBooksByYear(1996).size());
        assertEquals(0, library.listBooksByYear(1980).size());
    }

    @Test
    void groupBooksByGenre() {
        library.addAllBooks(books);
        Map<BookGenre, List<Book>> groupedBooks = library.groupBooksByGenre();
        assertEquals(7, groupedBooks.size());
        groupedBooks.forEach((genre, bookList) -> {
            bookList.forEach(book -> assertEquals(genre, book.genre()));
        });
    }

    /*
        Use todos os livros definidos no array books (da entrada 0 à entrada 35).
        A unidade de teste deve validar os seguintes aspetos:
        1. Os autores estão ordenados por ordem alfabética, sem distinguir maiúsculas de minúsculas;
        2. Os livros agrupados por autor pertencem ao autor;
        3. Os livros agrupados por autor estão ordenados por ano de publicação, do mais recente para o mais antigo.
        A iteração sobre cada grupo de livros deve ser realizada com o método forEach da interface Map. No entanto, para
        verificar a ordem dos livros e validar a ordem por ano de publicação, do mais recente para o mais antigo, já não
        poderá utilizar a operação forEach de Iterable.
     */
    @Test
    void groupBooksByAuthor() {
        library.addAllBooks(books);
        Map<String, PriorityQueue<Book>> grouped = library.groupBooksByAuthor();

        // Número de autores distintos e total de livros agrupados
        assertEquals(Arrays.stream(books).map(Book::author).distinct().count(), grouped.size());
        int[] totalBooks = {0};
        String[] previousAuthor = {null};

        grouped.forEach((author, queue) -> {
            // 1. Autores por ordem alfabética, sem distinguir maiúsculas de minúsculas
            if (previousAuthor[0] != null)
                assertTrue(previousAuthor[0].compareToIgnoreCase(author) < 0);
            previousAuthor[0] = author;

            // 2 e 3. Livros do autor, do ano mais recente para o mais antigo.
            // poll() devolve sempre o elemento de maior prioridade (iterar a fila não garante a ordem).
            int previousYear = Integer.MAX_VALUE;
            while (!queue.isEmpty()) {
                Book book = queue.poll();
                assertEquals(author, book.author());
                assertTrue(book.year() <= previousYear);
                previousYear = book.year();
                totalBooks[0]++;
            }
        });
        assertEquals(books.length, totalBooks[0]);
    }
}