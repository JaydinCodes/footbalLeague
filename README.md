# Football League Standings Calculator

A production-ready Java command-line application that calculates football league standings from match results provided as CSV input.

This project was developed for the **SPAN Associate Software Engineer take-home assessment**.

The application is designed to calculate standings from arbitrary football match results, with the English First Division 1974/75 season used as the historical validation case.

## What the Application Does

The application:

1. Reads football match results from a CSV file.
2. Converts the input into domain objects.
3. Calculates each team's:

    * Matches played
    * Wins
    * Draws
    * Losses
    * Goals scored
    * Goals conceded
    * Goal average
    * Points
4. Sorts the teams into league-table order.
5. Writes the resulting standings to a CSV file.

The application does not hard-code the 1974/75 standings. The standings are calculated from the supplied match results.

---

# Requirements

The following are required to build and run the application:

* Java 21
* Maven 3.9 or later
* Git

Verify your Java installation:

```bash
java -version
```

Verify Maven:

```bash
mvn -version
```

No external services or databases are required.

---

# Project Structure

```text
footbalLeague/
├── data/
│   └── 1974-75-week-10.csv
├── expected/
│   └── 1974-75-week-10-standings.csv
├── src/
│   ├── main/
│   │   └── java/
│   │       └── org/example/footballeague/
│   │           ├── cli/
│   │           ├── csv/
│   │           ├── domain/
│   │           └── service/
│   └── test/
│       └── java/
├── ai/
├── AI_REFLECTION.md
├── AI_INSTRUCTIONS.md
├── pom.xml
└── README.md
```

The main application responsibilities are separated into:

```text
CSV Input
    ↓
CsvMatchReader
    ↓
MatchResult
    ↓
LeagueTableCalculator
    ↓
TeamStanding
    ↓
LeagueTableSorter
    ↓
CsvStandingWriter
    ↓
CSV Output
```

### Package responsibilities

#### `domain`

Contains the core football domain objects:

* `MatchResult`
* `TeamStanding`

#### `csv`

Responsible for translating between CSV files and application objects:

* `CsvMatchReader`
* `CsvStandingWriter`

#### `service`

Contains the league business logic:

* `LeagueTableCalculator`
* `LeagueTableSorter`

#### `cli`

Contains the command-line application entry point:

* `LeagueApplication`

---

# Building the Application

From the project root, run:

```bash
mvn clean test
```

This compiles the application and runs the complete automated test suite.

To create the executable JAR:

```bash
mvn clean package
```

The resulting JAR will be created under:

```text
target/footbalLeague-0.0.1-SNAPSHOT.jar
```

---

# Running the Application

The application accepts two command-line arguments:

```text
<input-file> <output-file>
```

General usage:

```bash
java -jar target/footbalLeague-0.0.1-SNAPSHOT.jar <input-file> <output-file>
```

For example:

```bash
java -jar target/footbalLeague-0.0.1-SNAPSHOT.jar \
    data/1974-75-week-10.csv \
    output/standings.csv
```

On Windows PowerShell:

```powershell
java -jar target\footbalLeague-0.0.1-SNAPSHOT.jar `
    data\1974-75-week-10.csv `
    output\standings.csv
```

The application creates the output directory if it does not already exist.

A successful execution produces:

```text
League standings successfully written to: output/standings.csv
```

---

# Input CSV Format

The input file contains one match per row.

The expected header is:

```text
match_id,season,competition,matchweek,date,home_team,away_team,home_goals,away_goals,result
```

Example:

```text
match_id,season,competition,matchweek,date,home_team,away_team,home_goals,away_goals,result
1,1974/75,First Division,1,1974-08-17,Liverpool,Everton,2,0,H
2,1974/75,First Division,1,1974-08-17,Arsenal,Chelsea,1,1,D
```

The application uses the following fields when calculating the standings:

| Field        | Purpose                       |
| ------------ | ----------------------------- |
| `matchweek`  | Identifies the game week      |
| `home_team`  | Home team                     |
| `away_team`  | Away team                     |
| `home_goals` | Goals scored by the home team |
| `away_goals` | Goals scored by the away team |

The remaining fields provide match metadata and are retained in the input format for traceability.

## Input Validation

The application rejects invalid match data such as:

* Missing team names
* The same team appearing as both home and away
* Negative goals
* Invalid match weeks
* Invalid numeric values
* CSV rows with an incorrect number of columns
* Missing input files

Errors include the relevant input line where possible to make invalid data easier to identify.

---

# Output CSV Format

The generated standings use the following header:

```text
Pos,Team,P,W,D,L,F,A,GAvg,Pts
```

The columns represent:

| Column | Meaning         |
| ------ | --------------- |
| `Pos`  | League position |
| `Team` | Team name       |
| `P`    | Matches played  |
| `W`    | Wins            |
| `D`    | Draws           |
| `L`    | Losses          |
| `F`    | Goals for       |
| `A`    | Goals against   |
| `GAvg` | Goal average    |
| `Pts`  | Points          |

Example:

```text
Pos,Team,P,W,D,L,F,A,GAvg,Pts
1,Ipswich Town,10,8,0,2,18,6,3.000,16
2,Manchester City,10,6,2,2,14,11,1.273,14
3,Liverpool,10,6,1,3,17,8,2.125,13
```

---

# League Calculation Rules

The application uses the rules applicable to the historical 1974/75 English First Division dataset.

## Points

Results are converted into points as follows:

| Result | Points |
| ------ | -----: |
| Win    |      2 |
| Draw   |      1 |
| Loss   |      0 |

For example, a team winning eight matches and losing two would receive:

```text
8 × 2 = 16 points
```

## Goal Average

For the historical dataset, ranking uses goal average rather than modern goal difference.

The calculation is:

```text
Goal Average = Goals For / Goals Against
```

For example:

```text
Goals For     = 18
Goals Against = 6

Goal Average = 18 / 6
             = 3.000
```

## Sorting

The implementation sorts standings by:

1. Points, descending
2. Goal average, descending
3. Team name, ascending as a deterministic implementation tie-breaker

The team-name tie-breaker is an implementation detail used to ensure deterministic output. It should not be interpreted as a historical league rule.

---

# 1974/75 Historical Dataset

The assessment requires the application to be used to calculate the English First Division standings after the tenth game week of the 1974/75 season.

The repository includes the historical match results used for this calculation.

The important distinction is that the application calculates the standings **after the tenth game week**, rather than only processing matches belonging to the tenth round of fixtures.

Because fixtures could be postponed or rescheduled, teams do not necessarily have the same number of matches played at this point in the season.

The resulting table therefore reflects the matches actually represented in the supplied dataset.

The historical dataset is located at:

```text
data/1974-75-week-10.csv
```

The expected result is located at:

```text
expected/1974-75-week-10-standings.csv
```

---

# Reproducing the Historical Result

First build the application:

```bash
mvn clean package
```

Then run:

```bash
java -jar target/footbalLeague-0.0.1-SNAPSHOT.jar \
    data/1974-75-week-10.csv \
    output/standings.csv
```

The generated file can then be compared with:

```text
expected/1974-75-week-10-standings.csv
```

The expected standings begin:

```text
Pos,Team,P,W,D,L,F,A,GAvg,Pts
1,Ipswich Town,10,8,0,2,18,6,3.000,16
2,Manchester City,10,6,2,2,14,11,1.273,14
3,Liverpool,10,6,1,3,17,8,2.125,13
4,Everton,10,4,5,1,14,11,1.273,13
5,Sheffield United,10,5,3,2,14,14,1.000,13
```

The complete expected output is included in the repository so the result can be independently reproduced and inspected.

---

# Testing

The project uses JUnit for automated testing.

Run the complete test suite with:

```bash
mvn clean test
```

Tests cover the application's main business and technical requirements, including:

### Domain validation

* Valid match creation
* Invalid match weeks
* Empty team names
* Duplicate home and away teams
* Negative scores

### Standing calculations

* Wins
* Draws
* Losses
* Points
* Goals for
* Goals against
* Goal average

### League sorting

* Points ranking
* Goal average ranking
* Deterministic ordering when values are otherwise equal

### CSV processing

* Valid CSV input
* Invalid CSV structure
* Invalid numeric values
* Missing input files
* CSV output formatting

### Integration

An end-to-end test verifies the complete flow:

```text
CSV input
   ↓
CSV reader
   ↓
League calculation
   ↓
League sorting
   ↓
CSV writer
   ↓
CSV output
```

---

# Design Decisions

## Separation of Responsibilities

The calculation logic is independent from the CSV and command-line layers.

This means the league calculation can be tested directly using Java objects without requiring files or command-line execution.

The CLI is responsible for orchestration rather than implementing football-specific business rules.

## Domain Validation

Invalid match data is rejected when it enters the domain rather than allowing invalid values to propagate through the application.

## Dependency Management

The application intentionally uses a small dependency footprint.

Maven manages the build and JUnit is used for testing. The core application uses standard Java APIs for file handling and collection processing.

## Deterministic Output

The sorting logic includes a team-name tie-breaker so that the generated CSV has deterministic ordering when the football-specific ranking criteria are otherwise equal.

---

# AI Collaboration

OpenAI ChatGPT was used as an engineering collaborator during development.

AI assistance was used for:

* Architectural discussion
* Code review
* Test design
* Debugging
* Identifying edge cases
* Refactoring
* Documentation

The implementation was reviewed, tested and adapted by the developer.

A concrete example of this collaboration was the discovery that the historical 1974/75 standings required goal average rather than the modern assumption of goal difference. This resulted in a change to the domain model and league sorting implementation.

Further details are documented in:

```text
AI_REFLECTION.md
AI_INSTRUCTIONS.md
ai/
```

---

# Clean Build

To reproduce the project from a clean state:

```bash
mvn clean test
mvn clean package
```

Then execute the application:

```bash
java -jar target/footbalLeague-0.0.1-SNAPSHOT.jar \
    data/1974-75-week-10.csv \
    output/standings.csv
```

No generated build artifacts or dependency directories need to be committed to the repository.

The Maven `target/` directory should remain excluded from version control.
