package com.klef.jfsd.exam;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

public class ClientDemo {
    public static void main(String[] args) {
        // Configure Hibernate
        Configuration cfg = new Configuration();
        cfg.configure("hibernate.cfg.xml");
        SessionFactory sessionFactory = cfg.buildSessionFactory();
        Session session = sessionFactory.openSession();

        // Insert records
        insertProjects(session);

        // Perform aggregate functions
        performAggregateFunctions(session);

        session.close();
        sessionFactory.close();
    }

    private static void insertProjects(Session session) {
        Transaction tx = session.beginTransaction();

        Project project1 = new Project();
        project1.setProjectName("AI Development");
        project1.setDuration(12);
        project1.setBudget(150000);
        project1.setTeamLead("John Doe");

        Project project2 = new Project();
        project2.setProjectName("Cloud Migration");
        project2.setDuration(8);
        project2.setBudget(85000);
        project2.setTeamLead("Jane Smith");

        Project project3 = new Project();
        project3.setProjectName("Mobile App");
        project3.setDuration(6);
        project3.setBudget(50000);
        project3.setTeamLead("Alice Johnson");

        session.save(project1);
        session.save(project2);
        session.save(project3);
        tx.commit();

        System.out.println("Projects inserted successfully!");
    }

    private static void performAggregateFunctions(Session session) {
        CriteriaBuilder builder = session.getCriteriaBuilder();

        // Count
        CriteriaQuery<Long> countQuery = builder.createQuery(Long.class);
        Root<Project> countRoot = countQuery.from(Project.class);
        countQuery.select(builder.count(countRoot));
        Long count = session.createQuery(countQuery).getSingleResult();
        System.out.println("Count of projects: " + count);

        // Max Budget
        CriteriaQuery<Double> maxQuery = builder.createQuery(Double.class);
        Root<Project> maxRoot = maxQuery.from(Project.class);
        maxQuery.select(builder.max(maxRoot.get("budget")));
        Double maxBudget = session.createQuery(maxQuery).getSingleResult();
        System.out.println("Max Budget: " + maxBudget);

        // Min Budget
        CriteriaQuery<Double> minQuery = builder.createQuery(Double.class);
        Root<Project> minRoot = minQuery.from(Project.class);
        minQuery.select(builder.min(minRoot.get("budget")));
        Double minBudget = session.createQuery(minQuery).getSingleResult();
        System.out.println("Min Budget: " + minBudget);

        // Sum Budget
        CriteriaQuery<Double> sumQuery = builder.createQuery(Double.class);
        Root<Project> sumRoot = sumQuery.from(Project.class);
        sumQuery.select(builder.sum(sumRoot.get("budget")));
        Double sumBudget = session.createQuery(sumQuery).getSingleResult();
        System.out.println("Sum of Budgets: " + sumBudget);

        // Average Budget
        CriteriaQuery<Double> avgQuery = builder.createQuery(Double.class);
        Root<Project> avgRoot = avgQuery.from(Project.class);
        avgQuery.select(builder.avg(avgRoot.get("budget")));
        Double avgBudget = session.createQuery(avgQuery).getSingleResult();
        System.out.println("Average Budget: " + avgBudget);
    }
}
