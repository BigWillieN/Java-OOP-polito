package it.polito.library;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.stream.Collectors;



public class LibraryManager {
	
    // R1: Readers and Books 
    String bookID = "1000";
    String readerID = "1000";
    Map<String, LibBook> books = new TreeMap<>();
    Map<String, Integer> bookCounts = new TreeMap<>();
    Map<String, Reader> readers = new TreeMap<>();
 
    /**
	 * adds a book to the library archive
	 * The method can be invoked multiple times.
	 * If a book with the same title is already present,
	 * it increases the number of copies available for the book
	 * 
	 * @param title the title of the added book
	 * @return the ID of the book added 
	 */
    public String addBook(String title) {
    	LibBook book = new LibBook(title,bookID);
    	books.put(bookID, book);
    	bookID = String.valueOf(Integer.parseInt(bookID)+1);
    	
    	
    	if(!bookCounts.containsKey(title)) {
    		bookCounts.put(title, 1);
    	} else {
    		bookCounts.replace(title, bookCounts.get(title)+1);
    	}
    
    	
    	return book.getBookID();
    }
    
    /**
	 * Returns the book titles available in the library
	 * sorted alphabetically, each one linked to the
	 * number of copies available for that title.
	 * 
	 * @return a map of the titles liked to the number of available copies
	 */
    public SortedMap<String, Integer> getTitles() {    	
    	return bookCounts.entrySet().stream().sorted(Entry.comparingByKey()).collect(Collectors.toMap(e -> e.getKey(), e -> e.getValue(), (a1,a2)-> a1, TreeMap::new));
    }
    
    /**
	 * Returns the books available in the library
	 * 
	 * @return a set of the titles liked to the number of available copies
	 */
    public Set<String> getBooks() {    	    	
         return books.values().stream().map(b -> b.getBookID()).collect(Collectors.toSet());
       
    }
    
    /**
	 * Adds a new reader
	 * 
	 * @param name first name of the reader
	 * @param surname last name of the reader
	 */
    public void addReader(String name, String surname) {
    	Reader r = new Reader(name,surname,readerID);
    	readers.put(readerID, r);
    	
    	readerID = String.valueOf(Integer.parseInt(readerID)+1);
    	
    }
    
    
    /**
	 * Returns the reader name associated to a unique reader ID
	 * 
	 * @param readerID the unique reader ID
	 * @return the reader name
	 * @throws LibException if the readerID is not present in the archive
	 */
    public String getReaderName(String readerID) throws LibException {
       Reader r = readers.get(readerID);
       if (r==null)throw new LibException();
       
       return r.getName()+ " " + r.getSurname();
    }    
    
    
    // R2: Rentals Management
    
    
    /**
	 * Retrieves the bookID of a copy of a book if available
	 * 
	 * @param bookTitle the title of the book
	 * @return the unique book ID of a copy of the book or the message "Not available"
	 * @throws LibException  an exception if the book is not present in the archive
	 */
    public String getAvailableBook(String bookTitle) throws LibException {
    	if(bookCounts.get(bookTitle) == null) throw new LibException();
    	
    	LibBook book = books.values().stream().filter(b -> b.getAvailable() == true && b.getTitle().equals(bookTitle)).findFirst().orElse(null);
        if (book == null) {
    		String s = "Not available";
    		return s;
    	} else{
    		return book.getBookID();
    	}
    }

    /**
	 * Starts a rental of a specific book copy for a specific reader
	 * 
	 * @param bookID the unique book ID of the book copy
	 * @param readerID the unique reader ID of the reader
	 * @param startingDate the starting date of the rental
	 * @throws LibException  an exception if the book copy or the reader are not present in the archive,
	 * if the reader is already renting a book, or if the book copy is already rented
	 */
	public void startRental(String bookID, String readerID, String startingDate) throws LibException {
		LibBook book = books.get(bookID);
		if (book == null) throw new LibException();
		if (book.getAvailable() != true)throw new LibException();
		
		Reader reader = readers.get(readerID);
		if (reader == null) throw new LibException();
		if (reader.getAvailable() != true)throw new LibException();
		
		
		Rental rental = new Rental(bookID,readerID,startingDate);
		book.addRental(rental);
		reader.addRental(rental);
    }
    
	/**
	 * Ends a rental of a specific book copy for a specific reader
	 * 
	 * @param bookID the unique book ID of the book copy
	 * @param readerID the unique reader ID of the reader
	 * @param endingDate the ending date of the rental
	 * @throws LibException  an exception if the book copy or the reader are not present in the archive,
	 * if the reader is not renting a book, or if the book copy is not rented
	 */
    public void endRental(String bookID, String readerID, String endingDate) throws LibException {
    	LibBook book = books.get(bookID);
		if (book == null) throw new LibException();
		if (book.getAvailable() == true)throw new LibException();
		
		Reader reader = readers.get(readerID);
		if (reader == null) throw new LibException();
		if (reader.getAvailable() == true)throw new LibException();
		
		book.endRental(endingDate);
		reader.endRental(endingDate);
	
    }
    
    
   /**
	* Retrieves the list of readers that rented a specific book.
	* It takes a unique book ID as input, and returns the readers' reader IDs and the starting and ending dates of each rental
	* 
	* @param bookID the unique book ID of the book copy
	* @return the map linking reader IDs with rentals starting and ending dates
	* @throws LibException  an exception if the book copy or the reader are not present in the archive,
	* if the reader is not renting a book, or if the book copy is not rented
	*/
    public SortedMap<String, String> getRentals(String bookID) throws LibException {
    	LibBook book = books.get(bookID);
        if (book == null) {
            throw new LibException("Book copy not found in the archive: " + bookID);
        }

        SortedMap<String, String> rentalMap = book.rentals.stream()
                .filter(r -> r.getReaderID() != null && r.getStartingDate() != null)
                .collect(Collectors.toMap(
                    rental -> rental.getReaderID(),
                    rental -> {
                        String startingDate = rental.getStartingDate();
                        String endingDate = rental.getEndingDate();
                        return (endingDate != null) ? startingDate + " " + endingDate : startingDate + " ONGOING";
                    },
                    (a1, a2) -> a1, // Merge function (in case of duplicate reader IDs)
                    TreeMap::new // Use TreeMap to maintain sorting
                ));

        if (rentalMap.isEmpty()) {
            throw new LibException("No rentals found for book copy: " + bookID);
        }

        return rentalMap;
		
    }
    
    
    // R3: Book Donations
    
    /**
	* Collects books donated to the library.
	* 
	* @param donatedTitles It takes in input book titles in the format "First title,Second title"
	*/
    public void receiveDonation(String donatedTitles) {
    	String[] split = donatedTitles.split(",");
    	for (String title : split) {
    		LibBook book = new LibBook(title,bookID);
        	books.put(bookID, book);
        	bookID = String.valueOf(Integer.parseInt(bookID)+1);
        	
        	
        	if(!bookCounts.containsKey(title)) {
        		bookCounts.put(title, 1);
        	} else {
        		bookCounts.replace(title, bookCounts.get(title)+1);
        	}
    	}
    }
    
    // R4: Archive Management

    /**
	* Retrieves all the active rentals.
	* 
	* @return the map linking reader IDs with their active rentals

	*/
    public Map<String, String> getOngoingRentals() {
    	return readers.values().stream().filter(r -> r.getAvailable()==false).collect(Collectors.toMap(r -> r.getReaderID(), b -> b.currentRental.getBookID()));
    }
    
    /**
	* Removes from the archives all book copies, independently of the title, that were never rented.
	* 
	*/
    public void removeBooks() {	
    	List<LibBook> remove = books.values().stream().filter(b -> b.rentals.isEmpty()).collect(Collectors.toList());
    	remove.stream().forEach(b -> bookCounts.replace(b.getTitle(), bookCounts.get(b.getTitle()) - 1));
    	remove.stream().forEach(b -> books.remove(b.getBookID()));
    }
    	
    // R5: Stats
    
    /**
	* Finds the reader with the highest number of rentals
	* and returns their unique ID.
	* 
	* @return the uniqueID of the reader with the highest number of rentals
	*/
    public String findBookWorm() {
    	Reader bookWorm = readers.values().stream()
                .max(Comparator.comparingInt(r -> r.getRentals().size())).get();

        return bookWorm.getReaderID();
    }
    
    /**
	* Returns the total number of rentals by title. 
	* 
	* @return the map linking a title with the number of rentals
	*/
    public Map<String,Integer> rentalCounts() {
    	return books.values().stream()
                .collect(Collectors.toMap(
                        LibBook::getTitle, // Key mapper
                        book -> book.rentals.size(), // Value mapper
                        Integer::sum // Merge function in case of duplicate titles
                ));
}
}
