# AI Collaboration Reflection

## AI Tool Used

I used OpenAI ChatGPT throughout the development of this project as an engineering collaborator.

I did not use Claude Code. I used ChatGPT for architectural discussions, implementation guidance, debugging, test design, code review and documentation.

I did not treat the generated suggestions as automatically correct. I used the discussions to make engineering decisions, then implemented, tested and reviewed those decisions myself.

## A Decision That Meaningfully Shaped the Solution

One of the most useful parts of the collaboration was that the solution evolved as we challenged our initial assumptions.

An early version of the application was relatively simple. The calculation logic, domain objects and input/output responsibilities were not as clearly separated as they are in the final implementation.

During the review, ChatGPT pushed toward separating the problem into distinct responsibilities:

* `MatchResult` for an individual match
* `TeamStanding` for a team's accumulated statistics
* `LeagueTableCalculator` for applying match results
* `LeagueTableSorter` for ranking the standings
* `CsvMatchReader` for input
* `CsvStandingWriter` for output
* `LeagueApplication` for command-line orchestration

I agreed with this direction because the assessment was not simply asking for a script that produced the correct answer. It asked for a production-ready application that I would be expected to explain.

This separation made the core calculation independently testable and meant that the CLI did not contain football-specific business logic.

## A Place Where the Initial Direction Was Wrong

A more concrete example was the league ranking rules.

Our initial implementation used goal difference as part of the ranking logic. That is a very natural assumption when building a modern football league table, but it was not correct for the historical dataset we were working with.

When we compared the implementation against the expected 1974/75 First Division standings, it became clear that the historical table was using **goal average**, calculated as goals scored divided by goals conceded.

That resulted in an actual change to the implementation rather than simply changing documentation.

`TeamStanding` was extended to calculate goal average, and the sorting responsibility was moved into `LeagueTableSorter`, where the ordering became:

1. Points descending
2. Goal average descending
3. Team name as a deterministic implementation tie-breaker

The team-name tie-breaker is deliberately treated as an implementation detail rather than being presented as a historical rule.

This was an important lesson for me because the obvious modern solution was not necessarily the correct solution for a historical dataset. The assessment required us to implement the rules applicable to that season, rather than assume current football rules.

## Handling the "10th Week" Requirement

Another decision that shaped the solution was interpreting what the assessment meant by calculating the league table after the tenth week.

The important distinction was between:

> matches played during week 10

and:

> the league standings after the tenth game week.

We treated the requirement as the second case. Therefore, the input dataset needs to represent the matches played up to that point in the season, rather than only the fixtures belonging to the tenth round.

This also explains why some teams in the historical result have played nine matches while others have played ten. The standings are based on the actual matches played by that point rather than artificially assigning every team ten games.

This affected how I approached the dataset. Instead of making the application assume that every team must have played the same number of matches, the calculation simply processes the supplied results and derives the actual number of games played.

## Domain Validation

Another area where the implementation changed during development was validation.

Rather than allowing invalid football data to propagate through the application, we added validation at the domain boundary.

For example, `MatchResult` validates:

* matchweek must be positive
* team names cannot be empty
* home and away teams cannot be the same
* goals cannot be negative

`TeamStanding` also validates the values being recorded.

I preferred this approach because invalid data should fail close to where it enters the domain rather than producing an incorrect league table several layers later.

## Testing Influenced the Design

AI assistance was also useful in identifying tests that I initially would not have considered as separate cases.

The final tests cover both individual pieces of logic and the application as a whole.

Examples include:

* a home win
* an away win
* a draw
* points calculation
* goal average calculation
* teams with equal points
* CSV rows with invalid column counts
* invalid scores
* missing input files
* invalid domain objects
* end-to-end CSV input to CSV output

The end-to-end test was particularly useful because it tests the actual flow of the application rather than only testing individual classes in isolation.

## Where I Disagreed With or Constrained AI Suggestions

I also made decisions to keep the implementation simpler rather than automatically adding complexity.

For example, the CSV format used by the assessment is straightforward, and the application only needs a small subset of the CSV fields for the calculation. I therefore kept the CSV layer deliberately lightweight rather than introducing a large CSV abstraction or unnecessary dependencies.

Similarly, I kept the domain calculation independent from the command-line interface. The CLI coordinates the application but does not perform the standings calculations itself.

These decisions were partly influenced by AI discussions, but they were ultimately engineering choices based on the requirements of the assessment.

## What I Learned From the Collaboration

The biggest benefit of using ChatGPT was not that it generated code faster.

It was that I could use it to challenge my assumptions while building the application.

The most important example was the historical ranking rule. A modern football implementation could easily have used goal difference without questioning it. Comparing the implementation against the historical result exposed that assumption and forced us to investigate the actual rules.

The process therefore became:

```text
Requirement
    ↓
Proposed design
    ↓
Implementation
    ↓
Tests
    ↓
Compare against expected behaviour
    ↓
Identify incorrect assumption
    ↓
Refactor
    ↓
Retest
```

That was the most valuable part of the AI collaboration for me. I was not simply asking an AI to produce a finished solution. I was using it as a second engineering perspective while still being responsible for understanding, testing and making the final decisions about the implementation.
