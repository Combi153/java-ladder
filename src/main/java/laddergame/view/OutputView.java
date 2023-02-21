package laddergame.view;

import laddergame.domain.rung.Rung;
import laddergame.domain.rung.Line;

import java.util.List;
import java.util.stream.Collectors;

import static laddergame.view.message.LadderMessage.*;
import static laddergame.view.message.Message.RESULT_GUIDE;

public class OutputView {

    public static void print(final String message) {
        System.out.println(message);
    }

    public void printResultGuide() {
        print(RESULT_GUIDE.getFormattedMessage());
    }

    public void printParticipantNames(final List<String> participantNames) {
        String paddedParticipantNames = makePaddedParticipantNames(participantNames);
        print(paddedParticipantNames.trim());
    }

    public void printLadder(final List<Line> ladder, final List<String> participantNames) {
        int firstNameLength = getLengthOfFirstParticipantName(participantNames);
        String ladderMessage = makeLadderMessage(ladder, firstNameLength);
        print(ladderMessage);
    }

    private String makePaddedParticipantNames(final List<String> participantNames) {
        return participantNames.stream()
                .map(this::getPaddedName)
                .collect(Collectors.joining());
    }

    private String getPaddedName(final String participantName) {
        return String.format("%6s", participantName);
    }

    private int getLengthOfFirstParticipantName(final List<String> participantNames) {
        String firstParticipantName = participantNames.get(0);
        return firstParticipantName.length();
    }

    private String makeLadderMessage(final List<Line> ladder, final int firstNameLength) {
        return ladder.stream()
                .map(rungs -> makeRungsMessage(rungs.getRungs(), firstNameLength))
                .collect(Collectors.joining(System.lineSeparator()));
    }

    private String makeRungsMessage(final List<Rung> rungs, final int firstNameLength) {
        final String firstLadderFrame = LADDER_PADDING.getMessage().repeat(firstNameLength) + LADDER_FRAME.getMessage();
        return rungs.stream()
                .map(rung -> makeRungMessage(rung.exists()))
                .collect(Collectors.joining(LADDER_FRAME.getMessage(), firstLadderFrame, LADDER_FRAME.getMessage()));
    }

    private String makeRungMessage(final boolean isExistence) {
        if (isExistence) {
            return LADDER_RUNG.getMessage();
        }
        return LADDER_BLANK.getMessage();
    }
}
