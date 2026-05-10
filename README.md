# springboot-trading

Spring Boot application for trading automation (IBKR, Kafka, Angular UI).

---

## Run everything (recommended)

From the **repository root**, scripts start the **Spring Boot API** and **Angular dev server** in separate windows. You still need **PostgreSQL** and **`lib/tws-api.jar`** on your machine (see prerequisites below).

### Prerequisites

1. **JDK 17** and **Maven** (use the included **`mvnw.cmd`** / **`./mvnw`**).
2. **`lib/tws-api.jar`** — copy **`TwsApi.jar`** from the [IB TWS API (Java)](https://interactivebrokers.github.io/) install into **`lib/tws-api.jar`** (see **`lib/README.txt`**). Maven cannot download this from Central; the build enforces that the file exists.
3. **PostgreSQL** — database **`trading_db_dev`**, user **`postgres`** (see `springboot-trading-web/src/main/resources/application.yml`). The app creates JPA schema **`trading`** on first pool connections (`CREATE SCHEMA IF NOT EXISTS trading`).
4. **Node.js** / **npm** (for the Angular app).
5. **`POSTGRES_PW`** — must match the Postgres password for user `postgres` (the app reads this env var).

### `run-all` modes

| Command | What it does |
|--------|----------------|
| **`run-all.bat`** or **`run-all.bat local`** (default) | Sets **`SPRING_PROFILES_ACTIVE=local`**: no Kafka broker or TWS required at startup; good for UI + API against Postgres only. |
| **`run-all.bat full`** | Runs **`docker compose up -d`** (Kafka on **`127.0.0.1:9092`**), waits briefly, then starts the API **without** the `local` profile so Kafka consumers run. Requires **Docker**. |

The script sets default **`AUTOTRADE_*`** values if they are unset. It runs **`npm install`** in **`springboot-trading-frontend`** when **`node_modules`** is missing.

### Windows — Command Prompt (CMD)

```bat
cd D:\path\to\springboot-trading
set POSTGRES_PW=yourpassword
run-all.bat
```

Full stack with Kafka:

```bat
set POSTGRES_PW=yourpassword
run-all.bat full
```

### Windows — PowerShell

In PowerShell you must use **`.\`** to run a script in the current directory:

```powershell
cd D:\path\to\springboot-trading
$env:POSTGRES_PW = 'yourpassword'
.\run-all.bat
```

Equivalent wrapper (forwards arguments to the batch file):

```powershell
$env:POSTGRES_PW = 'yourpassword'
.\run-all.ps1          # same as .\run-all.bat local
.\run-all.ps1 full     # Kafka + default Spring profile
```

### After it starts

- **API:** [http://localhost:8080/](http://localhost:8080/)
- **UI:** [http://localhost:4200/](http://localhost:4200/)

Close the **springboot-trading API** and **springboot-trading UI** terminal windows to stop those processes. If you used **`full`**, stop Kafka when finished:

```bat
docker compose down
```

**Not started by `run-all`:** PostgreSQL itself, and **TWS / IB Gateway** (needed for live IB connectivity on the default API port in config).

---

## Run manually (without `run-all`)

### Backend only (easiest dev stack — no Kafka / no TWS)

From the **repo root**:

```powershell
$env:POSTGRES_PW = "<your password>"
$env:AUTOTRADE_DELTA_VALUE = "0.3"
$env:AUTOTRADE_ORDER_LIMIT_VALUE = "1000"
$env:AUTOTRADE_SPREAD_SIZE = "2"
$env:AUTOTRADE_QUANTITY_OF_STRATEGIES = "1"
$env:SPRING_PROFILES_ACTIVE = "local"
.\mvnw.cmd spring-boot:run
```

The root POM skips `spring-boot:run` on the aggregator and library modules; only **`springboot-trading-web`** runs. If module resolution fails:

```powershell
.\mvnw.cmd -pl springboot-trading-web -am spring-boot:run
```

### Frontend only

```powershell
cd springboot-trading-frontend
npm install
npm start
```

Then open [http://localhost:4200/](http://localhost:4200/) (expects the API at [http://localhost:8080/](http://localhost:8080/) per `environment.ts`).

### Full stack (default Spring profile)

Start Kafka on **`127.0.0.1:9092`** (e.g. **`docker compose up -d`** in this repo), run TWS/Gateway with API on port **7497** as in `application.yml`, and **do not** set `SPRING_PROFILES_ACTIVE` to `local`.

---

More detail for the Angular app: **`springboot-trading-frontend/README.md`**.
