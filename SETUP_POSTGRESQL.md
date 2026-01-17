# Setup PostgreSQL untuk Game Database

## 1️⃣ Install PostgreSQL

### Ubuntu/Debian:
```bash
sudo apt update
sudo apt install postgresql postgresql-contrib
```

### Fedora/RHEL:
```bash
sudo dnf install postgresql postgresql-server
sudo postgresql-setup --initdb
sudo systemctl start postgresql
```

### macOS:
```bash
brew install postgresql
brew services start postgresql
```

## 2️⃣ Setup Database

### Masuk ke PostgreSQL:
```bash
sudo -u postgres psql
```

### Buat Database dan User:
```sql
-- Buat database
CREATE DATABASE game_db;

-- Buat user (opsional, bisa pakai user postgres default)
CREATE USER gameuser WITH PASSWORD 'password123';

-- Berikan akses ke database
GRANT ALL PRIVILEGES ON DATABASE game_db TO gameuser;

-- Keluar
\q
```

## 3️⃣ Konfigurasi Koneksi di Code

Edit file `DatabaseManager.java` line 28-30:

```java
private static final String POSTGRES_URL = "jdbc:postgresql://localhost:5432/game_db";
private static final String POSTGRES_USER = "postgres";  // atau "gameuser"
private static final String POSTGRES_PASSWORD = "password123";  // sesuaikan password Anda
```

## 4️⃣ Test Koneksi

Jalankan contoh program:
```bash
mvn clean compile exec:java -Dexec.mainClass="project_akhir.project2.DatabaseExample"
```

## 5️⃣ Perintah PostgreSQL Berguna

### Cek koneksi:
```bash
psql -U postgres -d game_db
```

### Lihat semua tabel:
```sql
\dt
```

### Lihat data tabel players:
```sql
SELECT * FROM players;
```

### Lihat data battle_history:
```sql
SELECT * FROM battle_history;
```

### Hapus semua data (reset):
```sql
TRUNCATE TABLE battle_history, players RESTART IDENTITY CASCADE;
```

### Keluar dari psql:
```sql
\q
```

## 🔧 Troubleshooting

### Error: "password authentication failed"
Edit file pg_hba.conf:
```bash
sudo nano /etc/postgresql/*/main/pg_hba.conf
```
Ubah `peer` menjadi `md5` atau `trust`, lalu restart:
```bash
sudo systemctl restart postgresql
```

### Error: "FATAL: database does not exist"
Pastikan Anda sudah membuat database `game_db`:
```bash
sudo -u postgres createdb game_db
```

### Error: "Connection refused"
Pastikan PostgreSQL service berjalan:
```bash
sudo systemctl status postgresql
sudo systemctl start postgresql
```
