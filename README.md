
## Estructura
- `src/controller` -> lógica principal y menús
- `src/config` -> carga de configuración
- `src/model` -> POJOs del dominio
- `src/dao` -> acceso a datos con JDBC
- `src/view` -> interfaz por consola
- `src/util` -> utilidades, validaciones, logs, carga inicial
- `src/services` -> login y servicio LLM de OpenRouter
- `sql/levelup_arcade.sql` -> script de base de datos
- `lib/` -> librerías externas
- `scripts/` -> compilación, tests, backup y generación del JAR
- `dist/` -> JAR ejecutable


## Compilar
```bash
./scripts/compile.sh
```

## Ejecutar desde clases compiladas
```bash
java -cp "out:lib/*" controller.MainApp
```

## Ejecutar tests 
```bash
./scripts/run_tests.sh
```

## Crear JAR ejecutable
```bash
./scripts/build_jar.sh
```

## Ejecutar JAR
```bash
./scripts/run_jar.sh
```

## Backup automático
```bash
./scripts/backup_mysql.sh
```
## Usuarios
- `admin` / `admin`
- `usuario` / `usuario`
