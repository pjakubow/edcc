package net.simplewebapps.edcc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class Application {


    public static void main(String[] args) throws Exception {
        ConfigurableApplicationContext ctx = SpringApplication.run(Application.class);
        JournalReader journalReader = ctx.getBean(JournalReader.class);
        new Thread(() -> journalReader.readJournal(findNewestLogfile())).start();
    }

    private static String findNewestLogfile() {
        return Application.class.getClassLoader().getResource("Journal.161025234203.01.log").getPath();
    }
}
