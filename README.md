# lejupladet maven un ielikt uz C disku

# palaist komandas (var pazust pec vscode restarta)
export JAVA_HOME="C:\Program Files\Java\jdk-17"

# uzgeneret maven wrapper no CMD konsoles
"C:\apache-maven-3.9.6\bin\mvn" wrapper:wrapper

# palaist projektu
./mvnw.cmd javafx:run -f "C:\Users\ae00170\Documents\Battle-ships\pom.xml"
./mvnw.cmd javafx:run -f "C:\Users\rz00754\Documents\Project\Battle-ships\pom.xml"
