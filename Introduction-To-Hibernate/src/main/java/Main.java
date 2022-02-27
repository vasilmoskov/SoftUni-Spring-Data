import entities.Address;
import entities.Employee;
import entities.Project;
import entities.Town;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

public class Main {
    static EntityManagerFactory factory = Persistence.createEntityManagerFactory("soft_uni");
    static EntityManager em = factory.createEntityManager();
    static EntityTransaction transaction = em.getTransaction();

    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {

        transaction.begin();

        System.out.println("Please enter exercise number:");
        int exNum = Integer.parseInt(scanner.nextLine());

        switch (exNum) {
            case 2 -> exTwo();
            case 3 -> exThree();
            case 4 -> exFour();
            case 5 -> exFive();
            case 6 -> exSix();
            case 7 -> exSeven();
            case 8 -> exEight();
            case 9 -> exNine();
            case 10 -> exTen();
            case 11 -> exEleven();
            case 12 -> exTwelve();
            case 13 -> exThirteen();
        }

        em.close();
    }

    private static void exTwo() {
        int affectedRows = em.createQuery("update Town t set t.name = upper(t.name) where length(t.name) <= 5").executeUpdate();

        List<Town> towns = em.createQuery("SELECT t from Town t", Town.class).getResultList();

        for (Town town : towns) {
            String name = town.getName();

            if (name.length() > 5) {
                em.detach(town);
            } else {
                town.setName(name.toUpperCase());
                em.persist(town);
            }
        }

        for (Town town : towns) {
            System.out.println(town.getName());
        }
    }

    private static void exThree() {
        System.out.println("Please enter a name: ");
        String[] tokens = scanner.nextLine().split(" ");
        String firstName = tokens[0];
        String lastName = tokens[1];

        Long singleResult = em.createQuery("select count(e) from Employee e where e.firstName = :f_name and e.lastName = :l_name", Long.class)
                .setParameter("f_name", firstName)
                .setParameter("l_name", lastName)
                .getSingleResult();

        System.out.println(singleResult == 0 ? "No" : "Yes");


        List<Employee> employees = em.createQuery("select e from Employee e " +
                "where concat(e.firstName, ' ', e.lastName) = '" + firstName + "'", Employee.class).getResultList();

        System.out.println(employees.isEmpty() ? "No" : "Yes");

        /*
        // Alternative:
        boolean found = false;
        for (Employee employee : employees) {
            String fullName = employee.getFirstName() + " " + employee.getLastName();
            if (name.equals(fullName)) {
                System.out.println("Yes");
                found = true;
                break;
            }
        }

        if (!found) {
            System.out.println("No");
        }
        */
    }

    private static void exFour() {
        List<String> resultList = em.createQuery("select e.firstName from Employee e where e.salary > 50000", String.class).getResultList();

        for (String employee : resultList) {
            System.out.println(employee);
        }

        //Alternative:
//        em.createQuery("select e from Employee e where e.salary > :min_salary", Employee.class)
//                .setParameter("min_salary", BigDecimal.valueOf(50000L))
//                .getResultStream()
//                .map(Employee::getFirstName)
//                .forEach(System.out::println);
    }

    private static void exFive() {
        List<Employee> employees = em.createQuery("select e from Employee e " +
                        "join e.department d where d.name = :d_name " +
                        "order by e.salary, e.id", Employee.class)
                .setParameter("d_name", "Research and Development")
                .getResultList();

        for (Employee e : employees) {
            System.out.printf("%s %s from %s - $%.2f%n",
                    e.getFirstName(), e.getLastName(), e.getDepartment().getName(), e.getSalary());
        }
    }

    private static void exSix() {
        Town town = em.find(Town.class, 32);

        Address address = new Address();
        address.setText("Vitoshka 15");
        address.setTown(town);
        em.persist(address);
        em.getTransaction().commit();

        String lastName = scanner.nextLine();

        List<Employee> employees = em.createQuery("select e from Employee e where e.lastName = :l_name", Employee.class)
                .setParameter("l_name", lastName).getResultList();

        for (Employee e : employees) {
            e.setAddress(address);
        }
    }

    private static void exSeven() {
        List<Address> addresses = em.createQuery(
                "select a from Address a " +
                        "order by a.employees.size desc",
                Address.class).setMaxResults(10).getResultList();

        //Alternative
//        List<Address> addresses = em.createQuery(
//                "select a from Address a " +
//                        "join a.employees " +
//                        "join a.town " +
//                        "group by a.id " +
//                        "order by a.employees.size desc",
//                Address.class).setMaxResults(10).getResultList();

        for (Address address : addresses) {
            System.out.printf("%s, %s - %d employees%n", address.getText(), address.getTown().getName(), address.getEmployees().size());
        }
    }

    private static void exEight() {
        System.out.println("Please enter an id:");

        int id = Integer.parseInt(scanner.nextLine());

        Employee e = em.createQuery(
                "select e from Employee e join e.projects where e.id = " + id
                , Employee.class).getSingleResult();

//        Alternative
//        Employee e = em.find(Employee.class, id);

        System.out.printf("%s %s - %s%n", e.getFirstName(), e.getLastName(), e.getJobTitle());

        e.getProjects().stream()
                .sorted((p1, p2) -> p2.getName().compareTo(p1.getName()))
                .forEach(p -> System.out.printf("\t%s\n", p.getName()));
    }

    private static void exNine() {
        List<Project> projects = em.createQuery(
                "select p from Project p order by p.startDate desc",
                Project.class).setMaxResults(10).getResultList();

        for (Project p : projects) {
            System.out.println("Project name: " + p.getName());
            System.out.println("\tProject Description: " + p.getDescription());
            System.out.println("\tProject Start Date: " + p.getStartDate().format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT)));
            System.out.println("\tProject End Date: " + p.getEndDate());
        }
    }

    private static void exTen() {
        int affectedRows = em.createQuery("update Employee e " +
                        "set e.salary = e.salary * 1.12 where e.department.id in :d_id")
                .setParameter("d_id", Set.of(1, 2, 4))
                .executeUpdate();

//        List<String> departments = Arrays.asList("Engineering", "Tool Design", "Marketing", "Information Services");
//
//        int affectedRows = em.createQuery("update Employee e " +
//                        "set e.salary = e.salary * 1.12 where e.department.name in (:d_name)")
//                .setParameter("d_name", departments)
//                .executeUpdate();

        em.getTransaction().commit();

        System.out.println(affectedRows);

        List<Employee> employees = em.createQuery(
                "select e " +
                        "from Employee e " +
                        "join e.department d " +
                        "where d.name in ('Engineering', 'Tool Design', 'Marketing', 'Information Services')",
                Employee.class).getResultList();

        for (Employee e : employees) {
            System.out.printf("%s %s (%.2f)\n", e.getFirstName(), e.getLastName(), e.getSalary());
        }
    }

    //ex 11

    private static void exEleven() {
        System.out.println("Please enter a name starting with: ");

        String nameStart = scanner.nextLine();

        List<Employee> employees = em.createQuery(
                        "select e from Employee e where e.firstName like :name_start", Employee.class)
                .setParameter("name_start", nameStart + "%")
                .getResultList();

        for (Employee e : employees) {
            System.out.printf("%s %s - %s - ($%.2f)\n", e.getFirstName(), e.getLastName(), e.getJobTitle(), e.getSalary());
        }
    }

    private static void exTwelve() {
        List<Object[]> resultList = em.createNativeQuery("select d.name, max(salary) as max_salary\n" +
                "        from departments as d\n" +
                "        join employees\n" +
                "        using(department_id)\n" +
                "        group by department_id\n" +
                "        having max_salary not between 30000 and 70000;").getResultList();

        for (Object[] objects : resultList) {
            System.out.println(objects[0] + " " + objects[1]);
        }
    }

    private static void exThirteen() {
        System.out.println("Enter a town");
        String townName = scanner.nextLine();

        int deleted = em.createQuery("delete from Town t where t.name = :t_name")
                .setParameter("t_name", townName).executeUpdate();

        System.out.println(deleted);

//        Town town = em.createQuery("select t from Town t where t.name = :t_name", Town.class)
//                .setParameter("t_name", townName).getSingleResult();
//
//        List<Address> addresses = em.createQuery("select a from Address  a where  a.town.id = :p_id", Address.class)
//                .setParameter("p_id", town.getId()).getResultList();
//
//        int deletedAddresses = addresses.size();
//
//        for (Address address : addresses) {
//            em.remove(address);
//        }
//
//        em.remove(town);

//        System.out.printf("%d %s in %s deleted", deletedAddresses,
//                deletedAddresses == 1 ? "address" : "addresses", townName);
    }
}
