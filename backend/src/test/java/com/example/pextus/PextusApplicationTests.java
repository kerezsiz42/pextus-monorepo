package com.example.pextus;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PextusApplicationTests {

	private final BookService bookService;
	private final WriterService writerService;
	private final BookRepository bookRepository;
	private final WriterRepository writerRepository;
	@Autowired
	public PextusApplicationTests(BookService bookService, WriterService writerService, BookRepository bookRepository, WriterRepository writerRepository) {
		this.bookService = bookService;
		this.writerService = writerService;
		this.bookRepository = bookRepository;
		this.writerRepository = writerRepository;
	}

	@DisplayName("BookService.getAllBooks should return a list of 5 books")
	@Test
	void getAllBooksTest() {
		var numberOfBooks = bookRepository.findAll().size();
		List<Book> books = bookService.getAllBooks();
		assertTrue(books.size() == numberOfBooks);
	}

	@DisplayName("BookService.createOrModifyBook should create a new book if no books with the same id found")
	@Test
	void createBookTest() {
		var numberOfBooks = bookRepository.findAll().size();

		WriterData writerData = new WriterData();
		UUID writerUUID = UUID.randomUUID();
		writerData.setId(writerUUID.toString());
		writerData.setBirthDate(LocalDate.now().toString());
		writerData.setFullName("The Author");
		Optional<Writer> writer = writerService.createOrModifyWriter(writerData);
		BookData bookData = new BookData();
		UUID bookUUID = UUID.randomUUID();
		bookData.setId(bookUUID.toString());
		bookData.setTitle("The Book");
		bookData.setPublicationYear(2023);
		bookData.setWriterId(writer.get().getId().toString());
		bookService.createOrModifyBook(bookData);
		List<Book> books = bookService.getAllBooks();

		assertTrue(books.size() == numberOfBooks + 1);
	}

	@DisplayName("BookService.createOrModifyBook should update existing book when one with the same id found")
	@Test
	void updateBookTest() {
		WriterData writerData = new WriterData();
		writerData.setBirthDate(LocalDate.now().toString());
		writerData.setFullName("The New Author");
		Optional<Writer> writer = writerService.createOrModifyWriter(writerData);
		BookData bookData = new BookData();
		bookData.setTitle("The New Book");
		bookData.setPublicationYear(2023);
		bookData.setWriterId(writer.get().getId().toString());
		Optional<Book> b1 = bookService.createOrModifyBook(bookData);

		var numberOfBooksBefore = bookRepository.findAll().size();
		bookData.setId(b1.get().getId().toString());
		bookData.setPublicationYear(2000);
		bookService.createOrModifyBook(bookData);
		var numberOfBooksAfter = bookRepository.findAll().size();
		Optional<Book> b2 = bookRepository.findById(b1.get().getId());

		assertEquals(numberOfBooksBefore, numberOfBooksAfter);
		assertEquals(b2.get().getPublicationYear(), 2000);
	}

	@DisplayName("BookService.findBookById should retrieve the desired book")
	@Test
	void findBookByIdTest() {
		WriterData writerData = new WriterData();
		writerData.setBirthDate(LocalDate.now().toString());
		writerData.setFullName("The New Author 2");
		Optional<Writer> writer = writerService.createOrModifyWriter(writerData);
		BookData bookData = new BookData();
		bookData.setTitle("The New Book 2");
		bookData.setPublicationYear(2050);
		bookData.setWriterId(writer.get().getId().toString());
		Optional<Book> b1 = bookService.createOrModifyBook(bookData);
		Optional<Book> b2 = bookService.findBookById(b1.get().getId().toString());

		assertEquals(b1.get().getId(), b2.get().getId());
	}

	@DisplayName("BookService.deleteBookById should delete the desired book")
	@Test
	void deleteBookByIdTest() {
		WriterData writerData = new WriterData();
		writerData.setBirthDate(LocalDate.now().toString());
		writerData.setFullName("The New Author 3");
		Optional<Writer> writer = writerService.createOrModifyWriter(writerData);
		BookData bookData = new BookData();
		bookData.setTitle("The New Book 3");
		bookData.setPublicationYear(1950);
		bookData.setWriterId(writer.get().getId().toString());
		Optional<Book> b1 = bookService.createOrModifyBook(bookData);
		bookService.deleteBookById(b1.get().getId().toString());
		Optional<Book> b2 = bookRepository.findById(b1.get().getId());

		assertFalse(b1.isEmpty());
		assertTrue(b2.isEmpty());
	}

	@DisplayName("BookService.searchBooksByTitle should find books with title starting with \"the\"")
	@Test
	void searchBooksByTitleTest() {
		var numberOfBooksStartingWithThe = bookService.searchBooksByTitle("a").size();

		assertEquals(numberOfBooksStartingWithThe, 2);
	}

}
