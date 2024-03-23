package Tasks;

import org.flywaydb.core.Flyway;

public class Migration {
    public static void main(String[] args) {

        Flyway flyway = Flyway.configure()
                .dataSource("jdbc:postgresql://localhost:5432/Test_DB", "postgres", "root")
                .locations("classpath:db/migration")
                .load();

        flyway.repair();

        flyway.migrate();

        System.out.println("Migration successfully");
    }
}