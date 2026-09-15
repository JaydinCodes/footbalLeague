package org.example.footballeague.service;

import org.example.footballeague.domain.TeamStanding;

import java.util.Comparator;
import java.util.List;

public class LeagueTableSorter {

    private static final Comparator<TeamStanding> STANDING_COMPARATOR =
            Comparator
                    .comparingInt(TeamStanding::getPoints)
                    .reversed()
                    .thenComparing(
                            TeamStanding::getGoalAverage,
                            Comparator.reverseOrder()
                    )
                    .thenComparing(
                            TeamStanding::getTeamName
                    );

    public List<TeamStanding> sort(
            List<TeamStanding> standings) {

        standings.sort(STANDING_COMPARATOR);

        return standings;
    }
}