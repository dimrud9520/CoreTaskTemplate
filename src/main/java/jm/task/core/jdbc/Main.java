package jm.task.core.jdbc;

import jm.task.core.jdbc.dao.UserDaoHibernateImpl;
import jm.task.core.jdbc.dao.UserDaoJDBCImpl;
import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserServiceImpl;
import jm.task.core.jdbc.util.Util;
import org.hibernate.SessionFactory;

public class Main {
    public static void main(String[] args) {

        UserServiceImpl userService = new UserServiceImpl();

        userService.createUsersTable();

        userService.saveUser("Иван", "Петров", (byte) 18);
        userService.saveUser("Андрей", "Иванов", (byte) 19);
        userService.saveUser("Алена", "Крюкова", (byte) 27);
        userService.saveUser("Петр", "Александров", (byte) 17);

        for (User values : userService.getAllUsers()) {
            System.out.println(values.toString());
        }

        userService.cleanUsersTable();

        userService.dropUsersTable();


    }
}
