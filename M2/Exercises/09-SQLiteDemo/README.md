Example structure:

```
JavaSQLiteDemo/
├── lib/
│   └── sqlite-jdbc-3.36.0.3.jar
├── src/
│   └── Main.java
└── .vscode/
```

---

## Download SQLite JDBC

1. Download from: [https://github.com/xerial/sqlite-jdbc/releases](https://github.com/xerial/sqlite-jdbc/releases)
2. Put the `.jar` file in the `lib/` folder.

---

## Write `Main.java`

Create `src/Main.java`:

```java
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
        String url = "jdbc:sqlite:sample.db";

        try (Connection conn = DriverManager.getConnection(url)) {
            if (conn != null) {
                System.out.println("Connected to SQLite database.");
            }
        } catch (SQLException e) {
            System.out.println("Connection failed.");
            e.printStackTrace();
        }
    }
}
```

This creates (or connects to) a `sample.db` SQLite file in the root directory.

---

## Compile and Run

### Terminal Commands

```bash
# Navigate to the root of the project
cd JavaSQLiteDemo

# Compile
javac -cp "lib/sqlite-jdbc-3.36.0.3.jar" -d bin src/Main.java

# Run
java -cp "bin:lib/sqlite-jdbc-3.36.0.3.jar" Main
```

> ✅ On Windows, use `;` instead of `:` in classpath:

```bash
java -cp "bin;lib/sqlite-jdbc-3.36.0.3.jar" Main
```

---

## Output

```
Connected to SQLite database.
```

---
