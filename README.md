# springboot-trading

Spring Boot Application for Trading Automation

## Run (quick)

1. Put **`TwsApi.jar`** from the [IB TWS API (Java)](https://interactivebrokers.github.io/) install into **`lib/tws-api.jar`** (see `lib/README.txt`). Maven cannot download this artifact from Central.
2. PostgreSQL: database **`trading_db_dev`**, user **`postgres`** (matches `application.yml`). The app uses JPA schema **`trading`**; the datasource runs `CREATE SCHEMA IF NOT EXISTS trading` on new pool connections so you do not need to create it manually.
3. **Easiest dev stack** (no Kafka / no TWS): set profile **`local`** and env vars, then from the **repo root**:

```powershell
$env:POSTGRES_PW = "<your password>"
$env:AUTOTRADE_DELTA_VALUE = "0.3"
$env:AUTOTRADE_ORDER_LIMIT_VALUE = "1000"
$env:AUTOTRADE_SPREAD_SIZE = "2"
$env:AUTOTRADE_QUANTITY_OF_STRATEGIES = "1"
$env:SPRING_PROFILES_ACTIVE = "local"
.\mvnw.cmd spring-boot:run
```

The root POM skips `spring-boot:run` on the aggregator and on library modules; only **`springboot-trading-web`** runs. If the first run fails to resolve sibling modules, use: `.\mvnw.cmd -pl springboot-trading-web -am spring-boot:run`.

4. **Full stack** (default profile): start Kafka on `127.0.0.1:9092` (e.g. `docker compose up -d` in this repo), run TWS/Gateway with API on port **7497**, omit `SPRING_PROFILES_ACTIVE` or unset `local`.

API default: **http://localhost:8080/** (run Angular separately on **4200** if you use the UI).
