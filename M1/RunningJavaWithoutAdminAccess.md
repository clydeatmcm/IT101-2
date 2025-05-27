### ✅ Run Java Code in VS Code Without Admin Access

1. **Download VS Code ZIP**
   Go to [https://code.visualstudio.com/download](https://code.visualstudio.com/download)
   – Choose the **.zip** version.

2. **Download JDK ZIP**
   Go to [https://www.openlogic.com/openjdk-downloads](https://www.openlogic.com/openjdk-downloads)
   – Download the latest **.zip** version (Windows x64).

3. **Set Up Folders**
   – Create a folder named **Java** in `C:\`
   – Extract both ZIP files there.
   – Rename the folders to:
   `C:\Java\VSCode`
   `C:\Java\JDK`

4. **Open VS Code**
   – Go to `C:\Java\VSCode\bin`
   – Double-click **Code.exe** to open VS Code.

5. **Create a Java File**
   – In VS Code, create a file named `Test.java`
   – Copy and paste this code:

```java
class Test {
    public static void main(String[] args) {
        System.out.println("Hello");
    }
}
```

6. **Open Integrated Terminal**
   – Right-click `Test.java` in the Explorer (left side).
   – Click **"Open in Integrated Terminal"**.

7. **Set the JDK Path**
   – In the terminal, type this command:

```bash
set path=%PATH%;C:\Java\JDK\bin
```

8. **Compile the Code**

```bash
javac Test.java
```

9. **Run the Code**

```bash
java Test
```

10. **See the Output**
    You should see:

```
Hello
```

---

🎉 Done! You’ve successfully run Java in VS Code without admin rights. Buy me a coffee :)
