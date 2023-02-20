package laddergame.domain.ladder;

import laddergame.domain.rung.Rungs;
import laddergame.util.BooleanGenerator;

import java.util.ArrayList;
import java.util.List;

import static laddergame.domain.message.ErrorMessage.INVALID_HEIGHT_RANGE;
import static laddergame.domain.message.ErrorMessage.INVALID_HEIGHT_TYPE;

public class LadderFactory {

    private static final int MIN_HEIGHT = 1;
    private static final int MAX_HEIGHT = 10_000;
    private static final int DEFAULT_COUNT = 1;

    private final BooleanGenerator rungBooleanGenerator;

    private LadderFactory(final BooleanGenerator rungBooleanGenerator) {
        this.rungBooleanGenerator = rungBooleanGenerator;
    }

    public static LadderFactory create(final BooleanGenerator rungBooleanGenerator) {
        return new LadderFactory(rungBooleanGenerator);
    }

    public Ladder createLadder(final String height, final int participantCount) {
        int ladderHeight = convertToLadderHeight(height);
        List<Rungs> ladder = new ArrayList<>();
        for (int i = 0; i < ladderHeight; i++) {
            ladder.add(Rungs.create(makeRungCount(participantCount), rungBooleanGenerator));
        }
        return Ladder.create(ladder);
    }

    private int convertToLadderHeight(final String height) {
        int ladderHeight = validateHeightType(height);
        validateHeightRange(ladderHeight);
        return ladderHeight;
    }

    private int makeRungCount(final int participantCount) {
        return participantCount - DEFAULT_COUNT;
    }

    private int validateHeightType(final String height) {
        try {
            return Integer.parseInt(height);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(INVALID_HEIGHT_TYPE.getMessage());
        }
    }

    private void validateHeightRange(final int height) {
        if (height < MIN_HEIGHT || height > MAX_HEIGHT) {
            throw new IllegalArgumentException(INVALID_HEIGHT_RANGE.getMessage());
        }
    }
}
