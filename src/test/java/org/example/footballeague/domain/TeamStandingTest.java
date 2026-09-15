package org.example.footballeague.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TeamStandingTest {

    @Test
    void recordsWinCorrectly() {

        TeamStanding standing =
                new TeamStanding("Liverpool");

        standing.recordMatch(2, 1);

        assertEquals(1, standing.getPlayed());
        assertEquals(1, standing.getWon());
        assertEquals(0, standing.getDraw());
        assertEquals(0, standing.getLost());
        assertEquals(2, standing.getGoalsFor());
        assertEquals(1, standing.getGoalsAgainst());
        assertEquals(2, standing.getPoints());
    }

    @Test
    void recordsDrawCorrectly() {

        TeamStanding standing =
                new TeamStanding("Liverpool");

        standing.recordMatch(1, 1);

        assertEquals(1, standing.getPlayed());
        assertEquals(0, standing.getWon());
        assertEquals(1, standing.getDraw());
        assertEquals(0, standing.getLost());
        assertEquals(1, standing.getPoints());
    }

    @Test
    void recordsLossCorrectly() {

        TeamStanding standing =
                new TeamStanding("Liverpool");

        standing.recordMatch(0, 2);

        assertEquals(1, standing.getPlayed());
        assertEquals(0, standing.getWon());
        assertEquals(0, standing.getDraw());
        assertEquals(1, standing.getLost());
        assertEquals(0, standing.getPoints());
    }

    @Test
    void calculatesGoalAverage() {

        TeamStanding standing =
                new TeamStanding("Liverpool");

        standing.recordMatch(6, 2);

        assertEquals(
                3.0,
                standing.getGoalAverage()
        );
    }

    @Test
    void goalAverageHandlesZeroGoalsAgainst() {

        TeamStanding standing =
                new TeamStanding("Liverpool");

        standing.recordMatch(3, 0);

        assertEquals(
                3.0,
                standing.getGoalAverage()
        );
    }
}