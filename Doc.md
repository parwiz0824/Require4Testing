

wie die Datenbankstruktur beschaffen ist ?

Die Datenbankstruktur für die beschriebenen Anforderungen besteht aus zwei Haupttabellen: user und test. Hier ist das Datenbankmodell für beide Tabellen mit ihren jeweiligen Feldern und den Beziehungen zwischen ihnen:

Tabelle user
Die user-Tabelle speichert die Informationen über die Benutzer des Systems. Sie hat die folgenden Felder:

id: Primärschlüssel, automatisch generiert
username: Benutzername, eindeutiger Wert
password: Passwort
role: Rolle des Benutzers (Tester, TestManager, Testfallersteller)

CREATE TABLE user (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(100) NOT NULL,
    role ENUM('Tester', 'TestManager', 'Testfallersteller') NOT NULL
);


Tabelle test
Die test-Tabelle speichert die Informationen über die Tests. Sie hat die folgenden Felder:
id: Primärschlüssel, automatisch generiert
Test: Frage des Tests
Testergebnis: Antwort des Tests 
status: Status des Tests 
user_id: Fremdschlüssel

CREATE TABLE test (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    test TEXT NOT NULL,
    testergebnis TEXT,
    status BOOLEAN DEFAULT FALSE,
    user_id BIGINT,
    FOREIGN KEY (user_id) REFERENCES user(id)
);

Datenbankbeziehungen
Beziehung: Ein Benutzer kann mehrere Tests haben, aber ein Test gehört zu einem Benutzer.