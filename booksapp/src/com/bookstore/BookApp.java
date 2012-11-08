package com.bookstore;

import java.util.List;
import java.util.Iterator;
import org.hibernate.Session;
import org.hibernate.Query;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
public class BookApp{
	public static void main(String args[]){
		BookMethods bm = new BookMethods();
		
		//Adding books
		bm.addBook("Professional Hibernate","Eric Pugh, Gradecki", 350);
		bm.addBook("Hibernate in Action","Christian Bauer, Gavin King", 499);
		bm.addBook("Java Persistence With Hibernate","Bauer, Gavin King", 423);
		bm.addBook("Begining Hibernate","Dave Minter, Jeff Linwood", 1500);

		//Listing added books
		bm.listBooks();
		
		//Getting book details for a particular book name.
		bm.getBookDetails("Hibernate in Action");
		
		//To see no. of books
		bm.countBook();

		//Update particular book cost
		bm.updateBook("Begining Hibernate",1000);

		//Listing available books
		bm.listBooks();

		//Deleting a book
		bm.deleteBook("Java Persistence With Hibernate");

		//Listing available books After executing delete
		bm.listBooks();
	}
}

class BookMethods{
	//Using persistent objects to store data into the database.
	public void addBook(String bookName, String authorName, int bookCost){
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		BookDetails bd = new BookDetails();
		bd.setBookName(bookName);
		bd.setAuthorName(authorName);
		bd.setBookCost(bookCost);
		session.save(bd);
		session.getTransaction().commit();
	}

	//Using persistence object to delete a row from the database.
	public void deleteBook(String bookName){
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		List list = session.createQuery("from BookDetails where bookName='"+bookName+"'").list();
		Iterator itr = list.iterator();
		while(itr.hasNext()){
			BookDetails bd = (BookDetails)itr.next();
			System.out.println("delete : "+bd);
			session.delete(bd);
		}
		session.getTransaction().commit();
	}

	//Using Criteria API to update the book cost
	public void updateBook(String bookName, int bookCost){
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		Criteria cri = session.createCriteria(BookDetails.class);
		cri = cri.add(Restrictions.eq("bookName", bookName));
		List list = cri.list();
		BookDetails bd = (BookDetails)list.iterator().next();
		bd.setBookCost(bookCost);
		session.update(bd);
		session.getTransaction().commit();
	}

	//Using HQL - Hibernate Query Language
	public void getBookDetails(String bookName){
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		Query q = session.createQuery("from BookDetails where bookName=:bn");
		q.setString("bn", bookName);
		List list = q.list();
		System.out.println("Getting Book Details using HQL. \n"+list);

		//The above query can also be achieved with Criteria & Restrictions API.
		Criteria cri = session.createCriteria(BookDetails.class);
		cri = cri.add(Restrictions.eq("bookName", bookName));
		list = cri.list();
		System.out.println("Getting Book Details using Criteria API. \n"+list);

		session.getTransaction().commit();
	}

	//Aggregate function.
	public void countBook(){
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		List list = session.createQuery("select count(*) from BookDetails").list();
		System.out.println("Aggregate function count \n"+list);
		session.getTransaction().commit();
	}

	//Native SQL Query
	public void listBooks(){
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		List list = session.createSQLQuery("select * from BookStore").addEntity(BookDetails.class).list();
		Iterator itr = list.iterator();
		while(itr.hasNext()){
			BookDetails bd = (BookDetails)itr.next();
			System.out.println(bd);
		}
		session.getTransaction().commit();

	}
}

