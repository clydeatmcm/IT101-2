# Guided Java GUI Hands-on Lab Exercises

### Transaction-based Java GUI
* [01-SwingDemo.md](./01-SwingDemo.md) | [Source Code](./SwingDemo.java)
* [02-TaskTrackerApp.md](./02-TaskTrackerApp.md) | [Source Code](./TaskTrackerApp.java)

### Game-based Java GUI
* [01-LibGDXSetup.md](./01-LibGDXSetup.md)

#### Setting Up FileHandling

```java

import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        FileWriter writer = null;

        try {
            writer = new FileWriter("Names.txt", true); // true to append to file
            System.out.println("Enter names to save to Names.txt (type 'exit' to finish):");

            while (true) {
                System.out.print("Name: ");
                String name = scanner.nextLine();

                if ("exit".equalsIgnoreCase(name)) {
                    break;
                }

                writer.write(name + System.lineSeparator());
            }

            System.out.println("Names saved successfully.");
        } catch (IOException e) {
            System.out.println("An error occurred: " + e.getMessage());
        } finally {
            try {
                if (writer != null) {
                    writer.close();
                }
                scanner.close();
            } catch (IOException e) {
                System.out.println("Error closing file: " + e.getMessage());
            }
        }
    }
}

```
