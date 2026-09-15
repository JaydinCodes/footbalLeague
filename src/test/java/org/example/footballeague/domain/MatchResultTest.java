package org.example.footballeague.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class MatchResultTest {

    @Test
    void createsValidMatch() {

        MatchResult match = new MatchResult(
                1,
                "Liverpool",
                "Arsenal",
                2,
                1
        );

        assertEquals(1, match.getMatchWeek());
        assertEquals("Liverpool", match.getHomeTeam());
        assertEquals("Arsenal", match.getAwayTeam());
        assertEquals(2, match.getHomeGoals());
        assertEquals(1, match.getAwayGoals());
    }

    @Test
    void rejectsNegativeHomeGoals() {

        assertThrows(
                IllegalArgumentException.class,
                () -> new MatchResult(
                        1,
                        "Liverpool",
                        "Arsenal",
                        -1,
                        1
                )
        );
    }

    @Test
    void rejectsNegativeAwayGoals() {

        assertThrows(
                IllegalArgumentException.class,
                () -> new MatchResult(
                        1,
                        "Liverpool",
                        "Arsenal",
                        1,
                        -1
                )
        );
    }

    @Test
    void rejectsEmptyTeamName() {

        assertThrows(
                IllegalArgumentException.class,
                () -> new MatchResult(
                        1,
                        "",
                        "Arsenal",
                        1,
                        0
                )
        );
    }

    @Test
    void rejectsSameTeams() {

        assertThrows(
                IllegalArgumentException.class,
                () -> new MatchResult(
                        1,
                        "Liverpool",
                        "Liverpool",
                        1,
                        0
                )
        );
    }

    @Test
    void rejectsInvalidMatchWeek() {

        assertThrows(
                IllegalArgumentException.class,
                () -> new MatchResult(
                        0,
                        "Liverpool",
                        "Arsenal",
                        1,
                        0
                )
        );
    }
}