## Implementation Plan

The application will be implemented in phases, with responsibilities separated between the domain, business logic, input/output, and command-line layers.

### 1. Define Package Structure

Establish a small, maintainable package structure that separates domain models, business logic, CSV handling, CLI concerns, and error handling.

```text
com.jaydin.league
├── cli
├── domain
├── service
├── csv
└── exception
```

### 2. Define Domain Classes

Identify the core objects required to represent the football league domain.

Initial domain model:

* `MatchResult`

    * Represents the result of a single football match.
    * Contains the home team, away team, and goals scored by each team.

* `TeamStanding`

    * Represents a team's accumulated league statistics.
    * Tracks games played, wins, draws, losses, goals for, goals against, goal difference, and points.

### 3. Define Service Responsibilities

The service layer will contain the core league calculation logic.

Responsibilities include:

* Processing match results.
* Creating and updating team standings.
* Calculating wins, draws, and losses.
* Calculating goals for and goals against.
* Calculating goal difference.
* Calculating league points according to the applicable historical rules.
* Sorting teams according to the league's historical ranking rules.

The calculation logic should remain independent of the CLI and CSV implementation.

### 4. Define CSV Reader Responsibility

The CSV reader will be responsible only for converting input CSV data into domain objects.

Responsibilities include:

* Reading match results from a CSV file.
* Parsing each row into a `MatchResult`.
* Validating the expected CSV structure.
* Detecting malformed or invalid match data.
* Reporting meaningful input errors.

The CSV reader should not contain league calculation logic.

### 5. Define CSV Writer Responsibility

The CSV writer will be responsible for converting calculated standings into the required output format.

Responsibilities include:

* Accepting calculated `TeamStanding` objects.
* Writing the conventional league table to CSV.
* Writing columns in a predictable order.
* Ensuring the output can be consumed by another program.

The CSV writer should not calculate league statistics.

### 6. Define Error-Handling Strategy

The application should fail predictably and provide useful error messages.

Potential errors include:

* Missing input file.
* Invalid CSV structure.
* Missing team names.
* Invalid or non-numeric scores.
* Negative scores.
* Empty input files.
* Invalid command-line arguments.
* Unable to read or write files.

Errors should be handled at the appropriate layer and surfaced to the CLI as clear, user-friendly messages.

### 7. Define CLI Contract

The command-line interface will provide a simple way to execute the application without requiring interaction with the internal classes.

Example:

```bash
java -jar football-league.jar <input-file> <output-file>
```

Example:

```bash
java -jar football-league.jar \
    data/1974-75-after-gw10.csv \
    output/standings.csv
```

The CLI is responsible for:

1. Validating command-line arguments.
2. Reading the input file.
3. Passing match results to the league calculation service.
4. Passing calculated standings to the CSV writer.
5. Reporting success or meaningful errors to the user.

The CLI should remain thin and should not contain league calculation rules.

### Implementation Order

The implementation will follow this order:

1. Package structure
2. Domain classes
3. League calculation service
4. Unit tests for domain and calculation logic
5. CSV reader
6. CSV writer
7. CLI
8. Integration tests
9. Historical 1974/75 validation
10. Documentation and production-readiness review
