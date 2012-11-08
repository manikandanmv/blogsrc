package com.bookstore;

public class BookDetails{
	private int bookId;
	private String bookName;
	private String authorName;
	private int bookCost;

	BookDetails(){
	}

	public int getBookId(){
		return bookId;
	}
	public void setBookId(int bookId){
		this.bookId = bookId;
	}

	public String getBookName(){
		return bookName;
	}
	public void setBookName(String bookName){
		this.bookName = bookName;
	}

	public String getAuthorName(){
		return authorName;
	}
	public void setAuthorName(String authorName){
		this.authorName = authorName;
	}

	public int getBookCost(){
		return bookCost;
	}
	public void setBookCost(int bookCost){
		this.bookCost = bookCost;
	}

	public String toString(){
		StringBuffer sb = new StringBuffer();
		sb.append("BookName : ").append(bookName);
		sb.append(" ,AuthorName : ").append(authorName);
		sb.append(" , BookCost : ").append(bookCost);
		return sb.toString();
	}
}

