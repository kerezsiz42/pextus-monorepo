package com.example.pextus;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

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
		var numberOfBooksBefore = bookRepository.findAll().size();

		WriterData writerData = new WriterData();
		writerData.setBirthDate(LocalDate.now().toString());
		writerData.setFullName("The Author");
		Optional<Writer> writer = writerService.createOrModifyWriter(writerData);
		BookData bookData = new BookData();
		bookData.setTitle("The Book");
		bookData.setPublicationYear(2023);
		bookData.setWriterId(writer.get().getId().toString());
		bookService.createOrModifyBook(bookData);
		var numberOfBooksAfter = bookRepository.findAll().size();

		assertEquals(numberOfBooksBefore, numberOfBooksAfter - 1);
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

	@DisplayName("WriterService.getAllWriters should return a list of 3 writers")
	@Test
	void getAllWritersTest() {
		var numberOfWriters = writerRepository.findAll().size();
		List<Writer> writers = writerService.getAllWriters();
		assertEquals(writers.size(), numberOfWriters);
	}

	@DisplayName("WriterService.createOrModifyWriter should create a new writer if no writers with the same id found")
	@Test
	void createWriterTest() {
		var numberOfWritersBefore = writerRepository.findAll().size();
		WriterData writerData = new WriterData();
		writerData.setBirthDate(LocalDate.now().toString());
		writerData.setFullName("The Author");
		writerService.createOrModifyWriter(writerData);
		var numberOfWritersAfter = writerRepository.findAll().size();

		assertEquals(numberOfWritersBefore, numberOfWritersAfter - 1);
	}

	@DisplayName("WriterService.createOrModifyWriter should update existing writer when one with the same id found")
	@Test
	void updateWriterTest() {
		WriterData writerData = new WriterData();
		writerData.setBirthDate(LocalDate.now().toString());
		writerData.setFullName("Arany János");
		Optional<Writer> w1 = writerService.createOrModifyWriter(writerData);
		var numberOfWritersBefore = writerRepository.findAll().size();

		writerData.setId(w1.get().getId().toString());
		writerData.setFullName("János Arany");
		writerService.createOrModifyWriter(writerData);
		var numberOfWritersAfter = writerRepository.findAll().size();

		Optional<Writer> w2 = writerRepository.findById(w1.get().getId());

		assertEquals(numberOfWritersBefore, numberOfWritersAfter);
		assertEquals(w2.get().getFullName(), "János Arany");
	}

	@DisplayName("WriterService.findWriterById should retrieve the desired writer")
	@Test
	void findWriterByIdTest() {
		WriterData writerData = new WriterData();
		writerData.setBirthDate(LocalDate.now().toString());
		writerData.setFullName("The New Author 2");
		Optional<Writer> w1 = writerService.createOrModifyWriter(writerData);
		Optional<Writer> w2 = writerService.findWriterById(w1.get().getId().toString());

		assertEquals(w1.get().getId(), w2.get().getId());
	}

	@DisplayName("WriterService.deleteWriterById should delete the desired writer")
	@Test
	void deleteWriterByIdTest() {
		WriterData writerData = new WriterData();
		writerData.setBirthDate(LocalDate.now().toString());
		writerData.setFullName("The New Author 3");
		Optional<Writer> w1 = writerService.createOrModifyWriter(writerData);

		writerService.deleteWriterById(w1.get().getId().toString());
		Optional<Writer> w2 = writerRepository.findById(w1.get().getId());

		assertFalse(w1.isEmpty());
		assertTrue(w2.isEmpty());
	}
}
