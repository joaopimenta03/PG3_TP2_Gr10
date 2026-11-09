package trab2.grupo1;

import java.util.Objects;

public record Book(String title, String author, int year, String editor, String isbn, BookGenre genre) {
    /**
     * Verifica a igualdade entre este Book e o objeto recebido no parâmetro obj.
     * @param obj   referência do objeto a comparar.
     * @return true se os dois objetos tiverem o mesmo ISBN; false, caso contrário
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        return obj instanceof Book other && Objects.equals(isbn, other.isbn);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(isbn);
    }
}

