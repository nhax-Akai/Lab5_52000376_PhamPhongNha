package dao;

import model.Product;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;
import utils.HibernateUtils;

public class ProductDAO {

    private SessionFactory sessionFactory;

    public static ProductDAO getInstance() {
        return new ProductDAO();
    }

    public ProductDAO() {
        // Initialize the Hibernate session factory
        sessionFactory = HibernateUtils.getSessionFactory();
    }

    public boolean addProduct(Product product) {
        // Add a new product to the database
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.save(product);
            transaction.commit();
            return true;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
            return false;
        }
    }

    public Product geProductByName(String name) {
        // Get a product from the database by name hibernate
        try (Session session = sessionFactory.openSession()) {
            Query<Product> query = session.createQuery("FROM Product WHERE name = :name");
            query.setParameter("name", name);
            return query.getSingleResult();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public Product getProductById(int id) {
        // Get a product from the database by id hibernate
        try (Session session = sessionFactory.openSession()) {
            return session.get(Product.class, id);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean deleteProductById(int id) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            Product product = session.get(Product.class, id);
            session.delete(product);
            transaction.commit();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Product> getAllProducts() {
        // Get all products from the database
        try (Session session = sessionFactory.openSession()) {
            Query<Product> query = session.createQuery("FROM Product");
            return (List<Product>) query.list();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
