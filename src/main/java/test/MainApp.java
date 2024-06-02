package test;

import org.hibernate.SessionFactory;

public class MainApp {
	private static SessionFactory sessionFactory;

	public static void main(String[] args) {
		// Initialize the SessionFactory using HibernateUtil
		sessionFactory = HibernateUtil.getSessionFactory();

		// Print a message to indicate that the SessionFactory has been initialized
		System.out.println("SessionFactory initialized successfully.");

		// Perform further operations using the SessionFactory as needed
		// (e.g., create sessions, perform database operations)

		// Example: keep the application running until terminated externally
		// You may replace this with your actual application logic
		while (true) {
			try {
				Thread.sleep(1000); // Sleep for 1 second
				System.out.println("SessionFactory initialized successfully.");

			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	// Ensure the SessionFactory is closed properly when the application exits
	// This method can be called externally to gracefully shut down the application
	public static void shutdown() {
		if (sessionFactory != null && !sessionFactory.isClosed()) {
			sessionFactory.close();
			System.out.println("SessionFactory closed gracefully.");
		}
	}
}
